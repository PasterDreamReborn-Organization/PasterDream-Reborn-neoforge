package com.pasterdream.pasterdreammod.world.effect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * 战意 buff：击杀后获得，每级 +2 攻击力 + 每秒回血 + 周围 6 格范围光环共享。
 * 等级加成：一级=+2、+0.5血/秒，二级=+4、+1.0血/秒，三级=+6、+1.5血/秒
 */
public class WarFlagEffect extends MobEffect {

    private static final ResourceLocation ATTACK_DAMAGE_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "war_flag_attack_damage");
    private static final double AURA_RANGE = 6.0;

    public WarFlagEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0F74AE);
        // 攻击力百分比加成（amplifier 自动叠加）
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_ID,
                0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // 每秒 0.5 点/级的回血
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(0.5F * (amplifier + 1));
        }

        // 光环：向周围 6 格内的友方玩家共享战意 buff
        if (entity instanceof Player carrier && !entity.level().isClientSide()) {
            List<Player> nearby = entity.level().getEntitiesOfClass(Player.class,
                    entity.getBoundingBox().inflate(AURA_RANGE),
                    p -> p != carrier && p.distanceToSqr(carrier) <= AURA_RANGE * AURA_RANGE);

            for (Player ally : nearby) {
                MobEffectInstance existing = ally.getEffect(ModEffects.WAR_FLAG);
                // 若对侧没有 buff，或等级更低、或剩余时间不足 5 秒时刷新
                if (existing == null
                        || existing.getAmplifier() < amplifier
                        || existing.getDuration() < 100) {
                    ally.addEffect(new MobEffectInstance(ModEffects.WAR_FLAG, 100, amplifier,
                            false, false, true));
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0; // 每秒触发一次
    }
}
