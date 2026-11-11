package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class ShadowBrazierDisplayItemRenderer extends GeoItemRenderer<ShadowBrazierDisplayItem> {
    public ShadowBrazierDisplayItemRenderer() {
        super(new ShadowBrazierDisplayModel());
    }

    @Override
    public RenderType getRenderType(ShadowBrazierDisplayItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
