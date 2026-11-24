package com.pasterdream.pasterdreammod.client.audio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModSounds;
import com.pasterdream.pasterdreammod.world.entity.AaroncosLeftHandEntity;
import com.pasterdream.pasterdreammod.world.entity.AaroncosRightHandEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.LinkedHashSet;
import java.util.Set;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public class BossMusicHandler {
    private static final Set<Mob> TRACKED_HANDS = new LinkedHashSet<>();
    static BossMusicInstance currentMusic;

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() || !(event.getEntity() instanceof Mob mob))
            return;
        if (mob instanceof AaroncosLeftHandEntity || mob instanceof AaroncosRightHandEntity) {
            TRACKED_HANDS.add(mob);
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        TRACKED_HANDS.removeIf(mob -> mob.level() != mc.level || !mob.isAlive());

        if (currentMusic != null && !currentMusic.isInLevel(mc.level)) {
            mc.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }
        if (currentMusic != null && currentMusic.isStopped())
            currentMusic = null;
        if (currentMusic != null)
            return;

        Mob mob = getActiveBoss();
        if (mob != null) {
            currentMusic = new BossMusicInstance(
                    ModSounds.AARONCOS_MUSIC.get(), mob, 0.75F, 1.0F);
            mc.getSoundManager().play(currentMusic);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onSelectMusic(SelectMusicEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (currentMusic != null && !currentMusic.isStopped() && currentMusic.isInLevel(mc.level)
                && mc.getSoundManager().isActive(currentMusic)
                && mc.options.getSoundSourceVolume(SoundSource.RECORDS) > 0.0F) {
            event.overrideMusic(null);
        }
    }

    static Mob getActiveBoss() {
        for (Mob hand : TRACKED_HANDS) {
            if (hand.isAlive() && hand.isAggressive()) {
                return hand;
            }
        }
        return null;
    }
}
