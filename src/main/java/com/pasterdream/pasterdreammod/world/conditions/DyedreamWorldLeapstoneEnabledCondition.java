package com.pasterdream.pasterdreammod.world.conditions;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 配方条件：仅当染梦世界跃迁石总开关 {@code dyedreamWorldLeapstoneEnabled} 打开时，
 * 对应的合成配方与进度才会被加载。对齐 Forge 1.20.1 的
 * {@code DyedreamWorldLeapstoneEnabledCondition}。
 */
public final class DyedreamWorldLeapstoneEnabledCondition implements ICondition {
    public static final DyedreamWorldLeapstoneEnabledCondition INSTANCE = new DyedreamWorldLeapstoneEnabledCondition();
    public static final MapCodec<DyedreamWorldLeapstoneEnabledCondition> CODEC = MapCodec.unit(INSTANCE);

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, PasterDreamMod.MOD_ID);
    public static final DeferredHolder<MapCodec<? extends ICondition>,
            MapCodec<DyedreamWorldLeapstoneEnabledCondition>> REGISTERED =
            CONDITION_CODECS.register("dyedream_world_leapstone_enabled", () -> CODEC);

    private DyedreamWorldLeapstoneEnabledCondition() {
    }

    @Override
    public boolean test(IContext context) {
        return Config.DYEDREAM_WORLD_LEAPSTONE_ENABLED.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    public static void register(IEventBus modEventBus) {
        CONDITION_CODECS.register(modEventBus);
    }
}
