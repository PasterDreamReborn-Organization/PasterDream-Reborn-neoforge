package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class GoldenrodTeaEffect extends MobEffect {

    public GoldenrodTeaEffect() {
        super(MobEffectCategory.BENEFICIAL, -395670);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.removeEffect(MobEffects.HUNGER);
        entity.removeEffect(MobEffects.CONFUSION);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
