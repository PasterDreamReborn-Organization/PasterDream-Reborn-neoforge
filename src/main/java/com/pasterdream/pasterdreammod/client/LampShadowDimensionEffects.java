package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

/**
 * 灯影之下维度视觉效果（客户端）。
 *
 * <p>来源把 {@code DimensionSpecialEffects} 注册写在维度逻辑类中；目标仓库按客户端/服务端
 * 边界拆分，避免专用服务器加载客户端类。雾色、无云、恒定环境光与来源一致；纯黑天空和
 * 黑色降雨由 {@code LevelRendererMixin}/{@code FogRendererMixin} 补充。
 */
public final class LampShadowDimensionEffects {
    private LampShadowDimensionEffects() {
    }

    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                Float.NaN,                              // cloudLevel: 无云
                true,                                   // hasGround
                DimensionSpecialEffects.SkyType.NONE,   // 无天空
                false,                                  // forceBrightLightmap
                true                                    // constantAmbientLight
        ) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return new Vec3(0.5215686275, 0.5215686275, 0.5215686275);
            }

            @Override
            public boolean isFoggyAt(int x, int y) {
                return false;
            }
        };
        event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lamp_shadow_world"), customEffect);
    }
}
