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

    // === 精神值条 HUD 位置（预设1：右下角） ===
    public static final ModConfigSpec.IntValue SAN_BAR_X_FROM_RIGHT = BUILDER
            .comment("精神值条距屏幕右侧的距离（像素），预设1：34")
            .defineInRange("sanBarXFromRight", 34, 0, 3840);

    public static final ModConfigSpec.IntValue SAN_BAR_Y_FROM_BOTTOM = BUILDER
            .comment("精神值条距屏幕底部的距离（像素），预设1：30")
            .defineInRange("sanBarYFromBottom", 30, 0, 2160);

    // === 精神值条预设 ===
    public static final ModConfigSpec.IntValue SAN_BAR_PRESET = BUILDER
            .comment("""
                    精神值条显示预设：\

                    1=默认（右下角，显示小数），\

                    2=紧凑（居中于血量与饥饿值之间，缩小，仅显示整数）""")
            .defineInRange("sanBarPreset", 1, 1, 2);

    public static final ModConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_PRECISE = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示数值，默认 false（始终显示）")
            .define("sanBarPreset1SneakPrecise", false);

    public static final ModConfigSpec.BooleanValue SAN_BAR_PRESET1_SNEAK_SHOW_BAR = BUILDER
            .comment("精神值条预设1：是否仅在下蹲时显示SAN条，默认 false（始终显示）")
            .define("sanBarPreset1SneakShowBar", false);

    public static final ModConfigSpec.IntValue SAN_BAR_PRESET1_DECIMAL_PLACES = BUILDER
            .comment("精神值条预设1：数值小数位数（0~4），默认 4")
            .defineInRange("sanBarPreset1DecimalPlaces", 4, 0, 4);

    public static final ModConfigSpec.BooleanValue SAN_BAR_PRESET1_SHOW_CHANGE_ARROW = BUILDER
            .comment("精神值条预设1：是否显示SAN变化箭头（↑ 上升 / ↓ 下降），默认 true")
            .define("sanBarPreset1ShowChangeArrow", true);

    public static final ModConfigSpec.DoubleValue SAN_BAR_PRESET2_SCALE = BUILDER
            .comment("精神值条预设2的缩放比例，默认 0.5（28×26 缩小为 14×13）")
            .defineInRange("sanBarPreset2Scale", 0.5, 0.1, 1.5);

    public static final ModConfigSpec.DoubleValue SAN_BAR_PRESET2_LOW_THRESHOLD = BUILDER
            .comment("精神值条预设2的低SAN警告阈值（ratio = 当前SAN / 最大SAN），低于该值时数字变红、bar抖动，默认 0.2（20%）")
            .defineInRange("sanBarPreset2LowThreshold", 0.2, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue SAN_BAR_PRESET2_SNEAK_SHOW_IN_CREATIVE = BUILDER
            .comment("精神值条预设2：创造模式下是否仅在下蹲时显示，默认 true")
            .define("sanBarPreset2SneakShowInCreative", true);

    // === 疯狂状态效果的屏幕效果设置 ===
    public static final ModConfigSpec.BooleanValue LOW_SAN_OVERLAY = BUILDER
            .comment("疯狂状态效果的全屏画面叠加效果，默认 true。"
                    + "\n指令 /pasterdreamdebug san lowsan overlay 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanOverlay", true);

    public static final ModConfigSpec.BooleanValue LOW_SAN_JITTER = BUILDER
            .comment("疯狂状态效果的视角抖动，默认 true。"
                    + "\n指令 /pasterdreamdebug san lowsan jitter 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanJitter", true);

    public static final ModConfigSpec.BooleanValue LOW_SAN_SOUND = BUILDER
            .comment("疯狂状态效果的循环音效，默认 true。"
                    + "\n指令 /pasterdreamdebug san lowsan sound 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanSound", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private PasterDreamClientConfig() {
    }
}
