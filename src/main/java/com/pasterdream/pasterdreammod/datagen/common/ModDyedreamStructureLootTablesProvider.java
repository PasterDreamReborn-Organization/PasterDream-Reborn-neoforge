package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModDyedreamStructureLootTablesProvider implements LootTableSubProvider {
    private final HolderLookup.Provider registries;

    public ModDyedreamStructureLootTablesProvider(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(
                ModLootTables.PICNIC_BASKET_DYEDREAM_WORLD_LOOT,
                LootTable.lootTable()
                        // 幸运影响系数0.15，移除原版的2-4次抽取，改为固定4次抽取
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(4.0F))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_DREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MARTINI_GLASS_OF_SEA_SALT_SLUMBER_PALM_BEVERAGE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SLUMBER_PALM.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.POPPING_CANDY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_HONEY_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CREAM_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
        );
        consumer.accept(
                ModLootTables.DYEDREAM_RELIC_CHEST_LOOT_COMMON,//原loots_relic_0
                LootTable.lootTable()
                        // 染梦物品，固定2抽取，幸运影响系数0.15
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIME_BLOCK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.GALAXY_JELLY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(5))
                        )
                        // 植物，固定2抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SINGULARITY_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LINHT_FLOWER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_MUSHROOM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 食材&食物，固定3抽取，幸运影响系数0.15
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.DOUGH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLOUR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.DOUGH_WITH_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(ModItems.CAKE_BASE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.SUGAR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FRIED_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ODD_BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RICE_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_YEAST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 建材，固定1抽取，不提供额外抽取次数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.POLISHED_CALCITE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALCITE_TILES.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_GLASS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARVE_DYEDREAM_GLASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAND.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 32.0F))))

                        )
                        .withPool(LootPool.lootPool()
                                // 装备&工具，固定1抽取，不提供额外抽取次数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PALE_BONENEEDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MORTAR.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PLIERS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SORBENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DYE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(2.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_SHOVEL)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_AXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_HOE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.SHEARS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                        )
                        .withPool(LootPool.lootPool()
                                // 材料物品，固定1抽取，幸运影响系数0.25
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BROKEN_NOTE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(90))
                        )
                        .withPool(LootPool.lootPool()
                                // 染梦装备
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HELMET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_CHESTPLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LEGGINGS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BOOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_UPGRADE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        .withPool(LootPool.lootPool()
                                // 饰品，固定1抽取，无幸运修正系数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }}))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(30)))
                        .withPool(LootPool.lootPool()
                                // 附魔书，固定1抽取，无幸运修正系数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                ))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )
        );
        consumer.accept(
                ModLootTables.DYEDREAM_RELIC_CHEST_LOOT_RARE,//原loots_relic_1
                LootTable.lootTable()
                        // 染梦物品，固定1抽取，幸运影响系数0.2
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.2F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                                        .setWeight(4)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIME_BLOCK.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.MELT_DREAM_COIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GALAXY_JELLY.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 3.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(9))
                        )
                        // 植物，固定2抽取，幸运影响系数0.1
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAPLING.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.CLOUD_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.SINGULARITY_FERN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.LINHT_FLOWER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_MUSHROOM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 食材&食物，固定3抽取，幸运影响系数0.15
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(3.0F))
                                .add(LootItem.lootTableItem(ModItems.DOUGH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.FLOUR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.DOUGH_WITH_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CAKE_BASE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 4.0F))))
                                .add(LootItem.lootTableItem(Items.SUGAR)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FRIED_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.ODD_BACON_AND_EGG.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RICE_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.BUBBLE_GUM.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_YEAST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SANDWICH.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )

                        .withPool(LootPool.lootPool()
                                // 建材，固定1抽取，不提供额外抽取次数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.POLISHED_CALCITE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CALCITE_TILES.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_GLASS.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.CARVE_DYEDREAM_GLASS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 16.0F))))
                                .add(LootItem.lootTableItem(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LOG.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PLANKS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SAND.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 24.0F))))

                        )
                        .withPool(LootPool.lootPool()
                                // 装备&工具，固定1抽取，幸运修正系数0.1
                                .setBonusRolls(ConstantValue.exactly(0.1F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PALE_BONENEEDLE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.MORTAR.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SORBENT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DYE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(2.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_PICKAXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_SHOVEL)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_AXE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_HOE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.SHEARS)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.IRON_HELMET)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.IRON_BOOTS)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 4.0F))
                                                ))
                        )
                        .withPool(LootPool.lootPool()
                                // 材料物品，固定1抽取，幸运影响系数0.25
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DUST.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_ALLOY_NUGGET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_NUGGET.get())
                                        .setWeight(5)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(3.0F, 5.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_INGOT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BROKEN_NOTE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(80))
                        )
                        .withPool(LootPool.lootPool()
                                // 染梦装备
                                .setBonusRolls(ConstantValue.exactly(0.15F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_UPGRADE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SWORD.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_PICKAXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_AXE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_SHOVEL.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_HELMET.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_CHESTPLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_LEGGINGS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_BOOTS.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.TITANIUM_UPGRADE.get())
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(400))
                        )
                        .withPool(LootPool.lootPool()
                                // 饰品，固定1抽取，无幸运修正系数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_NECKLACE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.EMBRYO_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.SOUL_ESSENCE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.RED_DEW_RING.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F)))
                                        .apply(SetComponentsFunction.setComponent(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag(){ {
                                            putInt("lv", 1);
                                        }}))))
                                .add(LootItem.lootTableItem(ModItems.TRAVELER_BELT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(50)))
                        .withPool(LootPool.lootPool()
                                // 附魔书，固定1抽取，无幸运修正系数
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                ))
                                .add(EmptyLootItem.emptyItem()
                                        .setWeight(3))
                        )

        );
        consumer.accept(
                ModLootTables.ECOSYSTEM_BUBBLE_CHEST_LOOT,
                LootTable.lootTable()
                        // 水产，改为固定2次抽取，不受幸运影响
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(Items.COD)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 6.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SEAGRASS)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(2.0F, 7.0F))))
                                .add(LootItem.lootTableItem(Items.KELP)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 5.0F))))
                                .add(LootItem.lootTableItem(Items.SEA_PICKLE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                        )
                        //食材，抽取1次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.GLASS_JAR_OF_WATER.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.WATER_BUCKET)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.COARSE_SALT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(4.0F, 8.0F))))
                        )
                        //杂物，抽取1次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.INK_SAC)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.STRING)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.TURTLE_SCUTE)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.FISHING_ROD)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(10.0F, 20.0F))
                                                ))
                                .add(LootItem.lootTableItem(Items.NAME_TAG)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.FABRIC.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .setWeight(1)
                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(registries, 
                                                        UniformGenerator.between(1.0F, 20.0F))
                                                ))
                        )
                        //真活鱼真好吃，抽取1次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.WAFER_BISCUIT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.PINK_HEART_CHOCOLATE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.DRIED_KELP)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 3.0F))))
                                .add(LootItem.lootTableItem(Items.BREAD)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
                        //食物，抽取1次，无幸运修正
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.0F))
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.COD_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.SALMON_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.PUFFERFISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.TROPICAL_FISH_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                                .add(LootItem.lootTableItem(Items.AXOLOTL_BUCKET)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(
                                                ConstantValue.exactly(1.0F))))
                        )
                        //染梦钓鱼宝匣，抽取1次，0.25幸运修正系数
                        .withPool(LootPool.lootPool()
                                .setBonusRolls(ConstantValue.exactly(0.25F))
                                .setRolls(ConstantValue.exactly(2.0F))
                                .add(LootItem.lootTableItem(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get())
                                        .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(1.0F, 2.0F))))
                        )
        );

        //测试战利品列表0
    }
}
