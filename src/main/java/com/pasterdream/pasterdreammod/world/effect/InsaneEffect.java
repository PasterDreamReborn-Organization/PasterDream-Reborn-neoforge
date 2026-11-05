package com.pasterdream.pasterdreammod.world.effect;

import net.minecraft.resources.ResourceLocation;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class InsaneEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID = ResourceLocation.parse("pasterdream:insane");

    public InsaneEffect() {
        super(MobEffectCategory.HARMFUL, -14744315);
        this.addAttributeModifier(ModAttributes.BLINK_COOLDOWN_REDUCTION, MODIFIER_ID, -2.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID, -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_ID, -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_ID, -2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, MODIFIER_ID, -0.2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, MODIFIER_ID, -0.2, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.insane";
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayer player) || !player.isAlive()) return true;
        if (player.isCreative() || player.isSpectator()) return true;
        if (player.getHealth() <= 1) return true;
        if (amplifier < 2) return true;
        if (ShadowDifficultyHelper.getDifficulty(player) <= 0) return true;
        player.hurt(new DamageSource(player.serverLevel().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD)), 1);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
