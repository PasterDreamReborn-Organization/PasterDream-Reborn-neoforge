package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MementoEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:memento_luck");

    public MementoEffect() {
        super(MobEffectCategory.BENEFICIAL, -8970);
        this.addAttributeModifier(Attributes.LUCK, MODIFIER_ID, 10.0, AttributeModifier.Operation.ADD_VALUE);
    }
}
