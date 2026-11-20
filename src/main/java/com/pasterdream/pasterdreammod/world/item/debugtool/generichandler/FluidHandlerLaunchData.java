package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public record FluidHandlerLaunchData(IFluidHandler handler, Runnable onChanged){}
