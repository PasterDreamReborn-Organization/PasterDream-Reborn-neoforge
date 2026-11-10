package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeInventory;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeMatcher;
import com.pasterdream.pasterdreammod.recipe.genericrecipe.recipematchandprocess.GenericRecipeProcesser;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlockEntity;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowBlastFurnaceGameTests
{
    @GameTest(template = "empty")
    public static void recipesLoadAndCraft(GameTestHelper helper)
    {
        var recipes = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.SHADOW_BLAST_FURNACE.get());
        helper.assertTrue(recipes.size() == 7, "暗影高炉来源配方应加载 7 条，实际 " + recipes.size());

        var holder = helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "shadow_blast_furnace/shadow_stone")).orElseThrow();
        ShadowBlastFurnaceRecipe recipe = (ShadowBlastFurnaceRecipe) holder.value();
        helper.assertTrue(recipe.getRecipeTime() == 100, "暗影石加工时间应为 100 tick");
        helper.assertTrue(recipe.getInputFluidIngredients().get(0).getFluid() == ModFluids.SHADOW_LIQUID.get()
                && recipe.getInputFluidIngredients().get(0).getAmount() == 125, "暗影石配方应消耗 125 mB 暗影流体");
        helper.assertTrue(recipe.getOutputItemIngredients().get(0).getItem() == ModItems.SHADOW_STONE.get(),
                "暗影石配方产物不符");

        GenericRecipeInventory matched = GenericRecipeMatcher.match(List.of(new ItemStack(Items.STONE)),
                List.of(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 125)), List.of(recipe));
        helper.assertTrue(matched != null, "暗影高炉应匹配石头与 125 mB 暗影流体");
        GenericRecipeInventory processed = GenericRecipeProcesser.processing(matched,
                new GenericRecipeInventory(List.of(new ItemStack(Items.STONE)), List.of(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 125)),
                        List.of(ItemStack.EMPTY, ItemStack.EMPTY), List.of(), 0, 0));
        helper.assertTrue(processed != null
                && processed.inputItemStacks().get(0).isEmpty()
                && processed.inputFluidStacks().get(0).isEmpty()
                && processed.outputItemStacks().get(0).is(ModItems.SHADOW_STONE.get()),
                "暗影高炉加工应消耗石头与暗影流体并产出暗影石");

        GenericRecipeInventory matchedTooLittle = GenericRecipeMatcher.match(List.of(new ItemStack(Items.STONE)),
                List.of(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 124)), List.of(recipe));
        helper.assertFalse(matchedTooLittle != null && GenericRecipeProcesser.processing(matchedTooLittle,
                new GenericRecipeInventory(List.of(new ItemStack(Items.STONE)), List.of(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 124)),
                        List.of(ItemStack.EMPTY, ItemStack.EMPTY), List.of(), 0, 0)) != null,
                "暗影流体不足 125 mB 时不得完成加工");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blockEntityTicksAndPersists(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SHADOW_BLAST_FURNACE.get());
        ShadowBlastFurnaceBlockEntity furnace = helper.getBlockEntity(pos);

        helper.assertTrue(furnace.getFluidTank(0).getCapacity() == 9000, "暗影高炉流体容量应保留 9000 mB");
        helper.assertTrue(furnace.getItemHandler().insertItem(2, new ItemStack(Items.DIAMOND), false)
                .is(Items.DIAMOND), "暗影高炉输出槽不得接受外部插入");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,
                helper.absolutePos(pos), null) != null, "暗影高炉应暴露物品接口");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.FluidHandler.BLOCK,
                helper.absolutePos(pos), null) != null, "暗影高炉应暴露流体接口");

        furnace.getFluidTank(0).setFluid(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 125));
        furnace.getItemHandler().setStackInSlot(0, new ItemStack(Items.STONE));
        furnace.tick();
        helper.assertTrue(furnace.getMaxProgress() == 100 && furnace.getProgress() == 1
                && furnace.getFluidTank(0).getFluid().isEmpty()
                && furnace.getItemHandler().getStackInSlot(0).isEmpty(),
                "暗影高炉匹配后应立即扣料并开始计时");
        helper.assertTrue(furnace.getItemHandler().getStackInSlot(2).isEmpty(),
                "暗影高炉产物应在计时结束后才出现");

        for (int i = 0; i < 99; i++)
        {
            furnace.tick();
        }
        helper.assertTrue(furnace.getProgress() == 0 && furnace.getMaxProgress() == 0
                && furnace.getItemHandler().getStackInSlot(2).is(ModItems.SHADOW_STONE.get()),
                "暗影高炉应在计时结束后产出暗影石");

        furnace.getFluidTank(0).setFluid(new FluidStack(ModFluids.SHADOW_LIQUID.get(), 125));
        furnace.getItemHandler().setStackInSlot(0, new ItemStack(Items.STONE));
        furnace.tick();
        var saved = furnace.saveWithFullMetadata(helper.getLevel().registryAccess());
        ShadowBlastFurnaceBlockEntity restored = new ShadowBlastFurnaceBlockEntity(helper.absolutePos(pos), helper.getBlockState(pos));
        restored.loadWithComponents(saved, helper.getLevel().registryAccess());
        helper.assertTrue(restored.getMaxProgress() == 100 && restored.getProgress() == 1
                && restored.getFluidTank(0).getFluid().isEmpty()
                && restored.getItemHandler().getStackInSlot(0).isEmpty(),
                "暗影高炉重载后应保留计时进度");
        helper.succeed();
    }
}
