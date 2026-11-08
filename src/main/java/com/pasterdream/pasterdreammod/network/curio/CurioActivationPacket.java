package com.pasterdream.pasterdreammod.network.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CurioActivationPacket(ItemStack stack) implements CustomPacketPayload {

    public static final Type<CurioActivationPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "curio_activation"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CurioActivationPacket> STREAM_CODEC =
            StreamCodec.composite(ItemStack.STREAM_CODEC, CurioActivationPacket::stack,
                    CurioActivationPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CurioActivationPacket payload, IPayloadContext context) {
        context.enqueueWork(() ->
                com.pasterdream.pasterdreammod.client.CurioActivationClientHandler.handle(payload.stack()));
    }
}
