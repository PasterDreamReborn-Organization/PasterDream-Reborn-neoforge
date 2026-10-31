package com.pasterdream.pasterdreammod.network.skill;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.grasstool.MurakumoKusanagiItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * 客户端左键（空挥 / 破坏方块 / 攻击实体）→ 服务端，用于触发『天丛云』草薙的毒刺发射。
 * 与 {@code TerraBladeSwingPacket} 同构：避免用 {@code ServerPlayer#swing} 钩子（Q 丢物品会误触发）。
 */
public record KusanagiVenomStingerPacket() implements CustomPacketPayload {

    public static final Type<KusanagiVenomStingerPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "kusanagi_venom_stinger"));
    public static final StreamCodec<ByteBuf, KusanagiVenomStingerPacket> STREAM_CODEC =
            StreamCodec.unit(new KusanagiVenomStingerPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(KusanagiVenomStingerPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                MurakumoKusanagiItem.onSwing(player);
            }
        });
    }
}
