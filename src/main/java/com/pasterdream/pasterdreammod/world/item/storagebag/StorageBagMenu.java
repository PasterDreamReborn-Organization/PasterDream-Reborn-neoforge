package com.pasterdream.pasterdreammod.world.item.storagebag;

import com.pasterdream.pasterdreammod.init.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class StorageBagMenu extends AbstractContainerMenu {

    private final ItemStack bagStack;
    private final InteractionHand hand;
    private final int lockedSlotIndex;
    private final StorageBagInventoryCapability bagInventory;

    // 从服务端构建（物品直接传入）
    public StorageBagMenu(int id, Inventory playerInventory, ItemStack bagStack, InteractionHand hand) {
        super(ModMenus.STORAGE_BAG.get(), id);
        this.bagStack = bagStack;
        this.hand = hand;
        this.bagInventory = createInventory(bagStack);

        if (hand == InteractionHand.MAIN_HAND) {
            lockedSlotIndex = StorageBagItem.SLOT_COUNT + 27 + playerInventory.selected;
        } else {
            lockedSlotIndex = -1;
        }

        // 袋子物品栏 3×3
        int bagTexLeft = 45;
        int bagStartX = bagTexLeft + (86 - 3 * 18) / 2 - 1;
        int bagStartY = 17 + 7;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                addSlot(new SlotItemHandler(bagInventory, row * 3 + col,
                        bagStartX + col * 18, bagStartY + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !isStorageBag(stack);
                    }
                });
            }
        }

        // 玩家背包 3×9
        int invStartX = 5;
        int invStartY = 82 + 5;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));
            }
        }

        // 快捷栏 1×9
        int hotbarY = invStartY + 3 * 18 + 4;
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, invStartX + col * 18, hotbarY));
        }
    }

    // 从客户端构建（openMenu 数据包）
    public StorageBagMenu(int id, Inventory playerInventory, RegistryFriendlyByteBuf data) {
        super(ModMenus.STORAGE_BAG.get(), id);
        byte handId = data.readByte();
        this.hand = handId == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        this.bagStack = playerInventory.player.getItemInHand(this.hand);
        this.bagInventory = createInventory(this.bagStack);

        if (this.hand == InteractionHand.MAIN_HAND) {
            lockedSlotIndex = StorageBagItem.SLOT_COUNT + 27 + playerInventory.selected;
        } else {
            lockedSlotIndex = -1;
        }

        // 袋子物品栏 3×3
        int bagTexLeft = 45;
        int bagStartX = bagTexLeft + (86 - 3 * 18) / 2 + 1;
        int bagStartY = 17 - 7;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                addSlot(new SlotItemHandler(bagInventory, row * 3 + col,
                        bagStartX + col * 18, bagStartY + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return !isStorageBag(stack);
                    }
                });
            }
        }

        // 玩家背包 3×9
        int invStartX = 5;
        int invStartY = 82 + 5;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        invStartX + col * 18, invStartY + row * 18));
            }
        }

        // 快捷栏 1×9
        int hotbarY = invStartY + 3 * 18 + 4;
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, invStartX + col * 18, hotbarY));
        }
    }

    private static StorageBagInventoryCapability createInventory(ItemStack stack) {
        return new StorageBagInventoryCapability(stack, StorageBagItem.SLOT_COUNT);
    }

    private void save() {
        bagInventory.flush();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        int bagSlots = StorageBagItem.SLOT_COUNT;

        if (index < bagSlots) {
            if (!moveItemStackTo(stack, bagSlots, bagSlots + 36, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, bagSlots, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand).getItem() == bagStack.getItem();
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType clickType, Player player) {
        if (slotIndex == lockedSlotIndex && clickType != ClickType.QUICK_MOVE) return;
        if (clickType == ClickType.QUICK_MOVE && slotIndex >= 0) {
            Slot slot = this.slots.get(slotIndex);
            if (slot.hasItem() && slot.getItem().getItem() instanceof StorageBagItem) return;
        }
        super.clicked(slotIndex, button, clickType, player);
    }

    @Override
    public void removed(Player player) {
        save();
        super.removed(player);
    }

    @Override
    public void broadcastChanges() {
        save();
        super.broadcastChanges();
    }

    private static boolean isStorageBag(ItemStack stack) {
        return stack.getItem() instanceof StorageBagItem || stack.getItem() instanceof LargeStorageBagItem;
    }
}
