package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import net.minecraft.world.item.*;

public class ShadowErosionShovelItem extends ShovelItem implements ShadowErosionTool {

    public ShadowErosionShovelItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, ShadowErosionToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.RARE),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
