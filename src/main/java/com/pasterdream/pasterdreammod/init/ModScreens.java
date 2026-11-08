package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableScreen;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskScreen;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagScreen;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagScreen;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarScreen;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanScreen;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronScreen;
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
        event.register(ModMenus.DYEDREAM_DESK.get(), DyedreamDeskScreen::new);
        event.register(ModMenus.STORAGE_BAG.get(), StorageBagScreen::new);
        event.register(ModMenus.MORTAR.get(), MortarScreen::new);
        event.register(ModMenus.LARGE_STORAGE_BAG.get(), LargeStorageBagScreen::new);
        event.register(ModMenus.CLAYPAN.get(), ClaypanScreen::new);
        event.register(ModMenus.DREAM_CAULDRON.get(), DreamCauldronScreen::new);
    }
}
