package com.pasterdream.pasterdreammod.world.item.storagebag;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

/**
 * 便携储物袋的 IItemHandler 能力，直接读写物品 {@code DataComponents.CONTAINER}。
 * 与 {@link StorageBagMenu} 使用同一套存储方式，方便工具等外部系统访问。
 */
public class StorageBagInventoryCapability extends ItemStackHandler {

    private final ItemStack bagStack;
    private boolean suppressSave = true;

    public StorageBagInventoryCapability(ItemStack bagStack, int slotCount) {
        super(slotCount);
        this.bagStack = bagStack;
        load();
        suppressSave = false;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if (!suppressSave) {
            save();
        }
    }

    public void flush() {
        save();
    }

    private void load() {
        List<ItemStack> items = BagItemData.load(bagStack, getSlots());
        for (int i = 0; i < getSlots(); i++) {
            setStackInSlot(i, items.get(i));
        }
    }

    private void save() {
        List<ItemStack> items = new java.util.ArrayList<>(getSlots());
        for (int i = 0; i < getSlots(); i++) {
            items.add(getStackInSlot(i));
        }
        BagItemData.save(bagStack, items);
    }
}
