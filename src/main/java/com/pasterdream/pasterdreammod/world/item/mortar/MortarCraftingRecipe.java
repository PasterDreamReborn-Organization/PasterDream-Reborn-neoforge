package com.pasterdream.pasterdreammod.world.item.mortar;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.helper.ContainerBalanceHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class MortarCraftingRecipe extends ShapelessRecipe
{
    public MortarCraftingRecipe(ShapelessRecipe recipe)
    {
        super(recipe.getGroup(), recipe.category(), recipe.getResultItem(RegistryAccess.EMPTY).copy(), recipe.getIngredients());
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input)
    {
        NonNullList<ItemStack> remaining = super.getRemainingItems(input);
        for (int i = 0; i < input.size(); i++)
        {
            if (input.getItem(i).is(ModItems.MORTAR.get()))
            {
                remaining.set(i, input.getItem(i).copyWithCount(1));
            }
        }
        return ContainerBalanceHelper.balance(input, getResultItem(RegistryAccess.EMPTY), remaining, getIngredients());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.MORTAR_CRAFTING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<MortarCraftingRecipe>
    {
        private static final ShapelessRecipe.Serializer FALLBACK = new ShapelessRecipe.Serializer();
        private static final MapCodec<MortarCraftingRecipe> CODEC = FALLBACK.codec()
                .xmap(MortarCraftingRecipe::new, recipe -> recipe);
        private static final StreamCodec<RegistryFriendlyByteBuf, MortarCraftingRecipe> STREAM_CODEC =
                FALLBACK.streamCodec().map(MortarCraftingRecipe::new, recipe -> recipe);

        @Override
        public MapCodec<MortarCraftingRecipe> codec()
        {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MortarCraftingRecipe> streamCodec()
        {
            return STREAM_CODEC;
        }
    }
}
