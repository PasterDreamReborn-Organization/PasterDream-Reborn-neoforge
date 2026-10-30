package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        dustRecipes(output);
        quartzRecipes(output);
        blackStickRecipes(output);
        materialRecipes(output);
        titaniumUpgradeRecipe(output);
        titaniumToolRecipes(output);
        titaniumArmorRecipes(output);
        moltenGoldToolRecipes(output);
        titaniumCooking(output, ModItems.TITANIUM_ORE.get(), "titanium_ore_");
        titaniumCooking(output, ModItems.DEEPSLATE_TITANIUM_ORE.get(), "deepslate_titanium_ore_");
        titaniumCooking(output, ModItems.RAW_TITANIUM.get(), "");
        RecipeHelpers.storageCompress(output, ModItems.TITANIUM_NUGGET.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(output, ModItems.RAW_TITANIUM.get(), ModItems.RAW_TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.RAW_TITANIUM_BLOCK.get(), ModItems.RAW_TITANIUM.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(output, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.TITANIUM_BLOCK.get(), ModItems.TITANIUM_INGOT.get(), PasterDreamMod.MOD_ID);
        moltenGoldCooking(output, ModItems.MOLTEN_GOLD_ORE.get(), "molten_gold_ingot_from_molten_gold_ore_");
        // 来源粗炙焰金的配方 ID 使用历史拼写 moltengold，迁移时不更名。
        moltenGoldCooking(output, ModItems.RAW_MOLTEN_GOLD.get(), "moltengold_ingot_from_");
        RecipeHelpers.storageCompress(output, ModItems.MOLTEN_GOLD_NUGGET.get(), ModItems.MOLTEN_GOLD_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.MOLTEN_GOLD_INGOT.get(), ModItems.MOLTEN_GOLD_NUGGET.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(output, ModItems.MOLTEN_GOLD_INGOT.get(), ModItems.MOLTEN_GOLD_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(output, ModItems.MOLTEN_GOLD_BLOCK.get(), ModItems.MOLTEN_GOLD_INGOT.get(), PasterDreamMod.MOD_ID);
    }

    private void dustRecipes(RecipeOutput pWriter) {
        // 烧制：染梦粉尘矿石 → 染梦粉尘碎片
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_DUST_ORE.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_DUST_PIECE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_ORE.get()), has(ModItems.DYEDREAM_DUST_ORE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_dust_piece_from_smelting"));

        // 染梦粉尘 → 9× 染梦粉尘碎片
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DUST_PIECE.get(), 9)
                .requires(ModItems.DYEDREAM_DUST.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST.get()), has(ModItems.DYEDREAM_DUST.get()))
                .save(pWriter);

        // 9× 染梦粉尘碎片 → 染梦粉尘
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DUST.get(), 1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(), 9)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get()))
                .save(pWriter);
    }

    private void quartzRecipes(RecipeOutput pWriter) {
        // 烧制：染梦石英矿石 → 染梦石英
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_QUARTZ_ORE.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_QUARTZ.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_ORE.get()), has(ModItems.DYEDREAM_QUARTZ_ORE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_from_smelting"));

        // 烧制：染梦石英块 → 平滑染梦石英块
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_QUARTZ_BLOCK.get()),
                        RecipeCategory.MISC, ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "smooth_dyedream_quartz_block_from_smelting"));

        // 4×染梦石英 → 染梦石英块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_QUARTZ.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ.get()), has(ModItems.DYEDREAM_QUARTZ.get()))
                .save(pWriter);

        // 4×染梦石英块 → 染梦石英砖 ×4
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 2×染梦石英块 → 染梦石英柱 ×2
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), 2)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 3×染梦石英块 → 染梦石英台阶 ×6
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), 6)
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 染梦石英块 → 染梦石英楼梯 ×4
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), 4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 染梦石英块 → 染梦石英墙 ×6
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get(), 6)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter);

        // 2×染梦石英台阶 → 錾制染梦石英块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get()))
                .save(pWriter);

        // 切石机配方
        var quartzIngredient = Ingredient.of(ModItems.DYEDREAM_QUARTZ_BLOCK.get());

        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_block_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "smooth_dyedream_quartz_block_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "bricks_dyedream_quartz_block_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pillar_dyedream_quartz_block_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "chiseled_dyedream_quartz_block_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_block_stairs_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), 2)
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_block_slab_from_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(quartzIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_QUARTZ_BLOCK.get()), has(ModItems.DYEDREAM_QUARTZ_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_quartz_block_wall_from_stonecutting"));
    }

    private void blackStickRecipes(RecipeOutput pWriter) {
        // 黑石 → 黑石棍（斜向）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_STICK.get(), 1)
                .pattern("  a")
                .pattern(" b ")
                .pattern("a  ")
                .define('a', Items.BLACKSTONE)
                .define('b', Items.BLACKSTONE)
                .unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE))
                .save(pWriter);

        // 黑石 → 黑石棍（直向）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_STICK.get(), 1)
                .pattern("a")
                .pattern("b")
                .pattern("a")
                .define('a', Items.BLACKSTONE)
                .define('b', Items.BLACKSTONE)
                .unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_stick_straight"));
    }

    private void titaniumToolRecipes(RecipeOutput pWriter) {
        // 工作台升级：钻石工具 + 2钛金锭 + 黑石棍 → 钛金工具（保留附魔）
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .requires(Items.DIAMOND_SWORD)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_sword_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .requires(Items.DIAMOND_PICKAXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_pickaxe_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .requires(Items.DIAMOND_AXE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_axe_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .requires(Items.DIAMOND_SHOVEL)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_shovel_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .requires(Items.DIAMOND_HOE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_hoe_crafting");

        // 锻造台配方：燧石 + 钻石工具 + 钛金升级套件 → 钛金工具
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_SWORD),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_SWORD.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_sword_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_PICKAXE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_PICKAXE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_pickaxe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_AXE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_AXE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_axe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_SHOVEL),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_SHOVEL.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_shovel_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.FLINT),
                        Ingredient.of(Items.DIAMOND_HOE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.TITANIUM_HOE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_hoe_smithing"));
    }

    private void titaniumUpgradeRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TITANIUM_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', ModItems.TITANIUM_INGOT.get())
                .define('c', Items.STONE_BUTTON)
                .define('d', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
    }

    // ===== 材料配方 =====
    private void materialRecipes(RecipeOutput pWriter) {
        // 线轴
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPOOL.get(), 1)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', Items.STRING)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(pWriter);

        // 纺织布料
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FABRIC.get(), 1)
                .requires(Ingredient.of(ItemTags.WOOL))
                .requires(ModItems.COTTON.get())
                .requires(ModItems.SPOOL.get())
                .unlockedBy(getHasName(ModItems.SPOOL.get()), has(ModItems.SPOOL.get()))
                .save(pWriter);

        // 护甲板
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROTECT_DECK.get(), 1)
                .pattern("aa")
                .pattern("bc")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.LEATHER)
                .define('c', ModItems.FABRIC.get())
                .unlockedBy(getHasName(ModItems.FABRIC.get()), has(ModItems.FABRIC.get()))
                .save(pWriter);
    }

    // ===== 钛金装备配方 =====
    private void titaniumArmorRecipes(RecipeOutput pWriter) {
        // 工作台升级：钻石装备 + 2钛金锭 + 黑石棍 → 钛金装备（保留附魔）
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .requires(Items.DIAMOND_HELMET)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_helmet_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .requires(Items.DIAMOND_CHESTPLATE)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_chestplate_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .requires(Items.DIAMOND_LEGGINGS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_leggings_crafting");
        saveNbtPreserving(ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .requires(Items.DIAMOND_BOOTS)
                .requires(ModItems.TITANIUM_INGOT.get(), 2)
                .requires(ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get())),
                pWriter, "titanium_boots_crafting");

        // 工作台合成：钛金锭 + 护甲板 → 钛金装备
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .pattern("aba")
                .pattern("a a")
                .define('a', ModItems.TITANIUM_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_helmet_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .pattern("a a")
                .pattern("bbb")
                .pattern("aba")
                .define('a', ModItems.PROTECT_DECK.get()).define('b', ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_chestplate_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .pattern("aba")
                .pattern("a a")
                .pattern("b b")
                .define('a', ModItems.TITANIUM_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_leggings_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_boots_from_protect_deck"));

        // 锻造台配方：护甲板 + 钻石装备 + 钛金升级套件 → 钛金装备
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_HELMET),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_HELMET.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_helmet_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_CHESTPLATE),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_CHESTPLATE.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_chestplate_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_LEGGINGS),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_LEGGINGS.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_leggings_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(Items.DIAMOND_BOOTS),
                        Ingredient.of(ModItems.TITANIUM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.TITANIUM_BOOTS.get())
                .unlocks("has_titanium_upgrade", has(ModItems.TITANIUM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "titanium_boots_smithing"));
    }

    private void saveNbtPreserving(ShapelessRecipeBuilder builder, RecipeOutput output, String name) {
        builder.save(new RecipeOutput() {
            @Override
            public Advancement.Builder advancement() {
                return output.advancement();
            }

            @Override
            public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
                output.accept(id, new NbtPreservingShapelessRecipe((ShapelessRecipe) recipe), advancement, conditions);
            }
        }, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    // ===== 炙焰金工具配方 =====
    private void moltenGoldToolRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MOLTEN_GOLD_SWORD.get())
                .pattern("a").pattern("a").pattern("b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_PICKAXE.get())
                .pattern("aaa").pattern(" b ").pattern(" b ")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_AXE.get())
                .pattern("aa").pattern("ab").pattern(" b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_SHOVEL.get())
                .pattern("a").pattern("b").pattern("b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MOLTEN_GOLD_HOE.get())
                .pattern("aa").pattern(" b").pattern(" b")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get()).define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);
    }

    private void moltenGoldCooking(RecipeOutput output, ItemLike ingredient, String recipePrefix) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC,
                        ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, recipePrefix + "smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC,
                        ModItems.MOLTEN_GOLD_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, recipePrefix + "blasting"));
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
