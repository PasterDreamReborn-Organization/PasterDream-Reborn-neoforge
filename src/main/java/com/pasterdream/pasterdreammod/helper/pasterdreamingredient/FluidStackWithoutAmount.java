package com.pasterdream.pasterdreammod.helper.pasterdreamingredient;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FluidStackWithoutAmount
{
    @Nullable private final Fluid fluid;
    private final DataComponentPatch components;

    public FluidStackWithoutAmount(@Nullable Fluid fluid, DataComponentPatch components)
    {
        this.fluid = fluid;
        this.components = components;
    }

    public Fluid getFluid() { return fluid; }
    public DataComponentPatch getComponents() { return components; }

    public static List<FluidStackWithoutAmount> fromListFluidStack(List<FluidStack> fluidStackList)
    {
        List<FluidStackWithoutAmount> result = new ArrayList<>();
        for (FluidStack stack : fluidStackList)
        {
            if (stack.isEmpty()) continue;
            FluidStackWithoutAmount converted = new FluidStackWithoutAmount(stack.getFluid(), stack.getComponentsPatch());
            if (result.stream().noneMatch(existing -> isSame(existing, converted))) result.add(converted);
        }
        return result;
    }

    public static boolean isSame(FluidStackWithoutAmount first, FluidStackWithoutAmount second)
    {
        return first.fluid == second.fluid && first.components.equals(second.components);
    }
}
