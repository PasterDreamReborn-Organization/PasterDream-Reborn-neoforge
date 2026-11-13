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

        // 圣诞树岛战利品（原 loots_relic_8）
        consumer.accept(
                ModLootTables.CHRISTMAS_TREE_CHEST_LOOT,
                LootTable.lootTable()
                        // 甜食&食物，抽取 3~5 次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(UniformGenerator.between(3.0F, 5.0F))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_MILK.get())
                                        .setWeight(7)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CREAM_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.BERRY_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.TUBER_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WATERMELON_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PUMPKIN_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLOW_BERRY_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_APPLE_JUICE.get())
                                        .setWeight(10)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.GARLAND.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.LUCK_ENHANCE_STONE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.WAFER_BISCUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.STUFFED_WAFER_COOKIES.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SWISS_ROLL.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.WHITE_HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.CANDY_CANE.get())
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.GINGERBREAD_MAN.get())
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.APPLE)
                                        .setWeight(20)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.CAKE)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.COOKED_CHICKEN)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.SWEET_BERRIES)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.HONEY_BOTTLE)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
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
                                .add(LootItem.lootTableItem(ModItems.WIND_RUNNER_JELLY.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 5.0F)))))
                        // 装饰&彩灯，抽取 2~3 次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                .add(LootItem.lootTableItem(Items.SPRUCE_LEAVES)
                                        .setWeight(8)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(16.0F, 32.0F))))
                                .add(LootItem.lootTableItem(Items.YELLOW_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.RED_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.BLUE_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.WHITE_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(Items.PINK_STAINED_GLASS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHRISTMAS_LIGHTS.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(5.0F, 9.0F)))))
                        // 稀有掉落，抽取 1 次（原作 tabitem_1 占位权重 100 的空条目），无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.SNOW_VOW_HEAD.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(100)))
                        // 雪景物品，抽取 1~2 次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                .add(LootItem.lootTableItem(Items.SNOWBALL)
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.POWDER_SNOW_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SNOW_BLOCK)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.ICE)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EDELWEISS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                registries, UniformGenerator.between(1.0F, 30.0F)))))
                        // 宝石，抽取 1 次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.DIAMOND)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.AMETHYST_SHARD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.SPRUCE_LEAVES)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(8.0F, 16.0F)))))
        );
    }
}
