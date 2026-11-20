package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.FluidHandlerLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.FluidHandlerMenu;
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


public class OpenFluidHandlerPacket implements CustomPacketPayload
{
    public static final Type<OpenFluidHandlerPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/open_fluid_handler"));
    public static final StreamCodec<FriendlyByteBuf, OpenFluidHandlerPacket> STREAM_CODEC =
            StreamCodec.ofMember(OpenFluidHandlerPacket::encode, OpenFluidHandlerPacket::decode);

    @Override
    public Type<OpenFluidHandlerPacket> type()
    {
        return TYPE;
    }

    private final int menuId;

    public OpenFluidHandlerPacket(int menuId)
    {
        this.menuId = menuId;
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
    }

    public static OpenFluidHandlerPacket decode(FriendlyByteBuf buffer)
    {
        return new OpenFluidHandlerPacket(buffer.readVarInt());
    }

    public static void handle(OpenFluidHandlerPacket packet, IPayloadContext context)
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
                    FluidHandlerLaunchData data = editor.provideFluidHandlerLaunch();
                    if(data != null)
                    {
                        MenuProvider parent = editor.asMenuProvider();
                        DebugToolBlockEditorMenu blockEditor = current instanceof DebugToolBlockEditorMenu block ? block : null;
                        BlockEntity original = blockEditor == null ? null : blockEditor.getBlockEntity();
                        BooleanSupplier targetValid = () -> current.stillValid(player)
                                && (blockEditor == null || blockEditor.getBlockEntity() == original);
                        final int totalSlots = data.handler().getTanks();
                        if (totalSlots > 4096)
                        {
                            return;
                        }
                        int[] tankCapacities = new int[totalSlots];
                        for (int i = 0; i < totalSlots; i++)
                        {
                            tankCapacities[i] = data.handler().getTankCapacity(i);
                        }
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
                                return new FluidHandlerMenu(id, playerInventory, data.handler(), data.onChanged(), parent, targetValid);
                            }
                        }, buffer -> buffer.writeVarIntArray(tankCapacities));
                    }
                    else
                    {
                        player.displayClientMessage(Component.translatable("message.pasterdream.无FluidHandler"), true);
                    }
                }
            }
        });
    }
}
