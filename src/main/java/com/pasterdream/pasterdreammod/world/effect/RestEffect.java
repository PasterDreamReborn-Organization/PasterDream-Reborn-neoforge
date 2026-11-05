package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class RestEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:rest");

    public RestEffect() {
        super(MobEffectCategory.BENEFICIAL, -3873025);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY, MODIFIER_ID,
                1.2, AttributeModifier.Operation.ADD_VALUE);
    }
}
