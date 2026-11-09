package com.pasterdream.pasterdreammod.advancement.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * 自定义进度触发器 —— 持有前置进度。
 * <p>
 * 用于「拥有物品 + 已完成前置进度」这类组合条件；组合条件中的
 * {@code inventory_changed} 会由原版自动判定，前置进度则在
 * {@code PlayerEvents} 的周期检查中调用本触发器。
 */
public class HasAdvancementTrigger extends SimpleCriterionTrigger<HasAdvancementTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "has_advancement");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> instance.matches(player));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation advancement)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ResourceLocation.CODEC.fieldOf("advancement").forGetter(TriggerInstance::advancement)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> hasAdvancement(ResourceLocation advancement) {
            return ModCriteriaTriggers.HAS_ADVANCEMENT.get().createCriterion(
                    new TriggerInstance(Optional.empty(), advancement));
        }

        public boolean matches(ServerPlayer player) {
            AdvancementHolder holder = player.server.getAdvancements().get(this.advancement);
            return holder != null && player.getAdvancements().getOrStartProgress(holder).isDone();
        }
    }
}
