package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.blueprint.BlueprintPlacePacket;
import com.pasterdream.pasterdreammod.network.blueprint.UpdateBlueprintPlacingPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BluePrintItem extends Item
{
    public BluePrintItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        String content = itemStack.get(ModDataComponents.BLUEPRINT_CONTENT.get());
        BluePrintInfo bluePrintInfo = content != null ? BluePrintRegistry.getInfo(content) : null;
        return Component.translatable("item.pasterdream.blue_print").copy().append(" - ").append(bluePrintInfo != null ? bluePrintInfo.title() : Component.literal("null"));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.右键打开GUI以查看蓝图结构"));
        tooltip.add(Component.translatable("tooltip.pasterdream.蓝图搭建消耗规则"));
    }

    private void handleClientInteraction(Player player, ItemStack stack, @Nullable BlockPos clickedPos)
    {
        if (isPlacing(stack))
        {
            if (clickedPos != null)
            {
                ModNetwork.sendToServer(new BlueprintPlacePacket(clickedPos));
            }
                else
                {
                    ModNetwork.sendToServer(new UpdateBlueprintPlacingPacket(false));
                    player.displayClientMessage(Component.translatable("message.pasterdream.取消放置蓝图"), true);
                }
            ClientBluePrintPlacement.cancel();
        }
            else
            {
                String content = stack.get(ModDataComponents.BLUEPRINT_CONTENT.get());
                if (content != null)
                {
                    BluePrintInfo info = BluePrintRegistry.getInfo(content);
                    if (info != null)
                    {
                        BluePrintClientHelper.openBluePrintScreen(info);
                    }
                }
            }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide)
        {
            handleClientInteraction(player, stack, null);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level.isClientSide && player != null)
        {
            handleClientInteraction(player, context.getItemInHand(), context.getClickedPos());
        }
        return InteractionResult.SUCCESS;
    }

    private boolean isPlacing(ItemStack stack)
    {
        return stack.getOrDefault(ModDataComponents.BLUEPRINT_PLACING.get(), false);
    }
}
