package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.FluidIngredient;
import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.ResearchTableCopyRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.ResearchTableCopyRecipeInventoryWithOutputSlot;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.ResearchTableCopyRecipeProcessor;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.ResearchTableResearchRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.researchtableinventory.ResearchTableResearchRecipeProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ResearchTableGameTests
{
    @GameTest(template = "empty")
    public static void copyRecipeProcesses(GameTestHelper helper)
    {
        CompoundTag penTag = new CompoundTag();
        penTag.putInt("Damage", 1);
        ItemStack requirePen = new ItemStack(ModItems.FEATHER_PEN.get());
        ItemIngredient.setCustomDataTag(requirePen, penTag);
        ItemIngredient sourceBook = ItemIngredient.of(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get(), 1);
        ItemIngredient material = ItemIngredient.of(ModItems.PERGAMYN.get(), 1);

        FluidStack ink = new FluidStack(ModFluids.INK.get(), 25);
        ResearchTableCopyRecipeInventory require = new ResearchTableCopyRecipeInventory(ink, requirePen, sourceBook.getItemStack(), material.getItemStack());

        ItemStack actualPen = new ItemStack(ModItems.FEATHER_PEN.get());
        ItemStack actualBook = new ItemStack(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get());
        ResearchTableCopyRecipeInventoryWithOutputSlot machine = new ResearchTableCopyRecipeInventoryWithOutputSlot(
                ink.copy(), actualPen, actualBook, new ItemStack(ModItems.PERGAMYN.get()), ItemStack.EMPTY);

        ResearchTableCopyRecipeInventoryWithOutputSlot result = ResearchTableCopyRecipeProcessor.processing(require, machine, helper.getLevel().getRandom());
        helper.assertTrue(result != null, "研究台复制配方应可处理");
        helper.assertTrue(result.output().is(ModItems.DREAM_NOTES_LAMP_SHADOW_WORLD.get()), "复制产物应为寻梦者笔记");
        helper.assertTrue(result.output().getCount() == 2, "复制产物数量应为 2");
        helper.assertTrue(result.pen().getDamageValue() == 1, "羽毛笔应消耗 1 点耐久");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void researchRecipeProcesses(GameTestHelper helper)
    {
        CompoundTag penTag = new CompoundTag();
        penTag.putInt("Damage", 1);
        ItemStack requirePen = new ItemStack(ModItems.FEATHER_PEN.get());
        ItemIngredient.setCustomDataTag(requirePen, penTag);
        ItemIngredient unresolvedNote = ItemIngredient.of(ModItems.LAMP_SHADOW_UNKNOWN_NOTE.get(), 1);
        ItemIngredient output = ItemIngredient.of(ModItems.LAMP_SHADOW_STORY_GUIDE.get(), 1);

        FluidStack ink = new FluidStack(ModFluids.INK.get(), 25);
        ResearchTableResearchRecipeInventory require = new ResearchTableResearchRecipeInventory(ink, requirePen, unresolvedNote.getItemStack(), output.getItemStack());
        ResearchTableResearchRecipeInventory machine = new ResearchTableResearchRecipeInventory(
                ink.copy(), new ItemStack(ModItems.FEATHER_PEN.get()),
                new ItemStack(ModItems.LAMP_SHADOW_UNKNOWN_NOTE.get()), ItemStack.EMPTY);

        ResearchTableResearchRecipeInventory result = ResearchTableResearchRecipeProcessor.processing(require, machine, helper.getLevel().getRandom());
        helper.assertTrue(result != null, "研究台解析配方应可处理");
        helper.assertTrue(result.output().is(ModItems.LAMP_SHADOW_STORY_GUIDE.get()), "解析产物应为灯影之下已解析的笔记");
        helper.assertTrue(result.pen().getDamageValue() == 1, "羽毛笔应消耗 1 点耐久");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void researchRecipesLoad(GameTestHelper helper)
    {
        var manager = helper.getLevel().getRecipeManager();
        helper.assertTrue(manager.getAllRecipesFor(ModRecipes.RESEARCH_TABLE_COPY.get()).size() == 4,
                "研究台复制配方应加载 4 条");
        helper.assertTrue(manager.getAllRecipesFor(ModRecipes.RESEARCH_TABLE_RESEARCH.get()).size() == 2,
                "研究台解析配方应加载 2 条");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void lampShadowLootTablesLoad(GameTestHelper helper)
    {
        var server = helper.getLevel().getServer();
        for (ResourceKey<LootTable> key : java.util.List.of(
                ModLootTables.SHADOW_RELIC_CHEST_LOOT_COMMON,
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_COMMON,
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_CORE,
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_SUPPLY))
        {
            LootTable table = server.reloadableRegistries().getLootTable(key);
            helper.assertTrue(table != LootTable.EMPTY, "灯影之下战利品表应可加载: " + key.location());
        }
        helper.succeed();
    }
}
