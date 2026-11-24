package com.pasterdream.pasterdreammod.network.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.RebirthDreamCrystalMenu;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class UploadItemPacket implements CustomPacketPayload
{
    public static final Type<UploadItemPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rebirth_dream_crystal/upload_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UploadItemPacket> STREAM_CODEC =
            StreamCodec.ofMember(UploadItemPacket::encode, UploadItemPacket::new);

    private final String dimension;

    public UploadItemPacket(String dimension)
    {
        this.dimension = dimension;
    }

    public UploadItemPacket(RegistryFriendlyByteBuf buffer)
    {
        this.dimension = buffer.readUtf();
    }

    @Override
    public Type<UploadItemPacket> type()
    {
        return TYPE;
    }

    public void encode(RegistryFriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);
    }

    public static void handle(UploadItemPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                AbstractContainerMenu menu = player.containerMenu;
                if(menu instanceof RebirthDreamCrystalMenu rebirthDreamCrystalMenu)
                {
                    IPasterDreamDimensionTask dimensionTask = DreamNotesBookProgressRegistry.getTaskByDimension(message.dimension);
                    if(dimensionTask != null && dimensionTask.isAllSingleTaskCompleted(player) && !PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, message.dimension))
                    {
                        ItemStack needUploadItemStack = dimensionTask.getUploadItemStack(player);
                        ItemStack currentUploadItemStack = rebirthDreamCrystalMenu.getUploadSlotItemStack();
                        if(ItemStack.isSameItem(needUploadItemStack, currentUploadItemStack) && currentUploadItemStack.getCount() >= needUploadItemStack.getCount())
                        {
                            currentUploadItemStack.shrink(needUploadItemStack.getCount());
                            PasterDreamPlayerDreamNoteProgressData.setItem(player, currentUploadItemStack);
                            dimensionTask.completeTask(player).run();
                        }
                    }
                }
            }
        });
    }
}
