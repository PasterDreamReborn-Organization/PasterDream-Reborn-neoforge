package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public final class ModJukeboxSongs {
    public static final ResourceKey<Registry<JukeboxSong>> REGISTRY = Registries.JUKEBOX_SONG;

    public static final ResourceKey<JukeboxSong> SWEET_DREAM = create("sweet_dream_music_disc");
    public static final ResourceKey<JukeboxSong> SNOWFALL_DREAM = create("snowfall_dream_music_disc");
    public static final ResourceKey<JukeboxSong> WIND_JOURNEY = create("wind_journey_music_disc");
    public static final ResourceKey<JukeboxSong> AARONCOS_MUSIC = create("aaroncos_music_disc");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, SWEET_DREAM, ModSounds.SWEET_DREAM_MUSIC, 111.0F, 0);
        register(context, SNOWFALL_DREAM, ModSounds.SNOWFALL_DREAM_MUSIC, 126.0F, 0);
        register(context, WIND_JOURNEY, ModSounds.WIND_JOURNEY, 212.0F, 0);
        register(context, AARONCOS_MUSIC, ModSounds.AARONCOS_MUSIC, 149.0F, 0);
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key,
                                 Holder<SoundEvent> sound, float lengthInSeconds, int comparatorOutput) {
        ResourceLocation id = key.location();
        context.register(key, new JukeboxSong(sound,
                Component.translatable("item." + id.getNamespace() + "." + id.getPath() + ".desc"),
                lengthInSeconds, comparatorOutput));
    }

    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(REGISTRY, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    private ModJukeboxSongs() {
    }
}
