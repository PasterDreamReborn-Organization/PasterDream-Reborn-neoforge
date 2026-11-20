package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FluidHandlerContainerWrapper implements IFluidContainer
{
    private final IFluidHandler handler;
    private final Runnable onChanged;
    private final int[] tankCapacities;
    private final FluidStack[] syncedFluids;

    public FluidHandlerContainerWrapper(IFluidHandler handler, Runnable onChanged)
    {
        this.handler = handler;
        this.onChanged = onChanged;
        this.tankCapacities = null;
        this.syncedFluids = null;
    }

    public FluidHandlerContainerWrapper(int[] tankCapacities)
    {
        this.handler = null;
        this.onChanged = null;
        this.tankCapacities = tankCapacities;
        this.syncedFluids = new FluidStack[tankCapacities.length];
        Arrays.fill(syncedFluids, FluidStack.EMPTY);
    }

    @Override
    public int getFluidContainerSize()
    {
        return handler == null ? syncedFluids.length : handler.getTanks();
    }

    @Override
    public FluidStack getFluid(int index)
    {
        return handler == null ? syncedFluids[index] : handler.getFluidInTank(index);
    }

    @Override
    public void setFluid(int index, FluidStack fluidStack)
    {
        if (handler == null)
        {
            syncedFluids[index] = fluidStack;
        }
    }

    @Override
    public int getMaxFluidCapacity(int index)
    {
        return handler == null ? tankCapacities[index] : handler.getTankCapacity(index);
    }

    @Override
    public void setChanged()
    {
        if (onChanged != null)
        {
            onChanged.run();
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public boolean canPlaceFluid(int index, FluidStack stack)
    {
        return handler != null && handler.isFluidValid(index, stack);
    }

    @Override
    public boolean canTakeFluid(int index, Player player)
    {
        return true;
    }
}
