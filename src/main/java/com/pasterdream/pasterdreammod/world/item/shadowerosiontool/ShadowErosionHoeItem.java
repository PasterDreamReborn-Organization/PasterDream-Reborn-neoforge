package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import net.minecraft.world.item.*;

public class ShadowErosionHoeItem extends HoeItem implements ShadowErosionTool {

    public ShadowErosionHoeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowErosionToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.RARE),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
