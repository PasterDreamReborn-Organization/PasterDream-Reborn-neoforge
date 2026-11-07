package com.pasterdream.pasterdreammod.helper.drinkandfoodproperties;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FluidDrinkPropertiesRegistry
{
    private static final Map<ResourceLocation, GenericFluidDrinkProperties> PROPERTIES = new ConcurrentHashMap<>();

    public static void register(Fluid fluid, GenericFluidDrinkProperties properties)
    {
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(fluid);
        if (id != null)
        {
            PROPERTIES.put(id, properties);
        }
    }

    public static GenericFluidDrinkProperties getProperties(FluidStack fluidStack)
    {
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(fluidStack.getFluid());
        if(id != null)
        {
            return PROPERTIES.get(id);
        }
            else
            {
                return null;
            }
    }
}
