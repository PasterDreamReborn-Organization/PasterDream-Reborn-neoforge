package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 风行者效果：无需鞘翅即可开启、维持鞘翅飞行。
 * <p>
 * NeoForge 的 {@code ItemStack.canElytraFly / elytraFlightTick} 会转调
 * {@code Item} 上的 {@code IItemExtension} 默认方法；此处给 {@link Item}
 * 添加同名具体方法，覆盖默认的 {@code false} 返回。真实鞘翅由子类方法优先，
 * 不受影响。
 */
@Mixin(Item.class)
public abstract class ItemElytraMixin {

    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity.hasEffect(ModEffects.WIND_RUNNER);
    }

    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return entity.hasEffect(ModEffects.WIND_RUNNER);
    }
}
