package com.pasterdream.pasterdreammod.network.animationstatechange;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AnimationStateChangePacket(BlockPos pos, int animationState) implements CustomPacketPayload
{
    public static final Type<AnimationStateChangePacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animation_state_change"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AnimationStateChangePacket> STREAM_CODEC =
            StreamCodec.composite(BlockPos.STREAM_CODEC, AnimationStateChangePacket::pos,
                    ByteBufCodecs.VAR_INT, AnimationStateChangePacket::animationState,
                    AnimationStateChangePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(AnimationStateChangePacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() != null && context.player().level() != null)
            {
                BlockEntity blockEntity = context.player().level().getBlockEntity(message.pos);
                if (blockEntity instanceof AnimatableSync sync)
                {
                    sync.setAnimationState(message.animationState);
                }
            }
        });
    }
}
