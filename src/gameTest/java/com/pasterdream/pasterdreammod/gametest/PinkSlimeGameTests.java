package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Collections;
import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class PinkSlimeGameTests {
    @GameTest(template = "empty")
    public static void compressionAndFalling(GameTestHelper helper) {
        var block = ModBlocks.PINK_SLIME_BLOCK.get();
        helper.assertTrue(block instanceof FallingBlock, "粉色粘液块应继承重力方块行为");

        var manager = helper.getLevel().getRecipeManager();
        var balls = CraftingInput.of(3, 3, Collections.nCopies(9, new ItemStack(ModItems.PINK_SLIMEBALL.get())));
        var compressed = manager.getRecipeFor(RecipeType.CRAFTING, balls, helper.getLevel()).orElseThrow();
        helper.assertTrue(compressed.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "pink_slime_block_from_pink_slimeball")), "压缩配方 ID 应与来源一致");
        helper.assertTrue(compressed.value().assemble(balls, helper.getLevel().registryAccess()).is(ModItems.PINK_SLIME_BLOCK.get()),
                "九个粉色粘液球应产出一块粉色粘液块");

        var blockInput = CraftingInput.of(1, 1, List.of(new ItemStack(ModItems.PINK_SLIME_BLOCK.get())));
        var decompressed = manager.getRecipeFor(RecipeType.CRAFTING, blockInput, helper.getLevel()).orElseThrow();
        var restored = decompressed.value().assemble(blockInput, helper.getLevel().registryAccess());
        helper.assertTrue(decompressed.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "pink_slimeball_from_pink_slime_block"))
                && restored.is(ModItems.PINK_SLIMEBALL.get()) && restored.getCount() == 9,
                "粉色粘液块应拆回九个粉色粘液球");

        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        helper.getLevel().setBlock(pos, block.defaultBlockState(), 3);
        var drops = Block.getDrops(block.defaultBlockState(), helper.getLevel(), pos, null, null, ItemStack.EMPTY);
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.PINK_SLIME_BLOCK.get()),
                "破坏粉色粘液块应掉落自身");

        helper.setBlock(new BlockPos(1, 0, 1), Blocks.STONE);
        helper.setBlock(new BlockPos(1, 1, 1), Blocks.AIR);
        helper.setBlock(new BlockPos(1, 3, 1), block);
        helper.runAfterDelay(30, () -> {
            helper.assertBlockPresent(block, new BlockPos(1, 1, 1));
            helper.succeed();
        });
    }
}
