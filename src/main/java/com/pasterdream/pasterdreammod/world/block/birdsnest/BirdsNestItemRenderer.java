package com.pasterdream.pasterdreammod.world.block.birdsnest;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class BirdsNestItemRenderer extends GeoItemRenderer<BirdsNestItem> {
    public BirdsNestItemRenderer() {
        super(new BirdsNestItemModel());
    }

    @Override
    public RenderType getRenderType(BirdsNestItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
