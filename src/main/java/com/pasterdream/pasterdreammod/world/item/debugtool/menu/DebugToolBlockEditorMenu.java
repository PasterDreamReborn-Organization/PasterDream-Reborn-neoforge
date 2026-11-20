package com.pasterdream.pasterdreammod.world.item.debugtool.menu;

import com.pasterdream.pasterdreammod.helper.nbthelper.WrappedNBTBlockItem;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.generichandler.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class DebugToolBlockEditorMenu extends AbstractContainerMenu implements IDebugItemHandlerAndFluidHandlerAndEnergyStorageEditorMenu
{
    private final Container editorContainer;
    private final Player player;
    private final Level level;
    private final BlockPos blockPosition;
    private final Direction face;
    private CompoundTag serverBlockNbt;
    private final List<Consumer<CompoundTag>> nbtListeners = new CopyOnWriteArrayList<>();
    private final List<Consumer<String>> blockStateListeners = new CopyOnWriteArrayList<>();
    private final DataSlot capabilities = DataSlot.standalone();
    private final DataSlot storedEnergy = DataSlot.standalone();
    private final DataSlot maxEnergy = DataSlot.standalone();
    private static final int ITEM_HANDLER = 1;
    private static final int FLUID_HANDLER = 2;
    private static final int ENERGY_STORAGE = 4;
    private static final int EXTRACT_ENERGY = 8;
    private static final int RECEIVE_ENERGY = 16;

    public DebugToolBlockEditorMenu(int id, Inventory playerInventory, BlockPos blockPosition, Direction face)
    {
        super(ModMenus.DEBUG_TOOL_BLOCK_EDITOR.get(), id);

        player = playerInventory.player;
        level = player.level();
        this.blockPosition = blockPosition;
        this.face = face;

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 217));
        }

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 159 + row * 18));
            }
        }

        this.editorContainer = new SimpleContainer(2)
        {
            @Override
            public void setChanged()
            {
                super.setChanged();
                if (!player.level().isClientSide)
                {
                    PlayerEditorSlotData.set(player, getItem(1), 1);
                }
            }
        };

        ItemStack stored = PlayerEditorSlotData.get(player, 1);
        if (!stored.isEmpty())
        {
            editorContainer.setItem(1, stored.copy());
        }

        addSlot(new Slot(editorContainer, 0, 152, 12)
        {
            @Override
            public void set(ItemStack itemStack)
            {
                if (level.isClientSide)
                {
                    super.set(itemStack);
                }
            }

            @Override
            public ItemStack getItem()
            {
                return level.isClientSide ? super.getItem() : getBlockItemStack();
            }

            @Override
            public boolean mayPlace(ItemStack itemStack)
            {
                return false;
            }

            @Override
            public boolean mayPickup(Player player)
            {
                return false;
            }
        });

        addSlot(new Slot(editorContainer, 1, 152, 128));

        addDataSlot(capabilities);
        addDataSlot(storedEnergy);
        addDataSlot(maxEnergy);
        refreshCapabilities();
    }

    private void refreshCapabilities()
    {
        if (level.isClientSide || !level.hasChunkAt(blockPosition))
        {
            return;
        }

        int available = 0;
        if (level.getCapability(Capabilities.ItemHandler.BLOCK, blockPosition, face) != null)
        {
            available |= ITEM_HANDLER;
        }
        if (level.getCapability(Capabilities.FluidHandler.BLOCK, blockPosition, face) != null)
        {
            available |= FLUID_HANDLER;
        }
        IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPosition, face);
        if (storage != null)
        {
            available |= ENERGY_STORAGE;
            if (storage.canExtract()) available |= EXTRACT_ENERGY;
            if (storage.canReceive()) available |= RECEIVE_ENERGY;
        }
        capabilities.set(available);
        storedEnergy.set(storage == null ? 0 : storage.getEnergyStored());
        maxEnergy.set(storage == null ? 0 : storage.getMaxEnergyStored());
    }

    @Override
    public void broadcastChanges()
    {
        refreshCapabilities();
        super.broadcastChanges();
    }

    public boolean hasItemHandler()
    {
        return (capabilities.get() & ITEM_HANDLER) != 0;
    }

    public boolean hasFluidHandler()
    {
        return (capabilities.get() & FLUID_HANDLER) != 0;
    }

    public boolean hasEnergyStorage()
    {
        return (capabilities.get() & ENERGY_STORAGE) != 0;
    }

    public boolean canExtractEnergy()
    {
        return (capabilities.get() & EXTRACT_ENERGY) != 0;
    }

    public boolean canReceiveEnergy()
    {
        return (capabilities.get() & RECEIVE_ENERGY) != 0;
    }

    public int getEnergyStored()
    {
        return storedEnergy.get();
    }

    public int getMaxEnergyStored()
    {
        return maxEnergy.get();
    }

    public DebugToolBlockEditorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer)
    {
        this(id, playerInventory, buffer.readBlockPos(), buffer.readEnum(Direction.class));
        this.serverBlockNbt = buffer.readNbt();
    }

    public void writeClientSideData(RegistryFriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(blockPosition);
        buffer.writeEnum(face);
        BlockEntity blockEntity = getBlockEntity();
        buffer.writeNbt(blockEntity == null ? null : blockEntity.saveWithId(level.registryAccess()));
    }

    private ItemStack getBlockItemStack()
    {
        BlockState blockState = level.getBlockState(blockPosition);
        if (blockState.isAir())
        {
            return ItemStack.EMPTY;
        }

        Item item = blockState.getBlock().asItem();
        if (item == Items.AIR)
        {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = new ItemStack(item);

        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        if (blockEntity != null)
        {
            CompoundTag nbt = blockEntity.saveWithId(level.registryAccess());
            if (!nbt.isEmpty())
            {
                WrappedNBTBlockItem.wrapper(itemStack, nbt);
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        if (index == 36) return ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if ((index == 37))
        {   //从机器移出到背包
            if (!this.moveItemStackTo(stack, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
            if((index >= 0 && index <= 35))
            {   //从背包移入输入槽
                if (!(this.moveItemStackTo(stack, 37, 38, false)))
                {
                    return ItemStack.EMPTY;
                }
            }

        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
            else
            {
                slot.setChanged();
            }
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return player.level().isClientSide || (DebugToolItem.canEdit(player) && player.level() == level
                && level.hasChunkAt(blockPosition) && player.canInteractWithBlock(blockPosition, 4.0)
                && player.mayInteract(level, blockPosition));
    }

    public void addNbtListener(Consumer<CompoundTag> listener)
    {
        nbtListeners.add(listener);
    }

    public void clearNbtListeners()
    {
        nbtListeners.clear();
    }

    public void setServerBlockNbt(CompoundTag nbt)
    {
        this.serverBlockNbt = nbt;
        for (Consumer<CompoundTag> listener : nbtListeners)
        {
            listener.accept(nbt);
        }
    }

    public void addBlockStateListener(Consumer<String> listener)
    {
        blockStateListeners.add(listener);
    }

    public void clearBlockStateListeners()
    {
        blockStateListeners.clear();
    }

    public void setServerBlockStateString(String stateString)
    {
        for (Consumer<String> listener : blockStateListeners)
        {
            listener.accept(stateString);
        }
    }

    @Override
    public MenuProvider asMenuProvider()
    {
        return new MenuProvider()
        {
            @Override
            public Component getDisplayName()
            {
                return Component.translatable("gui.pasterdream.debug_tool_block_editor");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
            {
                return new DebugToolBlockEditorMenu(id, playerInventory, blockPosition, face);
            }

            @Override
            public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer)
            {
                ((DebugToolBlockEditorMenu) menu).writeClientSideData(buffer);
            }
        };
    }

    @Override
    public ItemHandlerLaunchData provideItemHandlerLaunch()
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, blockPosition, face);
        if (handler != null)
        {
            Runnable onChanged = () ->
            {
                if (blockEntity != null) blockEntity.setChanged();
                BlockState state = getBlockState();
                level.sendBlockUpdated(blockPosition, state, state, 3);
            };
            return new ItemHandlerLaunchData(handler, onChanged);
        }
        return null;
    }

    @Override
    public @Nullable FluidHandlerLaunchData provideFluidHandlerLaunch()
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, blockPosition, face);
        if (handler != null)
        {
            Runnable onChanged = () ->
            {
                if (blockEntity != null) blockEntity.setChanged();
                BlockState state = getBlockState();
                level.sendBlockUpdated(blockPosition, state, state, 3);
            };
            return new FluidHandlerLaunchData(handler, onChanged);
        }
        return null;
    }

    @Override
    public EnergyStorageLaunchData provideEnergyStorageTarget()
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPosition);
        IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPosition, face);
        if (storage != null)
        {
            Runnable onChanged = () ->
            {
                if (blockEntity != null) blockEntity.setChanged();
                BlockState state = getBlockState();
                level.sendBlockUpdated(blockPosition, state, state, 3);
            };
            return new EnergyStorageLaunchData(storage, onChanged);
        }
        return null;
    }

    @Override
    public EnergyStorageLaunchData provideEnergyStorageExternal()
    {
        ItemStack stack = getSlot(37).getItem();
        IEnergyStorage storage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (storage != null)
        {
            Runnable onChanged = () ->
            {
                Slot slot = getSlot(37);
                slot.set(slot.getItem());
            };
            return new EnergyStorageLaunchData(storage, onChanged);
        }
            else
            {
                return null;
            }
    }

    public BlockEntity getBlockEntity()
    {
        return level.getBlockEntity(blockPosition);
    }

    public BlockPos getBlockPosition()
    {
        return blockPosition;
    }

    public CompoundTag getServerBlockNbt()
    {
        return serverBlockNbt;
    }

    public BlockState getBlockState()
    {
        return level.getBlockState(blockPosition);
    }

    public Level getLevel()
    {
        return level;
    }
}
