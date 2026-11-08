package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.client.renderer.TerrorbeakRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowHandRenderer;
import com.pasterdream.pasterdreammod.client.renderer.KusanagiVenomStingerRenderer;
import com.pasterdream.pasterdreammod.client.renderer.MeltDreamCrystalEntityRenderer;
import com.pasterdream.pasterdreammod.client.renderer.TideSwordProjectileRenderer;
import com.pasterdream.pasterdreammod.client.renderer.WindThunderSpearRenderer;
import com.pasterdream.pasterdreammod.client.renderer.FluffyWindArrowRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowAlloyArrowRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
        event.registerEntityRenderer(ModEntities.TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.CRAZY_TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.WEAKENESS_TERRORBEAK.get(), TerrorbeakRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_HAND.get(), ShadowHandRenderer::new);
        event.registerEntityRenderer(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.TIDE_SWORD_PROJECTILE.get(), TideSwordProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.KUSANAGI_VENOM_STINGER.get(), KusanagiVenomStingerRenderer::new);
        event.registerEntityRenderer(ModEntities.WIND_ALLOY_LIGHTNING.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.WIND_THUNDER_SPEAR.get(), WindThunderSpearRenderer::new);
        event.registerEntityRenderer(ModEntities.FLUFFY_WIND_ARROW.get(), FluffyWindArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_ALLOY_ARROW.get(), ShadowAlloyArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.PEBBLE_PROJECTILE.get(), ThrownItemRenderer::new);
    }
}
