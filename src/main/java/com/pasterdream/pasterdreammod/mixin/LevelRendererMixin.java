package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    /** 灯影之下维度专用的黑色降雨纹理。 */
    private static final ResourceLocation PASTERDREAM$BLACK_RAIN =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/environment/black_rain.png");

    /**
     * 灯影之下维度降雨时改用黑色雨纹理。重定向原版静态字段读取而非整段覆写，
     * 与其他模组对 renderSnowAndRain 的注入天然兼容。
     */
    @ModifyExpressionValue(method = "renderSnowAndRain", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;RAIN_LOCATION:Lnet/minecraft/resources/ResourceLocation;",
            opcode = Opcodes.GETSTATIC))
    private ResourceLocation pasterdream$useBlackRainInLampShadow(ResourceLocation original)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD))
        {
            return PASTERDREAM$BLACK_RAIN;
        }
        return original;
    }

    /**
     * 灯影之下与风之旅途使用纯色天空：取消原版天空几何体，由 {@link FogRendererMixin}
     * 设置的清屏色填充背景，效果与来源的全屏纯色四边形一致且不依赖旧版 PoseStack 签名。
     */
    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void pasterdream$cancelCustomSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick,
                                             Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo ci)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && (level.dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD)
                || level.dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD)))
        {
            ci.cancel();
        }
    }

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void pasterdream$cancelWindJourneyClouds(PoseStack poseStack, Matrix4f frustumMatrix, Matrix4f projectionMatrix,
                                                     float partialTick, double camX, double camY, double camZ, CallbackInfo ci)
    {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && level.dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD))
        {
            ci.cancel();
        }
    }
}
