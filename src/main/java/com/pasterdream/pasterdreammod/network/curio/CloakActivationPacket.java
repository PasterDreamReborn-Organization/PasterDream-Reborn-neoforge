package com.pasterdream.pasterdreammod.network.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.EvasionCloakItem;
import com.pasterdream.pasterdreammod.world.item.curio.TurnbackCloakItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CloakActivationPacket() implements CustomPacketPayload {

    public static final Type<CloakActivationPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "cloak_activation"));
    public static final StreamCodec<ByteBuf, CloakActivationPacket> STREAM_CODEC =
            StreamCodec.unit(new CloakActivationPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CloakActivationPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                TurnbackCloakItem.tryActivate(player);
                EvasionCloakItem.tryActivate(player);
            }
        });
    }
}
