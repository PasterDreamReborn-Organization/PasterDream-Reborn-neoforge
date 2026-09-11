package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 药水注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModPotions.java}。
 */
public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, PasterDreamMod.MOD_ID);

    // TODO: 在此注册药水

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

    private ModPotions() {
    }
}
