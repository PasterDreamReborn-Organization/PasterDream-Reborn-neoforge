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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/** 爆破箭：飞行带岩浆粒子拖尾，命中实体或物块后，造成 2 级爆炸（不生成火焰）。 */
public class BlastArrow extends Arrow {

    public BlastArrow(EntityType<? extends BlastArrow> type, Level level) {
        super(type, level);
    }

    public BlastArrow(Level level, double x, double y, double z) {
        this(ModEntities.BLAST_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public BlastArrow(Level level, LivingEntity shooter) {
        this(ModEntities.BLAST_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public BlastArrow(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        this(ModEntities.BLAST_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        this.setPickupItemStack(ammo.copyWithCount(1));
        ArrowBehaviors.applyFiredFromWeapon(this, weapon);
    }

    @Override
    public void tick() {
        super.tick();
        ArrowBehaviors.BLAST.onTick(this, this.inGround);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        ArrowBehaviors.BLAST.onHitEntityPost(this, result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        ArrowBehaviors.BLAST.onHitBlock(this, result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BLAST_ARROW.get());
    }
}
