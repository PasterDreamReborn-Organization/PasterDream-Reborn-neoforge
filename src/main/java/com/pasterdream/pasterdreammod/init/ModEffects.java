package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 药水效果注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModEffects.java}。
 */
public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, PasterDreamMod.MOD_ID);

    // TODO: 在此注册药水效果

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

    private ModEffects() {
    }
}
