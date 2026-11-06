package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DEEPSLATE_TITANIUM_ORE_PATCH = key("deepslate_titanium_ore_patch");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE = key("titanium_ore");
    public static final ResourceKey<PlacedFeature> MOLTEN_GOLD_ORE_PATCH = key("molten_gold_ore_patch");
    public static final ResourceKey<PlacedFeature> SOUL_ORE_PATCH = key("soul_ore_patch");
    // 染梦草方块骨粉植被筛选引用；对应 placed feature 注册随 T16 迁入。
    public static final ResourceKey<PlacedFeature> CALCITE_STALICRIPE = key("calcite_stalicripe");
    public static final ResourceKey<PlacedFeature> SMALL_CALCITE_STALICRIPE = key("small_calcite_stalicripe");
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_SMALL = key("cloud_pillar_small");
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_LARGE = key("cloud_pillar_large");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(MOLTEN_GOLD_ORE_PATCH, new PlacedFeature(
                features.getOrThrow(ModConfiguredFeatures.MOLTEN_GOLD_ORE_PATCH),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)))));
        context.register(SOUL_ORE_PATCH, new PlacedFeature(
                features.getOrThrow(ModConfiguredFeatures.SOUL_ORE_PATCH),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, new PlacedFeature(
                features.getOrThrow(ModConfiguredFeatures.DEEPSLATE_TITANIUM_ORE_PATCH),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))));
        context.register(TITANIUM_ORE, new PlacedFeature(features.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE),
                List.of(CountPlacement.of(7), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    }

    private static ResourceKey<PlacedFeature> key(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModPlacedFeatures() {
    }
}
