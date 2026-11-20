package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.fluids.FluidStack;

public class PlayerFluidSlotContainer implements IFluidContainer
{
    private static final String KEY = "pasterdream_debug_tool_fluids";

    private final Player player;

    public PlayerFluidSlotContainer(Player player)
    {
        this.player = player;
        ensureInit();
    }

    private void ensureInit()
    {
        CompoundTag data = player.getPersistentData();
        if (data.getList(KEY, Tag.TAG_COMPOUND).size() < getFluidContainerSize())
        {
            ListTag listTag = data.getList(KEY, Tag.TAG_COMPOUND);
            while (listTag.size() < getFluidContainerSize())
            {
                listTag.add(new CompoundTag());
            }
            data.put(KEY, listTag);
        }
    }

    private ListTag getRoot()
    {
        return player.getPersistentData().getList(KEY, Tag.TAG_COMPOUND);
    }

    @Override
    public int getFluidContainerSize()
    {
        return 8;
    }

    @Override
    public FluidStack getFluid(int index)
    {
        return FluidStack.parseOptional(player.registryAccess(), getRoot().getCompound(index));
    }

    @Override
    public void setFluid(int index, FluidStack fluidStack)
    {
        ListTag listTag = getRoot();
        if(index < listTag.size())
        {
            listTag.set(index, fluidStack.saveOptional(player.registryAccess()));
        }
    }

    @Override
    public int getMaxFluidCapacity(int index)
    {
        return 2147483647;
    }

    @Override
    public void setChanged()
    {

    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }
}
