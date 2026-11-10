package com.pasterdream.pasterdreammod.recipe;

import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class GoldenFoxTradeRecipe implements Recipe<RecipeInput> {
    private final Ingredient ingredient;
    private final ItemStack result;

    public GoldenFoxTradeRecipe(Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public boolean matches(RecipeInput container, Level level) {
        return ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack getResult() {
        return result;
    }

    public boolean matches(ItemStack stack) {
        return ingredient.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GOLDEN_FOX_TRADE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GOLDEN_FOX_TRADE.get();
    }
}
