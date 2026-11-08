package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipeSerializer;

public class ShadowBlastFurnaceRecipeSerializer extends GenericPasterDreamRecipeSerializer<ShadowBlastFurnaceRecipe>
{
    public ShadowBlastFurnaceRecipeSerializer()
    {
        super((fluidInputs, itemInputs, fluidOutputs, itemOutputs, processingTime) ->
                new ShadowBlastFurnaceRecipe(fluidInputs, itemInputs, itemOutputs, processingTime));
    }
}
