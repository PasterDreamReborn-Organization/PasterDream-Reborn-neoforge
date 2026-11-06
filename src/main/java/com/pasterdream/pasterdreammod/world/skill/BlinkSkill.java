package com.pasterdream.pasterdreammod.world.skill;

import com.pasterdream.pasterdreammod.helper.cooldown.CooldownReductionHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.item.curio.FracturedAngelStatueHandler;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class BlinkSkill {

    /** 瞬身术回避时长（tick）：基础 0.3s */
    private static final int EVASION_DURATION = 6;

    public static void execute(ServerPlayer player) {
        if (player == null || player.level().isClientSide()) return;

        boolean creative = player.getAbilities().instabuild;

        if (!creative) {
            if (player.getFoodData().getFoodLevel() <= 6) return;
            if (player.hasEffect(ModEffects.BLINK_COOLDOWN)) return;

            double consume = getAttributeValue(player, ModAttributes.BLINK_CONSUME, 1.0);
            player.causeFoodExhaustion((float) (3 * consume));

            doDisplacement(player);
            applyCooldown(player);
        } else {
            doDisplacement(player);
        }

        applyEvasionBuff(player);
        FracturedAngelStatueHandler.onBlink(player);
    }

    private static void doDisplacement(Player player) {
        double range = getAttributeValue(player, ModAttributes.BLINK_RANGE, 1.0);
        Vec3 look = player.getLookAngle();
        Vec3 delta;

        if (!player.isShiftKeyDown()) {
            if (player.isInWater()) {
                delta = new Vec3(look.x * range, look.y, look.z * range);
            } else {
                delta = new Vec3(look.x * range, look.y * 0.1 + 0.4, look.z * range);
            }
        } else {
            delta = new Vec3(-look.x * range, -look.y * 0.1 + 0.4, -look.z * range);
        }

        player.setDeltaMovement(delta);
        player.hurtMarked = true;
    }

    private static void applyCooldown(Player player) {
        double factor = CooldownReductionHelper.getCooldownFactor(
                player, ModAttributes.BLINK_COOLDOWN_REDUCTION);
        boolean hasElytra = player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
        int baseTicks = hasElytra ? 100 : 50;
        int duration = (int) Math.round(baseTicks * factor);
        if (duration <= 0) return;
        player.addEffect(new MobEffectInstance(ModEffects.BLINK_COOLDOWN, duration, 0,
                false, false, false));
    }

    private static void applyEvasionBuff(Player player) {
        player.addEffect(new MobEffectInstance(ModEffects.EVASION, EVASION_DURATION, 0,
                false, false));
    }

    private static double getAttributeValue(Player player, Holder<net.minecraft.world.entity.ai.attributes.Attribute> attr, double fallback) {
        AttributeInstance inst = player.getAttribute(attr);
        return inst != null ? inst.getValue() : fallback;
    }
}
