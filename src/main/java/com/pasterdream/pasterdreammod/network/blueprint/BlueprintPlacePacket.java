package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.blueprint.BlueprintPlacementManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BlueprintPlacePacket(BlockPos targetPos) implements CustomPacketPayload
{
    public static final Type<BlueprintPlacePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "blueprint_place"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlueprintPlacePacket> STREAM_CODEC =
            StreamCodec.composite(BlockPos.STREAM_CODEC, BlueprintPlacePacket::targetPos,
                    BlueprintPlacePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(BlueprintPlacePacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                BlueprintPlacementManager.tryPlace(player, message.targetPos);
            }
        });
    }
}
