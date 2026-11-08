package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShadowBlastFurnaceBlockEntity extends BlockEntity implements MenuProvider
{
    private static final int FLUID_CAPACITY = 9000;
    private int progress = 0;
    private int maxProgress = 0;
    private List<ItemStack> currentRecipeOutput = new ArrayList<>();

    public ShadowBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_BLAST_FURNACE.get(), pos, state);
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

    private final ItemStackHandler itemHandler = new ItemStackHandler(4)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return slotIndex < 2;
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
            if(slotIndex == 2 || slotIndex == 3)
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

    public void tick()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        if (maxProgress == 0)
        {
            matchRecipe();
        }

        if (maxProgress > 0)
        {
            progress++;
            setChanged();

            if (progress >= maxProgress)
            {
                generateProduct();
            }
        }
    }

    private void matchRecipe()
    {
        if (level == null || level.isClientSide)
        {
            return;
        }

        List<ShadowBlastFurnaceRecipe> recipes = level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.SHADOW_BLAST_FURNACE.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        List<ItemStack> inputItems = new ArrayList<>(2);
        inputItems.add(itemHandler.getStackInSlot(0).copy());
        inputItems.add(itemHandler.getStackInSlot(1).copy());

        List<ItemStack> outputItems = new ArrayList<>(2);
        outputItems.add(itemHandler.getStackInSlot(2).copy());
        outputItems.add(itemHandler.getStackInSlot(3).copy());

        List<FluidStack> inputFluids = new ArrayList<>(1);
        inputFluids.add(fluidTanks[0].getFluid().copy());

        GenericRecipeInventory matchedResult = GenericRecipeMatcher.match(inputItems, inputFluids, recipes);
        if(matchedResult != null)
        {
            GenericRecipeInventory processedResult = GenericRecipeProcesser.processing(matchedResult, new GenericRecipeInventory(inputItems, inputFluids, outputItems, List.of(), matchedResult.recipeTime(), 0));
            if(processedResult != null)
            {
                for(int i = 0; i < processedResult.inputItemStacks().size(); i++)
                {
                    itemHandler.setStackInSlot(i, processedResult.inputItemStacks().get(i));
                }

                for(int i = 0; i < processedResult.inputFluidStacks().size(); i++)
                {
                    fluidTanks[i].setFluid(processedResult.inputFluidStacks().get(i));
                }

                currentRecipeOutput = processedResult.outputItemStacks();
                maxProgress = matchedResult.recipeTime();
                //同步
                setChangedAndSync();
            }
        }
    }

    private void generateProduct()
    {
        itemHandler.setStackInSlot(2, currentRecipeOutput.get(0));
        itemHandler.setStackInSlot(3, currentRecipeOutput.get(1));

        progress = 0;
        maxProgress = 0;
        //同步
        setChangedAndSync();
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("FluidTank", fluidTanks[0].writeToNBT(registries, new CompoundTag()));
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        ListTag outputList = new ListTag();
        for (ItemStack itemStack : currentRecipeOutput)
        {
            if (!itemStack.isEmpty())
            {
                outputList.add(itemStack.save(registries, new CompoundTag()));
            }
        }
        tag.put("CurrentOutputs", outputList);
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        fluidTanks[0].readFromNBT(registries, tag.getCompound("FluidTank"));
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        currentRecipeOutput = new ArrayList<>();
        ListTag outputList = tag.getList("CurrentOutputs", 10);
        for (int i = 0; i < outputList.size(); i++)
        {
            ItemStack itemStack = ItemStack.parseOptional(registries, outputList.getCompound(i));
            if (!itemStack.isEmpty())
            {
                currentRecipeOutput.add(itemStack);
            }
        }
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
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
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".shadow_blast_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ShadowBlastFurnaceMenu(id, inventory, this);
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

    public int getProgress()
    {
        return progress;
    }

    public int getMaxProgress()
    {
        return maxProgress;
    }
}
