package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipeSerializer;

public class DreamCauldronRecipeSerializer extends GenericPasterDreamRecipeSerializer<DreamCauldronRecipe>
{
    public DreamCauldronRecipeSerializer()
    {
        super((fluidInputs, itemInputs, fluidOutputs, itemOutputs, processingTime) ->
                new DreamCauldronRecipe(fluidInputs, itemInputs, fluidOutputs, itemOutputs));
    }
}
