package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class ShadowErosionDaggerItem extends SwordItem implements ShadowErosionTool {

    public ShadowErosionDaggerItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowErosionToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.RARE),
                SwordItem.createAttributes(tier, damage, speed)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        ShadowErosionToolHelper.appendHoverText(stack, context, tooltip, flag);
    }
}
