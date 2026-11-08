package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HolyGrailEffect extends MobEffect {
    private static final ResourceLocation HEALTH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "holy_grail_health");

    public HolyGrailEffect() {
        super(MobEffectCategory.BENEFICIAL, -6341);
        this.addAttributeModifier(Attributes.MAX_HEALTH, HEALTH_ID,
                15, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            float healthPercent = entity.getHealth() / entity.getMaxHealth();
            int regenLevel;
            if (healthPercent >= 0.66f) {
                regenLevel = 0; // 生命恢复 I
            } else if (healthPercent >= 0.33f) {
                regenLevel = 1; // 生命恢复 II
            } else {
                regenLevel = 2; // 生命恢复 III
            }
            // 直接回血，与原版再生速率一致: 1 HP per (50 >> level) ticks
            // 每 20 tick 执行一次，按比例回复
            entity.heal(20.0f / (50 >> regenLevel));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0; // 每秒检查一次
    }
}
