package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import com.pasterdream.pasterdreammod.world.functions.ApplyEntityLootingFunction;
import net.minecraft.data.loot.LootTableSubProvider;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModEntityLootTablesProvider implements LootTableSubProvider {
    private final HolderLookup.Provider lookup;

    public ModEntityLootTablesProvider(HolderLookup.Provider lookup) {
        this.lookup = lookup;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        var looting = lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
        consumer.accept(
                ModEntities.PINK_CHICKEN.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.CHICKEN)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(EmptyLootItem.emptyItem().setWeight(2))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(2)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                                .add(LootItem.lootTableItem(Items.FEATHER)
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
        );

        consumer.accept(
                ModEntities.PINK_SLIME.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.PINK_SLIMEBALL.get())
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                                .add(EmptyLootItem.emptyItem().setWeight(2)))
        );

        consumer.accept(
                ModEntities.TERRORBEAK.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.CRAZY_TERRORBEAK.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.THICK_SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.WEAKENESS_TERRORBEAK.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModBlocks.SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.SHADOW_HAND.get().getDefaultLootTable(),
                LootTable.lootTable()
        );

        consumer.accept(
                ModEntities.SHADOW_GOLEM.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_STONE_TILES.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_LIGHT.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.RUST_BLACK_METAL_GRAIN.get())
                                        .setWeight(1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.SHADOW_GHOST.get().getDefaultLootTable(),
                ghostLoot(looting)
        );

        consumer.accept(
                ModEntities.SHADOW_SQUEAL_GHOST.get().getDefaultLootTable(),
                ghostLoot(looting)
        );

        consumer.accept(
                ModEntities.WAILING_SHADOW_GHOST.get().getDefaultLootTable(),
                ghostLoot(looting)
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.FRIENDLY_SHADOW_GHOST.get().getDefaultLootTable(),
                ghostLoot(looting)
        );

        consumer.accept(
                ModEntities.BLACK_BEETLE_MOTHER.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SHADOW_DUNGEON_KEY.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.BLACK_BEETLE_CARAPACE.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.BLACK_BEETLE_VOCALCORD.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.GOLDEN_FOX.get().getDefaultLootTable(),
                LootTable.lootTable()
        );

        consumer.accept(
                ModEntities.BONE_WING.get().getDefaultLootTable(),
                boneWingLoot(looting)
        );

        consumer.accept(
                ModEntities.ASH_BONE_WING.get().getDefaultLootTable(),
                boneWingLoot(looting)
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.BLAZE_POWDER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        consumer.accept(
                ModEntities.JELLYFISH.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.JELLYFISH_MUD.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1)))
                                .add(LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );

        //雷云
        consumer.accept(
                ModEntities.THUNDERCLOUD.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.DARK_CLOUD.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );
        consumer.accept(
                ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.DARK_CLOUD.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                        .apply(new ApplyEntityLootingFunction.Builder(looting, 1))))
        );
        consumer.accept(
                ModEntities.WIND_KNIGHT.get().getDefaultLootTable(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.FRACTURED_ANGEL_STATUE.get())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(lookup, 0.1F, 0.1F))))
        );
    }

    private LootTable.Builder boneWingLoot(net.minecraft.core.Holder<net.minecraft.world.item.enchantment.Enchantment> looting) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(new ApplyEntityLootingFunction.Builder(looting, 1)))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(new ApplyEntityLootingFunction.Builder(looting, 1))));
    }

    private LootTable.Builder ghostLoot(net.minecraft.core.Holder<net.minecraft.world.item.enchantment.Enchantment> looting) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SOUL_DUST.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(new ApplyEntityLootingFunction.Builder(looting, 1))));
    }
}
