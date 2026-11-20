package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt.GetSpawnEgg;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class StoreEntityToInventoryPacket implements CustomPacketPayload
{
    public static final Type<StoreEntityToInventoryPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/store_entity_to_inventory"));
    public static final StreamCodec<FriendlyByteBuf, StoreEntityToInventoryPacket> STREAM_CODEC =
            StreamCodec.ofMember(StoreEntityToInventoryPacket::encode, StoreEntityToInventoryPacket::new);

    @Override
    public Type<StoreEntityToInventoryPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final int entityId;

    public StoreEntityToInventoryPacket(int menuId, int entityId)
    {
        this.menuId = menuId;
        this.entityId = entityId;
    }

    public StoreEntityToInventoryPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.entityId = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
        buf.writeVarInt(entityId);
    }

    public static void handle(StoreEntityToInventoryPacket packet, IPayloadContext context)
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
                    ItemStack itemStack = GetSpawnEgg.getSpawnEgg(player.level().getEntity(packet.entityId));
                    if (!itemStack.isEmpty())
                    {
                        if (!player.getInventory().add(itemStack))
                        {
                            player.drop(itemStack, false);
                        }
                    }
                }
            }
        });
    }
}
