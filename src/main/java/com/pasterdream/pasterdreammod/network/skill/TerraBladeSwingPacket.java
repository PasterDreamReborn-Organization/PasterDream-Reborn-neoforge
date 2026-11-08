package com.pasterdream.pasterdreammod.network.skill;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.TerraBladeItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TerraBladeSwingPacket() implements CustomPacketPayload {

    public static final Type<TerraBladeSwingPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "terra_blade_swing"));
    public static final StreamCodec<ByteBuf, TerraBladeSwingPacket> STREAM_CODEC =
            StreamCodec.unit(new TerraBladeSwingPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TerraBladeSwingPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof TerraBladeItem) {
                player.swing(InteractionHand.MAIN_HAND);
                TerraBladeItem.tryFireSwordWave(player, stack);
            }
        });
    }
}
