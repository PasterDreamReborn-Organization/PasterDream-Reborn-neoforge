package com.pasterdream.pasterdreammod;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 通用配置（COMMON）。
 *
 * <p>骨架阶段仅保留调试开关；搬运内容时按 Forge 1.20.1 源仓库的
 * {@code Config.java} 分批补齐配置项（暗影难度、低 SAN 视觉、容器平衡等）。
 */
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING = BUILDER
            .comment("是否输出调试日志 / Enable debug logging")
            .define("debugLogging", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private Config() {
    }
}
