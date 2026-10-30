package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), cubeAll(ModBlocks.DYEDREAM_QUARTZ_ORE.get()));
        simpleBlockWithItem(ModBlocks.DYEDREAM_DUST_ORE.get(), cubeAll(ModBlocks.DYEDREAM_DUST_ORE.get()));
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
        simpleBlockWithItem(ModBlocks.TITANIUM_ORE.get(), cubeAll(ModBlocks.TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_TITANIUM_ORE.get()));
        simpleBlockWithItem(ModBlocks.RAW_TITANIUM_BLOCK.get(), cubeAll(ModBlocks.RAW_TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_ORE.get(), cubeAll(ModBlocks.MOLTEN_GOLD_ORE.get()));
        simpleBlockWithItem(ModBlocks.MOLTEN_GOLD_BLOCK.get(), cubeAll(ModBlocks.MOLTEN_GOLD_BLOCK.get()));
    }

    private void blockItem(net.neoforged.neoforge.registries.DeferredBlock<?> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(PasterDreamMod.MOD_ID + ":block/" + block.getId().getPath()));
    }
}
