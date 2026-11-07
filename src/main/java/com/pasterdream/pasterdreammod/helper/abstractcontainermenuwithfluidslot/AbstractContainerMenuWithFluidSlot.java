package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import com.pasterdream.pasterdreammod.network.fluidslot.FluidSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainerMenuWithFluidSlot extends AbstractContainerMenu
{
    private final List<FluidSlot> fluidSlots = new ArrayList<>();
    private List<FluidStack> lastFluids = List.of();
    private ServerPlayer viewer;

    protected AbstractContainerMenuWithFluidSlot(MenuType<?> type, int id)
    {
        super(type, id);
    }

    protected void addFluidSlot(FluidSlot slot)
    {
        fluidSlots.add(slot);
    }

    public List<FluidSlot> getFluidSlots()
    {
        return fluidSlots;
    }

    protected void bindFluidViewer(Player player)
    {
        if (player instanceof ServerPlayer serverPlayer) viewer = serverPlayer;
    }

    public FluidStack getFluid(int index)
    {
        return index >= 0 && index < fluidSlots.size() ? fluidSlots.get(index).getFluid() : FluidStack.EMPTY;
    }

    public void syncFluids(List<FluidStack> fluids)
    {
        if (fluids.size() != fluidSlots.size()) return;
        for (int i = 0; i < fluids.size(); i++) fluidSlots.get(i).setFluid(fluids.get(i).copy());
    }

    public void syncFluids(ServerPlayer player)
    {
        List<FluidStack> fluids = snapshotFluids();
        lastFluids = fluids;
        PacketDistributor.sendToPlayer(player, new FluidSyncPacket(containerId, fluids));
    }

    @Override
    public void broadcastChanges()
    {
        super.broadcastChanges();
        if (viewer != null && viewer.containerMenu == this && !sameFluids(lastFluids, snapshotFluids())) syncFluids(viewer);
    }

    public void interactFluid(Player player, int index, boolean insert)
    {
        if (!stillValid(player) || index < 0 || index >= fluidSlots.size()) return;
        FluidSlot slot = fluidSlots.get(index);
        if (!slot.isActive() || (!insert && !slot.mayPickup(player))) return;
        ItemStack carried = getCarried();
        if (carried.isEmpty()) return;
        if (insert && !slot.mayPlace(FluidUtil.getFluidContained(carried.copyWithCount(1))
                .orElse(FluidStack.EMPTY))) return;

        FluidTank tank = new FluidTank(slot.getMaxCapacity());
        tank.setFluid(slot.getFluid().copy());
        FluidStack soundFluid = insert ? FluidUtil.getFluidContained(carried.copyWithCount(1))
                .orElse(FluidStack.EMPTY) : slot.getFluid().copy();
        FluidActionResult result = insert
                ? FluidUtil.tryEmptyContainer(carried.copyWithCount(1), tank, slot.getMaxCapacity(), player, true)
                : FluidUtil.tryFillContainer(carried.copyWithCount(1), tank, slot.getMaxCapacity(), player, true);
        if (!result.isSuccess() || (insert && !slot.mayPlace(tank.getFluid()))) return;

        slot.setFluid(tank.getFluid().copy());
        if (!soundFluid.isEmpty())
        {
            SoundEvent sound = soundFluid.getFluidType().getSound(soundFluid,
                    insert ? SoundActions.BUCKET_EMPTY : SoundActions.BUCKET_FILL);
            if (sound == null) sound = insert ? SoundEvents.BUCKET_EMPTY : SoundEvents.BUCKET_FILL;
            player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (carried.getCount() == 1) setCarried(result.getResult());
        else
        {
            setCarried(carried.copyWithCount(carried.getCount() - 1));
            ItemStack produced = result.getResult();
            if (!produced.isEmpty() && !player.getInventory().add(produced)) player.drop(produced, false);
        }
        broadcastChanges();
    }

    private List<FluidStack> snapshotFluids()
    {
        List<FluidStack> fluids = new ArrayList<>(fluidSlots.size());
        for (FluidSlot slot : fluidSlots) fluids.add(slot.getFluid().copy());
        return fluids;
    }

    private static boolean sameFluids(List<FluidStack> left, List<FluidStack> right)
    {
        if (left.size() != right.size()) return false;
        for (int i = 0; i < left.size(); i++)
        {
            if (!FluidStack.matches(left.get(i), right.get(i))) return false;
        }
        return true;
    }
}
