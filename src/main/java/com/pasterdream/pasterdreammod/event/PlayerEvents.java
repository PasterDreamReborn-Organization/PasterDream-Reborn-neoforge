package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

public class PlayerEvents {
    public static void onPlayerSleepInBed(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        // 与来源相同，白天尝试睡觉时跳过。
        if (player.level().isDay()) return;

        // 躺下给予3分钟休憩效果
        player.addEffect(new MobEffectInstance(ModEffects.REST,
                3600, 0, false, false));
    }
}
