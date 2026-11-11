package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.entity.ShadowHandEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowGolemEntity;
import com.pasterdream.pasterdreammod.world.entity.shakingcrystal.ShakingCrystalEntity;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleEntity;
import com.pasterdream.pasterdreammod.world.entity.beetle.BlackBeetleMotherEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.FriendlyShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.ShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.SquealWaveProjectileEntity;
import com.pasterdream.pasterdreammod.world.entity.ghost.WailingShadowGhostEntity;
import com.pasterdream.pasterdreammod.world.entity.FireflyEntity;
import com.pasterdream.pasterdreammod.world.entity.SmallStoneSpiritEntity;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import com.pasterdream.pasterdreammod.world.entity.WindThunderSpearEntity;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.arrows.BlackstoneArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.BlastArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ChaoticArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ChargedArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.CongealWindArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.DewArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.FragdreamArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.MagicalArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.ToxicSporeArrow;
import com.pasterdream.pasterdreammod.world.entity.FoxFireEntity;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxEntity;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxFireballEntity;
import com.pasterdream.pasterdreammod.world.entity.GoldenFoxPetEntity;
import com.pasterdream.pasterdreammod.world.entity.TerraswordWaveEntity;
import com.pasterdream.pasterdreammod.world.entity.terrorbeak.TerrorbeakEntity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.entity.KusanagiVenomStingerEntity;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamCrystalEntityEntity;
import com.pasterdream.pasterdreammod.world.entity.PebbleProjectile;
import com.pasterdream.pasterdreammod.world.entity.PinkChickenEntity;
import com.pasterdream.pasterdreammod.world.entity.PinkSlimeEntity;
import com.pasterdream.pasterdreammod.world.entity.PotionBottleEffectEntity;
import com.pasterdream.pasterdreammod.world.entity.RejuvenationBottleEntity;
import com.pasterdream.pasterdreammod.world.entity.ThrownPinkEgg;
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
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

    public static final DeferredHolder<EntityType<?>, EntityType<PinkChickenEntity>> PINK_CHICKEN =
            register("pink_chicken",
                    EntityType.Builder.<PinkChickenEntity>of(PinkChickenEntity::new, MobCategory.CREATURE)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .sized(0.4f, 0.7f));

    public static final DeferredHolder<EntityType<?>, EntityType<PinkSlimeEntity>> PINK_SLIME =
            register("pink_slime",
                    EntityType.Builder.<PinkSlimeEntity>of(PinkSlimeEntity::new, MobCategory.CREATURE)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPinkEgg>> THROWN_PINK_EGG =
            register("thrown_pink_egg",
                    EntityType.Builder.<ThrownPinkEgg>of(ThrownPinkEgg::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .sized(0.25f, 0.25f));

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

    public static final DeferredHolder<EntityType<?>, EntityType<MeltDreamArrowEntity>> MELT_DREAM_ARROW =
            register("melt_dream_arrow",
                    EntityType.Builder.<MeltDreamArrowEntity>of(MeltDreamArrowEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ToxicSporeArrow>> TOXIC_SPORE_ARROW =
            register("toxic_spore_arrow",
                    EntityType.Builder.<ToxicSporeArrow>of(ToxicSporeArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<FragdreamArrow>> FRAGDREAM_ARROW =
            register("fragdream_arrow",
                    EntityType.Builder.<FragdreamArrow>of(FragdreamArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<MagicalArrow>> MAGICAL_ARROW =
            register("magical_arrow",
                    EntityType.Builder.<MagicalArrow>of(MagicalArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ChargedArrow>> CHARGED_ARROW =
            register("charged_arrow",
                    EntityType.Builder.<ChargedArrow>of(ChargedArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackstoneArrow>> BLACKSTONE_ARROW =
            register("blackstone_arrow",
                    EntityType.Builder.<BlackstoneArrow>of(BlackstoneArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ChaoticArrow>> CHAOTIC_ARROW =
            register("chaotic_arrow",
                    EntityType.Builder.<ChaoticArrow>of(ChaoticArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<BlastArrow>> BLAST_ARROW =
            register("blast_arrow",
                    EntityType.Builder.<BlastArrow>of(BlastArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<CongealWindArrow>> CONGEAL_WIND_ARROW =
            register("congeal_wind_arrow",
                    EntityType.Builder.<CongealWindArrow>of(CongealWindArrow::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<DewArrow>> DEW_ARROW =
            register("dew_arrow",
                    EntityType.Builder.<DewArrow>of(DewArrow::new, MobCategory.MISC)
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

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPotionBottle>> THROWN_POTION_BOTTLE = register("thrown_potion_bottle",
            EntityType.Builder.<ThrownPotionBottle>of(ThrownPotionBottle::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(0.25f, 0.25f));

    public static final DeferredHolder<EntityType<?>, EntityType<RejuvenationBottleEntity>> REJUVENATION_BOTTLE_ENTITY = register("rejuvenation_bottle_entity",
            EntityType.Builder.<RejuvenationBottleEntity>of(RejuvenationBottleEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(false)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<PotionBottleEffectEntity>> POTION_BOTTLE_EFFECT_ENTITY = register("potion_bottle_effect_entity",
            EntityType.Builder.<PotionBottleEffectEntity>of(PotionBottleEffectEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(false)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowGolemEntity>> SHADOW_GOLEM = register("shadow_golem",
            EntityType.Builder.<ShadowGolemEntity>of(ShadowGolemEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(2.2f, 3.5f));

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

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowGhostEntity>> SHADOW_GHOST = register("shadow_ghost",
            EntityType.Builder.<ShadowGhostEntity>of(ShadowGhostEntity::new, ModMobCategories.shadowGhost())
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final DeferredHolder<EntityType<?>, EntityType<ShadowGhostEntity>> SHADOW_SQUEAL_GHOST = register("shadow_squeal_ghost",
            EntityType.Builder.<ShadowGhostEntity>of(ShadowGhostEntity::new, ModMobCategories.shadowGhost())
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final DeferredHolder<EntityType<?>, EntityType<WailingShadowGhostEntity>> WAILING_SHADOW_GHOST = register("wailing_shadow_ghost",
            EntityType.Builder.<WailingShadowGhostEntity>of(WailingShadowGhostEntity::new, ModMobCategories.shadowGhost())
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(0.8f, 1.3f));

    public static final DeferredHolder<EntityType<?>, EntityType<FriendlyShadowGhostEntity>> FRIENDLY_SHADOW_GHOST = register("friendly_shadow_ghost",
            EntityType.Builder.<FriendlyShadowGhostEntity>of(FriendlyShadowGhostEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune()
                    .sized(0.7f, 1.2f));

    public static final DeferredHolder<EntityType<?>, EntityType<SquealWaveProjectileEntity>> SQUEAL_WAVE_PROJECTILE = register("squeal_wave_projectile",
            EntityType.Builder.<SquealWaveProjectileEntity>of(SquealWaveProjectileEntity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(1)
                    .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackBeetleEntity>> BLACK_BEETLE = register("black_beetle",
            EntityType.Builder.<BlackBeetleEntity>of(BlackBeetleEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(48)
                    .setUpdateInterval(3)
                    .sized(0.6f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackBeetleMotherEntity>> BLACK_BEETLE_MOTHER = register("black_beetle_mother",
            EntityType.Builder.<BlackBeetleMotherEntity>of(BlackBeetleMotherEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(48)
                    .setUpdateInterval(3)
                    .sized(2f, 1f));

    public static final DeferredHolder<EntityType<?>, EntityType<FireflyEntity>> FIREFLY = register("firefly",
            EntityType.Builder.<FireflyEntity>of(FireflyEntity::new, MobCategory.CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(0.5f, 0.5f));

    public static final DeferredHolder<EntityType<?>, EntityType<SmallStoneSpiritEntity>> SMALL_STONE_SPIRIT = register("small_stone_spirit",
            EntityType.Builder.<SmallStoneSpiritEntity>of(SmallStoneSpiritEntity::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .sized(0.7f, 1f));

    public static final DeferredHolder<EntityType<?>, EntityType<TerraswordWaveEntity>> TERRASWORD_WAVE =
            register("terrasword_wave",
                    EntityType.Builder.<TerraswordWaveEntity>of(TerraswordWaveEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(1)
                            .fireImmune()
                            .sized(0.1f, 0.1f));

    public static final DeferredHolder<EntityType<?>, EntityType<FoxFireEntity>> FOX_FIRE =
            register("fox_fire",
                    EntityType.Builder.<FoxFireEntity>of(FoxFireEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .fireImmune()
                            .sized(1f, 1f));

    public static final DeferredHolder<EntityType<?>, EntityType<ShakingCrystalEntity>> SHAKING_CRYSTAL =
            register("shaking_crystal",
                    EntityType.Builder.<ShakingCrystalEntity>of(ShakingCrystalEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .fireImmune()
                            .sized(0.6f, 0.8f));

    public static final DeferredHolder<EntityType<?>, EntityType<GoldenFoxEntity>> GOLDEN_FOX =
            register("golden_fox",
                    EntityType.Builder.<GoldenFoxEntity>of(GoldenFoxEntity::new, MobCategory.CREATURE)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .sized(0.6f, 0.6f));

    public static final DeferredHolder<EntityType<?>, EntityType<GoldenFoxPetEntity>> GOLDEN_FOX_PET =
            register("golden_fox_pet",
                    EntityType.Builder.<GoldenFoxPetEntity>of(GoldenFoxPetEntity::new, MobCategory.CREATURE)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(64)
                            .setUpdateInterval(3)
                            .fireImmune()
                            .sized(0.6f, 0.6f));

    public static final DeferredHolder<EntityType<?>, EntityType<GoldenFoxFireballEntity>> GOLDEN_FOX_FIREBALL =
            register("golden_fox_fireball",
                    EntityType.Builder.<GoldenFoxFireballEntity>of(GoldenFoxFireballEntity::new, MobCategory.MISC)
                            .setShouldReceiveVelocityUpdates(false)
                            .setTrackingRange(64)
                            .setUpdateInterval(2)
                            .fireImmune()
                            .sized(0.5f, 0.5f));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        eventBus.addListener(TerrorbeakEntity::init);
        eventBus.addListener(ShadowHandEntity::init);
        eventBus.addListener(ShadowGolemEntity::init);
        eventBus.addListener(ShadowGhostEntity::init);
        eventBus.addListener(WailingShadowGhostEntity::init);
        eventBus.addListener(FriendlyShadowGhostEntity::init);
        eventBus.addListener(BlackBeetleEntity::init);
        eventBus.addListener(FireflyEntity::init);
        eventBus.addListener(SmallStoneSpiritEntity::init);
        eventBus.addListener(PinkChickenEntity::init);
        eventBus.addListener(PinkSlimeEntity::init);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(TERRORBEAK.get(), TerrorbeakEntity.createTerrorbeakAttributes().build());
        event.put(CRAZY_TERRORBEAK.get(), TerrorbeakEntity.createCrazyTerrorbeakAttributes().build());
        event.put(WEAKENESS_TERRORBEAK.get(), TerrorbeakEntity.createWeakenessTerrorbeakAttributes().build());
        event.put(SHADOW_HAND.get(), ShadowHandEntity.createAttributes().build());
        event.put(SHADOW_GOLEM.get(), ShadowGolemEntity.createAttributes().build());
        event.put(SHADOW_GHOST.get(), ShadowGhostEntity.createShadowGhostAttributes().build());
        event.put(SHADOW_SQUEAL_GHOST.get(), ShadowGhostEntity.createShadowSquealGhostAttributes().build());
        event.put(WAILING_SHADOW_GHOST.get(), WailingShadowGhostEntity.createAttributes().build());
        event.put(FRIENDLY_SHADOW_GHOST.get(), FriendlyShadowGhostEntity.createAttributes().build());
        event.put(BLACK_BEETLE.get(), BlackBeetleEntity.createAttributes().build());
        event.put(BLACK_BEETLE_MOTHER.get(), BlackBeetleMotherEntity.createAttributes().build());
        event.put(TERRASWORD_WAVE.get(), TerraswordWaveEntity.createAttributes().build());
        event.put(FOX_FIRE.get(), FoxFireEntity.createAttributes().build());
        event.put(SHAKING_CRYSTAL.get(), ShakingCrystalEntity.createAttributes().build());
        event.put(GOLDEN_FOX.get(), GoldenFoxEntity.createAttributes().build());
        event.put(GOLDEN_FOX_PET.get(), GoldenFoxPetEntity.createAttributes().build());
        event.put(MELT_DREAM_CRYSTAL_ENTITY.get(), MeltDreamCrystalEntityEntity.createAttributes().build());
        event.put(FIREFLY.get(), FireflyEntity.createAttributes().build());
        event.put(SMALL_STONE_SPIRIT.get(), SmallStoneSpiritEntity.createAttributes().build());
        event.put(PINK_CHICKEN.get(), PinkChickenEntity.createAttributes().build());
        event.put(PINK_SLIME.get(), PinkSlimeEntity.createAttributes().build());
    }

    private ModEntities() {
    }
}
