package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.world.entity.FluffyWindArrowEntity;
import com.pasterdream.pasterdreammod.world.entity.WindAlloyLightningEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/** 引雷萦风箭命中后落下一道范围雷电。 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class FluffyWindBowHandler {
    private static final float LIGHTNING_DAMAGE_RATIO = 0.25F;
    private static final float MIN_LIGHTNING_DAMAGE = 5.0F;
    private static final String TAG_CHANNELING_TRIGGERED = "pasterdream:fluffy_wind_channeling_triggered";

    private FluffyWindBowHandler() {
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof FluffyWindArrowEntity arrow)) return;
        if (!arrow.hasChannelingLightning()) return;
        if (arrow.getPersistentData().getBoolean(TAG_CHANNELING_TRIGGERED)) return;
        arrow.getPersistentData().putBoolean(TAG_CHANNELING_TRIGGERED, true);
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;
        if (!(event.getSource().getEntity() instanceof Player owner)) return;
        float strikeDamage = Math.max(event.getNewDamage() * LIGHTNING_DAMAGE_RATIO, MIN_LIGHTNING_DAMAGE);
        int flame = arrow.channelingFlameLevel();
        WindAlloyLightningEntity lightning = ModEntities.WIND_ALLOY_LIGHTNING.get().create(serverLevel);
        if (lightning != null) {
            lightning.setStrikes(1);
            lightning.init(owner, event.getEntity(), strikeDamage, event.getEntity().position(), 0, 0, flame);
            lightning.moveTo(event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ());
            serverLevel.addFreshEntity(lightning);
        }
    }
}
