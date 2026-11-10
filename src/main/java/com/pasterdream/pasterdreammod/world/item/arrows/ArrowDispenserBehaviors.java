package com.pasterdream.pasterdreammod.world.item.arrows;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * 自定义箭矢的发射器行为注册。
 * <p>
 * 1.21 中箭矢通过 {@code ProjectileItem#asProjectile} 创建弹射物，
 * 这里为各箭矢登记 {@link DispenserBlock#registerProjectileBehavior}，使发射器能射出对应实体并允许回收。
 */
public final class ArrowDispenserBehaviors {

    private ArrowDispenserBehaviors() {
    }

    public static void register() {
        DispenserBlock.registerProjectileBehavior(ModItems.TOXIC_SPORE_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.FRAGDREAM_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.MAGICAL_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.CHARGED_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.BLACKSTONE_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.CHAOTIC_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.BLAST_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.CONGEAL_WIND_ARROW.get());
        DispenserBlock.registerProjectileBehavior(ModItems.DEW_ARROW.get());
    }
}
