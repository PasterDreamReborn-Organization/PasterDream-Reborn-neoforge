package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.helper.TeleportHelper;
import com.pasterdream.pasterdreammod.world.dimension.LampShadowDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class LampShadowWorldTeleporter {

    private static final ResourceKey<Level> LAMP_SHADOW_WORLD = LampShadowDimension.LAMP_SHADOW_WORLD;

    public static ResourceKey<Level> getLampShadowWorld() {
        return LAMP_SHADOW_WORLD;
    }

    /**
     * Teleports the entity to the lamp_shadow_world dimension.
     * Target is below the twilight_lantern in the shadow_world_spawn structure.
     * Height logic must match placeShadowWorldSpawn in ModWorldGenEvents.
     */
    public static void execute(Level world, Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().dimension() == LAMP_SHADOW_WORLD) return;

        ServerLevel destination = player.server.getLevel(LAMP_SHADOW_WORLD);
        if (destination == null) return;

        // Match the structure placement: height at (-9, -9) <= 100 → low spawn, else → high spawn
        boolean low = destination.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -9, -9) <= 100;
        double targetY = low ? 104 : 154;

        player.teleportTo(destination, 0.5, targetY, 0.5, player.getYRot(), player.getXRot());
        TeleportHelper.resendActiveEffects(player);
    }

    /**
     * 返回主世界（按重生点或世界出生点）。使用 teleportTo 的跨维度传送不会发送
     * RespawnPacket，因此补发主动效果，并在延迟 tick 后重新同步来源方块状态。
     */
    public static void teleportToOverworld(ServerPlayer player, BlockPos sourcePos) {
        ServerLevel overworld = player.server.getLevel(Level.OVERWORLD);
        if (overworld == null) return;

        player.server.execute(() -> {
            double destX, destY, destZ;
            if (player.getRespawnDimension() == Level.OVERWORLD && player.getRespawnPosition() != null) {
                BlockPos respawn = player.getRespawnPosition();
                destX = respawn.getX() + 0.5;
                destY = respawn.getY();
                destZ = respawn.getZ() + 0.5;
            } else {
                BlockPos spawn = overworld.getLevelData().getSpawnPos();
                destX = spawn.getX() + 0.5;
                destY = spawn.getY();
                destZ = spawn.getZ() + 0.5;
            }
            player.teleportTo(overworld, destX, destY, destZ, player.getYRot(), player.getXRot());
            TeleportHelper.resendActiveEffects(player);
            player.fallDistance = 0;
            player.connection.send(new ClientboundBlockUpdatePacket(overworld, sourcePos));
        });
    }
}
