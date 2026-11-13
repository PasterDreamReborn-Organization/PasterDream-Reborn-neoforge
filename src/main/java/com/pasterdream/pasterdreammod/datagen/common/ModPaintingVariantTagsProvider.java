package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModPaintings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModPaintingVariantTagsProvider extends TagsProvider<PaintingVariant> {
    private static final TagKey<PaintingVariant> PLACEABLE = TagKey.create(Registries.PAINTING_VARIANT,
            ResourceLocation.withDefaultNamespace("placeable"));

    public ModPaintingVariantTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                          ExistingFileHelper existingFileHelper) {
        super(output, Registries.PAINTING_VARIANT, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(PLACEABLE)
                .add(ModPaintings.PASTERDREAM_TITLE)
                .add(ModPaintings.PASTERDREAM_START)
                .add(ModPaintings.PASTERDREAM_AEROLITE_DUST)
                .add(ModPaintings.PASTERDREAM_PORTAL)
                .add(ModPaintings.PASTERDREAM_TELESCOPE)
                .add(ModPaintings.PASTERDREAM_DYEDREAM_FLOWER)
                .add(ModPaintings.PASTERDREAM_DYEDREAM_STATION)
                .add(ModPaintings.PASTERDREAM_PINK_YELLOW_HOUSE);
    }
}
