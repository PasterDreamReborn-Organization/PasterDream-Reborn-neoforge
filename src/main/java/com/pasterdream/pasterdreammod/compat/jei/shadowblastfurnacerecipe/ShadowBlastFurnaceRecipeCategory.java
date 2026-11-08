package com.pasterdream.pasterdreammod.compat.jei.shadowblastfurnacerecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ShadowBlastFurnaceRecipeCategory implements IRecipeCategory<ShadowBlastFurnaceJEIRecipe>
{
    public static final RecipeType<ShadowBlastFurnaceJEIRecipe> SHADOW_BLAST_FURNACE_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_blast_furnace"), ShadowBlastFurnaceJEIRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public ShadowBlastFurnaceRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.SHADOW_BLAST_FURNACE_GUI, 0, 0, 126, 103).setTextureSize(126, 103).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SHADOW_BLAST_FURNACE.get()));
    }

    @Override
    public RecipeType<ShadowBlastFurnaceJEIRecipe> getRecipeType()
    {
        return SHADOW_BLAST_FURNACE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".shadow_blast_furnace");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 126;
    }

    @Override
    public int getHeight()
    {
        return 111;
    }

    @Override
    public void draw(ShadowBlastFurnaceJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        int time = recipe.getProcessingTime();
        background.draw(guiGraphics);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.format("%02d", (time / 72000)) + "h" + String.format("%02d", ((time % 72000) / 1200)) + "m" + String.format("%02d", ((time % 1200) / 20)) + "s" + String.format("%02d", (time % 20)) + "tick", 63, 104, 0xFFFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShadowBlastFurnaceJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder inputFluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 109, 5);
        if (!recipe.getInputFluidIngredients().isEmpty())
        {
            addFluid(inputFluidSlot, recipe.getInputFluidIngredients().get(0));
        }
        else
        {
            inputFluidSlot.setFluidRenderer(1000, false, 16, 16);
        }

        int index = 0;
        for (ItemIngredient inputItemIngredient : recipe.getInputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 1, 5 + index * 45);
            addIngredient(slot, inputItemIngredient);
            index++;
        }

        index = 0;
        for (ItemIngredient outputItemIngredient : recipe.getOutputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.OUTPUT, 37 + (index * 36), 86);
            addIngredient(slot, outputItemIngredient);
            index++;
        }
    }

    private static void addFluid(IRecipeSlotBuilder slot, FluidIngredient ingredient)
    {
        if (ingredient.getFluid() != null)
        {
            FluidStack fluid = ingredient.getFluidStack();
            slot.addFluidStack(fluid.getFluid(), fluid.getAmount(), fluid.getComponentsPatch());
        }
        else if (ingredient.getTag() != null)
        {
            for (FluidStack fluid : ingredient.getListFluidStackFromTag())
                slot.addFluidStack(fluid.getFluid(), fluid.getAmount(), fluid.getComponentsPatch());
        }
        slot.setFluidRenderer(1000, false, 16, 16);
    }

    private static void addIngredient(IRecipeSlotBuilder slot, ItemIngredient ingredient)
    {
        if (ingredient.getItem() != null)
        {
            slot.addItemStack(ingredient.getItemStack());
        }
        else if (ingredient.getTag() != null)
        {
            slot.addItemStacks(ingredient.getListItemStackFromTag());
        }
    }
}
