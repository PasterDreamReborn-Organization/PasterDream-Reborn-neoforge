package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.fluid.AppleJuiceFluid;
import com.pasterdream.pasterdreammod.world.fluid.DyedreamFlowerTeaFluid;
import com.pasterdream.pasterdreammod.world.fluid.DyedreamJuiceFluid;
import com.pasterdream.pasterdreammod.world.fluid.GuidingDrugFluid;
import com.pasterdream.pasterdreammod.world.fluid.HoneyJuiceFluid;
import com.pasterdream.pasterdreammod.world.fluid.InkFluid;
import com.pasterdream.pasterdreammod.world.fluid.MeltDreamLiquidFluid;
import com.pasterdream.pasterdreammod.world.fluid.PotionFluid;
import com.pasterdream.pasterdreammod.world.fluid.ShadowLiquidFluid;
import com.pasterdream.pasterdreammod.world.fluid.UncookedDyedreamFlowerTeaFluid;
import com.pasterdream.pasterdreammod.world.fluid.WatermelonJuiceFluid;
import com.pasterdream.pasterdreammod.world.fluid.WindPlantExtractFluid;
import com.pasterdream.pasterdreammod.world.fluid.YeastFluid;
import com.pasterdream.pasterdreammod.world.fluid.RageElixirFluid;
import com.pasterdream.pasterdreammod.world.fluidtype.GuidingDrugFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.AppleJuiceFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.DyedreamFlowerTeaFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.DyedreamJuiceFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.HoneyJuiceFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.InkFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.MeltDreamLiquidFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.PotionFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.ShadowLiquidFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.UncookedDyedreamFlowerTeaFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.WatermelonJuiceFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.WindPlantExtractFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.YeastFluidType;
import com.pasterdream.pasterdreammod.world.fluidtype.RageElixirFluidType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
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

    public static final DeferredHolder<FluidType, FluidType> MELT_DREAM_LIQUID_TYPE = FLUID_TYPES.register("melt_dream_liquid", () -> MeltDreamLiquidFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> SHADOW_LIQUID_TYPE = FLUID_TYPES.register("shadow_liquid", () -> ShadowLiquidFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> APPLE_JUICE_TYPE = FLUID_TYPES.register("apple_juice", () -> AppleJuiceFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> DYEDREAM_FLOWER_TEA_TYPE = FLUID_TYPES.register("dyedream_flower_tea", () -> DyedreamFlowerTeaFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> DYEDREAM_JUICE_TYPE = FLUID_TYPES.register("dyedream_juice", () -> DyedreamJuiceFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> HONEY_JUICE_TYPE = FLUID_TYPES.register("honey_juice", () -> HoneyJuiceFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> UNCOOKED_DYEDREAM_FLOWER_TEA_TYPE = FLUID_TYPES.register("uncooked_dyedream_flower_tea", () -> UncookedDyedreamFlowerTeaFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> WATERMELON_JUICE_TYPE = FLUID_TYPES.register("watermelon_juice", () -> WatermelonJuiceFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> GUIDING_DRUG_TYPE = FLUID_TYPES.register("guiding_drug", () -> GuidingDrugFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> WIND_PLANT_EXTRACT_TYPE = FLUID_TYPES.register("wind_plant_extract", () -> WindPlantExtractFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> YEAST_TYPE = FLUID_TYPES.register("yeast", () -> YeastFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> INK_TYPE = FLUID_TYPES.register("ink", () -> InkFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> RAGE_ELIXIR_TYPE = FLUID_TYPES.register("rage_elixir", () -> RageElixirFluidType.TYPE);
    public static final DeferredHolder<FluidType, FluidType> POTION_TYPE = FLUID_TYPES.register("potion", () -> PotionFluidType.TYPE);

    public static final DeferredHolder<Fluid, Fluid> FLOWING_MELT_DREAM_LIQUID = FLUIDS.register("flowing_melt_dream_liquid", MeltDreamLiquidFluid.Flowing::new);
    public static final DeferredHolder<Fluid, Fluid> MELT_DREAM_LIQUID = FLUIDS.register("melt_dream_liquid", MeltDreamLiquidFluid.Source::new);
    public static final DeferredHolder<Fluid, Fluid> FLOWING_SHADOW_LIQUID = FLUIDS.register("flowing_shadow_liquid", ShadowLiquidFluid.Flowing::new);
    public static final DeferredHolder<Fluid, Fluid> SHADOW_LIQUID = FLUIDS.register("shadow_liquid", ShadowLiquidFluid.Source::new);
    public static final DeferredHolder<Fluid, Fluid> APPLE_JUICE = FLUIDS.register("apple_juice", AppleJuiceFluid::new);
    public static final DeferredHolder<Fluid, Fluid> DYEDREAM_FLOWER_TEA = FLUIDS.register("dyedream_flower_tea", DyedreamFlowerTeaFluid::new);
    public static final DeferredHolder<Fluid, Fluid> DYEDREAM_JUICE = FLUIDS.register("dyedream_juice", DyedreamJuiceFluid::new);
    public static final DeferredHolder<Fluid, Fluid> HONEY_JUICE = FLUIDS.register("honey_juice", HoneyJuiceFluid::new);
    public static final DeferredHolder<Fluid, Fluid> UNCOOKED_DYEDREAM_FLOWER_TEA = FLUIDS.register("uncooked_dyedream_flower_tea", UncookedDyedreamFlowerTeaFluid::new);
    public static final DeferredHolder<Fluid, Fluid> WATERMELON_JUICE = FLUIDS.register("watermelon_juice", WatermelonJuiceFluid::new);
    public static final DeferredHolder<Fluid, Fluid> GUIDING_DRUG = FLUIDS.register("guiding_drug", GuidingDrugFluid::new);
    public static final DeferredHolder<Fluid, Fluid> WIND_PLANT_EXTRACT = FLUIDS.register("wind_plant_extract", WindPlantExtractFluid::new);
    public static final DeferredHolder<Fluid, Fluid> YEAST = FLUIDS.register("yeast", YeastFluid::new);
    public static final DeferredHolder<Fluid, Fluid> INK = FLUIDS.register("ink", InkFluid::new);
    public static final DeferredHolder<Fluid, Fluid> RAGE_ELIXIR = FLUIDS.register("rage_elixir", RageElixirFluid::new);
    public static final DeferredHolder<Fluid, Fluid> POTION = FLUIDS.register("potion", PotionFluid::new);

    public static void register(IEventBus eventBus) {
        NeoForgeMod.enableMilkFluid(); //启用NeoForge的牛奶

        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    private ModFluids() {
    }
}
