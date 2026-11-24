package com.pasterdream.pasterdreammod.network.rebirthdreamcrystal;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.rebirthdreamcrystal.RebirthDreamCrystalScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class NotesProgressResponsePacket implements CustomPacketPayload
{
    public static final Type<NotesProgressResponsePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rebirth_dream_crystal/notes_progress_response"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NotesProgressResponsePacket> STREAM_CODEC =
            StreamCodec.ofMember(NotesProgressResponsePacket::encode, NotesProgressResponsePacket::new);

    private final String dimension;
    private final List<Boolean> progressData;
    private final List<Double> taskProgress;
    private final List<Double> taskMaxProgress;
    private final ItemStack needUploadItemStack;
    private final boolean isCompleteThisDimensionTasks;

    public NotesProgressResponsePacket(String dimension, List<Boolean> progressData, List<Double> taskProgress, List<Double> taskMaxProgress, ItemStack needUploadItemStack, boolean isCompleteThisDimensionTasks)
    {
        this.dimension = dimension;
        this.progressData = progressData;
        this.taskProgress = taskProgress;
        this.taskMaxProgress = taskMaxProgress;
        this.needUploadItemStack = needUploadItemStack;
        this.isCompleteThisDimensionTasks = isCompleteThisDimensionTasks;
    }

    public NotesProgressResponsePacket(RegistryFriendlyByteBuf buffer)
    {
        this.dimension = buffer.readUtf();

        int progressDataSize = buffer.readVarInt();
        List<Boolean> progressData = new ArrayList<>(progressDataSize);
        for (int i = 0; i < progressDataSize; i++)
        {
            progressData.add(buffer.readBoolean());
        }

        int taskProgressSize = buffer.readVarInt();
        List<Double> taskProgress = new ArrayList<>(taskProgressSize);
        for (int i = 0; i < taskProgressSize; i++)
        {
            taskProgress.add(buffer.readDouble());
        }

        int taskMaxProgressSize = buffer.readVarInt();
        List<Double> taskMaxProgress = new ArrayList<>(taskMaxProgressSize);
        for (int i = 0; i < taskMaxProgressSize; i++)
        {
            taskMaxProgress.add(buffer.readDouble());
        }

        ItemStack needUploadItemStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

        boolean isCompleteThisDimensionTasks = buffer.readBoolean();

        this.progressData = progressData;
        this.taskProgress = taskProgress;
        this.taskMaxProgress = taskMaxProgress;
        this.needUploadItemStack = needUploadItemStack;
        this.isCompleteThisDimensionTasks = isCompleteThisDimensionTasks;
    }

    @Override
    public Type<NotesProgressResponsePacket> type()
    {
        return TYPE;
    }

    public void encode(RegistryFriendlyByteBuf buffer)
    {
        buffer.writeUtf(dimension);

        buffer.writeVarInt(progressData.size());
        for(boolean isRead : progressData)
        {
            buffer.writeBoolean(isRead);
        }

        buffer.writeVarInt(taskProgress.size());
        for(double progress : taskProgress)
        {
            buffer.writeDouble(progress);
        }

        buffer.writeVarInt(taskMaxProgress.size());
        for(double maxProgress : taskMaxProgress)
        {
            buffer.writeDouble(maxProgress);
        }

        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, needUploadItemStack);

        buffer.writeBoolean(isCompleteThisDimensionTasks);
    }

    public static void handle(NotesProgressResponsePacket message, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (Minecraft.getInstance().screen instanceof RebirthDreamCrystalScreen screen)
            {
                screen.onNotesProgressReceived(message.dimension, message.progressData, message.taskProgress, message.taskMaxProgress, message.needUploadItemStack, message.isCompleteThisDimensionTasks);
            }
        });
    }
}
