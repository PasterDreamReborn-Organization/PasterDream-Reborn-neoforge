package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.DreamDimensionHelper;
import com.pasterdream.pasterdreammod.helper.GameModeHelper;
import com.pasterdream.pasterdreammod.helper.TeleportHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.network.curio.CurioActivationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class CurioPassiveHandler {

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof Player player) {
            // 染梦香水：效果持续期间幻翼不会锁定玩家
            if (event.getEntity() instanceof Phantom && player.hasEffect(ModEffects.DYEDREAM_PERFUME)) {
                event.setCanceled(true);
                return;
            }
            if (CuriosApi.getCuriosInventory(player)
                    .map(h -> h.findFirstCurio(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get()).isPresent())
                    .orElse(false)) {
                // 允许反击：如果玩家刚刚攻击过该生物则不取消
                if (event.getEntity().getLastHurtByMob() == player) return;
                event.setCanceled(true);
            }
        }
    }

    /**
     * 战争旗帜饰品：击杀后获得战意 buff，等级、持续时间随连杀叠加
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof Player player)) return;

        // 检查玩家是否装备了战争旗帜
        boolean hasWarFlag = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.WAR_FLAG.get()).isPresent())
                .orElse(false);
        if (!hasWarFlag) return;

        // 获取当前已有的战意 buff 等级并提升 1 级
        MobEffectInstance existing = player.getEffect(ModEffects.WAR_FLAG);
        int newAmplifier = (existing != null) ? existing.getAmplifier() + 1 : 0;

        // 最高三级（amplifier=2）
        if (newAmplifier > 2) newAmplifier = 2;

        // 等级越高持续时间越短：一=60秒，二=30秒，三=15秒
        int duration = switch (newAmplifier) {
            case 0 -> 1200; // 60秒
            case 1 -> 600;  // 30秒
            case 2 -> 300;  // 15秒
            default -> 1200;
        };
        player.addEffect(new MobEffectInstance(ModEffects.WAR_FLAG, duration, newAmplifier,
                false, false, true));
    }

    /**
     * 箭矢速度属性：弹射物生成时按玩家属性值加速（值即额外倍率，速度 ×(1+值)）
     */
    @SubscribeEvent
    public static void onArrowVelocityProjectileJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;
        // 防止重复加速（跨维度传送、克隆等再次进入世界时叠加）
        if (projectile.getPersistentData().getBoolean("pasterdream_arrow_velocity_applied")) return;

        double bonus = player.getAttributeValue(ModAttributes.ARROW_VELOCITY);
        if (bonus == 0.0) return;

        Vec3 motion = projectile.getDeltaMovement();
        if (motion.lengthSqr() <= 0.0) return;
        projectile.setDeltaMovement(motion.scale(1.0 + bonus));
        projectile.getPersistentData().putBoolean("pasterdream_arrow_velocity_applied", true);
    }

    /** 塞西莉亚的加护：常驻减伤、致命伤害拦截、效果不可移除。 */
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        // 加护生效期间：取消所有伤害
        if (player.hasEffect(ModEffects.CECILIA_BLESSING)) {
            event.setCanceled(true);
            return;
        }

        boolean hasCharm = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).isPresent())
                .orElse(false);
        if (hasCharm) {
            event.setAmount(event.getAmount() * 0.8F);
        }

        // 致死于伤害判定（event.getAmount() 已计算抗性）
        if (player.getHealth() + player.getAbsorptionAmount() - event.getAmount() > 0.0F) return;

        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && (player.getMainHandItem().is(Items.TOTEM_OF_UNDYING)
                    || player.getOffhandItem().is(Items.TOTEM_OF_UNDYING))) {
            return;
        }

        if (hasCharm) {
            event.setCanceled(true);
            player.setHealth(1.0F);

            CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                    handler.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).ifPresent(slotResult ->
                            handler.setEquippedCurio(slotResult.slotContext().identifier(),
                                    slotResult.slotContext().index(),
                                    new ItemStack(ModItems.FADED_BLESSING_OF_CECILIA.get()))));

            player.level().playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);

            ModNetwork.sendToTrackingAndSelf(player,
                    new CurioActivationPacket(new ItemStack(ModItems.BLESSING_OF_CECILIA.get())));

            if (player.level() instanceof ServerLevel sl) {
                sl.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                        player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
                sl.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(),
                        player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
                sl.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(),
                        player.getX(), player.getY(), player.getZ(), 32, 1, 1, 1, 0.2);
            }

            player.addEffect(new MobEffectInstance(ModEffects.CECILIA_BLESSING, 100, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 9, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false));
            return;
        }

        // 苍白骨针护符：梦境维度中拦截致命伤害
        if (DreamDimensionHelper.isDreamDimension(player.level())) {
            boolean hasTalisman = CuriosApi.getCuriosInventory(player)
                    .map(h -> h.findFirstCurio(ModItems.PALE_BONE_NEEDLE_TALISMAN.get()).isPresent())
                    .orElse(false);
            if (hasTalisman) {
                event.setCanceled(true);
                player.setHealth(1.0F);

                // 销毁护符
                CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                        handler.findFirstCurio(ModItems.PALE_BONE_NEEDLE_TALISMAN.get()).ifPresent(slotResult ->
                                handler.setEquippedCurio(slotResult.slotContext().identifier(),
                                        slotResult.slotContext().index(), ItemStack.EMPTY)));

                // 饰品激活动画 + 骨针音效
                ModNetwork.sendToTrackingAndSelf(player,
                        new CurioActivationPacket(new ItemStack(ModItems.PALE_BONE_NEEDLE_TALISMAN.get())));
                player.level().playSound(null, player.blockPosition(), ModSounds.AWAKE.get(),
                        SoundSource.NEUTRAL, 1.0F, 1.0F);

                // 粒子
                if (player.level() instanceof ServerLevel sl) {
                    sl.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(),
                            player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
                    sl.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(),
                            player.getX(), player.getY(), player.getZ(), 64, 1, 1, 1, 0.2);
                }

                // 施加效果：1.5秒塞西莉亚的加护 + 瞬间治疗
                player.addEffect(new MobEffectInstance(ModEffects.CECILIA_BLESSING, 30, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false));

                // 触发骨针使用进度
                if (player instanceof ServerPlayer sp) {
                    boolean wasFalling = player.fallDistance > 10;
                    ModCriteriaTriggers.USE_BONE_NEEDLE.get().trigger(sp, wasFalling);
                }

                // 记录传送延迟（1秒 = 20 tick）
                player.getPersistentData().putInt("pasterdream_talisman_teleport_delay", 20);
            }
        }
    }

    /** 苍白骨针护符：延迟传送回重生点。 */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        CompoundTag pd = player.getPersistentData();
        int talismanDelay = pd.getInt("pasterdream_talisman_teleport_delay");
        if (talismanDelay > 0) {
            talismanDelay--;
            if (talismanDelay > 0) {
                pd.putInt("pasterdream_talisman_teleport_delay", talismanDelay);
            } else {
                pd.remove("pasterdream_talisman_teleport_delay");
                if (player instanceof ServerPlayer sp) {
                    teleportToSpawn(sp);
                }
            }
        }
    }

    private static void teleportToSpawn(ServerPlayer sp) {
        ServerLevel overworld = sp.server.getLevel(Level.OVERWORLD);
        if (overworld != null && sp.level().dimension() != Level.OVERWORLD) {
            sp.teleportTo(overworld, sp.getX(), sp.getY(), sp.getZ(), sp.getYRot(), sp.getXRot());
        }
        ServerLevel targetLevel = (ServerLevel) sp.level();
        double spawnX, spawnY, spawnZ;
        if (sp.getRespawnDimension().equals(sp.level().dimension()) && sp.getRespawnPosition() != null) {
            BlockPos respawn = sp.getRespawnPosition();
            spawnX = respawn.getX() + 0.5;
            spawnY = respawn.getY();
            spawnZ = respawn.getZ() + 0.5;
        } else {
            BlockPos sharedSpawn = targetLevel.getLevelData().getSpawnPos();
            spawnX = sharedSpawn.getX();
            spawnY = sharedSpawn.getY();
            spawnZ = sharedSpawn.getZ();
        }
        sp.teleportTo(spawnX, spawnY, spawnZ);
        sp.fallDistance = 0;
        // 跨维度传送会重建客户端 LocalPlayer，补发效果包以恢复图标
        TeleportHelper.resendActiveEffects(sp);
        // 离开梦境维度后恢复进入前的游戏模式（防止卡在冒险模式）
        GameModeHelper.restorePreDreamGameMode(sp);
    }

    /** 佩戴塞西莉亚的加护时，加护效果不可被移除。 */
    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) {
        var instance = event.getEffectInstance();
        if (instance == null || instance.getEffect() != ModEffects.CECILIA_BLESSING) return;
        if (event.getEntity() instanceof Player player
                && CuriosApi.getCuriosInventory(player).map(h ->
                    h.findFirstCurio(ModItems.BLESSING_OF_CECILIA.get()).isPresent()
                    || h.findFirstCurio(ModItems.FADED_BLESSING_OF_CECILIA.get()).isPresent()
                ).orElse(false)) {
            event.setCanceled(true);
        }
    }
}
