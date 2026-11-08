package com.pasterdream.pasterdreammod.network;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.DreamCauldronMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DreamCauldronCraftPacket() implements CustomPacketPayload
{
    public static final Type<DreamCauldronCraftPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_cauldron_craft"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DreamCauldronCraftPacket> STREAM_CODEC =
            StreamCodec.unit(new DreamCauldronCraftPacket());

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(DreamCauldronCraftPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player && player.containerMenu instanceof DreamCauldronMenu menu
                    && menu.stillValid(player))
            {
                menu.getBlockEntity().craft(player);
            }
        });
    }
}
