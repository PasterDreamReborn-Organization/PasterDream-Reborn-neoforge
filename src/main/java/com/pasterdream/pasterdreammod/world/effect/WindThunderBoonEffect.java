package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/** 风雷之佑套装的状态标记；具体数值由护甲处理器结算。 */
public class WindThunderBoonEffect extends MobEffect implements ProtectedRemovalEffect {
    public WindThunderBoonEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x9BE0FF);
    }

    @Override
    public boolean hasRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean consumeRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean forceApplicableWhenAffected() {
        return true;
    }
}
