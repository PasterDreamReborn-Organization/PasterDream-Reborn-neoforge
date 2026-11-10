package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.CalciteConeBlock;
import com.pasterdream.pasterdreammod.world.block.FigVineBlock;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ChainBlock;
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
        getVariantBuilder(ModBlocks.DREAM_CAULDRON.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("custom/dream_cauldron_particle")))
                        .rotationY(((int) state.getValue(DreamCauldronBlock.FACING).toYRot() + 180) % 360)
                        .build());
        getVariantBuilder(ModBlocks.DREAM_ACCUMULATOR.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("custom/dream_accumulator_particle")))
                        .build());
        // 圣诞彩灯：水平朝向，手动模型
        var christmasLightsModel = models().getExistingFile(modLoc("block/christmas_lights"));
        horizontalBlock(ModBlocks.CHRISTMAS_LIGHTS.get(), christmasLightsModel);
        // 小石堆：水平朝向，手动模型
        var smallStoneSpiritBlockModel = models().getExistingFile(modLoc("block/small_stone_spirit_block"));
        horizontalBlock(ModBlocks.SMALL_STONE_SPIRIT_BLOCK.get(), smallStoneSpiritBlockModel);
        // 暗影高炉核心：上下侧面纹理
        var shadowBlastFurnaceCoreSideTex = modLoc("block/shadow_blast_furnace_core_side");
        var shadowBlastFurnaceCoreTopTex = modLoc("block/shadow_blast_furnace_core_top");
        var shadowBlastFurnaceCoreModel = models().cubeBottomTop("shadow_blast_furnace_core", shadowBlastFurnaceCoreSideTex, shadowBlastFurnaceCoreTopTex, shadowBlastFurnaceCoreTopTex);
        simpleBlockWithItem(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get(), shadowBlastFurnaceCoreModel);
        simpleBlock(ModBlocks.SHADOW_LIQUID.get(), models().cubeAll(ModBlocks.SHADOW_LIQUID.getId().getPath(), modLoc("block/shadow_liquid_flowing")));

        // ===== 鏌撴ⅵ鍦板舰鏂瑰潡 =====
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

        // ===== 绮夐《鑿岀郴鍒?=====
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
        // ===== 鏌撴ⅵ鐭宠嫳寤烘潗绯诲垪 =====
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

        // ===== 鏌撴ⅵ鐮傚博寤烘潗绯诲垪 =====
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

        // ===== 鏌撴ⅵ鏈ㄦ澘寤烘潗绯诲垪 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_PLANKS.get(), cubeAll(ModBlocks.DYEDREAM_PLANKS.get()));

        stairsBlock((StairBlock) ModBlocks.DYEDREAM_STAIRS.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_STAIRS);

        slabBlock((SlabBlock) ModBlocks.DYEDREAM_SLAB.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_SLAB);

        fenceBlock((FenceBlock) ModBlocks.DYEDREAM_FENCE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));

        fenceGateBlock((FenceGateBlock) ModBlocks.DYEDREAM_FENCE_GATE.get(), blockTexture(ModBlocks.DYEDREAM_PLANKS.get()));
        blockItem(ModBlocks.DYEDREAM_FENCE_GATE);

        paneBlockWithRenderType((IronBarsBlock) ModBlocks.DYEDREAM_PANE.get(), modLoc("block/dyedream_trapdoor"), modLoc("block/dyedream_trapdoor"), "cutout");

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

        // ===== 鏌撴ⅵ鐜荤拑绯诲垪 =====
        simpleBlockWithItem(ModBlocks.DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_GLASS.get())).renderType("translucent"));
        var glassTex = modLoc("block/dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.DYEDREAM_GLASS_PANE.get(), glassTex, glassTex, "translucent");

        simpleBlockWithItem(ModBlocks.CARVE_DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.CARVE_DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.CARVE_DYEDREAM_GLASS.get())).renderType("translucent"));
        var carveTex = modLoc("block/carve_dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), carveTex, carveTex, "translucent");

        simpleBlockWithItem(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), models().cubeAll(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.getId().getPath(), blockTexture(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get())).renderType("translucent"));
        var goldCarveTex = modLoc("block/gold_carve_dyedream_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), goldCarveTex, goldCarveTex, "translucent");

        // ===== 鏌撴ⅵ鏅惰娊涓庡悎閲戝潡 =====
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

        // ===== 鏌撴ⅵ姘存櫠寤烘潗绯诲垪 =====
        simpleBuildingFamily(ModBlocks.DYEDREAM_BUD_BRICKS, ModBlocks.DYEDREAM_BUD_STAIRS, ModBlocks.DYEDREAM_BUD_SLAB, ModBlocks.DYEDREAM_BUD_WALL, "translucent");

        // ===== 鏌撴ⅵ鍐颁笌浜?=====
        simpleBlockWithItem(ModBlocks.DYEDREAM_ICE.get(), models().cubeAll(ModBlocks.DYEDREAM_ICE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_ICE.get())).renderType("translucent"));
        simpleBlockWithItem(ModBlocks.DYEDREAM_PACKED_ICE.get(), models().cubeAll(ModBlocks.DYEDREAM_PACKED_ICE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_PACKED_ICE.get())).renderType("translucent"));
        var bubbleModel = models().cubeAll(ModBlocks.BIG_BUBBLE.getId().getPath(), modLoc("block/big_bubble")).renderType("translucent");
        simpleBlockWithItem(ModBlocks.BIG_BUBBLE.get(), bubbleModel);
        var cloudModel = models().cubeAll(ModBlocks.CLOUD.getId().getPath(), blockTexture(ModBlocks.CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.CLOUD.get(), cloudModel);
        var darkCloudModel = models().cubeAll(ModBlocks.DARK_CLOUD.getId().getPath(), blockTexture(ModBlocks.DARK_CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.DARK_CLOUD.get(), darkCloudModel);

        // ===== 鏌撴ⅵ鐏?=====
        simpleBlockWithItem(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), models().cubeAll(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get())).renderType("translucent"));
        var lanternStanding = models().withExistingParent("dyedream_lantern", mcLoc("block/template_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        var lanternHanging = models().withExistingParent("dyedream_lantern_hanging", mcLoc("block/template_hanging_lantern"))
                .texture("lantern", modLoc("block/dyedream_lantern")).renderType("cutout");
        getVariantBuilder(ModBlocks.DYEDREAM_LANTERN.get())
                .partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(lanternStanding).addModel()
                .partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(lanternHanging).addModel();
        simpleBlockItem(ModBlocks.DYEDREAM_LANTERN.get(), lanternStanding);

        // ===== 鍐版櫠宀╀笌鍐板噷鏅惰娊 =====
        simpleBlockWithItem(ModBlocks.ICE_STONE.get(), cubeAll(ModBlocks.ICE_STONE.get()));
        var iceBud = models().cross(ModBlocks.ICE_BUD.getId().getPath(), blockTexture(ModBlocks.ICE_BUD.get())).renderType("cutout");
        directionalBlock(ModBlocks.ICE_BUD.get(), iceBud);

        // ===== 鏂硅В鐭崇郴鍒?=====
        simpleBuildingFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL);
        simpleBuildingFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL);

        // ===== 鏂硅В鐭抽敟 =====
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

        // ===== 澶辫惤鍓戝啟 =====
        var lostSwordTomb = models().getBuilder(ModBlocks.LOST_SWORD_TOMB.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc("custom/lost_sword_tomb")))
                .texture("all", modLoc("block/lost_sword"))
                .texture("particle", mcLoc("block/mossy_cobblestone"))
                .texture("1", modLoc("block/lost_sword"))
                .texture("2", modLoc("block/lost_sword"))
                .renderType("cutout");
        horizontalBlock(ModBlocks.LOST_SWORD_TOMB.get(), lostSwordTomb);
        horizontalBlock(ModBlocks.DYEDREAM_DESK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/dyedream_desk")));
        horizontalBlock(ModBlocks.SHADOW_DESK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/shadow_desk")));
        horizontalBlock(ModBlocks.WIND_MOOR_DESK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/wind_moor_desk")));
        horizontalBlock(ModBlocks.PICNIC_BASKET.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/picnic_basket")));
        horizontalBlock(ModBlocks.SHADOW_CHEST.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/shadow_chest_particle")));
        horizontalBlock(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/melt_dream_crystal_chest_particle")));
        horizontalBlock(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/opened_melt_dream_crystal_chest_particle")));
        horizontalBlock(ModBlocks.WIND_MOOR_CRATE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/wind_moor_crate")));
        horizontalBlock(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/the_endless_book_of_dream_seekers_particle")));
        horizontalBlock(ModBlocks.LIFE_CRYSTAL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/life_crystal_particle")));
        blockItem(ModBlocks.LOST_SWORD_TOMB);

        // ===== 染梦裂隙 =====
        var dyedreamCrackModel = models().getBuilder(ModBlocks.DYEDREAM_CRACK.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(modLoc("custom/dyedream_crack")))
                .texture("all", modLoc("block/dyedream_crack"))
                .texture("particle", modLoc("block/dyedream_crack"))
                .texture("0", modLoc("block/dyedream_crack"))
                .renderType("translucent");
        horizontalBlock(ModBlocks.DYEDREAM_CRACK.get(), dyedreamCrackModel);

        var shadowModel = models().cubeAll(ModBlocks.SHADOW.getId().getPath(), blockTexture(ModBlocks.SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.SHADOW.get(), shadowModel);
        var thickShadowModel = models().cubeAll(ModBlocks.THICK_SHADOW.getId().getPath(), blockTexture(ModBlocks.THICK_SHADOW.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.THICK_SHADOW.get(), thickShadowModel);

        // ===== 鐏奖涔嬩笅 =====
        simpleBlockWithItem(ModBlocks.SHADOW_STONE.get(), models().cubeAll(ModBlocks.SHADOW_STONE.getId().getPath(), blockTexture(ModBlocks.SHADOW_STONE.get())));
        simpleBuildingFamily(ModBlocks.SHADOW_STONE_BRICK, ModBlocks.SHADOW_STONE_BRICK_STAIRS, ModBlocks.SHADOW_STONE_BRICK_SLAB, ModBlocks.SHADOW_STONE_BRICK_WALL);
        simpleBuildingFamily(ModBlocks.NARROW_SHADOW_STONE_BRICK, ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS, ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB, ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL);
        simpleBuildingFamily(ModBlocks.SHADOW_STONE_TILES, ModBlocks.SHADOW_STONE_TILES_STAIRS, ModBlocks.SHADOW_STONE_TILES_SLAB, ModBlocks.SHADOW_STONE_TILES_WALL);
        simpleBlockWithItem(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get(), cubeAll(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get()));
        simpleBlockWithItem(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get(), cubeAll(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get()));
        var shadowStoneTopBottom = blockTexture(ModBlocks.SHADOW_STONE.get());
        var cageRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_CAGE_RUNE.getId().getPath(), modLoc("block/shadow_stone_cage_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_CAGE_RUNE.get(), cageRuneModel);
        var holyGrailRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.getId().getPath(), modLoc("block/shadow_stone_holy_grail_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get(), holyGrailRuneModel);
        var oblationRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_OBLATION_RUNE.getId().getPath(), modLoc("block/shadow_stone_oblation_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get(), oblationRuneModel);
        var tripodCauldronRuneModel = models().cubeBottomTop(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.getId().getPath(), modLoc("block/shadow_stone_tripod_cauldron_rune"), shadowStoneTopBottom, shadowStoneTopBottom);
        simpleBlockWithItem(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get(), tripodCauldronRuneModel);

        simpleBlockWithItem(ModBlocks.SHADOW_DUNGEON_STONE.get(), cubeAll(ModBlocks.SHADOW_DUNGEON_STONE.get()));
        var chiseledDungeonSide = modLoc("block/chiseled_shadow_dungeon_bricks_side");
        var chiseledDungeonTop = modLoc("block/chiseled_shadow_dungeon_bricks_top");
        axisBlock((RotatedPillarBlock) ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get(), chiseledDungeonSide, chiseledDungeonTop);
        blockItem(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS);
        simpleBlockWithItem(ModBlocks.SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.SHADOW_DUNGEON_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get()));
        simpleBlockWithItem(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get()));
        var dungeonBrickTex = blockTexture(ModBlocks.SHADOW_DUNGEON_BRICKS.get());
        stairsBlock((StairBlock) ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get(), dungeonBrickTex);
        blockItem(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS);
        slabBlock((SlabBlock) ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), dungeonBrickTex, dungeonBrickTex);
        blockItem(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB);
        simpleBlockWithItem(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), cubeAll(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get()));

        simpleBlockWithItem(ModBlocks.SHADOW_ARENA_BLOCK.get(), cubeAll(ModBlocks.SHADOW_ARENA_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_0.get(), cubeAll(ModBlocks.SHADOW_FISSURE_0.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_1.get(), models().cubeAll(ModBlocks.SHADOW_FISSURE_1.getId().getPath(), blockTexture(ModBlocks.SHADOW_FISSURE_1.get())).renderType("translucent"));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_2.get(), cubeAll(ModBlocks.SHADOW_FISSURE_2.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_3.get(), models().cubeAll(ModBlocks.SHADOW_FISSURE_3.getId().getPath(), blockTexture(ModBlocks.SHADOW_FISSURE_3.get())).renderType("translucent"));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_4.get(), cubeAll(ModBlocks.SHADOW_FISSURE_4.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_FISSURE_5.get(), models().cubeAll(ModBlocks.SHADOW_FISSURE_5.getId().getPath(), blockTexture(ModBlocks.SHADOW_FISSURE_5.get())).renderType("translucent"));

        var shadowNyliumModel = models().cubeBottomTop(
                ModBlocks.SHADOW_NYLIUM.getId().getPath(),
                modLoc("block/shadow_nylium_side"),
                modLoc("block/shadow_stone"),
                modLoc("block/shadow_nylium_top"));
        simpleBlockWithItem(ModBlocks.SHADOW_NYLIUM.get(), shadowNyliumModel);
        var shadowLightModel = models().cubeBottomTop(
                ModBlocks.SHADOW_LIGHT.getId().getPath(),
                modLoc("block/shadow_light_side"),
                modLoc("block/shadow_light_top"),
                modLoc("block/shadow_light_top"));
        simpleBlockWithItem(ModBlocks.SHADOW_LIGHT.get(), shadowLightModel);
        simpleBlockWithItem(ModBlocks.SHADOW_SHROOMLIGHT.get(), cubeAll(ModBlocks.SHADOW_SHROOMLIGHT.get()));
        simpleBlockWithItem(ModBlocks.SHADOW_WART_BLOCK.get(), cubeAll(ModBlocks.SHADOW_WART_BLOCK.get()));
        logBlock((RotatedPillarBlock) ModBlocks.SHADOW_STEM.get());
        blockItem(ModBlocks.SHADOW_STEM);
        var shadowStemTex = blockTexture(ModBlocks.SHADOW_STEM.get());
        axisBlock((RotatedPillarBlock) ModBlocks.SHADOW_HYPHAE.get(), shadowStemTex, shadowStemTex);
        blockItem(ModBlocks.SHADOW_HYPHAE);
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOW_STEM.get());
        blockItem(ModBlocks.STRIPPED_SHADOW_STEM);
        var strippedShadowStemTex = blockTexture(ModBlocks.STRIPPED_SHADOW_STEM.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_SHADOW_HYPHAE.get(), strippedShadowStemTex, strippedShadowStemTex);
        blockItem(ModBlocks.STRIPPED_SHADOW_HYPHAE);

        simpleBlockWithItem(ModBlocks.SHADOW_PLANKS.get(), cubeAll(ModBlocks.SHADOW_PLANKS.get()));
        stairsBlock((StairBlock) ModBlocks.SHADOW_STAIRS.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_STAIRS);
        slabBlock((SlabBlock) ModBlocks.SHADOW_SLAB.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_SLAB);
        fenceBlock((FenceBlock) ModBlocks.SHADOW_FENCE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.SHADOW_FENCE_GATE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_FENCE_GATE);
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.SHADOW_PANE.get(), modLoc("block/shadow_trapdoor"), modLoc("block/shadow_trapdoor"), "cutout");
        doorBlockWithRenderType((DoorBlock) ModBlocks.SHADOW_DOOR.get(),
                modLoc("block/shadow_door_bottom"),
                modLoc("block/shadow_door_top"),
                "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.SHADOW_TRAPDOOR.get(),
                modLoc("block/shadow_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.SHADOW_TRAPDOOR, "_bottom");
        pressurePlateBlock((PressurePlateBlock) ModBlocks.SHADOW_PRESSURE_PLATE.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));
        blockItem(ModBlocks.SHADOW_PRESSURE_PLATE);
        buttonBlock((ButtonBlock) ModBlocks.SHADOW_BUTTON.get(), blockTexture(ModBlocks.SHADOW_PLANKS.get()));

        horizontalBookshelf(ModBlocks.SHADOW_BOOKSHELF);
        horizontalBookshelf(ModBlocks.WORN_SHADOW_BOOKSHELF);
        horizontalBookshelf(ModBlocks.COBWEB_SHADOW_BOOKSHELF);
        horizontalBookshelfKey(ModBlocks.KEY_SHADOW_BOOKSHELF);

        simpleBlockWithItem(ModBlocks.BLACK_METAL_BLOCK.get(), cubeAll(ModBlocks.BLACK_METAL_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.RUST_BLACK_METAL_BLOCK.get(), cubeAll(ModBlocks.RUST_BLACK_METAL_BLOCK.get()));
        var rustMetalTex = blockTexture(ModBlocks.RUST_BLACK_METAL_BLOCK.get());
        wallBlock((WallBlock) ModBlocks.RUST_BLACK_METAL_WALL.get(), rustMetalTex);
        var barsTex = modLoc("block/rust_black_metal_bars");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.RUST_BLACK_METAL_BARS.get(), barsTex, barsTex, "cutout");

        var clayPotModel = models().getExistingFile(modLoc("block/clay_pot"));
        horizontalBlock(ModBlocks.SHADOW_CLAY_POT.get(), clayPotModel);

        simpleBlock(ModBlocks.DYEDREAM_SAPLING.get(),
                models().cross(ModBlocks.DYEDREAM_SAPLING.getId().getPath(),
                        blockTexture(ModBlocks.DYEDREAM_SAPLING.get())).renderType("cutout"));

        var pinkMushroom = models().cross(ModBlocks.PINK_MUSHROOM.getId().getPath(), blockTexture(ModBlocks.PINK_MUSHROOM.get())).renderType("cutout");
        simpleBlock(ModBlocks.PINK_MUSHROOM.get(), pinkMushroom);

        var tallUpper = models().cross(ModBlocks.TALL_PINK_MUSHROOM.getId().getPath() + "_top", modLoc("block/pink_mushroom")).renderType("cutout");
        var tallLower = models().cross(ModBlocks.TALL_PINK_MUSHROOM.getId().getPath() + "_bottom", modLoc("block/tall_pink_mushroom_bottom")).renderType("cutout");
        getVariantBuilder(ModBlocks.TALL_PINK_MUSHROOM.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(tallLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(tallUpper));


        // ===== 妞嶇墿绯诲垪 =====
        var goldenrod = models().cross(ModBlocks.GOLDENROD.getId().getPath(), blockTexture(ModBlocks.GOLDENROD.get())).renderType("cutout");
        simpleBlock(ModBlocks.GOLDENROD.get(), goldenrod);
        var ferraria_crispa = models().cross(ModBlocks.FERRARIA_CRISPA.getId().getPath(), blockTexture(ModBlocks.FERRARIA_CRISPA.get())).renderType("cutout");
        simpleBlock(ModBlocks.FERRARIA_CRISPA.get(), ferraria_crispa);
        var eustoma = models().cross(ModBlocks.EUSTOMA.getId().getPath(), blockTexture(ModBlocks.EUSTOMA.get())).renderType("cutout");
        simpleBlock(ModBlocks.EUSTOMA.get(), eustoma);
        var dreaming_lotus_Upper = models().cross(ModBlocks.DREAMING_LOTUS.getId().getPath(),
                modLoc("block/dreaming_lotus_top")).renderType("cutout");
        var dreaming_lotus_Lower = models().cross(ModBlocks.DREAMING_LOTUS.getId().getPath() + "_bottom",
                modLoc("block/dreaming_lotus")).renderType("cutout");
        var misty_dreaming_lotus_Upper = models().cross(ModBlocks.MISTY_DREAMING_LOTUS.getId().getPath(),
                modLoc("block/misty_dreaming_lotus_top")).renderType("cutout");
        var misty_dreaming_lotus_Lower = models().cross(ModBlocks.MISTY_DREAMING_LOTUS.getId().getPath() + "_bottom",
                modLoc("block/dreaming_lotus")).renderType("cutout");
        getVariantBuilder(ModBlocks.DREAMING_LOTUS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(dreaming_lotus_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(dreaming_lotus_Upper));
        getVariantBuilder(ModBlocks.MISTY_DREAMING_LOTUS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(misty_dreaming_lotus_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(misty_dreaming_lotus_Upper));
        var linht_flower = models().cross(ModBlocks.LINHT_FLOWER.getId().getPath(), blockTexture(ModBlocks.LINHT_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.LINHT_FLOWER.get(), linht_flower);
        var blaze_flower = models().cross(ModBlocks.BLAZE_FLOWER.getId().getPath(), blockTexture(ModBlocks.BLAZE_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.BLAZE_FLOWER.get(), blaze_flower);
        var dyedream_lily_of_the_valley = models().cross(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get(), dyedream_lily_of_the_valley);

        var white_orchid_flower = models().cross(ModBlocks.WHITE_ORCHID_FLOWER.getId().getPath(), blockTexture(ModBlocks.WHITE_ORCHID_FLOWER.get())).renderType("cutout");
        simpleBlock(ModBlocks.WHITE_ORCHID_FLOWER.get(), white_orchid_flower);
        var edelweiss = models().cross(ModBlocks.EDELWEISS.getId().getPath(), blockTexture(ModBlocks.EDELWEISS.get())).renderType("cutout");
        simpleBlock(ModBlocks.EDELWEISS.get(), edelweiss);
        var nippy_edelweiss = models().cross(ModBlocks.NIPPY_EDELWEISS.getId().getPath(), blockTexture(ModBlocks.NIPPY_EDELWEISS.get())).renderType("cutout");
        simpleBlock(ModBlocks.NIPPY_EDELWEISS.get(), nippy_edelweiss);


        var malva_sinensis_cavan = models().cross(ModBlocks.MALVA_SINENSIS_CAVAN.getId().getPath(), blockTexture(ModBlocks.MALVA_SINENSIS_CAVAN.get())).renderType("cutout");
        simpleBlock(ModBlocks.MALVA_SINENSIS_CAVAN.get(), malva_sinensis_cavan);
        var GrassTallUpper = models().cross(ModBlocks.TALL_STEM_GRASS.getId().getPath() + "_top", modLoc("block/stem_grass")).renderType("cutout");
        var GrassTallLower = models().cross(ModBlocks.TALL_STEM_GRASS.getId().getPath() + "_bottom", modLoc("block/tall_stem_grass")).renderType("cutout");
        getVariantBuilder(ModBlocks.TALL_STEM_GRASS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(GrassTallLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(GrassTallUpper));

        var crimson_thorns_Upper = models().cross(ModBlocks.CRIMSON_THORNS.getId().getPath(), modLoc("block/crimson_thorns_top")).renderType("cutout");
        var crimson_thorns_Lower = models().cross(ModBlocks.CRIMSON_THORNS.getId().getPath() + "_bottom", modLoc("block/crimson_thorns")).renderType("cutout");
        getVariantBuilder(ModBlocks.CRIMSON_THORNS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(crimson_thorns_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(crimson_thorns_Upper));
        var oats = models().cross(ModBlocks.OATS.getId().getPath(), blockTexture(ModBlocks.OATS.get())).renderType("cutout");
        simpleBlock(ModBlocks.OATS.get(), oats);
        var rye = models().cross(ModBlocks.RYE.getId().getPath(), blockTexture(ModBlocks.RYE.get())).renderType("cutout");
        simpleBlock(ModBlocks.RYE.get(), rye);

        var stalicripe = models().cross(ModBlocks.POLISHED_CALCITE_STALICRIPE.getId().getPath(), blockTexture(ModBlocks.POLISHED_CALCITE_STALICRIPE.get())).renderType("cutout");
        simpleBlock(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), stalicripe);
        var small_stalicripe = models().cross(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.getId().getPath(), blockTexture(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get())).renderType("cutout");
        simpleBlock(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), small_stalicripe);
        var reed = models().cross(ModBlocks.REED.getId().getPath(), blockTexture(ModBlocks.REED.get())).renderType("cutout");
        simpleBlock(ModBlocks.REED.get(), reed);
        var Dyedream_vine = models().cross(ModBlocks.DYEDREAM_VINE.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_VINE.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_VINE.get(), Dyedream_vine);
        var Jungle_sporangium = models().cross(ModBlocks.JUNGLE_SPORANGIUM.getId().getPath(), blockTexture(ModBlocks.JUNGLE_SPORANGIUM.get())).renderType("cutout");
        simpleBlock(ModBlocks.JUNGLE_SPORANGIUM.get(),Jungle_sporangium);
        // 椋庝箣妞嶇墿绯诲垪
        var hairy_moss = models().cross(ModBlocks.HAIRY_MOSS.getId().getPath(), blockTexture(ModBlocks.HAIRY_MOSS.get())).renderType("cutout");
        simpleBlock(ModBlocks.HAIRY_MOSS.get(), hairy_moss);
        var wind_cleaving_grass = models().cross(ModBlocks.WIND_CLEAVING_GRASS.getId().getPath(), blockTexture(ModBlocks.WIND_CLEAVING_GRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.WIND_CLEAVING_GRASS.get(), wind_cleaving_grass);
        var wind_feather_grass_Upper = models().cross(ModBlocks.WIND_FEATHER_GRASS.getId().getPath() + "_top", modLoc("block/wind_feather_grass_top")).renderType("cutout");
        var wind_feather_grass_Lower = models().cross(ModBlocks.WIND_FEATHER_GRASS.getId().getPath() + "_bottom", modLoc("block/wind_feather_grass_bottom")).renderType("cutout");
        getVariantBuilder(ModBlocks.WIND_FEATHER_GRASS.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(wind_feather_grass_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(wind_feather_grass_Upper));
        var wind_island_reed_Upper = models().cross(ModBlocks.WIND_ISLAND_REED.getId().getPath() + "_top", modLoc("block/wind_island_reed_top")).renderType("cutout");
        var wind_island_reed_Lower = models().cross(ModBlocks.WIND_ISLAND_REED.getId().getPath() + "_bottom", modLoc("block/wind_island_reed_bottom")).renderType("cutout");
        getVariantBuilder(ModBlocks.WIND_ISLAND_REED.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(wind_island_reed_Lower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(wind_island_reed_Upper));
        // fourleaf_clover uses manual custom model
        simpleBlock(ModBlocks.FOURLEAF_CLOVER.get(), models().getExistingFile(modLoc("block/fourleaf_clover")));
        var light_ball = models().cross(ModBlocks.LIGHT_BALL.getId().getPath(), blockTexture(ModBlocks.LIGHT_BALL.get())).renderType("cutout");
        simpleBlock(ModBlocks.LIGHT_BALL.get(),light_ball);

        // 灏忕煶瀛?
        var pebbleModel = models().getExistingFile(modLoc("block/pebble"));
        horizontalBlock(ModBlocks.PEBBLE.get(), pebbleModel);


        //妞嶆牚鏂瑰潡
        generateTwoStageCrop(ModBlocks.DYEDREAM_COROLLA_CROP.get(), "dyedream_corolla_crop");
        generateTwoStageCrop(ModBlocks.WHITE_COROLLA_CROP.get(), "white_corolla_crop");
        generateTwoStageCrop(ModBlocks.LIGHT_BALL_CROP.get(), "light_ball_crop");
        generateTwoStageCrop(ModBlocks.CLOUD_CROP.get(), "cloud_crop");
        generateTwoStageCrop(ModBlocks.COTTON_CROP.get(), "cotton_crop");

        generateTwoStageCropBlockModel("dyedream_corolla_crop");
        generateTwoStageCropBlockModel("white_corolla_crop");
        generateTwoStageCropBlockModel("light_ball_crop");
        generateTwoStageCropBlockModel("cloud_crop");
        generateTwoStageCropBlockModel("cotton_crop");

        var dyedreamMoss = models().cross(ModBlocks.DYEDREAM_MOSS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_MOSS.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_MOSS.get(), dyedreamMoss);

        var stemGrass = models().cross(ModBlocks.STEM_GRASS.getId().getPath(), blockTexture(ModBlocks.STEM_GRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.STEM_GRASS.get(), stemGrass);

        var singularity_fern = models().cross(ModBlocks.SINGULARITY_FERN.getId().getPath(), blockTexture(ModBlocks.SINGULARITY_FERN.get())).renderType("cutout");
        simpleBlock(ModBlocks.SINGULARITY_FERN.get(), singularity_fern);

        // 闃村奖妞嶇墿绯诲垪
        var shadowShortRoots = models().cross(ModBlocks.SHADOW_SHORT_ROOTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_SHORT_ROOTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_SHORT_ROOTS.get(), shadowShortRoots);

        var shadowRoots = models().cross(ModBlocks.SHADOW_ROOTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_ROOTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_ROOTS.get(), shadowRoots);

        var shadowStemFernUpper = models().cross(ModBlocks.SHADOW_STEM_FERN.getId().getPath() + "_top", modLoc("block/shadow_stem_fern_top")).renderType("cutout");
        var shadowStemFernLower = models().cross(ModBlocks.SHADOW_STEM_FERN.getId().getPath() + "_bottom", modLoc("block/shadow_stem_fern")).renderType("cutout");
        getVariantBuilder(ModBlocks.SHADOW_STEM_FERN.get())
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(shadowStemFernLower))
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(shadowStemFernUpper));

        var shadowSprouts = models().cross(ModBlocks.SHADOW_SPROUTS.getId().getPath(), blockTexture(ModBlocks.SHADOW_SPROUTS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_SPROUTS.get(), shadowSprouts);

        var shadowFern = models().cross(ModBlocks.SHADOW_FERN.getId().getPath(), blockTexture(ModBlocks.SHADOW_FERN.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_FERN.get(), shadowFern);

        var shadowFungus = models().cross(ModBlocks.SHADOW_FUNGUS.getId().getPath(), blockTexture(ModBlocks.SHADOW_FUNGUS.get())).renderType("cutout");
        simpleBlock(ModBlocks.SHADOW_FUNGUS.get(), shadowFungus);

        // 鏌撴ⅵ娴疯崏
        var dyedreamSeagrass = models().cross(ModBlocks.DYEDREAM_SEAGRASS.getId().getPath(), blockTexture(ModBlocks.DYEDREAM_SEAGRASS.get())).renderType("cutout");
        simpleBlock(ModBlocks.DYEDREAM_SEAGRASS.get(), dyedreamSeagrass);

        // 鏌撴ⅵ鐫¤幉 / 鏌撴ⅵ鑾茶姳 (custom Blockbench models)
        simpleBlock(ModBlocks.DYEDREAM_LILY_PAD.get(), models().getExistingFile(modLoc("block/dyedream_lily_pad")));
        simpleBlock(ModBlocks.DYEDREAM_LOTUS.get(), models().getExistingFile(modLoc("block/dyedream_lotus")));


        // ===== 鑽掓紶鑻遍泟涔嬪 =====
        horizontalBlock(ModBlocks.QYM_DOLL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/qym_doll_particle")));
        horizontalBlock(ModBlocks.UUZ_DOLL.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/uuz_doll_particle")));
        horizontalBlock(ModBlocks.DESERT_HERO_TOMB.get(),
                new ModelFile.UncheckedModelFile(modLoc("custom/desert_hero_tomb_particle")));

        getVariantBuilder(ModBlocks.SHADOW_HAND_TRAP.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(modLoc("custom/shadow_hand_trap_particle")))
                        .build());

        // ===== 鐩嗘牻妞嶇墿 =====
        for (var entry : ModBlocks.POTTED_PLANTS.entrySet()) {
            var plant = entry.getKey();
            var potted = entry.getValue();
            boolean isCrop = ModBlocks.POTTED_CROPS.contains(potted);

            if (isCrop) {
                var plantName = plant.getId().getPath();
                var seedlingTex = modLoc("block/" + plantName + "_age_0");
                var matureTex = modLoc("block/" + plantName);

                var seedlingModel = models().withExistingParent(potted.getId().getPath() + "_age_0", mcLoc("block/flower_pot_cross"))
                        .texture("plant", seedlingTex).renderType("cutout");
                var matureModel = models().withExistingParent(potted.getId().getPath(), mcLoc("block/flower_pot_cross"))
                        .texture("plant", matureTex).renderType("cutout");

                getVariantBuilder(potted.get())
                        .partialState().with(BlockStateProperties.AGE_1, 0)
                        .addModels(new ConfiguredModel(seedlingModel))
                        .partialState().with(BlockStateProperties.AGE_1, 1)
                        .addModels(new ConfiguredModel(matureModel));
            } else if (potted == ModBlocks.POTTED_FOURLEAF_CLOVER) {
                simpleBlock(potted.get(), models().getExistingFile(modLoc("block/potted_fourleaf_clover")));
            } else {
                var plantTex = modLoc("block/" + plant.getId().getPath());
                var pottedModel = models().withExistingParent(potted.getId().getPath(), mcLoc("block/flower_pot_cross"))
                        .texture("plant", plantTex).renderType("cutout");
                simpleBlock(potted.get(), pottedModel);
            }
        }

        // ===== 风之旅途·群系方块 =====
        simpleBlockWithItem(ModBlocks.WHITE_SAND.get(), cubeAll(ModBlocks.WHITE_SAND.get()));
        var thickCloudModel = models().cubeAll(ModBlocks.THICK_CLOUD.getId().getPath(), blockTexture(ModBlocks.THICK_CLOUD.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.THICK_CLOUD.get(), thickCloudModel);

        var whiteSandstoneSide = modLoc("block/white_sandstone");
        var whiteSandstoneTop = modLoc("block/white_sandstone_top");
        var whiteSandstoneBottom = modLoc("block/white_sandstone_bottom");
        var whiteSandstoneModel = models().cubeBottomTop(
                ModBlocks.WHITE_SANDSTONE.getId().getPath(),
                whiteSandstoneSide,
                whiteSandstoneBottom,
                whiteSandstoneTop);
        simpleBlockWithItem(ModBlocks.WHITE_SANDSTONE.get(), whiteSandstoneModel);
        stairsBlock((StairBlock) ModBlocks.WHITE_SANDSTONE_STAIRS.get(), whiteSandstoneSide, whiteSandstoneBottom, whiteSandstoneTop);
        blockItem(ModBlocks.WHITE_SANDSTONE_STAIRS);
        slabBlock((SlabBlock) ModBlocks.WHITE_SANDSTONE_SLAB.get(), modLoc("block/white_sandstone"), whiteSandstoneSide, whiteSandstoneBottom, whiteSandstoneTop);
        blockItem(ModBlocks.WHITE_SANDSTONE_SLAB);
        wallBlock((WallBlock) ModBlocks.WHITE_SANDSTONE_WALL.get(), whiteSandstoneSide);

        var cutWhiteSandstoneTex = modLoc("block/cut_white_sandstone");
        var cutWhiteSandstoneModel = models().cubeColumn(
                ModBlocks.CUT_WHITE_SANDSTONE.getId().getPath(),
                cutWhiteSandstoneTex,
                whiteSandstoneTop);
        simpleBlockWithItem(ModBlocks.CUT_WHITE_SANDSTONE.get(), cutWhiteSandstoneModel);
        stairsBlock((StairBlock) ModBlocks.CUT_WHITE_SANDSTONE_STAIRS.get(), cutWhiteSandstoneTex);
        blockItem(ModBlocks.CUT_WHITE_SANDSTONE_STAIRS);
        slabBlock((SlabBlock) ModBlocks.CUT_WHITE_SANDSTONE_SLAB.get(), modLoc("block/cut_white_sandstone"), cutWhiteSandstoneTex, cutWhiteSandstoneTex, cutWhiteSandstoneTex);
        blockItem(ModBlocks.CUT_WHITE_SANDSTONE_SLAB);
        wallBlock((WallBlock) ModBlocks.CUT_WHITE_SANDSTONE_WALL.get(), cutWhiteSandstoneTex);

        var chiseledWhiteSandstoneTex = modLoc("block/chiseled_white_sandstone");
        var chiseledWhiteSandstoneModel = models().cubeColumn(
                ModBlocks.CHISELED_WHITE_SANDSTONE.getId().getPath(),
                chiseledWhiteSandstoneTex,
                whiteSandstoneTop);
        simpleBlockWithItem(ModBlocks.CHISELED_WHITE_SANDSTONE.get(), chiseledWhiteSandstoneModel);

        // 平滑白砂岩覆盖白砂岩顶部纹理，与源版一致；
        var smoothWhiteSandstoneModel = models().cubeAll(
                ModBlocks.SMOOTH_WHITE_SANDSTONE.getId().getPath(),
                whiteSandstoneTop);
        simpleBlockWithItem(ModBlocks.SMOOTH_WHITE_SANDSTONE.get(), smoothWhiteSandstoneModel);
        stairsBlock((StairBlock) ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS.get(), whiteSandstoneTop);
        blockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS);
        slabBlock((SlabBlock) ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB.get(), modLoc("block/smooth_white_sandstone"), whiteSandstoneTop, whiteSandstoneTop, whiteSandstoneTop);
        blockItem(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB);
        wallBlock((WallBlock) ModBlocks.SMOOTH_WHITE_SANDSTONE_WALL.get(), whiteSandstoneTop);

        // ===== 澄澈玻璃 =====
        simpleBlockWithItem(ModBlocks.CLARITY_GLASS.get(), models().cubeAll(ModBlocks.CLARITY_GLASS.getId().getPath(), blockTexture(ModBlocks.CLARITY_GLASS.get())).renderType("translucent"));
        var clarityTex = modLoc("block/clarity_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.CLARITY_GLASS_PANE.get(), clarityTex, clarityTex, "translucent");
        simpleBlockWithItem(ModBlocks.CARVE_CLARITY_GLASS.get(), models().cubeAll(ModBlocks.CARVE_CLARITY_GLASS.getId().getPath(), blockTexture(ModBlocks.CARVE_CLARITY_GLASS.get())).renderType("translucent"));
        var carveClarityTex = modLoc("block/carve_clarity_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), carveClarityTex, carveClarityTex, "translucent");
        simpleBlockWithItem(ModBlocks.FRAME_CLARITY_GLASS.get(), models().cubeAll(ModBlocks.FRAME_CLARITY_GLASS.getId().getPath(), blockTexture(ModBlocks.FRAME_CLARITY_GLASS.get())).renderType("translucent"));
        var frameClarityTex = modLoc("block/frame_clarity_glass");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), frameClarityTex, frameClarityTex, "translucent");

        // ===== 凝风系方块 =====
        simpleBlockWithItem(ModBlocks.CONGEAL_WIND_ORE.get(), cubeAll(ModBlocks.CONGEAL_WIND_ORE.get()));
        simpleBlockWithItem(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get(), cubeAll(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get()));
        simpleBlockWithItem(ModBlocks.CONGEAL_WIND_BLOCK.get(), cubeAll(ModBlocks.CONGEAL_WIND_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get(), cubeAll(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get(), cubeAll(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get(), cubeAll(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get()));
        var ejectionPressureBlockModel = models().cubeBottomTop(
                ModBlocks.EJECTION_PRESSURE_BLOCK.getId().getPath(),
                modLoc("block/ejection_pressure_block"),
                modLoc("block/ejection_pressure_block_top"),
                modLoc("block/ejection_pressure_block_top"));
        simpleBlockWithItem(ModBlocks.EJECTION_PRESSURE_BLOCK.get(), ejectionPressureBlockModel);
        pressurePlateBlock((PressurePlateBlock) ModBlocks.EJECTION_PRESSURE_PLATE.get(), blockTexture(ModBlocks.EJECTION_PRESSURE_PLATE.get()));
        blockItem(ModBlocks.EJECTION_PRESSURE_PLATE);
        var congealWindIronBarsTex = modLoc("block/congeal_wind_iron_bars");
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.CONGEAL_WIND_IRON_BARS.get(), congealWindIronBarsTex, congealWindIronBarsTex, "cutout");
        var congealWindIronLanternStanding = models().withExistingParent("congeal_wind_iron_lantern", mcLoc("block/template_lantern"))
                .texture("lantern", modLoc("block/congeal_wind_iron_lantern")).renderType("cutout");
        var congealWindIronLanternHanging = models().withExistingParent("congeal_wind_iron_lantern_hanging", mcLoc("block/template_hanging_lantern"))
                .texture("lantern", modLoc("block/congeal_wind_iron_lantern")).renderType("cutout");
        getVariantBuilder(ModBlocks.CONGEAL_WIND_IRON_LANTERN.get())
                .partialState().with(LanternBlock.HANGING, false).modelForState().modelFile(congealWindIronLanternStanding).addModel()
                .partialState().with(LanternBlock.HANGING, true).modelForState().modelFile(congealWindIronLanternHanging).addModel();
        // 凝风铁锁链模型为手写资源：assets/pasterdream/models/block/congeal_wind_iron_chain.json
        getVariantBuilder(ModBlocks.CONGEAL_WIND_IRON_CHAIN.get())
                .partialState().with(ChainBlock.AXIS, Direction.Axis.X).modelForState().modelFile(new ModelFile.UncheckedModelFile(modLoc("block/congeal_wind_iron_chain"))).rotationX(90).rotationY(90).addModel()
                .partialState().with(ChainBlock.AXIS, Direction.Axis.Y).modelForState().modelFile(new ModelFile.UncheckedModelFile(modLoc("block/congeal_wind_iron_chain"))).addModel()
                .partialState().with(ChainBlock.AXIS, Direction.Axis.Z).modelForState().modelFile(new ModelFile.UncheckedModelFile(modLoc("block/congeal_wind_iron_chain"))).rotationX(90).addModel();
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.CONGEAL_WIND_IRON_TRAPDOOR.get(),
                modLoc("block/congeal_wind_iron_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.CONGEAL_WIND_IRON_TRAPDOOR, "_bottom");
        pressurePlateBlock((PressurePlateBlock) ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE.get(), blockTexture(ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE.get()));
        blockItem(ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE);

        // ===== 苍青岩系列 =====
        simpleBlockWithItem(ModBlocks.CYAN_STONE.get(), cubeAll(ModBlocks.CYAN_STONE.get()));
        var cyanMossStoneModel = models().cubeBottomTop(
                ModBlocks.CYAN_MOSS_STONE.getId().getPath(),
                blockTexture(ModBlocks.CYAN_MOSS_STONE.get()),
                blockTexture(ModBlocks.CYAN_STONE.get()),
                modLoc("block/cyan_moss_stone_top"));
        simpleBlockWithItem(ModBlocks.CYAN_MOSS_STONE.get(), cyanMossStoneModel);
        simpleBuildingFamily(ModBlocks.CYAN_STONE_BRICKS, ModBlocks.CYAN_STONE_BRICK_STAIRS, ModBlocks.CYAN_STONE_BRICK_SLAB, ModBlocks.CYAN_STONE_BRICK_WALL);
        simpleBuildingFamily(ModBlocks.MOSSY_CYAN_STONE_BRICKS, ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS, ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB, ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL);
        simpleBuildingFamily(ModBlocks.POLISHED_CYAN_STONE, ModBlocks.POLISHED_CYAN_STONE_STAIRS, ModBlocks.POLISHED_CYAN_STONE_SLAB, ModBlocks.POLISHED_CYAN_STONE_WALL);
        simpleBlockWithItem(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get(), cubeAll(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get()));
        logBlock((RotatedPillarBlock) ModBlocks.CYAN_STONE_PILLAR.get());
        blockItem(ModBlocks.CYAN_STONE_PILLAR);
        pressurePlateBlock((PressurePlateBlock) ModBlocks.CYAN_STONE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.CYAN_STONE.get()));
        blockItem(ModBlocks.CYAN_STONE_PRESSURE_PLATE);
        buttonBlock((ButtonBlock) ModBlocks.CYAN_STONE_BUTTON.get(), blockTexture(ModBlocks.CYAN_STONE.get()));

        // ===== 风泊木系列 =====
        logBlock((RotatedPillarBlock) ModBlocks.WIND_MOOR_LOG.get());
        blockItem(ModBlocks.WIND_MOOR_LOG);
        var windMoorLogTex = blockTexture(ModBlocks.WIND_MOOR_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.WIND_MOOR_WOOD.get(), windMoorLogTex, windMoorLogTex);
        blockItem(ModBlocks.WIND_MOOR_WOOD);
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WIND_MOOR_LOG.get());
        blockItem(ModBlocks.STRIPPED_WIND_MOOR_LOG);
        var strippedWindMoorLogTex = blockTexture(ModBlocks.STRIPPED_WIND_MOOR_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WIND_MOOR_WOOD.get(), strippedWindMoorLogTex, strippedWindMoorLogTex);
        blockItem(ModBlocks.STRIPPED_WIND_MOOR_WOOD);
        simpleBlockWithItem(ModBlocks.WIND_MOOR_LEAVES_0.get(), cubeAll(ModBlocks.WIND_MOOR_LEAVES_0.get()));
        simpleBlockWithItem(ModBlocks.WIND_MOOR_LEAVES_1.get(), cubeAll(ModBlocks.WIND_MOOR_LEAVES_1.get()));
        simpleBlock(ModBlocks.WIND_MOOR_SAPLING.get(),
                models().cross(ModBlocks.WIND_MOOR_SAPLING.getId().getPath(),
                        blockTexture(ModBlocks.WIND_MOOR_SAPLING.get())).renderType("cutout"));
        // 无花果藤：底部 head 用 fig_vine 材质，上方 body 用风泊树叶材质
        var figVineHead = models().cross(ModBlocks.FIG_VINE.getId().getPath(), modLoc("block/fig_vine")).renderType("cutout");
        var figVineBody = models().cross(ModBlocks.FIG_VINE.getId().getPath() + "_body", modLoc("block/wind_moor_leaves_0")).renderType("cutout");
        getVariantBuilder(ModBlocks.FIG_VINE.get())
                .partialState().with(FigVineBlock.HEAD, true).with(FigVineBlock.TRIMMED, false).addModels(new ConfiguredModel(figVineHead))
                .partialState().with(FigVineBlock.HEAD, false).with(FigVineBlock.TRIMMED, false).addModels(new ConfiguredModel(figVineBody))
                .partialState().with(FigVineBlock.HEAD, true).with(FigVineBlock.TRIMMED, true).addModels(new ConfiguredModel(figVineHead))
                .partialState().with(FigVineBlock.HEAD, false).with(FigVineBlock.TRIMMED, true).addModels(new ConfiguredModel(figVineBody));
        simpleBlockWithItem(ModBlocks.WIND_MOOR_PLANKS.get(), cubeAll(ModBlocks.WIND_MOOR_PLANKS.get()));
        stairsBlock((StairBlock) ModBlocks.WIND_MOOR_STAIRS.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));
        blockItem(ModBlocks.WIND_MOOR_STAIRS);
        slabBlock((SlabBlock) ModBlocks.WIND_MOOR_SLAB.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));
        blockItem(ModBlocks.WIND_MOOR_SLAB);
        fenceBlock((FenceBlock) ModBlocks.WIND_MOOR_FENCE.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.WIND_MOOR_FENCE_GATE.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));
        blockItem(ModBlocks.WIND_MOOR_FENCE_GATE);
        paneBlockWithRenderType((IronBarsBlock) ModBlocks.WIND_MOOR_PANE.get(), modLoc("block/wind_moor_trapdoor"), modLoc("block/wind_moor_trapdoor"), "cutout");
        doorBlockWithRenderType((DoorBlock) ModBlocks.WIND_MOOR_DOOR.get(),
                modLoc("block/wind_moor_door_bottom"),
                modLoc("block/wind_moor_door_top"),
                "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.WIND_MOOR_TRAPDOOR.get(),
                modLoc("block/wind_moor_trapdoor"),
                true,
                "cutout");
        blockItem(ModBlocks.WIND_MOOR_TRAPDOOR, "_bottom");
        pressurePlateBlock((PressurePlateBlock) ModBlocks.WIND_MOOR_PRESSURE_PLATE.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));
        blockItem(ModBlocks.WIND_MOOR_PRESSURE_PLATE);
        buttonBlock((ButtonBlock) ModBlocks.WIND_MOOR_BUTTON.get(), blockTexture(ModBlocks.WIND_MOOR_PLANKS.get()));

        // 破风幕帐：6 向水平/垂直朝向
        var breakWindCurtainModel = models().getExistingFile(modLoc("block/break_wind_curtain"));
        getVariantBuilder(ModBlocks.BREAK_WIND_CURTAIN.get()).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.FACING);
            int rotationX = switch (dir) {
                case UP -> 270;
                case DOWN -> 90;
                default -> 0;
            };
            int rotationY = dir.getAxis().isVertical() ? 0 : (int) dir.toYRot();
            return ConfiguredModel.builder()
                    .modelFile(breakWindCurtainModel)
                    .rotationX(rotationX)
                    .rotationY(rotationY)
                    .build();
        });
        registerDebugBlocks();
    }

    private void registerDebugBlocks()
    {
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get()));
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get()));
        var translucentModel = models().cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.getId().getPath(), blockTexture(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get())).renderType("translucent");
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get(), translucentModel);
        simpleBlockWithItem(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get(), cubeAll(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get()));
    }

    private void generateTwoStageCrop(Block block, String baseName)
    {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.AGE_1, 0)
                .modelForState().modelFile(modelFile(baseName + "_age_0")).addModel()
                .partialState().with(BlockStateProperties.AGE_1, 1)
                .modelForState().modelFile(modelFile(baseName)).addModel();
    }

    private ModelFile modelFile(String name)
    {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + name));
    }

    private void generateTwoStageCropBlockModel(String baseName)
    {
        models().withExistingParent(baseName + "_age_0", mcLoc("block/cross")).texture("cross", modLoc("block/" + baseName + "_age_0")).renderType("cutout");
        models().withExistingParent(baseName, mcLoc("block/cross")).texture("cross", modLoc("block/" + baseName)).renderType("cutout");
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

    private void horizontalBookshelf(net.neoforged.neoforge.registries.DeferredBlock<?> block) {
        String name = block.getId().getPath();
        ModelFile model = models().cube(name,
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/" + name),
                modLoc("block/" + name),
                modLoc("block/" + name),
                modLoc("block/" + name))
                .texture("particle", modLoc("block/shadow_bookshelf_top"));
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }

    private void horizontalBookshelfKey(net.neoforged.neoforge.registries.DeferredBlock<?> block) {
        String name = block.getId().getPath();
        ModelFile model = models().cube(name,
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/shadow_bookshelf_top"),
                modLoc("block/key_shadow_bookshelf_front"),
                modLoc("block/worn_shadow_bookshelf"),
                modLoc("block/worn_shadow_bookshelf"),
                modLoc("block/worn_shadow_bookshelf"))
                .texture("particle", modLoc("block/shadow_bookshelf_top"));
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
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
