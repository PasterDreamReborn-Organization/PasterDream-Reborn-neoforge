package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipeSerializer;

public class ClaypanRecipeSerializer extends GenericPasterDreamRecipeSerializer<ClaypanRecipe>
{
    public ClaypanRecipeSerializer()
    {
        super((fluidInputs, itemInputs, fluidOutputs, itemOutputs, processingTime) ->
                new ClaypanRecipe(fluidInputs, itemOutputs, processingTime));
    }
}
