package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.mixin.LivingEntityAccessor;
import com.pasterdream.pasterdreammod.world.effect.EvasionEffect;
import com.pasterdream.pasterdreammod.world.skill.EvasionEffectHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class PlayerEvents {
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        EvasionEffectHandler.onPlayerTick(event.getEntity());
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
                .map(h -> h.findFirstCurio(ModItems.COUNTER_RING.get()).isPresent())
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
    }
}
