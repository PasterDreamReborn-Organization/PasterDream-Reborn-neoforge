package com.pasterdream.pasterdreammod.world.block.ecologyglassjar;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import javax.annotation.Nullable;

public class EcologyGlassJarItemRenderer extends GeoItemRenderer<EcologyGlassJarItem> {
    public EcologyGlassJarItemRenderer() {
        super(new EcologyGlassJarItemModel());
    }

    @Override
    public RenderType getRenderType(EcologyGlassJarItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
