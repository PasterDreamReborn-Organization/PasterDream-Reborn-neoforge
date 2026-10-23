package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                                ExistingFileHelper existingFileHelper) {
        super(output, lookup, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.TITANIUM_ORE.get(),
                ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), ModBlocks.RAW_TITANIUM_BLOCK.get(), ModBlocks.TITANIUM_BLOCK.get());
        // 来源普通钛矿要求铁镐；深层矿与储存块要求钻石镐，保留该差异。
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.TITANIUM_ORE.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(),
                ModBlocks.RAW_TITANIUM_BLOCK.get(), ModBlocks.TITANIUM_BLOCK.get());
        tag(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.TITANIUM_BLOCK.get());
        tag(ModBlockTags.ORES_TITANIUM).add(ModBlocks.TITANIUM_ORE.get(), ModBlocks.DEEPSLATE_TITANIUM_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModBlockTags.ORES_TITANIUM);
        tag(ModBlockTags.STORAGE_BLOCKS_TITANIUM).add(ModBlocks.TITANIUM_BLOCK.get());
        tag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM).add(ModBlocks.RAW_TITANIUM_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModBlockTags.STORAGE_BLOCKS_TITANIUM)
                .addTag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM);
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.MOLTEN_GOLD_ORE.get(), ModBlocks.MOLTEN_GOLD_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.MOLTEN_GOLD_ORE.get(), ModBlocks.MOLTEN_GOLD_BLOCK.get());
        tag(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        tag(ModBlockTags.ORES_MOLTEN_GOLD).add(ModBlocks.MOLTEN_GOLD_ORE.get());
        tag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD).add(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        tag(Tags.Blocks.ORES).addTag(ModBlockTags.ORES_MOLTEN_GOLD);
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD);
        // 矿石/金属块不属于动物自然生成地表，不加入 ANIMALS_SPAWNABLE_ON。
    }
}
