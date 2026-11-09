package com.pasterdream.pasterdreammod.world.block.weaponworkshop.anvil;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WeaponWorkshopAnvilBlockEntity extends BlockEntity implements MenuProvider
{
    public WeaponWorkshopAnvilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_ANVIL.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(2)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex == 0;
        }
    };

    private final IItemHandler externalHandler = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return itemHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slotIndex)
        {
            return itemHandler.getStackInSlot(slotIndex);
        }

        @Override
        public @NotNull ItemStack insertItem(int slotIndex, @NotNull ItemStack itemStack, boolean isSimulate)
        {
            return itemHandler.insertItem(slotIndex, itemStack, isSimulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slotIndex, int amount, boolean isSimulate)
        {
            if(slotIndex == 1)
            {
                return itemHandler.extractItem(slotIndex, amount, isSimulate);
            }
                else
                {
                    return ItemStack.EMPTY;
                }
        }

        @Override
        public int getSlotLimit(int slotIndex)
        {
            return itemHandler.getSlotLimit(slotIndex);
        }

        @Override
        public boolean isItemValid(int slotIndex, @NotNull ItemStack itemStack)
        {
            return itemHandler.isItemValid(slotIndex, itemStack);
        }
    };

    private void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".weapon_workshop_anvil");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new WeaponWorkshopAnvilMenu(id, inventory, this);
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    public IItemHandler getExternalHandler()
    {
        return externalHandler;
    }
}
