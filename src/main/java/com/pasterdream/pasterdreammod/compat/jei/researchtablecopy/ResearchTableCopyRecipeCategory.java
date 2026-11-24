package com.pasterdream.pasterdreammod.compat.jei.researchtablecopy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ResearchTableCopyRecipeCategory implements IRecipeCategory<ResearchTableCopyJEIRecipe>
{
    public static final RecipeType<ResearchTableCopyJEIRecipe> RESEARCH_TABLE_COPY_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "research_table_copy"), ResearchTableCopyJEIRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/research_table.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ResearchTableCopyRecipeCategory(IGuiHelper guiHelper)
    {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 197, 115).setTextureSize(197, 115).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.RESEARCH_TABLE.get()));
    }

    @Override
    public RecipeType<ResearchTableCopyJEIRecipe> getRecipeType()
    {
        return RESEARCH_TABLE_COPY_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("recipe." + PasterDreamMod.MOD_ID + ".copy");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ResearchTableCopyJEIRecipe recipe, IFocusGroup focuses)
    {
        IRecipeSlotBuilder fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 170, 17).setFluidRenderer(250, false, 16, 16);
        FluidIngredient fluidIngredient = recipe.getInputFluidIngredients().get(0);
        if (fluidIngredient.getFluid() != null)
        {
            addFluid(fluidSlot, recipe.getInputFluidIngredients().get(0));
        }
        else
            if (fluidIngredient.getTag() != null)
            {
                fluidSlot.setFluidRenderer(1000, false, 16, 16);
            }

        IRecipeSlotBuilder penSlot = builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 170, 80);
        ItemIngredient penItemIngredient = recipe.getInputItemIngredients().get(0);
        addIngredient(penSlot, penItemIngredient);

        IRecipeSlotBuilder sourceBookSlot = builder.addSlot(RecipeIngredientRole.INPUT, 35, 26);
        ItemIngredient sourceBookItemIngredient = recipe.getInputItemIngredients().get(1);
        addIngredient(sourceBookSlot, sourceBookItemIngredient);

        IRecipeSlotBuilder materialItemSlot = builder.addSlot(RecipeIngredientRole.INPUT, 35, 44);
        ItemIngredient materialItemIngredient = recipe.getInputItemIngredients().get(2);
        addIngredient(materialItemSlot, materialItemIngredient);

        IRecipeSlotBuilder outputItemSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 35);
        ItemIngredient outputItemIngredient = recipe.getOutputItemIngredients().get(0);
        addIngredient(outputItemSlot, outputItemIngredient);
    }

    @Override
    public void draw(ResearchTableCopyJEIRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth()
    {
        return 197;
    }

    @Override
    public int getHeight()
    {
        return 115;
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
