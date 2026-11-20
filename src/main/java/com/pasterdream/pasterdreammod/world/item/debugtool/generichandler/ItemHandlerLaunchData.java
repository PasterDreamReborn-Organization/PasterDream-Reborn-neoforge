package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.neoforged.neoforge.items.IItemHandler;

public record ItemHandlerLaunchData(IItemHandler handler, Runnable onChanged){}
