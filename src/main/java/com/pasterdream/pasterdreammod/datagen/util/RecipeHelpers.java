package com.pasterdream.pasterdreammod.datagen.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

/** 保留 Forge 来源的配方 ID、分类及 9:1 数量关系。 */
public final class RecipeHelpers {
    private RecipeHelpers() {
    }

    public static void storageCompress(RecipeOutput output, ItemLike source, ItemLike result, String modId) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("aaa").pattern("aaa").pattern("aaa").define('a', source)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(source.asItem()).getPath(),
                        InventoryChangeTrigger.TriggerInstance.hasItems(source))
                .save(output, id(modId, source, result));
    }

    public static void storageDecompress(RecipeOutput output, ItemLike source, ItemLike result, String modId) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9).requires(source)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(source.asItem()).getPath(),
                        InventoryChangeTrigger.TriggerInstance.hasItems(source))
                .save(output, id(modId, source, result));
    }

    private static ResourceLocation id(String modId, ItemLike source, ItemLike result) {
        return ResourceLocation.fromNamespaceAndPath(modId,
                BuiltInRegistries.ITEM.getKey(result.asItem()).getPath() + "_from_"
                        + BuiltInRegistries.ITEM.getKey(source.asItem()).getPath());
    }
}
