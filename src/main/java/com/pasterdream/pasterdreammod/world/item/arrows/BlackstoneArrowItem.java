package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.BlackstoneArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehavior;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviorProvider;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/** 黑石箭物品。作为弓 / 弩的弹药，射出 {@link BlackstoneArrow}。 */
public class BlackstoneArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public BlackstoneArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new BlackstoneArrow(level, shooter, ammo, weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BlackstoneArrow arrow = new BlackstoneArrow(level, pos.x(), pos.y(), pos.z());
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.BLACKSTONE;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, LivingEntity shooter) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                shooter.level().registryAccess().holderOrThrow(Enchantments.INFINITY), bow) > 0;
    }
}
