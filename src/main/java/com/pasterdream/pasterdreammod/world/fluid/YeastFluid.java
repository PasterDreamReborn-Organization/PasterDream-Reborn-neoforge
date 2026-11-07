package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.neoforged.neoforge.fluids.FluidType;

public class YeastFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.YEAST_TYPE.get();
    }
}
