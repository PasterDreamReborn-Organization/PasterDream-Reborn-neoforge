package com.pasterdream.pasterdreammod.worldgen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFeatures;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import com.pasterdream.pasterdreammod.worldgen.feature.LightBallTreeDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkShroomlightTreeDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.WindMoorDroopingDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.foliageplacers.DreamFoliagePlacer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import com.pasterdream.pasterdreammod.worldgen.feature.SlumberPalmTreeConfiguration;
import com.pasterdream.pasterdreammod.worldgen.feature.WindJourneyWaterPoolConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANIUM_ORE_PATCH = key("deepslate_titanium_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE = key("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_GOLD_ORE_PATCH = key("molten_gold_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_ORE_PATCH = key("soul_ore_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_TREE = key("pink_mushroom_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_HUGE_MUSHROOM = key("pink_huge_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE = key("dyedream_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SLUMBER_PALM_TREE = key("slumber_palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE_COLD_SPRUCE = key("dyedream_tree_cold_spruce");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_FUNGUS_TREE = key("shadow_fungus_tree");
    // 影芽 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_SPROUTS_PATCH = key("shadow_sprouts_patch");
    // 影蕨 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_FERN_PATCH = key("shadow_fern_patch");
    // 阴影矮菌索 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_SHORT_ROOTS_PATCH = key("shadow_short_roots_patch");
    // 阴影菌索 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_ROOTS_PATCH = key("shadow_roots_patch");
    // 影茎蕨 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_STEM_FERN_PATCH = key("shadow_stem_fern_patch");
    // 影菌 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_FUNGUS_PATCH = key("shadow_fungus_patch");
    // 白厄花 — 分散地表植被
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_ORCHID_FLOWER_PATCH = key("white_orchid_flower_patch");
    // 阴影残垣断壁 — 散落的破败墙体
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_RUIN_WALL = key("shadow_ruin_wall");
    // 阴影锁链柱 — 交替朝向的镂空矩形单元堆叠
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_CHAIN_PILLAR = key("shadow_chain_pillar");
    // 暗影之手 — NBT 结构物 Feature
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_HAND = key("shadow_hand");
    // 阴影古墓 — NBT 结构物 Feature（shadow_tomb_0/1 随机变体）
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW_TOMB = key("shadow_tomb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_WIND_MOOR_TREE = key("small_wind_moor_tree");

    // ===== 风之旅途 =====
    // 凝风矿 — 原作 congeal_wind_ore，替换 thick_cloud
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONGEAL_WIND_ORE = key("congeal_wind_ore");
    // 破风骑士水晶矿 — 原作 windrunner_crystal_ore，替换 cyan_stone
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_RUNNER_CRYSTAL_ORE = key("wind_runner_crystal_ore");
    // 风之旅途云团（高位）— 原作 ground_feature_wind_journey_0
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_CLOUD_PATCH = key("wind_journey_cloud_patch");
    // 风之旅途云团（低位）— 原作 ground_feature_wind_journey_2
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_CLOUD_PATCH_LOW = key("wind_journey_cloud_patch_low");
    // 风之旅途水池 — 原作 ground_feature_wind_journey_1
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_WATER_POOL = key("wind_journey_water_pool");
    // 风之旅途小石子 — 原作 ground_feature_wind_journey_3
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_PEBBLE_PATCH = key("wind_journey_pebble_patch");
    // 风之旅途萤火虫巢 — 原作 ground_feature_wind_journey_4
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_FIREFLY_NEST = key("wind_journey_firefly_nest");
    // 风之旅途苍青苔石团块 — 原作 ground_feature_wind_journey_5（替换泥）
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_CYAN_MOSS_STONE_BLOB = key("wind_journey_cyan_moss_stone_blob");
    // 风之旅途小石堆 — 原作 ground_feature_wind_journey_6
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_SMALL_STONE_SPIRIT = key("wind_journey_small_stone_spirit");
    // 风之旅途茸毛苔 — 原作 grass_13
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_HAIRY_MOSS_PATCH = key("wind_journey_hairy_moss_patch");
    // 风之旅途斩风草 — 原作 grass_14
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_CLEAVING_GRASS_PATCH = key("wind_journey_cleaving_grass_patch");
    // 风之旅途风羽草 — 原作 grass_15
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_FEATHER_GRASS_PATCH = key("wind_journey_feather_grass_patch");
    // 风之旅途风岛芦苇 — 原作 flower_18
    public static final ResourceKey<ConfiguredFeature<?, ?>> WIND_JOURNEY_REED_PATCH = key("wind_journey_reed_patch");

    // ===== 染梦冰晶 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_PILLAR = key("dyedream_ice_pillar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_PACKED_ICE_PILLAR = key("dyedream_packed_ice_pillar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWY_WATER_POOL = key("snowy_water_pool");

    // ===== 团块 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_STONE_BLOBS = key("dyedream_ice_stone_blobs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_SPIKE = key("calcite_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_CONE = key("calcite_cone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_CONE_CLUSTER = key("calcite_cone_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_CAVE_SHROOM_BLOCK_PATCH = key("lush_cave_shroom_block_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUSH_CAVE_MUSHROOM = key("lush_cave_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_PINK_MUSHROOM = key("small_pink_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_CURTAIN = key("pink_mushroom_curtain");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_PILLAR_OCEAN = key("stone_pillar_ocean");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_BOULDER = key("calcite_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_ICE_BLOBS = key("dyedream_ice_blobs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_PACKED_ICE_BLOBS = key("dyedream_packed_ice_blobs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ICE_BLOBS = key("vanilla_ice_blobs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_PACKED_ICE_BLOBS = key("vanilla_packed_ice_blobs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_MOSS_PATCH = key("dyedream_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_VINE_PATCH = key("dyedream_vine_patch");

    // ===== 云团柱 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_PILLAR_SMALL = key("cloud_pillar_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_PILLAR_LARGE = key("cloud_pillar_large");

    // ===== 水下装饰 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_TREE_PATCH = key("coral_tree_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_CLAW_PATCH = key("coral_claw_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORAL_MUSHROOM_PATCH = key("coral_mushroom_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEA_PICKLE_PATCH = key("sea_pickle_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_SEAGRASS_PATCH = key("dyedream_seagrass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_RIVER_CLAY = key("dyedream_river_clay");

    // ===== 洞穴晶芽 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_DYEDREAM_BUD_PATCH = key("small_dyedream_bud_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_DYEDREAM_BUD_PATCH = key("medium_dyedream_bud_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DYEDREAM_BUD_PATCH = key("large_dyedream_bud_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_BUD_PATCH = key("ice_bud_patch");

    // ===== 染梦晶洞 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_GEODE = key("dyedream_geode");

    // ===== 植被 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> STEM_GRASS_PATCH = key("stem_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_STEM_GRASS_PATCH = key("tall_stem_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_COROLLA_PATCH = key("dyedream_corolla_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_COROLLA_PATCH_DENSE = key("dyedream_corolla_patch_dense");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_LIGHT_BALL = key("floating_light_ball");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHT_BALL_PATCH = key("light_ball_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_CROP_PATCH = key("cloud_crop_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LILY_PATCH = key("dyedream_lily_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_MUSHROOM_PATCH = key("pink_mushroom_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_PINK_MUSHROOM_PATCH = key("tall_pink_mushroom_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DREAMING_LOTUS_PATCH = key("dreaming_lotus_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELWEISS_PATCH = key("edelweiss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGULARITY_FERN_PATCH = key("singularity_fern_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LINHT_FLOWER_PATCH = key("linht_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FIELD_FLOWERS = key("flower_field_flowers");

    // ===== 主世界植被 / 小石子 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOURLEAF_CLOVER_PATCH = key("fourleaf_clover_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDENROD_PATCH = key("goldenrod_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERRARIA_CRISPA_PATCH = key("ferraria_crispa_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALVA_SINENSIS_CAVAN_PATCH = key("malva_sinensis_cavan_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EUSTOMA_PATCH = key("eustoma_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OATS_PATCH = key("oats_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RYE_PATCH = key("rye_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_COROLLA_CROP_PATCH = key("white_corolla_crop_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COTTON_CROP_PATCH = key("cotton_crop_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_SPORANGIUM_PATCH = key("jungle_sporangium_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REED_PATCH = key("reed_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEBBLE_PATCH = key("pebble_patch");

    // ===== 方解石笋 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_STALICRIPE = key("calcite_stalicripe");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_CALCITE_STALICRIPE = key("small_calcite_stalicripe");

    // ===== 染梦维度矿石 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_CANDY_ORE = key("amber_candy_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_DUST_ORE = key("dyedream_dust_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_QUARTZ_ORE = key("dyedream_quartz_ore");

    // ===== 染梦世界·方解石原版矿物 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_COAL_ORE = key("calcite_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_COAL_ORE_BURIED = key("calcite_coal_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_IRON_ORE = key("calcite_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_IRON_ORE_SMALL = key("calcite_iron_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_COPPER_ORE = key("calcite_copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_COPPER_ORE_LARGE = key("calcite_copper_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_GOLD_ORE_BURIED = key("calcite_gold_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_REDSTONE_ORE = key("calcite_redstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_LAPIS_ORE = key("calcite_lapis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_LAPIS_ORE_BURIED = key("calcite_lapis_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_DIAMOND_ORE_SMALL = key("calcite_diamond_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_DIAMOND_ORE_LARGE = key("calcite_diamond_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_DIAMOND_ORE_BURIED = key("calcite_diamond_ore_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_EMERALD_ORE = key("calcite_emerald_ore");

    // ===== 染梦睡莲 / 莲花 =====
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LILY_PAD_PATCH = key("dyedream_lily_pad");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_LOTUS_PATCH = key("dyedream_lotus");

    private static final RuleTest TARGET_CALCITE = new BlockMatchTest(Blocks.CALCITE);
    private static final List<RuleTest> TARGET_CALCITE_AND_STONES = List.of(
            new BlockMatchTest(Blocks.CALCITE),
            new BlockMatchTest(Blocks.STONE),
            new BlockMatchTest(Blocks.GRANITE),
            new BlockMatchTest(Blocks.DIORITE),
            new BlockMatchTest(Blocks.ANDESITE)
    );

    private static final List<Block> DYEDREAM_BUD_GROUND = List.of(
            ModBlocks.DYEDREAM_DIRT.get(),
            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
            Blocks.CALCITE,
            ModBlocks.DYEDREAM_BUDDING_BLOCK.get()
    );

    private static final List<Block> ICE_BUD_GROUND = List.of(
            ModBlocks.DYEDREAM_DIRT.get(),
            ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
            ModBlocks.ICE_STONE.get(),
            Blocks.CALCITE
    );

    /** 风之旅途萤火虫巢/小石堆地面 — 仅苍青岩/苍青苔岩 */
    private static final List<Block> WIND_JOURNEY_GROUND = List.of(
            ModBlocks.CYAN_STONE.get(),
            ModBlocks.CYAN_MOSS_STONE.get()
    );

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

        // 眠椰树 — GT 椰子树风格（参数可经 JSON 调整）
        context.register(SLUMBER_PALM_TREE, new ConfiguredFeature<>(ModFeatures.SLUMBER_PALM_TREE.get(),
                new SlumberPalmTreeConfiguration(
                        UniformInt.of(8, 12),  // trunk_height：主干 8~12 格（GT 同款）
                        2,                     // branch_count：叉开时为 2 根枝
                        0.4,                   // fork_chance：40% 概率叉开，否则单干
                        UniformInt.of(3, 4),   // branch_length：分叉时每条枝 3~4 格
                        0.5,                   // branch_spread：分叉时每格向外倾斜 0.5 格
                        UniformInt.of(3, 4),   // arm_length：每条分支上树冠星臂 3~4 格
                        8,                     // arm_count：每条树冠 8 条星臂（GT 同款）
                        2,                     // max_droop：臂末最多下垂 2 格
                        false)));              // center_fill：不填充中心（GT 同款）

        context.register(DYEDREAM_TREE_COLD_SPRUCE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LOG.get()),
                        new StraightTrunkPlacer(9, 2, 2),
                        BlockStateProvider.simple(ModBlocks.DYEDREAM_LEAVES.get()),
                        new SpruceFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(3)),
                        new TwoLayersFeatureSize(2, 0, 2))
                        .ignoreVines()
                        .build()));

        // 小型风泊树 —— 风泊乔木：分叉树干 + 风泊树叶，悬挂无花果藤/风泊树叶
        context.register(SMALL_WIND_MOOR_TREE, new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.WIND_MOOR_LOG.get()),
                        new ForkingTrunkPlacer(7, 2, 2),
                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(ModBlocks.WIND_MOOR_LEAVES_0.get().defaultBlockState(), 1)
                                .add(ModBlocks.WIND_MOOR_LEAVES_1.get().defaultBlockState(), 1)),
                        new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .decorators(List.of(WindMoorDroopingDecorator.INSTANCE))
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

        context.register(DYEDREAM_ICE_PILLAR, new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(new BlockColumnConfiguration.Layer(
                                UniformInt.of(4, 11),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_ICE.get())
                        )),
                        Direction.UP,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        false
                )));

        context.register(DYEDREAM_PACKED_ICE_PILLAR, new ConfiguredFeature<>(Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(new BlockColumnConfiguration.Layer(
                                UniformInt.of(4, 11),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_PACKED_ICE.get())
                        )),
                        Direction.UP,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        false
                )));

        context.register(CALCITE_SPIKE, new ConfiguredFeature<>(ModFeatures.CALCITE_SPIKE.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(CALCITE_CONE, new ConfiguredFeature<>(ModFeatures.CALCITE_CONE.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(CALCITE_CONE_CLUSTER, new ConfiguredFeature<>(ModFeatures.CALCITE_CONE_CLUSTER.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(LUSH_CAVE_MUSHROOM, new ConfiguredFeature<>(ModFeatures.LUSH_CAVE_MUSHROOM.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(SMALL_PINK_MUSHROOM, new ConfiguredFeature<>(ModFeatures.SMALL_PINK_MUSHROOM.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(PINK_MUSHROOM_CURTAIN, new ConfiguredFeature<>(ModFeatures.PINK_MUSHROOM_CURTAIN.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(LUSH_CAVE_SHROOM_BLOCK_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 8, 3,
                        simpleBlockOnDyedreamGround(new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.PINK_SHROOMLIGHT.get().defaultBlockState(), 2)
                                        .add(ModBlocks.PINK_MUSHROOM_BLOCK.get().defaultBlockState(), 4))))));

        context.register(STONE_PILLAR_OCEAN, new ConfiguredFeature<>(ModFeatures.STONE_PILLAR.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(SNOWY_WATER_POOL, new ConfiguredFeature<>(ModFeatures.DYEDREAM_WATER_POOL.get(),
                new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.WATER),
                        BlockStateProvider.simple(Blocks.SNOW_BLOCK)
                )));

        context.register(DYEDREAM_ICE_STONE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.ICE_STONE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        context.register(DYEDREAM_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.DYEDREAM_ICE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        context.register(DYEDREAM_PACKED_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                ModBlocks.DYEDREAM_PACKED_ICE.get().defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        context.register(VANILLA_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                Blocks.ICE.defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        context.register(VANILLA_PACKED_ICE_BLOBS, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(
                        List.of(OreConfiguration.target(
                                new BlockMatchTest(Blocks.CALCITE),
                                Blocks.PACKED_ICE.defaultBlockState()
                        )),
                        64,
                        0.5f
                )));
        context.register(CALCITE_BOULDER, new ConfiguredFeature<>(Feature.FOREST_ROCK,
                new BlockStateConfiguration(Blocks.CALCITE.defaultBlockState())));

        context.register(STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.STEM_GRASS.get())))));

        context.register(TALL_STEM_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_STEM_GRASS.get())))));

        context.register(PINK_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.PINK_MUSHROOM.get())))));

        context.register(TALL_PINK_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 16, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.TALL_PINK_MUSHROOM.get())))));

        context.register(DYEDREAM_COROLLA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 6, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.DYEDREAM_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        context.register(DYEDREAM_COROLLA_PATCH_DENSE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(96, 10, 4,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.DYEDREAM_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        context.register(FLOATING_LIGHT_BALL, new ConfiguredFeature<>(ModFeatures.FLOATING_LIGHT_BALL.get(),
                NoneFeatureConfiguration.INSTANCE));

        context.register(LIGHT_BALL_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 6, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.LIGHT_BALL_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        context.register(CLOUD_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 6, 2,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.CLOUD_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));

        context.register(DYEDREAM_LILY_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 6, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get())))));

        context.register(DREAMING_LOTUS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DREAMING_LOTUS.get())))));

        context.register(EDELWEISS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 6, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.EDELWEISS.get())))));
        context.register(SINGULARITY_FERN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 5, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SINGULARITY_FERN.get())))));
        context.register(LINHT_FLOWER_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 5, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.LINHT_FLOWER.get())))));

        context.register(FLOWER_FIELD_FLOWERS, new ConfiguredFeature<>(Feature.FLOWER,
                new RandomPatchConfiguration(96, 6, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        new NoiseProvider(2345L,
                                                new NormalNoise.NoiseParameters(0, 1.0),
                                                0.020833334F,
                                                List.of(
                                                        ModBlocks.DYEDREAM_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1),
                                                        ModBlocks.LIGHT_BALL_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1),
                                                        ModBlocks.CLOUD_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1),
                                                        ModBlocks.LINHT_FLOWER.get().defaultBlockState(),
                                                        ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get().defaultBlockState())))))));

        // ===== 主世界植被 / 小石子 =====
        context.register(FOURLEAF_CLOVER_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.FOURLEAF_CLOVER.get())))));
        context.register(GOLDENROD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.GOLDENROD.get())))));
        context.register(FERRARIA_CRISPA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.FERRARIA_CRISPA.get())))));
        context.register(MALVA_SINENSIS_CAVAN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.MALVA_SINENSIS_CAVAN.get())))));
        context.register(EUSTOMA_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.EUSTOMA.get())))));
        context.register(OATS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.OATS.get())))));
        context.register(RYE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.RYE.get())))));
        context.register(WHITE_COROLLA_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(8, 4, 1,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.WHITE_COROLLA_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));
        context.register(COTTON_CROP_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(
                                ModBlocks.COTTON_CROP.get().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1))))));
        context.register(JUNGLE_SPORANGIUM_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 6, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.JUNGLE_SPORANGIUM.get())))));
        context.register(REED_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 6, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.REED.get())))));
        context.register(PEBBLE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(6, 7, 3,
                        PlacementUtils.inlinePlaced(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PEBBLE.get())),
                                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                                        BlockPredicate.matchesBlocks(Blocks.AIR),
                                        BlockPredicate.solid(Direction.DOWN.getNormal())))))));

        // ========= 灯影之下 =========
        // 阴影真菌树 — 骨粉催熟+自然生成，诡异菌形态
        context.register(SHADOW_FUNGUS_TREE, new ConfiguredFeature<>(Feature.HUGE_FUNGUS,
                new net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration(
                        ModBlocks.SHADOW_NYLIUM.get().defaultBlockState(),
                        ModBlocks.SHADOW_STEM.get().defaultBlockState(),
                        ModBlocks.SHADOW_WART_BLOCK.get().defaultBlockState(),
                        ModBlocks.SHADOW_SHROOMLIGHT.get().defaultBlockState(),
                        BlockPredicate.matchesBlocks(ModBlocks.SHADOW_NYLIUM.get()),
                        true)));

        // 影芽 — 分散地表植被，比染梦茎草更稀疏
        context.register(SHADOW_SPROUTS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(16, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_SPROUTS.get())))));
        // 影蕨 — 分散地表植被，比染梦茎草更稀疏
        context.register(SHADOW_FERN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_FERN.get())))));
        // 阴影矮菌索 — 分散地表植被
        context.register(SHADOW_SHORT_ROOTS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(16, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_SHORT_ROOTS.get())))));
        // 阴影菌索 — 分散地表植被
        context.register(SHADOW_ROOTS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_ROOTS.get())))));
        // 影茎蕨 — 分散地表植被
        context.register(SHADOW_STEM_FERN_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(8, 8, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_STEM_FERN.get())))));
        // 影菌 — 分散地表植被
        context.register(SHADOW_FUNGUS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(8, 4, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SHADOW_FUNGUS.get())))));
        // 白厄花 — 分散地表植被
        context.register(WHITE_ORCHID_FLOWER_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(8, 4, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.WHITE_ORCHID_FLOWER.get())))));

        // 阴影残垣断壁 — 散落的破败墙体
        context.register(SHADOW_RUIN_WALL, new ConfiguredFeature<>(ModFeatures.SHADOW_RUIN_WALL.get(),
                NoneFeatureConfiguration.INSTANCE));

        // 阴影锁链柱 — 交替朝向镂空矩形单元堆叠，3-8 单元随机高度
        context.register(SHADOW_CHAIN_PILLAR, new ConfiguredFeature<>(ModFeatures.SHADOW_CHAIN_PILLAR.get(),
                NoneFeatureConfiguration.INSTANCE));

        // 暗影之手 — NBT 结构地物
        context.register(SHADOW_HAND, new ConfiguredFeature<>(ModFeatures.SHADOW_HAND.get(),
                NoneFeatureConfiguration.INSTANCE));

        // 阴影古墓 — NBT 结构地物（shadow_tomb_0/1 随机变体，下陷2格）
        context.register(SHADOW_TOMB, new ConfiguredFeature<>(ModFeatures.SHADOW_TOMB.get(),
                NoneFeatureConfiguration.INSTANCE));


        context.register(CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.POLISHED_CALCITE_STALICRIPE.get())))));
        context.register(SMALL_CALCITE_STALICRIPE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get())))));

        context.register(DYEDREAM_MOSS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 8, 8,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_MOSS.get())))));

        context.register(DYEDREAM_VINE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 7, 3,
                        dyedreamVineColumn())));

        context.register(CLOUD_PILLAR_SMALL, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 2, 24,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get())))));
        context.register(CLOUD_PILLAR_LARGE, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(256, 5, 48,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get())))));

        context.register(CORAL_TREE_PATCH, new ConfiguredFeature<>(Feature.CORAL_TREE,
                NoneFeatureConfiguration.INSTANCE));
        context.register(CORAL_CLAW_PATCH, new ConfiguredFeature<>(Feature.CORAL_CLAW,
                NoneFeatureConfiguration.INSTANCE));
        context.register(CORAL_MUSHROOM_PATCH, new ConfiguredFeature<>(Feature.CORAL_MUSHROOM,
                NoneFeatureConfiguration.INSTANCE));
        context.register(SEA_PICKLE_PATCH, new ConfiguredFeature<>(Feature.SEA_PICKLE,
                new CountConfiguration(UniformInt.of(1, 4))));
        context.register(DYEDREAM_SEAGRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(7, 7, 3,
                        simpleBlockInWater(BlockStateProvider.simple(
                                ModBlocks.DYEDREAM_SEAGRASS.get().defaultBlockState()
                                        .setValue(BlockStateProperties.WATERLOGGED, true))))));

        context.register(DYEDREAM_RIVER_CLAY, new ConfiguredFeature<>(Feature.DISK,
                new DiskConfiguration(
                        RuleBasedBlockStateProvider.simple(Blocks.CLAY),
                        BlockPredicate.matchesBlocks(List.of(
                                ModBlocks.DYEDREAM_DIRT.get(),
                                ModBlocks.DYEDREAM_SAND.get(),
                                ModBlocks.WHITE_SAND.get())),
                        UniformInt.of(2, 3),
                        1)));

        context.register(SMALL_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.SMALL_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(MEDIUM_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.MEDIUM_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(LARGE_DYEDREAM_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 4, 4,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.LARGE_DYEDREAM_BUD.get()), DYEDREAM_BUD_GROUND))));
        context.register(ICE_BUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(25, 5, 5,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.ICE_BUD.get()), ICE_BUD_GROUND))));

        context.register(DYEDREAM_GEODE, new ConfiguredFeature<>(Feature.GEODE,
                new GeodeConfiguration(
                        new GeodeBlockSettings(
                                BlockStateProvider.simple(Blocks.AIR),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_BUD_BLOCK.get()),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_BUDDING_BLOCK.get()),
                                BlockStateProvider.simple(Blocks.CALCITE),
                                BlockStateProvider.simple(Blocks.DIORITE),
                                List.of(
                                        ModBlocks.SMALL_DYEDREAM_BUD.get().defaultBlockState(),
                                        ModBlocks.MEDIUM_DYEDREAM_BUD.get().defaultBlockState(),
                                        ModBlocks.LARGE_DYEDREAM_BUD.get().defaultBlockState()
                                ),
                                BlockTags.FEATURES_CANNOT_REPLACE,
                                BlockTags.GEODE_INVALID_BLOCKS
                        ),
                        new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                        new GeodeCrackSettings(0.95, 2.0, 2),
                        0.35,
                        0.083,
                        true,
                        UniformInt.of(4, 6),
                        UniformInt.of(3, 4),
                        UniformInt.of(1, 2),
                        -16,
                        16,
                        0.05,
                        1
                )));

        context.register(DYEDREAM_LILY_PAD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 7, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LILY_PAD.get())))));
        context.register(DYEDREAM_LOTUS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 6, 5,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.DYEDREAM_LOTUS.get())))));

        // ===== 染梦维度矿石 =====
        context.register(TITANIUM_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.TITANIUM_ORE.get(), List.of(TARGET_CALCITE)), 8, 0f)));
        context.register(AMBER_CANDY_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.AMBER_CANDY_ORE.get(), List.of(TARGET_CALCITE)), 24, 0f)));
        context.register(DYEDREAM_DUST_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.DYEDREAM_DUST_ORE.get(), TARGET_CALCITE_AND_STONES), 16, 0f)));
        context.register(DYEDREAM_QUARTZ_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), TARGET_CALCITE_AND_STONES), 28, 0f)));

        // ===== 染梦世界·方解石原版矿物 =====
        context.register(CALCITE_COAL_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_COAL_ORE.get(), List.of(TARGET_CALCITE)), 17, 0f)));
        context.register(CALCITE_COAL_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_COAL_ORE.get(), List.of(TARGET_CALCITE)), 17, 0.5f)));
        context.register(CALCITE_IRON_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_IRON_ORE.get(), List.of(TARGET_CALCITE)), 9, 0f)));
        context.register(CALCITE_IRON_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_IRON_ORE.get(), List.of(TARGET_CALCITE)), 4, 0f)));
        context.register(CALCITE_COPPER_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_COPPER_ORE.get(), List.of(TARGET_CALCITE)), 10, 0f)));
        context.register(CALCITE_COPPER_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_COPPER_ORE.get(), List.of(TARGET_CALCITE)), 20, 0f)));
        context.register(CALCITE_GOLD_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_GOLD_ORE.get(), List.of(TARGET_CALCITE)), 9, 0.5f)));
        context.register(CALCITE_REDSTONE_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_REDSTONE_ORE.get(), List.of(TARGET_CALCITE)), 8, 0f)));
        context.register(CALCITE_LAPIS_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_LAPIS_ORE.get(), List.of(TARGET_CALCITE)), 7, 0f)));
        context.register(CALCITE_LAPIS_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_LAPIS_ORE.get(), List.of(TARGET_CALCITE)), 7, 1.0f)));
        context.register(CALCITE_DIAMOND_ORE_SMALL, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_DIAMOND_ORE.get(), List.of(TARGET_CALCITE)), 4, 0.5f)));
        context.register(CALCITE_DIAMOND_ORE_LARGE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_DIAMOND_ORE.get(), List.of(TARGET_CALCITE)), 12, 0.7f)));
        context.register(CALCITE_DIAMOND_ORE_BURIED, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_DIAMOND_ORE.get(), List.of(TARGET_CALCITE)), 8, 1.0f)));
        context.register(CALCITE_EMERALD_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CALCITE_EMERALD_ORE.get(), List.of(TARGET_CALCITE)), 3, 0f)));

        // ===== 通用矿石（主世界/下界） =====
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH,
                new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                        List.of(Blocks.DEEPSLATE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE).stream()
                                .map(block -> OreConfiguration.target(new BlockMatchTest(block),
                                        ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState())).toList(),
                        8, 0f)));
        context.register(MOLTEN_GOLD_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK),
                        ModBlocks.MOLTEN_GOLD_ORE.get().defaultBlockState(), 16, 0f)));
        context.register(SOUL_ORE_PATCH, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.SOUL_SOIL),
                        ModBlocks.SOUL_ORE.get().defaultBlockState(), 16, 0f)));

        // ===== 风之旅途 =====
        // 凝风矿 — 原作 congeal_wind_ore: size=11, 替换 thick_cloud
        context.register(CONGEAL_WIND_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.CONGEAL_WIND_ORE.get(),
                        List.of(new BlockMatchTest(ModBlocks.THICK_CLOUD.get()))), 11, 0f)));
        // 破风骑士水晶矿 — 原作 windrunner_crystal_ore: size=6, 替换 cyan_stone
        context.register(WIND_RUNNER_CRYSTAL_ORE, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(oreTargets(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get(),
                        List.of(new BlockMatchTest(ModBlocks.CYAN_STONE.get()))), 6, 0f)));
        // 云团（高位）— 原作 ground_feature_wind_journey_0: tries=5, xz=11, y=4
        context.register(WIND_JOURNEY_CLOUD_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(5, 11, 4, windCloudSelector())));
        // 云团（低位）— 原作 ground_feature_wind_journey_2: tries=4, xz=11, y=4
        context.register(WIND_JOURNEY_CLOUD_PATCH_LOW, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(4, 11, 4, windCloudSelector())));
        // 水池 — 原作 ground_feature_wind_journey_1: lake, 水 + cyan_stone 围边
        // 每池 20% 概率在岸边生成 1~2 棵眠椰树（见 WindJourneyWaterPoolFeature）
        context.register(WIND_JOURNEY_WATER_POOL, new ConfiguredFeature<>(ModFeatures.WIND_JOURNEY_WATER_POOL.get(),
                new WindJourneyWaterPoolConfiguration(
                        new LakeFeature.Configuration(
                                BlockStateProvider.simple(Blocks.WATER),
                                BlockStateProvider.simple(ModBlocks.CYAN_STONE.get())),
                        0.2f,
                        UniformInt.of(1, 2))));
        // 小石子 — 原作 ground_feature_wind_journey_3: tries=5（xz/y 默认 7/3）
        context.register(WIND_JOURNEY_PEBBLE_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(5, 7, 3,
                        simpleBlockOnSolidGround(BlockStateProvider.simple(ModBlocks.PEBBLE.get())))));
        // 萤火虫巢 — 原作 ground_feature_wind_journey_4: tries=1, xz=2, y=1，底部仅限苍青岩/苍青苔岩
        context.register(WIND_JOURNEY_FIREFLY_NEST, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(1, 2, 1,
                        simpleBudInAir(BlockStateProvider.simple(ModBlocks.FIREFLY_NEST.get()),
                                WIND_JOURNEY_GROUND))));
        // 苍青苔石团块 — 原作 ground_feature_wind_journey_5: 替换泥为 cyan_moss_stone, radius 1~1
        context.register(WIND_JOURNEY_CYAN_MOSS_STONE_BLOB, new ConfiguredFeature<>(Feature.REPLACE_BLOBS,
                new ReplaceSphereConfiguration(
                        Blocks.MUD.defaultBlockState(),
                        ModBlocks.CYAN_MOSS_STONE.get().defaultBlockState(),
                        UniformInt.of(1, 1))));
        // 小石堆 — 原作 ground_feature_wind_journey_6: tries=2, xz=5, y=2，底部仅限苍青岩/苍青苔岩
        context.register(WIND_JOURNEY_SMALL_STONE_SPIRIT, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(2, 5, 2,
                        simpleBudInAir(BlockStateProvider.simple(
                                ModBlocks.SMALL_STONE_SPIRIT_BLOCK.get().defaultBlockState()),
                                WIND_JOURNEY_GROUND))));
        // 茸毛苔 — 原作 grass_13: tries=48（xz/y 走默认 7/3），仅替换空气
        context.register(WIND_JOURNEY_HAIRY_MOSS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 7, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.HAIRY_MOSS.get().defaultBlockState())))));
        // 斩风草 — 原作 grass_14: tries=48
        context.register(WIND_JOURNEY_CLEAVING_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 7, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.WIND_CLEAVING_GRASS.get().defaultBlockState())))));
        // 风羽草 — 原作 grass_15: tries=48
        context.register(WIND_JOURNEY_FEATHER_GRASS_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 7, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.WIND_FEATHER_GRASS.get().defaultBlockState())))));
        // 风岛芦苇 — 原作 flower_18: tries=64
        context.register(WIND_JOURNEY_REED_PATCH, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 7, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.WIND_ISLAND_REED.get().defaultBlockState())))));
    }

    private static Holder<PlacedFeature> simpleBlockInAir(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR))
        );
    }

    /** 目标格为空气且下方为染梦草/染梦土（防止浮空生成） */
    private static Holder<PlacedFeature> simpleBlockOnDyedreamGround(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(),
                                ModBlocks.DYEDREAM_GRASS_BLOCK.get(), ModBlocks.DYEDREAM_DIRT.get()))));
    }

    /** 染梦藤蔓柱 — 向上生长 2-4 格，仅生成在染梦地表方块上 */
    private static Holder<PlacedFeature> dyedreamVineColumn() {
        return PlacementUtils.inlinePlaced(
                Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(
                        List.of(new BlockColumnConfiguration.Layer(
                                UniformInt.of(2, 4),
                                BlockStateProvider.simple(ModBlocks.DYEDREAM_VINE.get())
                        )),
                        Direction.UP,
                        BlockPredicate.matchesBlocks(Blocks.AIR),
                        false
                ),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.AIR)),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.matchesBlocks(
                                Direction.DOWN.getNormal(),
                                ModBlocks.DYEDREAM_GRASS_BLOCK.get(),
                                ModBlocks.DYEDREAM_DIRT.get()))
        );
    }

    /** 晶芽专用的生成辅助：同时检查目标位置为空气、下方方块在允许列表内 */
    private static Holder<PlacedFeature> simpleBudInAir(BlockStateProvider provider, List<Block> groundBlocks) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(List.of(
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), groundBlocks)
                        ))
                )
        );
    }

    private static Holder<PlacedFeature> simpleBlockInWater(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(List.of(
                                BlockPredicate.matchesBlocks(Blocks.WATER),
                                BlockPredicate.solid(Direction.DOWN.getNormal())
                        ))
                )
        );
    }

    /** 放置在固体方块上的简单方块（同时要求目标为空气） */
    private static Holder<PlacedFeature> simpleBlockOnSolidGround(BlockStateProvider provider) {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(provider),
                BlockPredicateFilter.forPredicate(
                        BlockPredicate.allOf(List.of(
                                BlockPredicate.matchesBlocks(Blocks.AIR),
                                BlockPredicate.solid(Direction.DOWN.getNormal())
                        ))
                )
        );
    }

    /** 风之旅途云团 — 变体 A（原作内层: tries=64, xz=4, y=2） */
    private static Holder<PlacedFeature> windCloudBlobA() {
        return PlacementUtils.inlinePlaced(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(64, 4, 2,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get()))));
    }

    /** 风之旅途云团 — 变体 B（原作内层仅 xz=5，tries/y 走原版默认 128/3） */
    private static Holder<PlacedFeature> windCloudBlobB() {
        return PlacementUtils.inlinePlaced(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(128, 5, 3,
                        simpleBlockInAir(BlockStateProvider.simple(ModBlocks.CLOUD.get()))));
    }

    /** 风之旅途云团选择器 — 两种云簇二选一 */
    private static Holder<PlacedFeature> windCloudSelector() {
        return PlacementUtils.inlinePlaced(
                Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(HolderSet.direct(windCloudBlobA(), windCloudBlobB())));
    }

    private static List<OreConfiguration.TargetBlockState> oreTargets(Block oreBlock, List<RuleTest> ruleTests) {
        return ruleTests.stream()
                .<OreConfiguration.TargetBlockState>map(rt -> OreConfiguration.target(rt, oreBlock.defaultBlockState()))
                .toList();
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModConfiguredFeatures() {
    }
}
