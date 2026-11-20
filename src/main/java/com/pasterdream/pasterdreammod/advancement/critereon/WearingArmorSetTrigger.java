package com.pasterdream.pasterdreammod.advancement.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.MeltDreamCrystalArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.ShadowAlloyArmorItem;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * 自定义进度触发器 —— 玩家同时穿戴某一套护甲的全部四件。
 *
 * <p>为什么不用 {@code inventory_changed} + {@code requirements(AND)}：后者的判据是逐条
 * latch 并持久化的，AND 只表示「四件在任意时刻各拿到过」，并不要求同时持有；
 * 要「此刻正穿着」的语义就必须用自定义触发器。本触发器直接复用各护甲类的
 * {@code hasFullSet} 判定。
 *
 * <p>由 {@code ArmorSetAdvancementHandler} 在换装/登录/重生时触发（登录与重生覆盖
 * 「上线时已经穿着整套」这种没有换装事件的情况）。
 *
 * <p>1.21 适配：判据的序列化由 {@code createInstance(JsonObject...)}/{@code serializeToJson}
 * 改为 {@link Codec}，{@code set} 字段以字符串 id 编解码。
 */
public class WearingArmorSetTrigger extends SimpleCriterionTrigger<WearingArmorSetTrigger.TriggerInstance> {

    /** 可判定的护甲套装，{@code id} 即进度 JSON 中 {@code set} 字段的取值。 */
    public enum ArmorSet {
        /** 融梦水晶套装 */
        MELT_DREAM("melt_dream", MeltDreamCrystalArmorItem::hasFullSet),
        /** 萦风合金套装 */
        FLUFFY_WIND_ALLOY("fluffy_wind_alloy", FluffyWindAlloyArmorItem::hasFullSet),
        /** 暗影合金套装 */
        SHADOW_ALLOY("shadow_alloy", ShadowAlloyArmorItem::hasFullSet);

        public static final Codec<ArmorSet> CODEC = Codec.STRING.comapFlatMap(
                id -> {
                    ArmorSet set = byId(id);
                    if (set == null) {
                        return DataResult.error(() -> "Unknown armor set: " + id);
                    }
                    return DataResult.success(set);
                },
                ArmorSet::id
        );

        private final String id;
        private final Predicate<LivingEntity> wearing;

        ArmorSet(String id, Predicate<LivingEntity> wearing) {
            this.id = id;
            this.wearing = wearing;
        }

        public String id() {
            return this.id;
        }

        public boolean isWearing(LivingEntity entity) {
            return this.wearing.test(entity);
        }

        private static ArmorSet byId(String id) {
            for (ArmorSet set : values()) {
                if (set.id.equals(id)) {
                    return set;
                }
            }
            return null;
        }
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ArmorSet set) {
        this.trigger(player, instance -> instance.set == set);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, ArmorSet set)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        ArmorSet.CODEC.fieldOf("set").forGetter(TriggerInstance::set)
                ).apply(instance, TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> wearing(ArmorSet set) {
            return ModCriteriaTriggers.WEARING_ARMOR_SET.get()
                    .createCriterion(new TriggerInstance(Optional.empty(), set));
        }
    }
}
