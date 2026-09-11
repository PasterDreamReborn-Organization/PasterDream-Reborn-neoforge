package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 容器菜单类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModMenus.java}。
 * Screen 在 {@code client.ModScreens} 的 {@code RegisterMenuScreensEvent} 中注册。
 */
public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, PasterDreamMod.MOD_ID);

    // TODO: 在此注册菜单

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    private ModMenus() {
    }
}
