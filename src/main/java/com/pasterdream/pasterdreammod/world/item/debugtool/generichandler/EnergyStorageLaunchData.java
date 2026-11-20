package com.pasterdream.pasterdreammod.world.item.debugtool.generichandler;

import net.neoforged.neoforge.energy.IEnergyStorage;

public record EnergyStorageLaunchData(IEnergyStorage handler, Runnable onChanged){}
