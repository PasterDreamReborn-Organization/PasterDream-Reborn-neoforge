package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFeatures;
import com.pasterdream.pasterdreammod.worldgen.feature.LightBallTreeDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkShroomlightTreeDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.foliageplacers.DreamFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANIUM_ORE_PATCH = key("deepslate_titanium_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE = key("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_GOLD_ORE_PATCH = key("molten_gold_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_ORE_PATCH = key("soul_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_TREE = key("pink_mushroom_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_HUGE_MUSHROOM = key("pink_huge_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE = key("dyedream_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_FUNGUS_TREE = key("shadow_fungus_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(DYEDREAM_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LOG.get()),
                        new ForkingTrunkPlacer(7, 2, 2),
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LEAVES.get()),
                        new DreamFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 0.16666667F, 0.33333334F),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .decorators(List.of(LightBallTreeDecorator.INSTANCE))
                        .ignoreVines()
                        .build()));

        BlockState pinkMushroomCap = ModBlocks.PINK_MUSHROOM_BLOCK.get().defaultBlockState()
                .setValue(HugeMushroomBlock.DOWN, false);

        context.register(PINK_MUSHROOM_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_STEM.get()),
                        new MegaJungleTrunkPlacer(7, 2, 12),
                        BlockStateProvider.simple(pinkMushroomCap),
                        new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .decorators(List.of(PinkShroomlightTreeDecorator.INSTANCE))
                        .ignoreVines()
                        .dirt(BlockStateProvider.simple(ModBlocks.DYEDREAM_DIRT.get()))
                        .build()));

        context.register(PINK_HUGE_MUSHROOM, new ConfiguredFeature<>(ModFeatures.PINK_HUGE_MUSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(pinkMushroomCap),
                        BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM_STEM.get()),
                        3)));

        var targets = List.of(Blocks.DEEPSLATE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE).stream()
                .map(block -> OreConfiguration.target(new BlockMatchTest(block),
                        ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState())).toList();
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, 8, 0f)));
        context.register(MOLTEN_GOLD_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK),
                        ModBlocks.MOLTEN_GOLD_ORE.get().defaultBlockState(), 16, 0f)));
        context.register(SOUL_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.SOUL_SOIL),
                        ModBlocks.SOUL_ORE.get().defaultBlockState(), 16, 0f)));
        // 染梦维度尚未迁入，先保留该地物定义，不挂入主世界。
        context.register(TITANIUM_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.CALCITE),
                        ModBlocks.TITANIUM_ORE.get().defaultBlockState(), 8, 0f)));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModConfiguredFeatures() {
    }
}
