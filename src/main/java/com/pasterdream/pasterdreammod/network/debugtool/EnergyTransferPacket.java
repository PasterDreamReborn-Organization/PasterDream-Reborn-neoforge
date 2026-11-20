package com.pasterdream.pasterdreammod.network.debugtool;

import com.pasterdream.pasterdreammod.helper.energycalculator.EnergyCalculator;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.EnergyStorageLaunchData;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu;
import net.minecraft.network.FriendlyByteBuf;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.energy.IEnergyStorage;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class EnergyTransferPacket implements CustomPacketPayload
{
    public static final Type<EnergyTransferPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "debug_tool/energy_transfer"));
    public static final StreamCodec<FriendlyByteBuf, EnergyTransferPacket> STREAM_CODEC =
            StreamCodec.ofMember(EnergyTransferPacket::encode, EnergyTransferPacket::new);

    @Override
    public Type<EnergyTransferPacket> type()
    {
        return TYPE;
    }

    private final int menuId;
    private final int amount;

    public EnergyTransferPacket(int menuId, int amount)
    {
        this.menuId = menuId;
        this.amount = amount;
    }

    public EnergyTransferPacket(FriendlyByteBuf buffer)
    {
        this.menuId = buffer.readVarInt();
        this.amount = buffer.readVarInt();
    }

    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeVarInt(menuId);
        buffer.writeVarInt(amount);
    }

    public static void handle(EnergyTransferPacket packet, IPayloadContext context)
    {
        context.enqueueWork(() ->
        {
            if (!(context.player() instanceof ServerPlayer player)
                    || !DebugToolItem.canEdit(player) || !player.containerMenu.stillValid(player))
            {
                return;
            }
            if (player.containerMenu.containerId == packet.menuId && player.containerMenu instanceof IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu menu && packet.amount != 0 && packet.amount != Integer.MIN_VALUE)
            {
                EnergyStorageLaunchData external = menu.provideEnergyStorageExternal();
                EnergyStorageLaunchData target = menu.provideEnergyStorageTarget();
                if (external != null && target != null)
                {
                    IEnergyStorage disCharge;
                    IEnergyStorage charge;
                    int amount;

                    if (packet.amount > 0)
                    {
                        disCharge = external.handler();
                        charge = target.handler();
                        amount = packet.amount;
                    }
                        else
                        {
                            disCharge = target.handler();
                            charge = external.handler();
                            amount = -packet.amount;
                        }

                    EnergyCalculator.calculate(disCharge, charge, amount);
                    external.onChanged().run();
                    target.onChanged().run();
                    player.containerMenu.broadcastChanges();
                }
            }
        });
    }
}
