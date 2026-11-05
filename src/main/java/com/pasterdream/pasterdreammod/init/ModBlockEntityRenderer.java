package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntityRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * 方块实体渲染器注册中心（客户端）。
 */
public final class ModBlockEntityRenderer {
    private ModBlockEntityRenderer() {
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.QYM_DOLL.get(), QYMDollBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.UUZ_DOLL.get(), UUZDollBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.DESERT_HERO_TOMB.get(), DesertHeroTombBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LIFE_CRYSTAL.get(), LifeCrystalBlockEntityRenderer::new);
    }
}
