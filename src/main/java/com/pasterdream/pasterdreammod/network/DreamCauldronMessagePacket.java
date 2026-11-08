package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DreamCauldronMessagePacket(Component message) implements CustomPacketPayload
{
    public static final Type<DreamCauldronMessagePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_cauldron_message"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DreamCauldronMessagePacket> STREAM_CODEC =
            ComponentSerialization.STREAM_CODEC.map(DreamCauldronMessagePacket::new, DreamCauldronMessagePacket::message);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(DreamCauldronMessagePacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player().containerMenu instanceof DreamCauldronMenu
                    && Minecraft.getInstance().screen instanceof DreamCauldronScreen screen)
            {
                screen.showCauldronMessage(packet.message);
            }
            else
            {
                context.player().displayClientMessage(packet.message, true);
            }
        });
    }
}
