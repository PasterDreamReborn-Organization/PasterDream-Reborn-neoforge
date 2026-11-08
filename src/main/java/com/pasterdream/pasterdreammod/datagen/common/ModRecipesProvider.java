package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.datagen.util.RecipeHelpers;
import com.pasterdream.pasterdreammod.helper.ContainerBalanceHelper;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapedRecipe;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarCraftingRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import net.minecraft.core.registries.Registries;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    private final List<Runnable> containerValidations = new ArrayList<>();

    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        dustRecipes(output);
        soulRecipes(output);
        curioRecipes(output);
        quartzRecipes(output);
        calciteRecipes(output);
        calciteOreCookingRecipes(output);
        dyedreamBlockRecipes(output);
        dyedreamMaterialRecipes(output);
        blackStickRecipes(output);
        materialRecipes(output);
        chargedAmethystAndSaltRecipes(output);
        mortarRecipes(output);
        pebbleRecipes(output);
        glassJarRecipes(output);
        elixirBottleRecipes(output);
        glassCupRecipes(output);
        foodRecipes(output);
        storageBagRecipes(output);
        blackMetalRecipes(output);
        shadowBuildingRecipes(output);
        shadowBookshelfRecipes(output);
        shadowAlloyToolRecipes(output);
        shadowAlloyCrossbowRecipe(output);
        shadowErosionWorkshopRecipes(output);
        pinkSlimeRecipes(output);
        titaniumUpgradeRecipe(output);
        sculkUpgradeRecipe(output);
        copperToolRecipes(output);
        copperArmorRecipes(output);
        congealWindIronToolRecipes(output);
        fluffyWindAlloyToolRecipes(output);
        fluffyWindBowRecipe(output);
        windRunnerCloakRecipe(output);
        fluffyWindAlloyArmorRecipes(output);
        titaniumToolRecipes(output);
        titaniumArmorRecipes(output);
        sculkArmorRecipes(output);
        dyedreamUpgradeRecipe(output);
        fluffyWindAlloyUpgradeRecipe(output);
        windJourneyMaterialRecipes(output);
        windJourneyBlockRecipes(output);
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
        dyedreamDeskRecipes(output);
        equipmentDeviceRecipes(output);
        claypanRecipes(output);
        dreamAccumulatorRecipe(output);
        musicDiscRecipes(output);
        windwreathedThunderSpearRecipe(output);
        expansionWeaponWorkshopRecipes(output);
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

        validateContainerBalance();
    }

    private void soulRecipes(RecipeOutput pWriter) {
        // 灵魂矿土 → 灵魂粉尘（熔炉 + 高炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SOUL_ORE.get()),
                        RecipeCategory.MISC, ModItems.SOUL_DUST.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.SOUL_ORE.get()), has(ModItems.SOUL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":soul_dust_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.SOUL_ORE.get()),
                        RecipeCategory.MISC, ModItems.SOUL_DUST.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.SOUL_ORE.get()), has(ModItems.SOUL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":soul_dust_from_blasting");

        // 灵魂精华：1×恶魂之泪 + 8×灵魂粉尘
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOUL_ESSENCE.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.SOUL_DUST.get())
                .define('b', Items.GHAST_TEAR)
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter);

        // 护身符原胚 = 线 + 灵魂精华 + 钛金粒
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_CHARM.get(), 1)
                .pattern("a")
                .pattern("b")
                .pattern("c")
                .define('a', Items.STRING)
                .define('b', ModItems.SOUL_ESSENCE.get())
                .define('c', ModItems.TITANIUM_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 项链原胚 = 钛金粒 ×5 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_NECKLACE.get(), 1)
                .pattern("aaa")
                .pattern("a a")
                .pattern(" b ")
                .define('a', ModItems.TITANIUM_NUGGET.get())
                .define('b', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 戒指原胚 = 钛金粒 ×3 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_RING.get(), 1)
                .pattern(" a ")
                .pattern("b b")
                .pattern(" b ")
                .define('a', ModItems.SOUL_ESSENCE.get())
                .define('b', ModItems.TITANIUM_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 腰带原胚 = 钛金粒 ×5 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMBRYO_BELT.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .define('a', ModItems.TITANIUM_NUGGET.get())
                .define('b', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);
    }

    private void curioRecipes(RecipeOutput pWriter) {
        // 雪兔脚项链 = 项链原胚 + 兔子脚 + 细雪桶 + 钻石 + 下界疣
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RABBIT_FOOT_NECKLACE.get(), 1)
                .requires(ModItems.EMBRYO_NECKLACE.get())
                .requires(Items.RABBIT_FOOT)
                .requires(Items.POWDER_SNOW_BUCKET)
                .requires(Items.DIAMOND)
                .requires(Items.NETHER_WART)
                .unlockedBy(getHasName(ModItems.EMBRYO_NECKLACE.get()), has(ModItems.EMBRYO_NECKLACE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GOLD_CHARM.get(), 1)
                .requires(ModItems.EMBRYO_CHARM.get())
                .requires(Items.GOLD_INGOT)
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDEYE_CHARM.get(), 1)
                .requires(ModItems.EMBRYO_CHARM.get())
                .requires(Items.ENDER_EYE)
                .requires(Items.CHORUS_FLOWER)
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CARAPAX_CHARM.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.SEAGRASS)
                .define('b', Items.TURTLE_SCUTE)
                .define('c', ModItems.EMBRYO_CHARM.get())
                .unlockedBy(getHasName(ModItems.EMBRYO_CHARM.get()), has(ModItems.EMBRYO_CHARM.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GARLAND.get(), 1)
                .pattern("aaa")
                .pattern("a a")
                .pattern("aaa")
                .define('a', ItemTags.SMALL_FLOWERS)
                .unlockedBy("has_poppy", has(ItemTags.SMALL_FLOWERS))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NATURE_BELT.get(), 1)
                .pattern("aba")
                .pattern("b b")
                .pattern("aba")
                .define('a', Items.GLOW_BERRIES)
                .define('b', Items.VINE)
                .unlockedBy(getHasName(Items.GLOW_BERRIES), has(Items.GLOW_BERRIES))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TRAVELER_BELT.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" d ")
                .define('a', ModItems.FABRIC.get())
                .define('b', Items.LEATHER)
                .define('c', ModItems.EMBRYO_BELT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy(getHasName(ModItems.EMBRYO_BELT.get()), has(ModItems.EMBRYO_BELT.get()))
                .save(pWriter);

        //4个红露滴戒指配方
        ItemStack redDewRingLv1 = RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 1);
        ItemStack redDewRingLv2 = RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 2);
        ItemStack redDewRingLv3 = RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 3);
        ItemStack redDewRingLv4 = RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 4);
        CompoundTag redDewRingLv1Tag = new CompoundTag();
        redDewRingLv1Tag.putInt(RedDewRingItem.TAG_LV, 1);
        CompoundTag redDewRingLv2Tag = new CompoundTag();
        redDewRingLv2Tag.putInt(RedDewRingItem.TAG_LV, 2);
        CompoundTag redDewRingLv3Tag = new CompoundTag();
        redDewRingLv3Tag.putInt(RedDewRingItem.TAG_LV, 3);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, redDewRingLv1)
                .requires(ModItems.EMBRYO_RING.get())
                .requires(Items.GOLD_INGOT)
                .requires(ModItems.RED_DEW.get())
                .unlockedBy(getHasName(ModItems.EMBRYO_RING.get()), has(ModItems.EMBRYO_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "red_dew_ring_lv1"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, redDewRingLv2)
                .requires(DataComponentIngredient.of(false, DataComponents.CUSTOM_DATA,
                        CustomData.of(redDewRingLv1Tag), ModItems.RED_DEW_RING.get()))
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(ModItems.RED_DEW.get())
                .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "red_dew_ring_lv2"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, redDewRingLv3)
                .requires(DataComponentIngredient.of(false, DataComponents.CUSTOM_DATA,
                        CustomData.of(redDewRingLv2Tag), ModItems.RED_DEW_RING.get()))
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(ModItems.RED_DEW.get())
                .requires(ModItems.MELT_DREAM_LIQUID_BUCKET.get())
                .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "red_dew_ring_lv3"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, redDewRingLv4)
                .requires(DataComponentIngredient.of(false, DataComponents.CUSTOM_DATA,
                        CustomData.of(redDewRingLv3Tag), ModItems.RED_DEW_RING.get()))
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(ModItems.RED_DEW.get())
                .requires(ModItems.LIFE_CRYSTAL.get())
                .unlockedBy(getHasName(ModItems.RED_DEW_RING.get()), has(ModItems.RED_DEW_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "red_dew_ring_lv4"));

        ItemStack strikeRingLv1 = StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 1);
        ItemStack strikeRingLv2 = StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 2);
        CompoundTag strikeRingLv1Tag = new CompoundTag();
        strikeRingLv1Tag.putInt(StrikeRingItem.TAG_LV, 1);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, strikeRingLv1)
                .pattern(" c ")
                .pattern("bab")
                .pattern(" b ")
                .define('a', ModItems.EMBRYO_RING.get())
                .define('b', Items.COPPER_INGOT)
                .define('c', Items.BLAZE_POWDER)
                .unlockedBy(getHasName(ModItems.EMBRYO_RING.get()), has(ModItems.EMBRYO_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "strike_ring_lv1"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, strikeRingLv2)
                .pattern("cbc")
                .pattern("bab")
                .pattern("cbc")
                .define('a', DataComponentIngredient.of(false, DataComponents.CUSTOM_DATA,
                        CustomData.of(strikeRingLv1Tag), ModItems.STRIKE_RING.get()))
                .define('b', ModItems.MOLTEN_GOLD_INGOT.get())
                .define('c', Items.BLAZE_ROD)
                .unlockedBy(getHasName(ModItems.STRIKE_RING.get()), has(ModItems.STRIKE_RING.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "strike_ring_lv2"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MELT_DREAM_ENERGY_RING.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.EMBRYO_RING.get())
                .unlockedBy(getHasName(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()),
                        has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "melt_dream_energy_ring_from_embryo"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRIGHT_BUTTERFLY_CURIO.get(), 1)
                .requires(ModItems.LIGHT_BUTTERFLY_CURIO.get())
                .requires(ModItems.WHITE_CRYSTAL.get())
                .requires(ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.LIGHT_BUTTERFLY_CURIO.get()), has(ModItems.LIGHT_BUTTERFLY_CURIO.get()))
                .save(pWriter);
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

    // ===== 方解石系列配方 =====

    private void calciteRecipes(RecipeOutput pWriter) {
        // 4× 方解石 → 4× 磨制方解石
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.POLISHED_CALCITE.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', Items.CALCITE)
                .unlockedBy(getHasName(Items.CALCITE), has(Items.CALCITE))
                .save(pWriter);

        // 磨制方解石 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.POLISHED_CALCITE.get(), ModItems.POLISHED_CALCITE_STAIRS.get(),
                ModItems.POLISHED_CALCITE_SLAB.get(), ModItems.POLISHED_CALCITE_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 2×2 磨制方解石 → 4× 方解石瓦
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CALCITE_TILES.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(ModItems.POLISHED_CALCITE.get()), has(ModItems.POLISHED_CALCITE.get()))
                .save(pWriter);

        // 方解石瓦 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CALCITE_TILES.get(), ModItems.CALCITE_TILES_STAIRS.get(),
                ModItems.CALCITE_TILES_SLAB.get(), ModItems.CALCITE_TILES_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 跨系列切石机配方
        var calciteIngredient = Ingredient.of(Items.CALCITE);
        var polishedCalciteIngredient = Ingredient.of(ModItems.POLISHED_CALCITE.get());

        SingleItemRecipeBuilder.stonecutting(calciteIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(Items.CALCITE), has(Items.CALCITE))
                .save(pWriter, PasterDreamMod.MOD_ID + ":polished_calcite_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(polishedCalciteIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CALCITE_TILES.get())
                .unlockedBy(getHasName(ModItems.POLISHED_CALCITE.get()), has(ModItems.POLISHED_CALCITE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":calcite_tiles_from_stonecutting");
    }

    private void calciteOreCookingRecipes(RecipeOutput pWriter) {
        // ===== 染梦世界·方解石原版矿物（熔炉/高炉直接熔炼为原版产物） =====
        // 方解石铁矿 → 铁锭
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_IRON_ORE.get()),
                        RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_IRON_ORE.get()), has(ModItems.CALCITE_IRON_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":iron_ingot_from_calcite_iron_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_IRON_ORE.get()),
                        RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_IRON_ORE.get()), has(ModItems.CALCITE_IRON_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":iron_ingot_from_calcite_iron_ore_blasting");
        // 方解石铜矿 → 铜锭
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_COPPER_ORE.get()),
                        RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_COPPER_ORE.get()), has(ModItems.CALCITE_COPPER_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":copper_ingot_from_calcite_copper_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_COPPER_ORE.get()),
                        RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_COPPER_ORE.get()), has(ModItems.CALCITE_COPPER_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":copper_ingot_from_calcite_copper_ore_blasting");
        // 方解石金矿 → 金锭
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_GOLD_ORE.get()),
                        RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_GOLD_ORE.get()), has(ModItems.CALCITE_GOLD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":gold_ingot_from_calcite_gold_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_GOLD_ORE.get()),
                        RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_GOLD_ORE.get()), has(ModItems.CALCITE_GOLD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":gold_ingot_from_calcite_gold_ore_blasting");
        // 方解石煤矿 → 煤炭
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_COAL_ORE.get()),
                        RecipeCategory.MISC, Items.COAL, 0.1F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_COAL_ORE.get()), has(ModItems.CALCITE_COAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":coal_from_calcite_coal_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_COAL_ORE.get()),
                        RecipeCategory.MISC, Items.COAL, 0.1F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_COAL_ORE.get()), has(ModItems.CALCITE_COAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":coal_from_calcite_coal_ore_blasting");
        // 方解石红石矿石 → 红石粉
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_REDSTONE_ORE.get()),
                        RecipeCategory.REDSTONE, Items.REDSTONE, 0.7F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_REDSTONE_ORE.get()), has(ModItems.CALCITE_REDSTONE_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":redstone_from_calcite_redstone_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_REDSTONE_ORE.get()),
                        RecipeCategory.REDSTONE, Items.REDSTONE, 0.7F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_REDSTONE_ORE.get()), has(ModItems.CALCITE_REDSTONE_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":redstone_from_calcite_redstone_ore_blasting");
        // 方解石青金石矿石 → 青金石
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_LAPIS_ORE.get()),
                        RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_LAPIS_ORE.get()), has(ModItems.CALCITE_LAPIS_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":lapis_lazuli_from_calcite_lapis_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_LAPIS_ORE.get()),
                        RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_LAPIS_ORE.get()), has(ModItems.CALCITE_LAPIS_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":lapis_lazuli_from_calcite_lapis_ore_blasting");
        // 方解石钻石矿石 → 钻石
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_DIAMOND_ORE.get()),
                        RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_DIAMOND_ORE.get()), has(ModItems.CALCITE_DIAMOND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":diamond_from_calcite_diamond_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_DIAMOND_ORE.get()),
                        RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_DIAMOND_ORE.get()), has(ModItems.CALCITE_DIAMOND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":diamond_from_calcite_diamond_ore_blasting");
        // 方解石绿宝石矿石 → 绿宝石
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CALCITE_EMERALD_ORE.get()),
                        RecipeCategory.MISC, Items.EMERALD, 1.0F, 200)
                .unlockedBy(getHasName(ModItems.CALCITE_EMERALD_ORE.get()), has(ModItems.CALCITE_EMERALD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":emerald_from_calcite_emerald_ore_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CALCITE_EMERALD_ORE.get()),
                        RecipeCategory.MISC, Items.EMERALD, 1.0F, 100)
                .unlockedBy(getHasName(ModItems.CALCITE_EMERALD_ORE.get()), has(ModItems.CALCITE_EMERALD_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":emerald_from_calcite_emerald_ore_blasting");
    }

    private void dyedreamBlockRecipes(RecipeOutput pWriter) {
        // 染梦原木 → 染梦木头
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_LOG.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_LOG.get()), has(ModItems.DYEDREAM_LOG.get()))
                .save(pWriter);

        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.DYEDREAM_LOG.get(),
                ModItems.DYEDREAM_PLANKS.get(),
                ModItems.DYEDREAM_STAIRS.get(),
                ModItems.DYEDREAM_SLAB.get(),
                ModItems.DYEDREAM_FENCE.get(),
                ModItems.DYEDREAM_FENCE_GATE.get(),
                ModItems.DYEDREAM_DOOR.get(),
                ModItems.DYEDREAM_TRAPDOOR.get(),
                ModItems.DYEDREAM_PRESSURE_PLATE.get(),
                ModItems.DYEDREAM_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PLANKS.get(), 4)
                .requires(ModItems.DYEDREAM_WOOD.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_WOOD.get()), has(ModItems.DYEDREAM_WOOD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_planks_from_wood");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.DYEDREAM_PLANKS.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_PLANKS.get()), has(ModItems.DYEDREAM_PLANKS.get()))
                .save(pWriter);

        dyeConversion(pWriter, Items.DIRT, ModBlocks.DYEDREAM_DIRT.get().asItem());
        dyeConversion(pWriter, Items.GRASS_BLOCK, ModBlocks.DYEDREAM_GRASS_BLOCK.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LEAVES, ModBlocks.DYEDREAM_LEAVES.get().asItem());
        dyeConversionTag(pWriter, ItemTags.LOGS, ModBlocks.DYEDREAM_LOG.get().asItem());
        dyeConversionTag(pWriter, ItemTags.PLANKS, ModBlocks.DYEDREAM_PLANKS.get().asItem());
        dyeConversion(pWriter, Items.SHROOMLIGHT, ModBlocks.PINK_SHROOMLIGHT.get().asItem());
        dyeConversion(pWriter, Items.ICE, ModBlocks.DYEDREAM_ICE.get().asItem());
        dyeConversion(pWriter, Items.PACKED_ICE, ModBlocks.DYEDREAM_PACKED_ICE.get().asItem());
        dyeConversion(pWriter, Items.GLASS, ModBlocks.DYEDREAM_GLASS.get().asItem());
    }

    private void dyedreamMaterialRecipes(RecipeOutput pWriter) {
        // 9 染梦冰 → 1 染梦浮冰
        RecipeHelpers.storageCompress(pWriter, ModBlocks.DYEDREAM_ICE.get(), ModBlocks.DYEDREAM_PACKED_ICE.get(), PasterDreamMod.MOD_ID);

        // ===== 染梦玻璃系列 =====
        // 染梦沙 → 染梦玻璃（熔炉）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DYEDREAM_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_GLASS.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_SAND.get()), has(ModBlocks.DYEDREAM_SAND.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":dyedream_glass_from_smelting");

        // 染梦玻璃 → 16× 染梦玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_GLASS.get()), has(ModBlocks.DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 4× 染梦玻璃 → 4× 雕花染梦玻璃
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_DYEDREAM_GLASS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_GLASS.get()), has(ModBlocks.DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 雕花染梦玻璃 → 16× 雕花染梦玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.CARVE_DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 雕花染梦玻璃 + 金粒 → 镶金雕花染梦玻璃
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(), 1)
                .pattern("ab")
                .define('a', ModBlocks.CARVE_DYEDREAM_GLASS.get())
                .define('b', Items.GOLD_NUGGET)
                .unlockedBy(getHasName(ModBlocks.CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);

        // 镶金雕花染梦玻璃 → 16× 镶金雕花染梦玻璃板
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get())
                .unlockedBy(getHasName(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get()), has(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get()))
                .save(pWriter);

        // ===== 染梦灯 =====
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get(), 2)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ModItems.DYEDREAM_QUARTZ.get())
                .define('b', ModBlocks.DYEDREAM_GLASS.get())
                .define('c', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_LANTERN.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Items.IRON_NUGGET)
                .define('b', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter);

        // ===== 染梦水晶建材 =====
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DYEDREAM_BUD_BRICKS.get(), 4)
                .pattern("ab")
                .pattern("ba")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .define('b', ModItems.DYEDREAM_BUD_NUGGET.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_BUD_NUGGET.get()), has(ModItems.DYEDREAM_BUD_NUGGET.get()))
                .save(pWriter);

        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModBlocks.DYEDREAM_BUD_BRICKS.get(), ModBlocks.DYEDREAM_BUD_STAIRS.get(),
                ModBlocks.DYEDREAM_BUD_SLAB.get(), ModBlocks.DYEDREAM_BUD_WALL.get(),
                PasterDreamMod.MOD_ID);

        // ===== 染梦砂岩 =====
        // 4× 染梦沙 → 染梦砂岩
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DYEDREAM_SANDSTONE.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModBlocks.DYEDREAM_SAND.get())
                .unlockedBy(getHasName(ModBlocks.DYEDREAM_SAND.get()), has(ModBlocks.DYEDREAM_SAND.get()))
                .save(pWriter);

        // 4× 染梦砂岩 → 4× 切制染梦砂岩
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_DYEDREAM_SANDSTONE.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.DYEDREAM_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_SANDSTONE.get()), has(ModItems.DYEDREAM_SANDSTONE.get()))
                .save(pWriter);

        // 2× 染梦砂岩台阶 → 雕纹染梦砂岩
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_SANDSTONE.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.DYEDREAM_SANDSTONE_SLAB.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_SANDSTONE_SLAB.get()), has(ModItems.DYEDREAM_SANDSTONE_SLAB.get()))
                .save(pWriter);

        // 烧炼：染梦砂岩 → 平滑染梦砂岩
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DYEDREAM_SANDSTONE.get()),
                        RecipeCategory.MISC, ModItems.SMOOTH_DYEDREAM_SANDSTONE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.DYEDREAM_SANDSTONE.get()), has(ModItems.DYEDREAM_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":smooth_dyedream_sandstone_from_smelting");

        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.DYEDREAM_SANDSTONE.get(), ModItems.DYEDREAM_SANDSTONE_STAIRS.get(),
                ModItems.DYEDREAM_SANDSTONE_SLAB.get(), ModItems.DYEDREAM_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CUT_DYEDREAM_SANDSTONE.get(), ModItems.CUT_DYEDREAM_SANDSTONE_STAIRS.get(),
                ModItems.CUT_DYEDREAM_SANDSTONE_SLAB.get(), ModItems.CUT_DYEDREAM_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SMOOTH_DYEDREAM_SANDSTONE.get(), ModItems.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get(),
                ModItems.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get(), ModItems.SMOOTH_DYEDREAM_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);

        var sandstoneIngredient = Ingredient.of(ModItems.DYEDREAM_SANDSTONE.get());
        SingleItemRecipeBuilder.stonecutting(sandstoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_DYEDREAM_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_SANDSTONE.get()), has(ModItems.DYEDREAM_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cut_dyedream_sandstone_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(sandstoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_DYEDREAM_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_SANDSTONE.get()), has(ModItems.DYEDREAM_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_dyedream_sandstone_from_stonecutting");
    }

    private void dyeConversion(RecipeOutput writer, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 8)
                .pattern("aaa")
                .pattern("ada")
                .pattern("aaa")
                .define('a', material)
                .define('d', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
    }

    private void dyeConversionTag(RecipeOutput writer, TagKey<Item> tag, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 8)
                .pattern("aaa")
                .pattern("ada")
                .pattern("aaa")
                .define('a', Ingredient.of(tag))
                .define('d', ModItems.DYEDREAM_DYE.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(writer, PasterDreamMod.MOD_ID + ":" + getItemName(result) + "_from_dye");
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

    private void fluffyWindBowRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FLUFFY_WIND_BOW.get())
                .pattern("abb")
                .pattern("bcd")
                .pattern("bd ")
                .define('a', ModItems.BLACK_STICK.get())
                .define('b', ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                .define('c', ModItems.PULSE_WIND_RUNNER_CRYSTAL.get())
                .define('d', Items.STRING)
                .unlockedBy(getHasName(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()), has(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()))
                .save(pWriter);
    }

    private void windRunnerCloakRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WIND_RUNNER_CLOAK.get(), 1)
                .pattern("aba")
                .pattern("aca")
                .pattern("c c")
                .define('a', ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                .define('b', ModItems.FRACTURED_ANGEL_STATUE.get())
                .define('c', ModItems.CONGEAL_WIND.get())
                .unlockedBy(getHasName(ModItems.FRACTURED_ANGEL_STATUE.get()), has(ModItems.FRACTURED_ANGEL_STATUE.get()))
                .save(pWriter);
    }

    private void fluffyWindAlloyArmorRecipes(RecipeOutput output) {
        fluffyWindAlloyArmorRecipe(output, ModItems.TITANIUM_HELMET.get(), ModItems.FLUFFY_WIND_ALLOY_HELMET.get());
        fluffyWindAlloyArmorRecipe(output, ModItems.TITANIUM_CHESTPLATE.get(), ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get());
        fluffyWindAlloyArmorRecipe(output, ModItems.TITANIUM_LEGGINGS.get(), ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get());
        fluffyWindAlloyArmorRecipe(output, ModItems.TITANIUM_BOOTS.get(), ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());
    }

    private void fluffyWindAlloyArmorRecipe(RecipeOutput output, ItemLike base, ItemLike result) {
        List<ItemIngredient> itemInputs = List.of(
                ItemIngredient.of(base.asItem(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get(), 1),
                ItemIngredient.of(ModItems.FLUFFY_WIND_ALLOY_INGOT.get(), 1),
                ItemIngredient.of(ModItems.CONGEAL_WIND.get(), 2),
                ItemIngredient.of(ModItems.CONGEAL_WIND_IRON_INGOT.get(), 4));
        WeaponWorkshopCraftingTableRecipe recipe = new WeaponWorkshopCraftingTableRecipe(itemInputs,
                ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1), ItemIngredient.of(result.asItem(), 1));
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/" + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(result.asItem()).getPath()),
                recipe, null);
    }

    private void windJourneyMaterialRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PULSE_WIND_RUNNER_CRYSTAL.get(), 2)
                .pattern("aba")
                .pattern("aca")
                .pattern("aaa")
                .define('a', ModItems.CONGEAL_WIND.get())
                .define('b', ModItems.PULSE_WIND_RUNNER_CRYSTAL.get())
                .define('c', ModItems.FRACTURED_ANGEL_STATUE.get())
                .unlockedBy(getHasName(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()), has(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get()))
                .save(pWriter);
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

    private void windJourneyBlockRecipes(RecipeOutput pWriter) {
        // 风泊原木 → 风泊木
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WIND_MOOR_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.WIND_MOOR_LOG.get())
                .unlockedBy(getHasName(ModItems.WIND_MOOR_LOG.get()), has(ModItems.WIND_MOOR_LOG.get()))
                .save(pWriter);
        // 去皮风泊原木 → 去皮风泊木
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.STRIPPED_WIND_MOOR_WOOD.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.STRIPPED_WIND_MOOR_LOG.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_WIND_MOOR_LOG.get()), has(ModItems.STRIPPED_WIND_MOOR_LOG.get()))
                .save(pWriter);
        // 风泊原木 → 风泊木板 + 全套建筑配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.WIND_MOOR_LOG.get(),
                ModItems.WIND_MOOR_PLANKS.get(),
                ModItems.WIND_MOOR_STAIRS.get(),
                ModItems.WIND_MOOR_SLAB.get(),
                ModItems.WIND_MOOR_FENCE.get(),
                ModItems.WIND_MOOR_FENCE_GATE.get(),
                ModItems.WIND_MOOR_DOOR.get(),
                ModItems.WIND_MOOR_TRAPDOOR.get(),
                ModItems.WIND_MOOR_PRESSURE_PLATE.get(),
                ModItems.WIND_MOOR_BUTTON.get(),
                PasterDreamMod.MOD_ID);
        // 风泊木窗棂
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.WIND_MOOR_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.WIND_MOOR_PLANKS.get())
                .unlockedBy(getHasName(ModItems.WIND_MOOR_PLANKS.get()), has(ModItems.WIND_MOOR_PLANKS.get()))
                .save(pWriter);

        // 白砂 → 澄澈玻璃
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.WHITE_SAND.get()),
                        RecipeCategory.BUILDING_BLOCKS, ModItems.CLARITY_GLASS.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.WHITE_SAND.get()), has(ModItems.WHITE_SAND.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":clarity_glass_from_smelting");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModItems.CLARITY_GLASS.get()), has(ModItems.CLARITY_GLASS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CARVE_CLARITY_GLASS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModItems.CLARITY_GLASS.get()), has(ModItems.CLARITY_GLASS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CARVE_CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.CARVE_CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModItems.CARVE_CLARITY_GLASS.get()), has(ModItems.CARVE_CLARITY_GLASS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.FRAME_CLARITY_GLASS.get(), 4)
                .requires(ModItems.CLARITY_GLASS.get())
                .requires(ModItems.CLARITY_GLASS.get())
                .requires(ModItems.CLARITY_GLASS.get())
                .requires(ModItems.POLISHED_CALCITE.get())
                .unlockedBy(getHasName(ModItems.CLARITY_GLASS.get()), has(ModItems.CLARITY_GLASS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.FRAME_CLARITY_GLASS_PANE.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.FRAME_CLARITY_GLASS.get())
                .unlockedBy(getHasName(ModItems.FRAME_CLARITY_GLASS.get()), has(ModItems.FRAME_CLARITY_GLASS.get()))
                .save(pWriter);
        // 破风幕帐
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BREAK_WIND_CURTAIN.get(), 8)
                .pattern("aba")
                .pattern("bab")
                .pattern("aba")
                .define('a', ModItems.CONGEAL_WIND.get())
                .define('b', ModItems.CLARITY_GLASS_PANE.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND.get()), has(ModItems.CONGEAL_WIND.get()))
                .save(pWriter);

        // 白砂岩
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WHITE_SANDSTONE.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.WHITE_SAND.get())
                .unlockedBy(getHasName(ModItems.WHITE_SAND.get()), has(ModItems.WHITE_SAND.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_WHITE_SANDSTONE.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.WHITE_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.WHITE_SANDSTONE.get()), has(ModItems.WHITE_SANDSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_WHITE_SANDSTONE.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.WHITE_SANDSTONE_SLAB.get())
                .unlockedBy(getHasName(ModItems.WHITE_SANDSTONE_SLAB.get()), has(ModItems.WHITE_SANDSTONE_SLAB.get()))
                .save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.WHITE_SANDSTONE.get()),
                        RecipeCategory.MISC, ModItems.SMOOTH_WHITE_SANDSTONE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.WHITE_SANDSTONE.get()), has(ModItems.WHITE_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":smooth_white_sandstone_from_smelting");
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.WHITE_SANDSTONE.get(), ModItems.WHITE_SANDSTONE_STAIRS.get(),
                ModItems.WHITE_SANDSTONE_SLAB.get(), ModItems.WHITE_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CUT_WHITE_SANDSTONE.get(), ModItems.CUT_WHITE_SANDSTONE_STAIRS.get(),
                ModItems.CUT_WHITE_SANDSTONE_SLAB.get(), ModItems.CUT_WHITE_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SMOOTH_WHITE_SANDSTONE.get(), ModItems.SMOOTH_WHITE_SANDSTONE_STAIRS.get(),
                ModItems.SMOOTH_WHITE_SANDSTONE_SLAB.get(), ModItems.SMOOTH_WHITE_SANDSTONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        var whiteSandstoneIngredient = Ingredient.of(ModItems.WHITE_SANDSTONE.get());
        SingleItemRecipeBuilder.stonecutting(whiteSandstoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CUT_WHITE_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.WHITE_SANDSTONE.get()), has(ModItems.WHITE_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cut_white_sandstone_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(whiteSandstoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_WHITE_SANDSTONE.get())
                .unlockedBy(getHasName(ModItems.WHITE_SANDSTONE.get()), has(ModItems.WHITE_SANDSTONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_white_sandstone_from_stonecutting");

        // 凝风系
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CONGEAL_WIND_ORE.get()),
                        RecipeCategory.MISC, ModItems.CONGEAL_WIND.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_ORE.get()), has(ModItems.CONGEAL_WIND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":congeal_wind_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.CONGEAL_WIND_ORE.get()),
                        RecipeCategory.MISC, ModItems.CONGEAL_WIND.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_ORE.get()), has(ModItems.CONGEAL_WIND_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":congeal_wind_from_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()),
                        RecipeCategory.MISC, ModItems.WIND_RUNNER_CRYSTAL.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()), has(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":wind_runner_crystal_from_smelting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()),
                        RecipeCategory.MISC, ModItems.WIND_RUNNER_CRYSTAL.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()), has(ModItems.WIND_RUNNER_CRYSTAL_ORE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":wind_runner_crystal_from_blasting");
        RecipeHelpers.storageCompress(pWriter, ModItems.CONGEAL_WIND.get(), ModItems.CONGEAL_WIND_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.CONGEAL_WIND_BLOCK.get(), ModItems.CONGEAL_WIND.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.WIND_RUNNER_CRYSTAL.get(), ModItems.WIND_RUNNER_CRYSTAL_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.WIND_RUNNER_CRYSTAL_BLOCK.get(), ModItems.WIND_RUNNER_CRYSTAL.get(), PasterDreamMod.MOD_ID);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CONGEAL_WIND_IRON_INGOT.get(), 1)
                .requires(Items.IRON_INGOT)
                .requires(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                .requires(ModItems.CONGEAL_WIND.get())
                .requires(ModItems.CONGEAL_WIND.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND.get()), has(ModItems.CONGEAL_WIND.get()))
                .save(pWriter);
        RecipeHelpers.storageCompress(pWriter, ModItems.CONGEAL_WIND_IRON_INGOT.get(), ModItems.CONGEAL_WIND_IRON_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.CONGEAL_WIND_IRON_BLOCK.get(), ModItems.CONGEAL_WIND_IRON_INGOT.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageCompress(pWriter, ModItems.FLUFFY_WIND_ALLOY_INGOT.get(), ModItems.FLUFFY_WIND_ALLOY_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.FLUFFY_WIND_ALLOY_BLOCK.get(), ModItems.FLUFFY_WIND_ALLOY_INGOT.get(), PasterDreamMod.MOD_ID);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CONGEAL_WIND_IRON_LANTERN.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                .define('b', Items.TORCH)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_NUGGET.get()), has(ModItems.CONGEAL_WIND_IRON_NUGGET.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.CONGEAL_WIND_IRON_TRAPDOOR.get(), 2)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CONGEAL_WIND_IRON_CHAIN.get(), 1)
                .pattern("b")
                .pattern("a")
                .pattern("b")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.CONGEAL_WIND_IRON_PRESSURE_PLATE.get(), 1)
                .pattern("aa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CONGEAL_WIND_IRON_BARS.get(), 16)
                .pattern("aaa")
                .pattern("aaa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EJECTION_PRESSURE_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("bb")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .define('b', Items.SLIME_BALL)
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EJECTION_PRESSURE_PLATE.get(), 1)
                .pattern("aaa")
                .define('a', ModItems.CONGEAL_WIND_IRON_INGOT.get())
                .unlockedBy(getHasName(ModItems.CONGEAL_WIND_IRON_INGOT.get()), has(ModItems.CONGEAL_WIND_IRON_INGOT.get()))
                .save(pWriter);

        // 苍青岩
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_BRICKS.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.CYAN_STONE_BRICKS.get(), ModItems.CYAN_STONE_BRICK_STAIRS.get(),
                ModItems.CYAN_STONE_BRICK_SLAB.get(), ModItems.CYAN_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cyan_stone_bricks_from_stonecutting");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_PILLAR.get(), 2)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CYAN_STONE_PILLAR.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cyan_stone_pillar_from_stonecutting");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_CYAN_STONE_BRICKS.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.CYAN_STONE_BRICK_SLAB.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICK_SLAB.get()), has(ModItems.CYAN_STONE_BRICK_SLAB.get()))
                .save(pWriter);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_CYAN_STONE_BRICKS.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_cyan_stone_bricks_from_stonecutting");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.CYAN_STONE_PRESSURE_PLATE.get(), 1)
                .pattern("aa")
                .define('a', ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModItems.CYAN_STONE_BUTTON.get(), 1)
                .requires(ModItems.CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.CYAN_STONE.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.POLISHED_CYAN_STONE.get())
                .unlockedBy(getHasName(ModItems.CYAN_STONE.get()), has(ModItems.CYAN_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":polished_cyan_stone_from_stonecutting");
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.POLISHED_CYAN_STONE.get(), ModItems.POLISHED_CYAN_STONE_STAIRS.get(),
                ModItems.POLISHED_CYAN_STONE_SLAB.get(), ModItems.POLISHED_CYAN_STONE_WALL.get(),
                PasterDreamMod.MOD_ID);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.MOSSY_CYAN_STONE_BRICKS.get(), 1)
                .requires(ModItems.CYAN_STONE_BRICKS.get())
                .requires(Items.VINE)
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICKS.get()), has(ModItems.CYAN_STONE_BRICKS.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":mossy_cyan_stone_bricks_from_vine");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.MOSSY_CYAN_STONE_BRICKS.get(), 1)
                .requires(ModItems.CYAN_STONE_BRICKS.get())
                .requires(Items.MOSS_BLOCK)
                .unlockedBy(getHasName(ModItems.CYAN_STONE_BRICKS.get()), has(ModItems.CYAN_STONE_BRICKS.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":mossy_cyan_stone_bricks_from_moss_block");
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.MOSSY_CYAN_STONE_BRICKS.get(), ModItems.MOSSY_CYAN_STONE_BRICK_STAIRS.get(),
                ModItems.MOSSY_CYAN_STONE_BRICK_SLAB.get(), ModItems.MOSSY_CYAN_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 厚云与暗云
        RecipeHelpers.storageCompress(pWriter, ModItems.CLOUD.get(), ModItems.THICK_CLOUD.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.THICK_CLOUD.get(), ModItems.CLOUD.get(), PasterDreamMod.MOD_ID);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.DARK_CLOUD.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.THICK_CLOUD.get())
                .unlockedBy(getHasName(ModItems.THICK_CLOUD.get()), has(ModItems.THICK_CLOUD.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.THICK_CLOUD.get(), 4)
                .requires(ModItems.DARK_CLOUD.get())
                .unlockedBy(getHasName(ModItems.DARK_CLOUD.get()), has(ModItems.DARK_CLOUD.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":thick_cloud_from_dark_cloud");

        // 水母果冻
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JELLYFISH_JELLO.get(), 2)
                .requires(ModItems.JELLYFISH_MUD.get(), 2)
                .requires(Ingredient.of(ModItemTags.FRUITS_FIG), 1)
                .requires(Items.SUGAR, 1)
                .requires(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(), 1)
                .unlockedBy(getHasName(ModItems.JELLYFISH_MUD.get()), has(ModItems.JELLYFISH_MUD.get()))
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

    private void chargedAmethystAndSaltRecipes(RecipeOutput pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHARGED_AMETHYST.get(), 1)
                .requires(Items.AMETHYST_SHARD)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .requires(Items.DRAGON_BREATH)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHARGED_AMETHYST_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.CHARGED_AMETHYST.get())
                .unlockedBy(getHasName(ModItems.CHARGED_AMETHYST.get()), has(ModItems.CHARGED_AMETHYST.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.CHARGED_AMETHYST.get(), 4)
                .requires(ModItems.CHARGED_AMETHYST_BLOCK.get())
                .unlockedBy(getHasName(ModItems.CHARGED_AMETHYST_BLOCK.get()), has(ModItems.CHARGED_AMETHYST_BLOCK.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":charged_amethyst_from_charged_amethyst_block");
        RecipeHelpers.storageCompress(pWriter, ModItems.SALT.get(), ModItems.SALT_BLOCK.get(), PasterDreamMod.MOD_ID);
        RecipeHelpers.storageDecompress(pWriter, ModItems.SALT_BLOCK.get(), ModItems.SALT.get(), PasterDreamMod.MOD_ID);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGIC_STONE.get(), 32)
                .pattern("ab")
                .pattern("bb")
                .define('a', Items.DIAMOND)
                .define('b', ModItems.SOUL_DUST.get())
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":magic_stone_from_diamond");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAGIC_STONE.get(), 32)
                .pattern("ab")
                .pattern("bb")
                .define('a', ModItems.ICE_BUD.get())
                .define('b', ModItems.SOUL_DUST.get())
                .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":magic_stone_from_ice_bud");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_COROLLA.get(), 1)
                .requires(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get()), has(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHITE_COROLLA.get(), 1)
                .requires(ModItems.WHITE_COROLLA_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.WHITE_COROLLA_CROP_AGE_1.get()), has(ModItems.WHITE_COROLLA_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LIGHT_BALL.get(), 1)
                .requires(ModItems.LIGHT_BALL_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.LIGHT_BALL_CROP_AGE_1.get()), has(ModItems.LIGHT_BALL_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CLOUD.get(), 5)
                .requires(ModItems.CLOUD_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.CLOUD_CROP_AGE_1.get()), has(ModItems.CLOUD_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COTTON.get(), 1)
                .requires(ModItems.COTTON_CROP_AGE_1.get())
                .unlockedBy(getHasName(ModItems.COTTON_CROP_AGE_1.get()), has(ModItems.COTTON_CROP_AGE_1.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PERGAMYN.get(), 1)
                .requires(Items.PAPER)
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAPER, 3)
                .pattern("   ")
                .pattern("aaa")
                .pattern("   ")
                .define('a', ModItems.REED_ROD.get())
                .unlockedBy(getHasName(ModItems.REED_ROD.get()), has(ModItems.REED_ROD.get()))
                .save(pWriter);
    }

    // ===== 便携储物袋配方 =====
    private void storageBagRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STORAGE_BAG.get(), 1)
                .pattern(" b ")
                .pattern("a a")
                .pattern(" a ")
                .define('a', ModItems.FABRIC.get())
                .define('b', ModItems.SPOOL.get())
                .unlockedBy(getHasName(ModItems.FABRIC.get()), has(ModItems.FABRIC.get()))
                .save(pWriter);

        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LARGE_STORAGE_BAG.get())
                        .pattern(" d ")
                        .pattern("bab")
                        .pattern(" c ")
                        .define('a', ModItems.STORAGE_BAG.get())
                        .define('b', ModItems.FABRIC.get())
                        .define('c', Items.ENDER_PEARL)
                        .define('d', Items.DIAMOND)
                        .unlockedBy(getHasName(ModItems.STORAGE_BAG.get()), has(ModItems.STORAGE_BAG.get())),
                pWriter, "large_storage_bag");
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

    private void mortarRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MORTAR.get(), 1)
                .pattern("  a")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', Items.IRON_NUGGET)
                .define('b', Items.POLISHED_DEEPSLATE)
                .define('c', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MORTAR.get(), 1)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.MORTAR.get()), has(ModItems.MORTAR.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar_from_mortar"));

        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 7)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get())
                .requires(Items.BONE_MEAL)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get())),
                pWriter, "dyedream_dye_from_dust_piece");
        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_DYE.get(), 63)
                .requires(ModItems.DYEDREAM_DUST.get())
                .requires(Items.BONE_BLOCK)
                .requires(ModItems.MORTAR.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST.get()), has(ModItems.DYEDREAM_DUST.get())),
                pWriter, "dyedream_dye_from_dust");
        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SALT.get(), 1)
                .requires(ModItems.MORTAR.get())
                .requires(ModItems.COARSE_SALT.get())
                .unlockedBy(getHasName(ModItems.COARSE_SALT.get()), has(ModItems.COARSE_SALT.get())),
                pWriter, "salt_from_coarse_salt");
        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLOUR.get(), 1)
                .requires(ModItems.MORTAR.get())
                .requires(Items.WHEAT, 2)
                .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)),
                pWriter, "flour_from_wheat");
        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLOUR.get(), 1)
                .requires(ModItems.MORTAR.get())
                .requires(ModItems.RYE_SEED.get(), 2)
                .unlockedBy(getHasName(ModItems.RYE_SEED.get()), has(ModItems.RYE_SEED.get())),
                pWriter, "flour_from_rye_seed");
        saveMortarCrafting(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLOUR.get(), 1)
                .requires(ModItems.MORTAR.get())
                .requires(Items.WHEAT_SEEDS, 4)
                .unlockedBy(getHasName(Items.WHEAT_SEEDS), has(Items.WHEAT_SEEDS)),
                pWriter, "flour_from_wheat_seeds");

        TagKey<Item> wheat = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "crops/wheat"));
        TagKey<Item> seeds = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "seeds"));
        saveMortarRecipe(pWriter, "flour_from_wheat", List.of(ItemIngredient.of(wheat, 2)),
                List.of(ItemIngredient.of(ModItems.FLOUR.get(), 1)));
        saveMortarRecipe(pWriter, "flour_from_seeds", List.of(ItemIngredient.of(seeds, 4)),
                List.of(ItemIngredient.of(ModItems.FLOUR.get(), 1)));
        saveMortarRecipe(pWriter, "flour_from_rye_seed", List.of(ItemIngredient.of(ModItems.RYE_SEED.get(), 2)),
                List.of(ItemIngredient.of(ModItems.FLOUR.get(), 1)));
        saveMortarRecipe(pWriter, "salt", List.of(ItemIngredient.of(ModItems.COARSE_SALT.get(), 1)),
                List.of(ItemIngredient.of(ModItems.SALT.get(), 1)));
        saveMortarRecipe(pWriter, "dyedream_dye_from_dyedream_dust_piece_and_bone_meal",
                List.of(ItemIngredient.of(ModItems.DYEDREAM_DUST_PIECE.get(), 1), ItemIngredient.of(Items.BONE_MEAL, 1)),
                List.of(ItemIngredient.of(ModItems.DYEDREAM_DYE.get(), 9)));
    }

    private void pebbleRecipes(RecipeOutput pWriter) {
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PEBBLE.get(), 2)
                        .requires(Items.COBBLESTONE)
                        .requires(ModItems.MORTAR.get())
                        .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE)),
                pWriter, "pebble");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SNOWBALL_PEBBLE.get(), 1)
                .requires(Items.SNOWBALL)
                .requires(ModItems.PEBBLE.get())
                .unlockedBy(getHasName(ModItems.PEBBLE.get()), has(ModItems.PEBBLE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COBBLESTONE, 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.PEBBLE.get())
                .unlockedBy(getHasName(ModItems.PEBBLE.get()), has(ModItems.PEBBLE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cobblestone_from_pebble");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.FLINT, 1)
                .pattern("aa")
                .pattern("a ")
                .define('a', ModItems.PEBBLE.get())
                .unlockedBy(getHasName(ModItems.PEBBLE.get()), has(ModItems.PEBBLE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":flint_from_pebble");
    }

    private void glassCupRecipes(RecipeOutput pWriter) {
        // 玻璃杯合成配方（支持所有玻璃板tag）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_CUP.get(), 4)
                .pattern("a a")
                .pattern("a a")
                .pattern(" a ")
                .define('a', Ingredient.of(TagKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath("c", "glass_panes"))))
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(pWriter);

        // 饮料（玻璃杯类）合成配方
        RecipeHelpers.glass_cup_drink(pWriter, Items.APPLE, ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
        RecipeHelpers.glass_cup_drink(pWriter, Items.HONEYCOMB, ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
        RecipeHelpers.glass_cup_drink(pWriter, Items.MELON_SLICE, ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());

        // 染梦花茶（未烹调）合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(), 1)
                .requires(ModItems.GLASS_CUP.get(), 1)
                .requires(ModItems.AMBER_CANDY.get(), 1)
                .requires(ModItems.DYEDREAM_LEAVES.get(), 1)
                .requires(ModItems.DYEDREAM_COROLLA.get(), 1)
                .unlockedBy(getHasName(ModItems.GLASS_CUP.get()), has(ModItems.GLASS_CUP.get()))
                .save(pWriter);

        // 染梦花茶合成配方
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smelting"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()),
                        RecipeCategory.MISC, ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(), 0, 600)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()), has(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "glass_cup_of_cooked_dyedream_flower_tea_from_glass_cup_of_uncooked_dyedream_flower_tea_campfire_cooking"));

        // 染梦果汁合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(), 1)
                .requires(ModItems.GLASS_CUP.get(), 1)
                .requires(ModItems.DYEDREAM_FRUIT.get(), 1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(), 1)
                .requires(ModItems.AMBER_CANDY.get(), 1)
                .unlockedBy(getHasName(ModItems.GLASS_CUP.get()), has(ModItems.GLASS_CUP.get()))
                .save(pWriter);

        // 鲜红露滴合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RED_DEW.get(), 1)
                .requires(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get())
                .requires(Items.GLISTERING_MELON_SLICE)
                .requires(Items.GOLDEN_APPLE)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.RED_DYE)
                .unlockedBy(getHasName(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()),
                        has(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get()))
                .save(pWriter);
    }

    private void elixirBottleRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ELIXIR_BOTTLE.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .define('a', Items.GOLD_NUGGET)
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.GLASS_JAR.get())
                .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get(), 1)
                .requires(ModItems.ELIXIR_BOTTLE.get(), 1)
                .requires(ModItems.MELT_DREAM_LIQUID_BUCKET.get(), 1)
                .unlockedBy(getHasName(ModItems.MELT_DREAM_LIQUID_BUCKET.get()), has(ModItems.MELT_DREAM_LIQUID_BUCKET.get()))
                .save(pWriter);
    }

    private void glassJarRecipes(RecipeOutput pWriter) {
        // 玻璃罐合成（支持所有木台阶tag和玻璃板tag）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_JAR.get(), 8)
                .pattern("aba")
                .pattern("a a")
                .pattern("aaa")
                .define('a', Ingredient.of(TagKey.create(Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath("c", "glass_panes"))))
                .define('b', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(pWriter);

        // 药引瓶合成（研钵作为工具不消耗）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_GUIDING_DRUG.get(), 2)
                        .requires(ModItems.MORTAR.get())
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 2)
                        .requires(Items.NETHER_WART)
                        .requires(ModItems.LINHT_FLOWER.get())
                        .requires(ModItems.SOUL_DUST.get())
                        .unlockedBy(getHasName(ModItems.SOUL_DUST.get()), has(ModItems.SOUL_DUST.get())),
                pWriter, "glass_jar_of_guiding_drug");

        // 重做酵母合成配方（产物罐子数多于输入空罐子数，自动配平）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_YEAST.get(), 4)
                        .requires(ModItems.GLASS_JAR.get(), 2)
                        .requires(ModItems.FLOUR.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                        .requires(Items.SUGAR, 1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR.get()), has(ModItems.GLASS_JAR.get())),
                pWriter, "glass_jar_of_yeast",
                ModItems.GLASS_JAR_OF_YEAST.get(), 4,
                ModItems.GLASS_JAR.get(), 2,
                ModItems.FLOUR.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                ModItems.GLASS_JAR_OF_YEAST.get(), 1,
                Items.SUGAR, 1);

        // 水罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_WATER.get(), 8)
                .requires(Items.WATER_BUCKET, 1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.WATER_BUCKET), has(Items.WATER_BUCKET))
                .save(pWriter);

        // 牛奶罐合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_MILK.get(), 8)
                .requires(Items.MILK_BUCKET, 1)
                .requires(ModItems.GLASS_JAR.get(), 8)
                .unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET))
                .save(pWriter);

        // 墨水罐合成配方一（水罐 + 玻璃罐 + 黑色染料，容器自动配平）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_INK.get(), 2)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                        .requires(ModItems.GLASS_JAR.get(), 1)
                        .requires(Items.BLACK_DYE, 1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WATER.get()), has(ModItems.GLASS_JAR_OF_WATER.get())),
                pWriter, "glass_jar_of_ink_from_black_dye",
                ModItems.GLASS_JAR_OF_INK.get(), 2,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                ModItems.GLASS_JAR.get(), 1,
                Items.BLACK_DYE, 1);

        // 墨水罐合成配方二（水罐*2 + 墨囊，容器自动配平）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_INK.get(), 2)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 2)
                        .requires(Items.INK_SAC, 1)
                        .unlockedBy(getHasName(Items.INK_SAC), has(Items.INK_SAC)),
                pWriter, "glass_jar_of_ink_from_ink_sac",
                ModItems.GLASS_JAR_OF_INK.get(), 2,
                ModItems.GLASS_JAR_OF_WATER.get(), 2,
                Items.INK_SAC, 1);

        // 风植萃取液合成配方（研钵作为工具不消耗）
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(), 2)
                        .requires(ModItems.MORTAR.get())
                        .requires(ModItems.GLASS_JAR.get(), 2)
                        .requires(ModItems.WIND_ISLAND_REED.get())
                        .requires(ModItems.WIND_CLEAVING_GRASS.get())
                        .requires(ModItems.WIND_FEATHER_GRASS.get())
                        .requires(ModItems.HAIRY_MOSS.get())
                        .unlockedBy(getHasName(ModItems.MORTAR.get()),
                                has(ModItems.MORTAR.get())),
                pWriter, "glass_jar_of_wind_plant_extract");
    }

    // ===== 食物与饮品配方 =====
    private void foodRecipes(RecipeOutput pWriter) {
        // 巧克力合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE.get(), 1)
                .requires(Items.COCOA_BEANS, 2)
                .requires(Items.SUGAR, 1)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);

        // 面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH.get(), 1)
                .requires(Ingredient.of(ModItemTags.FLOUR), 1)
                .requires(ModItems.GLASS_JAR_OF_WATER.get(), 2)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter);

        // 蛋液面团合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DOUGH_WITH_EGG.get(), 1)
                .requires(Ingredient.of(Tags.Items.EGGS), 1)
                .requires(Ingredient.of(Tags.Items.FOODS_DOUGH), 2)
                .unlockedBy(getHasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get()))
                .save(pWriter);

        // 蛋糕胚合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CAKE_BASE.get(), 2)
                .requires(ModItems.DOUGH_WITH_EGG.get(), 1)
                .requires(Items.SUGAR, 1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .unlockedBy(getHasName(ModItems.DOUGH_WITH_EGG.get()), has(ModItems.DOUGH_WITH_EGG.get()))
                .save(pWriter);

        // 奶油小蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CREAM_BUN_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(), 1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

        // 六种蛋糕合成
        RecipeHelpers.cake(pWriter, Items.SWEET_BERRIES, ModItems.BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.POTATO, ModItems.TUBER_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.MELON_SLICE, ModItems.WATERMELON_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.PUMPKIN, ModItems.PUMPKIN_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, Items.GLOW_BERRIES, ModItems.GLOW_BERRY_BUN_CAKE.get());
        RecipeHelpers.cake(pWriter, ModItems.DYEDREAM_FRUIT.get(), ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());

        // 巧克力抹茶蛋糕合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHOCOLATE_MATCHA_CAKE.get(), 1)
                .requires(ModItems.CAKE_BASE.get(), 1)
                .requires(Ingredient.of(ItemTags.LEAVES), 1)
                .requires(ModItems.CHOCOLATE.get(), 1)
                .unlockedBy(getHasName(ModItems.CAKE_BASE.get()), has(ModItems.CAKE_BASE.get()))
                .save(pWriter);

        // 三种心形巧克力合成
        RecipeHelpers.heartChocolate(pWriter, Items.COCOA_BEANS, ModItems.HEART_CHOCOLATE.get());
        RecipeHelpers.heartChocolate(pWriter, ModItems.GLASS_JAR_OF_MILK.get(), ModItems.WHITE_HEART_CHOCOLATE.get());
        RecipeHelpers.heartChocolate(pWriter, ModItems.DYEDREAM_DUST.get(), ModItems.PINK_HEART_CHOCOLATE.get());

        // 拐杖糖合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CANDY_CANE.get(), 1)
                .pattern("ab ")
                .pattern("ba ")
                .pattern(" b ")
                .define('a', ModItems.AMBER_CANDY.get())
                .define('b', Items.SUGAR)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 姜饼人合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GINGERBREAD_MAN.get(), 1)
                .pattern(" b ")
                .pattern("aca")
                .pattern(" d ")
                .define('a', Items.SUGAR)
                .define('b', Items.RED_DYE)
                .define('c', ModItems.WAFER_BISCUIT.get())
                .define('d', Items.SPRUCE_LEAVES)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);

        // 泡泡糖合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BUBBLE_GUM.get(), 2)
                .requires(ModItems.AMBER_CANDY.get(), 2)
                .requires(ModItems.PINK_SLIMEBALL.get(), 1)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 跳跳糖合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.POPPING_CANDY.get(), 2)
                .requires(Items.SUGAR, 1)
                .requires(ModItems.AMBER_CANDY.get(), 1)
                .requires(Items.RABBIT_FOOT, 1)
                .requires(Items.GUNPOWDER, 1)
                .unlockedBy(getHasName(ModItems.AMBER_CANDY.get()), has(ModItems.AMBER_CANDY.get()))
                .save(pWriter);

        // 炙焰金苹果合成配方（8 炙焰金锭 + 1 苹果）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOLTEN_GOLD_APPLE.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.MOLTEN_GOLD_INGOT.get())
                .define('b', Items.APPLE)
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_INGOT.get()), has(ModItems.MOLTEN_GOLD_INGOT.get()))
                .save(pWriter);

        // 附魔炙焰金苹果合成配方（8 炙焰金块 + 1 苹果）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.MOLTEN_GOLD_BLOCK.get())
                .define('b', Items.APPLE)
                .unlockedBy(getHasName(ModItems.MOLTEN_GOLD_BLOCK.get()), has(ModItems.MOLTEN_GOLD_BLOCK.get()))
                .save(pWriter);

        // 染梦冰棒合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DYEDREAM_POPSICLE.get(), 1)
                .requires(Items.SNOWBALL, 1)
                .requires(ModItems.DYEDREAM_DUST_PIECE.get(), 1)
                .requires(Items.STICK, 1)
                .requires(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get(), 1)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DUST_PIECE.get()), has(ModItems.DYEDREAM_DUST_PIECE.get()))
                .save(pWriter);

        // 煎蛋合成配方（熔炉，烟熏炉，篝火）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Tags.Items.EGGS),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(), 1.0F, 200)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(Tags.Items.EGGS),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(), 1.0F, 100)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Tags.Items.EGGS),
                        RecipeCategory.MISC, ModItems.FRIED_EGG.get(), 0, 600)
                .unlockedBy(getHasName(Items.EGG), has(Items.EGG))
                .save(pWriter, PasterDreamMod.MOD_ID + ":fried_egg_from_pink_egg_campfire_cooking");

        // 面包片合成配方（熔炉，烟熏炉，篝火）
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(), 1.0F, 200)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(), 1.0F, 100)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_smoking");
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.FLOUR.get()),
                        RecipeCategory.MISC, ModItems.BREAD_SLICE.get(), 0, 600)
                .unlockedBy(getHasName(ModItems.FLOUR.get()), has(ModItems.FLOUR.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":bread_slice_from_flour_campfire_cooking");

        // 威化饼干合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WAFER_BISCUIT.get(), 2)
                .requires(ModItems.BREAD_SLICE.get(), 2)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_YEAST.get()), has(ModItems.GLASS_JAR_OF_YEAST.get()))
                .save(pWriter);

        // 瑞士卷合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SWISS_ROLL.get(), 1)
                .requires(ModItems.BREAD_SLICE.get(), 2)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(), 1)
                .requires(ModItems.GLASS_JAR_OF_YEAST.get(), 1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_YEAST.get()), has(ModItems.GLASS_JAR_OF_YEAST.get()))
                .save(pWriter);

        // 威化夹心合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STUFFED_WAFER_COOKIES.get(), 1)
                .requires(ModItems.WAFER_BISCUIT.get(), 2)
                .requires(ModItems.GLASS_JAR_OF_MILK.get(), 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_MILK.get()), has(ModItems.GLASS_JAR_OF_MILK.get()))
                .save(pWriter);

        // 三明治合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SANDWICH.get(), 1)
                .requires(Items.BREAD, 1)
                .requires(Items.COOKED_CHICKEN, 1)
                .requires(Ingredient.of(ModItemTags.COOKED_EGGS), 1)
                .requires(Items.KELP, 1)
                .unlockedBy(getHasName(Items.BREAD), has(Items.BREAD))
                .save(pWriter);

        // 培根煎蛋合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BACON_AND_EGG.get(), 1)
                .requires(Ingredient.of(ModItemTags.COOKED_EGGS), 2)
                .requires(Items.COOKED_PORKCHOP, 1)
                .requires(Ingredient.of(ModItemTags.SALT), 1)
                .unlockedBy(getHasName(ModItems.SALT.get()), has(ModItems.SALT.get()))
                .save(pWriter);

        // 融梦棉花糖合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MELT_DREAM_COTTON_CANDY.get(), 8)
                .pattern("bbb")
                .pattern("bab")
                .pattern("bbb")
                .define('a', ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                .define('b', Items.SUGAR)
                .unlockedBy(getHasName(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()), has(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get()))
                .save(pWriter);

        // 秋麒麟茶合成配方（空罐子由水罐 remainder 配平抵消）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), 1)
                        .requires(ModItems.GOLDENROD.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                        .unlockedBy(getHasName(ModItems.GOLDENROD.get()), has(ModItems.GOLDENROD.get())),
                pWriter, "glass_jar_of_goldenrod_tea",
                ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get(), 1,
                ModItems.GOLDENROD.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1);

        // 染梦香水合成配方（空罐子由水罐 remainder 配平抵消）
        saveContainerBalancedShapeless(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), 1)
                        .requires(ModItems.GLASS_JAR_OF_WATER.get(), 1)
                        .requires(ModItems.DYEDREAM_DUST_PIECE.get(), 1)
                        .requires(Items.FERMENTED_SPIDER_EYE, 1)
                        .requires(ModItems.PINK_MUSHROOM.get(), 1)
                        .requires(ModItems.DYEDREAM_MOSS.get(), 1)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WATER.get()), has(ModItems.GLASS_JAR_OF_WATER.get())),
                pWriter, "glass_jar_of_dyedream_perfume",
                ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get(), 1,
                ModItems.GLASS_JAR_OF_WATER.get(), 1,
                ModItems.DYEDREAM_DUST_PIECE.get(), 1,
                Items.FERMENTED_SPIDER_EYE, 1,
                ModItems.PINK_MUSHROOM.get(), 1,
                ModItems.DYEDREAM_MOSS.get(), 1);

        // 奇异炖菜合成配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.QUEER_SOUP.get(), 1)
                .requires(Items.MUSHROOM_STEW, 1)
                .requires(ModItems.SINGULARITY_FERN.get(), 1)
                .requires(ModItems.MISTY_DREAMING_LOTUS.get(), 1)
                .requires(ModItems.DYEDREAM_COROLLA.get(), 1)
                .requires(Items.GHAST_TEAR, 1)
                .unlockedBy(getHasName(ModItems.MISTY_DREAMING_LOTUS.get()), has(ModItems.MISTY_DREAMING_LOTUS.get()))
                .save(pWriter);

        // 银河果冻合成配方
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MILKY_WAY_JELLY.get(), 1)
                .pattern("cfc")
                .pattern("fgf")
                .pattern("cfc")
                .define('c', ModItems.CLOUD.get())
                .define('f', Items.FEATHER)
                .define('g', ModItems.GALAXY_JELLY.get())
                .unlockedBy(getHasName(ModItems.GALAXY_JELLY.get()), has(ModItems.GALAXY_JELLY.get()))
                .save(pWriter);

        // 琥珀糖合成配方
        dyeConversion(pWriter, Items.SUGAR, ModItems.AMBER_CANDY.get());

        // 风行者果冻合成配方
        saveMortarCrafting(
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WIND_RUNNER_JELLY.get(), 1)
                        .requires(ModItems.MORTAR.get())
                        .requires(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())
                        .requires(ModItems.JELLYFISH_JELLO.get())
                        .requires(ModItems.WIND_RUNNER_CRYSTAL.get())
                        .requires(ModItems.CONGEAL_WIND.get(), 2)
                        .requires(Items.SUGAR, 2)
                        .unlockedBy(getHasName(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get()),
                                has(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get())),
                pWriter, "wind_runner_jelly");
    }

    private void saveMortarRecipe(RecipeOutput output, String name, List<ItemIngredient> inputs, List<ItemIngredient> outputs) {
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar/" + name),
                new MortarRecipe(List.of(), inputs, List.of(), outputs, 0), null);
    }

    private void saveMortarCrafting(ShapelessRecipeBuilder builder, RecipeOutput output, String name) {
        builder.save(new RecipeOutput() {
            @Override
            public Advancement.Builder advancement() {
                return output.advancement();
            }

            @Override
            public void accept(ResourceLocation id, Recipe<?> recipe, AdvancementHolder advancement, ICondition... conditions) {
                output.accept(id, new MortarCraftingRecipe((ShapelessRecipe) recipe), advancement, conditions);
            }
        }, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
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

    /**
     * 保存无序配方并自动注册容器配平校验。
     * 输入参数为 (产物, 产物数量, 原料1, 数量1, 原料2, 数量2, ...)
     */
    private void saveContainerBalancedShapeless(ShapelessRecipeBuilder builder, RecipeOutput writer,
                                                String name, ItemLike result, int resultCount, Object... inputs) {
        containerValidations.add(() -> {
            ItemStack[] stacks = new ItemStack[inputs.length / 2];
            for (int i = 0; i < inputs.length; i += 2) {
                stacks[i / 2] = new ItemStack((ItemLike) inputs[i], (int) inputs[i + 1]);
            }
            ContainerBalanceHelper.validateOrThrow(name, new ItemStack(result, resultCount), stacks);
        });
        saveNbtPreserving(builder, writer, name);
    }

    /**
     * 保存有序配方并自动注册容器配平校验。
     */
    private void saveContainerBalancedShaped(ShapedRecipeBuilder builder, RecipeOutput writer,
                                             String name, ItemLike result, int resultCount, Object... inputs) {
        containerValidations.add(() -> {
            ItemStack[] stacks = new ItemStack[inputs.length / 2];
            for (int i = 0; i < inputs.length; i += 2) {
                stacks[i / 2] = new ItemStack((ItemLike) inputs[i], (int) inputs[i + 1]);
            }
            ContainerBalanceHelper.validateOrThrow(name, new ItemStack(result, resultCount), stacks);
        });
        saveNbtPreservingShaped(builder, writer, name);
    }

    // ===== 容器配平校验 =====

    /**
     * 校验所有通过 {@link #saveContainerBalancedShapeless} / {@link #saveContainerBalancedShaped}
     * 注册的容器配方是否能配平。各配方在构建时已自动加入校验列表，此处统一执行。
     */
    private void validateContainerBalance() {
        for (Runnable validation : containerValidations) {
            validation.run();
        }
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

    private void dreamAccumulatorRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DREAM_ACCUMULATOR.get(), 1)
                .pattern(" a ")
                .pattern("bcb")
                .pattern("bdb")
                .define('a', ModBlocks.DYEDREAM_GLASS.get())
                .define('b', ModItems.POLISHED_CALCITE.get())
                .define('c', ModItems.TITANIUM_INGOT.get())
                .define('d', ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get())
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);
    }

    private void claypanRecipes(RecipeOutput pWriter) {
        // 陶盆
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLAYPAN.get(), 2)
                .pattern("aba")
                .pattern("aaa")
                .define('a', ItemTags.TERRACOTTA)
                .define('b', Items.WHITE_DYE)
                .unlockedBy(getHasName(Items.WHITE_DYE), has(Items.WHITE_DYE))
                .save(pWriter);
    }

    private void dyedreamDeskRecipes(RecipeOutput pWriter) {
        // 染梦书桌
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_DESK.get(), 1)
                .pattern(" a ")
                .pattern("bbb")
                .pattern(" c ")
                .define('a', Items.BOOK)
                .define('b', ModItems.DYEDREAM_SLAB.get())
                .define('c', ModItems.DYEDREAM_LOG.get())
                .unlockedBy(getHasName(ModItems.DYEDREAM_PLANKS.get()), has(ModItems.DYEDREAM_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DYEDREAM_DESK.get(), 1)
                .pattern("aaa")
                .pattern("aba")
                .pattern("aaa")
                .define('a', ModItems.DYEDREAM_DYE.get())
                .define('b', Items.LECTERN)
                .unlockedBy(getHasName(ModItems.DYEDREAM_DYE.get()), has(ModItems.DYEDREAM_DYE.get()))
                .save(pWriter, "dyedream_desk_from_lectern");
    }

    private void equipmentDeviceRecipes(RecipeOutput pWriter) {
        // 园艺钳
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.PLIERS.get(), 1)
                .pattern(" a ")
                .pattern("b a")
                .pattern("cb ")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.STICK)
                .define('c', ItemTags.PLANKS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        // 羊毛 + 园艺钳 → 线
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 2)
                .requires(ItemTags.WOOL)
                .requires(ModItems.PLIERS.get())
                .unlockedBy(getHasName(ModItems.PLIERS.get()), has(ModItems.PLIERS.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "string_from_wool_and_pliers"));

        // 野餐篮
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PICNIC_BASKET.get(), 1)
                .pattern("aaa")
                .pattern("b b")
                .pattern("bbb")
                .define('a', Items.STICK)
                .define('b', ItemTags.PLANKS)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(pWriter);

        // 羽毛笔 = 羽毛 + 铁粒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FEATHER_PEN.get(), 1)
                .requires(Items.FEATHER)
                .requires(Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.FEATHER), has(Items.FEATHER))
                .save(pWriter);

        // 风泊书桌
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIND_MOOR_DESK.get(), 1)
                .pattern(" a ")
                .pattern("bbb")
                .pattern(" c ")
                .define('a', Items.BOOK)
                .define('b', ModItems.WIND_MOOR_SLAB.get())
                .define('c', ModItems.STRIPPED_WIND_MOOR_LOG.get())
                .unlockedBy(getHasName(ModItems.WIND_MOOR_SLAB.get()), has(ModItems.WIND_MOOR_SLAB.get()))
                .save(pWriter);

        // 阴影书桌
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHADOW_DESK.get(), 1)
                .pattern(" a ")
                .pattern("bbb")
                .pattern(" c ")
                .define('a', Items.BOOK)
                .define('b', ModItems.SHADOW_SLAB.get())
                .define('c', ModItems.STRIPPED_SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.SHADOW_SLAB.get()), has(ModItems.SHADOW_SLAB.get()))
                .save(pWriter);

        // 风泊板条筐
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIND_MOOR_CRATE.get(), 1)
                .pattern("a a")
                .pattern("aba")
                .define('a', ModBlocks.WIND_MOOR_PLANKS.get())
                .define('b', ModBlocks.CYAN_STONE.get())
                .unlockedBy(getHasName(ModBlocks.WIND_MOOR_PLANKS.get()), has(ModBlocks.WIND_MOOR_PLANKS.get()))
                .save(pWriter);

        // 阴影石瓦 → 浸影宝盒 (切石机)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.SHADOW_STONE_TILES.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_CHEST.get())
                .unlockedBy(getHasName(ModBlocks.SHADOW_STONE_TILES.get()), has(ModBlocks.SHADOW_STONE_TILES.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_chest_from_stonecutting");
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

    private void expansionWeaponWorkshopRecipes(RecipeOutput output) {
        ItemIngredient enhanceStone = ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1);
        List<ItemIngredient> terraInputs = List.of(
                ItemIngredient.of(ModItems.INFERNO_SWORD.get(), 1),
                ItemIngredient.of(ModItems.MURAKUMO_KUSANAGI.get(), 1),
                ItemIngredient.of(ModItems.BEIHAI_RUO_TIDE_SWORD.get(), 1),
                ItemIngredient.of(ModItems.CHENJINGMEN_DESERT_SWORD.get(), 1),
                ItemIngredient.of(ModItems.BROKEN_HERO_SWORD.get(), 1));
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "weapon_workshop_crafting_table/terra_blade"),
                new WeaponWorkshopCraftingTableRecipe(terraInputs, enhanceStone,
                        ItemIngredient.of(ModItems.TERRA_BLADE.get(), 1)), null);

        List<ItemIngredient> strawberryInputs = List.of(
                ItemIngredient.of(ModItems.SLIVER_BELL.get(), 1),
                ItemIngredient.of(ModItems.DYEDREAM_ALLOY_INGOT.get(), 1),
                ItemIngredient.of(Items.STRING, 5),
                ItemIngredient.of(ModItems.DYEDREAM_PLANKS.get(), 5),
                ItemIngredient.of(ModItems.WHITE_CRYSTAL.get(), 1));
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                        "weapon_workshop_crafting_table/strawberry_heart"),
                new WeaponWorkshopCraftingTableRecipe(strawberryInputs, enhanceStone,
                        ItemIngredient.of(ModItems.STRAWBERRY_HEART.get(), 1)), null);
    }

    // ===== 暗影合金工具配方（合成台 NBT 保留升级，耐久重置满） =====

    private void shadowAlloyToolRecipes(RecipeOutput pWriter) {
        // aba / bcb / aba（a 噩梦燃料、b 暗影合金锭、c 原影蚀工具）→ 暗影合金工具（保留附魔/名字，耐久归满）
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOW_ALLOY_PICKAXE.get())
                .pattern("aba").pattern("bcb").pattern("aba")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', ModItems.SHADOW_EROSION_PICKAXE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_ALLOY_INGOT.get()), has(ModItems.SHADOW_ALLOY_INGOT.get())),
                pWriter, "shadow_alloy_pickaxe_upgrade");
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOW_ALLOY_AXE.get())
                .pattern("aba").pattern("bcb").pattern("aba")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', ModItems.SHADOW_EROSION_AXE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_ALLOY_INGOT.get()), has(ModItems.SHADOW_ALLOY_INGOT.get())),
                pWriter, "shadow_alloy_axe_upgrade");
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOW_ALLOY_SHOVEL.get())
                .pattern("aba").pattern("bcb").pattern("aba")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', ModItems.SHADOW_EROSION_SHOVEL.get())
                .unlockedBy(getHasName(ModItems.SHADOW_ALLOY_INGOT.get()), has(ModItems.SHADOW_ALLOY_INGOT.get())),
                pWriter, "shadow_alloy_shovel_upgrade");
        saveNbtPreservingShaped(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SHADOW_ALLOY_HOE.get())
                .pattern("aba").pattern("bcb").pattern("aba")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', ModItems.SHADOW_EROSION_HOE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_ALLOY_INGOT.get()), has(ModItems.SHADOW_ALLOY_INGOT.get())),
                pWriter, "shadow_alloy_hoe_upgrade");
    }

    // ===== 暗影合金弩配方（合成台：黑木棍 + 暗影合金锭 + 原版弩 + 噩梦燃料） =====

    private void shadowAlloyCrossbowRecipe(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHADOW_ALLOY_CROSSBOW.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("dbd")
                .define('a', ModItems.BLACK_STICK.get())
                .define('b', ModItems.SHADOW_ALLOY_INGOT.get())
                .define('c', Items.CROSSBOW)
                .define('d', ModItems.NIGHTMARE_FUEL.get())
                .unlockedBy(getHasName(ModItems.SHADOW_ALLOY_INGOT.get()), has(ModItems.SHADOW_ALLOY_INGOT.get()))
                .save(pWriter);
    }

    private void blackMetalRecipes(RecipeOutput pWriter) {
        // 黑金属粒 → 黑金属锭；首份粒仍待暗影高炉。
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLACK_METAL_INGOT.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.BLACK_METAL_NUGGET.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_NUGGET.get()), has(ModItems.BLACK_METAL_NUGGET.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_ingot_from_grain"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_METAL_NUGGET.get(), 4)
                .requires(ModItems.BLACK_METAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_INGOT.get()), has(ModItems.BLACK_METAL_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_grain_from_ingot"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PURE_HORROR.get(), 2)
                .pattern("aba")
                .pattern("aca")
                .pattern("aaa")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.PURE_HORROR.get())
                .define('c', ModItemTags.PURE_HORROR_SOURCES)
                .unlockedBy("has_pure_horror_source", has(ModItemTags.PURE_HORROR_SOURCES))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pure_horror_from_source"));

        // 黑金属锭 → 黑金属块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.BLACK_METAL_BLOCK.get(), 1)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.BLACK_METAL_INGOT.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_INGOT.get()), has(ModItems.BLACK_METAL_INGOT.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_block_from_ingot"));

        // 黑金属块 → 黑金属锭（分解）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLACK_METAL_INGOT.get(), 4)
                .requires(ModItems.BLACK_METAL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.BLACK_METAL_BLOCK.get()), has(ModItems.BLACK_METAL_BLOCK.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "black_metal_ingot_from_block"));

        // 锈蚀金属块
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_BLOCK.get(), 4)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', ModItems.RUST_BLACK_METAL_GRAIN.get())
                .define('b', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
                .save(pWriter);

        // 锈蚀金属墙
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_WALL.get(), 2)
                .pattern("aba")
                .define('a', ModItems.RUST_BLACK_METAL_GRAIN.get())
                .define('b', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
                .save(pWriter);

        // 锈蚀金属栏杆
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.RUST_BLACK_METAL_BARS.get(), 1)
                .requires(Items.IRON_BARS)
                .requires(ModItems.RUST_BLACK_METAL_GRAIN.get())
                .unlockedBy(getHasName(ModItems.RUST_BLACK_METAL_GRAIN.get()), has(ModItems.RUST_BLACK_METAL_GRAIN.get()))
                .save(pWriter);
    }

    private void shadowBuildingRecipes(RecipeOutput pWriter) {
        // 阴影菌柄 → 阴影木板 + 全套建材配方
        RecipeHelpers.plankFamilyRecipes(pWriter,
                ModItems.SHADOW_STEM.get(),
                ModItems.SHADOW_PLANKS.get(),
                ModItems.SHADOW_STAIRS.get(),
                ModItems.SHADOW_SLAB.get(),
                ModItems.SHADOW_FENCE.get(),
                ModItems.SHADOW_FENCE_GATE.get(),
                ModItems.SHADOW_DOOR.get(),
                ModItems.SHADOW_TRAPDOOR.get(),
                ModItems.SHADOW_PRESSURE_PLATE.get(),
                ModItems.SHADOW_BUTTON.get(),
                PasterDreamMod.MOD_ID);

        // 阴影菌核 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.SHADOW_HYPHAE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_HYPHAE.get()), has(ModItems.SHADOW_HYPHAE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_hyphae");

        // 去皮阴影菌柄 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.STRIPPED_SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_STEM.get()), has(ModItems.STRIPPED_SHADOW_STEM.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_stripped_stem");

        // 去皮阴影菌核 → 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PLANKS.get(), 4)
                .requires(ModItems.STRIPPED_SHADOW_HYPHAE.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_HYPHAE.get()), has(ModItems.STRIPPED_SHADOW_HYPHAE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_planks_from_stripped_hyphae");

        // 阴影木窗格 - 玻璃板 + 阴影木板
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_PANE.get(), 1)
                .requires(Items.GLASS_PANE)
                .requires(ModItems.SHADOW_PLANKS.get())
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 阴影菌核 (2×2 shadow_stem → 3 shadow_hyphae)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_HYPHAE.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STEM.get()), has(ModItems.SHADOW_STEM.get()))
                .save(pWriter);

        // 去皮阴影菌核 (2×2 stripped_shadow_stem → 3 stripped_shadow_hyphae)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.STRIPPED_SHADOW_HYPHAE.get(), 3)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.STRIPPED_SHADOW_STEM.get())
                .unlockedBy(getHasName(ModItems.STRIPPED_SHADOW_STEM.get()), has(ModItems.STRIPPED_SHADOW_STEM.get()))
                .save(pWriter);

        // 2×2 阴影石 → 4× 阴影石砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_BRICK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STONE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter);

        // 阴影石砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SHADOW_STONE_BRICK.get(), ModItems.SHADOW_STONE_BRICK_STAIRS.get(),
                ModItems.SHADOW_STONE_BRICK_SLAB.get(), ModItems.SHADOW_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石 → 阴影石砖 (切石机)
        var shadowStoneIngredient = Ingredient.of(ModItems.SHADOW_STONE.get());
        SingleItemRecipeBuilder.stonecutting(shadowStoneIngredient, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_brick_from_stonecutting");

        // 阴影石建材 → 阴影石 (切石机，使用 c:shadow_stones 标签)
        var shadowStonesTag = Ingredient.of(ModItemTags.SHADOW_STONES);
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_from_stonecutting");

        // 2×2 阴影石砖 → 4× 细阴影石砖
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.NARROW_SHADOW_STONE_BRICK.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE_BRICK.get()), has(ModItems.SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 细阴影石砖 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.NARROW_SHADOW_STONE_BRICK.get(), ModItems.NARROW_SHADOW_STONE_BRICK_STAIRS.get(),
                ModItems.NARROW_SHADOW_STONE_BRICK_SLAB.get(), ModItems.NARROW_SHADOW_STONE_BRICK_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石建材 → 细阴影石砖 (切石机，使用 c:shadow_stones 标签)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":narrow_shadow_stone_brick_from_stonecutting");

        // 2×2 细阴影石砖 → 4× 阴影石瓦
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_TILES.get(), 4)
                .pattern("aa")
                .pattern("aa")
                .define('a', ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.NARROW_SHADOW_STONE_BRICK.get()), has(ModItems.NARROW_SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 阴影石瓦 → 楼梯/台阶/墙 + 切石机
        RecipeHelpers.buildingBlockFamilyRecipes(pWriter,
                ModItems.SHADOW_STONE_TILES.get(), ModItems.SHADOW_STONE_TILES_STAIRS.get(),
                ModItems.SHADOW_STONE_TILES_SLAB.get(), ModItems.SHADOW_STONE_TILES_WALL.get(),
                PasterDreamMod.MOD_ID);

        // 阴影石建材 → 阴影石瓦 (切石机，使用 c:shadow_stones 标签)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_STONE_TILES.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_stone_tiles_from_stonecutting");

        // 阴影石瓦 → 阴影陶罐 (切石机)
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.SHADOW_STONE_TILES.get()), RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_CLAY_POT.get())
                .unlockedBy(getHasName(ModBlocks.SHADOW_STONE_TILES.get()), has(ModBlocks.SHADOW_STONE_TILES.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":shadow_clay_pot_from_stonecutting");

        // 2× 阴影石砖台阶 → 1× 錾制阴影石砖 (工作台)
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SHADOW_STONE_BRICK.get(), 1)
                .pattern("a")
                .pattern("a")
                .define('a', ModItems.SHADOW_STONE_BRICK_SLAB.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE_BRICK.get()), has(ModItems.SHADOW_STONE_BRICK.get()))
                .save(pWriter);

        // 阴影石建材 → 裂阴影石砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.CRACKED_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":cracked_shadow_stone_brick_from_stonecutting");

        // 阴影石建材 → 錾制阴影石砖 (切石机)
        SingleItemRecipeBuilder.stonecutting(shadowStonesTag, RecipeCategory.BUILDING_BLOCKS, ModItems.CHISELED_SHADOW_STONE_BRICK.get())
                .unlockedBy(getHasName(ModItems.SHADOW_STONE.get()), has(ModItems.SHADOW_STONE.get()))
                .save(pWriter, PasterDreamMod.MOD_ID + ":chiseled_shadow_stone_brick_from_stonecutting");
    }

    private void shadowBookshelfRecipes(RecipeOutput pWriter) {
        // 阴影书架：上下各 3 个阴影木板，中间 3 本书
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 破旧阴影书架：左右两本书换成纸
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.WORN_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', Items.PAPER)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 蛛网阴影书架：左右两本书换成蜘蛛网
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.COBWEB_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', Items.COBWEB)
                .unlockedBy(getHasName(ModItems.SHADOW_PLANKS.get()), has(ModItems.SHADOW_PLANKS.get()))
                .save(pWriter);

        // 钥匙阴影书架：中间的书换成暗影地牢钥匙
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.KEY_SHADOW_BOOKSHELF.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', ModItems.SHADOW_PLANKS.get())
                .define('b', Items.BOOK)
                .define('c', ModItems.SHADOW_DUNGEON_KEY.get())
                .unlockedBy(getHasName(ModItems.SHADOW_DUNGEON_KEY.get()), has(ModItems.SHADOW_DUNGEON_KEY.get()))
                .save(pWriter);
    }

    private void shadowErosionWorkshopRecipes(RecipeOutput output) {
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/shadow_erosion_dagger"),
                new WeaponWorkshopCraftingTableRecipe(List.of(
                        ItemIngredient.of(Items.IRON_SWORD, 1),
                        ItemIngredient.of(ModItems.BLACK_METAL_INGOT.get(), 2),
                        ItemIngredient.of(ModItems.NIGHTMARE_FUEL.get(), 1),
                        ItemIngredient.of(Items.CRYING_OBSIDIAN, 3),
                        ItemIngredient.of(ModItems.BLACK_STICK.get(), 1)),
                        ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1),
                        ItemIngredient.of(ModItems.SHADOW_EROSION_DAGGER.get(), 1)), null);
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/shadow_erosion_pickaxe"),
                new WeaponWorkshopCraftingTableRecipe(List.of(
                        ItemIngredient.of(Items.IRON_PICKAXE, 1),
                        ItemIngredient.of(ModItems.BLACK_METAL_INGOT.get(), 2),
                        ItemIngredient.of(ModItems.NIGHTMARE_FUEL.get(), 1),
                        ItemIngredient.of(Items.CRYING_OBSIDIAN, 3),
                        ItemIngredient.of(ModItems.BLACK_STICK.get(), 1)),
                        ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1),
                        ItemIngredient.of(ModItems.SHADOW_EROSION_PICKAXE.get(), 1)), null);
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/shadow_erosion_axe"),
                new WeaponWorkshopCraftingTableRecipe(List.of(
                        ItemIngredient.of(Items.IRON_AXE, 1),
                        ItemIngredient.of(ModItems.BLACK_METAL_INGOT.get(), 2),
                        ItemIngredient.of(ModItems.NIGHTMARE_FUEL.get(), 1),
                        ItemIngredient.of(Items.CRYING_OBSIDIAN, 3),
                        ItemIngredient.of(ModItems.BLACK_STICK.get(), 1)),
                        ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1),
                        ItemIngredient.of(ModItems.SHADOW_EROSION_AXE.get(), 1)), null);
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/shadow_erosion_shovel"),
                new WeaponWorkshopCraftingTableRecipe(List.of(
                        ItemIngredient.of(Items.IRON_SHOVEL, 1),
                        ItemIngredient.of(ModItems.BLACK_METAL_INGOT.get(), 2),
                        ItemIngredient.of(ModItems.NIGHTMARE_FUEL.get(), 1),
                        ItemIngredient.of(Items.CRYING_OBSIDIAN, 3),
                        ItemIngredient.of(ModItems.BLACK_STICK.get(), 1)),
                        ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1),
                        ItemIngredient.of(ModItems.SHADOW_EROSION_SHOVEL.get(), 1)), null);
        output.accept(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "weapon_workshop_crafting_table/shadow_erosion_hoe"),
                new WeaponWorkshopCraftingTableRecipe(List.of(
                        ItemIngredient.of(Items.IRON_HOE, 1),
                        ItemIngredient.of(ModItems.BLACK_METAL_INGOT.get(), 2),
                        ItemIngredient.of(ModItems.NIGHTMARE_FUEL.get(), 1),
                        ItemIngredient.of(Items.CRYING_OBSIDIAN, 3),
                        ItemIngredient.of(ModItems.BLACK_STICK.get(), 1)),
                        ItemIngredient.of(ModItemTags.ENHANCE_STONE, 1),
                        ItemIngredient.of(ModItems.SHADOW_EROSION_HOE.get(), 1)), null);
    }

    private void musicDiscRecipes(RecipeOutput pWriter) {
        // 甜蜜的梦音乐唱片 = dyedream_dye + dyedream_alloy_nugget + soul_essence
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SWEET_DREAM_MUSIC_DISC.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', ModItems.DYEDREAM_DYE.get())
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 落雪之梦音乐唱片 = snowball + dyedream_alloy_nugget + soul_essence
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SNOWFALL_DREAM_MUSIC_DISC.get(), 1)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.SNOWBALL)
                .define('b', ModItems.DYEDREAM_ALLOY_NUGGET.get())
                .define('c', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
                .save(pWriter);

        // 亚伦柯斯之触音乐唱片 = nightmare_fuel + shadow_light + black_metal_nugget + pure_horror
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AARONCOS_MUSIC_DISC.get(), 1)
                .pattern("aba")
                .pattern("cdc")
                .pattern("aba")
                .define('a', ModItems.NIGHTMARE_FUEL.get())
                .define('b', ModItems.SHADOW_LIGHT.get())
                .define('c', ModItems.BLACK_METAL_NUGGET.get())
                .define('d', ModItems.PURE_HORROR.get())
                .unlockedBy(getHasName(ModItems.PURE_HORROR.get()), has(ModItems.PURE_HORROR.get()))
                .save(pWriter);

        // 风之旅途音乐唱片 = 破风幕 + 凝风铁粒 + 云 + 灵魂精华
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIND_JOURNEY_MUSIC_DISC.get(), 1)
                .pattern("abc")
                .pattern("bdb")
                .pattern("cba")
                .define('a', ModItems.BREAK_WIND_CURTAIN.get())
                .define('b', ModItems.CONGEAL_WIND_IRON_NUGGET.get())
                .define('c', ModItems.CLOUD.get())
                .define('d', ModItems.SOUL_ESSENCE.get())
                .unlockedBy(getHasName(ModItems.SOUL_ESSENCE.get()), has(ModItems.SOUL_ESSENCE.get()))
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
