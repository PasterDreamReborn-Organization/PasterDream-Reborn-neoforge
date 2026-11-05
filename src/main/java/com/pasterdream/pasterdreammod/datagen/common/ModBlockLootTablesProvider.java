package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());
        dropSelf(ModBlocks.PINK_SLIME_BLOCK.get());
        add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
        dropSelf(ModBlocks.DYEDREAM_ALLOY_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_BUD_BLOCK.get());
        add(ModBlocks.TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        dropSelf(ModBlocks.RAW_TITANIUM_BLOCK.get());
        dropSelf(ModBlocks.TITANIUM_BLOCK.get());
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), block -> createOreDrop(block, ModItems.RAW_MOLTEN_GOLD.get()));
        dropSelf(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        dropSelf(ModBlocks.LOST_SWORD_TOMB.get());
        dropSelf(ModBlocks.LIFE_CRYSTAL.get());
        dropSelf(ModBlocks.SHADOW.get());
        dropSelf(ModBlocks.THICK_SHADOW.get());
        dropSelf(ModBlocks.QYM_DOLL.get());
        dropSelf(ModBlocks.UUZ_DOLL.get());
        dropSelf(ModBlocks.DESERT_HERO_TOMB.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()).toList();
    }
}
