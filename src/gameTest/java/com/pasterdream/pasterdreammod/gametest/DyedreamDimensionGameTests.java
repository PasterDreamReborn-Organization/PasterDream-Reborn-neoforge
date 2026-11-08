package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModDimensionTypes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModNoiseSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
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
}
