package com.pasterdream.pasterdreammod.network.blueprint;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.blueprint.BlueprintPlacementManager;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record StartBlueprintPlacementPacket(CompoundTag materialNBT, CompoundTag resultNBT) implements CustomPacketPayload
{
    public static final Type<StartBlueprintPlacementPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "start_blueprint_placement"));
    public static final StreamCodec<RegistryFriendlyByteBuf, StartBlueprintPlacementPacket> STREAM_CODEC = StreamCodec.of(
            (buffer, packet) ->
            {
                buffer.writeNbt(packet.materialNBT);
                buffer.writeNbt(packet.resultNBT);
            },
            buffer -> new StartBlueprintPlacementPacket(buffer.readNbt(), buffer.readNbt()));

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }

    public static void handle(StartBlueprintPlacementPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof BluePrintItem)
                {
                    heldItem.set(ModDataComponents.BLUEPRINT_PLACING.get(), true);
                    player.inventoryMenu.broadcastChanges();
                }
                BlueprintPlacementManager.startPlacement(player, message.materialNBT, message.resultNBT);
            }
        });
    }
}
