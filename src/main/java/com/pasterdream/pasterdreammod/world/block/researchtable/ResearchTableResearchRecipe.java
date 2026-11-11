package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.IResearchTableResearchRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ResearchTableResearchRecipe implements IResearchTableResearchRecipe, Recipe<RecipeInput>
{
    private final FluidIngredient fluidInput;
    private final ItemIngredient pen;
    private final int penCost;
    private final ItemIngredient unresolvedNote;
    private final ItemIngredient output;

    public ResearchTableResearchRecipe(FluidIngredient fluidInput, ItemIngredient pen, ItemIngredient unresolvedNote, ItemIngredient output)
    {
        this.fluidInput = fluidInput;
        this.pen = pen;
        this.penCost = (pen.hasNbt() && !pen.getNbt().isEmpty() && pen.getNbt().contains("Damage") ? pen.getNbt().getInt("Damage") : 1);
        this.unresolvedNote = unresolvedNote;
        this.output = output;
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
    public FluidIngredient getFluidInput()
    {
        return fluidInput;
    }

    @Override
    public ItemIngredient getPen()
    {
        return pen;
    }

    @Override
    public int getPenCost()
    {
        return penCost;
    }

    @Override
    public ItemIngredient getUnresolvedNote()
    {
        return unresolvedNote;
    }

    @Override
    public ItemIngredient getOutput()
    {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.RESEARCH_TABLE_RESEARCH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.RESEARCH_TABLE_RESEARCH.get();
    }
}
