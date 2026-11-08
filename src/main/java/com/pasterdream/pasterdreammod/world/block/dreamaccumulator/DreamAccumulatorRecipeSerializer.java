package com.pasterdream.pasterdreammod.world.block.dreamaccumulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class DreamAccumulatorRecipeSerializer implements RecipeSerializer<DreamAccumulatorRecipe>
{
    private static final MapCodec<DreamAccumulatorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TagKey.codec(Registries.BIOME).listOf().optionalFieldOf("biomeTags", List.of()).forGetter(DreamAccumulatorRecipe::getBiomeTags),
            Codec.INT.fieldOf("time").forGetter(DreamAccumulatorRecipe::getTime),
            ItemIngredient.CODEC.fieldOf("catalyst").forGetter(DreamAccumulatorRecipe::getCatalyst),
            Codec.DOUBLE.fieldOf("catalystMultiplier").forGetter(DreamAccumulatorRecipe::getCatalystMultiplier),
            ItemStack.CODEC.fieldOf("output").forGetter(DreamAccumulatorRecipe::getOutput)
    ).apply(instance, DreamAccumulatorRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, DreamAccumulatorRecipe> STREAM_CODEC = StreamCodec.of(
            (buf, recipe) ->
            {
                List<TagKey<Biome>> tags = recipe.getBiomeTags();
                buf.writeVarInt(tags.size());
                for (TagKey<Biome> tag : tags)
                {
                    buf.writeResourceLocation(tag.location());
                }

                buf.writeVarInt(recipe.getTime());
                recipe.getCatalyst().toNetwork(buf);
                buf.writeDouble(recipe.getCatalystMultiplier());
                ItemStack.STREAM_CODEC.encode(buf, recipe.getOutput());
            },
            buffer ->
            {
                int size = buffer.readVarInt();
                List<TagKey<Biome>> biomeTags = new ArrayList<>(size);
                for (int i = 0; i < size; i++)
                {
                    biomeTags.add(TagKey.create(Registries.BIOME, buffer.readResourceLocation()));
                }

                int time = buffer.readVarInt();
                ItemIngredient catalyst = ItemIngredient.fromNetwork(buffer);
                double catalystMultiplier = buffer.readDouble();
                ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
                return new DreamAccumulatorRecipe(biomeTags, time, catalyst, catalystMultiplier, output);
            });

    @Override
    public MapCodec<DreamAccumulatorRecipe> codec()
    {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DreamAccumulatorRecipe> streamCodec()
    {
        return STREAM_CODEC;
    }
}
