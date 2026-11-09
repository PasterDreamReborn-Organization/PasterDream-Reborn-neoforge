package com.pasterdream.pasterdreammod.world.item;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.mixin.LootPoolAccessor;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.List;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class FishingLootsHandler {

    private static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<LootTable> DEEP_SEA_TREASURE =
            key("gameplay/fishing/deep_sea_treasure");
    public static final ResourceKey<LootTable> DYEDREAM_DEEP_SEA_TREASURE =
            key("gameplay/fishing/dyedream_deep_sea_treasure");
    public static final ResourceKey<LootTable> SHADOW_DEEP_SEA_TREASURE =
            key("gameplay/fishing/shadow_deep_sea_treasure");
    public static final ResourceKey<LootTable> SHADOW_JUNK =
            key("gameplay/fishing/shadow_junk");

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));
    private static final ResourceKey<Level> LAMP_SHADOW_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));
    private static final ResourceKey<Biome> SHADOW_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_ocean"));

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (!event.getKey().equals(BuiltInLootTables.FISHING)) return;
        LootPool originalMainPool = event.getTable().getPool("main");
        if (originalMainPool == null) return;

        // 提取原版 main 池的条目，保留其他模组（如 Aquaculture）注入的条目
        List<LootPoolEntryContainer> originalEntries =
                List.copyOf(((LootPoolAccessor) originalMainPool).pasterdream$getEntries());
        if (originalEntries.isEmpty()) return;
        HolderGetter<Biome> biomes = event.getRegistries().lookupOrThrow(Registries.BIOME);

        LootTable.Builder table = LootTable.lootTable()
                // 主世界池 — 保留原版/其他模组注入的条目，追加深海秘宝
                .withPool(createPoolWithExtraEntry(originalEntries,
                        LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(Level.OVERWORLD)),
                        NestedLootTable.lootTableReference(DEEP_SEA_TREASURE)
                                .setWeight(10).setQuality(2)))
                // 染梦维度池 — 保留原版条目，追加染梦深海秘宝
                .withPool(createPoolWithExtraEntry(originalEntries,
                        LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(DYEDREAM_WORLD)),
                        NestedLootTable.lootTableReference(DYEDREAM_DEEP_SEA_TREASURE)
                                .setWeight(10).setQuality(2)))
                // 浸影维度·垃圾池 — 灯影维度所有群系
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LocationCheck.checkLocation(
                                LocationPredicate.Builder.location().setDimension(LAMP_SHADOW_WORLD)))
                        .add(NestedLootTable.lootTableReference(SHADOW_JUNK)
                                .setWeight(1)))
                // 兜底池 — 其他维度保持原版行为（含其他模组注入的条目）
                .withPool(createPoolWithExtraEntry(originalEntries,
                        InvertedLootItemCondition.invert(
                                AnyOfCondition.anyOf(
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(Level.OVERWORLD)),
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(DYEDREAM_WORLD)),
                                        LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location().setDimension(LAMP_SHADOW_WORLD)))),
                        null));
        // 浸影维度·秘宝池 — 仅在阴影之海触发；群系尚未迁入时跳过
        biomes.get(SHADOW_OCEAN).ifPresent(shadowOcean -> table.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location()
                                .setDimension(LAMP_SHADOW_WORLD)
                                .setBiomes(HolderSet.direct(shadowOcean))))
                .add(EmptyLootItem.emptyItem()
                        .setWeight(90))
                .add(NestedLootTable.lootTableReference(SHADOW_DEEP_SEA_TREASURE)
                        .setWeight(10).setQuality(2))));
        event.setTable(table.build());
        LOGGER.info("[FishingLoots] Replaced fishing loot table with dimension-aware pools");
    }

    private static ResourceKey<LootTable> key(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    /**
     * 创建一个保留原版条目并可选追加额外条目的战利品池 Builder。
     *
     * @param baseEntries 从原版 main 池提取的条目（含其他模组注入）
     * @param condition   维度/群系条件
     * @param extraEntry  额外追加的条目，为 null 则不追加
     * @return LootPool.Builder 可直接传给 {@link LootTable.Builder#withPool}
     */
    private static LootPool.Builder createPoolWithExtraEntry(
            List<LootPoolEntryContainer> baseEntries,
            LootItemCondition.Builder condition,
            LootPoolEntryContainer.Builder<?> extraEntry) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(condition);
        for (LootPoolEntryContainer entry : baseEntries) {
            builder.add(new ExistingEntryBuilder(entry));
        }
        if (extraEntry != null) {
            builder.add(extraEntry);
        }
        return builder;
    }

    /**
     * 以已构建的条目作为 Builder 产物，绕过 {@code LootPool.Builder} 只接受 Builder 的限制。
     */
    private static final class ExistingEntryBuilder extends LootPoolEntryContainer.Builder<ExistingEntryBuilder> {
        private final LootPoolEntryContainer entry;

        private ExistingEntryBuilder(LootPoolEntryContainer entry) {
            this.entry = entry;
        }

        @Override
        protected ExistingEntryBuilder getThis() {
            return this;
        }

        @Override
        public LootPoolEntryContainer build() {
            return entry;
        }
    }
}
