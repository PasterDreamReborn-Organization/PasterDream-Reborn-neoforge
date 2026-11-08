package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 回避衣装 buff：持续 90s，每 tick 释放银色粒子。
 */
public class EvasionCloakBuffEffect extends MobEffect {

    public EvasionCloakBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, -5509124);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY() + 1;
            double z = entity.getZ();
            serverLevel.sendParticles(ModParticleTypes.SILVER_PARTICLE.get(), x, y, z, 4, 0.4, 0.5, 0.4, 0.01);
            serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), x, y, z, 4, 0.4, 0.5, 0.4, 0.01);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
