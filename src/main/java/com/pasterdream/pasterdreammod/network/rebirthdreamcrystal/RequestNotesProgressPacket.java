package com.pasterdream.pasterdreammod.network.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.playerdata.PasterDreamPlayerDreamNoteProgressData;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamDimensionTask;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.task.IPasterDreamSingleTask;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookProgressRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class RequestNotesProgressPacket implements CustomPacketPayload
{
    public static final Type<RequestNotesProgressPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rebirth_dream_crystal/request_notes_progress"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestNotesProgressPacket> STREAM_CODEC =
            StreamCodec.ofMember(RequestNotesProgressPacket::encode, RequestNotesProgressPacket::new);

    private final String dimension;

    public RequestNotesProgressPacket(String dimension)
    {
        this.dimension = dimension;
    }

    public RequestNotesProgressPacket(RegistryFriendlyByteBuf buffer)
    {
        this.dimension = buffer.readUtf();
    }

    @Override
    public Type<RequestNotesProgressPacket> type()
    {
        return TYPE;
    }

    public void encode(RegistryFriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);
    }

    public static void handle(RequestNotesProgressPacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player() instanceof ServerPlayer player)
            {
                List<Boolean> progressData = PasterDreamPlayerDreamNoteProgressData.getProgressData(player, message.dimension);
                IPasterDreamDimensionTask dimensionTask = DreamNotesBookProgressRegistry.getTaskByDimension(message.dimension);
                List<Double> taskProgress = new ArrayList<>();
                List<Double> taskMaxProgress = new ArrayList<>();
                ItemStack needUploadItemStack = ItemStack.EMPTY;
                boolean isCompleteThisDimensionTasks = false;

                if(dimensionTask != null)
                {
                    for(IPasterDreamSingleTask singleTask : dimensionTask.getAllTasks(player))
                    {
                        taskProgress.add(singleTask.getTaskProgress(player));
                        taskMaxProgress.add(singleTask.getTaskMaxProgress(player));
                    }
                    needUploadItemStack = dimensionTask.getUploadItemStack(player);
                    isCompleteThisDimensionTasks = PasterDreamPlayerDreamNoteProgressData.getTaskStatus(player, message.dimension);
                }

                PacketDistributor.sendToPlayer(player, new NotesProgressResponsePacket(message.dimension, progressData, taskProgress, taskMaxProgress, needUploadItemStack, isCompleteThisDimensionTasks));
            }
        });
    }
}
