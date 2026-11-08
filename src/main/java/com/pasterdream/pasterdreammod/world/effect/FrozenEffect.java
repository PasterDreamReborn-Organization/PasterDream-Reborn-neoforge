package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

/**
 * 冰冻 debuff —— 撤除 AI + 细雪 screen overlay + 无法移动/攻击。
 */
public class FrozenEffect extends MobEffect {

    private static final ResourceLocation MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "frozen");

    public FrozenEffect() {
        super(MobEffectCategory.HARMFUL, -4657930);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_ID,
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_ID,
                -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        // 效果开始时撤除 AI
        if (entity instanceof Mob mob) {
            mob.setNoAi(true);
        }
        super.onEffectAdded(entity, amplifier);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setTicksFrozen(200);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        // 对应来源 getCurativeItems 返回空列表：不可被牛奶等治愈
    }
}
