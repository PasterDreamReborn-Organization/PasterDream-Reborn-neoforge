package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.CalciteRedstoneOreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class CalciteOreGameTests {
    @GameTest(template = "empty")
    public static void oreTagsAndRedstoneState(GameTestHelper helper) {
        helper.assertTrue(ModBlocks.CALCITE_COAL_ORE.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "方解石煤矿石应使用镐");
        helper.assertTrue(ModBlocks.CALCITE_COAL_ORE.get().defaultBlockState().is(BlockTags.NEEDS_STONE_TOOL), "方解石煤矿石应需要石制工具");
        helper.assertTrue(ModBlocks.CALCITE_IRON_ORE.get().defaultBlockState().is(BlockTags.NEEDS_STONE_TOOL), "方解石铁矿石应需要石制工具");
        helper.assertTrue(ModBlocks.CALCITE_COPPER_ORE.get().defaultBlockState().is(BlockTags.NEEDS_STONE_TOOL), "方解石铜矿石应需要石制工具");
        helper.assertTrue(ModBlocks.CALCITE_LAPIS_ORE.get().defaultBlockState().is(BlockTags.NEEDS_STONE_TOOL), "方解石青金石矿石应需要石制工具");
        helper.assertTrue(ModBlocks.CALCITE_GOLD_ORE.get().defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL), "方解石金矿石应需要铁制工具");
        helper.assertTrue(ModBlocks.CALCITE_REDSTONE_ORE.get().defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL), "方解石红石矿石应需要铁制工具");
        helper.assertTrue(ModBlocks.CALCITE_DIAMOND_ORE.get().defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL), "方解石钻石矿石应需要铁制工具");
        helper.assertTrue(ModBlocks.CALCITE_EMERALD_ORE.get().defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL), "方解石绿宝石矿石应需要铁制工具");
        helper.assertTrue(ModBlocks.CALCITE_COAL_ORE.get().defaultBlockState().is(Tags.Blocks.ORES_COAL), "方解石煤矿石应属于煤矿石标签");
        helper.assertTrue(ModBlocks.CALCITE_DIAMOND_ORE.get().defaultBlockState().is(Tags.Blocks.ORES_DIAMOND), "方解石钻石矿石应属于钻石矿石标签");

        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var redstoneOre = ModBlocks.CALCITE_REDSTONE_ORE.get();
        level.setBlock(pos, redstoneOre.defaultBlockState(), 3);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        level.getBlockState(pos).useItemOn(ItemStack.EMPTY, level, player, InteractionHand.MAIN_HAND, hit);
        var litState = level.getBlockState(pos);
        helper.assertTrue(litState.getValue(CalciteRedstoneOreBlock.LIT), "右键方解石红石矿石应点亮");
        helper.assertTrue(litState.isRandomlyTicking(), "点亮的方解石红石矿石应接收随机刻");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void oreDropsAndCooking(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var pickaxe = new ItemStack(Items.IRON_PICKAXE);

        var coalOre = ModBlocks.CALCITE_COAL_ORE.get();
        var coalDrops = Block.getDrops(coalOre.defaultBlockState(), level, pos, null, null, pickaxe);
        helper.assertTrue(coalDrops.size() == 1 && coalDrops.getFirst().is(Items.COAL), "方解石煤矿石应掉落煤炭");

        var copperOre = ModBlocks.CALCITE_COPPER_ORE.get();
        var copperDrops = Block.getDrops(copperOre.defaultBlockState(), level, pos, null, null, pickaxe);
        helper.assertTrue(copperDrops.size() == 1 && copperDrops.getFirst().is(Items.RAW_COPPER)
                && copperDrops.getFirst().getCount() >= 2 && copperDrops.getFirst().getCount() <= 5, "方解石铜矿石应掉落 2~5 个粗铜");

        var diamondOre = ModBlocks.CALCITE_DIAMOND_ORE.get();
        var diamondDrops = Block.getDrops(diamondOre.defaultBlockState(), level, pos, null, null, pickaxe);
        helper.assertTrue(diamondDrops.size() == 1 && diamondDrops.getFirst().is(Items.DIAMOND), "方解石钻石矿石应掉落钻石");

        var coalInput = new SingleRecipeInput(new ItemStack(coalOre.asItem()));
        var smelting = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, coalInput, level).orElseThrow();
        var result = smelting.value().assemble(coalInput, level.registryAccess());
        helper.assertTrue(result.is(Items.COAL) && result.getCount() == 1, "方解石煤矿石应可在熔炉中烧成煤炭");

        var diamondInput = new SingleRecipeInput(new ItemStack(diamondOre.asItem()));
        var blasting = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, diamondInput, level).orElseThrow();
        var blasted = blasting.value().assemble(diamondInput, level.registryAccess());
        helper.assertTrue(blasted.is(Items.DIAMOND) && blasted.getCount() == 1, "方解石钻石矿石应可在高炉中烧成钻石");
        helper.succeed();
    }
}
