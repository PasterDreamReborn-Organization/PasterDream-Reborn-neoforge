package com.pasterdream.pasterdreammod.network.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** 客户端跳跃键状态，用于萦风满套缓降和披风飞行助力。 */
public record WindHoverInputPacket(boolean jumping) implements CustomPacketPayload {
    public static final Type<WindHoverInputPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_hover_input"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WindHoverInputPacket> STREAM_CODEC =
            StreamCodec.of((buf, packet) -> buf.writeBoolean(packet.jumping),
                    buf -> new WindHoverInputPacket(buf.readBoolean()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(WindHoverInputPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                player.getPersistentData().putBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP, packet.jumping);
            }
        });
    }
}
