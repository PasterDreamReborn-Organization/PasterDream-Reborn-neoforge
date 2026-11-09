package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.world.item.StoryProgressItem;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DreamNotesBookItem extends Item
{
    public DreamNotesBookItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        String contentName = itemStack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());
        DreamNotesBookInfo dreamNotesBookInfo = contentName != null ? DreamNotesBookRegistry.getInfo(contentName) : null;
        return dreamNotesBookInfo != null ? dreamNotesBookInfo.title().copy().append(" - " + dreamNotesBookInfo.author()) : Component.literal("null - null");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        String contentName = itemStack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());

        if (level.isClientSide)
        {
            DreamNotesBookInfo dreamNotesBookInfo = (contentName != null ? DreamNotesBookRegistry.getInfo(contentName) : null);
            DreamNotesBookClientHelper.openDreamNotesBookScreen(dreamNotesBookInfo);
        }
            else
            {
                level.playSound(null, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);

                String dimension = (contentName != null ? DreamNotesBookProgressRegistry.getDimensionByNotes(contentName) : null);
                if(dimension != null)
                {
                    PasterDreamPlayerDreamNoteProgressData.setProgressData(player, dimension, contentName, true);
                }

                if (player instanceof ServerPlayer serverPlayer)
                {
                    ModCriteriaTriggers.READ_DREAM_NOTE.get().trigger(serverPlayer, contentName);

                    // 剧情笔记书：打开时授予剧情进度（同时由进度解锁帕秋莉对应词条）
                    StoryProgressItem.grantProgressOnNoteOpened(serverPlayer, contentName);
                }
            }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }
}
