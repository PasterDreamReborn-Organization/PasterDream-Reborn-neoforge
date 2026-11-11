package com.pasterdream.pasterdreammod.network.shadowselect;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.ShadowChoiceHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShadowSelectEndButtonPacket(int buttonId) implements CustomPacketPayload {

    public static final Type<ShadowSelectEndButtonPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_select_end_button"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShadowSelectEndButtonPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.INT, ShadowSelectEndButtonPacket::buttonId,
                    ShadowSelectEndButtonPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShadowSelectEndButtonPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                ShadowChoiceHandler.choose(player, packet.buttonId());
            }
        });
    }
}
