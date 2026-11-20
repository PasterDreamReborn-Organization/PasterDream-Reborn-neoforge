package com.pasterdream.pasterdreammod.network.menu;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolItemEditorMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;

public class SetSlotNbtPacket implements CustomPacketPayload
{
    public static final Type<SetSlotNbtPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/set_slot_nbt"));
    public static final StreamCodec<FriendlyByteBuf, SetSlotNbtPacket> STREAM_CODEC =
            StreamCodec.ofMember(SetSlotNbtPacket::encode, SetSlotNbtPacket::new);

    @Override
    public Type<SetSlotNbtPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final int slotIndex;
    @Nullable
    private final CompoundTag tag;

    public SetSlotNbtPacket(int menuId, int slotIndex, @Nullable CompoundTag tag)
    {
        this.menuId = menuId;
        this.slotIndex = slotIndex;
        this.tag = tag;
    }

    public SetSlotNbtPacket(FriendlyByteBuf buf)
    {
        this.menuId = buf.readVarInt();
        this.slotIndex = buf.readVarInt();
        this.tag = buf.readNbt();
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
        buf.writeVarInt(slotIndex);
        buf.writeNbt(tag);
    }

    public static void handle(SetSlotNbtPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if (player.containerMenu instanceof DebugToolItemEditorMenu menu && menu.containerId == packet.menuId && packet.slotIndex == 36)
            {
                if(packet.slotIndex >= 0 && packet.slotIndex < menu.slots.size())
                {
                    Slot slot = menu.getSlot(packet.slotIndex);
                    ItemStack itemStack = slot.getItem();
                    if (!itemStack.isEmpty())
                    {
                        CompoundTag serialized = (CompoundTag) itemStack.save(player.registryAccess());
                        serialized.remove("components");
                        if (packet.tag != null)
                        {
                            serialized.put("components", packet.tag);
                        }
                        ItemStack.parse(player.registryAccess(), serialized).ifPresent(updated ->
                        {
                            slot.set(updated);
                            menu.broadcastChanges();
                        });
                    }
                }
            }
        });
    }
}
