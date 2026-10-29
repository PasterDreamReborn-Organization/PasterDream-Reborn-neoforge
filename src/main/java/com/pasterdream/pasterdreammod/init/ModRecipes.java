package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.recipe.NbtPreservingShapelessRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 配方类型与序列化器注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModRecipes.java}。
 */
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, PasterDreamMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, NbtPreservingShapelessRecipe.Serializer> NBT_PRESERVING_SHAPELESS_SERIALIZER =
            RECIPE_SERIALIZERS.register("nbt_preserving_shapeless", NbtPreservingShapelessRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

    private ModRecipes() {
    }
}
