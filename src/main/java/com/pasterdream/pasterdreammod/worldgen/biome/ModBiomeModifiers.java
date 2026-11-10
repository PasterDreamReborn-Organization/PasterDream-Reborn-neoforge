package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public final class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> DEEPSLATE_TITANIUM_ORE_PATCH =
            key("deepslate_titanium_ore_patch");
    public static final ResourceKey<BiomeModifier> MOLTEN_GOLD_ORE_PATCH =
            key("molten_gold_ore_patch");
    public static final ResourceKey<BiomeModifier> SOUL_ORE_PATCH =
            key("soul_ore_patch");

    private static final ResourceKey<PlacedFeature> VANILLA_TREES_CHERRY =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "trees_cherry"));
    private static final ResourceKey<PlacedFeature> VANILLA_FLOWER_CHERRY =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "flower_cherry"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);

        // 保留来源 addPatch 的生成阶段，平台迁移不顺带调整矿物分布或顺序。
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, addFeatures(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                features.getOrThrow(ModPlacedFeatures.DEEPSLATE_TITANIUM_ORE_PATCH),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(SOUL_ORE_PATCH, addFeatures(
                biomes.getOrThrow(ModBiomeTags.CAN_SOUL_ORE_SPAWN_BIOME),
                features.getOrThrow(ModPlacedFeatures.SOUL_ORE_PATCH),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(MOLTEN_GOLD_ORE_PATCH, addFeatures(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                features.getOrThrow(ModPlacedFeatures.MOLTEN_GOLD_ORE_PATCH),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // ===== 主世界地物 =====
        addPatch(context, biomes, features, "pebble_patch", ModBiomeTags.CAN_PEBBLE_SPAWN_BIOME, ModPlacedFeatures.PEBBLE_PATCH, GenerationStep.Decoration.SURFACE_STRUCTURES);
        addPatch(context, biomes, features, "goldenrod_patch", ModBiomeTags.CAN_GOLDENROD_SPAWN_BIOME, ModPlacedFeatures.GOLDENROD_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "fourleaf_clover_patch", ModBiomeTags.CAN_FOURLEAF_CLOVER_SPAWN_BIOME, ModPlacedFeatures.FOURLEAF_CLOVER_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "ferraria_crispa_patch", ModBiomeTags.CAN_FLOWER_FIELD_SPAWN_BIOME, ModPlacedFeatures.FERRARIA_CRISPA_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "malva_sinensis_cavan_patch", ModBiomeTags.CAN_FLOWER_FIELD_SPAWN_BIOME, ModPlacedFeatures.MALVA_SINENSIS_CAVAN_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "eustoma_patch", BiomeTags.IS_JUNGLE, ModPlacedFeatures.EUSTOMA_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "oats_patch", ModBiomeTags.CAN_OATS_SPAWN_BIOME, ModPlacedFeatures.OATS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "rye_patch", ModBiomeTags.CAN_RYE_SPAWN_BIOME, ModPlacedFeatures.RYE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "white_corolla_crop_patch", ModBiomeTags.CAN_WHITE_COROLLA_SPAWN_BIOME, ModPlacedFeatures.WHITE_COROLLA_CROP_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "cotton_crop_patch", ModBiomeTags.CAN_COTTON_SPAWN_BIOME, ModPlacedFeatures.COTTON_CROP_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "jungle_sporangium_patch", BiomeTags.IS_JUNGLE, ModPlacedFeatures.JUNGLE_SPORANGIUM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "reed_patch", ModBiomeTags.CAN_REED_SPAWN_BIOME, ModPlacedFeatures.REED_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 染梦维度通用：矿石 / 晶芽 / 晶洞 =====
        addPatch(context, biomes, features, "dyedream_titanium_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.TITANIUM_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_amber_candy_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.AMBER_CANDY_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_dust_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.DYEDREAM_DUST_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_quartz_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.DYEDREAM_QUARTZ_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_coal_ore_upper", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_COAL_ORE_UPPER, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_coal_ore_lower", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_COAL_ORE_LOWER, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_iron_ore_upper", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_IRON_ORE_UPPER, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_iron_ore_middle", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_IRON_ORE_MIDDLE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_iron_ore_small", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_IRON_ORE_SMALL, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_copper_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_COPPER_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_copper_ore_large", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_COPPER_ORE_LARGE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_gold_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_GOLD_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_gold_ore_lower", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_GOLD_ORE_LOWER, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_redstone_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_REDSTONE_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_redstone_ore_lower", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_REDSTONE_ORE_LOWER, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_lapis_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_LAPIS_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_lapis_ore_buried", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_LAPIS_ORE_BURIED, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_diamond_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_DIAMOND_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_diamond_ore_large", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_DIAMOND_ORE_LARGE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_diamond_ore_buried", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_DIAMOND_ORE_BURIED, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_calcite_emerald_ore", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_EMERALD_ORE, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_small_bud", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.SMALL_DYEDREAM_BUD_PATCH, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addPatch(context, biomes, features, "dyedream_medium_bud", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.MEDIUM_DYEDREAM_BUD_PATCH, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addPatch(context, biomes, features, "dyedream_large_bud", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.LARGE_DYEDREAM_BUD_PATCH, GenerationStep.Decoration.UNDERGROUND_DECORATION);
        addPatch(context, biomes, features, "dyedream_geode", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.DYEDREAM_GEODE, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

        // ===== 水面植物 =====
        addPatch(context, biomes, features, "dyedream_lily_pad_biome_modifier", ModBiomeTags.DYEDREAM_WORLD_WATER_BIOME, ModPlacedFeatures.DYEDREAM_LILY_PAD_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lotus_biome_modifier", ModBiomeTags.DYEDREAM_WORLD_WATER_BIOME, ModPlacedFeatures.DYEDREAM_LOTUS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 海草 / 河流粘土 =====
        addPatch(context, biomes, features, "dyedream_seagrass", ModBiomeTags.DYEDREAM_WORLD_SEAGRASS_BIOME, ModPlacedFeatures.DYEDREAM_SEAGRASS_PATCH, GenerationStep.Decoration.SURFACE_STRUCTURES);
        addPatch(context, biomes, features, "dyedream_river_clay", ModBiomeTags.DYEDREAM_WORLD_RIVER_BIOME, ModPlacedFeatures.DYEDREAM_RIVER_CLAY, GenerationStep.Decoration.UNDERGROUND_ORES);

        // ===== 冰团 =====
        addPatch(context, biomes, features, "dyedream_warm_ice_blob", ModBiomeTags.DYEDREAM_WORLD_WARM_ICE_BIOME, ModPlacedFeatures.DYEDREAM_ICE_BLOBS, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_warm_packed_ice_blob", ModBiomeTags.DYEDREAM_WORLD_WARM_ICE_BIOME, ModPlacedFeatures.DYEDREAM_PACKED_ICE_BLOBS, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_cold_ice_stone", ModBiomeTags.DYEDREAM_WORLD_COLD_BIOME, ModPlacedFeatures.DYEDREAM_ICE_STONE_BLOBS, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_cold_vanilla_ice", ModBiomeTags.DYEDREAM_WORLD_COLD_BIOME, ModPlacedFeatures.VANILLA_ICE_BLOBS, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_cold_vanilla_packed_ice", ModBiomeTags.DYEDREAM_WORLD_COLD_BIOME, ModPlacedFeatures.VANILLA_PACKED_ICE_BLOBS, GenerationStep.Decoration.UNDERGROUND_ORES);
        addPatch(context, biomes, features, "dyedream_cold_ice_bud", ModBiomeTags.DYEDREAM_WORLD_COLD_BIOME, ModPlacedFeatures.ICE_BUD_PATCH, GenerationStep.Decoration.UNDERGROUND_DECORATION);

        // 雪顶（寒冷陆地 + 冻河）
        addPatch(context, biomes, features, "dyedream_freeze_top", ModBiomeTags.DYEDREAM_WORLD_FREEZE_BIOME, ModPlacedFeatures.FREEZE_TOP_LAYER, GenerationStep.Decoration.TOP_LAYER_MODIFICATION);

        // ===== 温暖陆地共享花草 =====
        addPatch(context, biomes, features, "dyedream_warm_lotus", ModBiomeTags.DYEDREAM_WORLD_WARM_LAND_BIOME, ModPlacedFeatures.DREAMING_LOTUS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_warm_linht", ModBiomeTags.DYEDREAM_WORLD_WARM_LAND_BIOME, ModPlacedFeatures.LINHT_FLOWER_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_warm_fern", ModBiomeTags.DYEDREAM_WORLD_WARM_LAND_BIOME, ModPlacedFeatures.SINGULARITY_FERN_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 通用植被 =====
        addPatch(context, biomes, features, "dyedream_common_moss", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.DYEDREAM_MOSS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_common_coral_tree", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CORAL_TREE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_common_coral_claw", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CORAL_CLAW_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_common_coral_mushroom", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CORAL_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_common_sea_pickle", ModBiomeTags.DYEDREAM_WORLD_SEA_PICKLE_BIOME, ModPlacedFeatures.SEA_PICKLE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_common_calcite_boulder", ModBiomeTags.DYEDREAM_WORLD_BIOME, ModPlacedFeatures.CALCITE_BOULDER, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 平原 =====
        addPatch(context, biomes, features, "dyedream_plains_tree", ModBiomeTags.DYEDREAM_WORLD_PLAINS_BIOME, ModPlacedFeatures.DYEDREAM_TREE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_plains_corolla", ModBiomeTags.DYEDREAM_WORLD_PLAINS_BIOME, ModPlacedFeatures.DYEDREAM_COROLLA_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_plains_light_ball", ModBiomeTags.DYEDREAM_WORLD_PLAINS_BIOME, ModPlacedFeatures.LIGHT_BALL_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_plains_cloud_crop", ModBiomeTags.DYEDREAM_WORLD_PLAINS_BIOME, ModPlacedFeatures.CLOUD_CROP_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 花海 =====
        addPatch(context, biomes, features, "dyedream_flower_field_corolla", ModBiomeTags.DYEDREAM_WORLD_FLOWER_FIELD_BIOME, ModPlacedFeatures.DYEDREAM_COROLLA_PATCH_FLOWER_FIELD, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_flower_field_stem_grass", ModBiomeTags.DYEDREAM_WORLD_FLOWER_FIELD_BIOME, ModPlacedFeatures.STEM_GRASS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_flower_field_light_ball", ModBiomeTags.DYEDREAM_WORLD_FLOWER_FIELD_BIOME, ModPlacedFeatures.FLOATING_LIGHT_BALL, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_flower_field_mixed_flowers", ModBiomeTags.DYEDREAM_WORLD_FLOWER_FIELD_BIOME, ModPlacedFeatures.FLOWER_FIELD_FLOWERS, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 平原/森林共享装饰 =====
        addPatch(context, biomes, features, "dyedream_stem_grass", ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME, ModPlacedFeatures.STEM_GRASS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_tall_stem_grass", ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME, ModPlacedFeatures.TALL_STEM_GRASS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lily", ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME, ModPlacedFeatures.DYEDREAM_LILY_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_vine", ModBiomeTags.DYEDREAM_WORLD_VINE_BIOME, ModPlacedFeatures.DYEDREAM_VINE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_calcite_stalicripe", ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME, ModPlacedFeatures.CALCITE_STALICRIPE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_small_calcite_stalicripe", ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME, ModPlacedFeatures.SMALL_CALCITE_STALICRIPE, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 森林 =====
        addPatch(context, biomes, features, "dyedream_forest_dense_tree", ModBiomeTags.DYEDREAM_WORLD_FOREST_BIOME, ModPlacedFeatures.DYEDREAM_TREE_DENSE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_forest_mushroom", ModBiomeTags.DYEDREAM_WORLD_FOREST_BIOME, ModPlacedFeatures.PINK_HUGE_MUSHROOM_SPARSE, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 菇山 =====
        addPatch(context, biomes, features, "dyedream_mushroom_tree", ModBiomeTags.DYEDREAM_WORLD_MUSHROOM_BIOME, ModPlacedFeatures.PINK_MUSHROOM_TREE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_mushroom_huge", ModBiomeTags.DYEDREAM_WORLD_MUSHROOM_BIOME, ModPlacedFeatures.PINK_HUGE_MUSHROOM, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_mushroom_patch", ModBiomeTags.DYEDREAM_WORLD_MUSHROOM_BIOME, ModPlacedFeatures.PINK_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_tall_mushroom_patch", ModBiomeTags.DYEDREAM_WORLD_MUSHROOM_BIOME, ModPlacedFeatures.TALL_PINK_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 寒冷陆地冰雪装饰 =====
        addPatch(context, biomes, features, "dyedream_ice_pillar", ModBiomeTags.DYEDREAM_WORLD_SNOWY_ICE_PILLAR_BIOME, ModPlacedFeatures.DYEDREAM_ICE_PILLAR, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_packed_ice_pillar", ModBiomeTags.DYEDREAM_WORLD_SNOWY_ICE_PILLAR_BIOME, ModPlacedFeatures.DYEDREAM_PACKED_ICE_PILLAR, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_edelweiss", ModBiomeTags.DYEDREAM_WORLD_EDELWEISS_BIOME, ModPlacedFeatures.EDELWEISS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_cloud_pillar_small", ModBiomeTags.DYEDREAM_WORLD_CLOUD_PILLAR_BIOME, ModPlacedFeatures.CLOUD_PILLAR_SMALL, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_cloud_pillar_large", ModBiomeTags.DYEDREAM_WORLD_CLOUD_PILLAR_BIOME, ModPlacedFeatures.CLOUD_PILLAR_LARGE, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 雪原 =====
        addPatch(context, biomes, features, "dyedream_snowy_tree", ModBiomeTags.DYEDREAM_WORLD_SNOWY_TREE_BIOME, ModPlacedFeatures.DYEDREAM_TREE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_snowy_water_pool", ModBiomeTags.DYEDREAM_WORLD_SNOWY_POOL_BIOME, ModPlacedFeatures.SNOWY_WATER_POOL, GenerationStep.Decoration.SURFACE_STRUCTURES);

        // ===== 染梦樱花林 =====
        addPatch(context, biomes, features, "dyedream_cherry_grove_trees", ModBiomeTags.DYEDREAM_WORLD_CHERRY_GROVE_BIOME, VANILLA_TREES_CHERRY, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_cherry_grove_pink_petals", ModBiomeTags.DYEDREAM_WORLD_CHERRY_GROVE_BIOME, VANILLA_FLOWER_CHERRY, GenerationStep.Decoration.VEGETAL_DECORATION);

        // ===== 海洋 =====
        addPatch(context, biomes, features, "dyedream_kelp", ModBiomeTags.DYEDREAM_WORLD_KELP_BIOME, ModPlacedFeatures.DYEDREAM_KELP_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_iceberg_packed", ModBiomeTags.DYEDREAM_WORLD_ICEBERG_BIOME, ModPlacedFeatures.DYEDREAM_ICEBERG_PACKED, GenerationStep.Decoration.LOCAL_MODIFICATIONS);
        addPatch(context, biomes, features, "dyedream_iceberg_blue", ModBiomeTags.DYEDREAM_WORLD_ICEBERG_BIOME, ModPlacedFeatures.DYEDREAM_ICEBERG_BLUE, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

        // ===== 洞穴 =====
        addPatch(context, biomes, features, "dyedream_lush_mushroom_tree", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_MUSHROOM_TREE, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_small_mushroom", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.SMALL_PINK_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_mushroom_patch", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_tall_mushroom_patch", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_TALL_MUSHROOM_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_stem_grass", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_STEM_GRASS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_tall_stem_grass", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_TALL_STEM_GRASS_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_fern", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_FERN_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_vine", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_VINE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_shroom_block", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_SHROOM_BLOCK_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_lush_pink_mushroom_curtain", ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME, ModPlacedFeatures.LUSH_CAVE_PINK_MUSHROOM_CURTAIN, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_dripstone_cone", ModBiomeTags.DYEDREAM_WORLD_DRIPSTONE_BIOME, ModPlacedFeatures.CALCITE_CONE_PATCH, GenerationStep.Decoration.VEGETAL_DECORATION);
        addPatch(context, biomes, features, "dyedream_dripstone_cluster", ModBiomeTags.DYEDREAM_WORLD_DRIPSTONE_BIOME, ModPlacedFeatures.CALCITE_CONE_CLUSTER_PATCH, GenerationStep.Decoration.LOCAL_MODIFICATIONS);

        // ===== 方解石尖锥 =====
        addPatch(context, biomes, features, "calcite_spike", ModBiomeTags.DYEDREAM_WORLD_LAND_BIOME, ModPlacedFeatures.CALCITE_SPIKE, GenerationStep.Decoration.SURFACE_STRUCTURES);
        addPatch(context, biomes, features, "stone_pillar_ocean", ModBiomeTags.DYEDREAM_WORLD_OCEAN_BIOME, ModPlacedFeatures.STONE_PILLAR_OCEAN, GenerationStep.Decoration.SURFACE_STRUCTURES);

        // ===== 灯影之下维度实体生成（对照原作 NOT_MODIFY 中的 biome_modifier + 群系 JSON） =====
        // biome_shadow_0（菌索荒原）: terrorbeak(10,1-2), shadow_hand(15,1-3)
        addSpawns(context, biomes, "terrorbeak_biome_modifier", ModEntities.TERRORBEAK.get(), ModBiomeTags.SHADOW_NYLIUM_WASTES_SPAWN_BIOME, 10, 1, 2);
        addSpawns(context, biomes, "shadow_hand_biome_modifier", ModEntities.SHADOW_HAND.get(), ModBiomeTags.SHADOW_NYLIUM_WASTES_SPAWN_BIOME, 15, 1, 3);

        // ===== 灯影之下维度地物（对照原作 NOT_MODIFY 中的 structure/structure_set） =====
        // 暗影之手 — 原作 shadow_hand_0 结构: biome_shadow_0, surface_structures step
        addPatch(context, biomes, features, "shadow_hand_feature", ModBiomeTags.SHADOW_NYLIUM_WASTES_SPAWN_BIOME, ModPlacedFeatures.SHADOW_HAND, GenerationStep.Decoration.SURFACE_STRUCTURES);

        // biome_shadow_1（阴影森林）: black_beetle(20,5-7 + 20,2-4) + friendly_shadow_ghost(10,1,3)
        //   原作有两处 black_beetle 生成 — 群系 JSON monster spawn + biome_modifier add_spawns
        addSpawns(context, biomes, "black_beetle_biome_spawn", ModEntities.BLACK_BEETLE.get(), ModBiomeTags.SHADOW_FOREST_SPAWN_BIOME, 20, 5, 7);
        addSpawns(context, biomes, "black_beetle_biome_modifier", ModEntities.BLACK_BEETLE.get(), ModBiomeTags.SHADOW_FOREST_SPAWN_BIOME, 20, 2, 4);
        addSpawns(context, biomes, "friendly_shadow_ghost_biome_modifier", ModEntities.FRIENDLY_SHADOW_GHOST.get(), ModBiomeTags.SHADOW_FOREST_SPAWN_BIOME, 10, 1, 3);

        // biome_shadow_1（阴影古迹）: 幽灵系三变体（降低密度：总权重 12，单次最多 4 只）
        addSpawns(context, biomes, "shadow_ghost_biome_modifier", ModEntities.SHADOW_GHOST.get(), ModBiomeTags.SHADOW_GHOST_SPAWN_BIOME, 6, 1, 2);
        addSpawns(context, biomes, "shadow_squeal_ghost_biome_modifier", ModEntities.SHADOW_SQUEAL_GHOST.get(), ModBiomeTags.SHADOW_GHOST_SPAWN_BIOME, 4, 1, 1);
        addSpawns(context, biomes, "wailing_shadow_ghost_biome_modifier", ModEntities.WAILING_SHADOW_GHOST.get(), ModBiomeTags.SHADOW_GHOST_SPAWN_BIOME, 2, 1, 1);

        // biome_shadow_2（阴影古迹）: shadow_golem(4,1-1) + shadow_hand(10,1-2)，后者稀释精英怪比例
        addSpawns(context, biomes, "shadow_golem_biome_modifier", ModEntities.SHADOW_GOLEM.get(), ModBiomeTags.SHADOW_RUINS_SPAWN_BIOME, 4, 1, 1);
        addSpawns(context, biomes, "shadow_hand_ruins_biome_modifier", ModEntities.SHADOW_HAND.get(), ModBiomeTags.SHADOW_RUINS_SPAWN_BIOME, 10, 1, 2);
    }

    private static void addPatch(BootstrapContext<BiomeModifier> context, HolderGetter<Biome> biomes,
                                 HolderGetter<PlacedFeature> features, String name, TagKey<Biome> biomeTag,
                                 ResourceKey<PlacedFeature> feature, GenerationStep.Decoration step) {
        context.register(key(name), addFeatures(biomes.getOrThrow(biomeTag), features.getOrThrow(feature), step));
    }

    private static BiomeModifier addFeatures(HolderSet<Biome> biomes, net.minecraft.core.Holder<PlacedFeature> feature,
                                             GenerationStep.Decoration step) {
        return new BiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(feature), step);
    }

    private static void addSpawns(BootstrapContext<BiomeModifier> context, HolderGetter<Biome> biomes,
                                  String name, EntityType<?> entityType, TagKey<Biome> biomeTag,
                                  int weight, int minCount, int maxCount) {
        context.register(key(name), new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(biomeTag),
                List.of(new MobSpawnSettings.SpawnerData(entityType, weight, minCount, maxCount))));
    }

    private static ResourceKey<BiomeModifier> key(String path) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModBiomeModifiers() {
    }
}
