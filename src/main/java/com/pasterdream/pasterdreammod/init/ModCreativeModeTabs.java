package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 创造模式物品栏注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModCreativeModeTabs.java}。
 */
public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    // TODO: 在此注册物品栏（装备 / 方块 / 材料 / 食物 / 调试等）

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
