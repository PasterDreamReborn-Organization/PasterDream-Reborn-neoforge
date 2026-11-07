package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.worldgen.feature.LightBallTreeDecorator;
import com.pasterdream.pasterdreammod.worldgen.feature.PinkShroomlightTreeDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 树木装饰器类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModTreeDecoratorTypes.java}。
 */
public class ModTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<PinkShroomlightTreeDecorator>> PINK_SHROOMLIGHT =
            TREE_DECORATOR_TYPES.register("pink_shroomlight", () -> new TreeDecoratorType<>(MapCodec.unit(PinkShroomlightTreeDecorator.INSTANCE)));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<LightBallTreeDecorator>> LIGHT_BALL =
            TREE_DECORATOR_TYPES.register("light_ball", () -> new TreeDecoratorType<>(MapCodec.unit(LightBallTreeDecorator.INSTANCE)));

    public static void register(IEventBus eventBus) {
        TREE_DECORATOR_TYPES.register(eventBus);
    }

    private ModTreeDecoratorTypes() {
    }
}
