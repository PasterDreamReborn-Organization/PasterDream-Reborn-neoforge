package com.pasterdream.pasterdreammod.network.meltdreamcrystalchest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.MeltDreamCrystalChestResetCooldownClient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MeltDreamCrystalChestResetCooldownSyncPacket(int remainingTicks) implements CustomPacketPayload {
    public static final Type<MeltDreamCrystalChestResetCooldownSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_crystal_chest_reset_cooldown"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MeltDreamCrystalChestResetCooldownSyncPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.VAR_INT, MeltDreamCrystalChestResetCooldownSyncPacket::remainingTicks,
                    MeltDreamCrystalChestResetCooldownSyncPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MeltDreamCrystalChestResetCooldownSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> MeltDreamCrystalChestResetCooldownClient.setRemainingTicks(packet.remainingTicks()));
    }
}
