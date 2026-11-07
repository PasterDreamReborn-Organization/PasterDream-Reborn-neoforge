package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FluidContainerRegistry
{
    private static final Map<Item, ContainerEntry> EMPTY_TO_FILL = new ConcurrentHashMap<>();
    private static final Map<Item, ContainerEntry> FILL_TO_EMPTY = new ConcurrentHashMap<>();
    private static final Map<Item, Map<Fluid, ContainerEntry>> EMPTY_FLUID_TO_ENTRY = new ConcurrentHashMap<>();

    public static void register(Item emptyItem, Fluid fluid, int amount, Item filledItem)
    {
        if (amount <= 0) throw new IllegalArgumentException("流体容器容量必须为正数");
        ContainerEntry entry = new ContainerEntry(new ItemStack(emptyItem), new FluidStack(fluid, amount), new ItemStack(filledItem));
        EMPTY_TO_FILL.put(emptyItem, entry);
        FILL_TO_EMPTY.put(filledItem, entry);
        EMPTY_FLUID_TO_ENTRY.computeIfAbsent(emptyItem, item -> new ConcurrentHashMap<>()).put(fluid, entry);
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyToFill(ItemStack itemStack)
    {
        return EMPTY_TO_FILL.get(itemStack.getItem());
    }

    @Nullable
    public static ContainerEntry getEntryForFillToEmpty(ItemStack itemStack)
    {
        return FILL_TO_EMPTY.get(itemStack.getItem());
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyAndFluid(ItemStack emptyContainerItemStack, FluidStack fluidStack)
    {
        Map<Fluid, ContainerEntry> map = EMPTY_FLUID_TO_ENTRY.get(emptyContainerItemStack.getItem());
        return map != null ? map.get(fluidStack.getFluid()) : null;
    }

    @Nullable
    public static ContainerEntry getAnyEntryForEmpty(Item emptyItem)
    {
        return EMPTY_TO_FILL.get(emptyItem);
    }

    public static class ContainerEntry
    {
        public final ItemStack emptyContainerItemStack;
        public final FluidStack fluidStack;
        public final ItemStack fullContainerItemStack;

        public ContainerEntry(ItemStack emptyContainerItemStack, FluidStack fluidStack, ItemStack fullContainerItemStack)
        {
            this.emptyContainerItemStack = emptyContainerItemStack;
            this.fluidStack = fluidStack;
            this.fullContainerItemStack = fullContainerItemStack;
        }
    }
}
