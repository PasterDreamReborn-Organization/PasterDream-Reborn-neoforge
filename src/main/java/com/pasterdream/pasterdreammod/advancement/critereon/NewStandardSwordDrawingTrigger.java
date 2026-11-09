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
 * 自定义进度触发器 —— 新概念拔剑。
 * 由 {@code LostSwordTombHandler} 在剑冢击碎时调用。
 */
public class NewStandardSwordDrawingTrigger extends SimpleCriterionTrigger<NewStandardSwordDrawingTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "new_standard_sword_drawing");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> draw() {
            return ModCriteriaTriggers.NEW_STANDARD_SWORD_DRAWING.get().createCriterion(new TriggerInstance(Optional.empty()));
        }
    }
}
