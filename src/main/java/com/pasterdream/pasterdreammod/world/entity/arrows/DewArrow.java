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

/** 露滴箭：飞行带水珠、对水中生物造成额外伤害、命中后为射击者恢复氧气。 */
public class DewArrow extends Arrow {

    public DewArrow(EntityType<? extends DewArrow> type, Level level) {
        super(type, level);
    }

    public DewArrow(Level level, double x, double y, double z) {
        this(ModEntities.DEW_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public DewArrow(Level level, LivingEntity shooter) {
        this(ModEntities.DEW_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public DewArrow(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        this(ModEntities.DEW_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        this.setPickupItemStack(ammo.copyWithCount(1));
        ArrowBehaviors.applyFiredFromWeapon(this, weapon);
    }

    @Override
    protected float getWaterInertia() {
        return 1.0F;
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.DEW.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        ArrowBehaviors.DEW.onHitEntityPre(this, result);
        super.onHitEntity(result);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        ArrowBehaviors.DEW.doPostHurtEffects(this, target);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.DEW_ARROW.get());
    }
}
