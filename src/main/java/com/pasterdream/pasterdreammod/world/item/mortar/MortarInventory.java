package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class MortarInventory extends ItemStackHandler implements IFluidHandlerItem, IFluidContainer
{
    private final MortarDataHandler dataHandler;
    private final FluidTank[] fluidTanks;
    private boolean suppressSave;

    public MortarInventory(MortarDataHandler dataHandler)
    {
        super(12);
        this.dataHandler = dataHandler;
        suppressSave = true;
        fluidTanks = new FluidTank[6];
        for (int i = 0; i < fluidTanks.length; i++)
        {
            fluidTanks[i] = new FluidTank(1000)
            {
                @Override
                public void setFluid(FluidStack stack)
                {
                    super.setFluid(stack);
                    onContentsChanged();
                }

                @Override
                protected void onContentsChanged()
                {
                    if (!suppressSave) saveFluids();
                }
            };
        }
        List<ItemStack> items = dataHandler.getAllItems();
        for (int i = 0; i < getSlots(); i++)
        {
            setStackInSlot(i, items.get(i));
        }
        List<FluidStack> fluids = dataHandler.getAllFluids();
        for (int i = 0; i < fluidTanks.length; i++)
        {
            fluidTanks[i].setFluid(fluids.get(i));
        }
        suppressSave = false;
    }

    @Override
    protected void onContentsChanged(int slotIndex)
    {
        if (!suppressSave)
        {
            save();
        }
    }

    public void save()
    {
        List<ItemStack> items = new ArrayList<>(getSlots());
        for (int i = 0; i < getSlots(); i++)
        {
            items.add(getStackInSlot(i));
        }
        dataHandler.saveItems(items);
    }

    private void saveFluids()
    {
        List<FluidStack> fluids = new ArrayList<>(fluidTanks.length);
        for (FluidTank tank : fluidTanks) fluids.add(tank.getFluid());
        dataHandler.saveFluids(fluids);
    }

    public FluidTank[] getFluidTanks()
    {
        return fluidTanks;
    }

    @Override
    public int getFluidContainerSize()
    {
        return fluidTanks.length;
    }

    @Override
    public FluidStack getFluid(int index)
    {
        return getFluidInTank(index);
    }

    @Override
    public void setFluid(int index, FluidStack stack)
    {
        if (index >= 0 && index < fluidTanks.length) fluidTanks[index].setFluid(stack.copy());
    }

    @Override
    public int getMaxFluidCapacity(int index)
    {
        return getTankCapacity(index);
    }

    //流体槽变更：必须使用与原版 Container 不同的方法名，否则上游 Forge 正式环境会因重混淆而 AbstractMethodError
    @Override
    public void setFluidChanged()
    {
        save();
        saveFluids();
    }

    @Override
    public boolean canPlaceFluid(int index, FluidStack stack)
    {
        return index >= 0 && index < 4;
    }

    @Override
    public int getTanks()
    {
        return fluidTanks.length;
    }

    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return tank >= 0 && tank < fluidTanks.length ? fluidTanks[tank].getFluid() : FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return tank >= 0 && tank < fluidTanks.length ? fluidTanks[tank].getCapacity() : 0;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack)
    {
        return tank >= 0 && tank < 4 && fluidTanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        for (int i = 0; i < 4; i++)
        {
            if (!fluidTanks[i].getFluid().isEmpty() &&
                    FluidStack.isSameFluidSameComponents(fluidTanks[i].getFluid(), resource))
            {
                int amount = fluidTanks[i].fill(resource, action);
                if (amount > 0) return amount;
            }
        }
        for (int i = 0; i < 4; i++)
        {
            if (fluidTanks[i].isEmpty()) return fluidTanks[i].fill(resource, action);
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        for (int i = 4; i < fluidTanks.length; i++)
        {
            FluidStack drained = fluidTanks[i].drain(resource, action);
            if (!drained.isEmpty()) return drained;
        }
        for (int i = 0; i < 4; i++)
        {
            FluidStack drained = fluidTanks[i].drain(resource, action);
            if (!drained.isEmpty()) return drained;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action)
    {
        for (int i = 4; i < fluidTanks.length; i++)
        {
            FluidStack drained = fluidTanks[i].drain(maxDrain, action);
            if (!drained.isEmpty()) return drained;
        }
        for (int i = 0; i < 4; i++)
        {
            FluidStack drained = fluidTanks[i].drain(maxDrain, action);
            if (!drained.isEmpty()) return drained;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public ItemStack getContainer()
    {
        return dataHandler.getThisMortarItem();
    }
}
