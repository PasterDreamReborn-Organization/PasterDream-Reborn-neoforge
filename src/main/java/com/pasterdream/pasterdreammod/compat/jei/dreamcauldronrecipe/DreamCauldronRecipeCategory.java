package com.pasterdream.pasterdreammod.compat.jei.dreamcauldronrecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModItems;
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

public class DreamCauldronRecipeCategory implements IRecipeCategory<DreamCauldronJEIRecipe>
{
    public static final RecipeType<DreamCauldronJEIRecipe> DREAM_CAULDRON_RECIPE_TYPE = new RecipeType<>(
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_cauldron"), DreamCauldronJEIRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public DreamCauldronRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.DREAM_CAULDRON_GUI, 0, 0, 171, 93)
                .setTextureSize(171, 93).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.DREAM_CAULDRON.get()));
    }

    @Override
    public RecipeType<DreamCauldronJEIRecipe> getRecipeType()
    {
        return DREAM_CAULDRON_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block.pasterdream.dream_cauldron");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 171;
    }

    @Override
    public int getHeight()
    {
        return 93;
    }

    @Override
    public void draw(DreamCauldronJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DreamCauldronJEIRecipe recipe, IFocusGroup focuses)
    {
        int fluidIndex = 0;
        for (FluidIngredient ingredient : recipe.getInputFluidIngredients())
        {
            addFluid(builder.addSlot(RecipeIngredientRole.INPUT,
                    fluidIndex == 0 ? 154 : 1, fluidIndex == 0 ? 19 : 46), ingredient);
            fluidIndex++;
        }
        fluidIndex = 0;
        for (FluidIngredient ingredient : recipe.getOutputFluidIngredients())
        {
            addFluid(builder.addSlot(RecipeIngredientRole.OUTPUT, 86 + fluidIndex * 18, 46), ingredient);
            fluidIndex++;
        }
        int index = 0;
        for (ItemIngredient ingredient : recipe.getInputItemIngredients())
        {
            addIngredient(builder.addSlot(RecipeIngredientRole.INPUT, 45 + index * 28, 15), ingredient);
            index++;
        }
        index = 0;
        for (ItemIngredient ingredient : recipe.getOutputItemIngredients())
        {
            addIngredient(builder.addSlot(RecipeIngredientRole.OUTPUT, 60 + index * 18, 46), ingredient);
            index++;
        }
        if (!recipe.getDisplayBottle().isEmpty())
            builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 46).addItemStack(recipe.getDisplayBottle());
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
