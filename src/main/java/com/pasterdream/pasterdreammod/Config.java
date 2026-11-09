package com.pasterdream.pasterdreammod;

import java.util.ArrayList;
import java.util.List;
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

    public static final ModConfigSpec SPEC = BUILDER.build();

    /** 查询指定实体类型是否在罪之预言卡秒杀列表中 */
    public static boolean isSinInstakillTarget(EntityType<?> type) {
        return isEntityTypeListed(SIN_INSTAKILL_ENTITIES.get(), type);
    }

    /** 查询指定实体类型是否在纷争预言卡标记黑名单中 */
    public static boolean isConflictMarkBlacklisted(EntityType<?> type) {
        return isEntityTypeListed(CONFLICT_MARK_BLACKLIST.get(), type);
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
