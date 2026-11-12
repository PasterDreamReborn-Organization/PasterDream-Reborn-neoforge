package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import net.minecraft.world.item.*;

public class ShadowAlloyShovelItem extends ShovelItem implements ShadowAlloyTool {

    public ShadowAlloyShovelItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, ShadowAlloyToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.EPIC),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
