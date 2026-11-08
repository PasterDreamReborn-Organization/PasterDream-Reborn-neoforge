package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.skill.TerraBladeSwingPacket;
import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class TerraBladeClientHandler {
    private TerraBladeClientHandler() {
    }

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        sendSwingPacket(event.getEntity());
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        sendSwingPacket(event.getEntity());
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        sendSwingPacket(event.getEntity());
    }

    /** 按住左键时按冷却自动连发剑气（对准空气时原版不会重复触发左键事件）。 */
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen != null) return;
        if (!minecraft.options.keyAttack.isDown()) return;
        ItemStack stack = minecraft.player.getMainHandItem();
        if (!(stack.getItem() instanceof TerraBladeItem)) return;
        if (!TerraBladeItem.isSkillActive(stack)) return;
        if (minecraft.player.getCooldowns().isOnCooldown(stack.getItem())) return;
        minecraft.player.swing(InteractionHand.MAIN_HAND);
        sendSwingPacket(minecraft.player);
    }

    private static void sendSwingPacket(Player player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof TerraBladeItem && TerraBladeItem.isSkillActive(stack)) {
            ModNetwork.sendToServer(new TerraBladeSwingPacket());
        }
    }
}
