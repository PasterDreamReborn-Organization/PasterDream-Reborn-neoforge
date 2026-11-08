package com.pasterdream.pasterdreammod.compat.jei.claypanrecipe;

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

public class ClaypanRecipeCategory implements IRecipeCategory<ClaypanJEIRecipe>
{
    public static final RecipeType<ClaypanJEIRecipe> CLAYPAN_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "claypan"), ClaypanJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/claypan.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ClaypanRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.CLAYPAN_GUI, 0, 0, 82, 28).setTextureSize(82, 28).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CLAYPAN.get()));
    }

    @Override
    public RecipeType<ClaypanJEIRecipe> getRecipeType()
    {
        return CLAYPAN_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".claypan");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 82;
    }

    @Override
    public int getHeight()
    {
        return 36;
    }

    @Override
    public void draw(ClaypanJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        int time = recipe.getProcessingTime();
        background.draw(guiGraphics);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, String.format("%02d", (time / 72000)) + "h" + String.format("%02d", ((time % 72000) / 1200)) + "m" + String.format("%02d", ((time % 1200) / 20)) + "s" + String.format("%02d", (time % 20)) + "tick", 41, 29, 0xFFFFFFFF);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ClaypanJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder inputFluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 6, 6);
        if (!recipe.getInputFluidIngredients().isEmpty())
        {
            addFluid(inputFluidSlot, recipe.getInputFluidIngredients().get(0));
        }

        if (!recipe.getOutputItemIngredients().isEmpty())
        {
            addIngredient(builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 6), recipe.getOutputItemIngredients().get(0));
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
