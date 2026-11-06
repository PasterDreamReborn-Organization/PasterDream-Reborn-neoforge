package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamTerrainGameTests {
    @GameTest(template = "empty")
    public static void terrainTagsAndDrops(GameTestHelper helper) {
        helper.assertTrue(ModBlocks.DYEDREAM_DIRT.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_SHOVEL), "染梦泥土应使用铲子");
        helper.assertTrue(ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_SHOVEL), "染梦草方块应使用铲子");
        helper.assertTrue(ModBlocks.DYEDREAM_FARMLAND.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_SHOVEL), "染梦耕地应使用铲子");
        helper.assertTrue(ModBlocks.DYEDREAM_SAND.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_SHOVEL), "染梦沙应使用铲子");
        helper.assertTrue(ModBlocks.DYEDREAM_SAND.get().defaultBlockState().is(BlockTags.SAND), "染梦沙应属于沙子标签");
        helper.assertTrue(ModBlocks.DYEDREAM_LOG.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_AXE), "染梦原木应使用斧子");
        helper.assertTrue(ModBlocks.DYEDREAM_WOOD.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_AXE), "染梦木头应使用斧子");
        helper.assertTrue(ModBlocks.DYEDREAM_LOG.get().defaultBlockState().is(BlockTags.LOGS), "染梦原木应属于原木标签");
        helper.assertTrue(ModBlocks.DYEDREAM_LEAVES.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_HOE), "染梦树叶应使用锄头");
        helper.assertTrue(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get().defaultBlockState().is(BlockTags.LEAVES), "染梦世界树树叶应属于树叶标签");

        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var silk = new ItemStack(Items.IRON_SHOVEL);
        silk.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        var plain = new ItemStack(Items.IRON_SHOVEL);

        var grass = ModBlocks.DYEDREAM_GRASS_BLOCK.get();
        level.setBlock(pos, grass.defaultBlockState(), 3);
        var silkDrops = Block.getDrops(grass.defaultBlockState(), level, pos, null, null, silk);
        helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(grass.asItem()), "染梦草方块精准采集应掉落自身");
        var dirtDrops = Block.getDrops(grass.defaultBlockState(), level, pos, null, null, plain);
        helper.assertTrue(dirtDrops.size() == 1 && dirtDrops.getFirst().is(ModBlocks.DYEDREAM_DIRT.get().asItem()),
                "染梦草方块普通破坏应掉落染梦泥土");

        var farmland = ModBlocks.DYEDREAM_FARMLAND.get();
        var farmlandDrops = Block.getDrops(farmland.defaultBlockState(), level, pos, null, null, plain);
        helper.assertTrue(farmlandDrops.size() == 1 && farmlandDrops.getFirst().is(ModBlocks.DYEDREAM_DIRT.get().asItem()),
                "染梦耕地破坏应掉落染梦泥土");

        var leaves = ModBlocks.DYEDREAM_LEAVES.get();
        var leavesDrops = Block.getDrops(leaves.defaultBlockState(), level, pos, null, null, silk);
        helper.assertTrue(leavesDrops.size() == 1 && leavesDrops.getFirst().is(leaves.asItem()), "染梦树叶精准采集应掉落自身");

        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void hoeTillsDyedreamSoil(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var context = new UseOnContext(level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY,
                new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));

        var dirtResult = ModBlocks.DYEDREAM_DIRT.get().defaultBlockState()
                .getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        helper.assertTrue(dirtResult != null && dirtResult.is(ModBlocks.DYEDREAM_FARMLAND.get()),
                "锄头应把染梦泥土变为染梦耕地");

        var grassResult = ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState()
                .getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        helper.assertTrue(grassResult != null && grassResult.is(ModBlocks.DYEDREAM_FARMLAND.get()),
                "锄头应把染梦草方块变为染梦耕地");

        helper.succeed();
    }
}
