package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class IceShadowCurioItem extends Item implements ICurioItem {
    public IceShadowCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.master()));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_curio.0"));
        tooltip.add(Component.translatable("tooltip.pasterdream.ice_shadow_curio.1"));
    }
}
