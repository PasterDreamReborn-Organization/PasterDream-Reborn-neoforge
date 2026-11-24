package com.pasterdream.pasterdreammod.client.audio;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class BossMusicInstance extends AbstractTickableSoundInstance {
    private Mob boss;
    private final float maxVolume;
    private int fadeTicks;

    public BossMusicInstance(SoundEvent sound, Mob boss, float volume, float pitch) {
        super(sound, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.boss = boss;
        this.maxVolume = volume;
        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();
        this.looping = true;
        this.delay = 0;
        this.fadeTicks = 80;
        this.volume = 0.01F;
        this.pitch = pitch;
    }

    @Override
    public boolean canPlaySound() {
        return BossMusicHandler.currentMusic == this;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    boolean isInLevel(Level level) {
        return boss.level() == level;
    }

    @Override
    public void tick() {
        Mob activeBoss = BossMusicHandler.getActiveBoss();
        if (activeBoss != null) {
            boss = activeBoss;
            if (volume < maxVolume) {
                volume = Math.min(maxVolume, volume + maxVolume / 40.0F);
            }
            fadeTicks = 80;
        } else {
            if (!boss.isAlive()) {
                fadeTicks = 0;
            }
            if (fadeTicks > 0) {
                fadeTicks--;
            } else {
                volume = Math.max(0, volume - maxVolume / 40.0F);
            }
        }

        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();

        if (volume < 0.005F) {
            stop();
        }
    }
}
