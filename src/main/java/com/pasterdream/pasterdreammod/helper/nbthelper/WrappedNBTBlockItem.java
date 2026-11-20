package com.pasterdream.pasterdreammod.helper.nbthelper;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class WrappedNBTBlockItem
{
    public static void wrapper(ItemStack stack, CompoundTag originalNBT)
    {
        stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(originalNBT));
        stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal("(+NBT)"))));
    }
}
