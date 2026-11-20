package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class SyncEntityNbtPacket implements CustomPacketPayload
{
    public static final Type<SyncEntityNbtPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/sync_entity_nbt"));
    public static final StreamCodec<FriendlyByteBuf, SyncEntityNbtPacket> STREAM_CODEC =
            StreamCodec.ofMember(SyncEntityNbtPacket::encode, SyncEntityNbtPacket::new);

    @Override
    public Type<SyncEntityNbtPacket> type()
    {
        return TYPE;
    }

    private final CompoundTag NBT;

    private final int menuId;

    public SyncEntityNbtPacket(int menuId, CompoundTag NBT)
    {
        this.menuId = menuId;
        this.NBT = NBT;
    }

    public SyncEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeNbt(NBT);
    }

    public static void handle(SyncEntityNbtPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (context.player().containerMenu instanceof DebugToolEntityEditorMenu menu
                    && menu.containerId == packet.menuId)
            {
                menu.setServerEntityNbt(packet.NBT);
            }
        });
    }
}
