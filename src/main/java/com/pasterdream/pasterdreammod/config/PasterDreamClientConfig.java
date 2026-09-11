package com.pasterdream.pasterdreammod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 客户端配置（CLIENT）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code config/PasterDreamClientConfig.java}，
 * 搬运客户端内容时在此补齐配置项。
 */
public class PasterDreamClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec SPEC = BUILDER.build();

    private PasterDreamClientConfig() {
    }
}
