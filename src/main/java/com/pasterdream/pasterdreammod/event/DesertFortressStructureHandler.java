package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModCriteriaTriggers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * 沙漠遗迹结构探测 —— 玩家进入沙漠遗迹时授予进度。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class DesertFortressStructureHandler {

    private static final ResourceKey<Structure> DESERT_FORTRESS =
            ResourceKey.create(Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "desert_fortress"));

    private static final String COOLDOWN_TAG = "pasterdream_desert_fortress_check";
    private static final int CHECK_INTERVAL = 20;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        if (!(sp.level() instanceof ServerLevel serverLevel)) return;

        int serverTick = serverLevel.getServer().getTickCount();
        int lastCheck = sp.getPersistentData().getInt(COOLDOWN_TAG);
        if (serverTick - lastCheck < CHECK_INTERVAL) return;
        sp.getPersistentData().putInt(COOLDOWN_TAG, serverTick);

        HolderGetter<Structure> structures = serverLevel.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        Structure structure = structures.get(DESERT_FORTRESS).map(holder -> holder.value()).orElse(null);
        if (structure == null) return;

        StructureStart start = serverLevel.structureManager()
                .getStructureWithPieceAt(sp.blockPosition(), structure);
        if (start == null || !start.isValid()) return;

        ModCriteriaTriggers.FOUND_DESERT_FORTRESS.get().trigger(sp);
    }
}
