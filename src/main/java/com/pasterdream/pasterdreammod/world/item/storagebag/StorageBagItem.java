package com.pasterdream.pasterdreammod.world.item.storagebag;

import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class StorageBagItem extends Item {

    public static final int SLOT_COUNT = 9; // 3x3

    public StorageBagItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    public static List<ItemStack> getInventory(ItemStack stack) {
        return BagItemData.load(stack, SLOT_COUNT);
    }

    public static void saveInventory(ItemStack stack, List<ItemStack> items) {
        BagItemData.save(stack, items);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            tooltip.add(Component.translatable("tooltip.pasterdream.storage_bag.tooltip"));
        } else {
            tooltip.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        if (entity instanceof ServerPlayer serverPlayer) {
            ItemStack stack = entity.getItemInHand(hand);
            world.playSound(null, entity.blockPosition(), ModSounds.ZIPPER.get(), SoundSource.NEUTRAL, 0.2f, 1f);
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, player) -> new StorageBagMenu(id, inv, stack, hand),
                            Component.empty()
                    ),
                    buf -> buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1)
            );
        }
        return InteractionResultHolder.sidedSuccess(entity.getItemInHand(hand), world.isClientSide);
    }
}
