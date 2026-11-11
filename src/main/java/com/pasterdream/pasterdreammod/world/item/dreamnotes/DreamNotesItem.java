package com.pasterdream.pasterdreammod.world.item.dreamnotes;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DreamNotesItem extends Item
{
    public DreamNotesItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, context, tooltip, flag);
        String content = itemStack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());
        if (content != null && !content.isEmpty())
        {
            tooltip.add(Component.translatable("tooltip.pasterdream." + content));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        String content = stack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());
        if (content == null)
        {
            content = "";
        }

        if (level.isClientSide)
        {
            if (!content.isEmpty())
            {
                DreamNotesClientHelper.openDreamNotesScreen(content);
            }
        }
        else
        {
            if (!content.isEmpty())
            {
                level.playSound(null, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
