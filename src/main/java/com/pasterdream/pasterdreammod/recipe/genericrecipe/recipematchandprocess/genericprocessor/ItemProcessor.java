package com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.genericprocessor;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemProcessor
{
    public static List<ItemStack> processor(List<ItemStack> required, List<ItemStack> inventory, Boolean isAdd)
    {
        List<ItemStack> result = new ArrayList<>(inventory.size());
        for (ItemStack stack : inventory) result.add(stack.copy());
        for (ItemStack requirement : required)
        {
            if (requirement.isEmpty()) return null;
            int remaining = requirement.getCount();
            for (int i = 0; i < result.size() && remaining > 0; i++)
            {
                ItemStack stack = result.get(i);
                if (stack.isEmpty() || !(isAdd
                        ? ItemStack.isSameItemSameComponents(stack, requirement)
                        : ItemStack.isSameItem(stack, requirement))) continue;
                int amount = Math.min(remaining, isAdd ? stack.getMaxStackSize() - stack.getCount() : stack.getCount());
                if (amount <= 0) continue;
                if (isAdd) stack.grow(amount);
                else stack.shrink(amount);
                remaining -= amount;
            }
            if (isAdd)
            {
                for (int i = 0; i < result.size() && remaining > 0; i++)
                {
                    if (!result.get(i).isEmpty()) continue;
                    int amount = Math.min(remaining, requirement.getMaxStackSize());
                    result.set(i, requirement.copyWithCount(amount));
                    remaining -= amount;
                }
            }
            if (remaining > 0) return null;
        }
        return result;
    }
}
