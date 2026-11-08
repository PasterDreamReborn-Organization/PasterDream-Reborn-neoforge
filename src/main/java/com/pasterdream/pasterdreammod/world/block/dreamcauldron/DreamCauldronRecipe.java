package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class DreamCauldronRecipe extends GenericPasterDreamRecipe
{
    public DreamCauldronRecipe(List<FluidIngredient> fluidInputs, List<ItemIngredient> itemInputs, List<FluidIngredient> fluidOutputs, List<ItemIngredient> itemOutputs)
    {
        super(fluidInputs, itemInputs, fluidOutputs, itemOutputs, 0);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.DREAM_CAULDRON_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.DREAM_CAULDRON.get();
    }
}
