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
        var flowerPots = tag(BlockTags.FLOWER_POTS);
        for (var potted : ModBlocks.POTTED_PLANTS.values()) {
            flowerPots.add(potted.get());
        }
        tag(ModBlockTags.PLIER_PLANTS)
                .add(ModBlocks.STEM_GRASS.get())
                .add(ModBlocks.TALL_STEM_GRASS.get())
                .add(ModBlocks.DYEDREAM_MOSS.get())
                .add(ModBlocks.SINGULARITY_FERN.get())
                .add(ModBlocks.DYEDREAM_LILY_PAD.get())
                .add(ModBlocks.DYEDREAM_SEAGRASS.get())
                .add(ModBlocks.DYEDREAM_VINE.get())
                .add(ModBlocks.DYEDREAM_LOTUS.get())
                .add(ModBlocks.DREAMING_LOTUS.get())
                .add(ModBlocks.MISTY_DREAMING_LOTUS.get())
                .add(ModBlocks.GOLDENROD.get())
                .add(ModBlocks.FERRARIA_CRISPA.get())
                .add(ModBlocks.EUSTOMA.get())
                .add(ModBlocks.MALVA_SINENSIS_CAVAN.get())
                .add(ModBlocks.LINHT_FLOWER.get())
                .add(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())
                .add(ModBlocks.BLAZE_FLOWER.get())
                .add(ModBlocks.WHITE_ORCHID_FLOWER.get())
                .add(ModBlocks.EDELWEISS.get())
                .add(ModBlocks.NIPPY_EDELWEISS.get())
                .add(ModBlocks.CRIMSON_THORNS.get())
                .add(ModBlocks.REED.get())
                .add(ModBlocks.OATS.get())
                .add(ModBlocks.RYE.get())
                .add(ModBlocks.JUNGLE_SPORANGIUM.get())
                .add(ModBlocks.PINK_MUSHROOM.get())
                .add(ModBlocks.TALL_PINK_MUSHROOM.get())
                .add(ModBlocks.SHADOW_SHORT_ROOTS.get())
                .add(ModBlocks.SHADOW_ROOTS.get())
                .add(ModBlocks.SHADOW_STEM_FERN.get())
                .add(ModBlocks.SHADOW_SPROUTS.get())
                .add(ModBlocks.SHADOW_FERN.get())
                .add(ModBlocks.SHADOW_FUNGUS.get());
        tag(ModBlockTags.WHITE_COROLLA_CAN_PLACE_ON).add(Blocks.SNOW_BLOCK).addTag(BlockTags.DIRT);
        tag(ModBlockTags.CRIMSON_THORNS_CAN_PLACE_ON).add(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, Blocks.MAGMA_BLOCK);
        tag(ModBlockTags.BLAZE_FLOWER_CAN_PLACE_ON).add(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, Blocks.MAGMA_BLOCK);
        tag(ModBlockTags.SHADOW_PLANTS_CAN_PLACE_ON).add(Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.MYCELIUM);
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
                .add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.DYEDREAM_LOG.get())
                .add(ModBlocks.DYEDREAM_WOOD.get())
                .add(ModBlocks.DYEDREAM_PLANKS.get())
                .add(ModBlocks.DYEDREAM_STAIRS.get())
                .add(ModBlocks.DYEDREAM_SLAB.get())
                .add(ModBlocks.DYEDREAM_FENCE.get())
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get())
                .add(ModBlocks.DYEDREAM_PANE.get())
                .add(ModBlocks.DYEDREAM_DOOR.get())
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get())
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get())
                .add(ModBlocks.DYEDREAM_BUTTON.get())
                .add(ModBlocks.DYEDREAM_DESK.get())
                .add(ModBlocks.PINK_MUSHROOM_BLOCK.get())
                .add(ModBlocks.PINK_MUSHROOM_STEM.get())
                .add(ModBlocks.PINK_SHROOMLIGHT.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.DYEDREAM_PLANKS.get());

        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.DYEDREAM_STAIRS.get());

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.DYEDREAM_SLAB.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.DYEDREAM_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.DYEDREAM_FENCE_GATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.DYEDREAM_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.DYEDREAM_TRAPDOOR.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.DYEDREAM_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.DYEDREAM_BUTTON.get());

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
                .add(ModBlocks.AMBER_CANDY_ORE.get())
                .add(ModBlocks.CALCITE_COAL_ORE.get())
                .add(ModBlocks.CALCITE_IRON_ORE.get())
                .add(ModBlocks.CALCITE_COPPER_ORE.get())
                .add(ModBlocks.CALCITE_GOLD_ORE.get())
                .add(ModBlocks.CALCITE_REDSTONE_ORE.get())
                .add(ModBlocks.CALCITE_LAPIS_ORE.get())
                .add(ModBlocks.CALCITE_DIAMOND_ORE.get())
                .add(ModBlocks.CALCITE_EMERALD_ORE.get())
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
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.DREAM_CAULDRON.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.DREAM_ACCUMULATOR.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.SHADOW_BLAST_FURNACE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.CLAYPAN.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.SHADOW_DESK.get())
                .add(ModBlocks.WIND_MOOR_DESK.get())
                .add(ModBlocks.PICNIC_BASKET.get())
                .add(ModBlocks.WIND_MOOR_CRATE.get())
                .add(ModBlocks.DREAM_ACCUMULATOR.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.SHADOW_CHEST.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SOUL_ORE.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.SALT_BLOCK.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.CALCITE_COAL_ORE.get())
                .add(ModBlocks.CALCITE_IRON_ORE.get())
                .add(ModBlocks.CALCITE_COPPER_ORE.get())
                .add(ModBlocks.CALCITE_LAPIS_ORE.get())
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get());

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
                .add(ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .add(ModBlocks.CALCITE_GOLD_ORE.get())
                .add(ModBlocks.CALCITE_REDSTONE_ORE.get())
                .add(ModBlocks.CALCITE_DIAMOND_ORE.get())
                .add(ModBlocks.CALCITE_EMERALD_ORE.get())
                .add(ModBlocks.DYEDREAM_BUD_BRICKS.get())
                .add(ModBlocks.DYEDREAM_BUD_STAIRS.get())
                .add(ModBlocks.DYEDREAM_BUD_SLAB.get())
                .add(ModBlocks.DYEDREAM_BUD_WALL.get());

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
                .addTag(ModBlockTags.ORES_SOUL_DUST)
                .add(ModBlocks.CALCITE_COAL_ORE.get())
                .add(ModBlocks.CALCITE_IRON_ORE.get())
                .add(ModBlocks.CALCITE_COPPER_ORE.get())
                .add(ModBlocks.CALCITE_GOLD_ORE.get())
                .add(ModBlocks.CALCITE_REDSTONE_ORE.get())
                .add(ModBlocks.CALCITE_LAPIS_ORE.get())
                .add(ModBlocks.CALCITE_DIAMOND_ORE.get())
                .add(ModBlocks.CALCITE_EMERALD_ORE.get());

        tag(ModBlockTags.ORES_IN_GROUND_CALCITE)
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.CALCITE_COAL_ORE.get())
                .add(ModBlocks.CALCITE_IRON_ORE.get())
                .add(ModBlocks.CALCITE_COPPER_ORE.get())
                .add(ModBlocks.CALCITE_GOLD_ORE.get())
                .add(ModBlocks.CALCITE_REDSTONE_ORE.get())
                .add(ModBlocks.CALCITE_LAPIS_ORE.get())
                .add(ModBlocks.CALCITE_DIAMOND_ORE.get())
                .add(ModBlocks.CALCITE_EMERALD_ORE.get());

        tag(ModBlockTags.ORES_IN_GROUND_SOUL_SOIL).add(ModBlocks.SOUL_ORE.get());

        tag(ModBlockTags.ORE_RATES_SINGULAR)
                .add(ModBlocks.CALCITE_COAL_ORE.get())
                .add(ModBlocks.CALCITE_IRON_ORE.get())
                .add(ModBlocks.CALCITE_GOLD_ORE.get())
                .add(ModBlocks.CALCITE_DIAMOND_ORE.get())
                .add(ModBlocks.CALCITE_EMERALD_ORE.get());

        tag(ModBlockTags.ORE_RATES_DENSE)
                .add(ModBlocks.SOUL_ORE.get())
                .add(ModBlocks.DYEDREAM_QUARTZ_ORE.get())
                .add(ModBlocks.DYEDREAM_DUST_ORE.get())
                .add(ModBlocks.CALCITE_COPPER_ORE.get())
                .add(ModBlocks.CALCITE_REDSTONE_ORE.get())
                .add(ModBlocks.CALCITE_LAPIS_ORE.get());

        tag(Tags.Blocks.ORES_COAL).add(ModBlocks.CALCITE_COAL_ORE.get());
        tag(Tags.Blocks.ORES_IRON).add(ModBlocks.CALCITE_IRON_ORE.get());
        tag(Tags.Blocks.ORES_COPPER).add(ModBlocks.CALCITE_COPPER_ORE.get());
        tag(Tags.Blocks.ORES_GOLD).add(ModBlocks.CALCITE_GOLD_ORE.get());
        tag(Tags.Blocks.ORES_REDSTONE).add(ModBlocks.CALCITE_REDSTONE_ORE.get());
        tag(Tags.Blocks.ORES_LAPIS).add(ModBlocks.CALCITE_LAPIS_ORE.get());
        tag(Tags.Blocks.ORES_DIAMOND).add(ModBlocks.CALCITE_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORES_EMERALD).add(ModBlocks.CALCITE_EMERALD_ORE.get());

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

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.POLISHED_CALCITE.get())
                .add(ModBlocks.POLISHED_CALCITE_STAIRS.get())
                .add(ModBlocks.POLISHED_CALCITE_SLAB.get())
                .add(ModBlocks.POLISHED_CALCITE_WALL.get())
                .add(ModBlocks.CALCITE_TILES.get())
                .add(ModBlocks.CALCITE_TILES_STAIRS.get())
                .add(ModBlocks.CALCITE_TILES_SLAB.get())
                .add(ModBlocks.CALCITE_TILES_WALL.get())
                .add(ModBlocks.CALCITE_CONE.get())
                .add(ModBlocks.DYEDREAM_SANDSTONE.get())
                .add(ModBlocks.DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.CHISELED_DYEDREAM_SANDSTONE.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.DYEDREAM_BUD_BRICKS.get())
                .add(ModBlocks.DYEDREAM_BUD_STAIRS.get())
                .add(ModBlocks.DYEDREAM_BUD_SLAB.get())
                .add(ModBlocks.DYEDREAM_BUD_WALL.get())
                .add(ModBlocks.DYEDREAM_ICE.get())
                .add(ModBlocks.DYEDREAM_PACKED_ICE.get())
                .add(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get())
                .add(ModBlocks.DYEDREAM_LANTERN.get())
                .add(ModBlocks.ICE_STONE.get())
                .add(ModBlocks.ICE_BUD.get())
                .add(ModBlocks.CHARGED_AMETHYST_BLOCK.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.POLISHED_CALCITE_STAIRS.get())
                .add(ModBlocks.CALCITE_TILES_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.POLISHED_CALCITE_SLAB.get())
                .add(ModBlocks.CALCITE_TILES_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.POLISHED_CALCITE_WALL.get())
                .add(ModBlocks.CALCITE_TILES_WALL.get());
        // ===== 染梦砂岩与水晶建材标签 =====
        tag(BlockTags.STAIRS)
                .add(ModBlocks.DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get())
                .add(ModBlocks.DYEDREAM_BUD_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get())
                .add(ModBlocks.DYEDREAM_BUD_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.CUT_DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_WALL.get())
                .add(ModBlocks.DYEDREAM_BUD_WALL.get());

        // ===== 玻璃标签 =====
        tag(Tags.Blocks.GLASS_BLOCKS)
                .add(ModBlocks.DYEDREAM_GLASS.get())
                .add(ModBlocks.CARVE_DYEDREAM_GLASS.get())
                .add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get());
        tag(Tags.Blocks.GLASS_PANES)
                .add(ModBlocks.DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get())
                .add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get());

        // ===== 琥珀糖矿石与储块标签 =====
        tag(Tags.Blocks.ORES).add(ModBlocks.AMBER_CANDY_ORE.get());
        tag(ModBlockTags.ORES_IN_GROUND_CALCITE).add(ModBlocks.AMBER_CANDY_ORE.get());
        tag(ModBlockTags.ORE_RATES_DENSE).add(ModBlocks.AMBER_CANDY_ORE.get());
        tag(ModBlockTags.ORES_AMBER_CANDY).add(ModBlocks.AMBER_CANDY_ORE.get());
        tag(ModBlockTags.STORAGE_BLOCKS_CHARGED_AMETHYST).add(ModBlocks.CHARGED_AMETHYST_BLOCK.get());
        tag(ModBlockTags.STORAGE_BLOCKS_SALT).add(ModBlocks.SALT_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModBlockTags.STORAGE_BLOCKS_CHARGED_AMETHYST)
                .addTag(ModBlockTags.STORAGE_BLOCKS_SALT);

        // ===== 灯影之下 =====
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.SHADOW_SHROOMLIGHT.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get())
                .add(ModBlocks.SHADOW_PLANKS.get())
                .add(ModBlocks.SHADOW_STAIRS.get())
                .add(ModBlocks.SHADOW_SLAB.get())
                .add(ModBlocks.SHADOW_FENCE.get())
                .add(ModBlocks.SHADOW_FENCE_GATE.get())
                .add(ModBlocks.SHADOW_PANE.get())
                .add(ModBlocks.SHADOW_DOOR.get())
                .add(ModBlocks.SHADOW_TRAPDOOR.get())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get())
                .add(ModBlocks.SHADOW_BUTTON.get())
                .add(ModBlocks.SHADOW_BOOKSHELF.get())
                .add(ModBlocks.WORN_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.KEY_SHADOW_BOOKSHELF.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SHADOW_STONE.get())
                .add(ModBlocks.SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.SHADOW_STONE_TILES.get())
                .add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_TILES_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_TILES_WALL.get())
                .add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHADOW_STONE_CAGE_RUNE.get())
                .add(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get())
                .add(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get())
                .add(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get())
                .add(ModBlocks.SHADOW_DUNGEON_STONE.get())
                .add(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get())
                .add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_ARENA_BLOCK.get())
                .add(ModBlocks.SHADOW_FISSURE_0.get())
                .add(ModBlocks.SHADOW_FISSURE_1.get())
                .add(ModBlocks.SHADOW_FISSURE_2.get())
                .add(ModBlocks.SHADOW_FISSURE_3.get())
                .add(ModBlocks.SHADOW_FISSURE_4.get())
                .add(ModBlocks.SHADOW_FISSURE_5.get())
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_LIGHT.get())
                .add(ModBlocks.SHADOW_CANDLE.get())
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get())
                .add(ModBlocks.BLACK_METAL_BLOCK.get())
                .add(ModBlocks.SHADOW_CLAY_POT.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_LIGHT.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.BLACK_METAL_BLOCK.get());

        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.BLACK_METAL_BLOCK.get());

        tag(ModBlockTags.STORAGE_BLOCKS_BLACK_METAL)
                .add(ModBlocks.BLACK_METAL_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModBlockTags.STORAGE_BLOCKS_BLACK_METAL);

        tag(BlockTags.STAIRS)
                .add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_TILES_SLAB.get())
                .add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get())
                .add(ModBlocks.SHADOW_SLAB.get());

        tag(BlockTags.WALLS)
                .add(ModBlocks.SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.SHADOW_STONE_TILES_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get());

        tag(BlockTags.PLANKS).add(ModBlocks.SHADOW_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.SHADOW_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.SHADOW_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.SHADOW_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.SHADOW_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.SHADOW_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.SHADOW_TRAPDOOR.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.SHADOW_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.SHADOW_BUTTON.get());
        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());

        tag(Tags.Blocks.BOOKSHELVES)
                .add(ModBlocks.SHADOW_BOOKSHELF.get())
                .add(ModBlocks.WORN_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.KEY_SHADOW_BOOKSHELF.get());
        tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(ModBlocks.SHADOW_BOOKSHELF.get());

        tag(ModBlockTags.SHADOW_PLANTS_CAN_PLACE_ON).add(ModBlocks.SHADOW_NYLIUM.get());
        tag(BlockTags.VALID_SPAWN)
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get())
                .add(ModBlocks.SHADOW_STONE.get());

        tag(ModBlockTags.SHADOW_EROSION_TOOL_CAN_BOOST)
                .add(ModBlocks.SHADOW_STONE.get())
                .add(ModBlocks.SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get())
                .add(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get())
                .add(ModBlocks.SHADOW_STONE_TILES.get())
                .add(ModBlocks.SHADOW_STONE_TILES_STAIRS.get())
                .add(ModBlocks.SHADOW_STONE_TILES_SLAB.get())
                .add(ModBlocks.SHADOW_STONE_TILES_WALL.get())
                .add(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get())
                .add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get())
                .add(ModBlocks.SHADOW_NYLIUM.get())
                .add(ModBlocks.SHADOW_WART_BLOCK.get())
                .add(ModBlocks.SHADOW_STEM.get())
                .add(ModBlocks.SHADOW_HYPHAE.get())
                .add(ModBlocks.STRIPPED_SHADOW_STEM.get())
                .add(ModBlocks.STRIPPED_SHADOW_HYPHAE.get())
                .add(ModBlocks.SHADOW_SHROOMLIGHT.get())
                .add(ModBlocks.SHADOW_LIGHT.get())
                .add(ModBlocks.SHADOW_CANDLE.get())
                .add(ModBlocks.SHADOW_PLANKS.get())
                .add(ModBlocks.SHADOW_STAIRS.get())
                .add(ModBlocks.SHADOW_SLAB.get())
                .add(ModBlocks.SHADOW_FENCE.get())
                .add(ModBlocks.SHADOW_FENCE_GATE.get())
                .add(ModBlocks.SHADOW_PANE.get())
                .add(ModBlocks.SHADOW_DOOR.get())
                .add(ModBlocks.SHADOW_TRAPDOOR.get())
                .add(ModBlocks.SHADOW_PRESSURE_PLATE.get())
                .add(ModBlocks.SHADOW_BUTTON.get())
                .add(ModBlocks.SHADOW_BOOKSHELF.get())
                .add(ModBlocks.WORN_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.KEY_SHADOW_BOOKSHELF.get())
                .add(ModBlocks.BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_BLOCK.get())
                .add(ModBlocks.RUST_BLACK_METAL_WALL.get())
                .add(ModBlocks.RUST_BLACK_METAL_BARS.get())
                .add(ModBlocks.SHADOW_CLAY_POT.get())
                .add(ModBlocks.SHADOW_DESK.get())
                .add(ModBlocks.SHADOW_CHEST.get());

        // ===== 风之旅途 =====
        tag(BlockTags.DIRT).add(ModBlocks.CYAN_MOSS_STONE.get());
        tag(BlockTags.SAND).add(ModBlocks.WHITE_SAND.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.WHITE_SAND.get())
                .add(ModBlocks.CONGEAL_WIND_ORE.get());
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.WIND_MOOR_LEAVES_0.get())
                .add(ModBlocks.WIND_MOOR_LEAVES_1.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.WIND_MOOR_LOG.get())
                .add(ModBlocks.WIND_MOOR_WOOD.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get())
                .add(ModBlocks.WIND_MOOR_PLANKS.get())
                .add(ModBlocks.WIND_MOOR_STAIRS.get())
                .add(ModBlocks.WIND_MOOR_SLAB.get())
                .add(ModBlocks.WIND_MOOR_FENCE.get())
                .add(ModBlocks.WIND_MOOR_FENCE_GATE.get())
                .add(ModBlocks.WIND_MOOR_PANE.get())
                .add(ModBlocks.WIND_MOOR_DOOR.get())
                .add(ModBlocks.WIND_MOOR_TRAPDOOR.get())
                .add(ModBlocks.WIND_MOOR_PRESSURE_PLATE.get())
                .add(ModBlocks.WIND_MOOR_BUTTON.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get())
                .add(ModBlocks.CONGEAL_WIND_ORE.get())
                .add(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get())
                .add(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get())
                .add(ModBlocks.EJECTION_PRESSURE_PLATE.get())
                .add(ModBlocks.EJECTION_PRESSURE_BLOCK.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_BARS.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_LANTERN.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_CHAIN.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_TRAPDOOR.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE.get())
                .add(ModBlocks.CONGEAL_WIND_BLOCK.get())
                .add(ModBlocks.CYAN_STONE.get())
                .add(ModBlocks.CYAN_MOSS_STONE.get())
                .add(ModBlocks.CYAN_STONE_BRICKS.get())
                .add(ModBlocks.CYAN_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.CYAN_STONE_BRICK_SLAB.get())
                .add(ModBlocks.CYAN_STONE_BRICK_WALL.get())
                .add(ModBlocks.CYAN_STONE_PRESSURE_PLATE.get())
                .add(ModBlocks.CYAN_STONE_BUTTON.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICKS.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL.get())
                .add(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get())
                .add(ModBlocks.CYAN_STONE_PILLAR.get())
                .add(ModBlocks.POLISHED_CYAN_STONE.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_STAIRS.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_SLAB.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_WALL.get())
                .add(ModBlocks.WHITE_SANDSTONE.get())
                .add(ModBlocks.WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.WHITE_SANDSTONE_WALL.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_WALL.get())
                .add(ModBlocks.CHISELED_WHITE_SANDSTONE.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_WALL.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get())
                .add(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get())
                .add(ModBlocks.EJECTION_PRESSURE_PLATE.get())
                .add(ModBlocks.EJECTION_PRESSURE_BLOCK.get())
                .add(ModBlocks.CONGEAL_WIND_IRON_BARS.get());
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get())
                .add(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get());
        tag(BlockTags.STONE_PRESSURE_PLATES)
                .add(ModBlocks.CYAN_STONE_PRESSURE_PLATE.get())
                .add(ModBlocks.EJECTION_PRESSURE_PLATE.get());
        tag(BlockTags.STONE_BUTTONS).add(ModBlocks.CYAN_STONE_BUTTON.get());

        tag(BlockTags.PLANKS).add(ModBlocks.WIND_MOOR_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.WIND_MOOR_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.WIND_MOOR_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.WIND_MOOR_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.WIND_MOOR_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.WIND_MOOR_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.WIND_MOOR_TRAPDOOR.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.WIND_MOOR_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.WIND_MOOR_BUTTON.get());

        tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)
                .add(ModBlocks.WIND_MOOR_LOG.get())
                .add(ModBlocks.WIND_MOOR_WOOD.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WIND_MOOR_LOG.get())
                .add(ModBlocks.WIND_MOOR_WOOD.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get());
        tag(BlockTags.LOGS)
                .add(ModBlocks.WIND_MOOR_LOG.get())
                .add(ModBlocks.WIND_MOOR_WOOD.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_LOG.get())
                .add(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get());
        tag(BlockTags.LEAVES)
                .add(ModBlocks.WIND_MOOR_LEAVES_0.get())
                .add(ModBlocks.WIND_MOOR_LEAVES_1.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.WIND_MOOR_SAPLING.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS.get())
                .add(ModBlocks.CYAN_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_STAIRS.get())
                .add(ModBlocks.WIND_MOOR_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(ModBlocks.WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB.get())
                .add(ModBlocks.CYAN_STONE_BRICK_SLAB.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_SLAB.get())
                .add(ModBlocks.WIND_MOOR_SLAB.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.WHITE_SANDSTONE_WALL.get())
                .add(ModBlocks.CUT_WHITE_SANDSTONE_WALL.get())
                .add(ModBlocks.SMOOTH_WHITE_SANDSTONE_WALL.get())
                .add(ModBlocks.CYAN_STONE_BRICK_WALL.get())
                .add(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL.get())
                .add(ModBlocks.POLISHED_CYAN_STONE_WALL.get());

        tag(Tags.Blocks.GLASS_BLOCKS)
                .add(ModBlocks.CLARITY_GLASS.get())
                .add(ModBlocks.CARVE_CLARITY_GLASS.get())
                .add(ModBlocks.FRAME_CLARITY_GLASS.get());
        tag(Tags.Blocks.GLASS_PANES)
                .add(ModBlocks.CLARITY_GLASS_PANE.get())
                .add(ModBlocks.CARVE_CLARITY_GLASS_PANE.get())
                .add(ModBlocks.FRAME_CLARITY_GLASS_PANE.get());

        tag(Tags.Blocks.ORES)
                .add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get())
                .add(ModBlocks.CONGEAL_WIND_ORE.get());
        tag(ModBlockTags.ORES_CONGEAL_WIND).add(ModBlocks.CONGEAL_WIND_ORE.get());
        tag(ModBlockTags.ORES_WIND_RUNNER_CRYSTAL).add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get());
        tag(ModBlockTags.ORES_IN_GROUND_CYAN_STONE).add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get());
        tag(ModBlockTags.ORES_IN_GROUND_THICK_CLOUD).add(ModBlocks.CONGEAL_WIND_ORE.get());
        tag(ModBlockTags.ORE_RATES_SINGULAR).add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get());
        tag(ModBlockTags.ORE_RATES_DENSE).add(ModBlocks.CONGEAL_WIND_ORE.get());

        tag(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND_IRON).add(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get());
        tag(ModBlockTags.STORAGE_BLOCKS_FLUFFY_WIND_ALLOY).add(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get());
        tag(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND).add(ModBlocks.CONGEAL_WIND_BLOCK.get());
        tag(ModBlockTags.STORAGE_BLOCKS_WIND_RUNNER_CRYSTAL).add(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND_IRON)
                .addTag(ModBlockTags.STORAGE_BLOCKS_FLUFFY_WIND_ALLOY)
                .addTag(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND)
                .addTag(ModBlockTags.STORAGE_BLOCKS_WIND_RUNNER_CRYSTAL);

        // 矿石/金属块不属于动物自然生成地表，不加入 ANIMALS_SPAWNABLE_ON。
    }
}
