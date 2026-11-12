package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

/**
 * 风之旅途维度视觉效果（客户端）。
 *
 * <p>来源类将 {@code DimensionSpecialEffects} 注册写在维度逻辑类中；目标仓库按客户端/服务端
 * 边界拆分，避免专用服务器加载客户端类。效果参数与来源一致。
 */
public final class WindJourneyDimensionEffects {
    private WindJourneyDimensionEffects() {
    }

    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        DimensionSpecialEffects customEffect = new DimensionSpecialEffects(
                Float.NaN,                              // cloudLevel: 无云
                true,                                   // hasGround
                DimensionSpecialEffects.SkyType.NONE,   // 无天空（无太阳/月亮）
                false,                                  // forceBrightLightmap
                false                                   // constantAmbientLight
        ) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
                return new Vec3(0.5333333333, 0.9568627451, 0.9215686275);
            }

            @Override
            public boolean isFoggyAt(int x, int y) {
                return false;
            }
        };
        event.register(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"), customEffect);
    }
}
