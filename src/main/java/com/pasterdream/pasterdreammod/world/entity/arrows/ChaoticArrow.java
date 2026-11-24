package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/** 混沌箭：飞行带黑色粒子拖尾，命中施加混乱、概率恢复理智、对暗影生物造成额外伤害。 */
public class ChaoticArrow extends Arrow {

    public ChaoticArrow(EntityType<? extends ChaoticArrow> type, Level level) {
        super(type, level);
    }

    public ChaoticArrow(Level level, double x, double y, double z) {
        this(ModEntities.CHAOTIC_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public ChaoticArrow(Level level, LivingEntity shooter) {
        this(ModEntities.CHAOTIC_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public ChaoticArrow(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        this(ModEntities.CHAOTIC_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        ItemStack pickup = ammo.copyWithCount(1);
        if (pickup.remove(DataComponents.INTANGIBLE_PROJECTILE) != null) {
            this.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        this.setPickupItemStack(pickup);
        ArrowBehaviors.applyFiredFromWeapon(this, weapon);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        ArrowBehaviors.CHAOTIC.onShoot(this, velocity);
        super.shoot(x, y, z, velocity, inaccuracy);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.CHAOTIC.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        ArrowBehaviors.CHAOTIC.onHitEntityPre(this, result);
        super.onHitEntity(result);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        ArrowBehaviors.CHAOTIC.doPostHurtEffects(this, target);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHAOTIC_ARROW.get());
    }
}
