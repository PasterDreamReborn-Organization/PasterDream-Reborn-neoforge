package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntityRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * 方块实体渲染器注册中心（客户端）。
 */
public final class ModBlockEntityRenderer {
    private ModBlockEntityRenderer() {
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.DESERT_HERO_TOMB.get(), DesertHeroTombBlockEntityRenderer::new);
    }
}
