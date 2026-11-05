package com.pasterdream.pasterdreammod.compat.jei.weaponworkshopcraftingtablerecipe;

import com.pasterdream.pasterdreammod.PasterDreamMod;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WeaponWorkshopCraftingTableRecipeCategory implements IRecipeCategory<WeaponWorkshopCraftingTableJEIRecipe>
{
    public static final RecipeType<WeaponWorkshopCraftingTableJEIRecipe> WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "weapon_workshop_crafting_table"), WeaponWorkshopCraftingTableJEIRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public WeaponWorkshopCraftingTableRecipeCategory(IGuiHelper helper)
    {
        background = helper.drawableBuilder(GUIBackGroundRender.WEAPON_WORKSHOP_CRAFTING_TABLE_GUI, 0, 0, 174, 86).setTextureSize(174, 86).build();
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get()));
    }

    @Override
    public RecipeType<WeaponWorkshopCraftingTableJEIRecipe> getRecipeType()
    {
        return WEAPON_WORKSHOP_CRAFTING_TABLE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Component.translatable("block." + PasterDreamMod.MOD_ID + ".weapon_workshop_crafting_table");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public int getWidth()
    {
        return 174;
    }

    @Override
    public int getHeight()
    {
        return 86;
    }

    @Override
    public void draw(WeaponWorkshopCraftingTableJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        background.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WeaponWorkshopCraftingTableJEIRecipe recipe, IFocusGroup focuses)
    {
        int index = 0;
        for (ItemIngredient inputItemIngredient : recipe.getInputItemIngredients())
        {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 6 + 18 * index, 8);
            addIngredient(slot, inputItemIngredient);
            index++;
        }

        addIngredient(builder.addSlot(RecipeIngredientRole.INPUT, 132, 8), recipe.getEnhanceStoneItemIngredients());
        addIngredient(builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 53), recipe.getOutputItemIngredients());
    }

    private static void addIngredient(IRecipeSlotBuilder slot, ItemIngredient itemIngredient)
    {
        if (itemIngredient.getItem() != null)
        {
            slot.addItemStack(itemIngredient.getItemStack());
        }
            else
            {
                if (itemIngredient.getTag() != null)
                {
                    slot.addItemStacks(itemIngredient.getListItemStackFromTag());
                }
            }
    }
}
