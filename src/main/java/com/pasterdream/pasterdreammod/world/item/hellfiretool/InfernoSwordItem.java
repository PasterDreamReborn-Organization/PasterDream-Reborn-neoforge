package com.pasterdream.pasterdreammod.world.item.hellfiretool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.MinionFilterHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import java.util.List;

/**
 * 『融骸』狱炎剑 —
 *
 * 战技 · 融骸斩击：冷却就绪时，近战命中以命中点为中心 3 格范围（横扫之刃每级 +1，上限 6），
 *                 对其中敌人造成攻击力 50% 的熔岩伤害，冷却 2s。
 *                 爆炸伤害计入锋利加成，并受亡灵杀手/节肢杀手按生物类型加成。
 * 被动 · 引燃：命中点燃目标 4 秒，已燃烧目标延长 3 秒（爆炸波及的目标同样生效）。
 *              每级火焰附加额外增加 2 秒燃烧时长。
 */
public class InfernoSwordItem extends SwordItem {

    private static final int SKILL_COOLDOWN_TICKS = 40; // 战技冷却 2s
    private static final double AOE_RADIUS = 3.0; // 爆炸基础半径(格)
    private static final double AOE_RADIUS_MAX = 6.0; // 爆炸半径上限(格)
    private static final double AOE_RADIUS_PER_SWEEPING = 1.0; // 每级横扫之刃增加的爆炸半径(格)
    private static final float AOE_DAMAGE_RATIO = 0.5f; // 攻击力转化为范围熔岩伤害的比例
    private static final int PASSIVE_FIRE_TICKS = 60; // 被动引燃延长 tick 数 (3s)
    private static final int PASSIVE_IGNITE_SECONDS = 4; // 被动引燃秒数
    private static final int FIRE_ASPECT_BONUS_SECONDS = 2; // 每级火焰附加增加的被动燃烧时长(秒)
    private static final float HIT_SOUND_VOLUME = 1.5f; // 命中音效音量
    private static final float DRAGON_SOUND_VOLUME = 1.0f; // 龙息爆炸音效音量
    private static final int BASE_PARTICLE_COUNT = 64; // 基础爆炸粒子数量
    private static final double BASE_PARTICLE_SPREAD = 1.5; // 基础爆炸粒子散布(格)

    public InfernoSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.fireResistant().attributes(SwordItem.createAttributes(tier, damage, speed)));
    }

    /** 命中：战技冷却就绪则自动引发范围熔岩爆炸。被动引燃于攻击命中前由 AttackEntityEvent 施加，确保致死的点燃目标掉落熟食。 */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player
                && !player.level().isClientSide()
                && !SkillLockHelper.isSkillLockedSilent(player)
                && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            triggerInfernoBlast(player, stack, target);
            SkillCooldownHelper.applySharedCooldown(player, SKILL_COOLDOWN_TICKS);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    /** 范围熔岩爆炸：以命中点为中心，对范围内敌人造成攻击力 50% 的熔岩伤害并施加引燃。横扫之刃每级扩大 1 格半径。 */
    private void triggerInfernoBlast(Player player, ItemStack stack, LivingEntity center) {
        Level level = center.level();
        Holder<Enchantment> sweeping = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWEEPING_EDGE);
        double radius = Math.min(AOE_RADIUS + stack.getEnchantmentLevel(sweeping) * AOE_RADIUS_PER_SWEEPING,
                AOE_RADIUS_MAX);
        level.playSound(null, center.getX(), center.getY(), center.getZ(),
                ModSounds.SKILL_HIT_HEAVY.get(), SoundSource.NEUTRAL, HIT_SOUND_VOLUME, 1.0f);
        level.playSound(null, center.getX(), center.getY(), center.getZ(),
                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                SoundSource.NEUTRAL, DRAGON_SOUND_VOLUME, 1.0f);
        if (level instanceof ServerLevel serverLevel) {
            double spread = radius * (BASE_PARTICLE_SPREAD / AOE_RADIUS);
            int count = (int) Math.round(BASE_PARTICLE_COUNT * radius / AOE_RADIUS);
            serverLevel.sendParticles(ParticleTypes.LAVA, center.getX(), center.getY(), center.getZ(),
                    count, spread, spread * 1.2, spread, 0.5);
        }

        Holder<Enchantment> fireAspect = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT);
        int fireAspectLevel = stack.getEnchantmentLevel(fireAspect);
        Holder<DamageType> lavaHolder = level.registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.LAVA);

        AABB area = center.getBoundingBox().inflate(radius);
        List<LivingEntity> affected = level.getEntitiesOfClass(LivingEntity.class, area,
                e -> e != player && e.isAlive() && !(e instanceof ArmorStand)
                        && !MinionFilterHelper.isOwnedMinion(e, player)
                        && e.distanceToSqr(center) <= radius * radius);
        for (LivingEntity entity : affected) {
            DamageSource source = new DamageSource(lavaHolder, player, player);
            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            atk = EnchantmentHelper.modifyDamage((ServerLevel) level, stack, entity, source, atk);
            float dmg = atk * AOE_DAMAGE_RATIO * SkillCooldownHelper.getSkillDamageMultiplier(player);
            if (entity != center) {
                applyIgnite(entity, fireAspectLevel);
            }
            entity.invulnerableTime = 0;
            entity.hurt(source, dmg);
        }
    }

    /** 被动引燃：未燃烧则点燃 4s，已燃烧则延长 3s；每级火焰附加额外增加 2s。 */
    private static void applyIgnite(LivingEntity target, int fireAspectLevel) {
        int bonusSeconds = fireAspectLevel * FIRE_ASPECT_BONUS_SECONDS;
        if (target.isOnFire()) {
            target.setRemainingFireTicks(target.getRemainingFireTicks() + PASSIVE_FIRE_TICKS + bonusSeconds * 20);
        } else {
            target.igniteForSeconds(PASSIVE_IGNITE_SECONDS + bonusSeconds);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.8"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.9"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.inferno_sword.10"));
    }

    /** 被动 · 引燃：在近战命中结算伤害前点燃目标，保证一击致死也能掉落熟食。 */
    @EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
    public static class InfernoPassiveHandler {
        @SubscribeEvent
        public static void onAttackEntity(AttackEntityEvent event) {
            Player player = event.getEntity();
            if (player.level().isClientSide()) return;
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof InfernoSwordItem)) return;
            if (!(event.getTarget() instanceof LivingEntity target)) return;
            Holder<Enchantment> fireAspect = player.level().registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT);
            applyIgnite(target, stack.getEnchantmentLevel(fireAspect));
        }
    }
}
