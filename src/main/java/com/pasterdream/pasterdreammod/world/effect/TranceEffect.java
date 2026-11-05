package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TranceEffect extends MobEffect {
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:trance");
    public TranceEffect() {
        super(MobEffectCategory.HARMFUL, -4427155);
        this.addAttributeModifier(ModAttributes.BLINK_COOLDOWN_REDUCTION, MODIFIER_ID, -1.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID, -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_ID, -0.2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_ID, -1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_REDUCTION, MODIFIER_ID, -0.5, AttributeModifier.Operation.ADD_VALUE);
    }
    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.trance";
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
