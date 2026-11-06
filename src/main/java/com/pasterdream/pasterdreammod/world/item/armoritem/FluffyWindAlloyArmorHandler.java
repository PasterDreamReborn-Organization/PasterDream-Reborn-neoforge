package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** 萦风合金满套的天气、疾射、雷电防护、弹射物弹开和缓降。 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class FluffyWindAlloyArmorHandler {
    private static final double WEATHER_SPEED_BONUS = 0.20;
    private static final double WEATHER_CD_BONUS = 0.20;
    private static final double DRAW_TIME_REDUCTION = 0.25;
    private static final ResourceLocation WEATHER_SPEED_ID = id("wind_weather_speed");
    private static final ResourceLocation WEATHER_CD_ID = id("wind_weather_cooldown_reduction");
    private static final ResourceLocation DRAW_TIME_ID = id("wind_thunder_boon_draw_time_reduction");
    private static final float RAIN_DAMAGE_MULTIPLIER = 0.90F;
    private static final float THUNDER_DAMAGE_MULTIPLIER = 0.80F;
    private static final double DEFLECT_CHANCE = 0.5D;
    private static final double SLOW_FALL_GRAVITY_OFFSET = 0.07D;
    private static final float SLOW_FALL_EXHAUSTION = 0.05F;
    private static final int OATH_DURATION = 25;
    public static final String NBT_HOVER_JUMP = "pasterdream:fluffy_wind_alloy_hover_jump";

    private FluffyWindAlloyArmorHandler() {
    }

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        boolean fullSet = FluffyWindAlloyArmorItem.hasFullSet(serverPlayer);
        applyWeatherBonus(serverPlayer, fullSet && serverPlayer.level().isRaining());
        applyDrawTimeBonus(serverPlayer, fullSet);
        if (fullSet) {
            serverPlayer.addEffect(new MobEffectInstance(ModEffects.WIND_THUNDER_BOON, OATH_DURATION, 0,
                    true, false, false));
            applySlowFall(serverPlayer, true);
        }
        if (serverPlayer.onGround()) serverPlayer.getPersistentData().putBoolean(NBT_HOVER_JUMP, false);
    }

    private static void applyWeatherBonus(Player player, boolean active) {
        toggleModifier(player.getAttribute(Attributes.MOVEMENT_SPEED), WEATHER_SPEED_ID,
                WEATHER_SPEED_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, active);
        toggleModifier(player.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION), WEATHER_CD_ID,
                WEATHER_CD_BONUS, AttributeModifier.Operation.ADD_VALUE, active);
    }

    private static void applyDrawTimeBonus(Player player, boolean active) {
        toggleModifier(player.getAttribute(ModAttributes.RANGED_CHARGE_TIME_REDUCTION), DRAW_TIME_ID,
                DRAW_TIME_REDUCTION, AttributeModifier.Operation.ADD_VALUE, active);
    }

    private static void toggleModifier(AttributeInstance instance, ResourceLocation id,
                                       double amount, AttributeModifier.Operation operation, boolean active) {
        if (instance == null) return;
        if (active) {
            if (instance.getModifier(id) == null) instance.addTransientModifier(new AttributeModifier(id, amount, operation));
        } else {
            instance.removeModifier(id);
        }
    }

    public static void applySlowFall(Player player, boolean consumeHunger) {
        if (!player.getPersistentData().getBoolean(NBT_HOVER_JUMP)) return;
        if (player.onGround() || player.isInWater() || player.isInLava() || player.onClimbable()
                || player.isFallFlying() || player.getAbilities().flying || player.isPassenger()) return;
        if (player.getFoodData().getFoodLevel() <= 0) return;
        Vec3 motion = player.getDeltaMovement();
        if (motion.y >= 0) return;
        if (consumeHunger) player.causeFoodExhaustion(SLOW_FALL_EXHAUSTION);
        player.setDeltaMovement(motion.x, motion.y + SLOW_FALL_GRAVITY_OFFSET, motion.z);
        player.fallDistance = 0.0F;
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide() || !FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (player.level().isThundering()) {
            event.setNewDamage(event.getNewDamage() * THUNDER_DAMAGE_MULTIPLIER);
        } else if (player.level().isRaining()) {
            event.setNewDamage(event.getNewDamage() * RAIN_DAMAGE_MULTIPLIER);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        if (projectile.level().isClientSide()) return;
        if (!(event.getRayTraceResult() instanceof EntityHitResult entityHitResult)) return;
        if (!(entityHitResult.getEntity() instanceof ServerPlayer player)) return;
        if (!FluffyWindAlloyArmorItem.hasFullSet(player)) return;
        if (player.getRandom().nextDouble() >= DEFLECT_CHANCE) return;
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getPersistentData().putBoolean(NBT_HOVER_JUMP, false);
    }
}
