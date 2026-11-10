package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class TwilightLanternDisplayItemRenderer extends GeoItemRenderer<TwilightLanternDisplayItem> {
    public TwilightLanternDisplayItemRenderer() {
        super(new TwilightLanternDisplayModel());
    }

    @Override
    public RenderType getRenderType(TwilightLanternDisplayItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
