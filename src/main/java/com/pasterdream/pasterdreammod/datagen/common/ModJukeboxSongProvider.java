package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModJukeboxSongs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModJukeboxSongProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.JUKEBOX_SONG, ModJukeboxSongs::bootstrap);

    public ModJukeboxSongProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, BUILDER, Set.of(PasterDreamMod.MOD_ID));
    }

    @Override
    public String getName() {
        return "PasterDream Jukebox Songs";
    }
}
