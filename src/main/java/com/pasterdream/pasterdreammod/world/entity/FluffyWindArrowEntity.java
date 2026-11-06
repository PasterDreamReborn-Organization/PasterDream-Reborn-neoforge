package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/** 萦风弓强化箭：无重力、命中飘浮、青绿色三棱柱拖尾与飞行时长兜底。 */
public class FluffyWindArrowEntity extends Arrow {
    private static final int HEAD_COLOR = 0xFF3FE8C4;
    private static final int TAIL_COLOR = 0x0032C79E;
    private static final int MAX_TRAIL_POINTS = 10;
    private static final double MIN_TRAIL_SPEED = 0.001;
    private static final double MIN_SAMPLE_DISTANCE_SQR = 1.0E-6;
    private static final int GROUND_TRAIL_LIFETIME = 20;
    private static final int MAX_FLIGHT_TICKS = 200;
    private final List<Vec3> clientTrail = new ArrayList<>();
    private int flightTicks;
    private boolean channelingLightning;
    private int channelingFlameLevel;

    public FluffyWindArrowEntity(EntityType<? extends FluffyWindArrowEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public FluffyWindArrowEntity(Level level, LivingEntity shooter, ItemStack ammo) {
        this(ModEntities.FLUFFY_WIND_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        this.setPickupItemStack(ammo.copyWithCount(1));
        if (shooter instanceof Player) this.pickup = AbstractArrow.Pickup.ALLOWED;
        PotionContents contents = ammo.get(DataComponents.POTION_CONTENTS);
        if (contents != null) contents.forEachEffect(this::addEffect);
    }

    public void enableChannelingLightning(int flameLevel) {
        this.channelingLightning = true;
        this.channelingFlameLevel = Math.max(0, flameLevel);
    }

    public boolean hasChannelingLightning() {
        return this.channelingLightning;
    }

    public int channelingFlameLevel() {
        return this.channelingFlameLevel;
    }

    public int trailHeadColor() {
        return HEAD_COLOR;
    }

    public int trailTailColor() {
        return TAIL_COLOR;
    }

    public List<Vec3> getClientTrail() {
        return this.clientTrail;
    }

    public boolean isArrowInGround() {
        return this.inGround;
    }

    public int getArrowInGroundTime() {
        return this.inGroundTime;
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) this.updateClientTrail();
        super.tick();
        if (!this.level().isClientSide() && !this.inGround && ++this.flightTicks > MAX_FLIGHT_TICKS) this.discard();
    }

    private void updateClientTrail() {
        if (this.inGround) {
            if (this.inGroundTime > GROUND_TRAIL_LIFETIME) this.clientTrail.clear();
            return;
        }
        if (this.getDeltaMovement().length() < MIN_TRAIL_SPEED) return;
        Vec3 current = this.position();
        if (this.clientTrail.isEmpty()
                || this.clientTrail.get(this.clientTrail.size() - 1).distanceToSqr(current) > MIN_SAMPLE_DISTANCE_SQR) {
            this.clientTrail.add(current);
            while (this.clientTrail.size() > MAX_TRAIL_POINTS) this.clientTrail.remove(0);
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (!this.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 0, false, true));
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("WindFlightTicks", this.flightTicks);
        tag.putBoolean("WindChanneling", this.channelingLightning);
        tag.putInt("WindFlame", this.channelingFlameLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.flightTicks = tag.getInt("WindFlightTicks");
        this.channelingLightning = tag.getBoolean("WindChanneling");
        this.channelingFlameLevel = tag.getInt("WindFlame");
    }
}
