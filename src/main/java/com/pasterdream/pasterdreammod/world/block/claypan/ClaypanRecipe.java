package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class ClaypanRecipe extends GenericPasterDreamRecipe
{
    public ClaypanRecipe(List<FluidIngredient> fluidInputs, List<ItemIngredient> itemOutputs, int processingTime)
    {
        super(fluidInputs, List.of(), List.of(), itemOutputs, processingTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.CLAYPAN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.CLAYPAN.get();
    }
}
