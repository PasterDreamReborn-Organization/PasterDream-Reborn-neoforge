package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

/**
 * 行动抑制 —— 禁用跳跃和飞行，不可解除。
 * 由亚伦柯斯之触血锁机制施加。
 */
public class RestrainmoveBlockEffect extends MobEffect {
    public RestrainmoveBlockEffect() {
        super(MobEffectCategory.BENEFICIAL, -7469562);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.removeEffect(MobEffects.JUMP);
        if (entity instanceof Player player) {
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance instance) {
        cures.clear();
    }
}
