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

    // === 融梦能量条 HUD 位置（预设1：左下角） ===
    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_X = BUILDER
            .comment("融梦能量条距屏幕左侧的距离（像素），预设1：9")
            .defineInRange("meltDreamEnergyBarX", 9, 0, 3840);
    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("融梦能量条距屏幕底部的距离（像素），预设1：17")
            .defineInRange("meltDreamEnergyBarYFromBottom", 17, 0, 2160);

    // === 融梦能量条显示配置 ===
    public static final ModConfigSpec.IntValue MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES = BUILDER
            .comment("融梦能量条数值小数位数（0~4），默认 1")
            .defineInRange("meltDreamEnergyBarDecimalPlaces", 1, 0, 4);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_TEXT_CENTERED = BUILDER
            .comment("融梦能量条数值是否居中在bar正中央，默认 false（在bar上方）")
            .define("meltDreamEnergyBarTextCentered", false);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW = BUILDER
            .comment("融梦能量条是否常态显示，默认 true。设为 false 时以下两项生效")
            .define("meltDreamEnergyBarAlwaysShow", true);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE = BUILDER
            .comment("融梦能量条：数值变化时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnChange", true);
    public static final ModConfigSpec.BooleanValue MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK = BUILDER
            .comment("融梦能量条：蹲下时显示（仅 alwaysShow=false 时生效），默认 true")
            .define("meltDreamEnergyBarShowOnSneak", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private PasterDreamClientConfig() {
    }
}
