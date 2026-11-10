package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.world.entity.arrows.BlastArrow;
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

/** 爆破箭物品。作为弓 / 弩的弹药，射出 {@link BlastArrow}。 */
public class BlastArrowItem extends ArrowItem implements ArrowBehaviorProvider {

    public BlastArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new BlastArrow(level, shooter, ammo, weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BlastArrow arrow = new BlastArrow(level, pos.x(), pos.y(), pos.z());
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }

    @Override
    public ArrowBehavior arrowBehavior() {
        return ArrowBehaviors.BLAST;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip1"));
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip2"));
        list.add(Component.translatable("tooltip.pasterdream.blast_arrow.tooltip3"));
    }
}
