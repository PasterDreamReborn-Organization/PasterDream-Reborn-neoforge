package com.pasterdream.pasterdreammod.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.IsNotNeed;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.MaxMeltDreamEnergy;
import com.pasterdream.pasterdreammod.command.meltdreamenergy.MeltDreamEnergy;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

/**
 * 命令注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModCommands.java}。
 * 搬运时在 {@link #register(CommandDispatcher)} 中挂载 {@code /pasterdreamdebug} 及子命令，
 * 由 {@code RegisterCommandsEvent} 调用。
 */
public final class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pasterdreamdebug").requires(source -> source.hasPermission(2))
                .then(Commands.literal("meltdreamenergy")
                        .then(Commands.literal("energyvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set").then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(MeltDreamEnergy::setMeltDreamEnergy)))
                                        .then(Commands.literal("add").then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(MeltDreamEnergy::addMeltDreamEnergy)))
                                        .then(Commands.literal("get").executes(MeltDreamEnergy::getMeltDreamEnergy))))
                        .then(Commands.literal("maxenergyvalue")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set").then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(MaxMeltDreamEnergy::setMaxMeltDreamEnergy)))
                                        .then(Commands.literal("add").then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                .executes(MaxMeltDreamEnergy::addMaxMeltDreamEnergy)))
                                        .then(Commands.literal("get").executes(MaxMeltDreamEnergy::getMaxMeltDreamEnergy))))
                        .then(Commands.literal("isornotneed")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.literal("set").then(Commands.argument("boolean", BoolArgumentType.bool())
                                                .executes(IsNotNeed::setIsNotNeed)))
                                        .then(Commands.literal("get").executes(IsNotNeed::getIsNotNeed))))));
    }

    private ModCommands() {
    }
}
