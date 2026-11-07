package com.pasterdream.pasterdreammod.datagen.util;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

    public static void plankFamilyRecipes(RecipeOutput output,
                                          ItemLike log, ItemLike planks,
                                          ItemLike stairs, ItemLike slab,
                                          ItemLike fence, ItemLike fenceGate,
                                          ItemLike door, ItemLike trapdoor,
                                          ItemLike pressurePlate, ItemLike button,
                                          String modId) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(log)
                .unlockedBy(path(log), InventoryChangeTrigger.TriggerInstance.hasItems(log))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(planks) + "_from_log"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .pattern("aba")
                .pattern("aba")
                .define('a', planks)
                .define('b', Items.STICK)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate, 1)
                .pattern("aba")
                .pattern("aba")
                .define('a', Items.STICK)
                .define('b', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .pattern("aa")
                .pattern("aa")
                .pattern("aa")
                .define('a', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 2)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pressurePlate, 1)
                .pattern("aa")
                .define('a', planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, button, 1)
                .requires(planks)
                .unlockedBy(path(planks), InventoryChangeTrigger.TriggerInstance.hasItems(planks))
                .save(output);
    }

    public static void buildingBlockFamilyRecipes(RecipeOutput output,
                                                  ItemLike base, ItemLike stairs,
                                                  ItemLike slab, ItemLike wall,
                                                  String modId) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', base)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("aaa")
                .define('a', base)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', base)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output);

        var ingredient = Ingredient.of(base);
        SingleItemRecipeBuilder.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, stairs)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(stairs) + "_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, slab, 2)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(slab) + "_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(ingredient, RecipeCategory.BUILDING_BLOCKS, wall)
                .unlockedBy(path(base), InventoryChangeTrigger.TriggerInstance.hasItems(base))
                .save(output, ResourceLocation.fromNamespaceAndPath(modId, path(wall) + "_from_stonecutting"));
    }

    /**
     * 小蛋糕合成配方：奶油小蛋糕 + 材料。
     *
     * @param material 合成材料
     * @param cake     合成出的小蛋糕
     */
    public static void cake(RecipeOutput output, ItemLike material, ItemLike cake) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, cake)
                .requires(ModItems.CREAM_BUN_CAKE.get())
                .requires(material)
                .unlockedBy(path(ModItems.CREAM_BUN_CAKE.get()),
                        InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CREAM_BUN_CAKE.get()))
                .save(output);
    }

    /**
     * 心形巧克力合成配方。
     *
     * @param heart  心形巧克力中心材料
     * @param result 合成出的心形巧克力
     */
    public static void heartChocolate(RecipeOutput output, ItemLike heart, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 2)
                .pattern("a a")
                .pattern("aca")
                .pattern(" b ")
                .define('a', Items.COCOA_BEANS)
                .define('b', Items.SUGAR)
                .define('c', heart)
                .unlockedBy(path(heart), InventoryChangeTrigger.TriggerInstance.hasItems(heart))
                .save(output);
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
