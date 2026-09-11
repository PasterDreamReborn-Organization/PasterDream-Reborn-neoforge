package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 树木装饰器类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModTreeDecoratorTypes.java}。
 */
public class ModTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册树木装饰器类型

    public static void register(IEventBus eventBus) {
        TREE_DECORATOR_TYPES.register(eventBus);
    }

    private ModTreeDecoratorTypes() {
    }
}
