package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import net.minecraft.world.item.*;

public class ShadowErosionPickaxeItem extends PickaxeItem implements ShadowErosionTool {

    public ShadowErosionPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowErosionToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.RARE),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
