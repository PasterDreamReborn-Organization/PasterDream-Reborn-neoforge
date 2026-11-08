package com.pasterdream.pasterdreammod.network.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.KaichuOmamoriItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KaichuOmamoriActivationPacket() implements CustomPacketPayload {

    public static final Type<KaichuOmamoriActivationPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "kaichu_omamori_activation"));
    public static final StreamCodec<ByteBuf, KaichuOmamoriActivationPacket> STREAM_CODEC =
            StreamCodec.unit(new KaichuOmamoriActivationPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KaichuOmamoriActivationPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            BlockHitResult hit = (BlockHitResult) player.pick(5.0, 1.0F, false);
            BlockPos pos;
            if (hit.getType() != HitResult.Type.MISS) {
                pos = hit.getBlockPos().relative(hit.getDirection());
            } else {
                pos = BlockPos.containing(
                        player.getX() + player.getLookAngle().x * 2,
                        player.getY() + player.getEyeHeight() + player.getLookAngle().y * 2,
                        player.getZ() + player.getLookAngle().z * 2);
            }
            KaichuOmamoriItem.tryActivate(player, player.level(), pos);
        });
    }
}
