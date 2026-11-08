package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class MeltDreamPickaxeItem extends PickaxeItem implements MeltDreamTool {

    public MeltDreamPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.fireResistant().attributes(DiggerItem.createAttributes(tier, damage, speed)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        MeltDreamToolHelper.onInventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        MeltDreamToolHelper.appendHoverText(stack, context, tooltip, flag);
    }
}
