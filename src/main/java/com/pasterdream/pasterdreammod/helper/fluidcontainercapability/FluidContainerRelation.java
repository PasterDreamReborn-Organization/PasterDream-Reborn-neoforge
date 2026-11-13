package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public record FluidContainerRelation(ItemStack emptyContainer, FluidStack fluidStack, ItemStack fullContainer, Boolean isGenericFluidContainer){}
