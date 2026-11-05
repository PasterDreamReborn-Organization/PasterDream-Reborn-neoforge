package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CookEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:cook");

    public CookEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xEDF1C3);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY, MODIFIER_ID,
                1.2, AttributeModifier.Operation.ADD_VALUE);
    }
}
