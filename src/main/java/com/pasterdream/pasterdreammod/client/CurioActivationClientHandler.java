package com.pasterdream.pasterdreammod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public final class CurioActivationClientHandler {
    private CurioActivationClientHandler() {
    }

    public static void handle(ItemStack stack) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }
}
