package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块实体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModBlockEntities.java}。
 */
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册方块实体（BlockEntityType.Builder.of(...)）

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private ModBlockEntities() {
    }
}
