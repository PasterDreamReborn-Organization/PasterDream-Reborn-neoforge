package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

public class ThundercloudEntity extends AbstractThundercloudEntity {

    public ThundercloudEntity(EntityType<ThundercloudEntity> type, Level world) {
        super(type, world);
        xpReward = 7;
    }

    @Override
    protected String getDefaultTexture() {
        return "thundercloud";
    }

    @Override
    protected float getDimensionScale() {
        return 2f;
    }

    @Override
    protected float getLightningDamage() {
        return 7;
    }

    @Override
    protected double getLightningAttackChance() {
        return 0.012;
    }

    @Override
    protected boolean isFireImmune() {
        return false;
    }

    public static void init(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.THUNDERCLOUD.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getDifficulty() != Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(world, pos, random)
                                && Mob.checkMobSpawnRules(entityType, world, reason, pos, random),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.FLYING_SPEED, 0.15);
    }
}
