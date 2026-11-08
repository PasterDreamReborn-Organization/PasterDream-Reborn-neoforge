package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.dreamaccumulatorrecipe.IDreamAccumulatorRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class DreamAccumulatorRecipe implements IDreamAccumulatorRecipe, Recipe<RecipeInput>
{
    private final List<TagKey<Biome>> biomeTags;
    private final int time;
    private final ItemIngredient catalyst;
    private final double catalystMultiplier;
    private final ItemStack output;

    public DreamAccumulatorRecipe(List<TagKey<Biome>> biomeTags, int time, ItemIngredient catalyst, double catalystMultiplier, ItemStack output)
    {
        this.biomeTags = biomeTags;
        this.time = time;
        this.catalyst = catalyst;
        this.catalystMultiplier = catalystMultiplier;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeInput container, Level level)
    {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registryAccess)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider access)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public List<TagKey<Biome>> getBiomeTags()
    {
        return biomeTags;
    }

    @Override
    public int getTime()
    {
        return time;
    }

    public ItemIngredient getCatalyst()
    {
        return catalyst;
    }

    @Override
    public double getCatalystMultiplier()
    {
        return catalystMultiplier;
    }

    @Override
    public ItemStack getOutput()
    {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.DREAM_ACCUMULATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.DREAM_ACCUMULATOR.get();
    }
}
