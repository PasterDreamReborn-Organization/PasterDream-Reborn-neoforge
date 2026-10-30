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

    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_X = BUILDER.defineInRange("meltDreamEnergyBarX", 9, 0, 3840);
    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM = BUILDER.defineInRange("meltDreamEnergyBarYFromBottom", 17, 0, 2160);
    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES = BUILDER.defineInRange("meltDreamEnergyBarDecimalPlaces", 1, 0, 4);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_TEXT_CENTERED = BUILDER.define("meltDreamEnergyBarTextCentered", false);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW = BUILDER.define("meltDreamEnergyBarAlwaysShow", true);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE = BUILDER.define("meltDreamEnergyBarShowOnChange", true);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK = BUILDER.define("meltDreamEnergyBarShowOnSneak", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private PasterDreamClientConfig() {
    }
}
