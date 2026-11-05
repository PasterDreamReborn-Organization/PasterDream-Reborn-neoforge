package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlockEntity;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlockEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntity;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块实体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModBlockEntities.java}。
 */
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LostSwordTombBlockEntity>> LOST_SWORD_TOMB =
            BLOCK_ENTITIES.register("lost_sword_tomb",
                    () -> BlockEntityType.Builder.of(LostSwordTombBlockEntity::new, ModBlocks.LOST_SWORD_TOMB.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DesertHeroTombBlockEntity>> DESERT_HERO_TOMB =
            BLOCK_ENTITIES.register("desert_hero_tomb",
                    () -> BlockEntityType.Builder.of(DesertHeroTombBlockEntity::new, ModBlocks.DESERT_HERO_TOMB.get())
                            .build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<QYMDollBlockEntity>> QYM_DOLL = BLOCK_ENTITIES.register("qym_doll", () -> BlockEntityType.Builder.of(QYMDollBlockEntity::new, ModBlocks.QYM_DOLL.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UUZDollBlockEntity>> UUZ_DOLL = BLOCK_ENTITIES.register("uuz_doll", () -> BlockEntityType.Builder.of(UUZDollBlockEntity::new, ModBlocks.UUZ_DOLL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private ModBlockEntities() {
    }
}
