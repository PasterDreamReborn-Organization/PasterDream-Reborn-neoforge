package com.pasterdream.pasterdreammod.world.item.meltdreamtool;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.MeltDreamArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.arrows.behavior.ArrowBehaviors;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

/**
 * 融梦弓 —— 融梦水晶系列远程武器。
 * <p>
 * 被动「融梦箭」：拥有融梦能量时射出的一箭消耗 0.2 融梦能量，本次箭矢伤害 +30% 并转为魔法伤害；
 * 能量不足时照常射出但不触发。处于染梦世界时，普通箭矢不消耗箭矢/耐久/融梦能量，
 * 且依旧享受 +30% 加成。
 * <p>
 * 与融梦水晶工具一样自带「融梦修补」（在背包中消耗融梦能量修复耐久）。
 */
public class MeltDreamBowItem extends BowItem implements MeltDreamTool {

    /** 单次强化射击消耗的融梦能量 */
    private static final double ENERGY_COST = 0.2;
    /** 强化射击伤害倍率 */
    private static final float DAMAGE_MULTIPLIER = 1.3F;
    /** 染梦世界维度键 */
    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));

    public MeltDreamBowItem(Properties properties) {
        super(properties);
    }

    /* 覆盖原版逻辑：插入融梦箭强化 / 染梦世界免耗逻辑，其余（附魔、拾取、音效）保持一致。 */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) {
            super.releaseUsing(stack, level, entityLiving, timeLeft);
            return;
        }

        boolean infinite = player.hasInfiniteMaterials()
                || getEnchantmentLevel(level, Enchantments.INFINITY, stack) > 0;
        ItemStack ammo = player.getProjectile(stack);

        int charge = this.getUseDuration(stack, entityLiving) - timeLeft;
        charge = EventHooks.onArrowLoose(stack, level, player, charge, !ammo.isEmpty() || infinite);
        if (charge < 0) {
            return;
        }
        if (ammo.isEmpty() && !infinite) {
            return;
        }
        if (ammo.isEmpty()) {
            ammo = new ItemStack(Items.ARROW);
        }

        float power = getPowerForTime(charge);
        if (power < 0.1F) {
            return;
        }

        boolean infiniteArrow = player.hasInfiniteMaterials()
                || (infinite && ammo.is(Items.ARROW))
                || (ammo.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(ammo, stack, player));
        // 染梦世界 + 普通箭矢：不消耗箭矢/耐久/融梦能量，仍享受强化
        boolean dyedreamFree = level.dimension().equals(DYEDREAM_WORLD)
                && ammo.is(Items.ARROW);

        boolean empowered = false;
        if (!level.isClientSide) {
            empowered = dyedreamFree || tryConsumeEnergy(player);
        }

        if (!level.isClientSide) {
            AbstractArrow arrow;
            if (empowered) {
                // 强化射击统一使用融梦箭；光灵箭的命中发光由 TrailArrowEntity 保留
                MeltDreamArrowEntity trailArrow = new MeltDreamArrowEntity(level, player, ammo, stack);
                ArrowBehaviors.applyBaseStats(trailArrow, ammo);
                arrow = trailArrow;
            } else {
                ArrowItem arrowItem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
                arrow = arrowItem.createArrow(level, ammo, player, stack);
            }
            arrow = this.customArrow(arrow, ammo, stack);
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
            if (power == 1.0F) {
                arrow.setCritArrow(true);
            }

            if (empowered) {
                arrow.setBaseDamage(arrow.getBaseDamage() * DAMAGE_MULTIPLIER);
            }

            if (!dyedreamFree) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            }
            if (infiniteArrow || (player.hasInfiniteMaterials()
                    && (ammo.is(Items.SPECTRAL_ARROW) || ammo.is(Items.TIPPED_ARROW)))) {
                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            level.addFreshEntity(arrow);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        if (!infiniteArrow && !dyedreamFree) {
            ammo.shrink(1);
            if (ammo.isEmpty()) {
                player.getInventory().removeItem(ammo);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    private static int getEnchantmentLevel(Level level, ResourceKey<Enchantment> enchantment, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().holderOrThrow(enchantment), stack);
    }

    /**
     * 尝试消耗 0.2 融梦能量换取强化。创造模式或「无需消耗」状态下不扣能量但仍视为强化。
     *
     * @return 是否触发强化
     */
    private boolean tryConsumeEnergy(Player player) {
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

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    /**
     * 允许抢夺附魔（原版抢夺仅限剑）。1.21 附魔可应用范围由数据驱动，
     * 因此同时覆盖附魔台（{@link #isPrimaryItemFor}）与铁砧 / 附魔书（{@link #supportsEnchantment}）。
     */
    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.LOOTING) || super.isPrimaryItemFor(stack, enchantment);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(Enchantments.LOOTING) || super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_bow.6"));
    }
}
