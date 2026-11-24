package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import com.pasterdream.pasterdreammod.init.ModPaintings;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomeModifiers;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModDimensionTypes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModLevelStems;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModNoiseSettings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantment::bootstrap)
            .add(Registries.PAINTING_VARIANT, ModPaintings::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(Registries.BIOME, ModBiomes::bootstrap)
            .add(Registries.DIMENSION_TYPE, ModDimensionTypes::bootstrap)
            .add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
            .add(Registries.LEVEL_STEM, ModLevelStems::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, BUILDER, Set.of(PasterDreamMod.MOD_ID));
    }
}
