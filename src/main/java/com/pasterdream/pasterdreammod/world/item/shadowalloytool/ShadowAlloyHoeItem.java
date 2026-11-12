package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import net.minecraft.world.item.*;

public class ShadowAlloyHoeItem extends HoeItem implements ShadowAlloyTool {

    public ShadowAlloyHoeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowAlloyToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.EPIC),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
