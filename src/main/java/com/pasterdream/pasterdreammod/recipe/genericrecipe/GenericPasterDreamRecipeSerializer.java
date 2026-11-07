package com.pasterdream.pasterdreammod.recipe.genericrecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.List;

public class GenericPasterDreamRecipeSerializer<T extends GenericPasterDreamRecipe> implements RecipeSerializer<T>
{
    @FunctionalInterface
    public interface RecipeFactory<T>
    {
        T create(List<FluidIngredient> fluidInputs, List<ItemIngredient> itemInputs,
                 List<FluidIngredient> fluidOutputs, List<ItemIngredient> itemOutputs, int processingTime);
    }

    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public GenericPasterDreamRecipeSerializer(RecipeFactory<T> factory)
    {
        codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                FluidIngredient.CODEC.listOf().optionalFieldOf("fluidInputs", List.of()).forGetter(GenericPasterDreamRecipe::getInputFluidIngredients),
                ItemIngredient.CODEC.listOf().optionalFieldOf("itemInputs", List.of()).forGetter(GenericPasterDreamRecipe::getInputItemIngredients),
                FluidIngredient.CODEC.listOf().optionalFieldOf("fluidOutputs", List.of()).forGetter(GenericPasterDreamRecipe::getOutputFluidIngredients),
                ItemIngredient.CODEC.listOf().optionalFieldOf("itemOutputs", List.of()).forGetter(GenericPasterDreamRecipe::getOutputItemIngredients),
                com.mojang.serialization.Codec.INT.optionalFieldOf("processingTime", 0).forGetter(GenericPasterDreamRecipe::getRecipeTime)
        ).apply(instance, factory::create));
        streamCodec = StreamCodec.of(
                (buffer, recipe) ->
                {
                    writeFluids(buffer, recipe.getInputFluidIngredients());
                    writeItems(buffer, recipe.getInputItemIngredients());
                    writeFluids(buffer, recipe.getOutputFluidIngredients());
                    writeItems(buffer, recipe.getOutputItemIngredients());
                    buffer.writeVarInt(recipe.getRecipeTime());
                },
                buffer -> factory.create(readFluids(buffer), readItems(buffer), readFluids(buffer),
                        readItems(buffer), buffer.readVarInt()));
    }

    private static void writeFluids(RegistryFriendlyByteBuf buffer, List<FluidIngredient> ingredients)
    {
        buffer.writeVarInt(ingredients.size());
        for (FluidIngredient ingredient : ingredients) ingredient.toNetwork(buffer);
    }

    private static void writeItems(RegistryFriendlyByteBuf buffer, List<ItemIngredient> ingredients)
    {
        buffer.writeVarInt(ingredients.size());
        for (ItemIngredient ingredient : ingredients) ingredient.toNetwork(buffer);
    }

    private static List<FluidIngredient> readFluids(RegistryFriendlyByteBuf buffer)
    {
        int count = buffer.readVarInt();
        if (count < 0 || count > 32) throw new IllegalArgumentException("流体原料数量越界：" + count);
        List<FluidIngredient> ingredients = new ArrayList<>(count);
        for (int i = 0; i < count; i++) ingredients.add(FluidIngredient.fromNetwork(buffer));
        return ingredients;
    }

    private static List<ItemIngredient> readItems(RegistryFriendlyByteBuf buffer)
    {
        int count = buffer.readVarInt();
        if (count < 0 || count > 64) throw new IllegalArgumentException("物品原料数量越界：" + count);
        List<ItemIngredient> ingredients = new ArrayList<>(count);
        for (int i = 0; i < count; i++) ingredients.add(ItemIngredient.fromNetwork(buffer));
        return ingredients;
    }

    @Override
    public MapCodec<T> codec() { return codec; }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() { return streamCodec; }
}
