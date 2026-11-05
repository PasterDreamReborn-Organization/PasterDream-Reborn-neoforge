package com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.weaponworkshopcraftingtable.IWeaponWorkshopCraftingTableRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class WeaponWorkshopCraftingTableRecipe implements IWeaponWorkshopCraftingTableRecipe, Recipe<RecipeInput>
{
    private final List<ItemIngredient> itemInput;
    private final ItemIngredient enhanceStone;
    private final ItemIngredient itemOutput;

    public WeaponWorkshopCraftingTableRecipe(List<ItemIngredient> itemInput, ItemIngredient enhanceStone, ItemIngredient itemOutput)
    {
        this.itemInput = itemInput;
        this.enhanceStone = enhanceStone;
        this.itemOutput = itemOutput;
    }

    @Override
    public List<ItemIngredient> getInputItemIngredients()
    {
        return itemInput;
    }

    @Override
    public ItemIngredient getEnhanceStone()
    {
        return enhanceStone;
    }

    @Override
    public ItemIngredient getOutputItemIngredients()
    {
        return itemOutput;
    }

    @Override
    public boolean matches(RecipeInput input, Level level)
    {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.WEAPON_WORKSHOP_CRAFTING_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.WEAPON_WORKSHOP_CRAFTING_TABLE.get();
    }
}
