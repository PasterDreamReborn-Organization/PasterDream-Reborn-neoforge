package com.pasterdream.pasterdreammod.world.block.shadowvortex;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nullable;

public class ShadowVortexTileRenderer extends GeoBlockRenderer<ShadowVortexTileEntity> {
    public ShadowVortexTileRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShadowVortexBlockModel());
    }

    @Override
    public RenderType getRenderType(ShadowVortexTileEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
