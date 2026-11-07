package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/** Balances returned empty containers when a recipe produces a filled container. */
public final class ContainerBalanceHelper
{
    private ContainerBalanceHelper() {}

    private static Item getEmptyContainerType(ItemStack stack)
    {
        ItemStack remainder = stack.getCraftingRemainingItem();
        if (!remainder.isEmpty()) return remainder.getItem();
        FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForFillToEmpty(stack);
        return entry == null ? null : entry.emptyContainerItemStack.getItem();
    }

    public static NonNullList<ItemStack> balance(CraftingInput input, ItemStack result,
                                                  NonNullList<ItemStack> defaultRemaining,
                                                  List<Ingredient> ingredients)
    {
        Item emptyType = getEmptyContainerType(result);
        if (emptyType == null) return defaultRemaining;
        int emptyInputs = 0;
        int filledInputs = 0;
        for (int i = 0; i < input.size(); i++)
        {
            ItemStack stack = input.getItem(i);
            if (stack.is(emptyType)) emptyInputs++;
            else if (getEmptyContainerType(stack) == emptyType) filledInputs++;
        }
        int cancel = Math.min(filledInputs, Math.max(0, result.getCount() - emptyInputs));
        NonNullList<ItemStack> balanced = NonNullList.withSize(defaultRemaining.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultRemaining.size(); i++) balanced.set(i, defaultRemaining.get(i).copy());
        for (int i = 0; i < input.size() && cancel > 0; i++)
        {
            if (getEmptyContainerType(input.getItem(i)) == emptyType && balanced.get(i).is(emptyType))
            {
                balanced.set(i, ItemStack.EMPTY);
                cancel--;
            }
        }
        return balanced;
    }

    public static void validateOrThrow(String recipeName, ItemStack result, ItemStack... inputs)
    {
        Item emptyType = getEmptyContainerType(result);
        if (emptyType == null) return;
        int available = 0;
        for (ItemStack input : inputs)
        {
            if (input.is(emptyType) || getEmptyContainerType(input) == emptyType) available += input.getCount();
        }
        if (result.getCount() > available)
            throw new IllegalStateException("Recipe '" + recipeName + "' requires " + result.getCount()
                    + " containers but provides " + available);
    }
}
