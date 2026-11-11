package com.pasterdream.pasterdreammod.recipe.researchtableinventory;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ResearchTableResearchRecipeProcessor
{
    public static ResearchTableResearchRecipeInventory processing(ResearchTableResearchRecipeInventory recipeRequire, ResearchTableResearchRecipeInventory machineInventory, RandomSource random)
    {
        FluidStack requireFluidStack = recipeRequire.fluidStack();
        FluidStack machineFluidStack = machineInventory.fluidStack();
        FluidStack lessFluidStack = FluidStack.EMPTY;
        if(requireFluidStack.getFluid().equals(machineFluidStack.getFluid()))
        {
            if(machineFluidStack.getAmount() > requireFluidStack.getAmount())
            {
                lessFluidStack = new FluidStack(machineFluidStack.getFluid(), machineFluidStack.getAmount() - requireFluidStack.getAmount());
            }
            else
                if(machineFluidStack.getAmount() == requireFluidStack.getAmount())
                {
                    lessFluidStack = FluidStack.EMPTY;
                }
                    else
                    {
                        return null;
                    }
        }

        ItemStack requirePen = recipeRequire.pen();
        ItemStack machinePen = machineInventory.pen();
        ItemStack lessPen = ItemStack.EMPTY;
        if(ItemStack.isSameItem(requirePen, machinePen))
        {
            if(machinePen.isDamageableItem())
            {
                net.minecraft.nbt.CompoundTag penTag = ItemIngredient.getCustomDataTag(requirePen);
                int penCost = (penTag != null && penTag.contains("Damage")) ? penTag.getInt("Damage") : 1;
                int machinePenDurability = machinePen.getMaxDamage() - machinePen.getDamageValue();

                if(machinePenDurability >= penCost)
                {
                    lessPen = machinePen.copy();
                    lessPen.setDamageValue(lessPen.getDamageValue() + penCost);
                }
                    else
                    {
                        return null;
                    }
            }
                else
                {
                    lessPen = machinePen.copy();
                }
        }
            else
            {
                return null;
            }

        ItemStack requireUnresolvedNote = recipeRequire.unresolvedNote();
        ItemStack machineUnresolvedNote = machineInventory.unresolvedNote();
        ItemStack lessUnresolvedNote = ItemStack.EMPTY;
        if(ItemStack.isSameItem(requireUnresolvedNote, machineUnresolvedNote) && machineUnresolvedNote.getCount() >= requireUnresolvedNote.getCount())
        {
            lessUnresolvedNote = machineUnresolvedNote.copy();
            lessUnresolvedNote.shrink(requireUnresolvedNote.getCount());
        }
            else
            {
                return null;
            }

        ItemStack machineOutput = machineInventory.output();
        ItemStack output = new ItemStack(recipeRequire.output().getItem(), recipeRequire.output().getCount());
        ItemIngredient.setCustomDataTag(output, ItemIngredient.getCustomDataTag(machineUnresolvedNote));
        if(machineInventory.output().isEmpty())
        {
            return new ResearchTableResearchRecipeInventory(lessFluidStack, lessPen, lessUnresolvedNote, output);
        }
        else
            if (ItemStack.isSameItemSameComponents(machineOutput, output) && machineOutput.getCount() + output.getCount() <= output.getMaxStackSize())
            {
                output.setCount(machineOutput.getCount() + output.getCount());
                return new ResearchTableResearchRecipeInventory(lessFluidStack, lessPen, lessUnresolvedNote, output);
            }
                else
                {
                    return null;
                }
    }
}
