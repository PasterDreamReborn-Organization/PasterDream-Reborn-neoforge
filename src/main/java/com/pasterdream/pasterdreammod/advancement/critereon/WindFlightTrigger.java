package com.pasterdream.pasterdreammod.advancement.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * 自定义进度触发器 —— 在风之旅途维度中带顺风/逆风效果累计飞行距离。
 * <p>
 * 飞行以鞘翅滑翔（isFallFlying）为准，距离为累计值，由
 * {@code WindDirectionHandler} 每 tick 结算并调用 {@link #trigger}。
 */
public class WindFlightTrigger extends SimpleCriterionTrigger<WindFlightTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "wind_flight");

    /**
     * 飞行时携带的风向效果类型。
     */
    public enum FlightType {
        TAILWIND("tailwind"),
        DEADWIND("deadwind");

        private final String id;

        FlightType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static FlightType byId(String id) {
            for (FlightType type : values()) {
                if (type.id.equals(id)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown wind flight type: " + id);
        }
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * 玩家带指定风向效果累计飞行距离更新时调用。
     *
     * @param player   触发玩家
     * @param type     生效的风向效果
     * @param distance 累计飞行距离（格）
     */
    public void trigger(ServerPlayer player, FlightType type, double distance) {
        this.trigger(player, instance -> instance.matches(type, distance));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, FlightType type,
                                         MinMaxBounds.Doubles distance)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.STRING.fieldOf("type").xmap(FlightType::byId, FlightType::getId)
                                .forGetter(TriggerInstance::type),
                        MinMaxBounds.Doubles.CODEC.optionalFieldOf("distance", MinMaxBounds.Doubles.ANY)
                                .forGetter(TriggerInstance::distance)
                ).apply(instance, TriggerInstance::new)
        );

        /**
         * 带指定风向效果累计飞行达到给定距离时触发。
         */
        public static Criterion<TriggerInstance> flown(FlightType type, MinMaxBounds.Doubles distance) {
            return ModCriteriaTriggers.WIND_FLIGHT.get().createCriterion(
                    new TriggerInstance(Optional.empty(), type, distance));
        }

        public boolean matches(FlightType type, double distance) {
            return this.type == type && this.distance.matches(distance);
        }
    }
}
