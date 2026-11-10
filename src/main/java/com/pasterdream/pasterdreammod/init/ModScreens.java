package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil.WeaponWorkshopAnvilScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.coolerpot.WeaponWorkshopCoolerPotScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.grindstone.WeaponWorkshopGrindStoneScreen;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace.WeaponWorkshopBlastFurnaceScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.windmoordesk.WindMoorDeskScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.shadowchest.ShadowChestScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.windmoorcrate.WindMoorCrateScreen;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagScreen;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagScreen;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarScreen;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronScreen;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorScreen;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/**
 * Screen 注册中心（客户端）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModScreens.java}。
 * NeoForge 用 {@link RegisterMenuScreensEvent} 取代旧版 {@code MenuScreens.register} 直调。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ModScreens {
    private ModScreens() {
    }

    @SubscribeEvent
    public static void register(RegisterMenuScreensEvent event) {
        event.register(ModMenus.WEAPON_WORKSHOP_CRAFTING_TABLE.get(), WeaponWorkshopCraftingTableScreen::new);
        event.register(ModMenus.WEAPON_WORKSHOP_ANVIL.get(), WeaponWorkshopAnvilScreen::new);
        event.register(ModMenus.WEAPON_WORKSHOP_COOLER_POT.get(), WeaponWorkshopCoolerPotScreen::new);
        event.register(ModMenus.WEAPON_WORKSHOP_GRIND_STONE.get(), WeaponWorkshopGrindStoneScreen::new);
        event.register(ModMenus.WEAPON_WORKSHOP_BLAST_FURNACE.get(), WeaponWorkshopBlastFurnaceScreen::new);
        event.register(ModMenus.DYEDREAM_DESK.get(), DyedreamDeskScreen::new);
        event.register(ModMenus.SHADOW_DESK.get(), ShadowDeskScreen::new);
        event.register(ModMenus.WIND_MOOR_DESK.get(), WindMoorDeskScreen::new);
        event.register(ModMenus.PICNIC_BASKET.get(), PicnicBasketScreen::new);
        event.register(ModMenus.SHADOW_CHEST.get(), ShadowChestScreen::new);
        event.register(ModMenus.OPENED_MELT_DREAM_CRYSTAL_CHEST.get(), OpenedMeltDreamCrystalChestScreen::new);
        event.register(ModMenus.WIND_MOOR_CRATE.get(), WindMoorCrateScreen::new);
        event.register(ModMenus.STORAGE_BAG.get(), StorageBagScreen::new);
        event.register(ModMenus.MORTAR.get(), MortarScreen::new);
        event.register(ModMenus.LARGE_STORAGE_BAG.get(), LargeStorageBagScreen::new);
        event.register(ModMenus.CLAYPAN.get(), ClaypanScreen::new);
        event.register(ModMenus.DREAM_CAULDRON.get(), DreamCauldronScreen::new);
        event.register(ModMenus.DREAM_ACCUMULATOR.get(), DreamAccumulatorScreen::new);
        event.register(ModMenus.SHADOW_BLAST_FURNACE.get(), ShadowBlastFurnaceScreen::new);
    }
}
