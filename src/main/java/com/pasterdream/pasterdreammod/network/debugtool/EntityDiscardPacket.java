package com.pasterdream.pasterdreammod.network.debugtool;


import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class EntityDiscardPacket implements CustomPacketPayload
{
    public static final Type<EntityDiscardPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/entity_discard"));
    public static final StreamCodec<FriendlyByteBuf, EntityDiscardPacket> STREAM_CODEC =
            StreamCodec.ofMember(EntityDiscardPacket::encode, EntityDiscardPacket::new);

    @Override
    public Type<EntityDiscardPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final int entityId;

    public EntityDiscardPacket(int menuId, int entityId)
    {
        this.menuId = menuId;
        this.entityId = entityId;
    }

    public EntityDiscardPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.entityId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
        buf.writeVarInt(entityId);
    }

    public static void handle(EntityDiscardPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if(player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof DebugToolEntityEditorMenu menu && menu.getEntityId() == packet.entityId)
            {
                ServerLevel level = player.serverLevel();
                Entity entity = level.getEntity(packet.entityId);
                if (entity != null)
                {
                    entity.discard();
                    player.closeContainer();
                }
            }
        });
    }
}
