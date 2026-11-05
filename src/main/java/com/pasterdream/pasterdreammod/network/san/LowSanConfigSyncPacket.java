package com.pasterdream.pasterdreammod.network.san;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** 管理员仅覆盖显式切换的选项，其余遵循每位玩家的客户端配置。 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public record LowSanConfigSyncPacket(byte overrides, byte enabled) implements CustomPacketPayload {
    public static final int OVERLAY = 1;
    public static final int JITTER = 2;
    public static final int SOUND = 4;
    public static final Type<LowSanConfigSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "low_san_config"));
    public static final StreamCodec<FriendlyByteBuf, LowSanConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, LowSanConfigSyncPacket::overrides, ByteBufCodecs.BYTE, LowSanConfigSyncPacket::enabled,
            LowSanConfigSyncPacket::new);
    private static final LowSanConfigSyncPacket DEFAULT = new LowSanConfigSyncPacket((byte) 0, (byte) 0);
    private static LowSanConfigSyncPacket server = DEFAULT;
    private static LowSanConfigSyncPacket client = DEFAULT;

    @Override
    public Type<LowSanConfigSyncPacket> type() {
        return TYPE;
    }

    public static void handle(LowSanConfigSyncPacket packet, IPayloadContext context) {
        client = packet;
    }

    public static void setOverride(int flag, boolean value) {
        server = new LowSanConfigSyncPacket((byte) (server.overrides | flag),
                (byte) (value ? server.enabled | flag : server.enabled & ~flag));
        PacketDistributor.sendToAllPlayers(server);
    }

    public static boolean serverEnabled(int flag) {
        return (server.overrides & flag) == 0 || (server.enabled & flag) != 0;
    }

    public static boolean clientEnabled(int flag, boolean configured) {
        return (client.overrides & flag) == 0 ? configured : (client.enabled & flag) != 0;
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) PacketDistributor.sendToPlayer(player, server);
    }

    @SubscribeEvent
    public static void stop(ServerStoppedEvent event) {
        server = DEFAULT;
    }
}
