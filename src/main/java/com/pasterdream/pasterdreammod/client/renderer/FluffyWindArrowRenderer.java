package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class FluffyWindArrowRenderer extends ArrowRenderer<FluffyWindArrowEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/projectiles/arrow.png");
    private static final ResourceLocation SPECTRAL_ARROW_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/projectiles/spectral_arrow.png");

    public FluffyWindArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FluffyWindArrowEntity arrow) {
        if (arrow.getAmmoItem().is(Items.SPECTRAL_ARROW)) {
            return SPECTRAL_ARROW_TEXTURE;
        }
        return TEXTURE;
    }
}
