package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DEEPSLATE_TITANIUM_ORE_PATCH = key("deepslate_titanium_ore_patch");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE = key("titanium_ore");
    public static final ResourceKey<PlacedFeature> MOLTEN_GOLD_ORE_PATCH = key("molten_gold_ore_patch");
    public static final ResourceKey<PlacedFeature> SOUL_ORE_PATCH = key("soul_ore_patch");

    // ===== 染梦树木 =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_TREE = key("dyedream_tree");
    public static final ResourceKey<PlacedFeature> DYEDREAM_TREE_COLD_SPRUCE = key("dyedream_tree_cold_spruce");
    public static final ResourceKey<PlacedFeature> DYEDREAM_TREE_DENSE = key("dyedream_tree_dense");
    public static final ResourceKey<PlacedFeature> PINK_MUSHROOM_TREE = key("pink_mushroom_tree");
    public static final ResourceKey<PlacedFeature> PINK_HUGE_MUSHROOM = key("pink_huge_mushroom");
    public static final ResourceKey<PlacedFeature> PINK_HUGE_MUSHROOM_SPARSE = key("pink_huge_mushroom_sparse");

    // ===== 冰晶 =====
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_PILLAR = key("dyedream_ice_pillar");
    public static final ResourceKey<PlacedFeature> DYEDREAM_PACKED_ICE_PILLAR = key("dyedream_packed_ice_pillar");
    public static final ResourceKey<PlacedFeature> SNOWY_WATER_POOL = key("snowy_water_pool");
    public static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER = key("freeze_top_layer");
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_STONE_BLOBS = key("dyedream_ice_stone_blobs");
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICE_BLOBS = key("dyedream_ice_blobs");
    public static final ResourceKey<PlacedFeature> DYEDREAM_PACKED_ICE_BLOBS = key("dyedream_packed_ice_blobs");
    public static final ResourceKey<PlacedFeature> VANILLA_ICE_BLOBS = key("vanilla_ice_blobs");
    public static final ResourceKey<PlacedFeature> VANILLA_PACKED_ICE_BLOBS = key("vanilla_packed_ice_blobs");
    public static final ResourceKey<PlacedFeature> CALCITE_BOULDER = key("calcite_boulder");
    public static final ResourceKey<PlacedFeature> CALCITE_SPIKE = key("calcite_spike");
    public static final ResourceKey<PlacedFeature> STONE_PILLAR_OCEAN = key("stone_pillar_ocean");
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICEBERG_PACKED = key("dyedream_iceberg_packed");
    public static final ResourceKey<PlacedFeature> DYEDREAM_ICEBERG_BLUE = key("dyedream_iceberg_blue");
    public static final ResourceKey<PlacedFeature> DYEDREAM_KELP_PATCH = key("dyedream_kelp_patch");

    // ===== 阴影维度植物 =====
    // 阴影真菌树 — 自然生成 + 骨粉催熟
    public static final ResourceKey<PlacedFeature> SHADOW_FUNGUS_TREE = key("shadow_fungus_tree");
    // 影芽 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_SPROUTS_PATCH = key("shadow_sprouts_patch");
    // 影蕨 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_FERN_PATCH = key("shadow_fern_patch");
    // 影短根 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_SHORT_ROOTS_PATCH = key("shadow_short_roots_patch");
    // 影根须 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_ROOTS_PATCH = key("shadow_roots_patch");
    // 影茎蕨 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_STEM_FERN_PATCH = key("shadow_stem_fern_patch");
    // 影菌 — 分散地表植被
    public static final ResourceKey<PlacedFeature> SHADOW_FUNGUS_PATCH = key("shadow_fungus_patch");
    // 白厄花 — 分散地表植被
    public static final ResourceKey<PlacedFeature> WHITE_ORCHID_FLOWER_PATCH = key("white_orchid_flower_patch");
    // 阴影残垣断壁 — 散落的破败墙体
    public static final ResourceKey<PlacedFeature> SHADOW_RUIN_WALL = key("shadow_ruin_wall");
    // 阴影锁链柱 — 交替朝向镂空矩形单元堆叠
    public static final ResourceKey<PlacedFeature> SHADOW_CHAIN_PILLAR = key("shadow_chain_pillar");
    /** 阴影锁链柱（海底变体）— 向下搜索至海底固体方块 */
    public static final ResourceKey<PlacedFeature> SHADOW_CHAIN_PILLAR_OCEAN = key("shadow_chain_pillar_ocean");
    // 暗影之手 — NBT 结构地物
    public static final ResourceKey<PlacedFeature> SHADOW_HAND = key("shadow_hand");
    // 阴影古墓 — NBT 结构地物（shadow_tomb_0/1 随机变体，下陷2格）
    public static final ResourceKey<PlacedFeature> SHADOW_TOMB = key("shadow_tomb");

    // ===== 地表植被 =====
    public static final ResourceKey<PlacedFeature> STEM_GRASS_PATCH = key("stem_grass_patch");
    public static final ResourceKey<PlacedFeature> TALL_STEM_GRASS_PATCH = key("tall_stem_grass_patch");
    public static final ResourceKey<PlacedFeature> PINK_MUSHROOM_PATCH = key("pink_mushroom_patch");
    public static final ResourceKey<PlacedFeature> TALL_PINK_MUSHROOM_PATCH = key("tall_pink_mushroom_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_COROLLA_PATCH = key("dyedream_corolla_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_COROLLA_PATCH_FLOWER_FIELD = key("dyedream_corolla_patch_flower_field");
    public static final ResourceKey<PlacedFeature> FLOATING_LIGHT_BALL = key("floating_light_ball");
    public static final ResourceKey<PlacedFeature> LIGHT_BALL_PATCH = key("light_ball_patch");
    public static final ResourceKey<PlacedFeature> CLOUD_CROP_PATCH = key("cloud_crop_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_LILY_PATCH = key("dyedream_lily_patch");
    public static final ResourceKey<PlacedFeature> DREAMING_LOTUS_PATCH = key("dreaming_lotus_patch");
    public static final ResourceKey<PlacedFeature> EDELWEISS_PATCH = key("edelweiss_patch");
    public static final ResourceKey<PlacedFeature> SINGULARITY_FERN_PATCH = key("singularity_fern_patch");
    public static final ResourceKey<PlacedFeature> LINHT_FLOWER_PATCH = key("linht_flower_patch");
    public static final ResourceKey<PlacedFeature> FLOWER_FIELD_FLOWERS = key("flower_field_flowers");

    // ===== 主世界植被 / 小石子 =====
    public static final ResourceKey<PlacedFeature> FOURLEAF_CLOVER_PATCH = key("fourleaf_clover_patch");
    public static final ResourceKey<PlacedFeature> GOLDENROD_PATCH = key("goldenrod_patch");
    public static final ResourceKey<PlacedFeature> FERRARIA_CRISPA_PATCH = key("ferraria_crispa_patch");
    public static final ResourceKey<PlacedFeature> MALVA_SINENSIS_CAVAN_PATCH = key("malva_sinensis_cavan_patch");
    public static final ResourceKey<PlacedFeature> EUSTOMA_PATCH = key("eustoma_patch");
    public static final ResourceKey<PlacedFeature> OATS_PATCH = key("oats_patch");
    public static final ResourceKey<PlacedFeature> RYE_PATCH = key("rye_patch");
    public static final ResourceKey<PlacedFeature> WHITE_COROLLA_CROP_PATCH = key("white_corolla_crop_patch");
    public static final ResourceKey<PlacedFeature> COTTON_CROP_PATCH = key("cotton_crop_patch");
    public static final ResourceKey<PlacedFeature> JUNGLE_SPORANGIUM_PATCH = key("jungle_sporangium_patch");
    public static final ResourceKey<PlacedFeature> REED_PATCH = key("reed_patch");
    public static final ResourceKey<PlacedFeature> PEBBLE_PATCH = key("pebble_patch");
    public static final ResourceKey<PlacedFeature> CALCITE_STALICRIPE = key("calcite_stalicripe");
    public static final ResourceKey<PlacedFeature> SMALL_CALCITE_STALICRIPE = key("small_calcite_stalicripe");
    public static final ResourceKey<PlacedFeature> DYEDREAM_MOSS_PATCH = key("dyedream_moss_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_VINE_PATCH = key("dyedream_vine_patch");

    // ===== 云团柱 =====
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_SMALL = key("cloud_pillar_small");
    public static final ResourceKey<PlacedFeature> CLOUD_PILLAR_LARGE = key("cloud_pillar_large");

    // ===== 水下装饰 =====
    public static final ResourceKey<PlacedFeature> CORAL_TREE_PATCH = key("coral_tree_patch");
    public static final ResourceKey<PlacedFeature> CORAL_CLAW_PATCH = key("coral_claw_patch");
    public static final ResourceKey<PlacedFeature> CORAL_MUSHROOM_PATCH = key("coral_mushroom_patch");
    public static final ResourceKey<PlacedFeature> SEA_PICKLE_PATCH = key("sea_pickle_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_SEAGRASS_PATCH = key("dyedream_seagrass_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_RIVER_CLAY = key("dyedream_river_clay");
    public static final ResourceKey<PlacedFeature> DYEDREAM_LILY_PAD_PATCH = key("dyedream_lily_pad");
    public static final ResourceKey<PlacedFeature> DYEDREAM_LOTUS_PATCH = key("dyedream_lotus");

    // ===== 洞穴 =====
    public static final ResourceKey<PlacedFeature> SMALL_DYEDREAM_BUD_PATCH = key("small_dyedream_bud_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_DYEDREAM_BUD_PATCH = key("medium_dyedream_bud_patch");
    public static final ResourceKey<PlacedFeature> LARGE_DYEDREAM_BUD_PATCH = key("large_dyedream_bud_patch");
    public static final ResourceKey<PlacedFeature> ICE_BUD_PATCH = key("ice_bud_patch");
    public static final ResourceKey<PlacedFeature> DYEDREAM_GEODE = key("dyedream_geode");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_MUSHROOM_TREE = key("lush_cave_mushroom_tree");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_MUSHROOM_PATCH = key("lush_cave_mushroom_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_TALL_MUSHROOM_PATCH = key("lush_cave_tall_mushroom_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_VINE_PATCH = key("lush_cave_vine_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_SHROOM_BLOCK_PATCH = key("lush_cave_shroom_block_patch");
    public static final ResourceKey<PlacedFeature> SMALL_PINK_MUSHROOM_PATCH = key("small_pink_mushroom_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_PINK_MUSHROOM_CURTAIN = key("lush_cave_pink_mushroom_curtain");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_STEM_GRASS_PATCH = key("lush_cave_stem_grass_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_TALL_STEM_GRASS_PATCH = key("lush_cave_tall_stem_grass_patch");
    public static final ResourceKey<PlacedFeature> LUSH_CAVE_FERN_PATCH = key("lush_cave_fern_patch");
    public static final ResourceKey<PlacedFeature> CALCITE_CONE_PATCH = key("calcite_cone_patch");
    public static final ResourceKey<PlacedFeature> CALCITE_CONE_CLUSTER_PATCH = key("calcite_cone_cluster_patch");

    // ===== 染梦维度矿石 =====
    public static final ResourceKey<PlacedFeature> AMBER_CANDY_ORE = key("amber_candy_ore");
    public static final ResourceKey<PlacedFeature> DYEDREAM_DUST_ORE = key("dyedream_dust_ore");
    public static final ResourceKey<PlacedFeature> DYEDREAM_QUARTZ_ORE = key("dyedream_quartz_ore");

    // ===== 染梦世界·方解石原版矿物 =====
    public static final ResourceKey<PlacedFeature> CALCITE_COAL_ORE_UPPER = key("calcite_coal_ore_upper");
    public static final ResourceKey<PlacedFeature> CALCITE_COAL_ORE_LOWER = key("calcite_coal_ore_lower");
    public static final ResourceKey<PlacedFeature> CALCITE_IRON_ORE_UPPER = key("calcite_iron_ore_upper");
    public static final ResourceKey<PlacedFeature> CALCITE_IRON_ORE_MIDDLE = key("calcite_iron_ore_middle");
    public static final ResourceKey<PlacedFeature> CALCITE_IRON_ORE_SMALL = key("calcite_iron_ore_small");
    public static final ResourceKey<PlacedFeature> CALCITE_COPPER_ORE = key("calcite_copper_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_COPPER_ORE_LARGE = key("calcite_copper_ore_large");
    public static final ResourceKey<PlacedFeature> CALCITE_GOLD_ORE = key("calcite_gold_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_GOLD_ORE_LOWER = key("calcite_gold_ore_lower");
    public static final ResourceKey<PlacedFeature> CALCITE_REDSTONE_ORE = key("calcite_redstone_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_REDSTONE_ORE_LOWER = key("calcite_redstone_ore_lower");
    public static final ResourceKey<PlacedFeature> CALCITE_LAPIS_ORE = key("calcite_lapis_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_LAPIS_ORE_BURIED = key("calcite_lapis_ore_buried");
    public static final ResourceKey<PlacedFeature> CALCITE_DIAMOND_ORE = key("calcite_diamond_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_DIAMOND_ORE_LARGE = key("calcite_diamond_ore_large");
    public static final ResourceKey<PlacedFeature> CALCITE_DIAMOND_ORE_BURIED = key("calcite_diamond_ore_buried");
    public static final ResourceKey<PlacedFeature> CALCITE_EMERALD_ORE = key("calcite_emerald_ore");

    private static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_FREEZE_TOP_LAYER_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "freeze_top_layer"));
    private static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_KELP_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "kelp"));
    private static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICEBERG_PACKED_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "iceberg_packed"));
    private static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICEBERG_BLUE_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "iceberg_blue"));

    /** 限制植物只能生成在染梦地表方块（染梦草方块/染梦泥土）上 */
    private static final PlacementModifier ON_DYEDREAM_GROUND = BlockPredicateFilter.forPredicate(
            BlockPredicate.matchesBlocks(
                    Direction.DOWN.getNormal(),
                    ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                    ModBlocks.DYEDREAM_DIRT.get()));

    /** 限制植物/地物只能生成在灯影地表方块（阴影菌岩/影之石）上 */
    private static final PlacementModifier ON_SHADOW_GROUND = BlockPredicateFilter.forPredicate(
            BlockPredicate.matchesBlocks(
                    Direction.DOWN.getNormal(),
                    ModBlocks.SHADOW_NYLIUM.get(),
                    ModBlocks.SHADOW_STONE.get()));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> cf = context.lookup(Registries.CONFIGURED_FEATURE);

        // ===== 染梦树木 =====
        context.register(DYEDREAM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_TREE),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        PlacementUtils.filteredByBlockSurvival(ModBlocks.DYEDREAM_SAPLING.get()))));

        context.register(DYEDREAM_TREE_COLD_SPRUCE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_TREE_COLD_SPRUCE),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        PlacementUtils.filteredByBlockSurvival(ModBlocks.DYEDREAM_SAPLING.get()))));

        context.register(DYEDREAM_TREE_DENSE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_TREE),
                List.of(CountPlacement.of(12), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        PlacementUtils.filteredByBlockSurvival(ModBlocks.DYEDREAM_SAPLING.get()))));

        context.register(PINK_MUSHROOM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_TREE),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(
                                Direction.DOWN.getNormal(),
                                ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                                ModBlocks.DYEDREAM_DIRT.get())))));

        context.register(PINK_HUGE_MUSHROOM, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_HUGE_MUSHROOM),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_DYEDREAM_GROUND)));

        context.register(PINK_HUGE_MUSHROOM_SPARSE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_HUGE_MUSHROOM),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_DYEDREAM_GROUND)));

        // ===== 冰晶 =====
        context.register(DYEDREAM_ICE_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(DYEDREAM_PACKED_ICE_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_PACKED_ICE_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(SNOWY_WATER_POOL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SNOWY_WATER_POOL),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(FREEZE_TOP_LAYER, new PlacedFeature(
                cf.getOrThrow(VANILLA_FREEZE_TOP_LAYER_CF), List.of()));

        context.register(DYEDREAM_ICE_STONE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_STONE_BLOBS),
                List.of(CountPlacement.of(12), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

        context.register(DYEDREAM_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

        context.register(DYEDREAM_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_PACKED_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

        context.register(VANILLA_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

        context.register(VANILLA_PACKED_ICE_BLOBS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.VANILLA_PACKED_ICE_BLOBS),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)))));

        context.register(CALCITE_BOULDER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(CALCITE_SPIKE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_SPIKE),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(STONE_PILLAR_OCEAN, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.STONE_PILLAR_OCEAN),
                List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(DYEDREAM_ICEBERG_PACKED, new PlacedFeature(
                cf.getOrThrow(VANILLA_ICEBERG_PACKED_CF),
                List.of(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(DYEDREAM_ICEBERG_BLUE, new PlacedFeature(
                cf.getOrThrow(VANILLA_ICEBERG_BLUE_CF),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(DYEDREAM_KELP_PATCH, new PlacedFeature(
                cf.getOrThrow(VANILLA_KELP_CF),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        // ===== 阴影维度植物 =====
        // 阴影真菌树
        context.register(SHADOW_FUNGUS_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_FUNGUS_TREE),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 阴影残垣断壁 — 每 3 区块 1 个，仅灯影地表
        context.register(SHADOW_RUIN_WALL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_RUIN_WALL),
                List.of(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 阴影锁链柱 — 地表结构，平均每 16 区块 1 个，仅灯影地表
        context.register(SHADOW_CHAIN_PILLAR, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_CHAIN_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 阴影锁链柱（海底变体）— 使用 OCEAN_FLOOR_WG 定位海底
        context.register(SHADOW_CHAIN_PILLAR_OCEAN, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_CHAIN_PILLAR),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 暗影之手 — 原作 shadow_hand_0 结构
        context.register(SHADOW_HAND, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_HAND),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 阴影古墓 — shadow_tomb_0/1 随机变体，下陷2格，仅阴影古迹
        context.register(SHADOW_TOMB, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_TOMB),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // 影芽 — WORLD_SURFACE_WG，比染梦茎草更稀疏
        context.register(SHADOW_SPROUTS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_SPROUTS_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 影蕨 — WORLD_SURFACE_WG，比染梦茎草更稀疏
        context.register(SHADOW_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_FERN_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 阴影矮菌索 — WORLD_SURFACE_WG
        context.register(SHADOW_SHORT_ROOTS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_SHORT_ROOTS_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 阴影菌索 — WORLD_SURFACE_WG
        context.register(SHADOW_ROOTS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_ROOTS_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 影茎蕨 — WORLD_SURFACE_WG
        context.register(SHADOW_STEM_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_STEM_FERN_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 影菌 — WORLD_SURFACE_WG
        context.register(SHADOW_FUNGUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SHADOW_FUNGUS_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));
        // 白厄花 — WORLD_SURFACE_WG
        context.register(WHITE_ORCHID_FLOWER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.WHITE_ORCHID_FLOWER_PATCH),
                List.of(CountPlacement.of(1), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_SHADOW_GROUND,
                        BiomeFilter.biome())));

        // ===== 地表植被 =====
        context.register(STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.STEM_GRASS_PATCH),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(TALL_STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_STEM_GRASS_PATCH),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(PINK_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_PATCH),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(TALL_PINK_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_PINK_MUSHROOM_PATCH),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(DYEDREAM_COROLLA_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_COROLLA_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(DYEDREAM_COROLLA_PATCH_FLOWER_FIELD, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_COROLLA_PATCH_DENSE),
                List.of(CountPlacement.of(3), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(FLOATING_LIGHT_BALL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.FLOATING_LIGHT_BALL),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(LIGHT_BALL_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LIGHT_BALL_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(CLOUD_CROP_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_CROP_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(DYEDREAM_LILY_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LILY_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(DREAMING_LOTUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DREAMING_LOTUS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(EDELWEISS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.EDELWEISS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(SINGULARITY_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SINGULARITY_FERN_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(LINHT_FLOWER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LINHT_FLOWER_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        context.register(FLOWER_FIELD_FLOWERS, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.FLOWER_FIELD_FLOWERS),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // ===== 主世界植被 / 小石子 =====
        context.register(FOURLEAF_CLOVER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.FOURLEAF_CLOVER_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(GOLDENROD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.GOLDENROD_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(FERRARIA_CRISPA_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.FERRARIA_CRISPA_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(MALVA_SINENSIS_CAVAN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.MALVA_SINENSIS_CAVAN_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(EUSTOMA_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.EUSTOMA_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(OATS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.OATS_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(RYE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.RYE_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(WHITE_COROLLA_CROP_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.WHITE_COROLLA_CROP_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(COTTON_CROP_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.COTTON_CROP_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(JUNGLE_SPORANGIUM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.JUNGLE_SPORANGIUM_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(REED_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.REED_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(PEBBLE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PEBBLE_PATCH),
                List.of(InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                        BiomeFilter.biome())));

        context.register(CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(SMALL_CALCITE_STALICRIPE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SMALL_CALCITE_STALICRIPE),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(DYEDREAM_MOSS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_MOSS_PATCH),
                List.of(CountPlacement.of(10), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(DYEDREAM_VINE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_VINE_PATCH),
                List.of(RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        ON_DYEDREAM_GROUND)));

        // ===== 云团柱 =====
        context.register(CLOUD_PILLAR_SMALL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_PILLAR_SMALL),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        context.register(CLOUD_PILLAR_LARGE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CLOUD_PILLAR_LARGE),
                List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.MOTION_BLOCKING))));

        // ===== 水下装饰 =====
        context.register(CORAL_TREE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_TREE_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(CORAL_CLAW_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_CLAW_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(CORAL_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CORAL_MUSHROOM_PATCH),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(SEA_PICKLE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SEA_PICKLE_PATCH),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR))));

        context.register(DYEDREAM_SEAGRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_SEAGRASS_PATCH),
                List.of(CountPlacement.of(64), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(59)))));

        context.register(DYEDREAM_RIVER_CLAY, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_RIVER_CLAY),
                List.of(InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_TOP_SOLID,
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)))));

        context.register(DYEDREAM_LILY_PAD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LILY_PAD_PATCH),
                List.of(CountPlacement.of(7), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        context.register(DYEDREAM_LOTUS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_LOTUS_PATCH),
                List.of(CountPlacement.of(6), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
                        onHeightmap(Heightmap.Types.WORLD_SURFACE_WG))));

        // ===== 洞穴 =====
        context.register(SMALL_DYEDREAM_BUD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SMALL_DYEDREAM_BUD_PATCH),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        context.register(MEDIUM_DYEDREAM_BUD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.MEDIUM_DYEDREAM_BUD_PATCH),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        context.register(LARGE_DYEDREAM_BUD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LARGE_DYEDREAM_BUD_PATCH),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));
        context.register(ICE_BUD_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.ICE_BUD_PATCH),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));

        context.register(DYEDREAM_GEODE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_GEODE),
                List.of(RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)),
                        BiomeFilter.biome())));

        context.register(LUSH_CAVE_MUSHROOM_TREE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LUSH_CAVE_MUSHROOM),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(SMALL_PINK_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SMALL_PINK_MUSHROOM),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(LUSH_CAVE_PINK_MUSHROOM_CURTAIN, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_CURTAIN),
                caveCeilingPlacement(24)));

        context.register(LUSH_CAVE_STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.STEM_GRASS_PATCH),
                caveFloorPlacement(40)));

        context.register(LUSH_CAVE_TALL_STEM_GRASS_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_STEM_GRASS_PATCH),
                caveFloorPlacement(30)));

        context.register(LUSH_CAVE_FERN_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SINGULARITY_FERN_PATCH),
                caveFloorPlacement(30)));

        context.register(LUSH_CAVE_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.PINK_MUSHROOM_PATCH),
                caveFloorPlacement(40)));

        context.register(LUSH_CAVE_TALL_MUSHROOM_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TALL_PINK_MUSHROOM_PATCH),
                caveFloorPlacement(20)));

        context.register(LUSH_CAVE_VINE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_VINE_PATCH),
                caveFloorPlacement(40)));

        context.register(LUSH_CAVE_SHROOM_BLOCK_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.LUSH_CAVE_SHROOM_BLOCK_PATCH),
                caveFloorPlacement(64)));

        context.register(CALCITE_CONE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_CONE),
                List.of(CountPlacement.of(UniformInt.of(128, 192)), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));

        context.register(CALCITE_CONE_CLUSTER_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_CONE_CLUSTER),
                List.of(CountPlacement.of(UniformInt.of(12, 28)), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)))));

        // ===== 染梦维度矿石 =====
        context.register(TITANIUM_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE),
                List.of(CountPlacement.of(7), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

        context.register(AMBER_CANDY_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.AMBER_CANDY_ORE),
                List.of(CountPlacement.of(10), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(DYEDREAM_DUST_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_DUST_ORE),
                List.of(CountPlacement.of(5), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        context.register(DYEDREAM_QUARTZ_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DYEDREAM_QUARTZ_ORE),
                List.of(CountPlacement.of(18), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-60), VerticalAnchor.absolute(320)))));

        // ===== 染梦世界·方解石原版矿物 =====
        context.register(CALCITE_COAL_ORE_UPPER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_COAL_ORE),
                List.of(CountPlacement.of(30), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(136), VerticalAnchor.top()))));
        context.register(CALCITE_COAL_ORE_LOWER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_COAL_ORE_BURIED),
                List.of(CountPlacement.of(20), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(192)))));

        context.register(CALCITE_IRON_ORE_UPPER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_IRON_ORE),
                List.of(CountPlacement.of(90), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384)))));
        context.register(CALCITE_IRON_ORE_MIDDLE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_IRON_ORE),
                List.of(CountPlacement.of(10), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
        context.register(CALCITE_IRON_ORE_SMALL, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_IRON_ORE_SMALL),
                List.of(CountPlacement.of(10), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72)))));

        context.register(CALCITE_COPPER_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_COPPER_ORE),
                List.of(CountPlacement.of(16), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));
        context.register(CALCITE_COPPER_ORE_LARGE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_COPPER_ORE_LARGE),
                List.of(CountPlacement.of(16), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));

        context.register(CALCITE_GOLD_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_GOLD_ORE_BURIED),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)))));
        context.register(CALCITE_GOLD_ORE_LOWER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_GOLD_ORE_BURIED),
                List.of(CountPlacement.of(UniformInt.of(0, 1)), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48)))));

        context.register(CALCITE_REDSTONE_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_REDSTONE_ORE),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15)))));
        context.register(CALCITE_REDSTONE_ORE_LOWER, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_REDSTONE_ORE),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32)))));

        context.register(CALCITE_LAPIS_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_LAPIS_ORE),
                List.of(CountPlacement.of(2), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32)))));
        context.register(CALCITE_LAPIS_ORE_BURIED, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_LAPIS_ORE_BURIED),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64)))));

        context.register(CALCITE_DIAMOND_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_DIAMOND_ORE_SMALL),
                List.of(CountPlacement.of(7), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
        context.register(CALCITE_DIAMOND_ORE_LARGE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_DIAMOND_ORE_LARGE),
                List.of(RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
        context.register(CALCITE_DIAMOND_ORE_BURIED, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_DIAMOND_ORE_BURIED),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

        context.register(CALCITE_EMERALD_ORE, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.CALCITE_EMERALD_ORE),
                List.of(CountPlacement.of(100), InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)))));

        // ===== 主世界/下界矿石 =====
        context.register(MOLTEN_GOLD_ORE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.MOLTEN_GOLD_ORE_PATCH),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64)))));
        context.register(SOUL_ORE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.SOUL_ORE_PATCH),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128)))));
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, new PlacedFeature(
                cf.getOrThrow(ModConfiguredFeatures.DEEPSLATE_TITANIUM_ORE_PATCH),
                List.of(CountPlacement.of(8), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)))));
    }

    /** 洞穴地面放置：从随机锚点向下扫描找洞底（空气→实心），再上移 1 格落在洞底空气格（镜像原版 LUSH_CAVES_VEGETATION） */
    private static List<PlacementModifier> caveFloorPlacement(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                ON_DYEDREAM_GROUND);
    }

    /** 洞穴天花板放置：从随机锚点向上扫描找洞顶（空气→实心），再下移 1 格落在洞顶下方空气格，用于挂在天花板下的地物 */
    private static List<PlacementModifier> caveCeilingPlacement(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)));
    }

    private static HeightmapPlacement onHeightmap(Heightmap.Types type) {
        return HeightmapPlacement.onHeightmap(type);
    }

    private static ResourceKey<PlacedFeature> key(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModPlacedFeatures() {
    }
}
