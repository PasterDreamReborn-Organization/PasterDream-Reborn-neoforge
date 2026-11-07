package com.pasterdream.pasterdreammod.network.mortar;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MortarCraftPacket() implements CustomPacketPayload
{
    public static final Type<MortarCraftPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar_craft"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MortarCraftPacket> STREAM_CODEC =
            StreamCodec.unit(new MortarCraftPacket());

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(MortarCraftPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player && player.containerMenu instanceof MortarMenu menu)
            {
                menu.craft();
            }
        });
    }
}
