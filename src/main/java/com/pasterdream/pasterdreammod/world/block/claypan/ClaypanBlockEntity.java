package com.pasterdream.pasterdreammod.world.block.claypan;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeProcesser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClaypanBlockEntity extends BlockEntity implements MenuProvider
{
    private static final int FLUID_CAPACITY = 1000;

    public ClaypanBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CLAYPAN.get(), pos, state);
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return false;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return stack;
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChangedAndSync();
        }
    };

    private final FluidTank[] fluidTanks =
    {
        new FluidTank(FLUID_CAPACITY)
        {
            @Override
            protected void onContentsChanged()
            {
                setChangedAndSync();
            }
        }
    };

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

    private int progress = 0;
    private int maxProgress = 0;
    private FluidStack recipeRequiredFluid;
    private ItemStack currentRecipeOutput = ItemStack.EMPTY;

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

        List<ClaypanRecipe> recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.CLAYPAN.get())
                .stream().map(RecipeHolder::value).toList();

        List<ItemStack> outputItems = new ArrayList<>(1);
        outputItems.add(itemHandler.getStackInSlot(0).copy());

        List<FluidStack> inputFluids = new ArrayList<>(1);
        inputFluids.add(fluidTanks[0].getFluid().copy());

        GenericRecipeInventory matchedResult = GenericRecipeMatcher.match(List.of(), inputFluids, recipes);
        if(matchedResult != null)
        {
            GenericRecipeInventory processedResult = GenericRecipeProcesser.processing(matchedResult, new GenericRecipeInventory(List.of(), inputFluids, outputItems, List.of(), matchedResult.recipeTime(), 0));
            if(processedResult != null)
            {
                fluidTanks[0].setFluid(processedResult.inputFluidStacks().get(0));
                currentRecipeOutput = processedResult.outputItemStacks().get(0);
                maxProgress = matchedResult.recipeTime();

                //同步
                setChangedAndSync();
            }
        }
    }

    private void generateProduct()
    {
        itemHandler.setStackInSlot(0, currentRecipeOutput);
        progress = 0;
        maxProgress = 0;
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
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        fluidTanks[0].readFromNBT(registries, tag.getCompound("FluidTank"));
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
        if (tag.contains("RecipeRequiredFluid"))
        {
            recipeRequiredFluid = FluidStack.parseOptional(registries, tag.getCompound("RecipeRequiredFluid"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.put("FluidTank", fluidTanks[0].writeToNBT(registries, new CompoundTag()));
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
        if (recipeRequiredFluid != null && !recipeRequiredFluid.isEmpty())
        {
            tag.put("RecipeRequiredFluid", recipeRequiredFluid.save(registries, new CompoundTag()));
        }
    }

    //菜单
    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".claypan");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ClaypanMenu(id, inventory, this);
    }

    public IFluidHandler getFluidHandler(int tankIndex)
    {
        return fluidTanks[tankIndex];
    }

    public IItemHandler getItemHandler()
    {
        return itemHandler;
    }

    public int getProgress()
    {
        return progress;
    }

    public int getMaxProgress()
    {
        return maxProgress;
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
