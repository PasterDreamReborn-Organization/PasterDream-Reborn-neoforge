package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import com.pasterdream.pasterdreammod.world.dimension.WindJourneyDimension;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin
{
    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    private static void cancelFogSetup(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean nearFog, float partialTick, CallbackInfo ci)
    {
        if (Minecraft.getInstance().level != null
                && Minecraft.getInstance().level.dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD))
        {
            ci.cancel();
        }
    }

    @Inject(method = "setupColor", at = @At("HEAD"), cancellable = true)
    private static void pasterdream$forceBlackClearColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, CallbackInfo ci)
    {
        if (level != null && level.dimension().equals(LampShadowDimension.LAMP_SHADOW_WORLD))
        {
            RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 1.0F);
            RenderSystem.setShaderFogColor(0.0F, 0.0F, 0.0F, 1.0F);
            ci.cancel();
        }
    }

    @Inject(method = "setupColor", at = @At("HEAD"), cancellable = true)
    private static void pasterdream$forceCyanClearColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, CallbackInfo ci)
    {
        if (level != null && level.dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD))
        {
            RenderSystem.clearColor(0.5333333333F, 0.9568627451F, 0.9215686275F, 1.0F);
            ci.cancel();
        }
    }

    @Inject(method = "levelFogColor", at = @At("HEAD"), cancellable = true)
    private static void pasterdream$forceCyanFogColor(CallbackInfo ci)
    {
        if (Minecraft.getInstance().level != null
                && Minecraft.getInstance().level.dimension().equals(WindJourneyDimension.WIND_JOURNEY_WORLD))
        {
            RenderSystem.setShaderFogColor(0.5333333333F, 0.9568627451F, 0.9215686275F, 1.0F);
            ci.cancel();
        }
    }
}
