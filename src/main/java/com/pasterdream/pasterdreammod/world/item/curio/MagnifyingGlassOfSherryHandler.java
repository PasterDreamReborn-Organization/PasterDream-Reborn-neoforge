package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class MagnifyingGlassOfSherryHandler {

    /** 挖掘速度 +50% */
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (MagnifyingGlassOfSherryItem.isWearing(player)) {
            event.setNewSpeed(event.getNewSpeed() * 1.5F);
        }
    }

    /** 近战伤害 +100%；未使用武器（空手）时 +200% */
    @SubscribeEvent
    public static void onMeleeDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player
                && event.getSource().is(DamageTypes.PLAYER_ATTACK)
                && MagnifyingGlassOfSherryItem.isWearing(player)) {
            float multiplier = player.getMainHandItem().isEmpty() ? 3.0F : 2.0F;
            event.setAmount(event.getAmount() * multiplier);
        }
    }
}
