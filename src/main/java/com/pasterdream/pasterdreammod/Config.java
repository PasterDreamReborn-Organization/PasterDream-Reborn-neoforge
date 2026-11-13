package com.pasterdream.pasterdreammod;

import java.util.ArrayList;
import java.util.List;
import com.pasterdream.pasterdreammod.helper.BossLimitProfile;
import com.pasterdream.pasterdreammod.helper.BossLimitValues;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 通用配置（COMMON）。
 *
 * 包含 SAN 阈值、暗影生物倍率与低理智生成设置。
 */
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING = BUILDER
            .comment("是否输出调试日志 / Enable debug logging")
            .define("debugLogging", false);

    public static final ModConfigSpec.BooleanValue DREAM_CAULDRON_POTION_ENABLED = BUILDER
            .comment("梦之釜的酿造、融合、勾兑和装瓶模块，默认开启")
            .define("dreamCauldronPotionEnabled", true);

    public static final ModConfigSpec.BooleanValue DESTROY_DESK_ON_DREAMING_LOTUS_CONVERSION = BUILDER
            .comment("冶梦莲转化为迷梦冶梦莲时是否摧毁染梦书桌，默认 true")
            .define("destroyDeskOnDreamingLotusConversion", true);

    public static final ModConfigSpec.BooleanValue DESTROY_DESK_ON_EDELWEISS_CONVERSION = BUILDER
            .comment("雪绒花转化为凌冽雪绒花时是否摧毁染梦书桌，默认 true")
            .define("destroyDeskOnEdelweissConversion", true);

    public static final ModConfigSpec.IntValue NEED_KILL_ENEMY = BUILDER
            .comment("『天丛云』草薙进化需要击杀的生物数，默认 200")
            .defineInRange("the_number_of_kill_enemy_to_evolve", 200, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue TIME_OF_SAND_COOLDOWN = BUILDER
            .comment("时之沙切换昼夜的冷却时间（秒），默认 300 秒（5 分钟）")
            .defineInRange("timeOfSandCooldownSeconds", 300, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_NORMAL_Y_SPEED = BUILDER
            .comment("融梦水晶箱普通品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestNormalYSpeed", 0.375, 0.0, 5.0);
    public static final ModConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_RARE_Y_SPEED = BUILDER
            .comment("融梦水晶箱稀有品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestRareYSpeed", 0.375, 0.0, 5.0);
    public static final ModConfigSpec.DoubleValue MELT_DREAM_CRYSTAL_CHEST_LEGEND_Y_SPEED = BUILDER
            .comment("融梦水晶箱传奇品质物品抛出速度，默认 0.375")
            .defineInRange("meltDreamCrystalChestLegendYSpeed", 0.375, 0.0, 5.0);
    public static final ModConfigSpec.IntValue MELT_DREAM_CRYSTAL_CHEST_RESET_COOLDOWN_SECONDS = BUILDER
            .comment("融梦水晶箱重置工具的冷却时间（秒），默认 1200 秒（20 分钟）")
            .defineInRange("meltDreamCrystalChestResetCooldownSeconds", 1200, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue FRACTURED_ANGEL_STATUE_INVULNERABLE_CHANCE = BUILDER
            .comment("折翼天使雕像：受击后获得无敌的触发概率，默认 0.1（10%）")
            .defineInRange("fracturedAngelStatueInvulnerableChance", 0.1, 0.0, 1.0);
    public static final ModConfigSpec.IntValue FRACTURED_ANGEL_STATUE_INVULNERABLE_TICKS = BUILDER
            .comment("折翼天使雕像：受击后获得的无敌时长，默认 20 tick")
            .defineInRange("fracturedAngelStatueInvulnerableTicks", 20, 1, Integer.MAX_VALUE);

    // === SAN 阈值（ratio = 当前SAN / 最大SAN） ===
    public static final ModConfigSpec.DoubleValue SAN_CHEER_UP_THRESHOLD = BUILDER
            .comment("振奋效果触发阈值（ratio >= 该值），默认 0.9（90%）")
            .defineInRange("sanCheerUpThreshold", 0.9, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SAN_LETHARGY_UPPER_THRESHOLD = BUILDER
            .comment("不振效果上界（ratio < 该值且 >= 下界时触发），默认 0.6（60%）")
            .defineInRange("sanLethargyUpperThreshold", 0.6, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SAN_LETHARGY_LOWER_THRESHOLD = BUILDER
            .comment("不振效果下界 / 恍惚效果上界，默认 0.4（40%）")
            .defineInRange("sanLethargyLowerThreshold", 0.4, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SAN_TRANCE_LOWER_THRESHOLD = BUILDER
            .comment("恍惚效果下界 / 疯狂效果上界，默认 0.2（20%）")
            .defineInRange("sanTranceLowerThreshold", 0.2, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SAN_INSANE_LV2_THRESHOLD = BUILDER
            .comment("疯狂 II 触发阈值（ratio < 该值时升级为 Lv2），默认 0.1（10%）")
            .defineInRange("sanInsaneLv2Threshold", 0.1, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue SAN_INSANE_LV3_THRESHOLD = BUILDER
            .comment("疯狂 III 触发阈值（ratio < 该值时升级为 Lv3），默认 0.01（1%）")
            .defineInRange("sanInsaneLv3Threshold", 0.01, 0.0, 1.0);
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SHADOW_SPECIAL_SKILLS_ENABLED = BUILDER
            .comment("各难度是否启用暗影生物特殊技能（阴影傀儡AoE、悲泣尖啸怨魂召唤等），按难度等级排列，默认 [false, true, true, true]")
            .defineListAllowEmpty("shadowSpecialSkillsEnabled", List.of("false", "true", "true", "true"),
                    obj -> obj instanceof String);
    // 区间边界沿用上方 SAN 阈值，此处仅配置各区间的刷怪概率与实体权重
    // 概率为 4 个值，按暗影难度排列 [极简单, 简单, 普通, 困难]
    // 实体格式: "modid:entity_id:weight"，weight 为 0~1 的相对权重

    // 安全理智区间（上界由 lowSanSpawnHighThresholds 按难度控制）
    public static final ModConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_HIGH_PROBS = BUILDER
            .comment("安全理智区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，默认 [0, 0, 0, 0]")
            .defineListAllowEmpty("lowSanSpawnHighProbs", List.of(0.0, 0.0, 0.0, 0.0),
                    value -> value instanceof Double number && Double.isFinite(number));
    public static final ModConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_HIGH_ENTITIES = BUILDER
            .comment("振奋区间可生成的实体及权重，默认空")
            .defineListAllowEmpty("lowSanSpawnHighEntities", List.of(), obj -> obj instanceof String);

    // 中理智区间 (sanLethargyLowerThreshold <= ratio < sanLethargyUpperThreshold)
    public static final ModConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_MEDIUM_PROBS = BUILDER
            .comment("不振区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，简单难度不刷，默认 [0, 0, 0.001, 0.002]")
            .defineListAllowEmpty("lowSanSpawnMediumProbs", List.of(0.0, 0.0, 0.001, 0.002),
                    value -> value instanceof Double number && Double.isFinite(number));
    public static final ModConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_MEDIUM_ENTITIES = BUILDER
            .comment("不振区间可生成的实体及权重，默认虚弱恐怖尖喙:0.15, 暗影之手:0.3")
            .defineListAllowEmpty("lowSanSpawnMediumEntities",
                    List.of("pasterdream:weakeness_terrorbeak:0.15", "pasterdream:shadow_hand:0.3"),
                    obj -> obj instanceof String);

    // 低理智区间 (sanTranceLowerThreshold <= ratio < sanLethargyLowerThreshold)
    public static final ModConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_LOW_PROBS = BUILDER
            .comment("恍惚区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，简单难度不刷，默认 [0, 0, 0.002, 0.004]")
            .defineListAllowEmpty("lowSanSpawnLowProbs", List.of(0.0, 0.0, 0.002, 0.004),
                    value -> value instanceof Double number && Double.isFinite(number));
    public static final ModConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_LOW_ENTITIES = BUILDER
            .comment("恍惚区间可生成的实体及权重，默认虚弱恐怖尖喙:0.4, 暗影之手:0.2")
            .defineListAllowEmpty("lowSanSpawnLowEntities",
                    List.of("pasterdream:weakeness_terrorbeak:0.4", "pasterdream:shadow_hand:0.2",
                            "pasterdream:terrorbeak:0.1"),
                    obj -> obj instanceof String);

    // 极低理智区间 (ratio < sanTranceLowerThreshold)
    public static final ModConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_CRITICAL_PROBS = BUILDER
            .comment("极低理智区间每 tick 刷怪概率 [极简单, 简单, 普通, 困难]，默认 [0, 0.005, 0.005, 0.006]")
            .defineListAllowEmpty("lowSanSpawnCriticalProbs", List.of(0.0, 0.005, 0.005, 0.006),
                    value -> value instanceof Double number && Double.isFinite(number));
    public static final ModConfigSpec.ConfigValue<List<? extends String>> LOW_SAN_SPAWN_CRITICAL_ENTITIES = BUILDER
            .comment("极低理智区间可生成的实体及权重，默认疯狂恐怖尖喙:0.25, 恐怖尖喙:0.2, 虚弱恐怖尖喙:0.15, 暗影之手:0.15")
            .defineListAllowEmpty("lowSanSpawnCriticalEntities",
                    List.of("pasterdream:crazy_terrorbeak:0.25", "pasterdream:terrorbeak:0.2",
                            "pasterdream:weakeness_terrorbeak:0.15", "pasterdream:shadow_hand:0.15"),
                    obj -> obj instanceof String);

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> LOW_SAN_SPAWN_HIGH_THRESHOLDS = BUILDER
            .comment("各暗影难度下安全理智区间的下界（低于此值开始刷怪）[极简单, 简单, 普通, 困难]，默认 [0.0, 0.2, 0.6, 0.825]")
            .defineListAllowEmpty("lowSanSpawnHighThresholds", List.of(0.0, 0.2, 0.6, 0.825),
                    value -> value instanceof Double number && Double.isFinite(number));

    public static final ModConfigSpec.IntValue LOW_SAN_SPAWN_MAX_LIGHT = BUILDER
            .comment("低理智刷怪允许的最大亮度（方块光照），默认 5。设为 15 则无光照限制")
            .defineInRange("lowSanSpawnMaxLight", 5, 0, 15);

    public static final ModConfigSpec.DoubleValue LOW_SAN_SPAWN_RADIUS_MIN = BUILDER
            .comment("低理智刷怪距玩家的最小距离（格），默认 7")
            .defineInRange("lowSanSpawnRadiusMin", 7.0, 0.0, 64.0);

    public static final ModConfigSpec.DoubleValue LOW_SAN_SPAWN_RADIUS_MAX = BUILDER
            .comment("低理智刷怪距玩家的最大距离（格），默认 12")
            .defineInRange("lowSanSpawnRadiusMax", 12.0, 1.0, 64.0);

    public static final ModConfigSpec.IntValue LOW_SAN_SPAWN_MAX_NEARBY = BUILDER
            .comment("低理智刷怪时玩家周围允许存在的最大暗影生物数量，默认 2（饥荒上限）")
            .defineInRange("lowSanSpawnMaxNearby", 2, 0, 20);

    public static final ModConfigSpec.BooleanValue LOW_SAN_SPAWN_REQUIRES_SPECIAL_SKILL = BUILDER
            .comment("低理智刷怪是否受暗影难度特殊技能开关控制，默认 true")
            .define("lowSanSpawnRequiresSpecialSkill", true);

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> SHADOW_HEALTH_MULTIPLIERS = BUILDER
            .comment("暗影生物血量倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.5, 1.0, 1.5, 2.0]")
            .defineListAllowEmpty("shadowHealthMultipliers", List.of(0.5, 1.0, 1.5, 2.0),
                    value -> value instanceof Double number && Double.isFinite(number));

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> SHADOW_ATTACK_MULTIPLIERS = BUILDER
            .comment("暗影生物攻击倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.5, 1.0, 1.3, 1.8]")
            .defineListAllowEmpty("shadowAttackMultipliers", List.of(0.5, 1.0, 1.3, 1.8),
                    value -> value instanceof Double number && Double.isFinite(number));

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> SHADOW_SPEED_MULTIPLIERS = BUILDER
            .comment("暗影生物移速倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [0.7, 1.0, 1.1, 1.25]")
            .defineListAllowEmpty("shadowSpeedMultipliers", List.of(0.7, 1.0, 1.1, 1.25),
                    value -> value instanceof Double number && Double.isFinite(number));

    public static final ModConfigSpec.DoubleValue SHADOW_GOLEM_SKILL_DAMAGE = BUILDER
            .comment("阴影傀儡 AoE 技能基础伤害，默认 15")
            .defineInRange("shadowGolemSkillDamage", 15.0, 0.0, 1000.0);

    public static final ModConfigSpec.DoubleValue BASTION_GUARD_GRANT_RADIUS = BUILDER
            .comment("暮影之笼据点守卫事件完成时，向附近玩家授予进度的半径，默认 80")
            .defineInRange("bastionGuardGrantRadius", 80.0, 1.0, 256.0);

    public static final ModConfigSpec.DoubleValue SHADOW_HAND_SAN_DRAIN = BUILDER
            .comment("暗影之手每次命中扣除的 SAN 基础值，默认 0.02")
            .defineInRange("shadowHandSanDrain", 0.02, 0.0, 100.0);

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> SHADOW_LOOT_MULTIPLIERS = BUILDER
            .comment("暗影生物战利品掉落倍率，按难度等级排列 [极简单, 简单, 普通, 困难]，默认 [1.0, 1.0, 1.5, 2.0]"
                    + "\n每个掉落物以 (倍率-1) 的概率额外复制一份")
            .defineListAllowEmpty("shadowLootMultipliers", List.of(1.0, 1.0, 1.5, 2.0),
                    value -> value instanceof Double number && Double.isFinite(number));

    // === 大型便携储物袋捕获生物 ===
    public static final ModConfigSpec.BooleanValue CREATURE_CAPTURE_ENABLED = BUILDER
            .comment("是否启用大型便携储物袋（LargeStorageBag）捕获生物的功能，默认 true")
            .define("creatureCaptureEnabled", true);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CREATURE_CAPTURE_ENTITIES = BUILDER
            .comment("大型便携储物袋可捕获的实体 ID 列表，格式：modid:entity_id。"
                    + "\n例如 minecraft:chicken 为鸡，pasterdream:pink_chicken 为粉红鸡。"
                    + "\n按 Shift+右键时尝试捕获，无法捕获 Boss 生物（凋灵、末影龙）。")
            .defineListAllowEmpty("creatureCaptureEntities",
                    List.of("pasterdream:pink_chicken", "pasterdream:pink_slime",
                            "pasterdream:jellyfish", "pasterdream:friendly_shadow_ghost",
                            "minecraft:chicken", "minecraft:slime", "minecraft:silverfish",
                            "minecraft:vex", "minecraft:endermite", "minecraft:frog",
                            "minecraft:tadpole", "minecraft:tropical_fish", "minecraft:pufferfish",
                            "minecraft:salmon", "minecraft:cod", "minecraft:wolf", "minecraft:parrot"),
                    obj -> obj instanceof String);

    // === 卡莱的调料瓶 ===
    public static final ModConfigSpec.ConfigValue<List<? extends String>> CALAIS_SPICE_BOTTLE_BUFFS = BUILDER
            .comment("卡莱的调料瓶触发料理时可能获得的正面效果 ID 列表，格式：modid:effect_id",
                    "\n例如 minecraft:regeneration 为生命恢复，minecraft:speed 为速度",
                    "\n支持模组药水效果")
            .defineListAllowEmpty("calaisSpiceBottleBuffs",
                    List.of("minecraft:speed", "minecraft:haste", "minecraft:strength",
                            "minecraft:regeneration", "minecraft:resistance", "minecraft:fire_resistance",
                            "minecraft:absorption", "pasterdream:rest", "pasterdream:cook"),
                    obj -> obj instanceof String);

    public static final ModConfigSpec.ConfigValue<List<? extends Double>> CALAIS_SPICE_BOTTLE_WEIGHTS = BUILDER
            .comment("卡莱的调料瓶触发料理时六类结果的抽取权重，依次为：正面效果、san恢复、生命恢复、目标负面、闪避效果、doll效果",
                    "\n设为 0 可禁用该类结果")
            .defineListAllowEmpty("calaisSpiceBottleWeights", List.of(4.0, 4.0, 2.0, 3.0, 1.0, 1.0),
                    value -> value instanceof Double number && Double.isFinite(number));

    public static final ModConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_HEAL_MIN = BUILDER
            .comment("卡莱的调料瓶生命恢复最小值（点），默认 1.0")
            .defineInRange("calaisSpiceBottleHealMin", 1.0, 0.5, 20.0);
    public static final ModConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_HEAL_MAX = BUILDER
            .comment("卡莱的调料瓶生命恢复最大值（点），默认 2.0")
            .defineInRange("calaisSpiceBottleHealMax", 2.0, 0.5, 20.0);

    public static final ModConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_SAN_MIN = BUILDER
            .comment("卡莱的调料瓶 SAN 恢复最小值，默认 0.5")
            .defineInRange("calaisSpiceBottleSanMin", 0.5, 0.0, 100.0);
    public static final ModConfigSpec.DoubleValue CALAIS_SPICE_BOTTLE_SAN_MAX = BUILDER
            .comment("卡莱的调料瓶 SAN 恢复最大值，默认 3.0")
            .defineInRange("calaisSpiceBottleSanMax", 3.0, 0.0, 100.0);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CALAIS_SPICE_BOTTLE_DEBUFFS = BUILDER
            .comment("卡莱的调料瓶触发料理时施加给目标的负面效果 ID 列表，格式：modid:effect_id",
                    "\n例如 minecraft:slowness 为缓慢，minecraft:weakness 为虚弱")
            .defineListAllowEmpty("calaisSpiceBottleDebuffs",
                    List.of("pasterdream:confusion"),
                    obj -> obj instanceof String);
    public static final ModConfigSpec.IntValue CALAIS_SPICE_BOTTLE_DEBUFF_DURATION = BUILDER
            .comment("卡莱的调料瓶负面效果的持续时间（tick），默认 100（5 秒）")
            .defineInRange("calaisSpiceBottleDebuffDuration", 100, 0, 6000);
    public static final ModConfigSpec.IntValue CALAIS_SPICE_BOTTLE_DEBUFF_AMPLIFIER = BUILDER
            .comment("卡莱的调料瓶负面效果的等级（0=I, 1=II, ...），默认 0")
            .defineInRange("calaisSpiceBottleDebuffAmplifier", 0, 0, 255);

    public static final ModConfigSpec.IntValue CALAIS_SPICE_BOTTLE_EVASION_DURATION = BUILDER
            .comment("卡莱的调料瓶闪避效果的持续时间（tick），默认 20（1 秒）")
            .defineInRange("calaisSpiceBottleEvasionDuration", 20, 0, 200);

    // === 暗影合金弩 ===
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SHADOW_ALLOY_CROSSBOW_DEBUFFS = BUILDER
            .comment("暗影合金弩命中生物时随机施加的负面效果 ID 列表，格式：modid:effect_id",
                    "\n例如 minecraft:slowness 为缓慢，minecraft:weakness 为虚弱")
            .defineListAllowEmpty("shadowAlloyCrossbowDebuffs",
                    List.of("minecraft:slowness", "minecraft:mining_fatigue", "minecraft:blindness",
                            "minecraft:weakness", "minecraft:poison", "minecraft:wither",
                            "minecraft:levitation", "pasterdream:confusion", "pasterdream:bind"),
                    obj -> obj instanceof String);

    public static final ModConfigSpec.IntValue SHADOW_ALLOY_CROSSBOW_DEBUFF_DURATION = BUILDER
            .comment("暗影合金弩随机负面效果的持续时间（tick），默认 100（5 秒）")
            .defineInRange("shadowAlloyCrossbowDebuffDuration", 100, 0, 6000);

    public static final ModConfigSpec.IntValue SHADOW_ALLOY_CROSSBOW_DEBUFF_AMPLIFIER = BUILDER
            .comment("暗影合金弩随机负面效果的等级（0=I, 1=II, ...），默认 0")
            .defineInRange("shadowAlloyCrossbowDebuffAmplifier", 0, 0, 255);

    // === 鬼魂之面 ===
    public static final ModConfigSpec.ConfigValue<List<? extends String>> GHOST_FACE_PROJECTILE_BLACKLIST = BUILDER
            .comment("鬼魂之面不生效的投掷物实体 ID 列表，格式：modid:entity_id",
                    "\n例如 supplementaries:slingshot_projectile 为 Supplementary 模组的弹弓弹射物",
                    "\n支持模组投掷物，默认忽略钓鱼浮标以避免与弹射物复制冲突")
            .defineListAllowEmpty("ghostFaceProjectileBlacklist",
                    List.of(
                        "supplementaries:slingshot_projectile",
                        "tide:fishing_bobber"
                    ),
                    obj -> obj instanceof String);

    public static final ModConfigSpec.IntValue GHOST_FACE_CLONE_COOLDOWN = BUILDER
            .comment("鬼魂之面复制弹幕的冷却时间（秒），默认 3 秒，设为 0 可关闭冷却")
            .defineInRange("ghostFaceCloneCooldownSeconds", 3, 0, Integer.MAX_VALUE);

    // === 虹彩仙玉 ===
    public static final ModConfigSpec.ConfigValue<List<? extends String>> IRIDESCENT_FAIRY_JADE_PROJECTILE_WHITELIST = BUILDER
            .comment("虹彩仙玉：弓/弩发射主弹幕时额外发射的弹幕（投射物实体类型）白名单（格式：modid:entity_id），"
                    + "\n例：minecraft:arrow 为箭，pasterdream:golden_fox_fireball 为黄金狐火球"
                    + "\n支持模组投射物。每次发射主弹幕时，随机取其中一种发射 1 发")
            .defineListAllowEmpty("iridescentFairyJadeProjectileWhitelist",
                    List.of("pasterdream:golden_fox_fireball", "pasterdream:lightning_projectile",
                            "pasterdream:squeal_wave_projectile", "pasterdream:pebble_projectile",
                            "pasterdream:thrown_pink_egg",
                            "minecraft:arrow", "minecraft:spectral_arrow",
                            "minecraft:snowball", "minecraft:egg", "minecraft:trident",
                            "minecraft:llama_spit"),
                    obj -> obj instanceof String);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> IRIDESCENT_FAIRY_JADE_PROJECTILE_BLACKLIST = BUILDER
            .comment("虹彩仙玉不触发额外弹幕的投射物实体类型 ID 黑名单（格式：modid:entity_id），"
                    + "\n用于屏蔽无法作为弓/弩主弹幕处理的投射物")
            .defineListAllowEmpty("iridescentFairyJadeProjectileBlacklist",
                    List.of("minecraft:trident", "pasterdream:wind_thunder_spear",
                            "pasterdream:tide_sword_projectile"),
                    obj -> obj instanceof String);

    // === 预言卡 · 平衡 ===
    public static final ModConfigSpec.IntValue MIN_TAKE_EFFECT_DURATION = BUILDER
            .comment("平衡预言卡：药水等级翻倍、时间减半时，低于此时长的效果不会被处理，默认 30 tick")
            .defineInRange("minTakeEffectDuration", 30, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue MAX_TAKE_EFFECT_DURATION = BUILDER
            .comment("平衡预言卡：药水等级翻倍、时间减半时，高于此时长的效果不会被处理，默认 1 小时（3600 秒）")
            .defineInRange("maxTakeEffectDuration", 3600, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue MAX_LEVEL = BUILDER
            .comment("高于该等级的药水效果平衡预言卡不会生效，默认 255 级（不限制）")
            .defineInRange("maxLevel", 255, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> BALANCE_ALLOWED_EFFECTS = BUILDER
            .comment("平衡预言卡允许翻倍的药水效果 ID 列表（格式：modid:effect_id），留空则允许所有")
            .defineListAllowEmpty("balanceAllowedEffects",
                    List.of("minecraft:regeneration", "minecraft:speed", "minecraft:strength", "minecraft:luck",
                            "minecraft:jump_boost", "minecraft:health_boost", "pasterdream:cook"),
                    obj -> obj instanceof String);

    // === 预言卡 · 罪恶 ===
    public static final ModConfigSpec.ConfigValue<List<? extends String>> SIN_INSTAKILL_ENTITIES = BUILDER
            .comment("罪之预言卡直接秒杀的实体类型 ID 列表（格式：modid:entity_id）。小僵尸（isBaby）的秒杀逻辑为硬编码，不在此列表中")
            .defineListAllowEmpty("sinInstakillEntities",
                    List.of("minecraft:vex", "minecraft:bat", "minecraft:endermite",
                            "minecraft:silverfish", "minecraft:creeper", "minecraft:cave_spider",
                            "alexsmobs:centipede_head", "alexsmobs:centipede_body", "alexsmobs:centipede_tail",
                            "alexsmobs:crimson_mosquito", "alexsmobs:seagull", "iceandfire:pixie",
                            "twilightforest:pinch_beetle", "born_in_chaos_v1:maggot",
                            "born_in_chaos_v1:diamond_termite", "born_in_chaos_v1:firelight",
                            "illageandspillage:villager_soul"),
                    obj -> obj instanceof String);

    // === 预言卡 · 守护 ===
    public static final ModConfigSpec.DoubleValue HEALTH_PERCENT = BUILDER
            .comment("守护效果触发时，需要伤害为最大生命值的占比，默认 0.3（30%）")
            .defineInRange("healthPercentGuardNeed", 0.3, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue RESIST_DAMAGE = BUILDER
            .comment("守护效果触发时超过最大生命值部分减伤比例，默认 0.6（60%）")
            .defineInRange("resistDamage", 0.6, 0.0, 1.0);

    // === 预言卡 · 墓园 ===
    public static final ModConfigSpec.DoubleValue GRAVEYARD_DAMAGE = BUILDER
            .comment("墓园预言卡伤害，默认 50")
            .defineInRange("graveyardDamage", 50.0, 0.0, Double.MAX_VALUE);

    // === 预言卡 · 纷争 ===
    public static final ModConfigSpec.DoubleValue CONFLICT_MARK_RANGE = BUILDER
            .comment("纷争预言卡标记后，敌对生物检测被标记实体的范围（格），默认 16")
            .defineInRange("conflictMarkRange", 16.0, 1.0, 128.0);

    public static final ModConfigSpec.DoubleValue CONFLICT_CARD_REACH = BUILDER
            .comment("纷争预言卡右键选中实体的最远距离（格），默认 32")
            .defineInRange("conflictCardReach", 32.0, 4.0, 128.0);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CONFLICT_MARK_BLACKLIST = BUILDER
            .comment("纷争预言卡无法标记的实体类型 ID 列表（格式：modid:entity_id），用于排除不应被标记的实体")
            .defineListAllowEmpty("conflictMarkBlacklist",
                    List.of("dummmmmmy:target_dummy"),
                    obj -> obj instanceof String);

    // === 怀中御守 / 狐火 ===
    public static final ModConfigSpec.IntValue KAICHU_OMAMORI_COOLDOWN = BUILDER
            .comment("怀中御守的冷却时间（秒），默认 24 秒")
            .defineInRange("KaichuOmamoriCooldownSeconds", 24, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue FOX_FIRE_LIFETIME = BUILDER
            .comment("狐火力场的持续时间（秒），默认 15 秒")
            .defineInRange("FoxFireLifetimeSeconds", 15, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TOOLSMITH_BLUEPRINT_TRADE_CHANCE = BUILDER
            .comment("工具匠（5级）出售蓝图的概率，默认 1.0（100%）")
            .defineInRange("toolsmithBlueprintTradeChance", 1.0, 0.0, 1.0);

    // === 染梦裂隙污染 ===
    public static final ModConfigSpec.BooleanValue DYEDREAM_CRACK_CONTAMINATION_ENABLED = BUILDER
            .comment("染梦裂隙是否向外污染地形，默认 true")
            .define("dyedreamCrackContaminationEnabled", true);

    public static final ModConfigSpec.IntValue DYEDREAM_CRACK_CONTAMINATION_RADIUS = BUILDER
            .comment("染梦裂隙的污染半径（格），默认 5")
            .defineInRange("dyedreamCrackContaminationRadius", 5, 1, 32);

    // === 逐梦列车车票 ===
    public static final ModConfigSpec.BooleanValue TRAIN_TICKET_ONE_PER_DIMENSION = BUILDER
            .comment("逐梦列车车票每个维度只能一人使用（全服共享），默认 false")
            .define("trainTicketOnePerDimension", false);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> TRAIN_TICKET_DIMENSION_WHITELIST = BUILDER
            .comment("逐梦列车车票允许停靠的维度白名单（格式：modid:dimension_id），"
                    + "\n不在白名单中的维度使用时会提示「逐梦列车并没有在这里设立站点，无法停靠」"
                    + "\n默认包含原版三维度与模组的染梦世界、灯影之下、风之旅途")
            .defineListAllowEmpty("trainTicketDimensionWhitelist",
                    List.of("minecraft:overworld", "minecraft:the_nether", "minecraft:the_end",
                            "pasterdream:dyedream_world", "pasterdream:lamp_shadow_world",
                            "pasterdream:wind_journey_world"),
                    obj -> obj instanceof String);

    // === BOSS 限伤系统 · 全局配置 ===
    public static final ModConfigSpec.BooleanValue BOSS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP = BUILDER
            .comment("暗影难度是否影响 BOSS 限伤值（方案B：难度越高 damageCap 越低），默认 false")
            .define("bossShadowDifficultyAffectsDamageCap", false);

    public static final ModConfigSpec.BooleanValue BOSS_DAMAGE_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS 单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("bossDamageCapEnabled", true);

    public static final ModConfigSpec.BooleanValue BOSS_DPS_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS DPS 限制（每秒总伤害上限），默认 false。"
                    + "\n仅在启用单次伤害限制（bossDamageCapEnabled=true）时生效")
            .define("bossDpsCapEnabled", false);

    public static final ModConfigSpec.BooleanValue BOSS_RANGE_CAP_ENABLED = BUILDER
            .comment("是否启用 BOSS 远距离减伤（超出限伤距离后伤害线性衰减），默认 false")
            .define("bossRangeCapEnabled", false);

    public static final ModConfigSpec.DoubleValue BOSS_DAMAGE_CAP = BUILDER
            .comment("BOSS 单次受击伤害上限（同时也是 DPS 桶容量），默认 40")
            .defineInRange("bossDamageCap", 40.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue BOSS_DPS_CAP = BUILDER
            .comment("BOSS DPS 桶每秒恢复量，默认 200")
            .defineInRange("bossDpsCap", 200.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue BOSS_RANGE_CAP = BUILDER
            .comment("BOSS 限伤距离衰减起始距离（格），超过此距离伤害线性衰减，默认 12")
            .defineInRange("bossRangeCap", 12.0, 1.0, 256.0);

    // === BOSS 限伤系统 · 亚伦柯斯之触独立配置 ===
    public static final ModConfigSpec.BooleanValue AARONCOS_DAMAGE_CAP_INDEPENDENT = BUILDER
            .comment("亚伦柯斯之触（左右手）是否使用独立限伤配置（否则沿用上方全局 BOSS 限伤配置），默认 true")
            .define("aaroncosDamageCapIndependent", true);

    public static final ModConfigSpec.BooleanValue AARONCOS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP = BUILDER
            .comment("亚伦柯斯之触：暗影难度是否影响限伤值（难度越高 damageCap 越低），默认 false")
            .define("aaroncosShadowDifficultyAffectsDamageCap", false);

    public static final ModConfigSpec.BooleanValue AARONCOS_DAMAGE_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("aaroncosDamageCapEnabled", true);

    public static final ModConfigSpec.BooleanValue AARONCOS_DPS_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用 DPS 限制（每秒总伤害上限），默认 true。"
                    + "\n仅在启用单次伤害限制（aaroncosDamageCapEnabled=true）时生效")
            .define("aaroncosDpsCapEnabled", true);

    public static final ModConfigSpec.BooleanValue AARONCOS_RANGE_CAP_ENABLED = BUILDER
            .comment("亚伦柯斯之触：是否启用远距离减伤（超出限伤距离后伤害线性衰减），默认 false")
            .define("aaroncosRangeCapEnabled", false);

    public static final ModConfigSpec.DoubleValue AARONCOS_DAMAGE_CAP = BUILDER
            .comment("亚伦柯斯之触：单次受击伤害上限（同时也是 DPS 桶容量），默认 35")
            .defineInRange("aaroncosDamageCap", 35.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue AARONCOS_DPS_CAP = BUILDER
            .comment("亚伦柯斯之触：DPS 桶每秒恢复量，默认 200")
            .defineInRange("aaroncosDpsCap", 200.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue AARONCOS_RANGE_CAP = BUILDER
            .comment("亚伦柯斯之触：限伤距离衰减起始距离（格），默认 12")
            .defineInRange("aaroncosRangeCap", 12.0, 1.0, 256.0);

    public static final ModConfigSpec.BooleanValue AARONCOS_TOUCH_IMMUNE_TO_NEGATIVE_EFFECTS = BUILDER
            .comment("亚伦柯斯之触是否免疫负面状态效果（有害类效果），默认 true")
            .define("aaroncosTouchImmuneToNegativeEffects", true);

    // === BOSS 限伤系统 · 破风骑士独立配置 ===
    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_DAMAGE_CAP_INDEPENDENT = BUILDER
            .comment("破风骑士是否使用独立限伤配置（否则沿用上方全局 BOSS 限伤配置），默认 false")
            .define("windKnightDamageCapIndependent", false);

    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_DAMAGE_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用单次伤害限制（单次受击伤害上限），默认 true。"
                    + "\n设为 false 时，单发上限与 DPS 限制均不生效")
            .define("windKnightDamageCapEnabled", true);

    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_DPS_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用 DPS 限制（每秒总伤害上限），默认 true。"
                    + "\n仅在启用单次伤害限制（windKnightDamageCapEnabled=true）时生效")
            .define("windKnightDpsCapEnabled", true);

    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_RANGE_CAP_ENABLED = BUILDER
            .comment("破风骑士：是否启用远距离减伤（超出限伤距离后伤害线性衰减），默认 true")
            .define("windKnightRangeCapEnabled", true);

    public static final ModConfigSpec.DoubleValue WIND_KNIGHT_DAMAGE_CAP = BUILDER
            .comment("破风骑士：单次受击伤害上限（同时也是 DPS 桶容量），默认 20")
            .defineInRange("windKnightDamageCap", 20.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue WIND_KNIGHT_DPS_CAP = BUILDER
            .comment("破风骑士：DPS 桶每秒恢复量，默认 200")
            .defineInRange("windKnightDpsCap", 200.0, 1.0, 1_000_000.0);

    public static final ModConfigSpec.DoubleValue WIND_KNIGHT_RANGE_CAP = BUILDER
            .comment("破风骑士：限伤距离衰减起始距离（格），默认 8")
            .defineInRange("windKnightRangeCap", 8.0, 1.0, 256.0);

    public static final ModConfigSpec.DoubleValue WIND_KNIGHT_LIGHTNING_CHANCE = BUILDER
            .comment("破风骑士普攻命中后向目标头顶召唤落雷（与雷云实体使用的落雷投射物）的概率，默认 0.5（50%），取值范围最小值为0，最大值为1")
            .defineInRange("windKnightLightningChance", 0.5, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_PASSIVE_INVULNERABLE_ENABLED = BUILDER
            .comment("破风骑士：是否启用受击后概率获得短暂无敌的被动，默认 true")
            .define("windKnightPassiveInvulnerableEnabled", true);

    public static final ModConfigSpec.DoubleValue WIND_KNIGHT_PASSIVE_INVULNERABLE_CHANCE = BUILDER
            .comment("破风骑士：受击后短暂无敌的触发概率（受击后获得无敌的概率），默认 0.2（20%），取值范围最小值为0，最大值为1")
            .defineInRange("windKnightPassiveInvulnerableChance", 0.2, 0.0, 1.0);

    public static final ModConfigSpec.IntValue WIND_KNIGHT_PASSIVE_INVULNERABLE_TICKS = BUILDER
            .comment("破风骑士：受击后短暂无敌持续时间（tick，20 tick = 1 秒），默认 20")
            .defineInRange("windKnightPassiveInvulnerableTicks", 20, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue WIND_KNIGHT_IMMUNE_TO_NEGATIVE_EFFECTS = BUILDER
            .comment("破风骑士是否免疫负面状态效果（有害类效果），默认 true")
            .define("windKnightImmuneToNegativeEffects", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isAaroncosTouchImmuneToNegativeEffects() {
        return AARONCOS_TOUCH_IMMUNE_TO_NEGATIVE_EFFECTS.get();
    }

    public static BossLimitValues getBossLimitValues(BossLimitProfile profile) {
        return switch (profile) {
            case AARONCOS -> AARONCOS_DAMAGE_CAP_INDEPENDENT.get()
                    ? new BossLimitValues(
                            AARONCOS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP.get(),
                            AARONCOS_DAMAGE_CAP_ENABLED.get(), AARONCOS_DPS_CAP_ENABLED.get(),
                            AARONCOS_RANGE_CAP_ENABLED.get(),
                            AARONCOS_DAMAGE_CAP.get().floatValue(), AARONCOS_DPS_CAP.get().floatValue(),
                            AARONCOS_RANGE_CAP.get())
                    : getBossLimitValues(BossLimitProfile.GLOBAL);
            case WIND_KNIGHT -> {
                BossLimitValues base = WIND_KNIGHT_DAMAGE_CAP_INDEPENDENT.get()
                        ? new BossLimitValues(
                                false,
                                WIND_KNIGHT_DAMAGE_CAP_ENABLED.get(), WIND_KNIGHT_DPS_CAP_ENABLED.get(),
                                WIND_KNIGHT_RANGE_CAP_ENABLED.get(),
                                WIND_KNIGHT_DAMAGE_CAP.get().floatValue(), WIND_KNIGHT_DPS_CAP.get().floatValue(),
                                WIND_KNIGHT_RANGE_CAP.get())
                        : getBossLimitValues(BossLimitProfile.GLOBAL);
                // 破风骑士前置风泊群岛，不受暗影难度影响，强制关闭难度缩放
                yield new BossLimitValues(false, base.damageCapEnabled(), base.dpsCapEnabled(),
                        base.rangeCapEnabled(), base.damageCap(), base.dpsCap(), base.rangeCap());
            }
            case GLOBAL -> new BossLimitValues(
                    BOSS_SHADOW_DIFFICULTY_AFFECTS_DAMAGE_CAP.get(),
                    BOSS_DAMAGE_CAP_ENABLED.get(), BOSS_DPS_CAP_ENABLED.get(), BOSS_RANGE_CAP_ENABLED.get(),
                    BOSS_DAMAGE_CAP.get().floatValue(), BOSS_DPS_CAP.get().floatValue(), BOSS_RANGE_CAP.get());
        };
    }

    public static boolean isTrainTicketDimensionAllowed(ResourceLocation dimension) {
        for (String idStr : TRAIN_TICKET_DIMENSION_WHITELIST.get()) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (dimension.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /** 查询指定实体类型是否在罪之预言卡秒杀列表中 */
    public static boolean isSinInstakillTarget(EntityType<?> type) {
        return isEntityTypeListed(SIN_INSTAKILL_ENTITIES.get(), type);
    }

    /** 查询指定实体类型是否在纷争预言卡标记黑名单中 */
    public static boolean isConflictMarkBlacklisted(EntityType<?> type) {
        return isEntityTypeListed(CONFLICT_MARK_BLACKLIST.get(), type);
    }

    /** 查询指定投掷物类型是否被鬼魂之面忽略 */
    public static boolean isGhostFaceProjectileBlacklisted(EntityType<?> type) {
        return isEntityTypeListed(GHOST_FACE_PROJECTILE_BLACKLIST.get(), type);
    }

    public static boolean isIridescentFairyJadeProjectileBlacklisted(EntityType<?> type) {
        return isEntityTypeListed(IRIDESCENT_FAIRY_JADE_PROJECTILE_BLACKLIST.get(), type);
    }

    public static List<EntityType<?>> getIridescentFairyJadeProjectileTypes() {
        List<EntityType<?>> list = new ArrayList<>();
        for (String idStr : IRIDESCENT_FAIRY_JADE_PROJECTILE_WHITELIST.get()) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (id != null) {
                BuiltInRegistries.ENTITY_TYPE.getOptional(id).ifPresent(list::add);
            }
        }
        return list;
    }

    private static boolean isEntityTypeListed(List<? extends String> ids, EntityType<?> type) {
        for (String idStr : ids) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (id == null) {
                continue;
            }
            if (BuiltInRegistries.ENTITY_TYPE.getOptional(id).map(entityType -> entityType == type).orElse(false)) {
                return true;
            }
        }
        return false;
    }

    /** 卡莱的调料瓶正面效果池（按配置即时解析） */
    public static List<Holder<MobEffect>> getCalaisSpiceBottleEffects() {
        return resolveEffects(CALAIS_SPICE_BOTTLE_BUFFS.get());
    }

    /** 卡莱的调料瓶负面效果池（按配置即时解析） */
    public static List<Holder<MobEffect>> getCalaisSpiceBottleDebuffs() {
        return resolveEffects(CALAIS_SPICE_BOTTLE_DEBUFFS.get());
    }

    private static List<Holder<MobEffect>> resolveEffects(List<? extends String> ids) {
        List<Holder<MobEffect>> list = new ArrayList<>();
        for (String idStr : ids) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (id == null) {
                continue;
            }
            BuiltInRegistries.MOB_EFFECT.getHolder(id).ifPresent(list::add);
        }
        return list;
    }

    /** 暗影合金弩随机负面效果缓存（按配置即时解析） */
    public static List<Holder<MobEffect>> getShadowAlloyCrossbowDebuffs() {
        List<Holder<MobEffect>> list = new ArrayList<>();
        for (String idStr : SHADOW_ALLOY_CROSSBOW_DEBUFFS.get()) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (id == null) {
                continue;
            }
            BuiltInRegistries.MOB_EFFECT.getHolder(id).ifPresent(list::add);
        }
        return list;
    }

    public static boolean isCapturableEntity(EntityType<?> type) {
        for (String idStr : CREATURE_CAPTURE_ENTITIES.get()) {
            ResourceLocation id = ResourceLocation.tryParse(idStr);
            if (id == null) {
                continue;
            }
            if (BuiltInRegistries.ENTITY_TYPE.getOptional(id).map(entityType -> entityType == type).orElse(false)) {
                return true;
            }
        }
        return false;
    }

    private Config() {
    }
}
