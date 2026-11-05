package com.pasterdream.pasterdreammod.command.san;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.pasterdream.pasterdreammod.network.san.LowSanConfigSyncPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class LowSanEffect {

    private static final String PREFIX = "command.pasterdream.lowsan.";

    private static Component onOff(boolean value) {
        return Component.translatable(PREFIX + (value ? "enabled" : "disabled"));
    }

    public static int setOverlay(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        LowSanConfigSyncPacket.setOverride(LowSanConfigSyncPacket.OVERLAY, value);
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "overlay.set", onOff(value)), true);
        return 1;
    }

    public static int getOverlay(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "overlay.get",
                Component.translatable(PREFIX + "overlay"), onOff(LowSanConfigSyncPacket.serverEnabled(LowSanConfigSyncPacket.OVERLAY))), true);
        return 1;
    }

    public static int setJitter(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        LowSanConfigSyncPacket.setOverride(LowSanConfigSyncPacket.JITTER, value);
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "jitter.set", onOff(value)), true);
        return 1;
    }

    public static int getJitter(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "jitter.get",
                Component.translatable(PREFIX + "jitter"), onOff(LowSanConfigSyncPacket.serverEnabled(LowSanConfigSyncPacket.JITTER))), true);
        return 1;
    }

    public static int setSound(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "boolean");
        LowSanConfigSyncPacket.setOverride(LowSanConfigSyncPacket.SOUND, value);
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.set", onOff(value)), true);
        return 1;
    }

    public static int getSound(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.translatable(PREFIX + "sound.get",
                Component.translatable(PREFIX + "sound"), onOff(LowSanConfigSyncPacket.serverEnabled(LowSanConfigSyncPacket.SOUND))), true);
        return 1;
    }
}
