package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * 风之旅途维度（wind_journey_world）引用与客户端天空/雾特效。
 * 群系背景音乐由 {@code ModBiomes} 中 {@code backgroundMusic()} 配合
 * {@code SituationalMusicMixin} 和 {@code MusicManagerMixin} 统一管理，
 * 此处不再额外播放音效，避免与 MusicManager 叠加。
 */
public final class WindJourneyDimension {
    private WindJourneyDimension() {}

    public static final ResourceKey<Level> WIND_JOURNEY_WORLD = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));
}
