package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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
    }
}
