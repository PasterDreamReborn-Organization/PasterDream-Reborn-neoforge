package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 音效事件注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModSounds.java}。
 * {@code sounds.json} 与 {@code .ogg} 手动维护，字幕键需在 {@code sounds.json} 的 {@code subtitle} 字段显式声明。
 */
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, PasterDreamMod.MOD_ID);

    // TODO: 在此注册音效（SoundEvent.createVariableRangeEvent(...)）

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private ModSounds() {
    }
}
