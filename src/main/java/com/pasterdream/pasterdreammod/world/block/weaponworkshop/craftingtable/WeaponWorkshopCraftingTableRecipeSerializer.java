package com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.List;

public class WeaponWorkshopCraftingTableRecipeSerializer implements RecipeSerializer<WeaponWorkshopCraftingTableRecipe>
{
    private static final MapCodec<WeaponWorkshopCraftingTableRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemIngredient.CODEC.listOf().fieldOf("itemInputs").forGetter(WeaponWorkshopCraftingTableRecipe::getInputItemIngredients),
            ItemIngredient.CODEC.listOf().fieldOf("enhanceStone").forGetter(recipe -> List.of(recipe.getEnhanceStone())),
            ItemIngredient.CODEC.listOf().fieldOf("itemOutputs").forGetter(recipe -> List.of(recipe.getOutputItemIngredients()))
    ).apply(instance, (inputItems, enhanceStones, outputItems) -> new WeaponWorkshopCraftingTableRecipe(
            inputItems,
            enhanceStones.isEmpty() ? ItemIngredient.empty() : enhanceStones.get(0),
            outputItems.isEmpty() ? ItemIngredient.empty() : outputItems.get(0))));

    private static final StreamCodec<RegistryFriendlyByteBuf, WeaponWorkshopCraftingTableRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) ->
            {
                buffer.writeVarInt(recipe.getInputItemIngredients().size());
                for (ItemIngredient itemIngredient : recipe.getInputItemIngredients())
                {
                    itemIngredient.toNetwork(buffer);
                }

                recipe.getEnhanceStone().toNetwork(buffer);
                recipe.getOutputItemIngredients().toNetwork(buffer);
            },
            buffer ->
            {
                int inputItemCount = buffer.readVarInt();
                List<ItemIngredient> inputItems = new ArrayList<>();
                for (int i = 0; i < inputItemCount; i++)
                {
                    inputItems.add(ItemIngredient.fromNetwork(buffer));
                }

                ItemIngredient enhanceStone = ItemIngredient.fromNetwork(buffer);
                ItemIngredient outputItems = ItemIngredient.fromNetwork(buffer);

                return new WeaponWorkshopCraftingTableRecipe(inputItems, enhanceStone, outputItems);
            });

    @Override
    public MapCodec<WeaponWorkshopCraftingTableRecipe> codec()
    {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, WeaponWorkshopCraftingTableRecipe> streamCodec()
    {
        return STREAM_CODEC;
    }
}
