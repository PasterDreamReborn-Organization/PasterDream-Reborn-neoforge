package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class MortarRecipe extends GenericPasterDreamRecipe
{
    public MortarRecipe(List<FluidIngredient> fluidInputs, List<ItemIngredient> itemInputs,
                        List<FluidIngredient> fluidOutputs, List<ItemIngredient> itemOutputs, int processingTime)
    {
        super(fluidInputs, itemInputs, fluidOutputs, itemOutputs, processingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.MORTAR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.MORTAR.get();
    }
}
