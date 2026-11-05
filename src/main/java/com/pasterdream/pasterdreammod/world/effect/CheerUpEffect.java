package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CheerUpEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:cheer_up");

    public CheerUpEffect() {
        super(MobEffectCategory.BENEFICIAL, -33158);
        this.addAttributeModifier(ModAttributes.BLINK_COOLDOWN_REDUCTION, MODIFIER_ID, 0.1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID, 0.05, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_ID, 0.05, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_REDUCTION, MODIFIER_ID, 0.1, AttributeModifier.Operation.ADD_VALUE);
    }
}
