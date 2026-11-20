package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

import javax.annotation.Nullable;

public class SetEntityNbtPacket implements CustomPacketPayload
{
    public static final Type<SetEntityNbtPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/set_entity_nbt"));
    public static final StreamCodec<FriendlyByteBuf, SetEntityNbtPacket> STREAM_CODEC =
            StreamCodec.ofMember(SetEntityNbtPacket::encode, SetEntityNbtPacket::new);

    @Override
    public Type<SetEntityNbtPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final int entityId;
    @Nullable private final CompoundTag NBT;

    public SetEntityNbtPacket(int menuId, int entityId, @Nullable CompoundTag NBT)
    {
        this.menuId = menuId;
        this.entityId = entityId;
        this.NBT = NBT;
    }

    public SetEntityNbtPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.entityId = buffer.readVarInt();
        this.NBT = buffer.readNbt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeVarInt(entityId);
        buffer.writeNbt(NBT);
    }

    public static void handle(SetEntityNbtPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if (player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof DebugToolEntityEditorMenu menu && menu.getEntityId() == packet.entityId)
            {
                ServerLevel level = player.serverLevel();
                Entity entity = level.getEntity(packet.entityId);

                if (entity != null)
                {
                    CompoundTag incoming = (packet.NBT == null ? new CompoundTag() : packet.NBT.copy());
                    // Entity identity is used by the server's entity manager and cannot change here.
                    incoming.putUUID("UUID", entity.getUUID());
                    CompoundTag previous = entity.saveWithoutId(new CompoundTag());
                    try
                    {
                        entity.load(incoming);
                    }
                    catch (RuntimeException exception)
                    {
                        entity.load(previous);
                        player.sendSystemMessage(Component.translatable("error.pasterdream.无效的NBT", exception.getMessage()));
                        return;
                    }
                    level.sendBlockUpdated(entity.blockPosition(), level.getBlockState(entity.blockPosition()), level.getBlockState(entity.blockPosition()), 3);
                    context.reply(new SyncEntityNbtPacket(menu.containerId, entity.saveWithoutId(new CompoundTag())));
                }
            }

        });
    }
}
