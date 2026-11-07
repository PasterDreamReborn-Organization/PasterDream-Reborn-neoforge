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
 * 自定义进度触发器 —— 在指定高度以上食用星河果冻。
 * <p>
 * 对应进度条件参数：
 * <ul>
 *   <li>{@code min_y} (int) — 触发所需的最小 Y 坐标，默认 -64</li>
 * </ul>
 */
public class EatGalaxyJellyAtHeightTrigger extends SimpleCriterionTrigger<EatGalaxyJellyAtHeightTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "eat_galaxy_jelly_at_height");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * 当玩家食用星河果冻时调用。
     *
     * @param player 触发玩家
     * @param y      玩家食用时的 Y 坐标
     */
    public void trigger(ServerPlayer player, double y) {
        this.trigger(player, instance -> instance.matches(y));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, int minY)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.INT.optionalFieldOf("min_y", -64).forGetter(TriggerInstance::minY)
                ).apply(instance, TriggerInstance::new)
        );

        /**
         * 创建默认条件实例 —— 要求 Y ≥ 256（原版建筑高度上限附近）。
         */
        public static Criterion<TriggerInstance> atBuildHeight() {
            return ModCriteriaTriggers.EAT_GALAXY_JELLY_AT_HEIGHT.get().createCriterion(new TriggerInstance(Optional.empty(), 256));
        }

        /**
         * 创建指定高度的条件实例。
         */
        public static Criterion<TriggerInstance> above(int minY) {
            return ModCriteriaTriggers.EAT_GALAXY_JELLY_AT_HEIGHT.get().createCriterion(new TriggerInstance(Optional.empty(), minY));
        }

        public boolean matches(double y) {
            return y >= minY;
        }
    }
}
