package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

/**
 * 堕落者之印全局被动处理器。
 * 实现暗影生物中立、低SAN刷怪协助作战。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class SealOfTheCorruptedHandler {

    private static final String FRIENDLY_TAG = "pasterdream:seal_friendly";

    /** 检查玩家是否在饰品栏装备了堕落者之印 */
    public static boolean hasSeal(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(h -> h.findFirstCurio(ModItems.SEAL_OF_THE_CORRUPTED.get()).isPresent())
                .orElse(false);
    }

    /**
     * 拦截暗影生物对佩戴封印玩家的目标切换。
     * 友善暗影生物（低SAN刷怪）会被重定向到附近敌对生物，其余直接取消。
     */
    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getNewAboutToBeSetTarget() instanceof Player player)) return;
        if (!event.getEntity().getType().is(ModEntityTypeTags.SHADOW_MOB)) return;
        // 亚伦柯斯BOSS未迁入；迁入后需在此处保持其始终攻击佩戴者。

        // 友善暗影生物（低SAN刷怪）不攻击任何玩家，只把仇恨转向附近敌对生物
        if (event.getEntity() instanceof Mob mob
                && event.getEntity().getPersistentData().getBoolean(FRIENDLY_TAG)) {
            LivingEntity altTarget = findNearbyHostile(mob, player, 20);
            if (altTarget != null) {
                event.setNewAboutToBeSetTarget(altTarget);
                return;
            }
            event.setCanceled(true);
            return;
        }

        // 普通暗影生物不攻击佩戴封印的玩家（中立）
        if (!hasSeal(player)) return;
        event.setCanceled(true);
    }

    /**
     * 友善暗影生物每 20 tick 检查一次目标。
     * 当前目标无效（死亡/为封印玩家）时，重新搜索附近敌对生物协助玩家。
     * 若源玩家已离线或卸下封印，移除友善标记。
     */
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (!entity.getPersistentData().getBoolean(FRIENDLY_TAG)) return;
        if (!(entity instanceof Mob mob)) return;
        if (mob.tickCount % 20 != 0) return;

        LivingEntity currentTarget = mob.getTarget();
        // 友善暗影生物不保留任何玩家目标，只保留对敌对生物的目标
        if (currentTarget != null && currentTarget.isAlive()
                && !(currentTarget instanceof Player)) {
            return;
        }
        if (currentTarget != null) {
            mob.setTarget(null);
        }

        if (!entity.getPersistentData().hasUUID("ShadowSourcePlayer")) return;
        Player sourcePlayer = mob.level().getPlayerByUUID(
                entity.getPersistentData().getUUID("ShadowSourcePlayer"));
        if (sourcePlayer == null || !hasSeal(sourcePlayer)) {
            entity.getPersistentData().remove(FRIENDLY_TAG);
            return;
        }

        LivingEntity altTarget = findNearbyHostile(mob, sourcePlayer, 16);
        if (altTarget != null) {
            mob.setTarget(altTarget);
        }
    }

    /**
     * 在玩家附近搜索一个非暗影的战斗目标。
     * 优先选择正在以玩家为目标的生物（含被激怒的中立生物），其次选择天生敌对生物。
     */
    private static LivingEntity findNearbyHostile(Mob self, Player player, double range) {
        AABB area = self.getBoundingBox().inflate(range);
        List<Mob> candidates = self.level().getEntitiesOfClass(Mob.class, area,
                e -> e.isAlive()
                        && e.canAttack(e)
                        && !e.getType().is(ModEntityTypeTags.SHADOW_MOB)
                        && e != self);

        // 优先：正在攻击玩家的生物（含被激怒的中立生物如狼、末影人等）
        for (Mob m : candidates) {
            if (m.getTarget() == player) return m;
        }
        // 其次：天生敌对生物 (Monster 子类)
        for (Mob m : candidates) {
            if (m instanceof Monster) return m;
        }
        return null;
    }
}
