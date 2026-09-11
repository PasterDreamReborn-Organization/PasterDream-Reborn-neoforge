package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 物品注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModItems.java}。搬运物品时在此添加
 * {@code DeferredItem<T>} 常量；无特殊机制直接用原版 {@code Item}。
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PasterDreamMod.MOD_ID);

    // TODO: 在此注册物品（含 BlockItem、食物、饮品、工具、护甲、刷怪蛋）

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private ModItems() {
    }
}
