package com.pasterdream.pasterdreammod.network.fluidslot;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record FluidSyncPacket(int containerId, List<FluidStack> fluids) implements CustomPacketPayload
{
    public static final Type<FluidSyncPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fluid_slot_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidSyncPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, packet) ->
            {
                buffer.writeVarInt(packet.containerId);
                buffer.writeVarInt(packet.fluids.size());
                for (FluidStack fluid : packet.fluids) FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, fluid);
            },
            buffer ->
            {
                int containerId = buffer.readVarInt();
                int count = buffer.readVarInt();
                if (count < 0 || count > 64) throw new IllegalArgumentException("Invalid fluid slot count: " + count);
                List<FluidStack> fluids = new ArrayList<>(count);
                for (int i = 0; i < count; i++) fluids.add(FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer));
                return new FluidSyncPacket(containerId, fluids);
            });

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(FluidSyncPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player().containerMenu instanceof AbstractContainerMenuWithFluidSlot menu &&
                    menu.containerId == packet.containerId)
            {
                menu.syncFluids(packet.fluids);
            }
        });
    }
}
