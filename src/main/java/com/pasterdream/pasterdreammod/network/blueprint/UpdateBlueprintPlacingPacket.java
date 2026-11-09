package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateBlueprintPlacingPacket(boolean placing) implements CustomPacketPayload
{
    public static final Type<UpdateBlueprintPlacingPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "update_blueprint_placing"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateBlueprintPlacingPacket> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, UpdateBlueprintPlacingPacket::placing,
                    UpdateBlueprintPlacingPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(UpdateBlueprintPlacingPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                ItemStack held = player.getMainHandItem();
                if (held.getItem() instanceof BluePrintItem)
                {
                    held.set(ModDataComponents.BLUEPRINT_PLACING.get(), message.placing);
                    player.inventoryMenu.broadcastChanges();
                }
            }
        });
    }
}
