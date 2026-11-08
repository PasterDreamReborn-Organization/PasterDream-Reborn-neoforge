package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public static final TagKey<Fluid> CAULDRON_SPRING = TagKey.create(Registries.FLUID,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cauldron_spring"));
    public static final TagKey<Fluid> INK = TagKey.create(Registries.FLUID,
            ResourceLocation.fromNamespaceAndPath("c", "ink"));

    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CAULDRON_SPRING).add(ModFluids.MELT_DREAM_LIQUID.get());
        tag(CauldronPotionFluidTags.POTION).add(ModFluids.POTION.get());
        tag(INK).add(ModFluids.INK.get());
    }
}
