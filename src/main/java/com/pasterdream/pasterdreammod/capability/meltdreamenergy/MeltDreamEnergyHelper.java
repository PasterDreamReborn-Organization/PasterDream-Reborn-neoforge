package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public class MeltDreamEnergyHelper
{
    public static void setPlayerMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        var capability = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        capability.setMeltDreamEnergy(meltDreamEnergyValue);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static void addPlayerMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        var capability = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        if (meltDreamEnergyValue > 0)
        {
            double effectiveMax = getPlayerMaxMeltDreamEnergyEffective(player);
            double current = capability.getMeltDreamEnergy();
            capability.setMeltDreamEnergy(Math.min(current + meltDreamEnergyValue, effectiveMax));
        }
        else if (capability.getIsOrNotNeedConsumeDreamEnergy() && !player.isCreative())
        {
            capability.addMeltDreamEnergy(-getEffectiveConsumeCost(capability, -meltDreamEnergyValue));
        }
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static double getPlayerMeltDreamEnergy(Player player)
    {
        return player.getData(ModAttachments.MELT_DREAM_ENERGY).getMeltDreamEnergy();
    }

    public static void setPlayerMeltDreamEnergyIsNeed(ServerPlayer player, boolean isNeed)
    {
        player.getData(ModAttachments.MELT_DREAM_ENERGY).setIsOrNotNeedConsumeDreamEnergy(isNeed);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static boolean getPlayerMeltDreamEnergyIsNeed(ServerPlayer player)
    {
        return player.getData(ModAttachments.MELT_DREAM_ENERGY).getIsOrNotNeedConsumeDreamEnergy();
    }

    public static void setPlayerMeltDreamEnergyConsumeDoubled(ServerPlayer player, boolean isConsumeDoubled)
    {
        player.getData(ModAttachments.MELT_DREAM_ENERGY).setIsConsumeDoubled(isConsumeDoubled);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static void setPlayerMaxMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getData(ModAttachments.MELT_DREAM_ENERGY).setMaxMeltDreamEnergy(meltDreamEnergyValue);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static void addPlayerMaxMeltDreamEnergyAndSync(ServerPlayer player, double meltDreamEnergyValue)
    {
        player.getData(ModAttachments.MELT_DREAM_ENERGY).addMaxMeltDreamEnergy(meltDreamEnergyValue);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
    }

    public static double getPlayerMaxMeltDreamEnergy(ServerPlayer player)
    {
        return player.getData(ModAttachments.MELT_DREAM_ENERGY).getMaxMeltDreamEnergy();
    }

    /** 依据「消耗翻倍」减益计算实际消耗量（处于该减益下基础消耗 ×2）。 */
    public static double getEffectiveConsumeCost(IMeltDreamEnergy energy, double baseCost)
    {
        return energy.isConsumeDoubled() ? baseCost * 2.0 : baseCost;
    }

    /** 有效融梦能量上限 = 能力字段基础上限 + MAX_MELT_DREAM_ENERGY_EXTRA 属性的装备修饰器加成。 */
    public static double getPlayerMaxMeltDreamEnergyEffective(ServerPlayer player)
    {
        double base = getPlayerMaxMeltDreamEnergy(player);
        AttributeInstance attr = player.getAttribute(ModAttributes.MAX_MELT_DREAM_ENERGY_EXTRA);
        if (attr != null)
        {
            base += attr.getValue();
        }
        return base;
    }
}
