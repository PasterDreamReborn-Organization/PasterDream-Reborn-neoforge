package com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class MeltDreamCrystalChestResetCooldownEvents {
    private MeltDreamCrystalChestResetCooldownEvents() {
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        MeltDreamCrystalChestResetCooldown.copyOnPlayerClone(event.getOriginal(), event.getEntity());
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            MeltDreamCrystalChestResetCooldown.syncToClient(player);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            MeltDreamCrystalChestResetCooldown.syncToClient(player);
        }
    }
}
