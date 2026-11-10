package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/** 充能箭：先结算随蓄力缩放的魔法伤害，再走原版物理伤害；飞行带龙息粒子拖尾。 */
public class ChargedArrow extends Arrow {

    public ChargedArrow(EntityType<? extends ChargedArrow> type, Level level) {
        super(type, level);
    }

    public ChargedArrow(Level level, double x, double y, double z) {
        this(ModEntities.CHARGED_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public ChargedArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CHARGED_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public ChargedArrow(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        this(ModEntities.CHARGED_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        this.setPickupItemStack(ammo.copyWithCount(1));
        ArrowBehaviors.applyFiredFromWeapon(this, weapon);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        ArrowBehaviors.CHARGED.onShoot(this, velocity);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.CHARGED.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        ArrowBehaviors.CHARGED.onHitEntityPre(this, result);
        super.onHitEntity(result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHARGED_ARROW.get());
    }
}
