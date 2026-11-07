package com.pasterdream.pasterdreammod.world.item.mortar;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

/** 1.21 将来源的 InputItems/OutputItems NBT 槽位迁移到 CONTAINER 组件。 */
public class MortarDataHandler
{
    private final ItemStack thisMortarItem;
    private static final int ITEM_SLOTS = 12;
    private static final int FLUID_SLOTS = 6;

    public MortarDataHandler(ItemStack thisMortarItem)
    {
        this.thisMortarItem = thisMortarItem;
    }

    public List<ItemStack> getAllItems()
    {
        ItemContainerContents contents = thisMortarItem.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        NonNullList<ItemStack> items = NonNullList.withSize(ITEM_SLOTS, ItemStack.EMPTY);
        contents.copyInto(items);
        return items;
    }

    public void saveItems(List<ItemStack> items)
    {
        ItemContainerContents contents = ItemContainerContents.fromItems(items);
        if (contents.equals(ItemContainerContents.EMPTY))
        {
            thisMortarItem.remove(DataComponents.CONTAINER);
        }
            else
            {
                thisMortarItem.set(DataComponents.CONTAINER, contents);
            }
    }

    public List<FluidStack> getAllFluids()
    {
        List<FluidStack> saved = thisMortarItem.get(ModDataComponents.MORTAR_FLUIDS.get());
        List<FluidStack> fluids = new java.util.ArrayList<>(FLUID_SLOTS);
        for (int i = 0; i < FLUID_SLOTS; i++)
        {
            fluids.add(saved != null && i < saved.size() ? saved.get(i).copy() : FluidStack.EMPTY);
        }
        return fluids;
    }

    public void saveFluids(List<FluidStack> fluids)
    {
        if (fluids.stream().allMatch(FluidStack::isEmpty))
        {
            thisMortarItem.remove(ModDataComponents.MORTAR_FLUIDS.get());
        }
            else
            {
                thisMortarItem.set(ModDataComponents.MORTAR_FLUIDS.get(), fluids.stream().map(FluidStack::copy).toList());
            }
    }

    public ItemStack getThisMortarItem()
    {
        return thisMortarItem;
    }
}
