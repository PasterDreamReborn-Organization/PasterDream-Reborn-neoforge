package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 融梦水晶工具共享逻辑 —— 位于背包中时消耗融梦能量修复耐久。
 */
public final class MeltDreamToolHelper {

    /**
     * 在背包中时每 10 tick 消耗 0.01 融梦能量修复 1 点耐久。
     * 创造模式 / 无消耗标记时不扣能量。
     */
    public static void onInventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        // 仅服务端、玩家、每 10 tick、有损伤
        if (level.isClientSide || !(entity instanceof Player player)) return;
        if (player.tickCount % 10 != 0) return;
        if (stack.getDamageValue() < 1) return;

        double cost = 0.01;
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
        double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, cost);
        if (free || energy.getMeltDreamEnergy() > effectiveCost) {
            if (!free) {
                energy.addMeltDreamEnergy(-effectiveCost);
                player.syncData(ModAttachments.MELT_DREAM_ENERGY);
            }
            // 1.21 的 ItemStack#hurt 不接受负值，直接修复 1 点耐久。
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    public static void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_tool.2"));
    }
}
