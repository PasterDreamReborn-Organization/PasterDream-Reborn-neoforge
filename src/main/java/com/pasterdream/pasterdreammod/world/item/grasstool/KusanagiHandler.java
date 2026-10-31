package com.pasterdream.pasterdreammod.world.item.grasstool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class KusanagiHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack stack = null;
        if (mainHand.getItem() instanceof KusanagiItem) {
            stack = mainHand;
        } else {
            ItemStack offHand = player.getOffhandItem();
            if (offHand.getItem() instanceof KusanagiItem) {
                stack = offHand;
            }
        }

        if (stack == null) return;

        int killed = (int) stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("killed");
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("killed", killed + 1));
    }
}
