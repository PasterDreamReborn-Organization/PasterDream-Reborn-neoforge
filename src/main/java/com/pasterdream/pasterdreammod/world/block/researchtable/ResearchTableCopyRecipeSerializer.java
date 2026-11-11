package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class ResearchTableCopyRecipeSerializer implements RecipeSerializer<ResearchTableCopyRecipe>
{
    private static final MapCodec<ResearchTableCopyRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FluidIngredient.CODEC.listOf().fieldOf("fluidInputs").forGetter(recipe -> List.of(recipe.getFluidInput())),
            ItemIngredient.CODEC.listOf().fieldOf("pen").forGetter(recipe -> List.of(recipe.getPen())),
            ItemIngredient.CODEC.listOf().fieldOf("sourceBook").forGetter(recipe -> List.of(recipe.getSourceBook())),
            ItemIngredient.CODEC.listOf().fieldOf("material").forGetter(recipe -> List.of(recipe.getMaterial()))
    ).apply(instance, (fluidInputs, pens, sourceBooks, materials) -> new ResearchTableCopyRecipe(
            fluidInputs.isEmpty() ? FluidIngredient.of(net.minecraft.world.level.material.Fluids.EMPTY, 1) : fluidInputs.get(0),
            pens.isEmpty() ? ItemIngredient.empty() : pens.get(0),
            sourceBooks.isEmpty() ? ItemIngredient.empty() : sourceBooks.get(0),
            materials.isEmpty() ? ItemIngredient.empty() : materials.get(0))));

    private static final StreamCodec<RegistryFriendlyByteBuf, ResearchTableCopyRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) ->
            {
                recipe.getFluidInput().toNetwork(buffer);
                recipe.getPen().toNetwork(buffer);
                recipe.getSourceBook().toNetwork(buffer);
                recipe.getMaterial().toNetwork(buffer);
            },
            buffer -> new ResearchTableCopyRecipe(
                    FluidIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer),
                    ItemIngredient.fromNetwork(buffer)));

    @Override
    public MapCodec<ResearchTableCopyRecipe> codec()
    {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ResearchTableCopyRecipe> streamCodec()
    {
        return STREAM_CODEC;
    }
}
