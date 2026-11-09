package com.pasterdream.pasterdreammod.advancement.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * 自定义进度触发器 —— 在梦维度中使用苍白骨针。
 * <p>
 * 支持两种匹配模式：
 * <ul>
 *   <li>{@link TriggerInstance#use()} —— 任意使用骨针即触发（对应"哦，疼！"进度）</li>
 *   <li>{@link TriggerInstance#fellAndUse()} —— 要求玩家摔落超过 10 格后使用（对应"人类坠出梦境"进度）</li>
 * </ul>
 */
public class UseBoneNeedleTrigger extends SimpleCriterionTrigger<UseBoneNeedleTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "use_boneneedle");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * 当玩家在梦维度中使用骨针时调用。
     *
     * @param player     触发玩家
     * @param wasFalling 玩家使用骨针时是否处于摔落状态
     */
    public void trigger(ServerPlayer player, boolean wasFalling) {
        this.trigger(player, instance -> instance.matches(wasFalling));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, boolean requireFalling)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.BOOL.optionalFieldOf("falling", false).forGetter(TriggerInstance::requireFalling)
                ).apply(instance, TriggerInstance::new)
        );

        /**
         * 用于"哦，疼！"进度。
         */
        public static Criterion<TriggerInstance> use() {
            return ModCriteriaTriggers.USE_BONE_NEEDLE.get().createCriterion(
                    new TriggerInstance(Optional.empty(), false));
        }

        /**
         * 用于"人类坠出梦境"进度。
         */
        public static Criterion<TriggerInstance> fellAndUse() {
            return ModCriteriaTriggers.USE_BONE_NEEDLE.get().createCriterion(
                    new TriggerInstance(Optional.empty(), true));
        }

        public boolean matches(boolean wasFalling) {
            if (requireFalling) {
                return wasFalling;
            }
            return true;
        }
    }
}
