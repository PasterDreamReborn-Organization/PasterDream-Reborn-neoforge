package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResearchTableBlockEntity extends BlockEntity implements MenuProvider
{
    private static final int FLUID_CAPACITY = 250;

    public ResearchTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RESEARCH_TABLE.get(), pos, state);
    }

    private final FluidTank[] fluidTanks =
    {
        new FluidTank(FLUID_CAPACITY)
        {
            protected void onContentsChanged()
            {
                setChangedAndSync();
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(6)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex != 3 && slotIndex != 5;
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
            if(slotIndex == 3 || slotIndex == 5)
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

    public void copyNote()
    {
        ResearchTableCopyRecipeInventory matchResult = ResearchTableCopyRecipeMatch.matches(new ResearchTableCopyRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1), itemHandler.getStackInSlot(2)), level.getRecipeManager().getAllRecipesFor(ModRecipes.RESEARCH_TABLE_COPY.get()).stream().map(RecipeHolder::value).toList());
        if(matchResult != null)
        {
            ResearchTableCopyRecipeInventoryWithOutputSlot result = ResearchTableCopyRecipeProcessor.processing(matchResult, new ResearchTableCopyRecipeInventoryWithOutputSlot(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1), itemHandler.getStackInSlot(2), itemHandler.getStackInSlot(3)), level.getRandom());
            if(result != null)
            {
                fluidTanks[0].setFluid(result.fluidStack());
                itemHandler.setStackInSlot(0, result.pen());
                itemHandler.setStackInSlot(1, result.sourceBook());
                itemHandler.setStackInSlot(2, result.material());
                itemHandler.setStackInSlot(3, result.output());
            }
        }
    }

    public void researchNote()
    {
        ResearchTableResearchRecipeInventory matchResult = ResearchTableResearchRecipeMatch.matches(new ResearchTableResearchRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(4), itemHandler.getStackInSlot(5)), level.getRecipeManager().getAllRecipesFor(ModRecipes.RESEARCH_TABLE_RESEARCH.get()).stream().map(RecipeHolder::value).toList());
        if(matchResult != null)
        {
            ResearchTableResearchRecipeInventory result = ResearchTableResearchRecipeProcessor.processing(matchResult, new ResearchTableResearchRecipeInventory(fluidTanks[0].getFluid(), itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(4), itemHandler.getStackInSlot(5)), level.getRandom());
            if(result != null)
            {
                fluidTanks[0].setFluid(result.fluidStack());
                itemHandler.setStackInSlot(0, result.pen());
                itemHandler.setStackInSlot(4, result.unresolvedNote());
                itemHandler.setStackInSlot(5, result.output());
            }
        }
    }

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
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries)
    {
        loadAdditional(tag, registries);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".research_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ResearchTableMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("FluidTank", fluidTanks[0].writeToNBT(registries, new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        fluidTanks[0].readFromNBT(registries, tag.getCompound("FluidTank"));
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries)
    {
        CompoundTag tag = packet.getTag();
        if (tag != null)
        {
            loadAdditional(tag, registries);
        }
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }

    public IItemHandler getExternalHandler()
    {
        return externalHandler;
    }

    public IFluidHandler getFluidHandler(int tankIndex)
    {
        return fluidTanks[tankIndex];
    }

    public FluidTank getFluidTank(int tankIndex)
    {
        return fluidTanks[tankIndex];
    }

    public FluidTank[] getFluidTanks()
    {
        return fluidTanks;
    }
}
