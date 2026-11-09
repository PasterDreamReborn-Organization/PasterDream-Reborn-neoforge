package com.pasterdream.pasterdreammod.helper;

import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

/**
 * 跨维度传送后补发效果包。
 * {@code ServerPlayer.teleportTo(ServerLevel, ...)} 只发送 RespawnPacket，客户端会重建 LocalPlayer
 * 导致状态效果实例丢失。原版 {@code changeDimension} 会重新下发效果包，这里提供同样的补偿逻辑，
 * 供自定义传送方块在跨维度 teleportTo 之后调用，避免怪味效果在地图图标不显示。
 */
public final class TeleportHelper {

    private TeleportHelper() {}

    public static void resendActiveEffects(ServerPlayer player) {
        for (MobEffectInstance effect : player.getActiveEffects()) {
            player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect, false));
        }
    }
}
