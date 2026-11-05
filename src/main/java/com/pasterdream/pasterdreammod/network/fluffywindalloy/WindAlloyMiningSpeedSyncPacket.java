package com.pasterdream.pasterdreammod.network.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * 服务端 → 客户端：同步萦风合金工具的挖掘速度倍率（疾风过境被动）。
 * <p>
 * 服务端用权威移动速度算出倍率后发给客户端，客户端用它做裂纹动画，
 * 从而保证两端挖掘速度一致，避免"方块挖碎后回弹"。
 */
public record WindAlloyMiningSpeedSyncPacket(float multiplier) implements CustomPacketPayload {

    public static final Type<WindAlloyMiningSpeedSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_alloy_mining_speed_sync"));
    public static final StreamCodec<FriendlyByteBuf, WindAlloyMiningSpeedSyncPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.FLOAT, WindAlloyMiningSpeedSyncPacket::multiplier,
                    WindAlloyMiningSpeedSyncPacket::new);

    // 客户端缓存的最新倍率，默认无加成
    private static float clientMultiplier = 1.0F;

    @Override
    public Type<WindAlloyMiningSpeedSyncPacket> type() {
        return TYPE;
    }

    public static void handle(WindAlloyMiningSpeedSyncPacket packet, IPayloadContext context) {
        clientMultiplier = packet.multiplier;
    }

    public static float getClientMultiplier() {
        return clientMultiplier;
    }

    public static void sendToPlayer(float multiplier, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new WindAlloyMiningSpeedSyncPacket(multiplier));
    }
}
