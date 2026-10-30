package com.pasterdream.pasterdreammod.helper.tooltipadder;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class AddToolTip
{
    public static void addTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if(stack.is(ModItems.TITANIUM_HELMET.get()) || stack.is(ModItems.TITANIUM_CHESTPLATE.get())
                || stack.is(ModItems.TITANIUM_LEGGINGS.get()) || stack.is(ModItems.TITANIUM_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.2"));
        }
    }
}
