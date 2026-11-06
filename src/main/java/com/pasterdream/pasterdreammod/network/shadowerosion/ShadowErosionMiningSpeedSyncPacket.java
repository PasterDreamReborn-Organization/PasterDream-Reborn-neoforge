package com.pasterdream.pasterdreammod.network.shadowerosion;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** 服务端同步影蚀与暗影合金工具的权威挖掘倍率。 */
public record ShadowErosionMiningSpeedSyncPacket(float multiplier) implements CustomPacketPayload {
    public static final Type<ShadowErosionMiningSpeedSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_erosion_mining_speed_sync"));
    public static final StreamCodec<FriendlyByteBuf, ShadowErosionMiningSpeedSyncPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.FLOAT, ShadowErosionMiningSpeedSyncPacket::multiplier,
                    ShadowErosionMiningSpeedSyncPacket::new);

    private static float clientMultiplier = 1.0F;

    @Override
    public Type<ShadowErosionMiningSpeedSyncPacket> type() {
        return TYPE;
    }

    public static void handle(ShadowErosionMiningSpeedSyncPacket packet, IPayloadContext context) {
        clientMultiplier = packet.multiplier;
    }

    public static float getClientMultiplier() {
        return clientMultiplier;
    }

    public static void sendToPlayer(float multiplier, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new ShadowErosionMiningSpeedSyncPacket(multiplier));
    }
}
