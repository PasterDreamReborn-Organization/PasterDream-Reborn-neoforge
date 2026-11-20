package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.ItemHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.ItemHandlerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.server.level.ServerPlayer;
import java.util.function.BooleanSupplier;

import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class OpenItemHandlerPacket implements CustomPacketPayload
{
    public static final Type<OpenItemHandlerPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/open_item_handler"));
    public static final StreamCodec<FriendlyByteBuf, OpenItemHandlerPacket> STREAM_CODEC =
            StreamCodec.ofMember(OpenItemHandlerPacket::encode, OpenItemHandlerPacket::decode);

    @Override
    public Type<OpenItemHandlerPacket> type()
    {
        return TYPE;
    }

    private final int menuId;

    public OpenItemHandlerPacket(int menuId)
    {
        this.menuId = menuId;
    }

    public void encode(FriendlyByteBuf buf)
    {
        buf.writeVarInt(menuId);
    }

    public static OpenItemHandlerPacket decode(FriendlyByteBuf buffer)
    {
        return new OpenItemHandlerPacket(buffer.readVarInt());
    }

    public static void handle(OpenItemHandlerPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if(player.containerMenu.containerId == packet.menuId)
            {
                AbstractContainerMenu current = player.containerMenu;
                if ((current instanceof IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu editor))
                {
                    ItemHandlerLaunchData data = editor.provideItemHandlerLaunch();
                    if(data != null)
                    {
                        MenuProvider parent = editor.asMenuProvider();
                        DebugToolBlockEditorMenu blockEditor = current instanceof DebugToolBlockEditorMenu block ? block : null;
                        BlockEntity original = blockEditor == null ? null : blockEditor.getBlockEntity();
                        BooleanSupplier targetValid = () -> current.stillValid(player)
                                && (blockEditor == null || blockEditor.getBlockEntity() == original);
                        final int totalSlots = data.handler().getSlots();
                        player.openMenu(new MenuProvider()
                        {
                            @Override
                            public Component getDisplayName()
                            {
                                return Component.empty();
                            }
                            @Override
                            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
                            {
                                return new ItemHandlerMenu(id, playerInventory, data.handler(), data.onChanged(), parent, targetValid);
                            }
                        }, buffer -> buffer.writeVarInt(totalSlots));
                    }
                    else
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.无ItemHandler"), true);
                    }
                }
            }
        });
    }
}
