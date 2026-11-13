package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.function.BiConsumer;

public class ModWindJourneyStructureLootTablesProvider implements LootTableSubProvider {
    private final HolderLookup.Provider registries;

    public ModWindJourneyStructureLootTablesProvider(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        // 风之旅途通用战利品（原 loots_relic_6）：热气球/波奇/灵梦/风车小屋/失落风骑士遗迹
        consumer.accept(
                ModLootTables.WIND_JOURNEY_CHEST_LOOT,
                LootTable.lootTable()
                        // 维度特色物品，固定2抽取，幸运影响系数0.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MAGIC_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
                        // 植物，固定2抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_CLEAVING_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_FEATHER_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_ISLAND_REED.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        // 食物，固定3抽取，幸运影响系数0.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FORTUNE_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_JELLO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        // 建材，固定1抽取，不提供额外抽取次数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOSSY_CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_CLOUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_LANTERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // 装备&工具，固定1抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 12.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS,
                                                new Fireworks(2, List.of(new FireworkExplosion(
                                                        FireworkExplosion.Shape.LARGE_BALL,
                                                        IntList.of(0x9DD6FF), IntList.of(0xFFFFFF),
                                                        false, false))))))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 12.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS,
                                                new Fireworks(2, List.of()))))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 8.0F))))
                        )
                        // 材料物品，固定1抽取，幸运影响系数0.25
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(90))
                        )
                        // 稀有材料，固定1抽取，幸运影响系数1（萦风合金锭为Boss产出的延伸，概率极低）
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(1.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        // 特殊物品，固定1抽取，无幸运修正系数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PAPER_PLANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_VANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_KNIGHT_FLAG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BREAK_WIND_CURTAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(120)))
                        // 附魔书，固定1抽取，无幸运修正系数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 20.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )
        );

        // 风之旅途稀有战利品（原 loots_relic_7）：风岛/风车小屋/失落风骑士遗迹
        consumer.accept(
                ModLootTables.WIND_JOURNEY_CHEST_RARE_LOOT,
                LootTable.lootTable()
                        // 维度特色物品，固定2抽取，幸运影响系数0.2
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.2F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MAGIC_STONE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 植物，固定2抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.HAIRY_MOSS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_CLEAVING_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_FEATHER_GRASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_ISLAND_REED.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                        )
                        // 食物，固定3抽取，幸运影响系数0.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.FORTUNE_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_JELLO.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.FIG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                        )
                        // 建材，固定1抽取，不提供额外抽取次数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.MOSSY_CYAN_STONE_BRICKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_MOOR_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 48.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.THICK_CLOUD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_LANTERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 6.0F))))
                        )
                        // 装备&工具，固定1抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(2.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(6.0F, 16.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS,
                                                new Fireworks(3, List.of(new FireworkExplosion(
                                                        FireworkExplosion.Shape.LARGE_BALL,
                                                        IntList.of(0x9DD6FF, 0xFFFFFF), IntList.of(0x55AAFF),
                                                        false, false))))))
                                .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(6.0F, 16.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS,
                                                new Fireworks(3, List.of()))))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 10.0F))))
                        )
                        // 材料物品，固定1抽取，幸运影响系数0.25
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 6.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND_IRON_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(80))
                        )
                        // 稀有材料，固定1抽取，幸运影响系数1（萦风合金锭为Boss产出的延伸，概率极低）
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(1.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(300))
                        )
                        // 特殊物品，固定1抽取，无幸运修正系数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PAPER_PLANE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_VANE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_KNIGHT_FLAG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BREAK_WIND_CURTAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(70)))
                        // 附魔书，固定1抽取，无幸运修正系数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(5.0F, 25.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(2))
                        )
        );
    }
}
