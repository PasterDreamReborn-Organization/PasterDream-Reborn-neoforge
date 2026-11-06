package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class CurioPassiveHandler {

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof Player player
                && CuriosApi.getCuriosInventory(player)
                    .map(h -> h.findFirstCurio(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get()).isPresent())
                    .orElse(false)) {
            // 允许反击：如果玩家刚刚攻击过该生物则不取消
            if (event.getEntity().getLastHurtByMob() == player) return;
            event.setCanceled(true);
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
}
