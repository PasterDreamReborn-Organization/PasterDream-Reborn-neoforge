package com.pasterdream.pasterdreammod.world.behavior;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public final class ModTreeGrowers {
    private static final ResourceKey<ConfiguredFeature<?, ?>> DYEDREAM_TREE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_tree"));
    public static final TreeGrower DYEDREAM = new TreeGrower("dyedream",
            Optional.empty(), Optional.of(DYEDREAM_TREE), Optional.empty());

    private ModTreeGrowers() {
    }
}
