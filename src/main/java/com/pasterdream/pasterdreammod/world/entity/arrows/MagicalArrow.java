package com.pasterdream.pasterdreammod.world.entity.arrows;

import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.TrailArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;

import net.minecraft.world.level.Level;

/**
 * 魔法箭：飞行时追踪附近目标，带蓝色拖尾。
 * <p>
 * 追踪行为由弹药物品通过 {@code ArrowBehaviors.MAGICAL} 提供，并与 {@link TrailArrowEntity} 的通用钩子结合，
 * 因此被强化武器替换为拖尾箭实体时追踪仍然生效。
 */
public class MagicalArrow extends TrailArrowEntity {

    /** 拖尾头端颜色（ARGB，蓝色不透明） */
    private static final int HEAD_COLOR = 0xFF4D9FFF;
    /** 拖尾尾端颜色（ARGB，蓝色透明） */
    private static final int TAIL_COLOR = 0x004D9FFF;

    public MagicalArrow(EntityType<? extends MagicalArrow> type, Level level) {
        super(type, level);
    }

    public MagicalArrow(Level level, double x, double y, double z) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
        this.setPos(x, y, z);
    }

    public MagicalArrow(Level level, LivingEntity shooter) {
        this(ModEntities.MAGICAL_ARROW.get(), level);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.setOwner(shooter);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public MagicalArrow(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        super(ModEntities.MAGICAL_ARROW.get(), shooter, level, ammo.copyWithCount(1), weapon);
        this.setAmmoItem(ammo);
    }

    @Override
    public int trailHeadColor() {
        return HEAD_COLOR;
    }

    @Override
    public int trailTailColor() {
        return TAIL_COLOR;
    }

    @Override
    protected ItemStack getPickupItem() {
        ItemStack ammo = this.getAmmoItem();
        return ammo.isEmpty() ? new ItemStack(ModItems.MAGICAL_ARROW.get()) : ammo.copy();
    }
}
