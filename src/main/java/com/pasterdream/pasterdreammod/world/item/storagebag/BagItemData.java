package com.pasterdream.pasterdreammod.world.item.storagebag;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.List;

/**
 * 便携储物袋的物品栏数据读写。
 *
 * <p>1.21 物品数据使用 DataComponents，储物袋内容存放在 {@link DataComponents#CONTAINER}，
 * 槽位通过列表下标保持，空槽以 {@link ItemStack#EMPTY} 表示。
 */
public final class BagItemData {

    private BagItemData() {
    }

    public static List<ItemStack> load(ItemStack bagStack, int slotCount) {
        ItemContainerContents contents = bagStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        NonNullList<ItemStack> items = NonNullList.withSize(slotCount, ItemStack.EMPTY);
        contents.copyInto(items);
        return items;
    }

    public static void save(ItemStack bagStack, List<ItemStack> items) {
        ItemContainerContents contents = ItemContainerContents.fromItems(items);
        if (contents.equals(ItemContainerContents.EMPTY)) {
            bagStack.remove(DataComponents.CONTAINER);
        } else {
            bagStack.set(DataComponents.CONTAINER, contents);
        }
    }
}
