package com.pasterdream.pasterdreammod.tag;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ModBiomeTags {
    public static final TagKey<Biome> CAN_SOUL_ORE_SPAWN_BIOME = TagKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "can_soul_ore_spawn_biome"));

    private ModBiomeTags() {
    }
}
