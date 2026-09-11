package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
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
        // TODO: event.register(ModMenus.CLAYPAN.get(), ClaypanScreen::new);
        // TODO: event.register(ModMenus.DREAM_CAULDRON.get(), DreamCauldronScreen::new);
    }
}
