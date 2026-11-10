package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModDimensionTypes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModNoiseSettings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class LampShadowDimensionGameTests {
    @GameTest(template = "empty")
    public static void dimensionRegistriesLoad(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        access.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(ModDimensionTypes.LAMP_SHADOW_WORLD);
        var noise = access.lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(ModNoiseSettings.LAMP_SHADOW_WORLD).value();
        helper.assertTrue(noise.defaultBlock().is(ModBlocks.SHADOW_STONE.get()), "灯影维度默认方块应为阴影石");
        helper.assertTrue(noise.defaultFluid().is(ModBlocks.SHADOW_LIQUID.get()), "灯影维度默认流体应为影液");
        helper.assertTrue(noise.seaLevel() == 63, "灯影维度海平面应为主世界高度");

        List<ResourceKey<Biome>> biomes = List.of(
                ModBiomes.SHADOW_NYLIUM_WASTES,
                ModBiomes.SHADOW_FOREST,
                ModBiomes.SHADOW_RUINS,
                ModBiomes.SHADOW_OCEAN);
        var biomeLookup = access.lookupOrThrow(Registries.BIOME);
        for (ResourceKey<Biome> biome : biomes) {
            helper.assertTrue(biomeLookup.get(biome).isPresent(), "灯影群系应已注册: " + biome.location());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void lampShadowFeaturesInjected(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        var configured = access.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        var placed = access.lookupOrThrow(Registries.PLACED_FEATURE);

        helper.assertTrue(configured.get(ModConfiguredFeatures.SHADOW_FUNGUS_TREE).isPresent(), "阴影真菌树 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.SHADOW_RUIN_WALL).isPresent(), "阴影残垣断壁 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.SHADOW_HAND).isPresent(), "暗影之手 configured feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.SHADOW_CHAIN_PILLAR).isPresent(), "阴影锁链柱 placed feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.SHADOW_TOMB).isPresent(), "阴影古墓 placed feature 应已注册");

        var wastes = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.SHADOW_NYLIUM_WASTES).value();
        var wastesStructures = wastes.getGenerationSettings().features()
                .get(GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal());
        helper.assertTrue(wastesStructures.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_CHAIN_PILLAR)),
                "菌索荒原应注入阴影锁链柱");
        List<Holder<PlacedFeature>> wastesVegetation = wastes.getGenerationSettings().features()
                .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal()).stream().toList();
        helper.assertTrue(wastesVegetation.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_SPROUTS_PATCH)),
                "菌索荒原应注入影芽");
        helper.assertTrue(wastesVegetation.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_FUNGUS_PATCH)),
                "菌索荒原应注入影菌");

        var forest = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.SHADOW_FOREST).value();
        List<Holder<PlacedFeature>> forestVegetation = forest.getGenerationSettings().features()
                .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal()).stream().toList();
        helper.assertTrue(forestVegetation.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_FUNGUS_TREE)),
                "阴影森林应注入阴影真菌树");

        var ruins = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.SHADOW_RUINS).value();
        var ruinsStructures = ruins.getGenerationSettings().features()
                .get(GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal());
        helper.assertTrue(ruinsStructures.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_RUIN_WALL)),
                "阴影古迹应注入残垣断壁");
        helper.assertTrue(ruinsStructures.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_TOMB)),
                "阴影古迹应注入古墓");

        var ocean = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.SHADOW_OCEAN).value();
        var oceanStructures = ocean.getGenerationSettings().features()
                .get(GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal());
        helper.assertTrue(oceanStructures.contains(placed.getOrThrow(ModPlacedFeatures.SHADOW_CHAIN_PILLAR_OCEAN)),
                "阴影之海应注入海底锁链柱");

        var structures = helper.getLevel().getStructureManager();
        for (String name : List.of("shadow_hand", "shadow_tomb_0", "shadow_tomb_1", "shadow_light_lantern")) {
            helper.assertTrue(structures.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name)).isPresent(),
                    "灯影地物结构模板应可加载: " + name);
        }

        helper.succeed();
    }
}
