package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.MagicDamageHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

/**
 * 将玩家的魔法伤害倍率应用到间接魔法伤害。
 *
 * <p>投掷伤害药水（喷溅/滞留）的瞬时伤害使用 {@code indirectMagic}，其来源实体为投掷者；
 * 该钩子同时覆盖蛛网、箭矢等间接魔法来源。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class MagicDamageHandler {

    private MagicDamageHandler() {
    }

    @SubscribeEvent
    public static void onIncomingMagicDamage(LivingIncomingDamageEvent event) {
        if (!event.getSource().is(DamageTypes.INDIRECT_MAGIC)) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        float multiplier = MagicDamageHelper.getMagicDamageMultiplier(player);
        if (multiplier == 1.0f) return;
        event.setAmount(event.getAmount() * multiplier);
    }
}
