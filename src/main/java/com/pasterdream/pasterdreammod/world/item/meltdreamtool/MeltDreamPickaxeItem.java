package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import net.minecraft.world.item.*;

public class MeltDreamPickaxeItem extends PickaxeItem implements MeltDreamTool {

    public MeltDreamPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.fireResistant().attributes(DiggerItem.createAttributes(tier, damage, speed)));
    }
}
