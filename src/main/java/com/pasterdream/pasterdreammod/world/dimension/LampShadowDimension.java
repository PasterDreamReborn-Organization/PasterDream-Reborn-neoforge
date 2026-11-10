package com.pasterdream.pasterdreammod.world.dimension;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * 灯影之下维度常量。只在此处集中定义常驻键，Mixin 可通过该常量读取维度，
 * 避免在 Minecraft 初始化阶段创建 Registries 以外依赖。
 */
public final class LampShadowDimension {
    private LampShadowDimension() {}

    public static final ResourceKey<Level> LAMP_SHADOW_WORLD = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"));
}
