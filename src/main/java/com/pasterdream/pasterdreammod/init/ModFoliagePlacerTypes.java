package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.feature.foliageplacers.DreamFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 树叶放置器类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModFoliagePlacerTypes.java}。
 */
public class ModFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<DreamFoliagePlacer>> DREAM_FOLIAGE_PLACER =
            FOLIAGE_PLACER_TYPES.register("dream_foliage_placer", () -> new FoliagePlacerType<>(DreamFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACER_TYPES.register(eventBus);
    }

    private ModFoliagePlacerTypes() {
    }
}
