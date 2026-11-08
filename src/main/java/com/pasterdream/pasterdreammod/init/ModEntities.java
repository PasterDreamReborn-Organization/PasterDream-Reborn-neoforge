package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.entity.ShadowHandEntity;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.terrorbeak.TerrorbeakEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.KusanagiVenomStingerEntity;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import com.pasterdream.pasterdreammod.world.entity.PebbleProjectile;
import com.pasterdream.pasterdreammod.world.entity.TideSwordProjectileEntity;
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

    public static final DeferredHolder<EntityType<?>, EntityType<TideSwordProjectileEntity>> TIDE_SWORD_PROJECTILE =
            register("tide_sword_projectile",
                    EntityType.Builder.<TideSwordProjectileEntity>of(TideSwordProjectileEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<KusanagiVenomStingerEntity>> KUSANAGI_VENOM_STINGER =
            register("kusanagi_venom_stinger",
                    EntityType.Builder.<KusanagiVenomStingerEntity>of(KusanagiVenomStingerEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<WindAlloyLightningEntity>> WIND_ALLOY_LIGHTNING =
            register("wind_alloy_lightning",
                    EntityType.Builder.<WindAlloyLightningEntity>of(WindAlloyLightningEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(false)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<WindThunderSpearEntity>> WIND_THUNDER_SPEAR =
            register("wind_thunder_spear",
                    EntityType.Builder.<WindThunderSpearEntity>of(WindThunderSpearEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<FluffyWindArrowEntity>> FLUFFY_WIND_ARROW =
            register("fluffy_wind_arrow",
                    EntityType.Builder.<FluffyWindArrowEntity>of(FluffyWindArrowEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowAlloyArrowEntity>> SHADOW_ALLOY_ARROW =
            register("shadow_alloy_arrow",
                    EntityType.Builder.<ShadowAlloyArrowEntity>of(ShadowAlloyArrowEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<PebbleProjectile>> PEBBLE_PROJECTILE = register("pebble_projectile",
            EntityType.Builder.<PebbleProjectile>of(PebbleProjectile::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(0.25f, 0.25f));

    public static final DeferredHolder<EntityType<?>, EntityType<TerrorbeakEntity>> TERRORBEAK = register("terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(1.8f, 3.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<TerrorbeakEntity>> CRAZY_TERRORBEAK = register("crazy_terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(1.8f, 4f));

    public static final DeferredHolder<EntityType<?>, EntityType<TerrorbeakEntity>> WEAKENESS_TERRORBEAK = register("weakeness_terrorbeak",
            EntityType.Builder.<TerrorbeakEntity>of(TerrorbeakEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(1.5f, 3f));

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowHandEntity>> SHADOW_HAND = register("shadow_hand",
            EntityType.Builder.<ShadowHandEntity>of(ShadowHandEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(0.6f, 0.8f));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        eventBus.addListener(TerrorbeakEntity::init);
        eventBus.addListener(ShadowHandEntity::init);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(TERRORBEAK.get(), TerrorbeakEntity.createTerrorbeakAttributes().build());
        event.put(CRAZY_TERRORBEAK.get(), TerrorbeakEntity.createCrazyTerrorbeakAttributes().build());
        event.put(WEAKENESS_TERRORBEAK.get(), TerrorbeakEntity.createWeakenessTerrorbeakAttributes().build());
        event.put(SHADOW_HAND.get(), ShadowHandEntity.createAttributes().build());
        event.put(MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityEntity.createAttributes().build());
    }

    private ModEntities() {
    }
}
