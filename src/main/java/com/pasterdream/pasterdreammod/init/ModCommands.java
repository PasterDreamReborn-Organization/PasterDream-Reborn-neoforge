package com.pasterdream.pasterdreammod.init;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

/**
 * 命令注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModCommands.java}。
 * 搬运时在 {@link #register(CommandDispatcher)} 中挂载 {@code /pasterdreamdebug} 及子命令，
 * 由 {@code RegisterCommandsEvent} 调用。
 */
public final class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // TODO: dispatcher.register(...)
    }

    private ModCommands() {
    }
}
