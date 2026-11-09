package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.dreamaccumulatorrecipe.DreamAccumulatorRecipeProcessor;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorBlockEntity;
import com.pasterdream.pasterdreammod.world.block.dreamaccumulator.DreamAccumulatorRecipe;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DreamAccumulatorGameTests
{
    @GameTest(template = "empty")
    public static void recipeLoadsAndCrafts(GameTestHelper helper)
    {
        var holder = helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "dream_accumulator/dyedream_dust_piece")).orElseThrow();
        helper.assertTrue(holder.value() instanceof DreamAccumulatorRecipe, "蓄梦池配方应使用专属配方类");
        DreamAccumulatorRecipe recipe = (DreamAccumulatorRecipe) holder.value();
        helper.assertTrue(recipe.getType() == ModRecipes.DREAM_ACCUMULATOR.get(), "蓄梦池配方类型不符");
        helper.assertTrue(recipe.getTime() == 6000, "蓄梦池配方的蓄梦时间应为 6000 tick");
        helper.assertTrue(recipe.getCatalyst().getItem() == ModItems.SORBENT.get(), "蓄梦池配方催化剂应为吸附剂");
        helper.assertTrue(recipe.getCatalystMultiplier() == 10.0D, "吸附剂倍率应为 10");
        helper.assertTrue(recipe.getOutput().is(ModItems.DYEDREAM_DUST_PIECE.get()), "蓄梦池配方产物应为染梦粉尘碎片");
        helper.assertTrue(recipe.getBiomeTags().size() == 1
                && recipe.getBiomeTags().get(0).location().equals(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "dyedream_world_biome")), "蓄梦池配方应限定染梦群系标签");

        CraftingInput input = CraftingInput.of(3, 3, List.of(
                ItemStack.EMPTY, new ItemStack(ModItems.DYEDREAM_GLASS.get()), ItemStack.EMPTY,
                new ItemStack(ModItems.POLISHED_CALCITE.get()), new ItemStack(ModItems.TITANIUM_INGOT.get(), 1), new ItemStack(ModItems.POLISHED_CALCITE.get()),
                new ItemStack(ModItems.POLISHED_CALCITE.get()), new ItemStack(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get()), new ItemStack(ModItems.POLISHED_CALCITE.get())));
        var crafting = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel());
        helper.assertTrue(crafting.isPresent() && crafting.get().id().equals(ResourceLocation.fromNamespaceAndPath(
                PasterDreamMod.MOD_ID, "dream_accumulator")), "蓄梦池工作台配方 ID 不符");
        ItemStack result = crafting.get().value().assemble(input, helper.getLevel().registryAccess());
        helper.assertTrue(result.is(ModItems.DREAM_ACCUMULATOR.get()) && result.getCount() == 1,
                "蓄梦池工作台配方应产出 1 个蓄梦池");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blockEntitySlotsAndCapability(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_ACCUMULATOR.get());
        DreamAccumulatorBlockEntity accumulator = helper.getBlockEntity(pos);
        helper.assertTrue(accumulator != null, "蓄梦池应创建方块实体");
        helper.assertTrue(accumulator.getItemHandler().getSlots() == 2, "蓄梦池应保留两个物品槽");
        helper.assertTrue(!accumulator.getItemHandler().isItemValid(0, new ItemStack(Items.STONE)),
                "蓄梦池产物槽不得接受外部插入");
        helper.assertTrue(accumulator.getItemHandler().isItemValid(1, new ItemStack(ModItems.SORBENT.get())),
                "蓄梦池催化剂槽应接受吸附剂");
        helper.assertTrue(accumulator.getExternalHandler().extractItem(1, 1, true).isEmpty(),
                "蓄梦池外部接口不得抽取催化剂槽");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,
                helper.absolutePos(pos), null) != null, "蓄梦池应暴露内部物品接口");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,
                helper.absolutePos(pos), Direction.UP) != null, "蓄梦池应暴露外部物品接口");

        helper.assertTrue(DreamAccumulatorRecipeProcessor.processor(
                        new ItemStack(ModItems.DYEDREAM_DUST_PIECE.get(), 1),
                        new ItemStack(ModItems.DYEDREAM_DUST_PIECE.get(), 2)).getCount() == 3,
                "相同产物应合并计数");
        helper.assertTrue(DreamAccumulatorRecipeProcessor.processor(
                        new ItemStack(ModItems.DYEDREAM_DUST_PIECE.get(), 1),
                        new ItemStack(Items.STONE)) == null,
                "不同产物占用槽位时不得开始蓄梦");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void outsideRecipeBiomeDoesNotProduce(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_ACCUMULATOR.get());
        DreamAccumulatorBlockEntity accumulator = helper.getBlockEntity(pos);
        ItemStack sorbent = new ItemStack(ModItems.SORBENT.get());
        accumulator.getItemHandler().setStackInSlot(1, sorbent);
        helper.assertTrue(!helper.getLevel().getBiome(helper.absolutePos(pos)).is(ModBiomeTags.DYEDREAM_WORLD_BIOME),
                "测试方块必须位于配方不允许的群系");

        for (int i = 0; i < 10; i++)
        {
            accumulator.tick();
        }

        helper.assertTrue(accumulator.getMaxProgress() == 0 && accumulator.getProgress() == 0,
                "群系不符合配方时不得开始蓄梦");
        helper.assertTrue(accumulator.getItemHandler().getStackInSlot(0).isEmpty(),
                "群系不符合配方时不得产出");
        helper.assertTrue(sorbent.getDamageValue() == 0,
                "群系不符合配方时不得消耗吸附剂耐久");

        CompoundTag saved = accumulator.getUpdateTag(helper.getLevel().registryAccess());
        saved.putDouble("Progress", 5999);
        saved.putInt("MaxProgress", 6000);
        saved.put("Output", new ItemStack(ModItems.DYEDREAM_DUST_PIECE.get()).save(
                helper.getLevel().registryAccess(), new CompoundTag()));
        accumulator.handleUpdateTag(saved, helper.getLevel().registryAccess());
        accumulator.tick();
        helper.assertTrue(accumulator.getMaxProgress() == 0 && accumulator.getProgress() == 0
                        && accumulator.getItemHandler().getStackInSlot(0).isEmpty(),
                "已缓存的蓄梦进度不得在不符合配方的群系中完成产出");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void catalystDurabilityConsumedInRecipeBiome(GameTestHelper helper)
    {
        BlockPos pos = new BlockPos(1, 1, 1);
        BlockPos worldPos = helper.absolutePos(pos);
        var level = helper.getLevel();
        var originalBiome = level.getBiome(worldPos);
        var dyedreamBiome = level.registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(ModBiomes.DYEDREAM_PLAINS);
        var section = level.getChunkAt(worldPos).getSection(level.getSectionIndex(worldPos.getY()));
        section.fillBiomesFromNoise((x, y, z, sampler) -> dyedreamBiome,
                level.getChunkSource().randomState().sampler(), 0, 0, 0);

        helper.setBlock(pos, ModBlocks.DREAM_ACCUMULATOR.get());
        DreamAccumulatorBlockEntity accumulator = helper.getBlockEntity(pos);
        ItemStack sorbent = new ItemStack(ModItems.SORBENT.get());
        accumulator.getItemHandler().setStackInSlot(1, sorbent);
        for (int i = 0; i < 10; i++)
        {
            accumulator.tick();
        }

        helper.assertTrue(accumulator.getMaxProgress() == 6000, "染梦群系中蓄梦池应匹配正式配方");
        helper.assertTrue(sorbent.getDamageValue() == 10,
                "吸附剂每 tick 应消耗 1 点耐久，实际 " + sorbent.getDamageValue());
        section.fillBiomesFromNoise((x, y, z, sampler) -> originalBiome,
                level.getChunkSource().randomState().sampler(), 0, 0, 0);
        helper.succeed();
    }
}
