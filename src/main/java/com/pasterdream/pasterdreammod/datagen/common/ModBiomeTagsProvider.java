package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModBiomeTags.CAN_SOUL_ORE_SPAWN_BIOME).add(Biomes.SOUL_SAND_VALLEY);

        // ===== 主世界地物群系标签 =====
        tag(ModBiomeTags.CAN_WHITE_COROLLA_SPAWN_BIOME)
                .add(Biomes.SNOWY_SLOPES)
                .add(Biomes.JAGGED_PEAKS)
                .add(Biomes.FROZEN_PEAKS);
        tag(ModBiomeTags.CAN_FLOWER_FIELD_SPAWN_BIOME)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.PLAINS)
                .add(Biomes.JUNGLE);
        tag(ModBiomeTags.CAN_REED_SPAWN_BIOME)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP);
        tag(ModBiomeTags.CAN_RYE_SPAWN_BIOME)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.PLAINS)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.MEADOW);
        tag(ModBiomeTags.CAN_COTTON_SPAWN_BIOME)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.PLAINS)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.MEADOW)
                .add(Biomes.TAIGA)
                .add(Biomes.DARK_FOREST);
        tag(ModBiomeTags.CAN_GOLDENROD_SPAWN_BIOME)
                .add(Biomes.PLAINS)
                .add(Biomes.JUNGLE)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.BIRCH_FOREST);
        tag(ModBiomeTags.CAN_FOURLEAF_CLOVER_SPAWN_BIOME)
                .add(Biomes.PLAINS);
        tag(ModBiomeTags.CAN_OATS_SPAWN_BIOME)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.PLAINS)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.MEADOW);
        tag(ModBiomeTags.CAN_PEBBLE_SPAWN_BIOME)
                .add(Biomes.PLAINS)
                .add(Biomes.JUNGLE)
                .add(Biomes.DESERT)
                .add(Biomes.TAIGA)
                .add(Biomes.SAVANNA)
                .add(Biomes.BEACH)
                .add(Biomes.BADLANDS)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.FOREST);

        // ===== 主世界结构群系标签 =====
        tag(ModBiomeTags.CAN_FISHERMAN_HUT_SPAWN_BIOME)
                .add(Biomes.RIVER)
                .add(Biomes.BEACH)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP)
                .add(Biomes.PLAINS)
                .add(Biomes.FOREST);
        tag(ModBiomeTags.CAN_CAMPSITE_SPAWN_BIOME)
                .add(Biomes.PLAINS)
                .add(Biomes.FOREST)
                .add(Biomes.TAIGA)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.SAVANNA)
                .add(Biomes.MEADOW)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.JUNGLE)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.DARK_FOREST);
        tag(ModBiomeTags.CAN_DESERT_FORTRESS_SPAWN_BIOME)
                .add(Biomes.DESERT);

        tag(ModBiomeTags.CAN_GARDEN_DECRYPTION_NIPPY_EDELWEISS_SPAWN_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE);

        tag(ModBiomeTags.CAN_DYEDREAM_PAVILION_SNOWY_SPAWN_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE);

        tag(ModBiomeTags.CAN_ECOSYSTEM_BUBBLE_SPAWN_BIOME)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN);

        tag(ModBiomeTags.CAN_PINKAGARIC_HOUSE_SPAWN_BIOME)
                .add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS);

        tag(ModBiomeTags.DYEDREAM_FISHING_BIOMES)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN)
                .add(ModBiomes.DYEDREAM_COLD_OCEAN)
                .add(ModBiomes.DYEDREAM_OCEAN)
                .add(ModBiomes.DYEDREAM_RIVER)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_LAND_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS)
                .add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_OCEAN_BIOME)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN)
                .add(ModBiomes.DYEDREAM_COLD_OCEAN)
                .add(ModBiomes.DYEDREAM_OCEAN);

        tag(ModBiomeTags.DYEDREAM_WORLD_SNOWY_BIOME)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE);

        tag(BiomeTags.SPAWNS_SNOW_FOXES)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS);

        // ===== 染梦维度地物分组标签（16 群系） =====
        tag(ModBiomeTags.DYEDREAM_WORLD_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST).add(ModBiomes.DYEDREAM_FLOWER_FIELD)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN).add(ModBiomes.DYEDREAM_COLD_OCEAN)
                .add(ModBiomes.DYEDREAM_OCEAN).add(ModBiomes.DYEDREAM_BEACH)
                .add(ModBiomes.DYEDREAM_RIVER).add(ModBiomes.DYEDREAM_FROZEN_RIVER)
                .add(ModBiomes.DYEDREAM_CAVES).add(ModBiomes.DYEDREAM_LUSH_CAVES)
                .add(ModBiomes.DYEDREAM_DRIPSTONE_CAVES);

        tag(ModBiomeTags.DYEDREAM_WORLD_WATER_BIOME)
                .add(ModBiomes.DYEDREAM_COLD_OCEAN).add(ModBiomes.DYEDREAM_OCEAN)
                .add(ModBiomes.DYEDREAM_BEACH)
                .add(ModBiomes.DYEDREAM_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_SEAGRASS_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST).add(ModBiomes.DYEDREAM_FROZEN_OCEAN)
                .add(ModBiomes.DYEDREAM_COLD_OCEAN).add(ModBiomes.DYEDREAM_OCEAN)
                .add(ModBiomes.DYEDREAM_BEACH).add(ModBiomes.DYEDREAM_RIVER)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_RIVER_BIOME)
                .add(ModBiomes.DYEDREAM_RIVER)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_WARM_LAND_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST);

        tag(ModBiomeTags.DYEDREAM_WORLD_WARM_ICE_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST).add(ModBiomes.DYEDREAM_FLOWER_FIELD)
                .add(ModBiomes.DYEDREAM_OCEAN);

        tag(ModBiomeTags.DYEDREAM_WORLD_COLD_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN);

        tag(ModBiomeTags.DYEDREAM_WORLD_FREEZE_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PEAKS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_SEA_PICKLE_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST).add(ModBiomes.DYEDREAM_FLOWER_FIELD)
                .add(ModBiomes.DYEDREAM_OCEAN)
                .add(ModBiomes.DYEDREAM_BEACH).add(ModBiomes.DYEDREAM_RIVER)
                .add(ModBiomes.DYEDREAM_CAVES).add(ModBiomes.DYEDREAM_LUSH_CAVES)
                .add(ModBiomes.DYEDREAM_DRIPSTONE_CAVES);

        tag(ModBiomeTags.DYEDREAM_WORLD_PLAINS_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_FLOWER_FIELD_BIOME)
                .add(ModBiomes.DYEDREAM_FLOWER_FIELD);

        tag(ModBiomeTags.DYEDREAM_WORLD_STEM_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_FOREST);

        tag(ModBiomeTags.DYEDREAM_WORLD_VINE_BIOME)
                .add(ModBiomes.DYEDREAM_PLAINS).add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS)
                .add(ModBiomes.DYEDREAM_FOREST);

        tag(ModBiomeTags.DYEDREAM_WORLD_FOREST_BIOME)
                .add(ModBiomes.DYEDREAM_FOREST);

        tag(ModBiomeTags.DYEDREAM_WORLD_MUSHROOM_BIOME)
                .add(ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_SNOWY_ICE_PILLAR_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE)
                .add(ModBiomes.DYEDREAM_FROZEN_RIVER);

        tag(ModBiomeTags.DYEDREAM_WORLD_EDELWEISS_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE);

        tag(ModBiomeTags.DYEDREAM_WORLD_CHERRY_GROVE_BIOME)
                .add(ModBiomes.DYEDREAM_CHERRY_GROVE);

        tag(ModBiomeTags.DYEDREAM_WORLD_CLOUD_PILLAR_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_SNOWY_TREE_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_SNOWY_POOL_BIOME)
                .add(ModBiomes.DYEDREAM_SNOWY_PLAINS);

        tag(ModBiomeTags.DYEDREAM_WORLD_KELP_BIOME)
                .add(ModBiomes.DYEDREAM_COLD_OCEAN).add(ModBiomes.DYEDREAM_OCEAN);

        tag(ModBiomeTags.DYEDREAM_WORLD_ICEBERG_BIOME)
                .add(ModBiomes.DYEDREAM_FROZEN_OCEAN);

        tag(ModBiomeTags.DYEDREAM_WORLD_LUSH_BIOME)
                .add(ModBiomes.DYEDREAM_LUSH_CAVES);

        tag(ModBiomeTags.DYEDREAM_WORLD_DRIPSTONE_BIOME)
                .add(ModBiomes.DYEDREAM_DRIPSTONE_CAVES);

        // ===== 灯影之下生成群系标签 =====
        tag(ModBiomeTags.SHADOW_NYLIUM_WASTES_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_NYLIUM_WASTES);

        tag(ModBiomeTags.SHADOW_FOREST_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_FOREST);

        tag(ModBiomeTags.SHADOW_GHOST_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_RUINS);

        tag(ModBiomeTags.SHADOW_RUINS_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_RUINS);

        tag(ModBiomeTags.CAN_SHADOWNOTE_RUIN_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_RUINS);

        tag(ModBiomeTags.CAN_SHADOW_DUNGEON_SPAWN_BIOME)
                .add(ModBiomes.SHADOW_NYLIUM_WASTES)
                .add(ModBiomes.SHADOW_RUINS);

        // 风之旅途维度群系（结构与地物生成用）
        tag(ModBiomeTags.WIND_JOURNEY_WORLD_BIOME)
                .add(ModBiomes.WIND_MOOR_ARCHIPELAGO)
                .add(ModBiomes.MISTY_DREAM_CLOUD_LAYER);
    }
}
