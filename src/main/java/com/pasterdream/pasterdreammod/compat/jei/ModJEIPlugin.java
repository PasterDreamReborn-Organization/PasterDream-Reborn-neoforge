package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.claypanrecipe.ClaypanRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe.DreamAccumulatorRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamaccumulatorrecipe.DreamAccumulatorJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dyedreamcontamination.DyedreamContaminationJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dyedreamcontamination.DyedreamContaminationRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.fluidcontainerrelation.FluidContainerRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.info.ModJEIInfoRegistry;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRelation;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.GetAllFluidContainerCapability;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorScreen;
import com.pasterdream.pasterdreammod.world.block.portal.DyedreamContaminationRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.ElixirBottleSubtypeInterpreter;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.CauldronBrewRecipeCatalog;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.shadowblastfurnacerecipe.ShadowBlastFurnaceJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.shadowblastfurnacerecipe.ShadowBlastFurnaceRecipeCategory;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceRecipe;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new WeaponWorkshopCraftingTableRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ClaypanRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DreamCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DreamAccumulatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ShadowBlastFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DyedreamContaminationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FluidContainerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.registerSubtypeInterpreter(ModItems.ELIXIR_BOTTLE.get(), ElixirBottleSubtypeInterpreter.INSTANCE);
        registration.registerSubtypeInterpreter(
                ModItems.RED_DEW_RING.get(),
                (stack, context) -> String.valueOf(RedDewRingItem.getLv(stack)));

        registration.registerSubtypeInterpreter(
                ModItems.STRIKE_RING.get(),
                (stack, context) -> String.valueOf(StrikeRingItem.getLv(stack)));

        registration.registerSubtypeInterpreter(
                ModItems.POTION_BOTTLE.get(),
                (stack, context) -> PotionBottleItem.getPotionType(stack));

        registration.registerSubtypeInterpreter(
                ModItems.PROPHECY_CARD.get(),
                (stack, context) -> ProphecyCardItem.getType(stack));

        registration.registerSubtypeInterpreter(
                ModItems.BLUE_PRINT.get(),
                (stack, context) -> {
                    String content = stack.get(com.pasterdream.pasterdreammod.init.ModDataComponents.BLUEPRINT_CONTENT.get());
                    return content != null ? content : IIngredientSubtypeInterpreter.NONE;
                });

        for (Item item : List.of(ModItems.MELT_DREAM_CRYSTAL_CHEST.get(),
                ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get())) {
            registration.registerSubtypeInterpreter(item, (stack, context) -> {
                CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                if (!tag.contains("LootTables", Tag.TAG_LIST)) {
                    return IIngredientSubtypeInterpreter.NONE;
                }
                ListTag lootTables = tag.getList("LootTables", Tag.TAG_COMPOUND);
                StringBuilder key = new StringBuilder();
                for (int i = 0; i < lootTables.size(); i++) {
                    if (i > 0) key.append(',');
                    key.append(lootTables.getCompound(i).getString("LootTable"));
                }
                return key.toString();
            });
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration)
    {
        registration.addRecipeClickArea(ClaypanScreen.class, 74, 7, 22, 15, ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeClickArea(DreamAccumulatorScreen.class, 75, 27, 14, 29, DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE);
        registration.addRecipeClickArea(ShadowBlastFurnaceScreen.class, 65, 51, 40, 36, ShadowBlastFurnaceRecipeCategory.SHADOW_BLAST_FURNACE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null)
        {
            List<WeaponWorkshopCraftingTableRecipe> weaponWorkshopRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.WEAPON_WORKSHOP_CRAFTING_TABLE.get())
                    .stream()
                    .map(RecipeHolder::value)
                    .toList();
            registration.addRecipes(WeaponWorkshopCraftingTableRecipeCategory.WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE,
                    weaponWorkshopRecipes.stream().map(WeaponWorkshopCraftingTableJEIRecipe::new).toList());
            List<MortarRecipe> mortarRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.MORTAR.get())
                    .stream().map(RecipeHolder::value).toList();
            registration.addRecipes(MortarRecipeCategory.MORTAR_RECIPE_TYPE, mortarRecipes);
            List<ClaypanRecipe> claypanRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.CLAYPAN.get())
                    .stream().map(RecipeHolder::value).toList();
            registration.addRecipes(ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE,
                    claypanRecipes.stream().map(ClaypanJEIRecipe::new).toList());
            List<DreamCauldronRecipe> cauldronRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get()).stream().map(RecipeHolder::value).toList();
            registration.addRecipes(DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE,
                    java.util.stream.Stream.concat(cauldronRecipes.stream().map(DreamCauldronJEIRecipe::new),
                            CauldronBrewRecipeCatalog.createAll(level).stream()).toList());
            List<DreamAccumulatorRecipe> dreamAccumulatorRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.DREAM_ACCUMULATOR.get()).stream().map(RecipeHolder::value).toList();
            registration.addRecipes(DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE,
                    dreamAccumulatorRecipes.stream().map(DreamAccumulatorJEIRecipe::new).toList());
            List<ShadowBlastFurnaceRecipe> shadowBlastFurnaceRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.SHADOW_BLAST_FURNACE.get()).stream().map(RecipeHolder::value).toList();
            registration.addRecipes(ShadowBlastFurnaceRecipeCategory.SHADOW_BLAST_FURNACE_RECIPE_TYPE,
                    shadowBlastFurnaceRecipes.stream().map(ShadowBlastFurnaceJEIRecipe::new).toList());
            List<DyedreamContaminationRecipe> dyedreamContaminationRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.DYEDREAM_CONTAMINATION.get()).stream().map(RecipeHolder::value).toList();
            registration.addRecipes(DyedreamContaminationRecipeCategory.RECIPE_TYPE,
                    dyedreamContaminationRecipes.stream().map(DyedreamContaminationJEIRecipe::new).toList());
            List<FluidContainerRelation> fluidContainerRelations = GetAllFluidContainerCapability.getAllContainer();
            registration.addRecipes(FluidContainerRecipeCategory.FLUID_CONTAINER_RELATION, fluidContainerRelations);
        }

        // ===== 物品信息页（JEI 中按 R/U 查看）=====
        ModJEIInfoRegistry.register(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get()),
                WeaponWorkshopCraftingTableRecipeCategory.WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.MORTAR.get()), MortarRecipeCategory.MORTAR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CLAYPAN.get()), ClaypanRecipeCategory.CLAYPAN_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.DREAM_CAULDRON.get()),
                DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.DREAM_ACCUMULATOR.get()),
                DreamAccumulatorRecipeCategory.DREAM_ACCUMULATOR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SHADOW_BLAST_FURNACE.get()),
                ShadowBlastFurnaceRecipeCategory.SHADOW_BLAST_FURNACE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DYEDREAM_WORLD_LEAPSTONE.get()),
                DyedreamContaminationRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DYEDREAM_CRACK.get()),
                DyedreamContaminationRecipeCategory.RECIPE_TYPE);
    }
}
