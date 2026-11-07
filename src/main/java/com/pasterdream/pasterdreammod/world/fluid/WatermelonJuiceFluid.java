package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.neoforged.neoforge.fluids.FluidType;

public class WatermelonJuiceFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.WATERMELON_JUICE_TYPE.get();
    }
}
