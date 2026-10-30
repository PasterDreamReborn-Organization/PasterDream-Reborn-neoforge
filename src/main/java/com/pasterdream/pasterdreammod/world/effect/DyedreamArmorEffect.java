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

public class DyedreamArmorEffect extends MobEffect implements ProtectedRemovalEffect {

    private static final ResourceLocation HP_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_armor_health");

    public DyedreamArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF69B4);
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
        // 每 30 秒获得伤害吸收 I（2 颗黄心，持续 30 秒）
        if (entity.tickCount % 600 == 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0,
                    true, false, false));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
