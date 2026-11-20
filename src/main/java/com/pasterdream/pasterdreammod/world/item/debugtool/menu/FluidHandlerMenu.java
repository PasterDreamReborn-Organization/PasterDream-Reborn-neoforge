package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.IFluidContainer;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerContainerWrapper;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.PlayerEditorSlotData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import java.util.function.BooleanSupplier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FluidHandlerMenu extends AbstractContainerMenuWithFluidSlot
{
    private Container editorContainer;
    private IFluidHandler fluidHandler;
    private final IFluidContainer container;
    private final int slotCount;
    private final Player player;
    private final Runnable onChanged;
    private final MenuProvider parentMenu;
    private final BooleanSupplier targetValid;

    public FluidHandlerMenu(int id, Inventory playerInventory, IFluidHandler fluidHandler, Runnable onChanged, MenuProvider parentMenu, BooleanSupplier targetValid)
    {
        super(ModMenus.FLUID_HANDLER.get(), id);
        this.fluidHandler = fluidHandler;
        this.slotCount = fluidHandler.getTanks();
        player = playerInventory.player;
        this.onChanged = onChanged;
        this.parentMenu = parentMenu;
        this.targetValid = targetValid;
        this.container = new FluidHandlerContainerWrapper(fluidHandler, onChanged);
        bindFluidViewer(player);
        buildSlots(playerInventory);
    }

    public FluidHandlerMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        super(ModMenus.FLUID_HANDLER.get(), id);
        int[] tankCapacities = buffer.readVarIntArray(4096);
        this.slotCount = tankCapacities.length;
        fluidHandler = null;
        player = playerInventory.player;
        onChanged = null;
        parentMenu = null;
        targetValid = () -> true;
        this.container = new FluidHandlerContainerWrapper(tankCapacities);
        buildSlots(playerInventory);
    }

    private void buildSlots(Inventory playerInventory)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 91 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 149));
        }

        editorContainer = new SimpleContainer(1)
        {
            @Override
            public void setChanged()
            {
                super.setChanged();
                if (!player.level().isClientSide)
                {
                    PlayerEditorSlotData.set(player, getItem(0), 2);
                }
            }
        };
        if (!player.level().isClientSide)
        {
            ItemStack stored = PlayerEditorSlotData.get(player, 2);
            if (!stored.isEmpty())
            {
                editorContainer.setItem(0, stored.copy());
            }
        }

        addSlot(new Slot(editorContainer, 0, -56, 77)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                super.set(itemStack);
            }
        });

        for (int i = 0; i < slotCount; i++)
        {
            int x = 7 + (i % 8) * 18;
            int y = 7 + ((i % 32) / 8) * 18;

            addFluidSlot(new FluidSlot(container, i, x, y, onChanged)
            {
                @Override
                public boolean mayPickup(Player player)
                {
                    return false;
                }

                @Override
                public boolean mayPlace(FluidStack fluidStack)
                {
                    return false;
                }
            });
        }
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

        if ((index == 36))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 36, 37, false)))
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
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return targetValid.getAsBoolean();
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        if (player instanceof ServerPlayer serverPlayer && parentMenu != null)
        {
            serverPlayer.server.tell(new TickTask(serverPlayer.server.getTickCount(), () ->
            {
                if (!serverPlayer.hasDisconnected() && serverPlayer.isAlive()
                        && serverPlayer.containerMenu == serverPlayer.inventoryMenu && stillValid(serverPlayer))
                {
                    serverPlayer.openMenu(parentMenu);
                }
            }));
        }
    }

    public IFluidHandler getFluidHandler()
    {
        return fluidHandler;
    }

    public Runnable getOnChanged()
    {
        return onChanged;
    }
}
