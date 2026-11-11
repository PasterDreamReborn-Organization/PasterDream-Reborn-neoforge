package com.pasterdream.pasterdreammod.world.entity;

import com.mojang.logging.LogUtils;
import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TerraswordWaveEntity extends PathfinderMob {

    private int lifeTicks = 0;
    private static final int BLOCK_HIT_LINGER_TICKS = 10;
    private int blockHitLifeTick = -1;
    public static final int MAX_LIFE_TICKS = 32;
    private static final double COLLISION_RADIUS_BASE = 2.5;
    private static final double COLLISION_HEIGHT = 5.0;
    private static final double COLLISION_RADIUS_SWEEPING_BONUS = 0.5;
    private static final int MAX_SWEEPING_EDGE_LEVEL = 3;
    private static final int PARTICLE_COUNT_BASE = 8;
    private static final int PARTICLE_COUNT_SWEEPING_BONUS = 2;
    private static final double PARTICLE_SPREAD_BASE = 0.3;
    private static final double PARTICLE_SPREAD_SWEEPING_BONUS = 0.3;
    private static final double PARTICLE_VELOCITY = 0.1;
    private static final double TRAIL_PARTICLE_VIEW_DISTANCE = 96.0; // 覆盖远端剑气，避免向整张地图广播
    private static final double BASE_DAMAGE_OFFSET = 2.0;
    private static final float SMITE_BANE_MULTIPLIER = 2.5f;
    private static final double PENETRATION_DECAY = 0.75;
    private static final float MIN_DAMAGE = 1.0f;
    private static final int FIRE_ASPECT_TICK_MULTIPLIER = 4;
    private static final float KNOCKBACK_MULTIPLIER = 0.6f;
    private static final double KNOCKBACK_Y = 0.2;
    private static final EntityDataAccessor<Float> DATA_ROLL_ANGLE =
            SynchedEntityData.defineId(TerraswordWaveEntity.class, EntityDataSerializers.FLOAT);
    public static final double COLLISION_HALF_WIDTH = COLLISION_RADIUS_BASE / 2d;
    public static final double COLLISION_HALF_HEIGHT = COLLISION_HEIGHT / 2d;
    public static final double COLLISION_HALF_DEPTH = COLLISION_RADIUS_BASE / 2d;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final boolean DEBUG_LOG = false;
    private static final double DIRECTION_DEVIATION_WARN_DEG = 0.5;
    private final Set<UUID> hitEntities = new HashSet<>();
    private final Set<UUID> reflectedProjectiles = new HashSet<>();
    private int penetrationCount = 0;
    private double initialSpeed = -1.0;
    @Nullable
    private Vec3 travelDir;
    @Nullable
    private Player owner;
    @Nullable
    private UUID ownerUUID;

    public TerraswordWaveEntity(EntityType<TerraswordWaveEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ROLL_ANGLE, 0.0f);
    }

    public float getRollAngle() {
        return this.entityData.get(DATA_ROLL_ANGLE);
    }

    public void setRollAngle(float rollAngle) {
        this.entityData.set(DATA_ROLL_ANGLE, rollAngle);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double range = COLLISION_HEIGHT * 64.0 * getViewScale();
        return distance < range * range;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(COLLISION_HALF_HEIGHT + 3.5);
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        }
        if (compound.contains("RollAngle")) {
            this.setRollAngle(compound.getFloat("RollAngle"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("Owner", ownerUUID);
        }
        compound.putFloat("RollAngle", this.getRollAngle());
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Level level = this.level();
        if (!level.isClientSide()) {
            lifeTicks++;

            if (blockHitLifeTick >= 0 && lifeTicks - blockHitLifeTick >= BLOCK_HIT_LINGER_TICKS) {
                this.discard();
                return;
            }

            if (initialSpeed < 0.0) {
                Vec3 motion = this.getDeltaMovement();
                initialSpeed = motion.length();
                travelDir = motion.lengthSqr() > 1.0E-7 ? motion.normalize() : this.getLookAngle();
            }
            double currentSpeed = Math.max(0.0, initialSpeed * (1.0 - (double) lifeTicks / MAX_LIFE_TICKS));
            if (travelDir != null) {
                this.setDeltaMovement(travelDir.scale(currentSpeed));
            }

            CompoundTag data = this.getPersistentData();
            int sweepingEdge = Math.min(data.getInt("sweeping_edge"), MAX_SWEEPING_EDGE_LEVEL);

            int particleCount = PARTICLE_COUNT_BASE + sweepingEdge * PARTICLE_COUNT_SWEEPING_BONUS;
            double particleSpread = PARTICLE_SPREAD_BASE + sweepingEdge * PARTICLE_SPREAD_SWEEPING_BONUS;
            double particleVerticalSpread = COLLISION_HEIGHT / 2;
            double radius = COLLISION_RADIUS_BASE / 2d + sweepingEdge * COLLISION_RADIUS_SWEEPING_BONUS;
            Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ());

            Vec3 moveDir = getMoveDirection(1.0f);
            Vec3 heightAxis = getWaveHeightAxis(moveDir);
            Vec3 side = moveDir.cross(heightAxis).normalize();
            final Vec3 finalSide = side;
            final Vec3 finalHeightAxis = heightAxis;
            final Vec3 finalMoveDir = moveDir;

            if (level instanceof ServerLevel serverLevel) {
                spawnOrientedParticles(serverLevel, center, finalSide, finalHeightAxis, finalMoveDir,
                        particleCount, particleSpread, particleVerticalSpread, currentSpeed);
            }

            double pasterAtk = data.getDouble("paster_atk");
            int smite = data.getInt("smite");
            int bane = data.getInt("bane_of_arthropods");
            int fireAspect = data.getInt("fire_aspect");
            int knockback = data.getInt("knockback");

            double halfWidth = radius;
            double halfHeight = COLLISION_HEIGHT / 2;
            double halfDepth = radius;
            double searchRange = Math.max(halfWidth, Math.max(halfHeight, halfDepth));
            Vec3 movement = this.getDeltaMovement();
            Vec3 nextCenter = center.add(movement);
            double extentX = Math.abs(side.x) * halfWidth + Math.abs(heightAxis.x) * halfHeight
                    + Math.abs(moveDir.x) * halfDepth;
            double extentY = Math.abs(side.y) * halfWidth + Math.abs(heightAxis.y) * halfHeight
                    + Math.abs(moveDir.y) * halfDepth;
            double extentZ = Math.abs(side.z) * halfWidth + Math.abs(heightAxis.z) * halfHeight
                    + Math.abs(moveDir.z) * halfDepth;
            AABB attackBox = new AABB(
                    Math.min(center.x, nextCenter.x) - extentX,
                    Math.min(center.y, nextCenter.y) - extentY,
                    Math.min(center.z, nextCenter.z) - extentZ,
                    Math.max(center.x, nextCenter.x) + extentX,
                    Math.max(center.y, nextCenter.y) + extentY,
                    Math.max(center.z, nextCenter.z) + extentZ).inflate(1.0E-6);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                    attackBox, e -> true)
                    .stream()
                    .filter(e -> intersectsSweptOBB(e.getBoundingBox(), center, movement,
                            finalSide, finalHeightAxis, finalMoveDir,
                            halfWidth, halfHeight, halfDepth))
                    .sorted(Comparator.comparingDouble(e -> e.distanceToSqr(center)))
                    .toList();
            Player owner = resolveOwner();
            for (LivingEntity target : entities) {
                if (target != owner && !(target instanceof TerraswordWaveEntity)
                        && !MinionFilterHelper.isOwnedMinion(target, owner)
                        && !hitEntities.contains(target.getUUID())) {
                    float damage = (float) (BASE_DAMAGE_OFFSET + pasterAtk);
                    if (smite > 0 && target.getType().is(EntityTypeTags.UNDEAD)) {
                        damage += smite * SMITE_BANE_MULTIPLIER;
                    }
                    if (bane > 0 && target.getType().is(EntityTypeTags.ARTHROPOD)) {
                        damage += bane * SMITE_BANE_MULTIPLIER;
                    }
                    if (data.getBoolean("ignore_iframe")) {
                        target.invulnerableTime = 0;
                    }
                    float finalDamage = (float) (damage * Math.pow(PENETRATION_DECAY, penetrationCount));
                    if (owner == null || !target.hurt(
                            new DamageSource(this.damageSources().playerAttack(owner).typeHolder(), this, owner), finalDamage)) {
                        continue;
                    }
                    hitEntities.add(target.getUUID());
                    penetrationCount++;
                    if (fireAspect > 0) {
                        target.igniteForSeconds(fireAspect * FIRE_ASPECT_TICK_MULTIPLIER);
                    }
                    if (knockback > 0) {
                        Vec3 kb = target.position().subtract(center).normalize().scale(knockback * KNOCKBACK_MULTIPLIER);
                        target.push(kb.x, KNOCKBACK_Y, kb.z);
                    }
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                                target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(),
                                3, 0.3, 0.3, 0.3, 0.1);
                    }
                    if ((float) (damage * Math.pow(PENETRATION_DECAY, penetrationCount)) <= MIN_DAMAGE) {
                        if (level instanceof ServerLevel sl) {
                            sl.sendParticles(ParticleTypes.EXPLOSION,
                                    this.getX(), this.getY(), this.getZ(),
                                    3, 0.3, 0.3, 0.3, 0.1);
                        }
                        this.discard();
                        return;
                    }
                }
            }

            List<Projectile> projectiles = level.getEntitiesOfClass(Projectile.class,
                    attackBox, e -> true)
                    .stream()
                    .filter(p -> !reflectedProjectiles.contains(p.getUUID())
                            && intersectsSweptOBB(p.getBoundingBox(), center, movement,
                                    finalSide, finalHeightAxis, finalMoveDir,
                                    halfWidth, halfHeight, halfDepth))
                    .toList();
            for (Projectile projectile : projectiles) {
                Entity projOwner = projectile.getOwner();
                if (owner != null && projOwner != null && projOwner.getUUID().equals(owner.getUUID())) {
                    continue;
                }
                reflectedProjectiles.add(projectile.getUUID());
                projectile.setDeltaMovement(projectile.getDeltaMovement().reverse());
                if (owner != null) {
                    projectile.setOwner(owner);
                }
                if (projectile instanceof AbstractArrow arrow) {
                    arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                }
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CRIT,
                            projectile.getX(), projectile.getY(), projectile.getZ(),
                            8, 0.3, 0.3, 0.3, 0.1);
                }
            }

            if (lifeTicks >= MAX_LIFE_TICKS) {
                if (!hitEntities.isEmpty()) {
                    this.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 0.7f, 1.0f);
                }
                this.discard();
            }
        }
        this.refreshDimensions();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn,
                                   net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() > 1.0E-8) {
            double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
            this.setYRot((float) (Math.atan2(motion.z, motion.x) * 180.0 / Math.PI) - 90.0F);
            this.setXRot((float) (-(Math.atan2(motion.y, horizontal) * 180.0 / Math.PI)));
        }
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.yBodyRot = this.getYRot();
        this.yBodyRotO = this.yBodyRot;
        this.yHeadRot = this.getYRot();
        this.yHeadRotO = this.yHeadRot;

        if (DEBUG_LOG && !this.level().isClientSide()) {
            Vec3 moveDir = this.getMoveDirection(1.0f);
            double deviation = travelDir == null ? 0.0
                    : Math.toDegrees(Math.acos(Math.max(-1.0, Math.min(1.0, moveDir.dot(travelDir)))));
            if (lifeTicks <= 1 || deviation > DIRECTION_DEVIATION_WARN_DEG) {
                LOGGER.info("[TerraswordWave] id={} tick={} deviation={} delta={} moveDir={} travelDir={} view={} yRot={} xRot={} roll={} heightAxis={}",
                        this.getId(), lifeTicks, fmt(deviation), fmt(motion), fmt(moveDir),
                        travelDir == null ? "null" : fmt(travelDir), fmt(this.getViewVector(1.0f)),
                        fmt(this.getYRot()), fmt(this.getXRot()), fmt(this.getRollAngle()), fmt(this.getWaveHeightAxis(moveDir)));
            }
        }
    }

    private static String fmt(Vec3 vec) {
        return String.format("(%.3f, %.3f, %.3f)", vec.x, vec.y, vec.z);
    }

    private static String fmt(float value) {
        return String.format("%.2f", value);
    }

    private static String fmt(double value) {
        return String.format("%.3f", value);
    }

    @Override
    public void travel(Vec3 input) {
        if (this.level().isClientSide()) {
            return;
        }
        Vec3 delta = this.getDeltaMovement();
        if (delta.lengthSqr() < 1.0E-12) {
            return;
        }
        Vec3 from = this.position();
        Vec3 to = from.add(delta);
        if (blockHitLifeTick < 0 && !this.getPersistentData().getBoolean("pierce_blocks") && hitsSolidAlong(from, to)) {
            blockHitLifeTick = lifeTicks;
        }
        this.setPos(to.x, to.y, to.z);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int lerpSteps) {
        super.lerpTo(x, y, z, yRot, xRot, 1);
    }

    private boolean hitsSolidAlong(Vec3 from, Vec3 to) {
        double distance = from.distanceTo(to);
        int steps = Math.max(1, (int) Math.ceil(distance / 0.5));
        for (int i = 1; i <= steps; i++) {
            Vec3 point = from.lerp(to, (double) i / steps);
            BlockPos pos = BlockPos.containing(point);
            if (!this.level().getBlockState(pos).getCollisionShape(this.level(), pos).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5);
        builder = builder.add(Attributes.MAX_HEALTH, 1);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 10);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 10);
        builder = builder.add(Attributes.FLYING_SPEED, 0.5);
        return builder;
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerUUID = player.getUUID();
    }

    public Vec3 getMoveDirection(float partialTick) {
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() > 1.0E-8) {
            return motion.normalize();
        }
        Vec3 look = this.getViewVector(partialTick);
        return look.lengthSqr() > 1.0E-8 ? look.normalize() : new Vec3(0, 0, 1);
    }

    public static Vec3 getWaveSide(Vec3 moveDir) {
        Vec3 side = moveDir.cross(new Vec3(0, 1, 0));
        if (side.lengthSqr() < 1.0E-7) {
            side = new Vec3(1, 0, 0);
        }
        return side.normalize();
    }

    public Vec3 getWaveHeightAxis(Vec3 moveDir) {
        Vec3 side = getWaveSide(moveDir);
        Vec3 vertical = side.cross(moveDir);
        if (vertical.lengthSqr() < 1.0E-7) {
            vertical = new Vec3(0, 1, 0);
        } else {
            vertical = vertical.normalize();
        }
        return rotateAroundAxis(vertical, moveDir, Math.toRadians(this.getRollAngle()));
    }

    public static Vec3 rotateAroundAxis(Vec3 v, Vec3 axis, double radians) {
        double sin = Math.sin(radians);
        double cos = Math.cos(radians);
        double k = 1.0 - cos;
        double a = axis.x;
        double b = axis.y;
        double c = axis.z;
        double x = v.x * (a * a * k + cos) + v.y * (a * b * k - c * sin) + v.z * (a * c * k + b * sin);
        double y = v.x * (b * a * k + c * sin) + v.y * (b * b * k + cos) + v.z * (b * c * k - a * sin);
        double z = v.x * (c * a * k - b * sin) + v.y * (c * b * k + a * sin) + v.z * (c * c * k + cos);
        return new Vec3(x, y, z);
    }

    /** Check the entire tick's blade sweep against a target AABB using separating axes. */
    private static boolean intersectsSweptOBB(AABB box, Vec3 center, Vec3 movement,
                                               Vec3 side, Vec3 heightAxis, Vec3 moveDir,
                                               double halfWidth, double halfHeight, double halfDepth) {
        Vec3[] worldAxes = {new Vec3(1, 0, 0), new Vec3(0, 1, 0), new Vec3(0, 0, 1)};
        Vec3[] bladeAxes = {side, heightAxis, moveDir};
        Vec3[] axes = new Vec3[15];
        System.arraycopy(worldAxes, 0, axes, 0, 3);
        System.arraycopy(bladeAxes, 0, axes, 3, 3);
        int index = 6;
        for (Vec3 worldAxis : worldAxes) {
            for (Vec3 bladeAxis : bladeAxes) {
                axes[index++] = worldAxis.cross(bladeAxis);
            }
        }

        Vec3 boxOffset = box.getCenter().subtract(center);
        double boxHalfX = box.getXsize() / 2.0;
        double boxHalfY = box.getYsize() / 2.0;
        double boxHalfZ = box.getZsize() / 2.0;
        double enter = 0.0;
        double exit = 1.0;
        for (Vec3 axis : axes) {
            if (axis.lengthSqr() < 1.0E-12) {
                continue;
            }
            double separation = boxOffset.dot(axis);
            double reach = Math.abs(side.dot(axis)) * halfWidth
                    + Math.abs(heightAxis.dot(axis)) * halfHeight
                    + Math.abs(moveDir.dot(axis)) * halfDepth
                    + Math.abs(axis.x) * boxHalfX + Math.abs(axis.y) * boxHalfY + Math.abs(axis.z) * boxHalfZ;
            double speed = movement.dot(axis);
            if (Math.abs(speed) < 1.0E-12) {
                if (Math.abs(separation) > reach + 1.0E-9) {
                    return false;
                }
                continue;
            }
            double first = (separation - reach) / speed;
            double last = (separation + reach) / speed;
            enter = Math.max(enter, Math.min(first, last));
            exit = Math.min(exit, Math.max(first, last));
            if (enter > exit) {
                return false;
            }
        }
        return true;
    }

    private void spawnOrientedParticles(ServerLevel serverLevel, Vec3 center, Vec3 side, Vec3 heightAxis, Vec3 moveDir,
                                        int count, double spread, double verticalSpread, double trailLength) {
        for (int i = 0; i < count; i++) {
            double u = (serverLevel.random.nextDouble() - 0.5) * 2.0;
            double stratum = (i + serverLevel.random.nextDouble()) / count;
            double v = stratum * 2.0 - 1.0;
            double w = serverLevel.random.nextDouble();
            Vec3 offset = side.scale(u * spread)
                    .add(heightAxis.scale(v * verticalSpread))
                    .add(moveDir.scale(-w * trailLength));
            Vec3 pos = center.add(offset);
            for (ServerPlayer player : serverLevel.players()) {
                if (player.distanceToSqr(pos) > TRAIL_PARTICLE_VIEW_DISTANCE * TRAIL_PARTICLE_VIEW_DISTANCE) {
                    continue;
                }
                sendForcedTrailParticle(serverLevel, player, ModParticleTypes.SPORE_PARTICLE.get(), pos);
                sendForcedTrailParticle(serverLevel, player, ModParticleTypes.TERRASWORD_WAVE_PARTICLE.get(), pos);
            }
        }
    }

    private static void sendForcedTrailParticle(ServerLevel level, ServerPlayer player,
                                                ParticleOptions particle, Vec3 pos) {
        // true 同时放宽原版 32 格发送限制，并让客户端绕过“最少粒子”过滤
        level.sendParticles(player, particle, true,
                pos.x, pos.y, pos.z, 1, 0, 0, 0, PARTICLE_VELOCITY);
    }

    @Nullable
    private Player resolveOwner() {
        if (owner != null && !owner.isRemoved()) {
            return owner;
        }
        if (ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(ownerUUID);
            if (entity instanceof Player player) {
                this.owner = player;
                return player;
            }
        }
        return null;
    }
}
