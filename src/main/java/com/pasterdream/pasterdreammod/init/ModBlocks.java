package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModBlocks.java}。搬运方块时在此添加
 * {@code DeferredBlock<T>} 常量，无特殊机制直接用原版类，特殊逻辑放 {@code world/block/}。
 */
public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PasterDreamMod.MOD_ID);

    // TODO: 在此注册方块

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private ModBlocks() {
    }
}
