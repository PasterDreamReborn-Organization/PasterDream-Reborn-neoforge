package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModRecipes;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamDimensionGameTests {
    @GameTest(template = "empty")
    public static void dimensionRegistriesLoad(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        access.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(ModDimensionTypes.DYEDREAM_WORLD);
        var noise = access.lookupOrThrow(Registries.NOISE_SETTINGS)
                .getOrThrow(ModNoiseSettings.DYEDREAM_WORLD).value();
        helper.assertTrue(noise.defaultBlock().is(Blocks.CALCITE), "染梦维度默认方块应为方解石");
        helper.assertTrue(noise.seaLevel() == 63, "染梦维度海平面应为主世界高度");

        List<ResourceKey<Biome>> biomes = List.of(
                ModBiomes.DYEDREAM_PLAINS,
                ModBiomes.DYEDREAM_MUSHROOM_MOUNTAINS,
                ModBiomes.DYEDREAM_SNOWY_PLAINS,
                ModBiomes.DYEDREAM_FROZEN_OCEAN,
                ModBiomes.DYEDREAM_OCEAN,
                ModBiomes.DYEDREAM_COLD_OCEAN,
                ModBiomes.DYEDREAM_BEACH,
                ModBiomes.DYEDREAM_RIVER,
                ModBiomes.DYEDREAM_FROZEN_RIVER,
                ModBiomes.DYEDREAM_SNOWY_PEAKS,
                ModBiomes.DYEDREAM_CHERRY_GROVE,
                ModBiomes.DYEDREAM_FOREST,
                ModBiomes.DYEDREAM_FLOWER_FIELD,
                ModBiomes.DYEDREAM_CAVES,
                ModBiomes.DYEDREAM_LUSH_CAVES,
                ModBiomes.DYEDREAM_DRIPSTONE_CAVES);
        var biomeLookup = access.lookupOrThrow(Registries.BIOME);
        for (ResourceKey<Biome> biome : biomes) {
            helper.assertTrue(biomeLookup.get(biome).isPresent(), "染梦群系应已注册: " + biome.location());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dyedreamFeaturesInjected(GameTestHelper helper) {
        var access = helper.getLevel().registryAccess();
        var configured = access.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        var placed = access.lookupOrThrow(Registries.PLACED_FEATURE);

        helper.assertTrue(configured.get(ModConfiguredFeatures.DYEDREAM_TREE).isPresent(), "染梦树 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.DYEDREAM_GEODE).isPresent(), "染梦晶洞 configured feature 应已注册");
        helper.assertTrue(configured.get(ModConfiguredFeatures.CALCITE_SPIKE).isPresent(), "方解石尖锥 configured feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.DYEDREAM_TREE).isPresent(), "染梦树 placed feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.TITANIUM_ORE).isPresent(), "钛矿 placed feature 应已注册");
        helper.assertTrue(placed.get(ModPlacedFeatures.STONE_PILLAR_OCEAN).isPresent(), "海洋方解石尖锥 placed feature 应已注册");

        var plains = access.lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.DYEDREAM_PLAINS).value();
        List<Holder<PlacedFeature>> vegetation = plains.getGenerationSettings().features()
                .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal()).stream().toList();
        helper.assertTrue(vegetation.contains(placed.getOrThrow(ModPlacedFeatures.DYEDREAM_TREE)), "染梦平原应注入染梦树");
        helper.assertTrue(vegetation.contains(placed.getOrThrow(ModPlacedFeatures.STEM_GRASS_PATCH)), "染梦平原应注入茎草");

        var plainsOres = plains.getGenerationSettings().features()
                .get(GenerationStep.Decoration.UNDERGROUND_ORES.ordinal());
        helper.assertTrue(plainsOres.contains(placed.getOrThrow(ModPlacedFeatures.TITANIUM_ORE)), "染梦平原应注入钛矿");

        var dripstone = access.lookupOrThrow(Registries.BIOME)
                .getOrThrow(ModBiomes.DYEDREAM_DRIPSTONE_CAVES).value();
        var dripstoneLocal = dripstone.getGenerationSettings().features()
                .get(GenerationStep.Decoration.LOCAL_MODIFICATIONS.ordinal());
        helper.assertTrue(dripstoneLocal.contains(placed.getOrThrow(ModPlacedFeatures.CALCITE_CONE_CLUSTER_PATCH)),
                "染梦滴水洞穴应注入方解石锥簇");

        var structures = helper.getLevel().getStructureManager();
        helper.assertTrue(structures.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_pillar_0")).isPresent(),
                "����ʯ��׶�ṹģ�� stone_pillar_0 Ӧ�ɼ���");
        helper.assertTrue(structures.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "stone_pillar_1")).isPresent(),
                "����ʯ��׶�ṹģ�� stone_pillar_1 Ӧ�ɼ���");
        helper.assertTrue(structures.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_crack")).isPresent(),
                "染梦裂隙结构模板 dyedream_crack 应可加载");

        var contaminationRecipes = helper.getLevel().getRecipeManager()
                .getAllRecipesFor(ModRecipes.DYEDREAM_CONTAMINATION.get());
        helper.assertTrue(contaminationRecipes.size() == 10,
                "染梦污染配方应加载 10 条，实际 " + contaminationRecipes.size());
        helper.assertTrue(net.minecraft.core.registries.BuiltInRegistries.BLOCK.containsKey(
                        ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_crack")),
                "染梦裂隙方块应已注册");

        helper.succeed();
    }
}
