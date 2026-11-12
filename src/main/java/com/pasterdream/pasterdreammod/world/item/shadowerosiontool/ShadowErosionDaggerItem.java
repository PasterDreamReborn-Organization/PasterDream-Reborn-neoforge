package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import net.minecraft.world.item.*;

public class ShadowErosionDaggerItem extends SwordItem implements ShadowErosionTool {

    public ShadowErosionDaggerItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowErosionToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.RARE),
                SwordItem.createAttributes(tier, damage, speed)));
    }
}
