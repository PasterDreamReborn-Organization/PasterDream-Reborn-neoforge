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
 * 自定义进度触发器 —— 穿过破风幕帐。
 * 由 {@code BreakWindCurtainBlock#entityInside} 在玩家飞行穿过幕帐时调用。
 */
public class BreakWindCurtainTrigger extends SimpleCriterionTrigger<BreakWindCurtainTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "break_wind_curtain");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int count) {
        this.trigger(player, instance -> instance.matches(count));
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, 1);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints count)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        MinMaxBounds.Ints.CODEC.optionalFieldOf("count", MinMaxBounds.Ints.atLeast(1)).forGetter(TriggerInstance::count)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> passed() {
            return ModCriteriaTriggers.BREAK_WIND_CURTAIN.get().createCriterion(
                    new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.atLeast(1)));
        }

        public static Criterion<TriggerInstance> passedTimes(int times) {
            return ModCriteriaTriggers.BREAK_WIND_CURTAIN.get().createCriterion(
                    new TriggerInstance(Optional.empty(), MinMaxBounds.Ints.atLeast(times)));
        }

        public boolean matches(int count) {
            return this.count.matches(count);
        }
    }
}
