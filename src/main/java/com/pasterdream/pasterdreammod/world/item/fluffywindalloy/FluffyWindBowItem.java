package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

/** 萦风弓：消耗融梦能量发射无重力萦风箭，引雷附魔追加落雷。 */
public class FluffyWindBowItem extends BowItem {
    private static final double ENERGY_COST = 0.2;
    private static final double CHANNELING_EXTRA_COST = 0.1;

    public FluffyWindBowItem(Properties properties) {
        super(properties);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile normal = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (!(shooter instanceof Player player) || level.isClientSide()) return normal;
        int channeling = EnchantmentHelper.getItemEnchantmentLevel(
                level.registryAccess().holderOrThrow(Enchantments.CHANNELING), weapon);
        if (!tryConsumeEnergy(player, channeling > 0)) return normal;

        // 强化射击统一使用萦风箭；光灵箭的命中发光由 FluffyWindArrowEntity 保留
        FluffyWindArrowEntity arrow = new FluffyWindArrowEntity(level, shooter, ammo);
        if (normal instanceof AbstractArrow base) {
            arrow.setBaseDamage(base.getBaseDamage());
            arrow.pickup = base.pickup;
        }
        if (channeling > 0) {
            int flame = EnchantmentHelper.getItemEnchantmentLevel(
                    level.registryAccess().holderOrThrow(Enchantments.FLAME), weapon);
            arrow.enableChannelingLightning(flame);
        }
        return arrow;
    }

    private boolean tryConsumeEnergy(Player player, boolean channeling) {
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        if (!energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative()) return true;
        double cost = ENERGY_COST + (channeling ? CHANNELING_EXTRA_COST : 0.0);
        double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, cost);
        if (energy.getMeltDreamEnergy() < effectiveCost) return false;
        energy.addMeltDreamEnergy(-effectiveCost);
        player.syncData(ModAttachments.MELT_DREAM_ENERGY);
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.FLUFFY_WIND_ALLOY_INGOT.get())
                || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        for (int i = 1; i <= 6; i++) {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_bow." + i));
        }
    }
}
