package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WindJourneyDimensionGameTests {
    @GameTest(template = "empty")
    public static void dimensionRegistriesLoad(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        access.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(ModDimensionTypes.WIND_JOURNEY_WORLD);
        var noise = access.lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(ModNoiseSettings.WIND_JOURNEY_WORLD).value();
        helper.assertTrue(noise.defaultBlock().is(ModBlocks.THICK_CLOUD.get()), "风之旅途默认方块应为厚云");
        helper.assertTrue(noise.defaultFluid().is(net.minecraft.world.level.block.Blocks.WATER), "风之旅途默认流体应为水");
        helper.assertTrue(noise.seaLevel() == 0, "风之旅途海平面应为 0");

        var biomeLookup = access.lookupOrThrow(Registries.BIOME);
        for (ResourceKey<Biome> biome : List.of(ModBiomes.WIND_MOOR_ARCHIPELAGO,
                ModBiomes.MISTY_DREAM_CLOUD_LAYER)) {
            helper.assertTrue(biomeLookup.get(biome).isPresent(), "风之旅途群系应已注册: " + biome.location());
        }
        helper.assertTrue(biomeLookup.get(ModBiomeTags.WIND_JOURNEY_WORLD_BIOME).isPresent(),
                "风之旅途群系标签应已注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windFeaturesInjected(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        var configured = access.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        var placed = access.lookupOrThrow(Registries.PLACED_FEATURE);

        helper.assertTrue(configured.get(ModConfiguredFeatures.CONGEAL_WIND_ORE).isPresent(), "凝风矿 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.WIND_JOURNEY_CLOUD_PATCH).isPresent(), "风之旅途云团 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.WIND_JOURNEY_FIREFLY_NEST).isPresent(), "风之旅途萤火虫巢 configured feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.WIND_RUNNER_CRYSTAL_ORE).isPresent(), "破风骑士水晶矿 placed feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.WIND_JOURNEY_REED_PATCH).isPresent(), "风岛芦苇 placed feature 应已注册");

        var moor = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.WIND_MOOR_ARCHIPELAGO).value();
        var moorOres = moor.getGenerationSettings().features()
                .get(GenerationStep.Decoration.UNDERGROUND_ORES.ordinal());
        helper.assertTrue(moorOres.contains(placed.getOrThrow(ModPlacedFeatures.CONGEAL_WIND_ORE)),
                "风泊群岛应注入凝风矿");
        helper.assertTrue(moorOres.contains(placed.getOrThrow(ModPlacedFeatures.WIND_RUNNER_CRYSTAL_ORE)),
                "风泊群岛应注入破风骑士水晶矿");
        List<Holder<PlacedFeature>> moorVegetation = moor.getGenerationSettings().features()
                .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal()).stream().toList();
        helper.assertTrue(moorVegetation.contains(placed.getOrThrow(ModPlacedFeatures.WIND_JOURNEY_FIREFLY_NEST)),
                "风泊群岛应注入萤火虫巢");
        helper.assertTrue(moorVegetation.contains(placed.getOrThrow(ModPlacedFeatures.WIND_JOURNEY_REED_PATCH)),
                "风泊群岛应注入风岛芦苇");

        var cloudLayer = access.lookupOrThrow(Registries.BIOME)
                .getOrThrow(ModBiomes.MISTY_DREAM_CLOUD_LAYER).value();
        var cloudStructures = cloudLayer.getGenerationSettings().features()
                .get(GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal());
        helper.assertTrue(cloudStructures.contains(placed.getOrThrow(ModPlacedFeatures.WIND_JOURNEY_CLOUD_PATCH_LOW)),
                "迷梦云层应注入低位云团");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windMechanicsRegistriesLoad(GameTestHelper helper) {
        helper.getLevel().getGameRules().getRule(ModGameRules.WIND_DIRECTION);
        helper.assertTrue(ModEffects.TAILWIND.isBound(), "顺风效果应已注册");
        helper.assertTrue(ModEffects.DEADWIND.isBound(), "逆风效果应已注册");
        helper.assertTrue(ModEffects.CLOUD_MIST.isBound(), "云雾效果应已注册");
        helper.assertTrue(ModItems.WIND_KNIGHT_FLAG.isBound(), "风骑士旗帜应已注册");
        helper.succeed();
    }
}
