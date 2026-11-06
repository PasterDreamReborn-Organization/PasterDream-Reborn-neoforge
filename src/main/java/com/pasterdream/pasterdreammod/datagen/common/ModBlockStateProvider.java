package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.CalciteConeBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // ===== 染梦地形方块 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_DIRT.get(), cubeAll(ModBlocks.DYEDREAM_DIRT.get()));

        farmlandBlock(ModBlocks.DYEDREAM_FARMLAND.get());

        var grassBlock = models().cubeBottomTop(
                ModBlocks.DYEDREAM_GRASS_BLOCK.getId().getPath(),
                modLoc("block/dyedream_grass_block_side"),
                modLoc("block/dyedream_dirt"),
                modLoc("block/dyedream_grass_block_top"));
        var snowyGrassBlock = models().cubeBottomTop(
                ModBlocks.DYEDREAM_GRASS_BLOCK.getId().getPath() + "_snow",
                modLoc("block/dyedream_grass_block_snow"),
                modLoc("block/dyedream_dirt"),
                mcLoc("block/snow"));
        getVariantBuilder(ModBlocks.DYEDREAM_GRASS_BLOCK.get())
                .partialState().with(SnowyDirtBlock.SNOWY, false).modelForState().modelFile(grassBlock).addModel()
                .partialState().with(SnowyDirtBlock.SNOWY, true).modelForState().modelFile(snowyGrassBlock).addModel();
        simpleBlockItem(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), grassBlock);

        logBlock((RotatedPillarBlock) ModBlocks.DYEDREAM_LOG.get());
        blockItem(ModBlocks.DYEDREAM_LOG);
        var logTex = blockTexture(ModBlocks.DYEDREAM_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.DYEDREAM_WOOD.get(), logTex, logTex);
        blockItem(ModBlocks.DYEDREAM_WOOD);
        simpleBlockWithItem(ModBlocks.DYEDREAM_LEAVES.get(), cubeAll(ModBlocks.DYEDREAM_LEAVES.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), cubeAll(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_SAND.get(), cubeAll(ModBlocks.DYEDREAM_SAND.get()));

        simpleBlockWithItem(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), cubeAll(ModBlocks.DYEDREAM_QUARTZ_ORE.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_DUST_ORE.get(), cubeAll(ModBlocks.DYEDREAM_DUST_ORE.get()));
        simpleBlockWithItem(ModBlocks.AMBER_CANDY_ORE.get(), cubeAll(ModBlocks.AMBER_CANDY_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_COAL_ORE.get(), cubeAll(ModBlocks.CALCITE_COAL_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_IRON_ORE.get(), cubeAll(ModBlocks.CALCITE_IRON_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_COPPER_ORE.get(), cubeAll(ModBlocks.CALCITE_COPPER_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_GOLD_ORE.get(), cubeAll(ModBlocks.CALCITE_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_REDSTONE_ORE.get(), cubeAll(ModBlocks.CALCITE_REDSTONE_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_LAPIS_ORE.get(), cubeAll(ModBlocks.CALCITE_LAPIS_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_DIAMOND_ORE.get(), cubeAll(ModBlocks.CALCITE_DIAMOND_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_EMERALD_ORE.get(), cubeAll(ModBlocks.CALCITE_EMERALD_ORE.get()));
        simpleBlockWithItem(ModBlocks.PINK_SLIME_BLOCK.get(), models().cubeAll(ModBlocks.PINK_SLIME_BLOCK.getId().getPath(), blockTexture(ModBlocks.PINK_SLIME_BLOCK.get())).renderType("minecraft:translucent"));

        // ===== 粉顶菌系列 =====
        var capTex = modLoc("block/pink_mushroom_block_cap");
        var poreTex = modLoc("block/pink_mushroom_block_pores");
        var stemTex = modLoc("block/pink_mushroom_stem");
        var stemTopTex = modLoc("block/pink_mushroom_stem_top");

        var capFaceModel = models().withExistingParent(ModBlocks.PINK_MUSHROOM_BLOCK.getId().getPath(),
                mcLoc("block/template_single_face")).texture("texture", capTex);
        var poreFaceModel = models().withExistingParent(ModBlocks.PINK_MUSHROOM_BLOCK.getId().getPath() + "_inside",
                mcLoc("block/template_single_face")).texture("texture", poreTex);
        var capInventoryModel = models().cubeAll(ModBlocks.PINK_MUSHROOM_BLOCK.getId().getPath() + "_inventory", capTex);

        var mushroomBlockBuilder = getMultipartBuilder(ModBlocks.PINK_MUSHROOM_BLOCK.get());
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true)
                .addModel().condition(HugeMushroomBlock.NORTH, true);
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true).rotationY(90)
                .addModel().condition(HugeMushroomBlock.EAST, true);
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true).rotationY(180)
                .addModel().condition(HugeMushroomBlock.SOUTH, true);
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true).rotationY(270)
                .addModel().condition(HugeMushroomBlock.WEST, true);
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true).rotationX(270)
                .addModel().condition(HugeMushroomBlock.UP, true);
        mushroomBlockBuilder.part().modelFile(capFaceModel).uvLock(true).rotationX(90)
                .addModel().condition(HugeMushroomBlock.DOWN, true);
        mushroomBlockBuilder.part().modelFile(poreFaceModel)
                .addModel().condition(HugeMushroomBlock.NORTH, false);
        mushroomBlockBuilder.part().modelFile(poreFaceModel).rotationY(90)
                .addModel().condition(HugeMushroomBlock.EAST, false);
        mushroomBlockBuilder.part().modelFile(poreFaceModel).rotationY(180)
                .addModel().condition(HugeMushroomBlock.SOUTH, false);
        mushroomBlockBuilder.part().modelFile(poreFaceModel).rotationY(270)
                .addModel().condition(HugeMushroomBlock.WEST, false);
        mushroomBlockBuilder.part().modelFile(poreFaceModel).rotationX(270)
                .addModel().condition(HugeMushroomBlock.UP, false);
        mushroomBlockBuilder.part().modelFile(poreFaceModel).rotationX(90)
                .addModel().condition(HugeMushroomBlock.DOWN, false);
        simpleBlockItem(ModBlocks.PINK_MUSHROOM_BLOCK.get(), capInventoryModel);

        axisBlock((RotatedPillarBlock) ModBlocks.PINK_MUSHROOM_STEM.get(), stemTex, stemTopTex);
        blockItem(ModBlocks.PINK_MUSHROOM_STEM);

        simpleBlockWithItem(ModBlocks.PINK_SHROOMLIGHT.get(), cubeAll(ModBlocks.PINK_SHROOMLIGHT.get()));
        // ===== 染梦石英建材系列 =====
        var quartzTex = blockTexture(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        simpleBlockWithItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), cubeAll(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get()));

        logBlock((RotatedPillarBlock) ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        blockItem(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK);

        var chiseledQuartz = models().cubeBottomTop(
                ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.getId().getPath(),
                modLoc("block/chiseled_dyedream_quartz_block"),
                modLoc("block/chiseled_dyedream_quartz_block_top"),
                modLoc("block/chiseled_dyedream_quartz_block_top"));
        simpleBlockWithItem(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), chiseledQuartz);

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), quartzTex);
        blockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), quartzTex, quartzTex);
        blockItem(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB);

        wallBlock((WallBlock) ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), quartzTex);
        // wall inventory model handled in ModItemModelsProvider

        // ===== 染梦砂岩建材系列 =====
        var sandstoneSide = modLoc("block/dyedream_sandstone");
        var sandstoneTop = modLoc("block/dyedream_sandstone_top");
        var sandstoneBottom = modLoc("block/dyedream_sandstone_bottom");

        var sandstoneModel = models().cubeBottomTop(
                ModBlocks.DYEDREAM_SANDSTONE.getId().getPath(),
                sandstoneSide,
                sandstoneBottom,
                sandstoneTop);
        simpleBlockWithItem(ModBlocks.DYEDREAM_SANDSTONE.get(), sandstoneModel);

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_SANDSTONE_STAIRS.get(), sandstoneSide, sandstoneBottom, sandstoneTop);
        blockItem(ModBlocks.DYEDREAM_SANDSTONE_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_SANDSTONE_SLAB.get(), sandstoneSide, sandstoneSide, sandstoneBottom, sandstoneTop);
        blockItem(ModBlocks.DYEDREAM_SANDSTONE_SLAB);

        wallBlock((WallBlock) ModBlocks.DYEDREAM_SANDSTONE_WALL.get(), sandstoneSide);

        var cutSandstoneTex = modLoc("block/cut_dyedream_sandstone");
        var cutSandstoneModel = models().cubeColumn(
                ModBlocks.CUT_DYEDREAM_SANDSTONE.getId().getPath(),
                cutSandstoneTex,
                sandstoneTop);
        simpleBlockWithItem(ModBlocks.CUT_DYEDREAM_SANDSTONE.get(), cutSandstoneModel);

        stairsBlock((StairBlock) ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS.get(), cutSandstoneTex);
        blockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS);

        slabBlock((SlabBlock) ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB.get(), cutSandstoneTex, cutSandstoneTex, cutSandstoneTex, cutSandstoneTex);
        blockItem(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB);

        wallBlock((WallBlock) ModBlocks.CUT_DYEDREAM_SANDSTONE_WALL.get(), cutSandstoneTex);

        var chiseledSandstoneTex = modLoc("block/chiseled_dyedream_sandstone");
        var chiseledSandstoneModel = models().cubeColumn(
                ModBlocks.CHISELED_DYEDREAM_SANDSTONE.getId().getPath(),
                chiseledSandstoneTex,
                sandstoneTop);
        simpleBlockWithItem(ModBlocks.CHISELED_DYEDREAM_SANDSTONE.get(), chiseledSandstoneModel);

        var smoothSandstoneModel = models().cubeAll(
                ModBlocks.SMOOTH_DYEDREAM_SANDSTONE.getId().getPath(),
                sandstoneTop);
        simpleBlockWithItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE.get(), smoothSandstoneModel);

        stairsBlock((StairBlock) ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get(), sandstoneTop);
        blockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS);

        slabBlock((SlabBlock) ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get(), modLoc("block/smooth_dyedream_sandstone"), sandstoneTop, sandstoneTop, sandstoneTop);
        blockItem(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB);

        wallBlock((WallBlock) ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_WALL.get(), sandstoneTop);

        // ===== 染梦木板建材系列 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_PLANKS.get(), cubeAll(ModBlocks.DYEDREAM_PLANKS.get()));

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_STAIRS.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_SLAB.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_SLAB);

        fenceBlock((FenceBlock) ModBlocks.DYEDREAM_FENCE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        fenceGateBlock((FenceGateBlock) ModBlocks.DYEDREAM_FENCE_GATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_FENCE_GATE);

        paneBlock((IronBarsBlock) ModBlocks.DYEDREAM_PANE.get(), modLoc("block/dyedream_trapdoor"), modLoc("block/dyedream_trapdoor"));

        doorBlockWithRenderType((DoorBlock) ModBlocks.DYEDREAM_DOOR.get(),
                modLoc("block/dyedream_door_bottom"),
                modLoc("block/dyedream_door_top"),
                "cutout");

        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DYEDREAM_TRAPDOOR.get(),
                modLoc("block/dyedream_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.DYEDREAM_TRAPDOOR, "_bottom");

        pressurePlateBlock((PressurePlateBlock) ModBlocks.DYEDREAM_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_PRESSURE_PLATE);

        buttonBlock((ButtonBlock) ModBlocks.DYEDREAM_BUTTON.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        // ===== 染梦玻璃系列 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_GLASS.get())).renderType("translucent"));
        var glassTex = modLoc("block/dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.DYEDREAM_GLASS_PANE.get(), glassTex, glassTex, "translucent");

        simpleBlockWithItem(ModBlocks.CARVE_DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.CARVE_DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.CARVE_DYEDREAM_GLASS.get())).renderType("translucent"));
        var carveTex = modLoc("block/carve_dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), carveTex, carveTex, "translucent");

        simpleBlockWithItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get())).renderType("translucent"));
        var goldCarveTex = modLoc("block/gold_carve_dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), goldCarveTex, goldCarveTex, "translucent");

        // ===== 染梦晶芽与合金块 =====
        var buddingModel = models().cubeAll(ModBlocks.DYEDREAM_BUDDING_BLOCK.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_BUDDING_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), buddingModel);

        var budBlockModel = models().cubeAll(ModBlocks.DYEDREAM_BUD_BLOCK.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_BUD_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_BUD_BLOCK.get(), budBlockModel);

        var smallBud = models().cross(ModBlocks.SMALL_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.SMALL_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.SMALL_DYEDREAM_BUD.get(), smallBud);

        var mediumBud = models().cross(ModBlocks.MEDIUM_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.MEDIUM_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.MEDIUM_DYEDREAM_BUD.get(), mediumBud);

        var largeBud = models().cross(ModBlocks.LARGE_DYEDREAM_BUD.getId().getPath(), blockTexture(ModBlocks.LARGE_DYEDREAM_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.LARGE_DYEDREAM_BUD.get(), largeBud);

        simpleBlockWithItem(ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), cubeAll(ModBlocks.DYEDREAM_ALLOY_BLOCK.get()));

        // ===== 染梦水晶建材系列 =====
        simpleBuildingFamily(ModBlocks.DYEDREAM_BUD_BRICKS, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL, "translucent");

        // ===== 染梦冰与云 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_ICE.get(), models().cubeAll(ModBlocks.DYEDREAM_ICE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_ICE.get())).renderType("translucent"));
        simpleBlockWithItem(ModBlocks.DYEDREAM_PACKED_ICE.get(), models().cubeAll(ModBlocks.DYEDREAM_PACKED_ICE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_PACKED_ICE.get())).renderType("translucent"));
        var bubbleModel = models().cubeAll(ModBlocks.BIG_BUBBLE.getId().getPath(), modLoc("block/big_bubble")).renderType("translucent");
        simpleBlockWithItem(ModBlocks.BIG_BUBBLE.get(), bubbleModel);
        var cloudModel = models().cubeAll(ModBlocks.CLOUD.getId().getPath(), blockTexture(ModBlocks.CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.CLOUD.get(), cloudModel);
        var darkCloudModel = models().cubeAll(ModBlocks.DARK_CLOUD.getId().getPath(), blockTexture(ModBlocks.DARK_CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.DARK_CLOUD.get(), darkCloudModel);

        // ===== 染梦灯 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), models().cubeAll(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get())).renderType("translucent"));
        var lanternStanding = models().withExistingParent("dyedream_lantern", mcLoc("block/template_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        var lanternHanging = models().withExistingParent("dyedream_lantern_hanging", mcLoc("block/template_hanging_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        getVariantBuilder(ModBlocks.DYEDREAM_LANTERN.get())
                .partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lanternStanding).addModel()
                .partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(lanternHanging).addModel();
        simpleBlockItem(ModBlocks.DYEDREAM_LANTERN.get(), lanternStanding);

        // ===== 冰晶岩与冰凌晶芽 =====
        simpleBlockWithItem(ModBlocks.ICE_STONE.get(), cubeAll(ModBlocks.ICE_STONE.get()));
        var iceBud = models().cross(ModBlocks.ICE_BUD.getId().getPath(), blockTexture(ModBlocks.ICE_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.ICE_BUD.get(), iceBud);

        // ===== 方解石系列 =====
        simpleBuildingFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL);
        simpleBuildingFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL);

        // ===== 方解石锥 =====
        var calciteConeBase = models().getExistingFile(modLoc("block/calcite_cone_base"));
        var calciteConeMiddle = models().getExistingFile(modLoc("block/calcite_cone_middle"));
        var calciteConeFrustum = models().getExistingFile(modLoc("block/calcite_cone_frustum"));
        var calciteConeTip = models().getExistingFile(modLoc("block/calcite_cone_tip"));
        var calciteConeTipMerge = models().getExistingFile(modLoc("block/calcite_cone_tip_merge"));
        getVariantBuilder(ModBlocks.CALCITE_CONE.get()).forAllStates(state -> {
            DripstoneThickness thickness = state.getValue(CalciteConeBlock.THICKNESS);
            ModelFile model = switch (thickness) {
                case BASE -> calciteConeBase;
                case MIDDLE -> calciteConeMiddle;
                case FRUSTUM -> calciteConeFrustum;
                case TIP -> calciteConeTip;
                case TIP_MERGE -> calciteConeTipMerge;
            };
            if (thickness == DripstoneThickness.TIP && state.getValue(CalciteConeBlock.TIP_DIRECTION) == Direction.DOWN) {
                return ConfiguredModel.builder().modelFile(calciteConeTip).rotationX(180).build();
            }
            return ConfiguredModel.builder().modelFile(model).build();
        });

        simpleBlockWithItem(ModBlocks.TITANIUM_ORE.get(), cubeAll(ModBlocks.TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.RAW_TITANIUM_BLOCK.get(), cubeAll(ModBlocks.RAW_TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_ORE.get(), cubeAll(ModBlocks.MOLTEN_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.SOUL_ORE.get(), cubeAll(ModBlocks.SOUL_ORE.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_BLOCK.get(), cubeAll(ModBlocks.MOLTEN_GOLD_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SALT_BLOCK.get(), cubeAll(ModBlocks.SALT_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.CHARGED_AMETHYST_BLOCK.get(), models().cubeAll(ModBlocks.CHARGED_AMETHYST_BLOCK.getId().getPath(), blockTexture(ModBlocks.CHARGED_AMETHYST_BLOCK.get())).renderType("translucent"));

        // ===== 失落剑冢 =====
        var lostSwordTomb = models().getBuilder(ModBlocks.LOST_SWORD_TOMB.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc("custom/lost_sword_tomb")))
                .texture("all", modLoc("block/lost_sword"))
                .texture("particle", mcLoc("block/mossy_cobblestone"))
                .texture("1", modLoc("block/lost_sword"))
                .texture("2", modLoc("block/lost_sword"))
                .renderType("cutout");
        horizontalBlock(ModBlocks.LOST_SWORD_TOMB.get(), lostSwordTomb);
        horizontalBlock(ModBlocks.LIFE_CRYSTAL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/life_crystal_particle")));
        blockItem(ModBlocks.LOST_SWORD_TOMB);

        var shadowModel = models().cubeAll(ModBlocks.SHADOW.getId().getPath(), blockTexture(ModBlocks.SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.SHADOW.get(), shadowModel);
        var thickShadowModel = models().cubeAll(ModBlocks.THICK_SHADOW.getId().getPath(), blockTexture(ModBlocks.THICK_SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.THICK_SHADOW.get(), thickShadowModel);

        // ===== 荒漠英雄之墓 =====
        horizontalBlock(ModBlocks.QYM_DOLL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/qym_doll_particle")));
        horizontalBlock(ModBlocks.UUZ_DOLL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/uuz_doll_particle")));
        horizontalBlock(ModBlocks.DESERT_HERO_TOMB.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/desert_hero_tomb_particle")));
    }

    private void farmlandBlock(Block block) {
        String baseName = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
        ModelFile dry = models().getExistingFile(modLoc("block/" + baseName));
        ModelFile moist = models().getExistingFile(modLoc("block/" + baseName + "_moist"));
        getVariantBuilder(block).forAllStates(state -> {
            int moisture = state.getValue(FarmBlock.MOISTURE);
            return ConfiguredModel.builder().modelFile(moisture == 7 ? moist : dry).build();
        });
        simpleBlockItem(block, dry);
    }

    private void blockItem(net.neoforged.neoforge.registries.DeferredBlock<?> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath()));
    }

    private void blockItem(net.neoforged.neoforge.registries.DeferredBlock<?> block, String suffix) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath() + suffix));
    }

    private void simpleBuildingFamily(net.neoforged.neoforge.registries.DeferredBlock<?> base,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> stairs,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> slab,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> wall) {
        var tex = blockTexture(base.get());
        simpleBlockWithItem(base.get(), cubeAll(base.get()));
        stairsBlock((StairBlock) stairs.get(), tex);
        blockItem(stairs);
        slabBlock((SlabBlock) slab.get(), tex, tex);
        blockItem(slab);
        wallBlock((WallBlock) wall.get(), tex);
    }

    private void simpleBuildingFamily(net.neoforged.neoforge.registries.DeferredBlock<?> base,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> stairs,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> slab,
                                      net.neoforged.neoforge.registries.DeferredBlock<?> wall,
                                      String renderType) {
        var tex = blockTexture(base.get());
        var baseModel = models().cubeAll(base.getId().getPath(), tex).renderType(renderType);
        simpleBlockWithItem(base.get(), baseModel);
        stairsBlockWithRenderType((StairBlock) stairs.get(), tex, renderType);
        blockItem(stairs);
        var slabModel = models().slab(slab.getId().getPath(), tex, tex, tex).renderType(renderType);
        var slabTopModel = models().slabTop(slab.getId().getPath() + "_top", tex, tex, tex).renderType(renderType);
        slabBlock((SlabBlock) slab.get(), slabModel, slabTopModel, baseModel);
        blockItem(slab);
        wallBlockWithRenderType((WallBlock) wall.get(), tex, renderType);
    }
}
