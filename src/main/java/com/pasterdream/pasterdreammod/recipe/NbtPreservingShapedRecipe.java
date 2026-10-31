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
import net.minecraft.world.item.crafting.ShapedRecipe;

/** 沿用来源类名和序列化 ID，旧 NBT 保留逻辑迁移为 DataComponentPatch。 */
public class NbtPreservingShapedRecipe extends ShapedRecipe {
    public NbtPreservingShapedRecipe(ShapedRecipe recipe) {
        super(recipe.getGroup(), recipe.category(), recipe.pattern,
                recipe.getResultItem(RegistryAccess.EMPTY).copy(), recipe.showNotification());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack result = super.assemble(input, registries);
        for (ItemStack stack : input.items()) {
            // 1.20 的耐久物品即使未损伤也具有 Damage:0 NBT；保留其参与来源选择的顺序。
            if (!stack.isEmpty() && (stack.isDamageableItem() || !stack.getComponentsPatch().isEmpty())) {
                // 仅复制显式修改，不将旧工具的默认耐久、属性和挖掘组件覆盖到升级产物。
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
        return ModRecipes.NBT_PRESERVING_SHAPED_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<NbtPreservingShapedRecipe> {
        private static final ShapedRecipe.Serializer FALLBACK = new ShapedRecipe.Serializer();
        private static final MapCodec<NbtPreservingShapedRecipe> CODEC = FALLBACK.codec()
                .xmap(NbtPreservingShapedRecipe::new, recipe -> recipe);
        private static final StreamCodec<RegistryFriendlyByteBuf, NbtPreservingShapedRecipe> STREAM_CODEC =
                FALLBACK.streamCodec().map(NbtPreservingShapedRecipe::new, recipe -> recipe);

        @Override
        public MapCodec<NbtPreservingShapedRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NbtPreservingShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
