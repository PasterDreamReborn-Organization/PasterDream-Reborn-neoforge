package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
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
import com.pasterdream.pasterdreammod.world.entity.ThrownPotionBottle;
import com.pasterdream.pasterdreammod.world.entity.TrailArrowEntity;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class CurioPassiveHandler {

    private static final double GHOST_FACE_CLONE_MAX_SPAWN_DISTANCE_SQR = 9.0;

    private static final String GHOST_FACE_AMMO_TAG = "pasterdream_ghost_face_ammo";
    private static final String GHOST_FACE_STATE_TAG = "pasterdream_ghost_face_state";
    private static final Set<String> GHOST_FACE_STATE_EXCLUDES = Set.of(
            "pasterdream_ghost_face_spawned",
            "pasterdream_arrow_velocity_applied");

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

    /** 卡莱的调料瓶：进食时缩短使用时间 40%。 */
    @SubscribeEvent
    public static void onItemUseTick(LivingEntityUseItemEvent.Tick event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getItem().getFoodProperties(player) == null) return;
        boolean hasBottle = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.CALAIS_SPICE_BOTTLE.get()).isPresent())
                .orElse(false);
        if (!hasBottle) return;

        long gameTime = player.level().getGameTime();
        if (gameTime % 3 != 0) {
            event.setDuration(event.getDuration() - 1);
        }
    }

    /** 卡莱的调料瓶：攻击命中消耗一层并随机触发效果。 */
    @SubscribeEvent
    public static void onCalaisSpiceAttack(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (event.getSource().getEntity() == event.getEntity()) return;

        MobEffectInstance buff = player.getEffect(ModEffects.CALAIS_SPICE_BOTTLE);
        if (buff == null) return;

        int level = buff.getAmplifier() + 1;
        player.removeEffect(ModEffects.CALAIS_SPICE_BOTTLE);
        if (level > 1) {
            player.addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE,
                    -1, level - 2, false, false, true));
        } else {
            player.getPersistentData().putBoolean("pasterdream.calais_depleted", true);
        }

        if (!player.level().isClientSide()) {
            List<? extends Double> weights = Config.CALAIS_SPICE_BOTTLE_WEIGHTS.get();
            double totalWeight = 0;
            for (double w : weights) totalWeight += w;
            if (totalWeight <= 0) return;

            double r = player.getRandom().nextDouble() * totalWeight;
            double cumulative = 0;
            int roll = -1;
            for (int i = 0; i < weights.size(); i++) {
                cumulative += weights.get(i);
                if (r < cumulative) {
                    roll = i;
                    break;
                }
            }
            if (roll < 0) roll = weights.size() - 1;

            switch (roll) {
                case 0 -> {
                    List<Holder<MobEffect>> buffPool = Config.getCalaisSpiceBottleEffects();
                    if (buffPool.isEmpty()) return;
                    Holder<MobEffect> picked = buffPool.get(player.getRandom().nextInt(buffPool.size()));
                    MobEffectInstance existingBuff = player.getEffect(picked);
                    int newAmp = existingBuff != null ? Math.min(existingBuff.getAmplifier() + 1, 2) : 0;
                    player.addEffect(new MobEffectInstance(picked, 300, newAmp, false, true, true));
                }
                case 1 -> {
                    if (player instanceof ServerPlayer sp) {
                        double s = Config.CALAIS_SPICE_BOTTLE_SAN_MIN.get() + sp.getRandom().nextDouble()
                                * (Config.CALAIS_SPICE_BOTTLE_SAN_MAX.get() - Config.CALAIS_SPICE_BOTTLE_SAN_MIN.get());
                        SanHelper.addPlayerSanAndSync(sp, s);
                    }
                }
                case 2 -> {
                    float amount = (float) (Config.CALAIS_SPICE_BOTTLE_HEAL_MIN.get() + player.getRandom().nextDouble()
                            * (Config.CALAIS_SPICE_BOTTLE_HEAL_MAX.get() - Config.CALAIS_SPICE_BOTTLE_HEAL_MIN.get()));
                    player.heal(amount);
                }
                case 3 -> {
                    List<Holder<MobEffect>> debuffPool = Config.getCalaisSpiceBottleDebuffs();
                    if (debuffPool.isEmpty()) return;
                    Holder<MobEffect> picked = debuffPool.get(player.getRandom().nextInt(debuffPool.size()));
                    LivingEntity target = event.getEntity();
                    target.addEffect(new MobEffectInstance(picked,
                            Config.CALAIS_SPICE_BOTTLE_DEBUFF_DURATION.get(),
                            Config.CALAIS_SPICE_BOTTLE_DEBUFF_AMPLIFIER.get(),
                            false, true, true));
                }
                case 4 -> {
                    player.level().playSound(null, player.blockPosition(), ModSounds.EVASION.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.addEffect(new MobEffectInstance(ModEffects.EVASION,
                            Config.CALAIS_SPICE_BOTTLE_EVASION_DURATION.get(), 0,
                            false, false, false));
                }
                case 5 -> {
                    player.level().playSound(null, player.blockPosition(), ModSounds.DOLL.get(),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.literal("?"), true);
                }
            }
        }
    }

    /** 卡莱的调料瓶：进食按饥饿值积累料理层数。 */
    @SubscribeEvent
    public static void onCalaisSpiceEat(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) return;
        var foodProps = event.getItem().getFoodProperties(player);
        if (foodProps == null) return;

        boolean hasBottle = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.CALAIS_SPICE_BOTTLE.get()).isPresent())
                .orElse(false);
        if (!hasBottle) return;

        int nutrition = foodProps.nutrition();
        if (nutrition < 3) return;

        int levelsGained = nutrition / 3;
        MobEffectInstance existing = player.getEffect(ModEffects.CALAIS_SPICE_BOTTLE);
        int currentLevel = existing != null ? existing.getAmplifier() + 1 : 0;
        int newLevel = Math.min(currentLevel + levelsGained, 10);

        if (existing != null) {
            player.removeEffect(ModEffects.CALAIS_SPICE_BOTTLE);
        }
        player.addEffect(new MobEffectInstance(ModEffects.CALAIS_SPICE_BOTTLE,
                -1, newLevel - 1, false, false, true));
        player.getPersistentData().remove("pasterdream.calais_depleted");
    }

    /** 鬼魂之面：远程射弹无视无敌帧。 */
    @SubscribeEvent
    public static void onProjectileHit(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        event.getEntity().invulnerableTime = 0;
    }

    /** 鬼魂之面：远程射弹发射时记录复制状态。 */
    @SubscribeEvent
    public static void onProjectileJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Projectile projectile)) return;
        if (!(projectile.getOwner() instanceof Player player)) return;
        if (projectile.getPersistentData().getBoolean("pasterdream_ghost_face_spawned")) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        if (projectile instanceof ThrownEnderpearl) return;
        if (projectile instanceof ThrownPotion) return;
        if (projectile instanceof FishingHook) return;
        if (Config.isGhostFaceProjectileBlacklisted(projectile.getType())) return;
        if (projectile.distanceToSqr(player) > GHOST_FACE_CLONE_MAX_SPAWN_DISTANCE_SQR) return;

        CompoundTag pd = player.getPersistentData();
        if (pd.contains("pasterdream_ghost_face_delay")) {
            captureGhostFaceProjectileState(pd, projectile);
            if (!(projectile instanceof ThrownTrident)) {
                pd.putInt("pasterdream_ghost_face_type", 2);
                pd.putString("pasterdream_ghost_face_clone",
                        BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType()).toString());
                if (projectile instanceof AbstractArrow arrowProj) {
                    pd.putDouble("pasterdream_ghost_face_damage", arrowProj.getBaseDamage());
                }
                if (projectile instanceof ThrownPotionBottle potionBottle) {
                    pd.putString("pasterdream_ghost_face_potion_type",
                            PotionBottleItem.getPotionType(potionBottle.getItem()));
                }
            }
            return;
        }

        if (isGhostFaceOnCooldown(player)) return;

        Vec3 motion = projectile.getDeltaMovement();
        float velocity = (float) motion.length();
        if (velocity < 0.1F) return;

        int extra = 1 + (player.getRandom().nextFloat() < 0.2F ? 1 : 0);

        double baseDamage = 2.0;
        if (projectile instanceof AbstractArrow arrowProj) {
            baseDamage = arrowProj.getBaseDamage();
        }

        int projectileType;
        String cloneTypeId = "";
        if (projectile instanceof ThrownTrident) {
            projectileType = 1;
        } else {
            projectileType = 2;
            cloneTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(projectile.getType()).toString();
        }

        pd.putInt("pasterdream_ghost_face_delay", 1);
        pd.putInt("pasterdream_ghost_face_extra", extra);
        pd.putFloat("pasterdream_ghost_face_velocity", velocity);
        pd.putDouble("pasterdream_ghost_face_damage", baseDamage);
        pd.putBoolean("pasterdream_ghost_face_flame", false);
        pd.putBoolean("pasterdream_ghost_face_crit", false);
        pd.putInt("pasterdream_ghost_face_type", projectileType);
        if (projectileType == 2) {
            pd.putString("pasterdream_ghost_face_clone", cloneTypeId);
        }
        if (projectile instanceof ThrownPotionBottle potionBottle) {
            pd.putString("pasterdream_ghost_face_potion_type",
                    PotionBottleItem.getPotionType(potionBottle.getItem()));
        }
        captureGhostFaceProjectileState(pd, projectile);

        markGhostFaceClone(player);
    }

    /** 鬼魂之面：弓/弩发射时记录复制参数。 */
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                .orElse(false);
        if (!hasGhostFace) return;

        if (isGhostFaceOnCooldown(player)) return;

        ItemStack bow = event.getBow();

        float f;
        if (bow.getItem() instanceof CrossbowItem) {
            f = 1.0F;
        } else {
            f = event.getCharge() / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if (f > 1.0F) f = 1.0F;
        }
        float velocity = f * 3.0F;

        double damage = 2.0;
        int powerLevel = getEnchantmentLevel(player, Enchantments.POWER, bow);
        if (powerLevel > 0) {
            damage += powerLevel * 0.5 + 0.5;
        }

        boolean flame = getEnchantmentLevel(player, Enchantments.FLAME, bow) > 0;
        boolean crit = f >= 1.0F;

        int extra = 1 + (player.getRandom().nextFloat() < 0.2F ? 1 : 0);

        CompoundTag pd = player.getPersistentData();
        pd.putInt("pasterdream_ghost_face_delay", 1);
        pd.putInt("pasterdream_ghost_face_extra", extra);
        pd.putFloat("pasterdream_ghost_face_velocity", velocity);
        pd.putDouble("pasterdream_ghost_face_damage", damage);
        pd.putBoolean("pasterdream_ghost_face_flame", flame);
        pd.putBoolean("pasterdream_ghost_face_crit", crit);
        pd.putInt("pasterdream_ghost_face_type", 0);

        markGhostFaceClone(player);
    }

    private static int getEnchantmentLevel(Player player, ResourceKey<Enchantment> enchantment, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                player.level().registryAccess().holderOrThrow(enchantment), stack);
    }

    /** 苍白骨针护符：延迟传送回重生点。 */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        CompoundTag pd = player.getPersistentData();

        int gfDelay = pd.getInt("pasterdream_ghost_face_delay");
        if (gfDelay > 0) {
            gfDelay--;
            if (gfDelay > 0) {
                pd.putInt("pasterdream_ghost_face_delay", gfDelay);
            } else {
                boolean hasGhostFace = CuriosApi.getCuriosInventory(player)
                        .map(h -> h.findFirstCurio(ModItems.GHOST_FACE.get()).isPresent())
                        .orElse(false);
                if (!hasGhostFace) {
                    clearGhostFaceData(pd);
                } else {
                    int extra = pd.getInt("pasterdream_ghost_face_extra");
                    float velocity = pd.getFloat("pasterdream_ghost_face_velocity");
                    double damage = pd.contains("pasterdream_ghost_face_damage")
                            ? pd.getDouble("pasterdream_ghost_face_damage") : 2.0;
                    boolean flame = pd.getBoolean("pasterdream_ghost_face_flame");
                    boolean crit = pd.getBoolean("pasterdream_ghost_face_crit");
                    int projectileType = pd.getInt("pasterdream_ghost_face_type");

                    for (int i = 0; i < extra; i++) {
                        switch (projectileType) {
                            case 1 -> {
                                ThrownTrident trident = new ThrownTrident(player.level(), player,
                                        new ItemStack(Items.TRIDENT));
                                trident.shootFromRotation(player, player.getXRot(), player.getYRot(),
                                        0.0F, velocity, 1.0F);
                                trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                trident.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
                                player.level().addFreshEntity(trident);
                            }
                            case 2 -> spawnClonedProjectile(player, pd, velocity, damage, flame, crit && i == 0);
                            default -> {
                                Arrow arrow = new Arrow(player.level(), player,
                                        new ItemStack(Items.ARROW), ItemStack.EMPTY);
                                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(),
                                        0.0F, velocity, 1.0F);
                                arrow.setBaseDamage(damage);
                                arrow.setCritArrow(crit && i == 0);
                                if (flame) arrow.igniteForSeconds(100.0F);
                                arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                arrow.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
                                player.level().addFreshEntity(arrow);
                            }
                        }
                    }
                    clearGhostFaceData(pd);
                }
            }
        }

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

    private static void spawnClonedProjectile(Player player, CompoundTag pd, float velocity,
                                              double damage, boolean flame, boolean crit) {
        String typeIdStr = pd.getString("pasterdream_ghost_face_clone");
        if (typeIdStr.isEmpty()) return;
        ResourceLocation typeId = ResourceLocation.tryParse(typeIdStr);
        if (typeId == null) return;
        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(typeId);
        if (entityType == null) return;

        Entity copy = entityType.create(player.level());
        if (!(copy instanceof Projectile copyProj)) return;

        copyProj.setOwner(player);
        copy.moveTo(player.getX(), player.getEyeY() - 0.1, player.getZ(), player.getYRot(), player.getXRot());
        if (copy instanceof TrailArrowEntity trailArrow) {
            ItemStack ammo = ItemStack.parseOptional(player.level().registryAccess(),
                    pd.getCompound(GHOST_FACE_AMMO_TAG));
            if (!ammo.isEmpty()) {
                trailArrow.setAmmoItem(ammo);
            }
        }
        if (pd.contains(GHOST_FACE_STATE_TAG)) {
            copy.getPersistentData().merge(pd.getCompound(GHOST_FACE_STATE_TAG));
        }
        copyProj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);
        if (copy instanceof AbstractArrow copyArrow) {
            copyArrow.setBaseDamage(damage);
            copyArrow.setCritArrow(crit);
            if (flame) copyArrow.igniteForSeconds(100.0F);
            copyArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        if (copy instanceof ThrownPotionBottle potionClone) {
            String potionType = pd.getString("pasterdream_ghost_face_potion_type");
            if (!potionType.isEmpty()) {
                ItemStack stack = potionClone.getItem().copy();
                PotionBottleItem.setPotionType(stack, potionType);
                potionClone.setItem(stack);
            }
        }
        copy.getPersistentData().putBoolean("pasterdream_ghost_face_spawned", true);
        player.level().addFreshEntity(copy);
    }

    private static void captureGhostFaceProjectileState(CompoundTag pd, Projectile projectile) {
        pd.remove(GHOST_FACE_AMMO_TAG);
        pd.remove(GHOST_FACE_STATE_TAG);

        if (projectile instanceof TrailArrowEntity trailArrow) {
            ItemStack ammo = trailArrow.getAmmoItem();
            if (!ammo.isEmpty()) {
                pd.put(GHOST_FACE_AMMO_TAG, ammo.save(projectile.level().registryAccess()));
            }
        }

        CompoundTag source = projectile.getPersistentData();
        CompoundTag copied = new CompoundTag();
        for (String key : source.getAllKeys()) {
            if (!key.startsWith("pasterdream") || GHOST_FACE_STATE_EXCLUDES.contains(key)) {
                continue;
            }
            net.minecraft.nbt.Tag value = source.get(key);
            if (value != null) {
                copied.put(key, value.copy());
            }
        }
        if (!copied.isEmpty()) {
            pd.put(GHOST_FACE_STATE_TAG, copied);
        }
    }

    private static void clearGhostFaceData(CompoundTag pd) {
        pd.remove("pasterdream_ghost_face_delay");
        pd.remove("pasterdream_ghost_face_extra");
        pd.remove("pasterdream_ghost_face_velocity");
        pd.remove("pasterdream_ghost_face_damage");
        pd.remove("pasterdream_ghost_face_flame");
        pd.remove("pasterdream_ghost_face_crit");
        pd.remove("pasterdream_ghost_face_type");
        pd.remove("pasterdream_ghost_face_clone");
        pd.remove("pasterdream_ghost_face_potion_type");
        pd.remove(GHOST_FACE_AMMO_TAG);
        pd.remove(GHOST_FACE_STATE_TAG);
    }

    private static boolean isGhostFaceOnCooldown(Player player) {
        int cooldownTicks = Config.GHOST_FACE_CLONE_COOLDOWN.get() * 20;
        if (cooldownTicks <= 0) return false;
        long lastClone = player.getPersistentData().getLong("pasterdream_ghost_face_last_clone");
        return lastClone > 0 && player.level().getGameTime() - lastClone < cooldownTicks;
    }

    private static void markGhostFaceClone(Player player) {
        player.getPersistentData().putLong("pasterdream_ghost_face_last_clone", player.level().getGameTime());
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
