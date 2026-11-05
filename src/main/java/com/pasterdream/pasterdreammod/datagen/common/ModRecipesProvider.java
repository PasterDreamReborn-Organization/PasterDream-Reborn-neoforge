package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapedRecipe;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.List;
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
        pinkSlimeRecipes(output);
        titaniumUpgradeRecipe(output);
        sculkUpgradeRecipe(output);
        copperToolRecipes(output);
        copperArmorRecipes(output);
        congealWindIronToolRecipes(output);
        fluffyWindAlloyToolRecipes(output);
        titaniumToolRecipes(output);
        titaniumArmorRecipes(output);
        sculkArmorRecipes(output);
        dyedreamUpgradeRecipe(output);
        fluffyWindAlloyUpgradeRecipe(output);
        windJourneyMaterialRecipes(output);
        dyedreamToolRecipes(output);
        meltDreamToolRecipes(output);
        dyedreamArmorRecipes(output);
        meltDreamCrystalArmorRecipes(output);
        budBuildingRecipes(output);
        alloyRecipes(output);
        moltenGoldToolRecipes(output);
        hellfireToolRecipes(output);
        infernoSwordRecipe(output);
        tideSwordRecipes(output);
        grassSwordRecipe(output);
        jungleSporeRecipe(output);
        lostSwordTombRecipe(output);
        weaponWorkshopRecipe(output);
        windwreathedThunderSpearRecipe(output);
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

    // ===== 铜工具配方 =====

    private void copperToolRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_SWORD.get())
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_AXE.get())
                .pattern("aa")
                .pattern("ab")
                .pattern(" b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_SHOVEL.get())
                .pattern("a")
                .pattern("b")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_HOE.get())
                .pattern("aa")
                .pattern(" b")
                .pattern(" b")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.STICK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
    }

    // ===== 铜装备配方 =====

    private void copperArmorRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.COPPER_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
    }

    // ===== 融梦水晶装备配方（锻造台：生命水晶 + 对应染梦装备 + 融梦水晶碎片） =====

    private void meltDreamCrystalArmorRecipes(RecipeOutput pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.LIFE_CRYSTAL.get()),
                        Ingredient.of(ModItems.DYEDREAM_HELMET.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.MELT_DREAM_HELMET.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_helmet_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.LIFE_CRYSTAL.get()),
                        Ingredient.of(ModItems.DYEDREAM_CHESTPLATE.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.MELT_DREAM_CHESTPLATE.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_chestplate_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.LIFE_CRYSTAL.get()),
                        Ingredient.of(ModItems.DYEDREAM_LEGGINGS.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.MELT_DREAM_LEGGINGS.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_leggings_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.LIFE_CRYSTAL.get()),
                        Ingredient.of(ModItems.DYEDREAM_BOOTS.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.MELT_DREAM_BOOTS.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_boots_smithing"));
    }

    // ===== 粉色粘液配方 =====
    private void pinkSlimeRecipes(RecipeOutput pWriter) {
        RecipeHelpers.storageCompress(pWriter, ModItems.PINK_SLIMEBALL.get(), ModItems.PINK_SLIME_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.PINK_SLIME_BLOCK.get(), ModItems.PINK_SLIMEBALL.get(), PasterDreamMod.MOD_ID);
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

    // ===== 凝风铁工具配方 =====

    private void congealWindIronToolRecipes(RecipeOutput pWriter) {
        RecipeHelpers.toolSetRecipes(pWriter,
                ModItems.CONGEAL_WIND_IRON_INGOT.get(), ModItems.BLACK_STICK.get(), PasterDreamMod.MOD_ID,
                ModItems.CONGEAL_WIND_IRON_SWORD.get(), ModItems.CONGEAL_WIND_IRON_PICKAXE.get(),
                ModItems.CONGEAL_WIND_IRON_AXE.get(), ModItems.CONGEAL_WIND_IRON_SHOVEL.get(),
                ModItems.CONGEAL_WIND_IRON_HOE.get());
    }

    // ===== 萦风合金工具配方（锻造台：凝结之风 + 凝风铁工具 + 萦风合金升级套件） =====

    private void fluffyWindAlloyToolRecipes(RecipeOutput pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.CONGEAL_WIND.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_SWORD.get()),
                        Ingredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.FLUFFY_WIND_ALLOY_SWORD.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluffy_wind_alloy_sword_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.CONGEAL_WIND.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_PICKAXE.get()),
                        Ingredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluffy_wind_alloy_pickaxe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.CONGEAL_WIND.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_AXE.get()),
                        Ingredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.FLUFFY_WIND_ALLOY_AXE.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluffy_wind_alloy_axe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.CONGEAL_WIND.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_SHOVEL.get()),
                        Ingredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluffy_wind_alloy_shovel_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.CONGEAL_WIND.get()),
                        Ingredient.of(ModItems.CONGEAL_WIND_IRON_HOE.get()),
                        Ingredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.FLUFFY_WIND_ALLOY_HOE.get())
                .unlocks("has_fluffy_wind_alloy_upgrade", has(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluffy_wind_alloy_hoe_smithing"));
    }

    // ===== 萦风合金升级套件与材料配方 =====

    private void fluffyWindAlloyUpgradeRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                .define('c', ModItems.TITANIUM_UPGRADE.get())
                .define('d', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.FLUFFY_WIND_ALLOY_INGOT.get()), has(ModItems.FLUFFY_WIND_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    private void windJourneyMaterialRecipes(RecipeOutput pWriter) {
        // 凝风铁锭 ↔ 凝风铁粒
        RecipeHelpers.storageCompress(pWriter, ModItems.CONGEAL_WIND_IRON_NUGGET.get(),
                ModItems.CONGEAL_WIND_IRON_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.CONGEAL_WIND_IRON_INGOT.get(),
                ModItems.CONGEAL_WIND_IRON_NUGGET.get(), PasterDreamMod.MOD_ID);
        // 萦风合金锭：凝风铁锭 + 脉冲风行者水晶 + 钛金锭 + 凝结之风
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLUFFY_WIND_ALLOY_INGOT.get(), 2)
                .pattern("dcd")
                .pattern("aba")
                .pattern("dcd")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', ModItems.PULSE_WIND_RUNNER_CRYSTAL.get())
                .define('c', ModItems.TITANIUM_INGOT.get())
                .define('d', ModItems.CONGEAL_WIND.get())
                .unlockedBy(getHasName(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()), has(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()))
                .save(pWriter);
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

    private void sculkUpgradeRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCULK_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.ECHO_SHARD)
                .define('c', ModItems.SCULK_HEART.get())
                .define('d', Items.SCULK_VEIN)
                .unlockedBy(getHasName(ModItems.SCULK_HEART.get()), has(ModItems.SCULK_HEART.get()))
                .save(pWriter);
    }

    private void dyedreamUpgradeRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_UPGRADE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', Items.IRON_INGOT)
                .define('b', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .define('c', ModItems.DYEDREAM_BUD_NUGGET.get())
                .define('d', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    // ===== 染梦合金工具配方 =====

    private void dyedreamToolRecipes(RecipeOutput pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_SWORD.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_SWORD.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_sword_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_PICKAXE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_PICKAXE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_pickaxe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_AXE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_AXE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_axe_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_SHOVEL.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_SHOVEL.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_shovel_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_BUD_NUGGET.get()),
                        Ingredient.of(ModItems.TITANIUM_HOE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.TOOLS, ModItems.DYEDREAM_HOE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_hoe_smithing"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_DUST.get()),
                        Ingredient.of(ModItems.DYEDREAM_SWORD.get()),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.COMBAT, ModItems.SHARP_MELT_DREAM_SWORD.get())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_sharp_sword_smithing"));

        // 染梦合金锤（工作台合成）
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DYEDREAM_HAMMER.get())
                .pattern("aaa")
                .pattern("aba")
                .pattern(" b ")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .define('b', ModItems.BLACK_STICK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    private void meltDreamToolRecipes(RecipeOutput pWriter) {
        meltDreamToolRecipe(pWriter, ModItems.DYEDREAM_PICKAXE.get(), ModItems.MELT_DREAM_PICKAXE.get(), "melt_dream_pickaxe_smithing");
        meltDreamToolRecipe(pWriter, ModItems.DYEDREAM_AXE.get(), ModItems.MELT_DREAM_AXE.get(), "melt_dream_axe_smithing");
        meltDreamToolRecipe(pWriter, ModItems.DYEDREAM_SHOVEL.get(), ModItems.MELT_DREAM_SHOVEL.get(), "melt_dream_shovel_smithing");
        meltDreamToolRecipe(pWriter, ModItems.DYEDREAM_HOE.get(), ModItems.MELT_DREAM_HOE.get(), "melt_dream_hoe_smithing");
    }

    private void meltDreamToolRecipe(RecipeOutput pWriter, ItemLike base, ItemLike result, String id) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        Ingredient.of(base),
                        Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        RecipeCategory.TOOLS, result.asItem())
                .unlocks("has_melt_dream_crystal_fragment", has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, id));
    }

    // ===== 染梦合金装备配方 =====

    private void dyedreamArmorRecipes(RecipeOutput pWriter) {
        // 工作台合成：染梦合金锭 + 护甲板 → 染梦合金装备
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_HELMET.get())
                .pattern("aba")
                .pattern("a a")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_helmet_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_CHESTPLATE.get())
                .pattern("a a")
                .pattern("bbb")
                .pattern("aba")
                .define('a', ModItems.PROTECT_DECK.get()).define('b', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_chestplate_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_LEGGINGS.get())
                .pattern("aba")
                .pattern("a a")
                .pattern("b b")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get()).define('b', ModItems.PROTECT_DECK.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_leggings_from_protect_deck"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DYEDREAM_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a', ModItems.DYEDREAM_ALLOY_INGOT.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_ALLOY_INGOT.get()), has(ModItems.DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_boots_from_protect_deck"));

        // 锻造台配方：护甲板 + 钛金装备 + 染梦升级套件 → 染梦合金装备
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_HELMET.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_HELMET.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_helmet_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_CHESTPLATE.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_CHESTPLATE.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_chestplate_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_LEGGINGS.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_LEGGINGS.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_leggings_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.PROTECT_DECK.get()),
                        Ingredient.of(ModItems.TITANIUM_BOOTS.get()),
                        Ingredient.of(ModItems.DYEDREAM_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.DYEDREAM_BOOTS.get())
                .unlocks("has_dyedream_upgrade", has(ModItems.DYEDREAM_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_boots_smithing"));
    }

    // ===== 染梦合金配方 =====

    private void alloyRecipes(RecipeOutput pWriter) {
        // 染梦合金锭粗胚：钛金锭 + 染梦粉尘 + 4×染梦晶芽粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_DYEDREAM_ALLOY_INGOT.get(), 1)
                .requires(ModItems.TITANIUM_INGOT.get())
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(ModItems.DYEDREAM_BUD_NUGGET.get(), 4)
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);

        // 染梦合金锭粗胚 → 染梦合金锭（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_alloy_ingot_from_smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()),
                        RecipeCategory.MISC, ModItems.DYEDREAM_ALLOY_INGOT.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()), has(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_alloy_ingot_from_blasting"));

        // 染梦合金锭 ↔ 粒
        RecipeHelpers.storageCompress(pWriter, ModItems.DYEDREAM_ALLOY_NUGGET.get(), ModItems.DYEDREAM_ALLOY_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.DYEDREAM_ALLOY_INGOT.get(), ModItems.DYEDREAM_ALLOY_NUGGET.get(), PasterDreamMod.MOD_ID);

        // 染梦合金锭 ↔ 块
        RecipeHelpers.storageCompress(pWriter, ModItems.DYEDREAM_ALLOY_INGOT.get(), ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModBlocks.DYEDREAM_ALLOY_BLOCK.get(), ModItems.DYEDREAM_ALLOY_INGOT.get(), PasterDreamMod.MOD_ID);
    }

    // ===== 染梦水晶建材配方 =====

    private void budBuildingRecipes(RecipeOutput pWriter) {
        // 4× 染梦晶芽粒 → 染梦水晶块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_BUD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_BUD_NUGGET.get()), has(ModItems.DYEDREAM_BUD_NUGGET.get()))
                .save(pWriter);

        // 染梦水晶块 → 4× 染梦晶芽粒（分解）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_BUD_NUGGET.get(), 4)
                .requires(ModBlocks.DYEDREAM_BUD_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_BUD_BLOCK.get()), has(ModBlocks.DYEDREAM_BUD_BLOCK.get()))
                .save(pWriter);
    }

    // ===== 材料配方 =====
    private void materialRecipes(RecipeOutput pWriter) {
        // 蔚蓝海洋之心
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLUE_HEART_OF_THE_SEA.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.GLOW_INK_SAC)
                .define('b', ModItems.BLUE_DEW.get())
                .define('c', Items.HEART_OF_THE_SEA)
                .unlockedBy(getHasName(Items.HEART_OF_THE_SEA), has(Items.HEART_OF_THE_SEA))
                .save(pWriter);

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

    // ===== 回响装备配方 =====

    private void sculkArmorRecipes(RecipeOutput pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_HELMET),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_HELMET.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk_helmet_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_CHESTPLATE),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_CHESTPLATE.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk_chestplate_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_LEGGINGS),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_LEGGINGS.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk_leggings_smithing"));
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.SCULK_VEIN),
                        Ingredient.of(Items.NETHERITE_BOOTS),
                        Ingredient.of(ModItems.SCULK_UPGRADE.get()),
                        RecipeCategory.COMBAT, ModItems.SCULK_BOOTS.get())
                .unlocks("has_sculk_upgrade", has(ModItems.SCULK_UPGRADE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sculk_boots_smithing"));
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

    private void saveNbtPreservingShaped(ShapedRecipeBuilder builder, RecipeOutput output, String name) {
        builder.save(new RecipeOutput() {
            @Override
            public Advancement.Builder advancement() {
                return output.advancement();
            }

            @Override
            public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
                output.accept(id, new NbtPreservingShapedRecipe((ShapedRecipe) recipe), advancement, conditions);
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

    // ===== 狱炎工具配方 =====

    private void hellfireToolRecipes(RecipeOutput pWriter) {
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.HELLFIRE_SWORD.get())
                .pattern(" a ").pattern("bcb").pattern(" d ")
                .define('a', Items.BLAZE_POWDER)
                .define('b', Items.NETHERITE_SCRAP)
                .define('c', ModItems.MOLTEN_GOLD_SWORD.get())
                .define('d', ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_SWORD.get()), has(ModItems.MOLTEN_GOLD_SWORD.get())),
                pWriter, "hellfire_sword");
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.HELLFIRE_PICKAXE.get())
                .pattern(" a ").pattern("bcb").pattern(" d ")
                .define('a', Items.BLAZE_POWDER)
                .define('b', Items.NETHERITE_SCRAP)
                .define('c', ModItems.MOLTEN_GOLD_PICKAXE.get())
                .define('d', ModBlocks.MOLTEN_GOLD_BLOCK.get())
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_PICKAXE.get()), has(ModItems.MOLTEN_GOLD_PICKAXE.get())),
                pWriter, "hellfire_pickaxe");
    }

    // ===== 引潮剑配方 =====

    // ===== 『融骸』狱炎剑配方 =====

    private void infernoSwordRecipe(RecipeOutput pWriter) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHER_BRICK),
                        Ingredient.of(ModItems.HELLFIRE_SWORD.get()),
                        Ingredient.of(Items.NETHER_STAR),
                        RecipeCategory.COMBAT, ModItems.INFERNO_SWORD.get())
                .unlocks("has_nether_star", has(Items.NETHER_STAR))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "inferno_sword_smithing"));
    }

    // ===== 草薙配方 =====

    private void grassSwordRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.KUSANAGI.get())
                .pattern(" ab")
                .pattern("aca")
                .pattern("da ")
                .define('a', ModItems.JUNGLE_SPORE.get())
                .define('b', Items.SPORE_BLOSSOM)
                .define('c', ModItems.SWORD_EMBRYO.get())
                .define('d', Items.EMERALD_BLOCK)
                .unlockedBy(getHasName(ModItems.JUNGLE_SPORE.get()), has(ModItems.JUNGLE_SPORE.get()))
                .save(pWriter);
    }

    // ===== 丛林孢子配方 =====

    private void jungleSporeRecipe(RecipeOutput pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JUNGLE_SPORE.get(), 2)
                .requires(ModItems.JUNGLE_SPORE.get())
                .requires(Items.BONE_MEAL)
                .unlockedBy(getHasName(ModItems.JUNGLE_SPORE.get()), has(ModItems.JUNGLE_SPORE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "jungle_spore_from_bone_meal"));
    }

    // ===== 失落剑冢配方 =====

    private void lostSwordTombRecipe(RecipeOutput pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LOST_SWORD_TOMB.get(), 1)
                .requires(ModItems.SWORD_EMBRYO.get())
                .requires(Items.MOSSY_COBBLESTONE)
                .unlockedBy(getHasName(ModItems.SWORD_EMBRYO.get()), has(ModItems.SWORD_EMBRYO.get()))
                .save(pWriter);
    }

    private void tideSwordRecipes(RecipeOutput pWriter) {
        // 引潮剑
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.TIDE_SWORD.get())
                .pattern(" ab")
                .pattern("aca")
                .pattern("da ")
                .define('a', Items.PRISMARINE_SHARD)
                .define('b', Items.PRISMARINE_CRYSTALS)
                .define('c', Items.TRIDENT)
                .define('d', ModItems.ELDER_GUARDIAN_SCALE.get())
                .unlockedBy(getHasName(ModItems.ELDER_GUARDIAN_SCALE.get()), has(ModItems.ELDER_GUARDIAN_SCALE.get()))
                .save(pWriter);

        // 『北海若』引潮 (锻造台)
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.PRISMARINE_CRYSTALS),
                Ingredient.of(ModItems.TIDE_SWORD.get()),
                Ingredient.of(ModItems.BLUE_HEART_OF_THE_SEA.get()),
                RecipeCategory.COMBAT, ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .unlocks("has_tide_sword", has(ModItems.TIDE_SWORD.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "beihairuo_tide_sword_smithing"));
    }

    private void weaponWorkshopRecipe(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), 1)
                .pattern("abc")
                .pattern("d d")
                .define('a', Items.ANVIL)
                .define('b', Items.SMITHING_TABLE)
                .define('c', Items.MAGMA_BLOCK)
                .define('d', Items.CUT_COPPER)
                .unlockedBy(getHasName(Items.ANVIL), has(Items.ANVIL))
                .unlockedBy(getHasName(Items.SMITHING_TABLE), has(Items.SMITHING_TABLE))
                .unlockedBy(getHasName(Items.MAGMA_BLOCK), has(Items.MAGMA_BLOCK))
                .unlockedBy(getHasName(Items.CUT_COPPER), has(Items.CUT_COPPER))
                .save(output, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "weapon_workshop_crafting_table"));
    }

    private void windwreathedThunderSpearRecipe(RecipeOutput output) {
        List<ItemIngredient> itemInputs = List.of(
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_SWORD.get(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_AXE.get(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_HOE.get(), 1));
        ItemIngredient enhanceStone = ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1);
        ItemIngredient itemOutput = ItemIngredient.of(ModItems.WINDWREATHED_THUNDER_SPEAR.get(), 1);
        WeaponWorkshopCraftingTableRecipe recipe = new WeaponWorkshopCraftingTableRecipe(itemInputs, enhanceStone, itemOutput);
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/windwreathed_thunder_spear"), recipe, null);
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
