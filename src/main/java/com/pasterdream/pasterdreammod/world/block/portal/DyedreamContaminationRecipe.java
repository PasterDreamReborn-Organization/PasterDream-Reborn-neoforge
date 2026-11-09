package com.pasterdream.pasterdreammod.world.block.portal;

import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * 染梦污染配方：染梦传送门/裂隙方块周期 tick 时，把 {@code from}（方块或方块标签）转换为 {@code to} 方块状态。
 * <p>
 * JSON 示例：
 * <pre>
 * {
 *   "type": "pasterdream:dyedream_contamination",
 *   "from": "#pasterdream:dyedream_contamination_to_dirt",
 *   "to": "pasterdream:dyedream_dirt"
 * }
 * </pre>
 */
public class DyedreamContaminationRecipe implements Recipe<RecipeInput> {
    @Nullable
    private final TagKey<Block> inputTag;
    @Nullable
    private final Block inputBlock;
    private final BlockState output;

    public DyedreamContaminationRecipe(@Nullable TagKey<Block> inputTag,
                                       @Nullable Block inputBlock, BlockState output) {
        this.inputTag = inputTag;
        this.inputBlock = inputBlock;
        this.output = output;
    }

    @Nullable
    public TagKey<Block> getInputTag() {
        return inputTag;
    }

    @Nullable
    public Block getInputBlock() {
        return inputBlock;
    }

    public BlockState getOutput() {
        return output;
    }

    public String getSerializedFrom() {
        if (inputTag != null) {
            return "#" + inputTag.location();
        }
        if (inputBlock != null) {
            return BuiltInRegistries.BLOCK.getKey(inputBlock).toString();
        }
        return "minecraft:air";
    }

    public String getSerializedTo() {
        return BuiltInRegistries.BLOCK.getKey(output.getBlock()).toString();
    }

    public boolean matches(BlockState state) {
        if (inputTag != null) {
            return state.is(inputTag);
        }
        return inputBlock != null && state.is(inputBlock);
    }

    /** 输出方块带雪时继承来源状态的雪层属性。 */
    public BlockState getOutputState(BlockState input) {
        BlockState result = output;
        if (result.hasProperty(SnowyDirtBlock.SNOWY) && input.hasProperty(SnowyDirtBlock.SNOWY)) {
            result = result.setValue(SnowyDirtBlock.SNOWY, input.getValue(SnowyDirtBlock.SNOWY));
        }
        return result;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DYEDREAM_CONTAMINATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DYEDREAM_CONTAMINATION.get();
    }
}
