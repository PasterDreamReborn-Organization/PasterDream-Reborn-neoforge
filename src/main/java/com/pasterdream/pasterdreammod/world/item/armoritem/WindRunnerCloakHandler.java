package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** 风行者披风的服务端飞行助力与动能伤害防护。 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class WindRunnerCloakHandler {
    private static final double BOOST_TARGET_SPEED = 1.5D;
    private static final double BOOST_ACCELERATION = 0.06D;

    private WindRunnerCloakHandler() {
    }

    public static boolean isWearing(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WIND_RUNNER_CLOAK.get());
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!player.isFallFlying() || !isWearing(player)) return;
        if (!player.getPersistentData().getBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP)) return;
        Vec3 look = player.getLookAngle();
        Vec3 motion = player.getDeltaMovement();
        double forward = motion.dot(look);
        double delta = Math.min(BOOST_TARGET_SPEED - forward, BOOST_ACCELERATION);
        if (delta <= 0.0D) return;
        player.setDeltaMovement(motion.add(look.scale(delta)));
        player.hurtMarked = true;
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (isWearing(player) && event.getSource().is(DamageTypes.FLY_INTO_WALL)) event.setCanceled(true);
    }
}
