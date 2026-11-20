package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;

/**
 * 帕秋莉宝典《旧梦归引》的物品形态。
 * Patchouli 1.21.1 只在自己的 guide_book 物品内处理开书，自定义书物品需要自行调用 PatchouliAPI 打开对应书。
 */
public class SeniorsDreamBookItem extends Item
{
    public static final ResourceLocation BOOK_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "seniors_dream");

    public SeniorsDreamBookItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer)
        {
            PatchouliAPI.get().openBookGUI(serverPlayer, BOOK_ID);
            player.playSound(PatchouliSounds.BOOK_OPEN, 1.0F, (float) (0.7 + Math.random() * 0.4));
        }
        return InteractionResultHolder.success(itemStack);
    }
}
