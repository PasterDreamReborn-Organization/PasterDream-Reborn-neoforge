package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class GhostFaceItem extends Item implements ICurioItem {
    public GhostFaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.superior()));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.ghost_face.effect.1"));
        list.add(Component.translatable("tooltip.pasterdream.ghost_face.effect.2"));
        list.add(Component.translatable("tooltip.pasterdream.ghost_face.cooldown",
                Config.GHOST_FACE_CLONE_COOLDOWN.get()).withStyle(ChatFormatting.BLUE));
    }
}
