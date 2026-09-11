package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 流体与流体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModFluids.java}。
 * 注意：流体类型注册表在 NeoForge 为 {@link NeoForgeRegistries#FLUID_TYPES}，不再是 {@code ForgeRegistries.Keys.FLUID_TYPES}。
 */
public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, PasterDreamMod.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, PasterDreamMod.MOD_ID);

    // TODO: 在此注册流体类型与流体

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    private ModFluids() {
    }
}
