package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SculkArmorEffect extends MobEffect implements ProtectedRemovalEffect {

    private static final ResourceLocation HP_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk_armor_health");

    public SculkArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x1A4D5E);
    }

    @Override
    public boolean hasRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean consumeRemovalAllowance(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean forceApplicableWhenAffected() {
        return true;
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
        AttributeInstance attr = attributeMap.getInstance(Attributes.MAX_HEALTH);
        if (attr != null && attr.getModifier(HP_ID) == null) {
            attr.addTransientModifier(new AttributeModifier(HP_ID, 4.0,
                    AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        AttributeInstance attr = attributeMap.getInstance(Attributes.MAX_HEALTH);
        if (attr != null) attr.removeModifier(HP_ID);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // 身处深层时，每 tick 给予抗性
        if (entity.getY() < 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, 0,
                    true, false, false));
        }
        // 受到黑暗效果时，移除黑暗并给予移速和抗性
        if (entity.hasEffect(MobEffects.DARKNESS)) {
            entity.removeEffect(MobEffects.DARKNESS);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1,
                    true, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1,
                    true, false, false));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
