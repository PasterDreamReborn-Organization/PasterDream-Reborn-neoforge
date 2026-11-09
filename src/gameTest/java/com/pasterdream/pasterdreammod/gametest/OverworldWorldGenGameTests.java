package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class OverworldWorldGenGameTests {
    @GameTest(template = "empty")
    public static void overworldFeaturesRegistered(GameTestHelper helper) {
        var lookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
        List<ResourceKey<PlacedFeature>> keys = List.of(
                ModPlacedFeatures.FOURLEAF_CLOVER_PATCH,
                ModPlacedFeatures.GOLDENROD_PATCH,
                ModPlacedFeatures.FERRARIA_CRISPA_PATCH,
                ModPlacedFeatures.MALVA_SINENSIS_CAVAN_PATCH,
                ModPlacedFeatures.EUSTOMA_PATCH,
                ModPlacedFeatures.OATS_PATCH,
                ModPlacedFeatures.RYE_PATCH,
                ModPlacedFeatures.WHITE_COROLLA_CROP_PATCH,
                ModPlacedFeatures.COTTON_CROP_PATCH,
                ModPlacedFeatures.JUNGLE_SPORANGIUM_PATCH,
                ModPlacedFeatures.REED_PATCH,
                ModPlacedFeatures.PEBBLE_PATCH);
        for (ResourceKey<PlacedFeature> key : keys) {
            helper.assertTrue(lookup.get(key).isPresent(), "主世界放置地物应已注册: " + key.location());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void overworldStructuresAndTemplatesLoad(GameTestHelper helper) {
        var structureLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE);
        var setLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE_SET);
        List<String> structures = List.of(
                "oak_fisherman_hut",
                "spruce_fisherman_hut",
                "campsite_overworld",
                "desert_cottage",
                "desert_fortress",
                "the_lost_sword_tomb",
                "dyedream_crack");
        List<String> templates = List.of(
                "oak_fisherman_hut",
                "spruce_fisherman_hut",
                "campsite_overworld",
                "desert_cottage",
                "desert_fortress_top",
                "desert_fortress_bottom",
                "the_lost_sword_tomb",
                "dyedream_crack");
        var manager = helper.getLevel().getStructureManager();
        for (String name : structures) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(structureLookup.get(ResourceKey.create(Registries.STRUCTURE, id)).isPresent(),
                    "主世界结构应已注册: " + id);
            helper.assertTrue(setLookup.get(ResourceKey.create(Registries.STRUCTURE_SET, id)).isPresent(),
                    "主世界结构集应已注册: " + id);
        }
        for (String name : templates) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(manager.get(id).isPresent(), "主世界结构模板应可加载: " + id);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void overworldStructureLootTablesLoad(GameTestHelper helper) {
        var server = helper.getLevel().getServer();
        List<ResourceKey<LootTable>> keys = List.of(
                ModLootTables.PICNIC_BASKET_OVERWORLD_LOOT,
                ModLootTables.FISHERMAN_HUT_CHEST_LOOT,
                ModLootTables.DESERT_FORTRESS_CHEST_LOOT,
                ModLootTables.DESERT_COTTAGE_CHEST_LOOT);
        for (ResourceKey<LootTable> key : keys) {
            helper.assertTrue(server.reloadableRegistries().getLootTable(key) != LootTable.EMPTY,
                    "主世界结构战利品表应可加载: " + key.location());
        }
        helper.succeed();
    }
}
