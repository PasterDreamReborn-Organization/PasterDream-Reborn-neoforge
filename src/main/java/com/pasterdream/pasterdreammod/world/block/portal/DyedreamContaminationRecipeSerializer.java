package com.pasterdream.pasterdreammod.world.block.portal;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DyedreamContaminationRecipeSerializer implements RecipeSerializer<DyedreamContaminationRecipe> {

    private static final MapCodec<DyedreamContaminationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("from").forGetter(DyedreamContaminationRecipe::getSerializedFrom),
                    Codec.STRING.fieldOf("to").forGetter(DyedreamContaminationRecipe::getSerializedTo)
            ).apply(instance, DyedreamContaminationRecipeSerializer::create));

    private static final StreamCodec<RegistryFriendlyByteBuf, DyedreamContaminationRecipe> STREAM_CODEC =
            StreamCodec.of(
                    (buffer, recipe) -> {
                        // 在服务端把输入解析成具体方块列表再同步，避免客户端标签状态影响 JEI 展示。
                        List<Block> inputs = new ArrayList<>();
                        if (recipe.getInputBlock() != null) {
                            inputs.add(recipe.getInputBlock());
                        } else if (recipe.getInputTag() != null) {
                            BuiltInRegistries.BLOCK.getTags()
                                    .filter(entry -> entry.getFirst().equals(recipe.getInputTag()))
                                    .forEach(entry -> entry.getSecond().forEach(holder -> inputs.add(holder.value())));
                        }
                        buffer.writeVarInt(inputs.size());
                        for (Block block : inputs) {
                            buffer.writeVarInt(BuiltInRegistries.BLOCK.getId(block));
                        }
                        buffer.writeVarInt(Block.getId(recipe.getOutput()));
                    },
                    buffer -> {
                        int inputCount = buffer.readVarInt();
                        List<Block> displayInputs = new ArrayList<>(inputCount);
                        for (int i = 0; i < inputCount; i++) {
                            Block block = BuiltInRegistries.BLOCK.byId(buffer.readVarInt());
                            if (block != null) {
                                displayInputs.add(block);
                            }
                        }
                        BlockState output = Block.stateById(buffer.readVarInt());
                        return new DyedreamContaminationRecipe(null, null, output, displayInputs);
                    }
            );

    private static DyedreamContaminationRecipe create(String from, String to) {
        TagKey<Block> inputTag = null;
        Block inputBlock = null;
        if (from.startsWith("#")) {
            inputTag = TagKey.create(Registries.BLOCK, ResourceLocation.parse(from.substring(1)));
        } else {
            inputBlock = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(from))
                    .orElseThrow(() -> new IllegalArgumentException("Unknown block '" + from + "'"));
        }

        BlockState output;
        try {
            output = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), to, false).blockState();
        } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException("Invalid block state '" + to + "'", e);
        }
        return new DyedreamContaminationRecipe(inputTag, inputBlock, output);
    }

    @Override
    public MapCodec<DyedreamContaminationRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DyedreamContaminationRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
