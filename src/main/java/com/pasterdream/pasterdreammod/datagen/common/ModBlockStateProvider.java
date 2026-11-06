package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
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
        simpleBlockWithItem(ModBlocks.CALCITE_COAL_ORE.get(), cubeAll(ModBlocks.CALCITE_COAL_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_IRON_ORE.get(), cubeAll(ModBlocks.CALCITE_IRON_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_COPPER_ORE.get(), cubeAll(ModBlocks.CALCITE_COPPER_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_GOLD_ORE.get(), cubeAll(ModBlocks.CALCITE_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_REDSTONE_ORE.get(), cubeAll(ModBlocks.CALCITE_REDSTONE_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_LAPIS_ORE.get(), cubeAll(ModBlocks.CALCITE_LAPIS_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_DIAMOND_ORE.get(), cubeAll(ModBlocks.CALCITE_DIAMOND_ORE.get()));
        simpleBlockWithItem(ModBlocks.CALCITE_EMERALD_ORE.get(), cubeAll(ModBlocks.CALCITE_EMERALD_ORE.get()));
        simpleBlockWithItem(ModBlocks.PINK_SLIME_BLOCK.get(), models().cubeAll(ModBlocks.PINK_SLIME_BLOCK.getId().getPath(), blockTexture(ModBlocks.PINK_SLIME_BLOCK.get())).renderType("minecraft:translucent"));
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

        // ===== 方解石系列 =====
        simpleBuildingFamily(ModBlocks.POLISHED_CALCITE, ModBlocks.POLISHED_CALCITE_STAIRS, ModBlocks.POLISHED_CALCITE_SLAB, ModBlocks.POLISHED_CALCITE_WALL);
        simpleBuildingFamily(ModBlocks.CALCITE_TILES, ModBlocks.CALCITE_TILES_STAIRS, ModBlocks.CALCITE_TILES_SLAB, ModBlocks.CALCITE_TILES_WALL);

        simpleBlockWithItem(ModBlocks.TITANIUM_ORE.get(), cubeAll(ModBlocks.TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.RAW_TITANIUM_BLOCK.get(), cubeAll(ModBlocks.RAW_TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_ORE.get(), cubeAll(ModBlocks.MOLTEN_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.SOUL_ORE.get(), cubeAll(ModBlocks.SOUL_ORE.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_BLOCK.get(), cubeAll(ModBlocks.MOLTEN_GOLD_BLOCK.get()));

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
}
