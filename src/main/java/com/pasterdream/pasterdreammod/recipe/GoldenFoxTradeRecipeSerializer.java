package com.pasterdream.pasterdreammod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class GoldenFoxTradeRecipeSerializer implements RecipeSerializer<GoldenFoxTradeRecipe> {
    private static final MapCodec<GoldenFoxTradeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(GoldenFoxTradeRecipe::getIngredient),
                    ItemStack.CODEC.fieldOf("result").forGetter(GoldenFoxTradeRecipe::getResult)
            ).apply(instance, GoldenFoxTradeRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, GoldenFoxTradeRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, GoldenFoxTradeRecipe::getIngredient,
                    ItemStack.STREAM_CODEC, GoldenFoxTradeRecipe::getResult,
                    GoldenFoxTradeRecipe::new);

    @Override
    public MapCodec<GoldenFoxTradeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, GoldenFoxTradeRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
