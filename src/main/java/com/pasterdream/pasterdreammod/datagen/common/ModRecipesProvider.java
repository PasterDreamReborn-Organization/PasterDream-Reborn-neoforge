package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        titaniumCooking(output, ModItems.TITANIUM_ORE.get(), "titanium_ore_");
        titaniumCooking(output, ModItems.DEEPSLATE_TITANIUM_ORE.get(), "deepslate_titanium_ore_");
        titaniumCooking(output, ModItems.RAW_TITANIUM.get(), "");
        RecipeHelpers.storageCompress(output, ModItems.TITANIUM_NUGGET.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(output, ModItems.RAW_TITANIUM.get(), ModItems.RAW_TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.RAW_TITANIUM_BLOCK.get(), ModItems.RAW_TITANIUM.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(output, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.TITANIUM_BLOCK.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);
    }

    private void titaniumCooking(RecipeOutput output, ItemLike ingredient, String sourceName) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC,
                        ModItems.TITANIUM_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "titanium_ingot_from_" + sourceName + "smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC,
                        ModItems.TITANIUM_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "titanium_ingot_from_" + sourceName + "blasting"));
    }
}
