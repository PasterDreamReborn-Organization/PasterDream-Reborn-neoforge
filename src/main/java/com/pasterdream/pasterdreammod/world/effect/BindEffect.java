package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 束缚 —— 大幅降低移动速度，使目标无法移动。
 * 由白厄剑雨命中施加。
 */
public class BindEffect extends MobEffect {

    private static final ResourceLocation BIND_ID = ResourceLocation.parse("pasterdream:bind");

    public BindEffect() {
        super(MobEffectCategory.HARMFUL, -6180680);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, BIND_ID, -1, AttributeModifier.Operation.ADD_VALUE);
    }
}
