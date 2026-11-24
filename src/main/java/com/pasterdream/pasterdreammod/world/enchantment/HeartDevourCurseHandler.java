package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * 「噬心诅咒」效果处理器：玩家受伤时按原始伤害扣除等量理智，每件附带诅咒的护甲各自叠加。
 * SAN 系统关闭时改为额外受到本次伤害 50% 的生命值伤害（可致死，每件叠加）。
 * <p>
 * 在「庇护」和护甲减伤之前读取本次伤害。
 */
public final class HeartDevourCurseHandler {

    private HeartDevourCurseHandler() {
    }

    public static float applyHeartDevour(LivingEntity entity, float amount) {
        if (!(entity instanceof ServerPlayer player)) return amount;

        int totalLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack armor = player.getItemBySlot(slot);
            if (!armor.isEmpty()) {
                totalLevel += ModEnchantment.getLevel(armor, ModEnchantment.HEART_DEVOUR_ENCHANTMENT);
            }
        }

        if (totalLevel <= 0) return amount;

        if (SanHelper.getIsSanEnabled(player)) {
            double sanLoss = amount * totalLevel;
            if (sanLoss <= 0) return amount;
            SanHelper.addPlayerSanAndSync(player, -sanLoss);
        } else {
            return amount * (1.0F + 0.5F * totalLevel);
        }
        return amount;
    }
}
