package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.client.model.ShadowTuneTotemModel;
import com.pasterdream.pasterdreammod.world.entity.ShadowTuneTotemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ShadowTuneTotemRenderer extends GeoEntityRenderer<ShadowTuneTotemEntity> {
    public ShadowTuneTotemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShadowTuneTotemModel());
        this.shadowRadius = 4f;
    }

    @Override
    public RenderType getRenderType(ShadowTuneTotemEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
