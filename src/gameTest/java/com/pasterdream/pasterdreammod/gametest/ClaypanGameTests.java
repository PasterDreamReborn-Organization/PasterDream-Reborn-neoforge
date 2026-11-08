package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeProcesser;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanBlockEntity;
import com.pasterdream.pasterdreammod.world.block.claypan.ClaypanRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ClaypanGameTests
{
    @GameTest(template = "empty")
    public static void recipesLoadAndCraft(GameTestHelper helper)
    {
        var recipes = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.CLAYPAN.get());
        helper.assertTrue(recipes.size() == 3, "陶盆来源配方应加载 3 条，实际 " + recipes.size());

        var holder = helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "claypan/coarse_salt_from_water")).orElseThrow();
        ClaypanRecipe recipe = (ClaypanRecipe) holder.value();
        helper.assertTrue(recipe.getRecipeTime() == 6000, "粗盐晾晒时间应为 6000 tick");
        helper.assertTrue(recipe.getInputFluidIngredients().get(0).getFluid() == Fluids.WATER
                && recipe.getInputFluidIngredients().get(0).getAmount() == 125, "粗盐配方应消耗 125 mB 水");
        helper.assertTrue(recipe.getOutputItemIngredients().get(0).getItem() == ModItems.COARSE_SALT.get(),
                "粗盐配方产物不符");

        GenericRecipeInventory matched = GenericRecipeMatcher.match(List.of(),
                List.of(new FluidStack(Fluids.WATER, 125)), List.of(recipe));
        helper.assertTrue(matched != null, "陶盆应匹配 125 mB 水");
        GenericRecipeInventory processed = GenericRecipeProcesser.processing(matched,
                new GenericRecipeInventory(List.of(), List.of(new FluidStack(Fluids.WATER, 125)),
                        List.of(ItemStack.EMPTY), List.of(), 0, 0));
        helper.assertTrue(processed != null
                && processed.inputFluidStacks().get(0).isEmpty()
                && processed.outputItemStacks().get(0).is(ModItems.COARSE_SALT.get()),
                "陶盆加工应消耗水并产出粗盐");
        GenericRecipeInventory matchedTooLittle = GenericRecipeMatcher.match(List.of(),
                List.of(new FluidStack(Fluids.WATER, 124)), List.of(recipe));
        helper.assertTrue(matchedTooLittle != null && GenericRecipeProcesser.processing(matchedTooLittle,
                new GenericRecipeInventory(List.of(), List.of(new FluidStack(Fluids.WATER, 124)),
                        List.of(ItemStack.EMPTY), List.of(), 0, 0)) == null,
                "水量不足 125 mB 时不得完成加工");

        CraftingInput input = CraftingInput.of(3, 2, List.of(
                new ItemStack(Items.TERRACOTTA), new ItemStack(Items.WHITE_DYE), new ItemStack(Items.TERRACOTTA),
                new ItemStack(Items.TERRACOTTA), new ItemStack(Items.TERRACOTTA), new ItemStack(Items.TERRACOTTA)));
        var crafting = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel());
        helper.assertTrue(crafting.isPresent() && crafting.get().id().equals(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "claypan")), "陶盆工作台配方 ID 不符");
        ItemStack result = crafting.get().value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(ModItems.CLAYPAN.get()) && result.getCount() == 2,
                "陶盆工作台配方应产出 2 个陶盆");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blockEntityMatchesAndPersists(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.CLAYPAN.get());
        ClaypanBlockEntity claypan = helper.getBlockEntity(pos);

        helper.assertTrue(claypan.getFluidTank(0).getCapacity() == 1000, "陶盆流体容量应保留 1000 mB");
        helper.assertTrue(claypan.getItemHandler().insertItem(0, new ItemStack(Items.SUGAR), false)
                .is(Items.SUGAR), "陶盆输出槽不得接受外部插入");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.FluidHandler.BLOCK,
                helper.absolutePos(pos), null) != null, "陶盆应暴露流体接口");

        claypan.getFluidTank(0).setFluid(new FluidStack(Fluids.WATER, 125));
        claypan.tick();
        helper.assertTrue(claypan.getMaxProgress() == 6000 && claypan.getProgress() == 1
                && claypan.getFluidTank(0).getFluid().isEmpty(),
                "陶盆匹配后应立即扣除流体并开始计时");
        helper.assertTrue(claypan.getItemHandler().getStackInSlot(0).isEmpty(),
                "陶盆产物应在计时结束后才出现");

        var saved = claypan.saveWithFullMetadata(helper.getLevel().registryAccess());
        ClaypanBlockEntity restored = new ClaypanBlockEntity(helper.absolutePos(pos), helper.getBlockState(pos));
        restored.loadWithComponents(saved, helper.getLevel().registryAccess());
        helper.assertTrue(restored.getMaxProgress() == 6000 && restored.getProgress() == 1
                && restored.getFluidTank(0).getFluid().isEmpty(), "陶盆重载后应保留计时进度");
        helper.succeed();
    }
}
