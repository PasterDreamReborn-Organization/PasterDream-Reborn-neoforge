package com.pasterdream.pasterdreammod.init;

import net.minecraft.world.level.GameRules;

/**
 * 游戏规则注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModGameRules.java}。
 * 搬运时在 {@link #init()} 中调用 {@code GameRules.register(...)} 注册
 * {@code shadowDifficulty} / {@code playerShadowDifficulty} / {@code pasterdreamWindDirection} 等。
 */
public final class ModGameRules {
    public static final GameRules.Key<GameRules.IntegerValue> SHADOW_DIFFICULTY =
            GameRules.register("shadowDifficulty", GameRules.Category.MISC,
                    GameRules.IntegerValue.create(1));

    /**
     * 默认玩家暗影难度：新玩家没有个人覆盖时使用此值。
     * 此版本 API 不支持创建世界菜单的数值边界，但运行时 ShadowDifficultyHelper 会自动钳制到 0-3。
     */
    public static final GameRules.Key<GameRules.IntegerValue> PLAYER_SHADOW_DIFFICULTY =
            GameRules.register("playerShadowDifficulty", GameRules.Category.MISC,
                    GameRules.IntegerValue.create(1));

    public static void init() {
        // 通过类初始化注册。
    }

    private ModGameRules() {
    }
}
