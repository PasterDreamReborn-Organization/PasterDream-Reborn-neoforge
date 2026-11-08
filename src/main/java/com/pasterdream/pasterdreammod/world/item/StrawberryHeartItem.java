package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StrawberryHeartItem extends Item {
    private static final double ENERGY_COST = 1;
    private static final int COOLDOWN_TICKS = 240; // 12秒
    private static final double EFFECT_RADIUS = 8.0;
    private static final int SAN_AURA_DURATION = 160; // 8秒
    private static final double SAN_AURA_RATE = 27.0; // SAN_VARIABILITY 加成 = 3.6 SAN / 8s ≈ 27/min
    private static final double SAN_INSTANT = 2.0;
    private static final ResourceLocation SAN_AURA_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "strawberry_heart_san_aura");
    public static final String SAN_AURA_TAG = "pasterdream:strawberry_san_aura";

    public StrawberryHeartItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    // ===== 物品逻辑 =====

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getCooldowns().isOnCooldown(this)) {
                return InteractionResultHolder.fail(stack);
            }
            double currentEnergy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(serverPlayer);
            if (!player.isCreative() && currentEnergy < ENERGY_COST) {
                serverPlayer.displayClientMessage(
                        Component.translatable("tooltip.pasterdream.strawberry_heart.no_energy"), true);
                return InteractionResultHolder.fail(stack);
            }
            if (!player.isCreative()) {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, -ENERGY_COST);
            }
            serverPlayer.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                tag.putLong("performance_start", level.getGameTime());
                tag.putBoolean("performance_active", true);
            });
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (level.isClientSide() || !(entity instanceof Player player)) return;

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.isEmpty()) return;

        // 演奏阶段：定时吉他音效 + 支援效果触发
        if (tag.getBoolean("performance_active")) {
            long startTick = tag.getLong("performance_start");
            long elapsed = level.getGameTime() - startTick;
            double px = player.getX(), py = player.getY(), pz = player.getZ();

            if (elapsed == 0) {
                level.playSound(null, px, py, pz,
                        SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 1.2F, 0.8F);
            } else if (elapsed == 4) {
                level.playSound(null, px, py, pz,
                        SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 1.2F, 1.0F);
            } else if (elapsed == 7) {
                level.playSound(null, px, py, pz,
                        SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 1.2F, 1.2F);
            } else if (elapsed == 10) {
                level.playSound(null, px, py, pz,
                        SoundEvents.NOTE_BLOCK_GUITAR.value(), SoundSource.PLAYERS, 1.4F, 1.7F);

                // 范围支援效果 + SAN 即时恢复 + SAN_VARIABILITY 光环
                Vec3 center = player.position();
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class,
                        new AABB(center, center).inflate(EFFECT_RADIUS));
                for (Player target : nearbyPlayers) {
                    target.heal(4.0F);
                    target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2, false, true, true));
                    target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 160, 0, false, true, true));
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 160, 0, false, true, true));

                    if (target instanceof ServerPlayer sp) {
                        SanHelper.addPlayerSanAndSync(sp, SAN_INSTANT);
                        applySanAura(sp);
                    }
                }

                CustomData.update(DataComponents.CUSTOM_DATA, stack, updated -> {
                    updated.putBoolean("performance_active", false);
                    updated.remove("performance_start");
                    updated.putLong("san_aura_end", level.getGameTime() + SAN_AURA_DURATION);
                });
            }
        }

        // SAN 光环：8 秒内维持 SAN_VARIABILITY 加成
        if (tag.contains("san_aura_end")) {
            long now = level.getGameTime();
            if (now < tag.getLong("san_aura_end")) {
                Vec3 center = player.position();
                List<ServerPlayer> nearby = level.getEntitiesOfClass(ServerPlayer.class,
                        new AABB(center, center).inflate(EFFECT_RADIUS));
                for (ServerPlayer target : nearby) {
                    applySanAura(target);
                }
            } else {
                Vec3 center = player.position();
                List<ServerPlayer> nearby = level.getEntitiesOfClass(ServerPlayer.class,
                        new AABB(center, center).inflate(EFFECT_RADIUS));
                for (ServerPlayer target : nearby) {
                    removeSanAura(target);
                }
                CustomData.update(DataComponents.CUSTOM_DATA, stack, updated -> updated.remove("san_aura_end"));
            }
        }
    }

    // ===== SAN 光环辅助方法 =====

    private static void applySanAura(ServerPlayer player) {
        var attr = player.getAttribute(ModAttributes.SAN_VARIABILITY);
        if (attr == null) return;
        var existing = attr.getModifier(SAN_AURA_ID);
        if (existing == null || existing.amount() != SAN_AURA_RATE) {
            attr.removeModifier(SAN_AURA_ID);
            attr.addTransientModifier(
                    new AttributeModifier(SAN_AURA_ID, SAN_AURA_RATE, AttributeModifier.Operation.ADD_VALUE));
        }
        player.getPersistentData().putBoolean(SAN_AURA_TAG, true);
    }

    private static void removeSanAura(ServerPlayer player) {
        var attr = player.getAttribute(ModAttributes.SAN_VARIABILITY);
        if (attr != null) {
            attr.removeModifier(SAN_AURA_ID);
        }
        player.getPersistentData().remove(SAN_AURA_TAG);
    }

    // ===== Tooltip =====

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.strawberry_heart.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.strawberry_heart.desc2"));
        tooltip.add(Component.translatable("tooltip.pasterdream.strawberry_heart.desc3"));
        tooltip.add(Component.translatable("tooltip.pasterdream.strawberry_heart.desc4"));
    }
}
