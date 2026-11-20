package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.menu.FluidHandlerMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class FluidTransferPacket implements CustomPacketPayload
{
    public static final Type<FluidTransferPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/fluid_transfer"));
    public static final StreamCodec<FriendlyByteBuf, FluidTransferPacket> STREAM_CODEC =
            StreamCodec.ofMember(FluidTransferPacket::encode, FluidTransferPacket::new);

    @Override
    public Type<FluidTransferPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final boolean extract;   //true:方块->物品;false:物品->方块

    public FluidTransferPacket(int menuId, boolean extract)
    {
        this.menuId = menuId;
        this.extract = extract;
    }

    public FluidTransferPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.extract = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeBoolean(extract);
    }

    public static void handle(FluidTransferPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if(player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof FluidHandlerMenu menu)
            {
                Slot containerSlot = menu.getSlot(36);
                ItemStack containerItem = containerSlot.getItem();

                if (!containerItem.isEmpty())
                {
                    IFluidHandler targetHandler = menu.getFluidHandler();
                    if (targetHandler != null)
                    {
                        FluidActionResult result = packet.extract ? FluidUtil.tryFillContainer(containerItem, targetHandler, 2147483647, null, true) : FluidUtil.tryEmptyContainer(containerItem, targetHandler, 2147483647, null, true);
                        if (result.isSuccess())
                        {
                            if (containerItem.getCount() == 1)
                            {
                                containerSlot.set(result.getResult());
                            }
                                else
                                {
                                    ItemStack remaining = containerItem.copyWithCount(containerItem.getCount() - 1);
                                    containerSlot.set(remaining);

                                    ItemStack newItem = result.getResult();
                                    if (!newItem.isEmpty())
                                    {
                                        if (!player.getInventory().add(newItem))
                                        {
                                            player.drop(newItem, false);
                                        }
                                    }
                                }

                            containerSlot.setChanged();
                            if (menu.getOnChanged() != null)
                            {
                                menu.getOnChanged().run();
                            }

                            menu.broadcastChanges();
                        }
                    }
                }
            }
        });
    }
}
