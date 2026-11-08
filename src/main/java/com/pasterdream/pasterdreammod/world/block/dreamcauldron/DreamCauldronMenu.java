package com.pasterdream.pasterdreammod.world.block.dreamcauldron;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class DreamCauldronMenu extends AbstractContainerMenuWithFluidSlot
{
    private final DreamCauldronBlockEntity blockEntity;
    private final IFluidContainer fluidContainer;

    public DreamCauldronMenu(int id, Inventory inventory, DreamCauldronBlockEntity blockEntity)
    {
        super(ModMenus.DREAM_CAULDRON.get(), id);
        this.blockEntity = blockEntity;
        bindFluidViewer(inventory.player);
        fluidContainer = new FluidContainer.GenericFluidContainer(blockEntity.getFluidTanks());
        IItemHandler handler = blockEntity.getItemHandler();

        addFluidSlot(new FluidSlot(fluidContainer, 0, 162, 18));
        addFluidSlot(new FluidSlot(fluidContainer, 1, 9, 45));
        addFluidSlot(new FluidSlot(fluidContainer, 2, 94, 45)
        {
            @Override
            public boolean mayPlace(FluidStack stack)
            {
                return false;
            }
        });

        for (int i = 0; i < 3; i++)
        {
            addSlot(new SlotItemHandler(handler, i, 54 + i * 28, 15));
        }

        addSlot(new SlotItemHandler(handler, 3, 69, 46)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });

        //玩家物品栏
        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(inventory, i, 10 + i * 18, 168));
        }

        for (int i = 0; i < 27; i++)
        {
            addSlot(new Slot(inventory, i + 9, 10 + (i % 9) * 18, 110 + (i / 9) * 18));
        }

    }

    public DreamCauldronBlockEntity getBlockEntity()
    {
        return blockEntity;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().getBlockEntity(blockEntity.getBlockPos()) == blockEntity && player.distanceToSqr(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5) <= 64;
    }

    public IFluidContainer getFluidContainer()
    {
        return fluidContainer;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        //移出到背包
        if (index >= 0 && index <= 3)
        {
            if (!this.moveItemStackTo(stack, 4, 40, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else    //背包移入输入槽
            if (index >= 4 && index <= 39)
            {
                if (!this.moveItemStackTo(stack, 0, 3, false))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
            else
            {
                slot.setChanged();
            }

        if (stack.getCount() == copy.getCount())
        {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copy;
    }
}
