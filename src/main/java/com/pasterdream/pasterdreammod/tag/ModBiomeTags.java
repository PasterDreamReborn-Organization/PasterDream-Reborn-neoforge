package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ModBiomeTags {
    public static final TagKey<Biome> CAN_SOUL_ORE_SPAWN_BIOME = create("can_soul_ore_spawn_biome");

    public static final TagKey<Biome> DYEDREAM_FISHING_BIOMES = create("dyedream_fishing_biomes");

    public static final TagKey<Biome> DYEDREAM_WORLD_BIOME = create("dyedream_world_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_WATER_BIOME = create("dyedream_world_water_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SEAGRASS_BIOME = create("dyedream_world_seagrass_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_RIVER_BIOME = create("dyedream_world_river_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_WARM_LAND_BIOME = create("dyedream_world_warm_land_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_WARM_ICE_BIOME = create("dyedream_world_warm_ice_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_COLD_BIOME = create("dyedream_world_cold_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_FREEZE_BIOME = create("dyedream_world_freeze_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SEA_PICKLE_BIOME = create("dyedream_world_sea_pickle_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_PLAINS_BIOME = create("dyedream_world_plains_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_FLOWER_FIELD_BIOME = create("dyedream_world_flower_field_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_STEM_BIOME = create("dyedream_world_stem_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_VINE_BIOME = create("dyedream_world_vine_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_FOREST_BIOME = create("dyedream_world_forest_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_MUSHROOM_BIOME = create("dyedream_world_mushroom_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SNOWY_ICE_PILLAR_BIOME = create("dyedream_world_snowy_ice_pillar_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_EDELWEISS_BIOME = create("dyedream_world_edelweiss_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_CLOUD_PILLAR_BIOME = create("dyedream_world_cloud_pillar_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SNOWY_TREE_BIOME = create("dyedream_world_snowy_tree_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_CHERRY_GROVE_BIOME = create("dyedream_world_cherry_grove_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SNOWY_POOL_BIOME = create("dyedream_world_snowy_pool_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_KELP_BIOME = create("dyedream_world_kelp_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_ICEBERG_BIOME = create("dyedream_world_iceberg_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_LUSH_BIOME = create("dyedream_world_lush_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_DRIPSTONE_BIOME = create("dyedream_world_dripstone_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_LAND_BIOME = create("dyedream_world_land_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_OCEAN_BIOME = create("dyedream_world_ocean_biome");
    public static final TagKey<Biome> DYEDREAM_WORLD_SNOWY_BIOME = create("dyedream_world_snowy_biome");

    private static TagKey<Biome> create(String path) {
        return TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path));
    }

    private ModBiomeTags() {
    }
}
