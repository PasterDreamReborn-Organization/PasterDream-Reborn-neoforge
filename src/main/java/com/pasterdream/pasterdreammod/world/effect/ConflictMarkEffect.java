package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class ConflictMarkEffect extends MobEffect {
    public ConflictMarkEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF4444);
    }

    @Override
    public String getDescriptionId() {
        return "effect.pasterdream.conflict_mark";
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // 每 5 ticks (0.25秒) 触发一次
        return duration % 5 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity target, int amplifier) {
        if (target.level().isClientSide() || !target.isAlive()) {
            return true;
        }

        double range = Config.CONFLICT_MARK_RANGE.get() + amplifier * 4.0;
        ServerLevel serverLevel = (ServerLevel) target.level();

        serverLevel.getEntitiesOfClass(Mob.class, target.getBoundingBox().inflate(range))
                .stream()
                .filter(mob -> mob.isAlive() && mob != target)
                .filter(mob -> mob.canAttack(target) && canFight(mob))
                .forEach(mob -> mob.setTarget(target));
        return true;
    }

    /**
     * 检查生物是否有攻击能力（有攻击伤害属性的视为敌对/可战斗生物）
     */
    private boolean canFight(Mob mob) {
        AttributeInstance attr = mob.getAttribute(Attributes.ATTACK_DAMAGE);
        return attr != null && attr.getValue() > 0;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance instance) {
        // 牛奶等无法解除此效果
        cures.clear();
    }
}
