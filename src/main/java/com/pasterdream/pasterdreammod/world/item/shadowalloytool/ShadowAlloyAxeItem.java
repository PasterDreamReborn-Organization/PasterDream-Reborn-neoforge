package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import net.minecraft.world.item.*;

public class ShadowAlloyAxeItem extends AxeItem implements ShadowAlloyTool {

    public ShadowAlloyAxeItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, ShadowAlloyToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.EPIC),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
