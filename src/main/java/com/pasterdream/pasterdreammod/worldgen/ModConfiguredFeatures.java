package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANIUM_ORE_PATCH = key("deepslate_titanium_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE = key("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_GOLD_ORE_PATCH = key("molten_gold_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_ORE_PATCH = key("soul_ore_patch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
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
