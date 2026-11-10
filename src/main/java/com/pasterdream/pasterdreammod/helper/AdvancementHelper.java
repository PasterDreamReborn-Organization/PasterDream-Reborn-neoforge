package com.pasterdream.pasterdreammod.helper;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class AdvancementHelper {

    private AdvancementHelper() {
    }

    public static boolean isDone(ServerPlayer player, ResourceLocation id) {
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        return adv != null && player.getAdvancements().getOrStartProgress(adv).isDone();
    }

    public static void grant(ServerPlayer player, ResourceLocation id, String criterion) {
        AdvancementHolder adv = player.server.getAdvancements().get(id);
        if (adv != null && !player.getAdvancements().getOrStartProgress(adv).isDone()) {
            player.getAdvancements().award(adv, criterion);
        }
    }
}
