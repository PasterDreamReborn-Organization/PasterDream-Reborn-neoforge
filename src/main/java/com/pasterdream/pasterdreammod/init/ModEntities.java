package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 实体类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModEntities.java}。
 * 实体属性在 {@link EntityAttributeCreationEvent} 中注册。
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<MeltDreamCrystalEntityEntity>> MELT_DREAM_CRYSTAL_ENTITY =
            register("melt_dream_crystal_entity",
                    EntityType.Builder.<MeltDreamCrystalEntityEntity>of(MeltDreamCrystalEntityEntity::new, MobCategory.CREATURE)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .fireImmune()
                            .sized(0.6f, 1f));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityEntity.createAttributes().build());
    }

    private ModEntities() {
    }
}
