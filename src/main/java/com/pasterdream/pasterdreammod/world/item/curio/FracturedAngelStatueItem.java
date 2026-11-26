package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/** 折翼天使雕像：受击后概率获得短暂无敌，佩戴期间免疫摔落伤害。 */
public class FracturedAngelStatueItem extends Item implements ICurioItem {
    public FracturedAngelStatueItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.epic()));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect1",
                formatPercent(Config.FRACTURED_ANGEL_STATUE_INVULNERABLE_CHANCE.get()),
                formatSeconds(Config.FRACTURED_ANGEL_STATUE_INVULNERABLE_TICKS.get())));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect3"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.effect4"));
        list.add(Component.translatable("tooltip.pasterdream.fractured_angel_statue.lore"));
    }

    private static String formatPercent(double chance) {
        double pct = chance * 100.0;
        return pct == Math.floor(pct) ? String.valueOf((long) pct) : String.valueOf(pct);
    }

    private static String formatSeconds(int ticks) {
        double seconds = ticks / 20.0;
        return seconds == Math.floor(seconds) ? String.valueOf((long) seconds) : String.valueOf(seconds);
    }
}
