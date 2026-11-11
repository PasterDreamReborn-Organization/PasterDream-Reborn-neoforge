package com.pasterdream.pasterdreammod.network.researchtable;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.researchtable.ResearchTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ResearchTableResearchPacket(BlockPos blockPosition) implements CustomPacketPayload
{
    public static final Type<ResearchTableResearchPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "research_table_research"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ResearchTableResearchPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, packet) -> buffer.writeBlockPos(packet.blockPosition),
            buffer -> new ResearchTableResearchPacket(buffer.readBlockPos()));

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(ResearchTableResearchPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player))
            {
                return;
            }

            ServerLevel level = player.serverLevel();
            BlockEntity blockEntity = level.getBlockEntity(packet.blockPosition);
            if (blockEntity instanceof ResearchTableBlockEntity researchTable)
            {
                researchTable.researchNote();
            }
        });
    }
}
