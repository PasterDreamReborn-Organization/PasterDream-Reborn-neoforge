package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

/**
 * 暴露 {@link AbstractArrow#setPierceLevel(byte)}，供暗影合金弩手动设置穿透等级。
 * <p>1.21 中该方法为 private，无法在物品类中直接调用；穿透附魔的数据驱动结算
 * 依赖武器实体类型位于 {@code #minecraft:arrows}，自定义箭矢无法享受，故手动设置。
 * <p>同时暴露 {@code firedFromWeapon}，供自定义箭矢在保留自身实体类型的同时
 * 接入 1.21 的附魔结算（力量 / 冲击 / 火矢）。
 */
@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {

    @Invoker("setPierceLevel")
    void pasterdream$setPierceLevel(byte level);

    @Accessor("firedFromWeapon")
    void pasterdream$setFiredFromWeapon(@Nullable ItemStack stack);
}
