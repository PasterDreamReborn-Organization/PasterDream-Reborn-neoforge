package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import net.minecraft.world.item.*;

public class ShadowAlloyPickaxeItem extends PickaxeItem implements ShadowAlloyTool {

    public ShadowAlloyPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, ShadowAlloyToolHelper.withSanVariability(properties.fireResistant().rarity(Rarity.EPIC),
                DiggerItem.createAttributes(tier, damage, speed)));
    }
}
