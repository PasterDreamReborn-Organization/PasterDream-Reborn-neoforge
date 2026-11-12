package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 顺风 buff：在风之旅途维度顺风行走时获得，提升移速、闪烁距离，降低闪烁冷却。
 * 与逆风 buff 互斥。
 */
public class TailwindEffect extends MobEffect {

    private static final ResourceLocation SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tailwind_speed");
    private static final ResourceLocation RANGE_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tailwind_range");
    private static final ResourceLocation CD_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "tailwind_cooldown");

    public TailwindEffect() {
        super(MobEffectCategory.BENEFICIAL, -7087682);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        entity.removeEffect(ModEffects.DEADWIND);
        super.onEffectAdded(entity, amplifier);
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
        double speed = amplifier == 0 ? 0.03 : 0.04;
        double range = amplifier == 0 ? 1.0 : 1.5;
        double cd = amplifier == 0 ? 0.3 : 0.4;

        addModifier(attributeMap, Attributes.MOVEMENT_SPEED, SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE);
        addModifier(attributeMap, ModAttributes.BLINK_RANGE, RANGE_ID, range, AttributeModifier.Operation.ADD_VALUE);
        // 顺风降低瞬身冷却：-0.3 / -0.4 缩减，对应 0.7× / 0.6×
        addModifier(attributeMap, ModAttributes.BLINK_COOLDOWN_REDUCTION, CD_ID, cd, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        removeModifier(attributeMap, Attributes.MOVEMENT_SPEED, SPEED_ID);
        removeModifier(attributeMap, ModAttributes.BLINK_RANGE, RANGE_ID);
        removeModifier(attributeMap, ModAttributes.BLINK_COOLDOWN_REDUCTION, CD_ID);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private static void addModifier(AttributeMap attributeMap, Holder<Attribute> attribute, ResourceLocation id,
                                    double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance != null && instance.getModifier(id) == null) {
            instance.addTransientModifier(new AttributeModifier(id, amount, operation));
        }
    }

    private static void removeModifier(AttributeMap attributeMap, Holder<Attribute> attribute, ResourceLocation id) {
        AttributeInstance instance = attributeMap.getInstance(attribute);
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}
