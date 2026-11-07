package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;

/**
 * 流体容器关系注册（空容器 ↔ 流体 ↔ 填充容器）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModFluidContainerRelation.java}。
 * 搬运时通过 {@code FluidContainerRegistry.register(空容器, 流体, 量, 填充容器)} 注册。
 */
public final class ModFluidContainerRelation {
    public static void registerFluidContainerRelation() {
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), Fluids.WATER, 125, ModItems.GLASS_JAR_OF_WATER.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), NeoForgeMod.MILK.get(), 125, ModItems.GLASS_JAR_OF_MILK.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.GUIDING_DRUG.get(), 125, ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.WIND_PLANT_EXTRACT.get(), 125, ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.YEAST.get(), 125, ModItems.GLASS_JAR_OF_YEAST.get());
        FluidContainerRegistry.register(ModItems.GLASS_JAR.get(), ModFluids.INK.get(), 125, ModItems.GLASS_JAR_OF_INK.get());
    }

    private ModFluidContainerRelation() {
    }
}
