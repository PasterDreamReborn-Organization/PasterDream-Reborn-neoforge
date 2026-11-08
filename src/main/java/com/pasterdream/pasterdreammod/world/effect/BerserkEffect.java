package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BerserkEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "berserk");

    public BerserkEffect() {
        super(MobEffectCategory.BENEFICIAL, -4827668);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_ID,
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID,
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_ID,
                0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.SKILL_DAMAGE_RATE, MODIFIER_ID,
                0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.BLINK_COOLDOWN_REDUCTION, MODIFIER_ID,
                0.3, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.SKILL_COOLDOWN_REDUCTION, MODIFIER_ID,
                0.3, AttributeModifier.Operation.ADD_VALUE);
    }
}
