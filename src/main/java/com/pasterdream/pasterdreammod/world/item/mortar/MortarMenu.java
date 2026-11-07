package com.pasterdream.pasterdreammod.world.item.mortar;

import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.AbstractContainerMenuWithFluidSlot;
import com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot.FluidSlot;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeProcesser;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class MortarMenu extends AbstractContainerMenuWithFluidSlot
{
    private final MortarInventory mortarInventory;
    private final Inventory playerInventory;
    private final ItemStack thisMortarItem;
    private final InteractionHand interactionHand;
    private final int lockedSlotIndex;

    public MortarMenu(int id, Inventory playerInventory, ItemStack thisMortarItem, InteractionHand interactionHand)
    {
        super(ModMenus.MORTAR.get(), id);
        this.playerInventory = playerInventory;
        this.thisMortarItem = thisMortarItem;
        mortarInventory = new MortarInventory(new MortarDataHandler(this.thisMortarItem));
        this.interactionHand = interactionHand;
        bindFluidViewer(playerInventory.player);

        for (int i = 0; i < 6; i++)
        {
            addFluidSlot(new FluidSlot(mortarInventory, i, 26 + (i % 2) * 18,
                    i < 4 ? 5 + (i / 2) * 18 : 67));
        }

        if (interactionHand == InteractionHand.MAIN_HAND)
        {   //快捷栏槽位在容器中的起始索引是12
            lockedSlotIndex = 12 + playerInventory.selected;
        }
            else
            {
                lockedSlotIndex = -1;
            }

        for (int row = 0; row < 2; row++)
        {
            for (int col = 0; col < 4; col++)
            {
                int index = row * 4 + col;
                addSlot(new SlotItemHandler(mortarInventory, index, 73 + col * 18, 6 + row * 18));
            }
        }

        for (int col = 0; col < 4; col++)
        {
            int index = 8 + col;
            addSlot(new SlotItemHandler(mortarInventory, index, 73 + col * 18, 68)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return false;
                }
            });
        }

        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 5 + col * 18, 159));
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 5 + col * 18, 101 + row * 18));
            }
        }
    }

    public MortarMenu(int id, Inventory playerInventory, RegistryFriendlyByteBuf data)
    {
        this(id, playerInventory, data.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
    }

    private MortarMenu(int id, Inventory playerInventory, InteractionHand hand)
    {
        this(id, playerInventory, playerInventory.player.getItemInHand(hand), hand);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        if (index < 0 || index >= slots.size() || index == lockedSlotIndex)
        {
            return ItemStack.EMPTY;
        }
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < 12)
        {
            if (!moveItemStackTo(stack, 12, 48, true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (!moveItemStackTo(stack, 0, 8, false))
        {
            return ItemStack.EMPTY;
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
        return player.getItemInHand(interactionHand) == thisMortarItem;
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType clickType, Player player)
    {
        if (slotIndex == lockedSlotIndex || !stillValid(player)
                || (clickType == ClickType.SWAP && interactionHand == InteractionHand.MAIN_HAND && button == playerInventory.selected)
                || (clickType == ClickType.SWAP && interactionHand == InteractionHand.OFF_HAND && button == 40))
        {
            return;
        }
        super.clicked(slotIndex, button, clickType, player);
    }

    @Override
    public void removed(Player player)
    {
        mortarInventory.save();
        super.removed(player);
    }

    public void craft()
    {
        if (playerInventory.player.level().isClientSide || !stillValid(playerInventory.player)) return;

        List<RecipeHolder<MortarRecipe>> holders = playerInventory.player.level().getRecipeManager()
                .getAllRecipesFor(ModRecipes.MORTAR.get());
        List<MortarRecipe> recipes = new ArrayList<>(holders.size());
        for (RecipeHolder<MortarRecipe> holder : holders) recipes.add(holder.value());

        List<ItemStack> inputs = new ArrayList<>(8);
        List<ItemStack> outputs = new ArrayList<>(4);
        List<FluidStack> fluidInputs = new ArrayList<>(4);
        List<FluidStack> fluidOutputs = new ArrayList<>(2);
        for (int i = 0; i < 8; i++) inputs.add(mortarInventory.getStackInSlot(i).copy());
        for (int i = 8; i < 12; i++) outputs.add(mortarInventory.getStackInSlot(i).copy());
        for (int i = 0; i < 4; i++) fluidInputs.add(mortarInventory.getFluid(i).copy());
        for (int i = 4; i < 6; i++) fluidOutputs.add(mortarInventory.getFluid(i).copy());

        GenericRecipeInventory required = GenericRecipeMatcher.match(inputs, fluidInputs, recipes);
        if (required == null) return;
        GenericRecipeInventory current = new GenericRecipeInventory(inputs, fluidInputs, outputs, fluidOutputs, 0, 1000);
        GenericRecipeInventory result = GenericRecipeProcesser.processing(required, current);
        if (result == null) return;

        for (int i = 0; i < 8; i++) mortarInventory.setStackInSlot(i, result.inputItemStacks().get(i));
        for (int i = 0; i < 4; i++) mortarInventory.setStackInSlot(i + 8, result.outputItemStacks().get(i));
        for (int i = 0; i < 4; i++) mortarInventory.setFluid(i, result.inputFluidStacks().get(i));
        for (int i = 0; i < 2; i++) mortarInventory.setFluid(i + 4, result.outputFluidStacks().get(i));
        broadcastChanges();
    }
}
