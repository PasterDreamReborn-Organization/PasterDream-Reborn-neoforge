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
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // 可以用镐来挖掘
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        // 需要铁工具挖掘
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        // 需要钻石工具挖掘
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get());

        // 可作为信标基座
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModBlockTags.STORAGE_BLOCKS_TITANIUM)
                .addTag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM)
                .addTag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD);

        tag(ModBlockTags.STORAGE_BLOCKS_TITANIUM)
                .add(ModBlocks.TITANIUM_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM)
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD)
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .addTag(ModBlockTags.ORES_DYEDREAM_QUARTZ)
                .addTag(ModBlockTags.ORES_DYEDREAM_DUST)
                .addTag(ModBlockTags.ORES_TITANIUM)
                .addTag(ModBlockTags.ORES_MOLTEN_GOLD);

        tag(ModBlockTags.ORES_IN_GROUND_CALCITE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get());

        tag(ModBlockTags.ORE_RATES_DENSE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get());

        tag(ModBlockTags.ORES_TITANIUM)
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get());

        tag(ModBlockTags.ORES_MOLTEN_GOLD)
                .add(ModBlocks.MOLTEN_GOLD_ORE.get());

        tag(ModBlockTags.ORES_DYEDREAM_QUARTZ)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get());

        tag(ModBlockTags.ORES_DYEDREAM_DUST)
                .add(ModBlocks.DYEDREAM_DUST_ORE.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());
        // 矿石/金属块不属于动物自然生成地表，不加入 ANIMALS_SPAWNABLE_ON。
    }
}
