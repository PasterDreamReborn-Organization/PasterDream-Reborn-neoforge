package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.worldgen.structures.FixedChunkStructurePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructurePlacementTypes {

    private static final DeferredRegister<StructurePlacementType<?>> PLACEMENT_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<StructurePlacementType<?>, StructurePlacementType<FixedChunkStructurePlacement>> FIXED_CHUNK =
            PLACEMENT_TYPES.register("fixed_chunk", ModStructurePlacementTypes::fixedChunk);

    private static StructurePlacementType<FixedChunkStructurePlacement> fixedChunk() {
        return () -> FixedChunkStructurePlacement.CODEC;
    }

    public static void register(IEventBus eventBus) {
        PLACEMENT_TYPES.register(eventBus);
    }
}
