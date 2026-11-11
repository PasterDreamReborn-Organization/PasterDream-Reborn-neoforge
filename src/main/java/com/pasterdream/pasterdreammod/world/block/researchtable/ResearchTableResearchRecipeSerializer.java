package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class ResearchTableResearchRecipeSerializer implements RecipeSerializer<ResearchTableResearchRecipe>
{
    private static final MapCodec<ResearchTableResearchRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FluidIngredient.CODEC.listOf().fieldOf("fluidInputs").forGetter(recipe -> List.of(recipe.getFluidInput())),
            ItemIngredient.CODEC.listOf().fieldOf("pen").forGetter(recipe -> List.of(recipe.getPen())),
            ItemIngredient.CODEC.listOf().fieldOf("unresolvedNote").forGetter(recipe -> List.of(recipe.getUnresolvedNote())),
            ItemIngredient.CODEC.listOf().fieldOf("output").forGetter(recipe -> List.of(recipe.getOutput()))
    ).apply(instance, (fluidInputs, pens, unresolvedNotes, outputs) -> new ResearchTableResearchRecipe(
            fluidInputs.isEmpty() ? FluidIngredient.of(net.minecraft.world.level.material.Fluids.EMPTY, 1) : fluidInputs.get(0),
            pens.isEmpty() ? ItemIngredient.empty() : pens.get(0),
            unresolvedNotes.isEmpty() ? ItemIngredient.empty() : unresolvedNotes.get(0),
            outputs.isEmpty() ? ItemIngredient.empty() : outputs.get(0))));

    private static final StreamCodec<RegistryFriendlyByteBuf, ResearchTableResearchRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) ->
            {
                recipe.getFluidInput().toNetwork(buffer);
                recipe.getPen().toNetwork(buffer);
                recipe.getUnresolvedNote().toNetwork(buffer);
                recipe.getOutput().toNetwork(buffer);
            },
            buffer -> new ResearchTableResearchRecipe(
                    FluidIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer)));

    @Override
    public MapCodec<ResearchTableResearchRecipe> codec()
    {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ResearchTableResearchRecipe> streamCodec()
    {
        return STREAM_CODEC;
    }
}
