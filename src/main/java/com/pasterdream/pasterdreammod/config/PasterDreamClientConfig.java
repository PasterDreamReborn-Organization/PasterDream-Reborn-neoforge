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

    public static final ModConfigSpec.BooleanValue LOADING_GUI_TIPS = BUILDER
            .comment("在加载界面时会弹出帕斯特之梦的tips 默认：true")
            .define("loading_gui_tips", true);

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

    // === 玩家血条 HUD ===
    public static final ModConfigSpec.BooleanValue PASTER_HEALTH_HUD = BUILDER
            .comment("启用帕斯特之梦主题玩家血条（替换原版生命值行），默认 false（需手动开启）")
            .define("pasterHealthHud", false);

    public static final ModConfigSpec.IntValue PASTER_HEALTH_HUD_DECIMAL_PLACES = BUILDER
            .comment("玩家血条数值小数位数（0~2），默认 0；设为 0 时只显示整数，避免过长小数")
            .defineInRange("pasterHealthHudDecimalPlaces", 0, 0, 2);

    // === 光影兼容 ===
    public static final ModConfigSpec.BooleanValue SHADER_BLOCK_INJECTION = BUILDER
            .comment("检测到光影生效时，把本模组的植物/树叶注入到光影的方块 ID 映射中，让它们跟随光影飘动。"
                    + "\n此操作直接修改 Oculus/Iris 内部状态（非官方 API），默认 true。")
            .define("shaderBlockInjection", true);

    // === 疯狂状态效果的屏幕效果设置 ===
    public static final ModConfigSpec.BooleanValue LOW_SAN_OVERLAY = BUILDER
            .comment("疯狂状态效果的全屏画面叠加效果，默认 true。"
                    + "\r\n指令 /pasterdreamdebug san lowsan overlay 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanOverlay", true);

    public static final ModConfigSpec.BooleanValue LOW_SAN_JITTER = BUILDER
            .comment("疯狂状态效果的视角抖动，默认 true。"
                    + "\r\n指令 /pasterdreamdebug san lowsan jitter 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanJitter", true);

    public static final ModConfigSpec.BooleanValue LOW_SAN_SOUND = BUILDER
            .comment("疯狂状态效果的循环音效，默认 true。"
                    + "\r\n指令 /pasterdreamdebug san lowsan sound 可运行时临时切换，重进存档后按此配置恢复")
            .define("lowSanSound", true);

    // === JEI 兼容 ===
    public static final ModConfigSpec.BooleanValue SHOW_FLUIDS_IN_JEI = BUILDER
            .comment("是否在 JEI 的原料列表（右侧物品/流体栏）中显示本模组的流体。"
                    + "\n默认 false：流体不出现在原料列表中，但仍会正常显示在配方里。"
                    + "\n修改后需重启游戏生效。")
            .define("showFluidsInJei", false);

    public static final ModConfigSpec.BooleanValue SHOW_POTION_ELIXIR_BOTTLES_IN_JEI = BUILDER
            .comment("是否在 JEI 的原料列表（右侧物品栏）中显示装满药水的灵药瓶（按药水效果展开的条目）。"
                    + "\n默认 false：药水灵药瓶不出现在原料列表中，但仍会正常显示在配方里，空灵药瓶不受影响。"
                    + "\n修改后需重启游戏生效。")
            .define("showPotionElixirBottlesInJei", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private PasterDreamClientConfig() {
    }
}
