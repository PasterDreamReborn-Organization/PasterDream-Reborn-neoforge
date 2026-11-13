package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** 让内置 UI 资源包的背包底图和创造栏精灵正确使用透明通道。 */
@Mixin(AbstractContainerScreen.class)
public abstract class InventoryScreenAlphaMixin
{
    @Inject(method = "renderBackground", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V"))
    private void pasterdream$enableInventoryAlpha(GuiGraphics guiGraphics, int mouseX, int mouseY,
                                                  float partialTick, CallbackInfo ci)
    {
        if ((Object) this instanceof InventoryScreen || (Object) this instanceof CreativeModeInventoryScreen)
        {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }
    }

    @Inject(method = "renderBackground", at = @At("RETURN"))
    private void pasterdream$restoreInventoryAlpha(GuiGraphics guiGraphics, int mouseX, int mouseY,
                                                   float partialTick, CallbackInfo ci)
    {
        if ((Object) this instanceof InventoryScreen || (Object) this instanceof CreativeModeInventoryScreen)
            RenderSystem.disableBlend();
    }
}
