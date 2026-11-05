package com.pasterdream.pasterdreammod.capability.san;

import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

public class SanHelper
{
    public static void setPlayerSanAndSync(ServerPlayer player, double sanValue)
    {
        var capability = player.getData(ModAttachments.SAN);
        capability.setSanValue(sanValue);
        player.syncData(ModAttachments.SAN);
    }

    public static void addPlayerSanAndSync(ServerPlayer player, double sanValue)
    {
        var capability = player.getData(ModAttachments.SAN);
        double previous = capability.getSanValue();
        capability.addSanValue(sanValue);
        if (previous == capability.getSanValue()) return;
        player.syncData(ModAttachments.SAN);
    }

    /** 写入最终总变化率（每 tick）并在变化时同步到客户端（仅用于 HUD 箭头）。 */
    public static void setPlayerSanRateAndSync(ServerPlayer player, double sanRate)
    {
        var capability = player.getData(ModAttachments.SAN);
        if (capability.getSanRate() == sanRate) return;
        capability.setSanRate(sanRate);
        player.syncData(ModAttachments.SAN);
    }

    public static double getPlayerSan(ServerPlayer player)
    {
        return player.getData(ModAttachments.SAN).getSanValue();
    }

    public static void setIsSanEnabled(ServerPlayer player, boolean isEnabled)
    {
        var capability = player.getData(ModAttachments.SAN);
        capability.setIsSanEnable(isEnabled);
        player.syncData(ModAttachments.SAN);
    }

    public static boolean getIsSanEnabled(ServerPlayer player)
    {
        return player.getData(ModAttachments.SAN).getIsSanEnabled();
    }

    public static void setPlayerMaxSanAndSync(ServerPlayer player, double maxSanValue)
    {
        var capability = player.getData(ModAttachments.SAN);
        capability.setMaxSanValue(maxSanValue);
        player.syncData(ModAttachments.SAN);
    }

    public static void addPlayerMaxSanAndSync(ServerPlayer player, double maxSanValue)
    {
        var capability = player.getData(ModAttachments.SAN);
        capability.addMaxSanValue(maxSanValue);
        player.syncData(ModAttachments.SAN);
    }

    public static double getPlayerMaxSan(ServerPlayer player)
    {
        return player.getData(ModAttachments.SAN).getMaxSanValue();
    }

    /** 有效理智上限 = 能力字段基础上限 + MAX_SAN_EXTRA 属性的装备修饰器加成。 */
    public static double getPlayerMaxSanEffective(ServerPlayer player)
    {
        double base = getPlayerMaxSan(player);
        AttributeInstance attr = player.getAttribute(ModAttributes.MAX_SAN_EXTRA);
        if (attr != null)
        {
            base += attr.getValue();
        }
        return base;
    }

    /**
     * 收集玩家已装备的 SAN 修正器（护甲槽 + Curios 饰品中实现 {@link ISanModifier} 的物品）。
     */
    public static List<ISanModifier> getEquippedSanModifiers(Player player)
    {
        List<ISanModifier> modifiers = new ArrayList<>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.getItem() instanceof ISanModifier modifier)
            {
                modifiers.add(modifier);
            }
        }
        CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                handler.findCurios(stack -> stack.getItem() instanceof ISanModifier)
                        .forEach(slotResult -> modifiers.add((ISanModifier) slotResult.stack().getItem())));
        return modifiers;
    }
}
