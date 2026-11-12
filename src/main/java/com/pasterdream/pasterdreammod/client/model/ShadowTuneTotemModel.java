package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.ShadowTuneTotemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ShadowTuneTotemModel extends GeoModel<ShadowTuneTotemEntity> {
    @Override
    public ResourceLocation getAnimationResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/shadow_rune_totem.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/shadow_rune_totem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShadowTuneTotemEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/entities/" + entity.getTexture() + ".png");
    }
}
