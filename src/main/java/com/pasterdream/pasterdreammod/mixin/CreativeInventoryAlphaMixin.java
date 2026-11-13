package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** 标签图标渲染可能关闭混合，故每次绘制半透明背景前重新启用。 */
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventoryAlphaMixin
{
    @Inject(method = "renderTabButton", at = @At("HEAD"))
    private void pasterdream$enableTabAlpha(GuiGraphics guiGraphics, CreativeModeTab tab, CallbackInfo ci)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    @Inject(method = "renderBg", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    private void pasterdream$enableBackgroundAlpha(GuiGraphics guiGraphics, float partialTick,
                                                   int mouseX, int mouseY, CallbackInfo ci)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }
}
