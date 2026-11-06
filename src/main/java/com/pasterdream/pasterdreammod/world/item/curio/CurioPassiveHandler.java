package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
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
}
