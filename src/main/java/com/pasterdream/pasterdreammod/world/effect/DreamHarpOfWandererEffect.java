package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
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

public class DreamHarpOfWandererEffect extends MobEffect {

    private static final ResourceLocation SAN_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_harp_san_variability");
    private static final ResourceLocation HEALTH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_harp_health");
    private static final ResourceLocation SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_harp_speed");

    public DreamHarpOfWandererEffect() {
        super(MobEffectCategory.BENEFICIAL, -1934926);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        entity.heal(8.0f);
    }

    @Override
    public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
        addModifier(attributeMap, ModAttributes.SAN_VARIABILITY, SAN_ID, 4.8,
                AttributeModifier.Operation.ADD_VALUE);
        addModifier(attributeMap, Attributes.MAX_HEALTH, HEALTH_ID, 4.0,
                AttributeModifier.Operation.ADD_VALUE);
        addModifier(attributeMap, Attributes.MOVEMENT_SPEED, SPEED_ID, 0.01,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        removeModifier(attributeMap, ModAttributes.SAN_VARIABILITY, SAN_ID);
        removeModifier(attributeMap, Attributes.MAX_HEALTH, HEALTH_ID);
        removeModifier(attributeMap, Attributes.MOVEMENT_SPEED, SPEED_ID);
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
