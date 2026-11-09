package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

/**
 * DataComponents 注册中心。
 *
 * <p>NeoForge 1.21 起物品数据由 NBT 改为 DataComponents：
 * 强类型状态（等级 / 类型 / 内容 key 等）在此定义 {@code DataComponentType}；
 * 历史/自由 NBT 用原版 {@code DataComponents.CUSTOM_DATA} 过渡。
 */
public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<FluidStack>>> MORTAR_FLUIDS =
            DATA_COMPONENTS.register("mortar_fluids", () -> DataComponentType.<List<FluidStack>>builder()
                    .persistent(FluidStack.OPTIONAL_CODEC.listOf())
                    .networkSynchronized(FluidStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()))
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SimpleFluidContent>> ELIXIR_BOTTLE_FLUID =
            DATA_COMPONENTS.register("elixir_bottle_fluid", () -> DataComponentType.<SimpleFluidContent>builder()
                    .persistent(SimpleFluidContent.CODEC)
                    .networkSynchronized(SimpleFluidContent.STREAM_CODEC)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> DEEP_TREASURE_SUPER =
            DATA_COMPONENTS.register("deep_treasure_super", () -> DataComponentType.<Boolean>builder()
                    .persistent(com.mojang.serialization.Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
                    .build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DREAM_NOTE_CONTENT =
            DATA_COMPONENTS.register("dream_note_content", () -> DataComponentType.<String>builder()
                    .persistent(com.mojang.serialization.Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                    .build());

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

    private ModDataComponents() {
    }
}
