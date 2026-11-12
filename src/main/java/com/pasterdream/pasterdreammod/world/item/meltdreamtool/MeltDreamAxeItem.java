package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import net.minecraft.world.item.*;

public class MeltDreamAxeItem extends AxeItem implements MeltDreamTool {

    public MeltDreamAxeItem(Tier tier, float damage, float speed, Properties properties) {
        super(tier, properties.fireResistant().attributes(DiggerItem.createAttributes(tier, damage, speed)));
    }
}
