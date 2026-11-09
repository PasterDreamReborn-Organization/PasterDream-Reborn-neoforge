package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.mixin.LivingEntityAccessor;
import com.pasterdream.pasterdreammod.world.effect.EvasionEffect;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookWithNBTToCreativeModeTab;
import com.pasterdream.pasterdreammod.world.skill.EvasionEffectHandler;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class PlayerEvents {

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_world"));
    private static final ResourceLocation LOOK_AT_PINK_SHEEP_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/look_at_pink_sheep");
    private static final ResourceLocation FIRST_CONTACT_DYEDREAM_CRACK_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/first_contact_dyedream_crack");
    private static final ResourceLocation DYEDREAM_CRACK_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dyedream_crack");
    private static final ResourceLocation DYEDREAM_WORLD_ADV =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "story/dyedream_world");
    private static final String NOTE_DELAY_TAG = "pasterdream:dream_note_delay";

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        EvasionEffectHandler.onPlayerTick(event.getEntity());

        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            tickNoteDelay(serverPlayer);
        }

        // 检查玩家是否在染梦维度注视粉色羊（已获得成就则跳过）
        if (event.getEntity() instanceof ServerPlayer serverPlayer
                && serverPlayer.level().dimension().equals(DYEDREAM_WORLD)
                && serverPlayer.tickCount % 20 == 0
                && !isAdvancementDone(serverPlayer, LOOK_AT_PINK_SHEEP_ADV)) {
            Vec3 eyePos = serverPlayer.getEyePosition(1.0F);
            Vec3 lookVec = serverPlayer.getViewVector(1.0F);
            AABB nearby = serverPlayer.getBoundingBox().inflate(16.0);
            var nearbySheep = serverPlayer.level().getEntitiesOfClass(
                    Sheep.class, nearby,
                    s -> s.getColor() == DyeColor.PINK);
            for (Sheep sheep : nearbySheep) {
                Vec3 toSheep = sheep.getEyePosition(1.0F).subtract(eyePos).normalize();
                if (lookVec.dot(toSheep) > 0.95) {
                    ModCriteriaTriggers.LOOK_AT_PINK_SHEEP.get().trigger(serverPlayer);
                    break;
                }
            }
        }
    }

    private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation id) {
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    /** 授予指定进度（幂等）。 */
    private static void grantAdvancement(ServerPlayer player, ResourceLocation id) {
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        if (adv == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(adv);
        for (String criteria : progress.getRemainingCriteria()) {
            player.getAdvancements().award(adv, criteria);
        }
    }

    /** 入睡后延迟发放染梦裂隙笔记；起床则取消。 */
    private static void tickNoteDelay(ServerPlayer player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(NOTE_DELAY_TAG)) {
            return;
        }

        // 仅在玩家真正躺在床上时计时；起床则重置倒计时
        if (!player.isSleeping()) {
            data.remove(NOTE_DELAY_TAG);
            return;
        }

        int delay = data.getInt(NOTE_DELAY_TAG) - 1;
        if (delay > 0) {
            data.putInt(NOTE_DELAY_TAG, delay);
            return;
        }

        data.remove(NOTE_DELAY_TAG);

        if (!player.isAlive()) {
            return;
        }

        ItemStack note = DreamNotesBookWithNBTToCreativeModeTab.buildNBT("染梦裂隙");
        if (!player.getInventory().add(note)) {
            player.drop(note, false);
        }

        // 发放笔记时直接授予染梦裂隙进度，避免玩家未读笔记再次睡觉导致重复发放
        grantAdvancement(player, DYEDREAM_CRACK_ADV);

        player.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.1"), false);
        player.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.2"), false);
        player.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.3"), false);
        player.displayClientMessage(
                Component.translatable("message.pasterdream.sleep.dream_of_crack.4"), false);
    }

    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!player.hasEffect(ModEffects.EVASION)) return;
        LivingEntityAccessor accessor = (LivingEntityAccessor) player;
        if (player.invulnerableTime > 10 && !event.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN)
                && event.getAmount() <= accessor.pasterdream$getLastHurt()) return;

        var effect = player.getEffect(ModEffects.EVASION);
        if (effect == null) return;

        int amplifier = effect.getAmplifier();
        int duration = effect.getDuration();
        EvasionEffect.allowRemoval(player);
        player.removeEffect(ModEffects.EVASION);

        // multi-level evasion: consume one level, keep remainder
        if (amplifier > 0) {
            player.addEffect(new MobEffectInstance(ModEffects.EVASION,
                    duration, amplifier - 1, false, false));
        } else {
            // 回避次数用完：转身衣装状态效果自动取消
            player.removeEffect(ModEffects.TURNBACK_CLOAK);
        }

        float incomingDamage = event.getAmount();
        event.setAmount(0);
        event.setCanceled(true);
        // 1.21 的 IncomingDamage 在原版受击无敌时间设置前触发；取消后需补回计时与上次受击量，
        // 避免贴身碰撞在回避效果消耗后紧接着再次造成伤害。
        player.invulnerableTime = Math.max(player.invulnerableTime, 20);
        accessor.pasterdream$setLastHurt(incomingDamage);

        // 反击戒指 / 转身衣装 / 回避衣装成功闪避时获得反击 buff
        if (CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.COUNTER_RING.get()).isPresent()
                        || h.findFirstCurio(ModItems.TURNBACK_CLOAK.get()).isPresent()
                        || h.findFirstCurio(ModItems.EVASION_CLOAK.get()).isPresent())
                .orElse(false)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0, false, false));
            player.addEffect(new MobEffectInstance(ModEffects.COUNTER_ATTACK, 200, 0, false, false));
        }

        if (player.level() instanceof ServerLevel serverLevel) {
            EvasionEffectHandler.execute(serverLevel, player);
        }
    }

    /** 反击 buff：下一次造成的伤害在结算时提高 50%，随后移除反击攻击力 I。 */
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;
        if (!player.hasEffect(ModEffects.COUNTER_ATTACK)) return;
        if (event.getNewDamage() <= 0) return;

        event.setNewDamage(event.getNewDamage() * 1.5f);
        player.removeEffect(ModEffects.COUNTER_ATTACK);
        player.removeEffect(MobEffects.DAMAGE_BOOST);
    }

    public static void onPlayerSleepInBed(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        // 与来源相同，白天尝试睡觉时跳过。
        if (player.level().isDay()) return;

        // 躺下给予3分钟休憩效果
        player.addEffect(new MobEffectInstance(ModEffects.REST,
                3600, 0, false, false));

        // 玩家接触过染梦裂隙但尚未获得染梦裂隙笔记时，睡觉触发笔记发放倒计时
        if (player instanceof ServerPlayer serverPlayer
                && isAdvancementDone(serverPlayer, FIRST_CONTACT_DYEDREAM_CRACK_ADV)
                && !isAdvancementDone(serverPlayer, DYEDREAM_CRACK_ADV)) {
            player.getPersistentData().putInt(NOTE_DELAY_TAG, 40);
        }
    }

    /** 玩家首次进入染梦世界时，授予进度。 */
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!event.getTo().equals(DYEDREAM_WORLD)) {
            return;
        }

        grantAdvancement(serverPlayer, DYEDREAM_WORLD_ADV);
    }
}
