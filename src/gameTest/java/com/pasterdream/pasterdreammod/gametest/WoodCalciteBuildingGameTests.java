package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WoodCalciteBuildingGameTests {
    @GameTest(template = "empty")
    public static void buildingTags(GameTestHelper helper) {
        helper.assertTrue(ModBlocks.DYEDREAM_PLANKS.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_AXE), "染梦木板应使用斧子");
        helper.assertTrue(ModBlocks.DYEDREAM_PLANKS.get().defaultBlockState().is(BlockTags.PLANKS), "染梦木板应属于木板标签");
        helper.assertTrue(ModBlocks.DYEDREAM_STAIRS.get().defaultBlockState().is(BlockTags.WOODEN_STAIRS), "染梦木楼梯应属于木楼梯标签");
        helper.assertTrue(ModBlocks.DYEDREAM_SLAB.get().defaultBlockState().is(BlockTags.WOODEN_SLABS), "染梦木台阶应属于木台阶标签");
        helper.assertTrue(ModBlocks.DYEDREAM_FENCE.get().defaultBlockState().is(BlockTags.WOODEN_FENCES), "染梦木栅栏应属于木栅栏标签");
        helper.assertTrue(ModBlocks.DYEDREAM_FENCE_GATE.get().defaultBlockState().is(BlockTags.FENCE_GATES), "染梦木栅栏门应属于栅栏门标签");
        helper.assertTrue(ModBlocks.DYEDREAM_DOOR.get().defaultBlockState().is(BlockTags.WOODEN_DOORS), "染梦木门应属于木门标签");
        helper.assertTrue(ModBlocks.DYEDREAM_TRAPDOOR.get().defaultBlockState().is(BlockTags.WOODEN_TRAPDOORS), "染梦木活板门应属于木活板门标签");
        helper.assertTrue(ModBlocks.DYEDREAM_PRESSURE_PLATE.get().defaultBlockState().is(BlockTags.WOODEN_PRESSURE_PLATES), "染梦木压力板应属于木压力板标签");
        helper.assertTrue(ModBlocks.DYEDREAM_BUTTON.get().defaultBlockState().is(BlockTags.WOODEN_BUTTONS), "染梦木按钮应属于木按钮标签");
        helper.assertTrue(ModBlocks.DYEDREAM_PANE.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_AXE), "染梦木窗格应使用斧子");
        helper.assertTrue(ModBlocks.POLISHED_CALCITE.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "磨制方解石应使用镐");
        helper.assertTrue(ModBlocks.CALCITE_TILES.get().defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "方解石瓦应使用镐");
        helper.assertTrue(ModBlocks.CALCITE_TILES_STAIRS.get().defaultBlockState().is(BlockTags.STAIRS), "方解石瓦楼梯应属于楼梯标签");
        helper.assertTrue(ModBlocks.CALCITE_TILES_SLAB.get().defaultBlockState().is(BlockTags.SLABS), "方解石瓦台阶应属于台阶标签");
        helper.assertTrue(ModBlocks.CALCITE_TILES_WALL.get().defaultBlockState().is(BlockTags.WALLS), "方解石瓦墙应属于墙标签");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void buildingRecipesAndDrops(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();

        var logInput = CraftingInput.of(1, 1, List.of(new ItemStack(ModItems.DYEDREAM_LOG.get())));
        var planks = manager.getRecipeFor(RecipeType.CRAFTING, logInput, helper.getLevel()).orElseThrow()
                .value().assemble(logInput, helper.getLevel().registryAccess());
        helper.assertTrue(planks.is(ModItems.DYEDREAM_PLANKS.get()) && planks.getCount() == 4, "一份染梦原木应合成四份染梦木板");

        var polishedInput = new SingleRecipeInput(new ItemStack(ModItems.POLISHED_CALCITE.get()));
        var stairsId = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "polished_calcite_stairs_from_stonecutting");
        var stairs = manager.getAllRecipesFor(RecipeType.STONECUTTING).stream()
                .filter(recipe -> recipe.id().equals(stairsId))
                .findFirst().orElseThrow();
        helper.assertTrue(stairs.value().matches(polishedInput, helper.getLevel())
                        && stairs.value().assemble(polishedInput, helper.getLevel().registryAccess())
                        .is(ModItems.POLISHED_CALCITE_STAIRS.get()),
                "切石机应由磨制方解石产出磨制方解石楼梯");

        var slabId = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "polished_calcite_slab_from_stonecutting");
        var slabRecipe = manager.getAllRecipesFor(RecipeType.STONECUTTING).stream()
                .filter(recipe -> recipe.id().equals(slabId))
                .findFirst().orElseThrow();
        helper.assertTrue(slabRecipe.value().assemble(polishedInput, helper.getLevel().registryAccess()).getCount() == 2,
                "切石机应由一块磨制方解石产出两块台阶");

        var slab = ModBlocks.DYEDREAM_SLAB.get();
        var doubleSlab = slab.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        helper.getLevel().setBlock(pos, doubleSlab, 3);
        var slabDrops = Block.getDrops(doubleSlab, helper.getLevel(), pos, null, null, new ItemStack(Items.IRON_AXE));
        helper.assertTrue(slabDrops.size() == 1 && slabDrops.getFirst().is(ModItems.DYEDREAM_SLAB.get())
                && slabDrops.getFirst().getCount() == 2, "染梦木双层台阶应掉落两个台阶");

        var door = ModBlocks.DYEDREAM_DOOR.get();
        helper.getLevel().setBlock(pos, door.defaultBlockState(), 3);
        var doorDrops = Block.getDrops(door.defaultBlockState(), helper.getLevel(), pos, null, null, new ItemStack(Items.IRON_AXE));
        helper.assertTrue(doorDrops.size() == 1 && doorDrops.getFirst().is(ModItems.DYEDREAM_DOOR.get()), "染梦木门应掉落自身");
        helper.succeed();
    }
}
