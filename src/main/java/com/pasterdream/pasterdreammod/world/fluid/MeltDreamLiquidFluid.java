package com.pasterdream.pasterdreammod.world.fluid;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public abstract class MeltDreamLiquidFluid extends BaseFlowingFluid
{
    public static final BaseFlowingFluid.Properties PROPERTIES = (new BaseFlowingFluid.Properties(ModFluids.MELT_DREAM_LIQUID_TYPE, ModFluids.MELT_DREAM_LIQUID, ModFluids.FLOWING_MELT_DREAM_LIQUID)).explosionResistance(100.0F).tickRate(3).bucket(ModItems.MELT_DREAM_LIQUID_BUCKET).block(() -> (LiquidBlock) ModBlocks.MELTDREAM_LIQUID.get());

    private MeltDreamLiquidFluid()
    {
        super(PROPERTIES);
    }

    public static class Source extends MeltDreamLiquidFluid
    {
        public int getAmount(FluidState state)
        {
            return 8;
        }

        public boolean isSource(FluidState state)
        {
            return true;
        }
    }

    public static class Flowing extends MeltDreamLiquidFluid
    {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder)
        {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getAmount(FluidState state)
        {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state)
        {
            return false;
        }
    }

    @Override
    public FluidType getFluidType()
    {
        return ModFluids.MELT_DREAM_LIQUID_TYPE.get();
    }
}
