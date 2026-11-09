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
 * 失落剑冢结构探测 —— 玩家进入失落剑冢时授予进度。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class LostSwordTombStructureHandler {

    private static final ResourceKey<Structure> LOST_SWORD_TOMB =
            ResourceKey.create(Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "the_lost_sword_tomb"));

    private static final String COOLDOWN_TAG = "pasterdream_lost_sword_tomb_check";
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
        Structure structure = structures.get(LOST_SWORD_TOMB).map(holder -> holder.value()).orElse(null);
        if (structure == null) return;

        StructureStart start = serverLevel.structureManager()
                .getStructureWithPieceAt(sp.blockPosition(), structure);
        if (start == null || !start.isValid()) return;

        ModCriteriaTriggers.FOUND_TOMB.get().trigger(sp);
    }
}
