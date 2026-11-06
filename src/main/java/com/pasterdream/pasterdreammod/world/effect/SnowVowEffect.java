package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SnowVowEffect extends MobEffect {
    private static final ResourceLocation LUCK_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snow_vow_luck");

    public SnowVowEffect() {
        super(MobEffectCategory.BENEFICIAL, -7684397);
        this.addAttributeModifier(Attributes.LUCK, LUCK_ID, 3.0, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.clearFire();
        entity.setTicksFrozen(0);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
