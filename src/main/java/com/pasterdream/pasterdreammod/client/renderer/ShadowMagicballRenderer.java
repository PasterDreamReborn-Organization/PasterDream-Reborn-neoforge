package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.client.model.ShadowMagicballModel;
import com.pasterdream.pasterdreammod.world.entity.ShadowMagicballEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ShadowMagicballRenderer extends GeoEntityRenderer<ShadowMagicballEntity> {
    public ShadowMagicballRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShadowMagicballModel());
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(ShadowMagicballEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
