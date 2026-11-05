package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** 融梦水晶护甲的融梦修补、套装增益和理智光环。 */
public final class MeltDreamCrystalArmorHandler {
    private static final ResourceLocation SAN_AURA_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_armor_san_aura");
    private static final double REPAIR_COST = 0.01;
    private static final double REPAIR_COST_FULL_SET = 0.005;
    private static final double SAN_AURA_HIGH = 1.2;
    private static final double SAN_AURA_LOW = 0.6;

    private MeltDreamCrystalArmorHandler() {
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.isSpectator()) return;
        boolean fullSet = MeltDreamCrystalArmorItem.hasFullSet(player);
        if (player.tickCount % 10 == 0) {
            repairMeltArmor(player, fullSet ? REPAIR_COST_FULL_SET : REPAIR_COST);
        }
        if (fullSet) {
            player.addEffect(new MobEffectInstance(ModEffects.MELT_DREAM_CRYSTAL_ARMOR, 25, 0, true, false, false));
            player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_UP, 25, 0, true, false, false));
            applySanAura(player);
        } else {
            removeSanAura(player);
        }
    }

    private static void repairMeltArmor(ServerPlayer player, double cost) {
        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        boolean free = !energy.getIsOrNotNeedConsumeDreamEnergy() || player.isCreative();
        double effectiveCost = MeltDreamEnergyHelper.getEffectiveConsumeCost(energy, cost);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack stack = player.getItemBySlot(slot);
            if (!(stack.getItem() instanceof MeltDreamCrystalArmorItem)) continue;
            if (stack.getDamageValue() < 1) continue;
            if (!free && energy.getMeltDreamEnergy() <= effectiveCost) continue;
            if (!free) {
                energy.addMeltDreamEnergy(-effectiveCost);
                player.syncData(ModAttachments.MELT_DREAM_ENERGY);
            }
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    private static void applySanAura(ServerPlayer player) {
        double energy = MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player);
        double max = MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergyEffective(player);
        double amount = (max > 0 && energy / max > 0.5) ? SAN_AURA_HIGH : SAN_AURA_LOW;
        AttributeInstance attr = player.getAttribute(ModAttributes.SAN_VARIABILITY);
        if (attr == null) return;
        AttributeModifier existing = attr.getModifier(SAN_AURA_ID);
        if (existing != null && existing.amount() == amount) return;
        attr.removeModifier(SAN_AURA_ID);
        attr.addTransientModifier(new AttributeModifier(SAN_AURA_ID, amount, AttributeModifier.Operation.ADD_VALUE));
    }

    private static void removeSanAura(ServerPlayer player) {
        AttributeInstance attr = player.getAttribute(ModAttributes.SAN_VARIABILITY);
        if (attr != null) attr.removeModifier(SAN_AURA_ID);
    }
}
