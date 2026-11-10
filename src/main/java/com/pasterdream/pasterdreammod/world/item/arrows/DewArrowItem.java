package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.DewArrow;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehavior;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviorProvider;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/** 露滴箭物品。作为弓 / 弩的弹药，射出 {@link DewArrow}。 */
public class DewArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public DewArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new DewArrow(level, shooter, ammo, weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        DewArrow arrow = new DewArrow(level, pos.x(), pos.y(), pos.z());
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.DEW;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.dew_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.dew_arrow.tooltip2"));
    }
}
