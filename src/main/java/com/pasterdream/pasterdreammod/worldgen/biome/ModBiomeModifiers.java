package com.pasterdream.pasterdreammod.worldgen.biome;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> DEEPSLATE_TITANIUM_ORE_PATCH =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "deepslate_titanium_ore_patch"));

    public static final ResourceKey<BiomeModifier> MOLTEN_GOLD_ORE_PATCH =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "molten_gold_ore_patch"));

    public static final ResourceKey<BiomeModifier> SOUL_ORE_PATCH =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "soul_ore_patch"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        var features = context.lookup(Registries.PLACED_FEATURE);
        // 保留来源 addPatch 的生成阶段，平台迁移不顺带调整矿物分布或顺序。
        context.register(DEEPSLATE_TITANIUM_ORE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(features.getOrThrow(ModPlacedFeatures.DEEPSLATE_TITANIUM_ORE_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(SOUL_ORE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModBiomeTags.CAN_SOUL_ORE_SPAWN_BIOME),
                HolderSet.direct(features.getOrThrow(ModPlacedFeatures.SOUL_ORE_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(MOLTEN_GOLD_ORE_PATCH, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(features.getOrThrow(ModPlacedFeatures.MOLTEN_GOLD_ORE_PATCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private ModBiomeModifiers() {
    }
}
