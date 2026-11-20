package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class SyncBlockStatePacket implements CustomPacketPayload
{
    public static final Type<SyncBlockStatePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/sync_block_state"));
    public static final StreamCodec<FriendlyByteBuf, SyncBlockStatePacket> STREAM_CODEC =
            StreamCodec.ofMember(SyncBlockStatePacket::encode, SyncBlockStatePacket::new);

    @Override
    public Type<SyncBlockStatePacket> type()
    {
        return TYPE;
    }

    private final String stateString;

    private final int menuId;

    public SyncBlockStatePacket(int menuId, String string)
    {
        this.menuId = menuId;
        this.stateString = string;
    }

    public SyncBlockStatePacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.stateString = buffer.readUtf();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeUtf(stateString);
    }

    public static void handle(SyncBlockStatePacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player().containerMenu instanceof DebugToolBlockEditorMenu menu
                    && menu.containerId == packet.menuId)
            {
                menu.setServerBlockStateString(packet.stateString);
            }
        });
    }
}
