package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 实体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModEntities.java}。
 * 实体属性在 {@code EntityAttributeCreationEvent} 中注册（后续搬运时补充）。
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册实体（生物 / 弹射物 / 投影物）

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    private ModEntities() {
    }
}
