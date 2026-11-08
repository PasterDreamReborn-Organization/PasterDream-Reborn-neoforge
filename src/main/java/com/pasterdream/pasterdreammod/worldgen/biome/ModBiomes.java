package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {

    public static final ResourceKey<Biome> DYEDREAM_PLAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_plains"));
    public static final ResourceKey<Biome> DYEDREAM_MUSHROOM_MOUNTAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_mushroom_mountains"));
    public static final ResourceKey<Biome> DYEDREAM_SNOWY_PLAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_plains"));
    public static final ResourceKey<Biome> DYEDREAM_FROZEN_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_ocean"));
    public static final ResourceKey<Biome> DYEDREAM_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_ocean"));
    public static final ResourceKey<Biome> DYEDREAM_COLD_OCEAN =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cold_ocean"));
    public static final ResourceKey<Biome> DYEDREAM_BEACH =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_beach"));
    public static final ResourceKey<Biome> DYEDREAM_RIVER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_river"));
    public static final ResourceKey<Biome> DYEDREAM_FROZEN_RIVER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_frozen_river"));
    public static final ResourceKey<Biome> DYEDREAM_SNOWY_PEAKS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_snowy_peaks"));
    public static final ResourceKey<Biome> DYEDREAM_CHERRY_GROVE =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_cherry_grove"));
    public static final ResourceKey<Biome> DYEDREAM_FOREST =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_forest"));
    public static final ResourceKey<Biome> DYEDREAM_FLOWER_FIELD =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_flower_field"));
    public static final ResourceKey<Biome> DYEDREAM_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_caves"));
    public static final ResourceKey<Biome> DYEDREAM_LUSH_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_lush_caves"));
    public static final ResourceKey<Biome> DYEDREAM_DRIPSTONE_CAVES =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dripstone_caves"));

    private static final ResourceKey<SoundEvent> SWEET_DREAM_MUSIC_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sweet_dream_music"));
    private static final ResourceKey<SoundEvent> SNOWFALL_DREAM_MUSIC_KEY =
            ResourceKey.create(Registries.SOUND_EVENT,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowfall_dream_music"));
    private static final int MUSIC_MIN_DELAY = 12000;
    private static final int MUSIC_MAX_DELAY = 24000;

    private static Music warmMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SWEET_DREAM_MUSIC_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, false);
    }

    private static Music coldMusic() {
        return new Music(BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(SNOWFALL_DREAM_MUSIC_KEY),
                MUSIC_MIN_DELAY, MUSIC_MAX_DELAY, false);
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(DYEDREAM_PLAINS, dyedreamPlains(placedFeatures, carvers));
        context.register(DYEDREAM_MUSHROOM_MOUNTAINS, dyedreamMushroomMountains(placedFeatures, carvers));
        context.register(DYEDREAM_SNOWY_PLAINS, dyedreamSnowyPlains(placedFeatures, carvers));
        context.register(DYEDREAM_FROZEN_OCEAN, dyedreamFrozenOcean(placedFeatures, carvers));
        context.register(DYEDREAM_COLD_OCEAN, dyedreamColdOcean(placedFeatures, carvers));
        context.register(DYEDREAM_OCEAN, dyedreamOcean(placedFeatures, carvers));
        context.register(DYEDREAM_BEACH, dyedreamBeach(placedFeatures, carvers));
        context.register(DYEDREAM_RIVER, dyedreamRiver(placedFeatures, carvers));
        context.register(DYEDREAM_FROZEN_RIVER, dyedreamFrozenRiver(placedFeatures, carvers));
        context.register(DYEDREAM_SNOWY_PEAKS, dyedreamSnowyPeaks(placedFeatures, carvers));
        context.register(DYEDREAM_CHERRY_GROVE, dyedreamCherryGrove(placedFeatures, carvers));
        context.register(DYEDREAM_FOREST, dyedreamForest(placedFeatures, carvers));
        context.register(DYEDREAM_FLOWER_FIELD, dyedreamFlowerField(placedFeatures, carvers));
        context.register(DYEDREAM_CAVES, dyedreamCaves(placedFeatures, carvers));
        context.register(DYEDREAM_LUSH_CAVES, dyedreamLushCaves(placedFeatures, carvers));
        context.register(DYEDREAM_DRIPSTONE_CAVES, dyedreamDripstoneCaves(placedFeatures, carvers));
    }

    // ==================== 共享辅助方法 ====================

    /** sky / fog / water 颜色：所有染梦群系完全相同 */
    private static BiomeSpecialEffects.Builder commonEffects() {
        return new BiomeSpecialEffects.Builder()
                .skyColor(0x79A6FF)
                .fogColor(0xC0D8FF)
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533);
    }

    /** 寒冷群系的 foliage / grass 颜色 */
    private static void applyColdFoliage(BiomeSpecialEffects.Builder effects) {
        effects.foliageColorOverride(0xFFFFA9ED)
                .grassColorOverride(0xFFFFABEE);
    }

    // ==================== 各群系构建方法 ====================

    private static Biome dyedreamPlains(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamMushroomMountains(HolderGetter<PlacedFeature> placedFeatures,
                                                    HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFCB3ED)
                .grassColorOverride(0xFFFFABEE)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.6f)
                .downfall(0.2f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 10, 4, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamSnowyPlains(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.7f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFrozenOcean(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.25f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 30, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 20, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamOcean(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.003f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.2f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 15, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 1, 1, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamColdOcean(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.003f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 15, 1, 3))
                        .addSpawn(MobCategory.WATER_CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SQUID, 1, 1, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.COD, 15, 1, 5))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamBeach(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamRiver(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFrozenRiver(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.005f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.0f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT,
                                new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamSnowyPeaks(HolderGetter<PlacedFeature> placedFeatures,
                                             HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.SNOWFLAKE, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.7f)
                .downfall(0.9f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamCherryGrove(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects();
        applyColdFoliage(effects);
        effects.ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(coldMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(-0.2f)
                .downfall(0.8f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamForest(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.7f)
                .downfall(0.8f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 15, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 10, 4, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamFlowerField(HolderGetter<PlacedFeature> placedFeatures,
                                              HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .foliageColorOverride(0xFFFDC6F2)
                .grassColorOverride(0xFFFDC6F2)
                .ambientParticle(new AmbientParticleSettings(ParticleTypes.CHERRY_LEAVES, 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        gen.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(1.0f)
                .downfall(0.35f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.ALLAY, 20, 1, 2))
                        .addSpawn(MobCategory.CREATURE,
                                new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4))
                        .build())
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamCaves(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamLushCaves(HolderGetter<PlacedFeature> placedFeatures,
                                            HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.01f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.5f)
                .downfall(0.5f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }

    private static Biome dyedreamDripstoneCaves(HolderGetter<PlacedFeature> placedFeatures,
                                                 HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeSpecialEffects.Builder effects = commonEffects()
                .ambientParticle(new AmbientParticleSettings(ModParticleTypes.LEAVES_PARTICLE.get(), 0.005f))
                .backgroundMusic(warmMusic());

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, carvers);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8f)
                .downfall(0.4f)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .specialEffects(effects.build())
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(gen.build())
                .build();
    }
}
