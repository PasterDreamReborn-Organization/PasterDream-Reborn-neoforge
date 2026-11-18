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
import top.theillusivec4.curios.api.client.ICuriosScreen;

/** 让内置 UI 资源包的背包底图和创造栏精灵正确使用透明通道。 */
@Mixin(AbstractContainerScreen.class)
public abstract class InventoryScreenAlphaMixin
{
    @Inject(method = "renderBackground", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V"))
    private void pasterdream$enableInventoryAlpha(GuiGraphics guiGraphics, int mouseX, int mouseY,
                                                  float partialTick, CallbackInfo ci)
    {
        if (pasterdream$drawsInventoryBackground())
        {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        }
    }

    @Inject(method = "renderBackground", at = @At("RETURN"))
    private void pasterdream$restoreInventoryAlpha(GuiGraphics guiGraphics, int mouseX, int mouseY,
                                                   float partialTick, CallbackInfo ci)
    {
        if (pasterdream$drawsInventoryBackground())
            RenderSystem.disableBlend();
    }

    /**
     * 会绘制 {@code textures/gui/container/inventory.png} 的界面。Curios 的饰品栏界面
     * （{@code CuriosScreen implements ICuriosScreen}）复用同一张背包底图，但类型不是
     * {@link InventoryScreen}，必须单独放行，否则打开饰品栏时底图会忽略 alpha、渲染成不透明亮块。
     */
    private boolean pasterdream$drawsInventoryBackground()
    {
        return (Object) this instanceof InventoryScreen
                || (Object) this instanceof CreativeModeInventoryScreen
                || (Object) this instanceof ICuriosScreen;
    }
}
