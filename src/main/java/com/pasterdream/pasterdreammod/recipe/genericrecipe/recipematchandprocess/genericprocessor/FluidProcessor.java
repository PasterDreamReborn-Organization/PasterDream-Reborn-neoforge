package com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.genericprocessor;

import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FluidProcessor
{
    public static List<FluidStack> processor(List<FluidStack> required, List<FluidStack> inventory, Boolean isAdd, int maxOutputTankCapacity)
    {
        List<FluidStack> result = new ArrayList<>(inventory.size());
        for (FluidStack stack : inventory) result.add(stack.copy());
        for (FluidStack requirement : required)
        {
            if (requirement.isEmpty()) return null;
            int remaining = requirement.getAmount();
            for (int i = 0; i < result.size() && remaining > 0; i++)
            {
                FluidStack stack = result.get(i);
                if (stack.isEmpty() || !FluidStack.isSameFluidSameComponents(stack, requirement)) continue;
                int amount = Math.min(remaining, isAdd ? maxOutputTankCapacity - stack.getAmount() : stack.getAmount());
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
                    int amount = Math.min(remaining, maxOutputTankCapacity);
                    result.set(i, requirement.copyWithAmount(amount));
                    remaining -= amount;
                }
            }
            if (remaining > 0) return null;
        }
        return result;
    }
}
