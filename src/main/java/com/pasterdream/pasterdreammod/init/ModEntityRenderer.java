package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.client.renderer.TerrorbeakRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowHandRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowGolemRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowGhostRenderer;
import com.pasterdream.pasterdreammod.client.renderer.BlackBeetleRenderer;
import com.pasterdream.pasterdreammod.client.renderer.BlackBeetleMotherRenderer;
import com.pasterdream.pasterdreammod.client.renderer.KusanagiVenomStingerRenderer;
import com.pasterdream.pasterdreammod.client.renderer.MeltDreamCrystalEntityRenderer;
import com.pasterdream.pasterdreammod.client.renderer.TideSwordProjectileRenderer;
import com.pasterdream.pasterdreammod.client.renderer.WindThunderSpearRenderer;
import com.pasterdream.pasterdreammod.client.renderer.FluffyWindArrowRenderer;
import com.pasterdream.pasterdreammod.client.renderer.ShadowAlloyArrowRenderer;
import com.pasterdream.pasterdreammod.client.renderer.FoxFireRenderer;
import com.pasterdream.pasterdreammod.client.renderer.GoldenFoxRenderer;
import com.pasterdream.pasterdreammod.client.renderer.GoldenFoxPetRenderer;
import com.pasterdream.pasterdreammod.client.renderer.GoldenFoxFireballRenderer;
import com.pasterdream.pasterdreammod.client.renderer.TerraswordWaveRenderer;
import com.pasterdream.pasterdreammod.client.renderer.FireflyRenderer;
import com.pasterdream.pasterdreammod.client.renderer.SmallStoneSpiritRenderer;
import com.pasterdream.pasterdreammod.client.renderer.PinkChickenRenderer;
import com.pasterdream.pasterdreammod.client.renderer.PinkSlimeRenderer;
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
        event.registerEntityRenderer(ModEntities.SHADOW_GOLEM.get(), ShadowGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_SQUEAL_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.WAILING_SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.FRIENDLY_SHADOW_GHOST.get(), ShadowGhostRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACK_BEETLE.get(), BlackBeetleRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACK_BEETLE_MOTHER.get(), BlackBeetleMotherRenderer::new);
        event.registerEntityRenderer(ModEntities.SQUEAL_WAVE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.TIDE_SWORD_PROJECTILE.get(), TideSwordProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.KUSANAGI_VENOM_STINGER.get(), KusanagiVenomStingerRenderer::new);
        event.registerEntityRenderer(ModEntities.WIND_ALLOY_LIGHTNING.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.WIND_THUNDER_SPEAR.get(), WindThunderSpearRenderer::new);
        event.registerEntityRenderer(ModEntities.FLUFFY_WIND_ARROW.get(), FluffyWindArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_ALLOY_ARROW.get(), ShadowAlloyArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.PEBBLE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_POTION_BOTTLE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.REJUVENATION_BOTTLE_ENTITY.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.POTION_BOTTLE_EFFECT_ENTITY.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.FOX_FIRE.get(), FoxFireRenderer::new);
        event.registerEntityRenderer(ModEntities.GOLDEN_FOX.get(), GoldenFoxRenderer::new);
        event.registerEntityRenderer(ModEntities.GOLDEN_FOX_PET.get(), GoldenFoxPetRenderer::new);
        event.registerEntityRenderer(ModEntities.GOLDEN_FOX_FIREBALL.get(), GoldenFoxFireballRenderer::new);
        event.registerEntityRenderer(ModEntities.TERRASWORD_WAVE.get(), TerraswordWaveRenderer::new);
        event.registerEntityRenderer(ModEntities.FIREFLY.get(), FireflyRenderer::new);
        event.registerEntityRenderer(ModEntities.SMALL_STONE_SPIRIT.get(), SmallStoneSpiritRenderer::new);
        event.registerEntityRenderer(ModEntities.PINK_CHICKEN.get(), PinkChickenRenderer::new);
        event.registerEntityRenderer(ModEntities.PINK_SLIME.get(), PinkSlimeRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_PINK_EGG.get(), ThrownItemRenderer::new);
    }
}
