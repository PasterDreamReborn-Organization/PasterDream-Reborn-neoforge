package com.pasterdream.pasterdreammod.world.item.debugtool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt.GetSpawnEgg;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class DebugToolHandler
{
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onAttackEntity(AttackEntityEvent event)
    {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        if (!(player.getMainHandItem().getItem() instanceof DebugToolItem) || !canTarget(player, target))
        {
            return;
        }

        event.setCanceled(true);
        if (player instanceof ServerPlayer)
        {
            ItemStack itemStack = GetSpawnEgg.getSpawnEgg(target);
            if (!itemStack.isEmpty() && !player.getInventory().add(itemStack))
            {
                player.drop(itemStack, false);
            }
            target.discard();
            player.sendSystemMessage(Component.translatable("message.pasterdream.debug_tool.entity_removed", target.getName()));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onInteractEntity(PlayerInteractEvent.EntityInteractSpecific event)
    {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        if (!(event.getItemStack().getItem() instanceof DebugToolItem) || !canTarget(player, target))
        {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(player.level().isClientSide));
        if (player instanceof ServerPlayer serverPlayer)
        {
            serverPlayer.openMenu(new DebugToolEntityEditorMenu.Provider(target));
        }
    }

    public static boolean canTarget(Player player, Entity target)
    {
        return DebugToolItem.canEdit(player) && target != null && !(target instanceof Player)
                && target.level() == player.level() && player.canInteractWithEntity(target, 1.0)
                && player.level().getWorldBorder().isWithinBounds(target.blockPosition());
    }
}
