package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.neoforged.neoforge.fluids.FluidType;

public class SlumberPalmJuiceFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.SLUMBER_PALM_JUICE_TYPE.get();
    }
}
