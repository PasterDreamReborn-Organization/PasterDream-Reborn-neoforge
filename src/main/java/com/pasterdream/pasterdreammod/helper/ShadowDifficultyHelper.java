package com.pasterdream.pasterdreammod.helper;

import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.init.ModGameRules;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.registries.BuiltInRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 暗影生物独立难度辅助类。
 * 支持世界级 gamerule 默认值 + 每玩家独立覆盖。
 */
public final class ShadowDifficultyHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowDifficultyHelper.class);
    private static final String PERSISTENT_KEY = "pasterdream.shadowDifficulty";

    private ShadowDifficultyHelper() {}

    // ===== 难度查询 =====

    /** 获取世界默认暗影难度等级 (0-3)，读取 gamerule */
    public static int getDifficulty(Level level) {
        int diff = level.getLevelData().getGameRules().getInt(ModGameRules.SHADOW_DIFFICULTY);
        return clampTier(diff);
    }

    /** 获取玩家的暗影难度等级：有个人覆盖则用个人覆盖，否则回退到默认玩家 gamerule */
    public static int getDifficulty(Player player) {
        if (player.getPersistentData().contains(PERSISTENT_KEY)) {
            return clampTier(player.getPersistentData().getInt(PERSISTENT_KEY));
        }
        int diff = player.level().getLevelData().getGameRules()
                .getInt(ModGameRules.PLAYER_SHADOW_DIFFICULTY);
        return clampTier(diff);
    }

    /**
     * 获取实体关联的难度上下文。
     * 优先级：源玩家 tag → 攻击目标 → 最近玩家 → 世界默认
     */
    public static int getDifficultyContext(LivingEntity entity) {
        // 1. 低 SAN 刷怪时写入的源玩家 UUID
        if (entity.getPersistentData().hasUUID("ShadowSourcePlayer")) {
            Player source = entity.level().getPlayerByUUID(
                    entity.getPersistentData().getUUID("ShadowSourcePlayer"));
            if (source != null) return getDifficulty(source);
        }
        // 2. 实体的攻击目标
        if (entity instanceof Mob mob && mob.getTarget() instanceof Player target) {
            return getDifficulty(target);
        }
        // 3. 最近玩家
        Player nearest = entity.level().getNearestPlayer(entity, 64);
        if (nearest != null) return getDifficulty(nearest);
        // 4. 世界默认
        return getDifficulty(entity.level());
    }

    // ===== 每玩家覆盖管理 =====

    /** 设置玩家的暗影难度覆盖 */
    public static void setPlayerDifficulty(Player player, int tier) {
        player.getPersistentData().putInt(PERSISTENT_KEY, clampTier(tier));
    }

    /** 清除玩家的暗影难度覆盖，恢复使用世界默认 */
    public static void clearPlayerDifficulty(Player player) {
        player.getPersistentData().remove(PERSISTENT_KEY);
    }

    /** 玩家是否有个人难度覆盖 */
    public static boolean hasPlayerOverride(Player player) {
        return player.getPersistentData().contains(PERSISTENT_KEY);
    }

    public static double getLootMultiplier(Level level) {
        return getValue(Config.SHADOW_LOOT_MULTIPLIERS.get(), getDifficulty(level), 1.0);
    }

    public static boolean isSpecialSkillEnabled(Player player) {
        int tier = getDifficulty(player);
        var skills = Config.SHADOW_SPECIAL_SKILLS_ENABLED.get();
        if (skills == null || tier >= skills.size()) return tier > 0;
        return Boolean.parseBoolean(skills.get(tier));
    }

    public static double getAttackMultiplier(Player player) {
        return getValue(Config.SHADOW_ATTACK_MULTIPLIERS.get(), getDifficulty(player), 1.0);
    }

    public static float getGolemSkillDamage(Player player) {
        return (float) (Config.SHADOW_GOLEM_SKILL_DAMAGE.get() * getAttackMultiplier(player));
    }

    public static float getShadowHandSanDrain(Player player) {
        return (float) (Config.SHADOW_HAND_SAN_DRAIN.get() * getAttackMultiplier(player));
    }

    public static double getLootMultiplier(Player player) {
        return getValue(Config.SHADOW_LOOT_MULTIPLIERS.get(), getDifficulty(player), 1.0);
    }

    public static double getLootMultiplier(int tier) {
        return getValue(Config.SHADOW_LOOT_MULTIPLIERS.get(), clampTier(tier), 1.0);
    }

    public static double getHealthMultiplier(int tier) {
        return getValue(Config.SHADOW_HEALTH_MULTIPLIERS.get(), clampTier(tier), 1.0);
    }

    public static double getAttackMultiplier(int tier) {
        return getValue(Config.SHADOW_ATTACK_MULTIPLIERS.get(), clampTier(tier), 1.0);
    }

    public static double getSpeedMultiplier(int tier) {
        return getValue(Config.SHADOW_SPEED_MULTIPLIERS.get(), clampTier(tier), 1.0);
    }

    /** 据难度等级查询特殊技能是否启用 */
    public static boolean isSpecialSkillEnabled(int tier) {
        int t = clampTier(tier);
        var skills = Config.SHADOW_SPECIAL_SKILLS_ENABLED.get();
        if (skills == null || t >= skills.size()) return t > 0;
        return Boolean.parseBoolean(skills.get(t));
    }

    public static float getGolemSkillDamage(int tier) {
        return (float) (Config.SHADOW_GOLEM_SKILL_DAMAGE.get() * getAttackMultiplier(tier));
    }

    /**
     * 根据 SAN 区间尝试生成暗影生物。
     * 使用玩家个人难度进行门控和概率计算。
     */
    public static void tryLowSanSpawn(ServerPlayer player, String zone) {
        var level = player.serverLevel();
        int tier = getDifficulty(player);
        if (tier == 0) return;
        if (level.isDay()) return;
        if (Config.LOW_SAN_SPAWN_REQUIRES_SPECIAL_SKILL.get() && !isSpecialSkillEnabled(tier)) return;

        double prob = getZoneBaseProb(zone, tier);
        if (prob <= 0) return;

        if (player.getRandom().nextDouble() >= prob) return;

        int maxNearby = Config.LOW_SAN_SPAWN_MAX_NEARBY.get();
        if (maxNearby > 0) {
            int nearby = countNearbyShadowMobs(player);
            if (nearby >= maxNearby) return;
        }

        List<? extends String> entities = getZoneEntities(zone);
        if (entities == null || entities.isEmpty()) return;

        spawnRandomEntity(player, entities);
    }

    private static int countNearbyShadowMobs(ServerPlayer player) {
        double range = Config.LOW_SAN_SPAWN_RADIUS_MAX.get();
        AABB area = AABB.ofSize(player.position(), range * 2, range * 2, range * 2);
        return player.level().getEntitiesOfClass(LivingEntity.class, area,
                e -> e.getType().is(ModEntityTypeTags.SHADOW_MOB) && e != player).size();
    }

    private static double getZoneBaseProb(String zone, int tier) {
        var list = switch (zone) {
            case "high"     -> Config.LOW_SAN_SPAWN_HIGH_PROBS.get();
            case "medium"   -> Config.LOW_SAN_SPAWN_MEDIUM_PROBS.get();
            case "low"      -> Config.LOW_SAN_SPAWN_LOW_PROBS.get();
            case "critical" -> Config.LOW_SAN_SPAWN_CRITICAL_PROBS.get();
            default -> null;
        };
        return getValue(list, tier, 0.0);
    }

    private static List<? extends String> getZoneEntities(String zone) {
        return switch (zone) {
            case "high"     -> Config.LOW_SAN_SPAWN_HIGH_ENTITIES.get();
            case "medium"   -> Config.LOW_SAN_SPAWN_MEDIUM_ENTITIES.get();
            case "low"      -> Config.LOW_SAN_SPAWN_LOW_ENTITIES.get();
            case "critical" -> Config.LOW_SAN_SPAWN_CRITICAL_ENTITIES.get();
            default -> List.of();
        };
    }

    /**
     * 从权重列表中随机选取一个实体并生成在玩家附近。
     * 条目格式: "modid:entity_id:weight"
     */
    private static void spawnRandomEntity(ServerPlayer player, List<? extends String> entries) {
        double totalWeight = 0;
        for (String entry : entries) {
            double w = parseWeight(entry);
            if (w > 0) totalWeight += w;
        }
        if (totalWeight <= 0) return;

        double roll = player.getRandom().nextDouble() * totalWeight;
        double cumulative = 0;
        for (String entry : entries) {
            double w = parseWeight(entry);
            if (w <= 0) continue;
            cumulative += w;
            if (roll <= cumulative) {
                spawnFromEntry(player, entry);
                return;
            }
        }
    }

    private static void spawnFromEntry(ServerPlayer player, String entry) {
        String[] parts = entry.split(":", 3);
        if (parts.length < 2) return;
        ResourceLocation rl = ResourceLocation.tryBuild(parts[0], parts[1]);
        EntityType<?> type = rl == null ? null : BuiltInRegistries.ENTITY_TYPE.getOptional(rl).orElse(null);
        if (type == null) {
            LOGGER.warn("lowSanSpawn: unknown entity type '{}', skipping", rl);
            return;
        }

        ServerLevel level = player.serverLevel();
        int maxLight = Config.LOW_SAN_SPAWN_MAX_LIGHT.get();
        double minR = Config.LOW_SAN_SPAWN_RADIUS_MIN.get();
        double maxR = Config.LOW_SAN_SPAWN_RADIUS_MAX.get();
        var random = player.getRandom();

        for (int attempt = 0; attempt < 8; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double dist = minR + random.nextDouble() * (maxR - minR);
            double sx = player.getX() + Math.cos(angle) * dist;
            double sz = player.getZ() + Math.sin(angle) * dist;
            double sy = player.getY();

            BlockPos pos = BlockPos.containing(sx, sy, sz);

            if (maxLight < 15 && level.getMaxLocalRawBrightness(pos) > maxLight) continue;

            BlockPos ground = pos;
            while (ground.getY() > level.getMinBuildHeight() && level.getBlockState(ground).isAir()) {
                ground = ground.below();
            }
            if (!level.getBlockState(ground).isSolid()) continue;

            BlockPos spawnPos = ground.above();
            if (!level.getBlockState(spawnPos).is(Blocks.AIR)) continue;
            if (!level.getBlockState(spawnPos.above()).is(Blocks.AIR)) continue;

            // 入场事件按源玩家缩放属性，因此必须在实体加入世界之前标记。
            Entity e = type.spawn(level, entity -> {
                entity.getPersistentData().putUUID("ShadowSourcePlayer", player.getUUID());
                // 堕落者之印：源玩家佩戴封印时，暗影生物协助作战
                if (CuriosApi.getCuriosInventory(player)
                        .map(h -> h.findFirstCurio(ModItems.SEAL_OF_THE_CORRUPTED.get()).isPresent())
                        .orElse(false)) {
                    entity.getPersistentData().putBoolean("pasterdream:seal_friendly", true);
                }
            }, spawnPos, MobSpawnType.MOB_SUMMONED, false, false);
            if (e != null) {
                e.setYRot(random.nextFloat() * 360F);
                return;
            }
        }
    }

    private static double parseWeight(String entry) {
        String[] parts = entry.split(":", 3);
        if (parts.length < 3) return 0;
        try {
            return Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ===== 通用工具 =====

    private static int clampTier(int tier) {
        if (tier < 0) return 0;
        if (tier > 3) return 3;
        return tier;
    }

    private static double getValue(@Nullable List<? extends Double> list, int index, double fallback) {
        if (list == null || list.isEmpty()) return fallback;
        if (index < list.size()) return list.get(index);
        return list.get(list.size() - 1);
    }
}
