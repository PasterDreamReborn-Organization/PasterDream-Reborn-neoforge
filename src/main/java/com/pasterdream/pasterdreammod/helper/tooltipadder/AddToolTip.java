package com.pasterdream.pasterdreammod.helper.tooltipadder;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

public class AddToolTip
{
    public static void addTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        // 自定义稀有度自动品质行（无需在物品内手动添加）
        Rarity rarity = stack.getRarity();
        if (ModRarities.tierOf(rarity) != null) {
            tooltip.add(1, ModRarities.qualityTooltip(rarity));
        }

        if(stack.is(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_crystal_fragment"));
        }

        if(stack.is(ModItems.TITANIUM_HELMET.get()) || stack.is(ModItems.TITANIUM_CHESTPLATE.get())
                || stack.is(ModItems.TITANIUM_LEGGINGS.get()) || stack.is(ModItems.TITANIUM_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.titanium_armor.2"));
        }

        if(stack.is(ModItems.SCULK_HELMET.get()) || stack.is(ModItems.SCULK_CHESTPLATE.get())
                || stack.is(ModItems.SCULK_LEGGINGS.get()) || stack.is(ModItems.SCULK_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.2"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.3"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.4"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.5"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.6"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.sculk_armor.7"));
        }

        if(stack.is(ModItems.DYEDREAM_HELMET.get()) || stack.is(ModItems.DYEDREAM_CHESTPLATE.get())
                || stack.is(ModItems.DYEDREAM_LEGGINGS.get()) || stack.is(ModItems.DYEDREAM_BOOTS.get())){
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.1"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.2"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.3"));
            tooltip.add(Component.translatable("tooltip.pasterdreammod.dyedream_armor.4"));
        }

        if(stack.is(ModItems.RICE_CAKE.get())){
            tooltip.add(Component.translatable("item.pasterdream.rice_cake.describe"));
        }
    }
}
