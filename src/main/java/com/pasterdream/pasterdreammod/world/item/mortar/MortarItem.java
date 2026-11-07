package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MortarItem extends Item
{
    public MortarItem(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.右键打开GUI"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer)
        {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (id, inventory, menuPlayer) -> new MortarMenu(id, inventory, stack, hand),
                    Component.translatable("gui." + PasterDreamMod.MOD_ID + ".mortar")),
                    buffer -> buffer.writeBoolean(hand == InteractionHand.MAIN_HAND));
            if (serverPlayer.containerMenu instanceof MortarMenu mortarMenu)
            {
                mortarMenu.syncFluids(serverPlayer);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
