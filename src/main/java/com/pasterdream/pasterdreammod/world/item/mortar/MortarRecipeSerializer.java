package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.recipe.genericrecipe.GenericPasterDreamRecipeSerializer;

public class MortarRecipeSerializer extends GenericPasterDreamRecipeSerializer<MortarRecipe>
{
    public MortarRecipeSerializer()
    {
        super(MortarRecipe::new);
    }
}
