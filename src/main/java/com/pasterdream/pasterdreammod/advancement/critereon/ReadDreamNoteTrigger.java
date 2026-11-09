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
 * 自定义进度触发器 —— 阅读特定内容的寻梦者笔记（书）。
 * <p>
 * 支持两种匹配模式：
 * <ul>
 *   <li>{@link TriggerInstance#forContent(String)} — 仅匹配指定内容的笔记</li>
 *   <li>{@link TriggerInstance#any()} — 匹配任意笔记阅读事件</li>
 * </ul>
 */
public class ReadDreamNoteTrigger extends SimpleCriterionTrigger<ReadDreamNoteTrigger.TriggerInstance> {

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "read_dream_note");

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * 当玩家阅读笔记时调用。
     *
     * @param player      触发玩家
     * @param contentKey  笔记内容的 NBT key
     */
    public void trigger(ServerPlayer player, String contentKey) {
        this.trigger(player, instance -> instance.matches(contentKey));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, String content)
            implements SimpleCriterionTrigger.SimpleInstance {

        /** 要匹配的笔记内容 key，空字符串表示匹配任意 */
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                        Codec.STRING.optionalFieldOf("content", "").forGetter(TriggerInstance::content)
                ).apply(instance, TriggerInstance::new)
        );

        /**
         * 创建仅匹配特定内容 key 的实例。
         */
        public static Criterion<TriggerInstance> forContent(String content) {
            return ModCriteriaTriggers.READ_DREAM_NOTE.get().createCriterion(
                    new TriggerInstance(Optional.empty(), content));
        }

        /**
         * 创建匹配任意笔记的实例。
         */
        public static Criterion<TriggerInstance> any() {
            return forContent("");
        }

        public boolean matches(String contentKey) {
            if (content.isEmpty()) {
                return true;
            }
            return content.equals(contentKey);
        }
    }
}
