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

    public static final DeferredHolder<SoundEvent, SoundEvent> WIND_CHIME =
            SOUND_EVENTS.register("wind_chime", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_chime")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SWORD_SLASH =
            SOUND_EVENTS.register("sword_slash", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_slash")));
    public static final DeferredHolder<SoundEvent, SoundEvent> EVASION =
            SOUND_EVENTS.register("evasion", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "evasion")));
    public static final DeferredHolder<SoundEvent, SoundEvent> PLIERS =
            SOUND_EVENTS.register("pliers", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "pliers")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SKILL_HIT =
            SOUND_EVENTS.register("skill_hit", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill_hit")));
    public static final DeferredHolder<SoundEvent, SoundEvent> COIN_DROP =
            SOUND_EVENTS.register("coin_drop", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_drop")));
    public static final DeferredHolder<SoundEvent, SoundEvent> COIN_EXCHANGE =
            SOUND_EVENTS.register("coin_exchange", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "coin_exchange")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_RARE =
            SOUND_EVENTS.register("melt_dream_crystal_chest_open_rare", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_rare")));
    public static final DeferredHolder<SoundEvent, SoundEvent> MELT_DREAM_CRYSTAL_CHEST_OPEN_LEGEND =
            SOUND_EVENTS.register("melt_dream_crystal_chest_open_legend", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_open_legend")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SKILL_HIT_HEAVY =
            SOUND_EVENTS.register("skill_hit_heavy", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "skill_hit_heavy")));
    public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTNING_CHARGE =
            SOUND_EVENTS.register("lightning_charge", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lightning_charge")));
    public static final DeferredHolder<SoundEvent, SoundEvent> FROZEN_SHOCK =
            SOUND_EVENTS.register("frozen_shock", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "frozen_shock")));
    public static final DeferredHolder<SoundEvent, SoundEvent> INFERNO_IMPACT =
            SOUND_EVENTS.register("inferno_impact", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "inferno_impact")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SWORD_AWAKEN =
            SOUND_EVENTS.register("sword_awaken", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_awaken")));

    public static final DeferredHolder<SoundEvent, SoundEvent> DOLL =
            SOUND_EVENTS.register("doll", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "doll")));
    public static final DeferredHolder<SoundEvent, SoundEvent> AWAKE =
            SOUND_EVENTS.register("awake", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "awake")));
    public static final DeferredHolder<SoundEvent, SoundEvent> CLOAK =
            SOUND_EVENTS.register("cloak", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloak")));
    public static final DeferredHolder<SoundEvent, SoundEvent> THERMAL_DAGGER =
            SOUND_EVENTS.register("thermal_dagger", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "thermal_dagger")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SWORD_WAVE =
            SOUND_EVENTS.register("sword_wave", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sword_wave")));
    public static final DeferredHolder<SoundEvent, SoundEvent> FOX_FIRE =
            SOUND_EVENTS.register("fox_fire", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fox_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADOW_TRAP =
            SOUND_EVENTS.register("shadow_trap", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_trap")));

    public static final DeferredHolder<SoundEvent, SoundEvent> LIFE_CRYSTAL =
            SOUND_EVENTS.register("life_crystal", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "life_crystal")));

    public static final DeferredHolder<SoundEvent, SoundEvent> LOSE_MIND =
            SOUND_EVENTS.register("lose_mind", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lose_mind")));

    public static final DeferredHolder<SoundEvent, SoundEvent> TERRORBEAK_ROAR =
            SOUND_EVENTS.register("terrorbeak_roar", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "terrorbeak_roar")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SHADOW_ROAR =
            SOUND_EVENTS.register("shadow_roar", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_roar")));

    public static final DeferredHolder<SoundEvent, SoundEvent> ZIPPER =
            SOUND_EVENTS.register("zipper", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "zipper")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SWEET_DREAM_MUSIC =
            SOUND_EVENTS.register("sweet_dream_music", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "sweet_dream_music")));
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOWFALL_DREAM_MUSIC =
            SOUND_EVENTS.register("snowfall_dream_music", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "snowfall_dream_music")));
    public static final DeferredHolder<SoundEvent, SoundEvent> WIND_JOURNEY =
            SOUND_EVENTS.register("wind_journey", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey")));
    public static final DeferredHolder<SoundEvent, SoundEvent> AARONCOS_MUSIC =
            SOUND_EVENTS.register("aaroncos_music", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "aaroncos_music")));

    public static final DeferredHolder<SoundEvent, SoundEvent> DREAM_ACCUMULATOR =
            SOUND_EVENTS.register("dream_accumulator", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_accumulator")));

    public static final DeferredHolder<SoundEvent, SoundEvent> DYEDREAM_CRACK =
            SOUND_EVENTS.register("dyedream_crack", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_crack")));

    public static final DeferredHolder<SoundEvent, SoundEvent> DING =
            SOUND_EVENTS.register("ding", () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "ding")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private ModSounds() {
    }
}
