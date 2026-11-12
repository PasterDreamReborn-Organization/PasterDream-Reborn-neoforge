package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

/**
 * 压抑 —— 降低SAN变化率，不可解除。
 * 由暗影符文塔施加。
 */
public class OppressionEffect extends MobEffect {
    private static final ResourceLocation OPPRESSION_ID = ResourceLocation.parse("pasterdream:oppression");

    public OppressionEffect() {
        super(MobEffectCategory.HARMFUL, 0x0);
        this.addAttributeModifier(ModAttributes.SAN_VARIABILITY, OPPRESSION_ID, -9.6,
                AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance instance) {
        cures.clear();
    }
}
