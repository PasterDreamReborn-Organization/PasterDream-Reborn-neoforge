package com.pasterdream.pasterdreammod.advancement;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.WearingArmorSetTrigger;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * 护甲套装进度的触发入口。
 * <p>
 * 触发时机：换装（任一护甲槽变化）、玩家登录、玩家重生。
 * 登录/重生用于覆盖「上线时已经穿着整套」这种没有换装事件的情况；
 * 判据本身幂等，重复触发无副作用。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ArmorSetAdvancementHandler {

    private ArmorSetAdvancementHandler() {
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!event.getSlot().isArmor()) return;
        check(player);
    }

    @SubscribeEvent
    public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            check(player);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            check(player);
        }
    }

    private static void check(ServerPlayer player) {
        for (WearingArmorSetTrigger.ArmorSet set : WearingArmorSetTrigger.ArmorSet.values()) {
            if (set.isWearing(player)) {
                ModCriteriaTriggers.WEARING_ARMOR_SET.get().trigger(player, set);
            }
        }
    }
}
