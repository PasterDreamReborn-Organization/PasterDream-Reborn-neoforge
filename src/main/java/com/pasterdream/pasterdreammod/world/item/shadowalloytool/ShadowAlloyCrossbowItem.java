package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.mixin.AbstractArrowAccessor;
import com.pasterdream.pasterdreammod.world.entity.ShadowAlloyArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * 暗影合金弩 —— 暗影合金系列远程武器。
 * <p>
 * 在原版弩基础上追加：
 * <ul>
 *   <li>被动「暗影飞矢」：发射时消耗 0.2 融梦能量，本次箭矢带标记，命中生物时由
 *       {@link ShadowAlloyCrossbowHandler} 施加 10s 易伤与随机 5s 负面效果；能量不足则照常发射，
 *       不触发被动。</li>
 *   <li>可附魔「无限」；装填/发射不消耗普通箭矢（类似原版弓的无限）。无限只免箭矢，不免融梦能量。</li>
 *   <li>自带力量 III（相当于原版力量 III），可与附魔力量 V 叠加（等效等级相加）。</li>
 * </ul>
 * 与暗影合金工具一致：防火、EPIC 稀有度、暗影合金锭修复。
 */
public class ShadowAlloyCrossbowItem extends CrossbowItem {

    private static final float ARROW_POWER = 3.15F;
    private static final float FIREWORK_POWER = 1.6F;
    /** 被动「暗影飞矢」单次发射消耗的融梦能量 */
    private static final double ENERGY_COST = 0.2;
    /** 内置力量等级（相当于原版力量 III），与附魔力量等级相加 */
    public static final int BUILT_IN_POWER = 3;

    public ShadowAlloyCrossbowItem(Properties properties) {
        super(properties.fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isCharged(stack)) {
            shootChargedProjectiles(level, player, hand, stack, computeShootingPower(stack), 1.0F);
            setCharged(stack, false);
            return InteractionResultHolder.consume(stack);
        }
        if (canCharge(player, stack)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int charge = this.getUseDuration(stack, entity) - timeLeft;
        float power = computeChargePower(charge, stack, entity);
        if (power >= 1.0F && !isCharged(stack) && loadProjectilesWithInfinity(entity, stack)) {
            SoundSource source = entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END,
                    source, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        // 1.21 附魔可应用范围由数据驱动；力量与无限原版仅限弓，本弩按来源放行
        return enchantment.is(Enchantments.POWER) || enchantment.is(Enchantments.INFINITY)
                || super.isPrimaryItemFor(stack, enchantment);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.POWER) || enchantment.is(Enchantments.INFINITY)
                || super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.SHADOW_ALLOY_INGOT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_crossbow.7"));
    }

    /* ============================ 装填（无限感知） ============================ */

    private static boolean canCharge(Player player, ItemStack stack) {
        return !player.getProjectile(stack).isEmpty() || hasInfinity(player.level(), stack);
    }

    private static boolean hasInfinity(Level level, ItemStack stack) {
        return enchantLevel(level, Enchantments.INFINITY, stack) > 0;
    }

    private static int enchantLevel(Level level, net.minecraft.resources.ResourceKey<Enchantment> enchantment, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(enchantment), stack);
    }

    /**
     * 尝试消耗 0.2 融梦能量以触发被动「暗影飞矢」。创造模式或「无需消耗」状态下不扣能量但仍触发。
     * 仅在服务端结算；能量不足时不扣能量、返回 {@code false}（照常发射但不带标记）。
     *
     * @return 本次发射是否触发被动
     */
    private static boolean tryConsumeEnergy(Level level, Player player) {
        if (level.isClientSide) {
            return false;
        }
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
        if (free) {
            return true;
        }
        // 消耗翻倍减益下按实际消耗判定，避免能量被扣成负数
        double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, ENERGY_COST);
        if (energy.getMeltDreamEnergy() < effectiveCost) {
            return false;
        }
        energy.addMeltDreamEnergy(-effectiveCost);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
        return true;
    }

    private static float computeChargePower(int time, ItemStack stack, LivingEntity entity) {
        float f = (float) time / getChargeDuration(stack, entity);
        return f > 1.0F ? 1.0F : f;
    }

    private static boolean loadProjectilesWithInfinity(LivingEntity shooter, ItemStack crossbow) {
        Level level = shooter.level();
        int multishot = enchantLevel(level, Enchantments.MULTISHOT, crossbow);
        int count = multishot == 0 ? 1 : 3;
        boolean creative = shooter.hasInfiniteMaterials();
        boolean infinity = shooter instanceof Player player && hasInfinity(level, crossbow);
        ItemStack ammo = shooter.getProjectile(crossbow);
        if (ammo.isEmpty() && infinity && !creative) {
            ammo = new ItemStack(Items.ARROW);
        }
        ItemStack template = ammo.copy();
        List<ItemStack> loaded = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                ammo = template.copy();
            }
            if (ammo.isEmpty() && creative) {
                ammo = new ItemStack(Items.ARROW);
                template = ammo.copy();
            }
            ItemStack single = loadSingleProjectile(shooter, crossbow, ammo, i > 0, creative, infinity);
            if (single.isEmpty()) {
                return false;
            }
            loaded.add(single);
        }
        crossbow.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(loaded));
        return true;
    }

    private static ItemStack loadSingleProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile,
                                                  boolean secondary, boolean creative, boolean infinity) {
        if (projectile.isEmpty()) {
            return ItemStack.EMPTY;
        }
        // 无限仅作用于普通箭矢：药水箭 / 光谱箭 / 烟花照常消耗；
        // 自定义箭矢可覆写 ArrowItem#isInfinite 声明自身是否享受无限。
        boolean infiniteProjectile = infinity
                && shooter instanceof Player player
                && (projectile.is(Items.ARROW)
                    || projectile.getItem() instanceof ArrowItem arrowItem
                        && arrowItem.isInfinite(projectile, crossbow, player));
        ItemStack loaded;
        if (creative || infiniteProjectile || secondary) {
            loaded = projectile.copy();
        } else {
            loaded = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof Player player) {
                player.getInventory().removeItem(projectile);
            }
        }
        return loaded;
    }

    /* ============================ 发射 ============================ */

    private static float computeShootingPower(ItemStack stack) {
        ChargedProjectiles charged = stack.get(DataComponents.CHARGED_PROJECTILES);
        return charged != null && charged.contains(Items.FIREWORK_ROCKET) ? FIREWORK_POWER : ARROW_POWER;
    }

    private static void shootChargedProjectiles(Level level, LivingEntity shooter, InteractionHand hand,
                                                ItemStack crossbow, float velocity, float inaccuracy) {
        List<ItemStack> projectiles = readChargedProjectiles(crossbow);
        // 被动「暗影飞矢」：玩家发射时按实有弹药结算一次 0.2 融梦能量；能量不足则不标记（不触发被动）
        boolean empowered = !projectiles.isEmpty()
                && (!(shooter instanceof Player player) || tryConsumeEnergy(level, player));
        float[] pitches = computeShotPitches(shooter);
        boolean creative = shooter.hasInfiniteMaterials();
        boolean infinity = shooter instanceof Player player && hasInfinity(level, crossbow);
        for (int i = 0; i < projectiles.size(); i++) {
            ItemStack projectile = projectiles.get(i);
            if (projectile.isEmpty()) {
                continue;
            }
            float angle = i == 0 ? 0.0F : (i == 1 ? -10.0F : 10.0F);
            fireProjectile(level, shooter, hand, crossbow, projectile, pitches[i % pitches.length],
                    creative || infinity, velocity, inaccuracy, angle, empowered);
        }
        afterCrossbowShot(level, shooter, crossbow);
    }

    private static void fireProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbow,
                                       ItemStack projectile, float soundPitch, boolean creativeOnly, float velocity,
                                       float inaccuracy, float angle, boolean empowered) {
        if (level.isClientSide) {
            return;
        }
        boolean firework = projectile.is(Items.FIREWORK_ROCKET);
        Projectile shot;
        if (firework) {
            shot = new FireworkRocketEntity(level, projectile, shooter, shooter.getX(),
                    shooter.getEyeY() - 0.15D, shooter.getZ(), true);
        } else {
            shot = createCrossbowArrow(level, shooter, crossbow, projectile, empowered);
            if (creativeOnly || angle != 0.0F) {
                ((AbstractArrow) shot).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
        }

        // 标记为暗影合金弩发射的弹射物：箭命中 / 烟花爆炸伤害时据此施加被动（仅在触发被动时标记）
        if (empowered) {
            shot.getPersistentData().putBoolean(ShadowAlloyCrossbowHandler.PROJECTILE_TAG, true);
        }

        LivingEntity target = shooter instanceof CrossbowAttackMob mob ? mob.getTarget() : null;
        Vector3f direction;
        if (target != null) {
            double dx = target.getX() - shooter.getX();
            double dz = target.getZ() - shooter.getZ();
            double dist = Math.sqrt(dx * dx + dz * dz);
            double dy = target.getY(0.3333333333333333) - shot.getY() + dist * 0.2F;
            direction = getProjectileShotVector(shooter, new Vec3(dx, dy, dz), angle);
        } else {
            Vec3 up = shooter.getUpVector(1.0F);
            Quaternionf rotation = new Quaternionf().setAngleAxis(angle * (float) (Math.PI / 180.0), up.x, up.y, up.z);
            Vec3 view = shooter.getViewVector(1.0F);
            direction = view.toVector3f().rotate(rotation);
        }
        shot.shoot(direction.x(), direction.y(), direction.z(), velocity, inaccuracy);

        crossbow.hurtAndBreak(firework ? 3 : 1, shooter, LivingEntity.getSlotForHand(hand));
        level.addFreshEntity(shot);
        level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT,
                SoundSource.PLAYERS, 1.0F, soundPitch);
    }

    private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = new Vector3f(vector3f).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double) vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = new Vector3f(vector3f).cross(vec3.toVector3f());
        }

        Vector3f vector3f2 = new Vector3f(vector3f).rotateAxis((float) (Math.PI / 2), vector3f1.x, vector3f1.y, vector3f1.z);
        return new Vector3f(vector3f).rotateAxis(angle * (float) (Math.PI / 180.0), vector3f2.x, vector3f2.y, vector3f2.z);
    }

    private static AbstractArrow createCrossbowArrow(Level level, LivingEntity shooter, ItemStack crossbow,
                                                     ItemStack projectile, boolean empowered) {
        AbstractArrow arrow;
        if (empowered) {
            arrow = new ShadowAlloyArrowEntity(level, shooter, projectile);
            ArrowBehaviors.applyBaseStats(arrow, projectile);
        } else {
            ArrowItem arrowItem = (ArrowItem) (projectile.getItem() instanceof ArrowItem ? projectile.getItem() : Items.ARROW);
            arrow = arrowItem.createArrow(level, projectile, shooter, null);
        }
        if (shooter instanceof Player) {
            arrow.setCritArrow(true);
        }
        arrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);

        // 自定义箭实体不在 #minecraft:arrows 中，穿透与力量附魔的数据驱动结算不会生效，这里手动补齐
        int piercing = enchantLevel(level, Enchantments.PIERCING, crossbow);
        if (piercing > 0) {
            ((AbstractArrowAccessor) arrow).pasterdream$setPierceLevel((byte) piercing);
        }
        int powerLevel = BUILT_IN_POWER + enchantLevel(level, Enchantments.POWER, crossbow);
        arrow.setBaseDamage(arrow.getBaseDamage() + (double) powerLevel * 0.5D + 0.5D);
        return arrow;
    }

    private static float[] computeShotPitches(LivingEntity shooter) {
        boolean flip = shooter.getRandom().nextBoolean();
        return new float[]{1.0F, computeRandomShotPitch(flip, shooter), computeRandomShotPitch(!flip, shooter)};
    }

    private static float computeRandomShotPitch(boolean low, LivingEntity shooter) {
        float base = low ? 0.63F : 0.43F;
        return 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.8F) + base;
    }

    private static void afterCrossbowShot(Level level, LivingEntity shooter, ItemStack crossbow) {
        if (shooter instanceof ServerPlayer serverPlayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, crossbow);
            }
            serverPlayer.awardStat(Stats.ITEM_USED.get(crossbow.getItem()));
        }
        setCharged(crossbow, false);
    }

    /* ============================ 组件存取 ============================ */

    private static List<ItemStack> readChargedProjectiles(ItemStack crossbow) {
        ChargedProjectiles charged = crossbow.get(DataComponents.CHARGED_PROJECTILES);
        return charged == null ? List.of() : charged.getItems();
    }

    private static void setCharged(ItemStack crossbow, boolean charged) {
        if (!charged) {
            crossbow.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
        }
    }
}
