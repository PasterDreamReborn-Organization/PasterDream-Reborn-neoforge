package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.mortarrecipe.MortarRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronRecipeCategory;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.DreamCauldronJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.ElixirBottleSubtypeInterpreter;
import com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe.CauldronBrewRecipeCatalog;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableRecipeCategory;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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
        registration.addRecipeCategories(new DreamCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
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
            List<DreamCauldronRecipe> cauldronRecipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.DREAM_CAULDRON.get()).stream().map(RecipeHolder::value).toList();
            registration.addRecipes(DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE,
                    java.util.stream.Stream.concat(cauldronRecipes.stream().map(DreamCauldronJEIRecipe::new),
                            CauldronBrewRecipeCatalog.createAll(level).stream()).toList());
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get()),
                WeaponWorkshopCraftingTableRecipeCategory.WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.MORTAR.get()), MortarRecipeCategory.MORTAR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.DREAM_CAULDRON.get()),
                DreamCauldronRecipeCategory.DREAM_CAULDRON_RECIPE_TYPE);
    }
}
