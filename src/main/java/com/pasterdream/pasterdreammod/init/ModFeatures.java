package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkHugeMushroomFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 自定义 Feature 类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModFeatures.java}。
 * 具体实现类放 {@code worldgen/feature/}。
 */
public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<HugeMushroomFeatureConfiguration>> PINK_HUGE_MUSHROOM =
            FEATURES.register("pink_huge_mushroom", () -> new PinkHugeMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

    private ModFeatures() {
    }
}
