package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** 融梦能量每分钟变化量按 1200 tick 换算。 */
public class MeltDreamAuraHandler {
    private static final double TICKS_PER_MINUTE = 1200.0;

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.isSpectator()) return;

        double effectiveMax = MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergyEffective(player);
        if (MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) > effectiveMax) {
            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, effectiveMax);
        }

        double rate = player.getAttributeValue(ModAttributes.MELT_DREAM_VARIABILITY);
        if (rate == 0.0) return;

        MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, rate / TICKS_PER_MINUTE);
    }
}
