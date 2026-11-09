package com.pasterdream.pasterdreammod.datagen;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.common.*;
import com.pasterdream.pasterdreammod.datagen.lang.ModEnUsLangProvider;
import com.pasterdream.pasterdreammod.datagen.lang.ModZhCnLangProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ModDataGenerator {
    private ModDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var existing = event.getExistingFileHelper();
        generator.addProvider(event.includeServer(), new ModJukeboxSongProvider(output, event.getLookupProvider()));
        var worldgen = generator.addProvider(event.includeServer(),
                new ModWorldGenProvider(output, event.getLookupProvider()));
        var lookup = worldgen.getRegistryProvider();

        generator.addProvider(event.includeServer(), new ModFluidTagsProvider(output, lookup, existing));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(output, lookup, existing));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, lookup, existing));
        var blockTags = generator.addProvider(event.includeServer(),
                new ModBlockTagsProvider(output, lookup, existing));
        generator.addProvider(event.includeServer(),
                new ModItemTagsProvider(output, lookup, blockTags.contentsGetter(), existing));
        generator.addProvider(event.includeServer(), new ModRecipesProvider(output, lookup));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(output, lookup, existing));
        generator.addProvider(event.includeServer(), new ModChannelingEnchantmentProvider(output));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(
                        ModEntityLootTablesProvider::new, LootContextParamSets.ENTITY), new LootTableProvider.SubProviderEntry(
                        provider -> new ModPliersCuttingLootTablesProvider(), LootContextParamSets.CHEST),
                        new LootTableProvider.SubProviderEntry(
                                ModDeepTreasureLootTablesProvider::new, LootContextParamSets.CHEST),
                        new LootTableProvider.SubProviderEntry(
                                ModOverworldStructureLootTablesProvider::new, LootContextParamSets.CHEST),
                        new LootTableProvider.SubProviderEntry(
                                ModFishingLootTablesProvider::new, LootContextParamSets.FISHING)), lookup));

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existing));
        generator.addProvider(event.includeClient(), new ModItemModelsProvider(output, existing));
        generator.addProvider(event.includeClient(), new ModEnUsLangProvider(output));
        generator.addProvider(event.includeClient(), new ModZhCnLangProvider(output));
    }
}
