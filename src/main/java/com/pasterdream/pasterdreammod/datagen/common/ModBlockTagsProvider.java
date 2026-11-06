package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
        // ===== 染梦地形方块 =====
        tag(BlockTags.DIRT)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(BlockTags.FOXES_SPAWNABLE_ON)
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get())
                .add(ModBlocks.DYEDREAM_FARMLAND.get())
                .add(ModBlocks.DYEDREAM_SAND.get());

        tag(BlockTags.SAND)
                .add(ModBlocks.DYEDREAM_SAND.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.DYEDREAM_LEAVES.get())
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get());

        tag(ModBlockTags.DYEDREAM_DIRT)
                .add(ModBlocks.DYEDREAM_DIRT.get())
                .add(ModBlocks.DYEDREAM_GRASS_BLOCK.get());

        tag(ModBlockTags.SHADOW_EROSION_TOOL_CAN_BOOST)
                .add(ModBlocks.SHADOW.get())
                .add(ModBlocks.THICK_SHADOW.get())
                .add(Blocks.DEEPSLATE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.COBBLED_DEEPSLATE_STAIRS)
                .add(Blocks.COBBLED_DEEPSLATE_SLAB)
                .add(Blocks.COBBLED_DEEPSLATE_WALL)
                .add(Blocks.POLISHED_DEEPSLATE)
                .add(Blocks.POLISHED_DEEPSLATE_STAIRS)
                .add(Blocks.POLISHED_DEEPSLATE_SLAB)
                .add(Blocks.POLISHED_DEEPSLATE_WALL)
                .add(Blocks.DEEPSLATE_BRICKS)
                .add(Blocks.DEEPSLATE_BRICK_STAIRS)
                .add(Blocks.DEEPSLATE_BRICK_SLAB)
                .add(Blocks.DEEPSLATE_BRICK_WALL)
                .add(Blocks.DEEPSLATE_TILES)
                .add(Blocks.DEEPSLATE_TILE_STAIRS)
                .add(Blocks.DEEPSLATE_TILE_SLAB)
                .add(Blocks.DEEPSLATE_TILE_WALL)
                .add(Blocks.CHISELED_DEEPSLATE)
                .add(Blocks.CRACKED_DEEPSLATE_BRICKS)
                .add(Blocks.CRACKED_DEEPSLATE_TILES)
                .addTag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(Blocks.SCULK)
                .add(Blocks.SCULK_SENSOR)
                .add(Blocks.CALIBRATED_SCULK_SENSOR)
                .add(Blocks.SCULK_SHRIEKER)
                .add(Blocks.SCULK_CATALYST)
                .add(Blocks.SCULK_VEIN);
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
                .add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get())
                .add(ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.LOST_SWORD_TOMB.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.LIFE_CRYSTAL.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SOUL_ORE.get());

        // 需要铁工具挖掘
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get())
                .add(ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .add(ModBlocks.SMALL_DYEDREAM_BUD.get())
                .add(ModBlocks.MEDIUM_DYEDREAM_BUD.get())
                .add(ModBlocks.LARGE_DYEDREAM_BUD.get())
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_ORE.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        // 需要钻石工具挖掘
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get())
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get());

        // 可作为信标基座
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModBlockTags.STORAGE_BLOCKS_TITANIUM)
                .addTag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM)
                .addTag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD)
                .addTag(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_ALLOY)
                .addTag(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_BUD);

        tag(ModBlockTags.STORAGE_BLOCKS_TITANIUM)
                .add(ModBlocks.TITANIUM_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM)
                .add(ModBlocks.RAW_TITANIUM_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD)
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_ALLOY)
                .add(ModBlocks.DYEDREAM_ALLOY_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_BUD)
                .add(ModBlocks.DYEDREAM_BUD_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .addTag(ModBlockTags.ORES_DYEDREAM_QUARTZ)
                .addTag(ModBlockTags.ORES_DYEDREAM_DUST)
                .addTag(ModBlockTags.ORES_TITANIUM)
                .addTag(ModBlockTags.ORES_MOLTEN_GOLD)
                .addTag(ModBlockTags.ORES_SOUL_DUST);

        tag(ModBlockTags.ORES_IN_GROUND_CALCITE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get());

        tag(ModBlockTags.ORES_IN_GROUND_SOUL_SOIL).add(ModBlocks.SOUL_ORE.get());

        tag(ModBlockTags.ORE_RATES_DENSE)
                .add(ModBlocks.SOUL_ORE.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get());

        tag(ModBlockTags.ORES_TITANIUM)
                .add(ModBlocks.TITANIUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get());

        tag(ModBlockTags.ORES_MOLTEN_GOLD)
                .add(ModBlocks.MOLTEN_GOLD_ORE.get());

        tag(ModBlockTags.ORES_SOUL_DUST).add(ModBlocks.SOUL_ORE.get());

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
