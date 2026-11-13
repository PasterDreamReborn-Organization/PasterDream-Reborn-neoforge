package com.pasterdream.pasterdreammod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** 状态效果框在背包背景之后单独绘制，需要独立开启混合。 */
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class InventoryEffectsAlphaMixin
{
    @Inject(method = "renderBackgrounds", at = @At("HEAD"))
    private void pasterdream$enableEffectsAlpha(GuiGraphics guiGraphics, int renderX, int yOffset,
                                                Iterable<MobEffectInstance> effects, boolean isSmall, CallbackInfo ci)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    @Inject(method = "renderBackgrounds", at = @At("RETURN"))
    private void pasterdream$restoreEffectsAlpha(GuiGraphics guiGraphics, int renderX, int yOffset,
                                                 Iterable<MobEffectInstance> effects, boolean isSmall, CallbackInfo ci)
    {
        RenderSystem.disableBlend();
    }
}
