package com.pasterdream.pasterdreammod.compat.jei.mortarrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe>
{
    public static final RecipeType<MortarRecipe> MORTAR_RECIPE_TYPE = new RecipeType<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "mortar"), MortarRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.MORTAR_GUI, 0, 0, 128, 90)
                .setTextureSize(128, 90).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.MORTAR.get()));
    }

    @Override
    public RecipeType<MortarRecipe> getRecipeType()
    {
        return MORTAR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("gui.pasterdream.mortar");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 128;
    }

    @Override
    public int getHeight()
    {
        return 90;
    }

    @Override
    public void draw(MortarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MortarRecipe recipe, IFocusGroup focuses)
    {
        int fluidIndex = 0;
        for (FluidIngredient ingredient : recipe.getInputFluidIngredients())
        {
            addFluid(builder.addSlot(RecipeIngredientRole.INPUT, 5 + (fluidIndex % 2) * 18,
                    5 + (fluidIndex / 2) * 18), ingredient);
            fluidIndex++;
        }
        fluidIndex = 0;
        for (FluidIngredient ingredient : recipe.getOutputFluidIngredients())
        {
            addFluid(builder.addSlot(RecipeIngredientRole.OUTPUT, 5 + fluidIndex * 18, 67), ingredient);
            fluidIndex++;
        }
        int index = 0;
        for (ItemIngredient ingredient : recipe.getInputItemIngredients())
        {
            addIngredient(builder.addSlot(RecipeIngredientRole.INPUT, 52 + (index % 4) * 18,
                    6 + (index / 4) * 18), ingredient);
            index++;
        }
        index = 0;
        for (ItemIngredient ingredient : recipe.getOutputItemIngredients())
        {
            addIngredient(builder.addSlot(RecipeIngredientRole.OUTPUT, 52 + index * 18, 68), ingredient);
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
