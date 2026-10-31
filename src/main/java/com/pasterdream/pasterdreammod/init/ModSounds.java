package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 音效事件注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModSounds.java}。
 * {@code sounds.json} 与 {@code .ogg} 手动维护，字幕键需在 {@code sounds.json} 的 {@code subtitle} 字段显式声明。
 */
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SWORD_SLASH =
            SOUND_EVENTS.register("sword_slash", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_slash")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SKILL_HIT =
            SOUND_EVENTS.register("skill_hit", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill_hit")));
    public static final DeferredHolder<SoundEvent, SoundEvent> COIN_DROP =
            SOUND_EVENTS.register("coin_drop", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_drop")));
    public static final DeferredHolder<SoundEvent, SoundEvent> COIN_EXCHANGE =
            SOUND_EVENTS.register("coin_exchange", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_exchange")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private ModSounds() {
    }
}
