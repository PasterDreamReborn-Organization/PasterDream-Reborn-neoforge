package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * DataComponents 注册中心。
 *
 * <p>NeoForge 1.21 起物品数据由 NBT 改为 DataComponents：
 * 强类型状态（等级 / 类型 / 内容 key 等）在此定义 {@code DataComponentType}；
 * 历史/自由 NBT 用原版 {@code DataComponents.CUSTOM_DATA} 过渡。
 */
public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册 DataComponentType（Codec + StreamCodec）

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }

    private ModDataComponents() {
    }
}
