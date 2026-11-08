package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RapidReactionEffect extends MobEffect {
    private static final ResourceLocation BLINK_COOLDOWN_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rapid_reaction_blink_cooldown");
    private static final ResourceLocation STEP_HEIGHT_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rapid_reaction_step_height");

    public RapidReactionEffect() {
        super(MobEffectCategory.BENEFICIAL, -9315102);
        this.addAttributeModifier(ModAttributes.BLINK_COOLDOWN_REDUCTION, BLINK_COOLDOWN_ID,
                0.3, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.STEP_HEIGHT, STEP_HEIGHT_ID,
                0.5, AttributeModifier.Operation.ADD_VALUE);
    }
}
