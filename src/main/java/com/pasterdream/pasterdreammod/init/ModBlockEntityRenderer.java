package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapTileRenderer;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.ecologyglassjar.EcologyGlassJarBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.fireflyglassjar.FireflyGlassJarBlockEntityRenderer;
import com.pasterdream.pasterdreammod.world.block.birdsnest.BirdsNestBlockEntityRenderer;
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
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_HAND_TRAP.get(), ShadowHandTrapTileRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.LIFE_CRYSTAL.get(), LifeCrystalBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CLAYPAN.get(), ClaypanBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WEAPON_WORKSHOP_COOLER_POT.get(), WeaponWorkshopCoolerPotBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DREAM_CAULDRON.get(), DreamCauldronBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DREAM_ACCUMULATOR.get(), DreamAccumulatorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SHADOW_CHEST.get(), ShadowChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MELT_DREAM_CRYSTAL_CHEST.get(), MeltDreamCrystalChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), OpenedMeltDreamCrystalChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), TheEndlessBookOfDreamSeekersBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ECOLOGY_GLASS_JAR.get(), EcologyGlassJarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.FIREFLY_GLASS_JAR.get(), FireflyGlassJarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BIRDS_NEST.get(), BirdsNestBlockEntityRenderer::new);
    }
}
