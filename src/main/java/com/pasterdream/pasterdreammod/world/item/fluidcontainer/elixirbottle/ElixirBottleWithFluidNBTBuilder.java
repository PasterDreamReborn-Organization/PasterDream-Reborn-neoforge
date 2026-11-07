package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;

public class ElixirBottleWithFluidNBTBuilder
{
    public static ItemStack builder(FluidStack fluidStack)
    {
        ItemStack elixirBottle = new ItemStack(ModItems.ELIXIR_BOTTLE.get());
        elixirBottle.set(ModDataComponents.ELIXIR_BOTTLE_FLUID, SimpleFluidContent.copyOf(fluidStack));
        return elixirBottle;
    }
}
