package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class SyncBlockNbtPacket implements CustomPacketPayload
{
    public static final Type<SyncBlockNbtPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/sync_block_nbt"));
    public static final StreamCodec<FriendlyByteBuf, SyncBlockNbtPacket> STREAM_CODEC =
            StreamCodec.ofMember(SyncBlockNbtPacket::encode, SyncBlockNbtPacket::new);

    @Override
    public Type<SyncBlockNbtPacket> type()
    {
        return TYPE;
    }

    private final CompoundTag compoundTag;

    private final int menuId;

    public SyncBlockNbtPacket(int menuId, CompoundTag compoundTag)
    {
        this.menuId = menuId;
        this.compoundTag = compoundTag;
    }

    public SyncBlockNbtPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.compoundTag = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeNbt(compoundTag);
    }

    public static void handle(SyncBlockNbtPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player().containerMenu instanceof DebugToolBlockEditorMenu menu
                    && menu.containerId == packet.menuId)
            {
                menu.setServerBlockNbt(packet.compoundTag);
            }
        });
    }
}
