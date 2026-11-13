package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GetAllFluidContainerCapability
{
    public static List<FluidContainerRelation> allFluidContainerRelation = new ArrayList<>();

    public static List<FluidContainerRelation> getAllContainer()
    {
        allFluidContainerRelation.clear();
        for (Item item : BuiltInRegistries.ITEM)
        {
            ItemStack itemStack = new ItemStack(item);
            IFluidHandlerItem iFluidHandlerItem = itemStack.getCapability(Capabilities.FluidHandler.ITEM);
            if (iFluidHandlerItem != null)
            {
                int fluidTankCount = iFluidHandlerItem.getTanks();
                if (fluidTankCount != 0)
                {

                    ItemStack emptyContainer = iFluidHandlerItem.getContainer();
                    FluidStack fullCapacityFluidStack = FluidStack.EMPTY;
                    ItemStack fullFluidContainer = ItemStack.EMPTY;

                    boolean isGenericFluidContainer = true;
                    boolean canNotFillAnyFluid = true;

                    int fluidTankCapacity = iFluidHandlerItem.getTankCapacity(0);
                    FluidStack currentFluidStack = iFluidHandlerItem.getFluidInTank(0);

                    if(currentFluidStack.isEmpty())
                    {
                        List<FluidStack> fluidStackList = new ArrayList<>();
                        List<ItemStack> fullItemStackList = new ArrayList<>();
                        for(Fluid fluid : BuiltInRegistries.FLUID)
                        {
                            if(fluid != Fluids.EMPTY)
                            {
                                fullCapacityFluidStack = new FluidStack(fluid, fluidTankCapacity);
                                final FluidStack fluidStack = fullCapacityFluidStack;
                                if (iFluidHandlerItem.fill(fullCapacityFluidStack, IFluidHandler.FluidAction.SIMULATE) == fluidTankCapacity)
                                {
                                    AtomicReference<ItemStack> AtomicReferenceFullFluidContainer = new AtomicReference<>(itemStack.copy());
                                    IFluidHandlerItem handler = AtomicReferenceFullFluidContainer.get().getCapability(Capabilities.FluidHandler.ITEM);
                                    if (handler != null)
                                    {
                                        handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                                        AtomicReferenceFullFluidContainer.set(handler.getContainer());
                                    }

                                    fullFluidContainer = AtomicReferenceFullFluidContainer.get();

                                    fluidStackList.add(fullCapacityFluidStack);
                                    fullItemStackList.add(fullFluidContainer);
                                    canNotFillAnyFluid = false;
                                }
                                    else
                                    {
                                        isGenericFluidContainer = false;
                                    }
                            }
                        }

                        if(!canNotFillAnyFluid)
                        {
                            if(isGenericFluidContainer)
                            {
                                allFluidContainerRelation.add(new FluidContainerRelation(emptyContainer, new FluidStack(Fluids.WATER, fluidTankCapacity), ItemStack.EMPTY, true));
                            }
                                else
                                {
                                    for(int i = 0; i < fluidStackList.size(); i++)
                                    {
                                        allFluidContainerRelation.add(new FluidContainerRelation(emptyContainer, fluidStackList.get(i), fullItemStackList.get(i), false));
                                    }
                                }
                        }
                    }
                }
            }
        }
        return allFluidContainerRelation;
    }
}
