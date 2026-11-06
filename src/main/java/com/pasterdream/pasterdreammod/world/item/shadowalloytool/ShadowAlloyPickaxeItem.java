package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class ShadowAlloyPickaxeItem extends PickaxeItem implements ShadowAlloyTool {

    public ShadowAlloyPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowAlloyToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.EPIC),
                DiggerItem.createAttributes(tier, damage, speed)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        ShadowAlloyToolHelper.onInventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        ShadowAlloyToolHelper.appendHoverText(stack, context, tooltip, flag);
    }
}
