package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    public ModBlockLootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        dropSelf(ModBlocks.RAW_TITANIUM_BLOCK.get());
        dropSelf(ModBlocks.TITANIUM_BLOCK.get());
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), block -> createOreDrop(block, ModItems.RAW_MOLTEN_GOLD.get()));
        dropSelf(ModBlocks.MOLTEN_GOLD_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()).toList();
    }
}
