package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.neoforged.neoforge.fluids.FluidType;

/**
 * 通用「药水」流体 —— 单一流体实例，具体药水效果由 FluidStack 的 POTION_CONTENTS 组件承载。
 */
public class PotionFluid extends PasterDreamBaseFluid
{
    @Override
    public FluidType getFluidType()
    {
        return ModFluids.POTION_TYPE.get();
    }
}
