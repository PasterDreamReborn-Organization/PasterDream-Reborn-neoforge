package com.pasterdream.pasterdreammod.network.fluidslot;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FluidSlotInteractPacket(int containerId, int slot, boolean insert) implements CustomPacketPayload
{
    public static final Type<FluidSlotInteractPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_slot_interact"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidSlotInteractPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, packet) -> { buffer.writeVarInt(packet.containerId); buffer.writeVarInt(packet.slot); buffer.writeBoolean(packet.insert); },
            buffer -> new FluidSlotInteractPacket(buffer.readVarInt(), buffer.readVarInt(), buffer.readBoolean()));

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(FluidSlotInteractPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player &&
                    player.containerMenu instanceof AbstractContainerMenuWithFluidSlot menu &&
                    menu.containerId == packet.containerId)
            {
                menu.interactFluid(player, packet.slot, packet.insert);
            }
        });
    }
}
