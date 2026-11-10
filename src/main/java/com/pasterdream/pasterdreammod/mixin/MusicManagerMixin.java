package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {

    private static final ResourceKey<Level> DYEDREAM_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"));
    private static final ResourceKey<Level> WIND_JOURNEY_WORLD =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_journey_world"));

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private int nextSongDelay;

    @Inject(method = "tick", at = @At("RETURN"))
    private void pasterdream$adjustMusicDelay(CallbackInfo ci) {
        LocalPlayer player = this.minecraft.player;

        if (player == null) return;
        if (!player.level().dimension().equals(DYEDREAM_WORLD)
                && !player.level().dimension().equals(WIND_JOURNEY_WORLD)) return;

        // 有一定概率播放MC原版音乐，此时不干预间隔
        if (player.level().random.nextFloat() < 0.35f) {
            return;
        }

        if (this.nextSongDelay > 1200) {
            this.nextSongDelay = 1200;
        }
    }
}
