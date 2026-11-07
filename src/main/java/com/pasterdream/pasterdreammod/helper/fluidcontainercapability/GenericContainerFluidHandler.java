package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class GenericContainerFluidHandler implements IFluidHandlerItem
{
    private ItemStack inputItem;
    private FluidContainerRegistry.ContainerEntry entry;
    private ItemStack outputItem;
    private final int defaultCapacity;

    public GenericContainerFluidHandler(ItemStack itemStack)
    {
        inputItem = itemStack;
        entry = FluidContainerRegistry.getEntryForFillToEmpty(inputItem);
        FluidContainerRegistry.ContainerEntry anyEntry = entry == null
                ? FluidContainerRegistry.getAnyEntryForEmpty(inputItem.getItem()) : entry;
        defaultCapacity = anyEntry == null ? 0 : anyEntry.fluidStack.getAmount();
    }

    @Override
    public int getTanks() { return 1; }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return tank == 0 && entry != null && inputItem.is(entry.fullContainerItemStack.getItem())
                ? entry.fluidStack.copy() : FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank) { return tank == 0 ? defaultCapacity : 0; }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        return tank == 0 && entry == null && !stack.isEmpty()
                && FluidContainerRegistry.getEntryForEmptyAndFluid(inputItem, stack) != null;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        if (entry != null || resource.isEmpty()) return 0;
        FluidContainerRegistry.ContainerEntry target = FluidContainerRegistry.getEntryForEmptyAndFluid(inputItem, resource);
        if (target == null || resource.getAmount() < target.fluidStack.getAmount()
                || !FluidStack.isSameFluidSameComponents(target.fluidStack, resource)) return 0;
        if (action.execute())
        {
            outputItem = target.fullContainerItemStack.copyWithCount(inputItem.getCount());
            inputItem = outputItem;
            entry = target;
        }
        return target.fluidStack.getAmount();
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        if (entry == null || resource.isEmpty() || resource.getAmount() < entry.fluidStack.getAmount()
                || !FluidStack.isSameFluidSameComponents(resource, entry.fluidStack)) return FluidStack.EMPTY;
        return drain(entry.fluidStack.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        if (entry == null || maxDrain < entry.fluidStack.getAmount()) return FluidStack.EMPTY;
        FluidStack drained = entry.fluidStack.copy();
        if (action.execute())
        {
            outputItem = entry.emptyContainerItemStack.copyWithCount(inputItem.getCount());
            inputItem = outputItem;
            entry = null;
        }
        return drained;
    }

    @Override
    public ItemStack getContainer() { return outputItem != null ? outputItem : inputItem; }
}
