package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class DyedreamDeskMenu extends AbstractContainerMenu
{
    private final DyedreamDeskBlockEntity blockEntity;

    public DyedreamDeskMenu(int id, Inventory inventory, DyedreamDeskBlockEntity blockEntity)
    {
        super(ModMenus.DYEDREAM_DESK.get(), id);
        this.blockEntity = blockEntity;
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 77, 5));
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(inventory, i, 5 + i * 18, 123));
        for (int i = 0; i < 27; i++)
            addSlot(new Slot(inventory, i + 9, 5 + (i % 9) * 18, 65 + (i / 9) * 18));
    }

    @Override
    public boolean stillValid(Player player)
    {
        BlockPos pos = blockEntity.getBlockPos();
        return player.level().getBlockEntity(pos) == blockEntity && player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index == 0)
        {
            if (!moveItemStackTo(stack, 1, 37, false)) return ItemStack.EMPTY;
        }
        else if (index < 37)
        {
            if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return copy;
    }
}
