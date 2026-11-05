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

    /**
     * 直接合成工具组配方：材料 + 手柄 → 剑/镐/斧/铲/锄（原版形状）。
     * 保存 ID 格式：{@code <tool>_direct}
     */
    public static void toolSetRecipes(RecipeOutput output,
                                      ItemLike material, ItemLike handle, String modId,
                                      ItemLike sword, ItemLike pickaxe, ItemLike axe,
                                      ItemLike shovel, ItemLike hoe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', material).define('b', handle)
                .unlockedBy(path(material), InventoryChangeTrigger.TriggerInstance.hasItems(material))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(sword) + "_direct"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a', material).define('b', handle)
                .unlockedBy(path(material), InventoryChangeTrigger.TriggerInstance.hasItems(material))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(pickaxe) + "_direct"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("aa")
                .pattern("ab")
                .pattern(" b")
                .define('a', material).define('b', handle)
                .unlockedBy(path(material), InventoryChangeTrigger.TriggerInstance.hasItems(material))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(axe) + "_direct"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern("a")
                .pattern("b")
                .pattern("b")
                .define('a', material).define('b', handle)
                .unlockedBy(path(material), InventoryChangeTrigger.TriggerInstance.hasItems(material))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(shovel) + "_direct"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("aa")
                .pattern(" b")
                .pattern(" b")
                .define('a', material).define('b', handle)
                .unlockedBy(path(material), InventoryChangeTrigger.TriggerInstance.hasItems(material))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(hoe) + "_direct"));
    }

    private static String path(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
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
