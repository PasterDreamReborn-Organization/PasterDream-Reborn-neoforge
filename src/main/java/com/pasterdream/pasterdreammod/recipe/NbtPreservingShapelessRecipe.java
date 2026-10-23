package com.pasterdream.pasterdreammod.recipe;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

/** 沿用来源类名和序列化 ID，旧 NBT 保留逻辑迁移为 DataComponentPatch。 */
public class NbtPreservingShapelessRecipe extends ShapelessRecipe {
    public NbtPreservingShapelessRecipe(ShapelessRecipe recipe) {
        super(recipe.getGroup(), recipe.category(), recipe.getResultItem(RegistryAccess.EMPTY).copy(), recipe.getIngredients());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack result = super.assemble(input, registries);
        for (ItemStack stack : input.items()) {
            // 1.20 的耐久物品即使未损伤也具有 Damage:0 NBT；保留其参与来源选择的顺序。
            if (!stack.isEmpty() && (stack.isDamageableItem() || !stack.getComponentsPatch().isEmpty())) {
                // 仅复制显式修改，不将钻石工具的默认耐久、属性和挖掘组件覆盖到钛金工具。
                result.applyComponents(stack.getComponentsPatch());
                break;
            }
        }
        if (result.has(DataComponents.DAMAGE)) {
            result.setDamageValue(0);
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.NBT_PRESERVING_SHAPELESS_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<NbtPreservingShapelessRecipe> {
        private static final ShapelessRecipe.Serializer FALLBACK = new ShapelessRecipe.Serializer();
        private static final MapCodec<NbtPreservingShapelessRecipe> CODEC = FALLBACK.codec()
                .xmap(NbtPreservingShapelessRecipe::new, recipe -> recipe);
        private static final StreamCodec<RegistryFriendlyByteBuf, NbtPreservingShapelessRecipe> STREAM_CODEC =
                FALLBACK.streamCodec().map(NbtPreservingShapelessRecipe::new, recipe -> recipe);

        @Override
        public MapCodec<NbtPreservingShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NbtPreservingShapelessRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
