package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.client.renderer.MeltDreamCrystalEntityRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * 实体渲染器注册中心（客户端）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code client/ModEntityRenderer.java}。
 */
public final class ModEntityRenderer {
    private ModEntityRenderer() {
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityRenderer::new);
    }
}
