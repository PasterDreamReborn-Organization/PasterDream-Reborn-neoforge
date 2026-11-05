package com.pasterdream.pasterdreammod.compat.jei;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableJEIRecipe;
import com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe.WeaponWorkshopCraftingTableRecipeCategory;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get()),
                WeaponWorkshopCraftingTableRecipeCategory.WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE);
    }
}
