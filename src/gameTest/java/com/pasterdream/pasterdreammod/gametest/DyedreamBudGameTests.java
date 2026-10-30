package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Collections;
import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamBudGameTests {
    @GameTest(template = "empty")
    public static void budTagsAndDrops(GameTestHelper helper) {
        var iron = new ItemStack(Items.IRON_PICKAXE);
        var silk = iron.copy();
        var enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        silk.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));

        for (var block : List.of(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), ModBlocks.DYEDREAM_BUD_BLOCK.get(),
                ModBlocks.SMALL_DYEDREAM_BUD.get(), ModBlocks.MEDIUM_DYEDREAM_BUD.get(),
                ModBlocks.LARGE_DYEDREAM_BUD.get())) {
            helper.assertTrue(block.defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "晶芽方块应使用镐");
            helper.assertTrue(block.defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL), "晶芽方块应要求铁镐");
        }
        helper.assertTrue(ModBlocks.DYEDREAM_ALLOY_BLOCK.get().defaultBlockState().is(BlockTags.NEEDS_DIAMOND_TOOL),
                "染梦合金块应要求钻石镐");

        var budding = ModBlocks.DYEDREAM_BUDDING_BLOCK.get();
        helper.getLevel().setBlock(pos, budding.defaultBlockState(), 3);
        var buddingDrops = Block.getDrops(budding.defaultBlockState(), helper.getLevel(), pos, null, null, iron);
        helper.assertTrue(buddingDrops.isEmpty(), "染梦母岩无精准采集时不应掉落");
        var buddingSilk = Block.getDrops(budding.defaultBlockState(), helper.getLevel(), pos, null, null, silk);
        helper.assertTrue(buddingSilk.size() == 1 && buddingSilk.getFirst().is(budding.asItem()),
                "染梦母岩精准采集应掉落自身");

        for (var entry : List.of(
                new Object[]{ModBlocks.SMALL_DYEDREAM_BUD.get(), 0, 2},
                new Object[]{ModBlocks.MEDIUM_DYEDREAM_BUD.get(), 1, 3},
                new Object[]{ModBlocks.LARGE_DYEDREAM_BUD.get(), 2, 4})) {
            var block = (Block) entry[0];
            var minimum = (Integer) entry[1];
            var maximum = (Integer) entry[2];
            helper.getLevel().setBlock(pos, block.defaultBlockState(), 3);
            var drops = Block.getDrops(block.defaultBlockState(), helper.getLevel(), pos, null, null, iron);
            var withinRange = drops.size() == 1 && drops.getFirst().is(ModItems.DYEDREAM_BUD_NUGGET.get())
                    && drops.getFirst().getCount() >= minimum && drops.getFirst().getCount() <= maximum;
            var emptyRoll = minimum == 0 && (drops.isEmpty() || drops.size() == 1 && drops.getFirst().isEmpty());
            helper.assertTrue(withinRange || emptyRoll,
                    "晶芽普通掉落数量应与来源一致: " + drops);
            var silkDrops = Block.getDrops(block.defaultBlockState(), helper.getLevel(), pos, null, null, silk);
            helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(block.asItem()),
                    "晶芽精准采集应掉落自身");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void buddingGrowth(GameTestHelper helper) {
        var level = helper.getLevel();
        var random = RandomSource.create(42L);
        var origin = helper.absolutePos(new BlockPos(1, 1, 1));
        var budding = ModBlocks.DYEDREAM_BUDDING_BLOCK.get();
        level.setBlock(origin, budding.defaultBlockState(), 3);

        for (int i = 0; i < 2000; i++) {
            budding.defaultBlockState().randomTick(level, origin, random);
        }

        for (var direction : Direction.values()) {
            var state = level.getBlockState(origin.relative(direction));
            helper.assertTrue(state.is(ModBlocks.LARGE_DYEDREAM_BUD.get()), "染梦母岩应逐级生长出大型晶芽");
            helper.assertTrue(state.getValue(AmethystClusterBlock.FACING) == direction, "晶芽朝向应背离母岩");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void budAndAlloyRecipes(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        var nugget = new ItemStack(ModItems.DYEDREAM_BUD_NUGGET.get());
        var budInput = CraftingInput.of(2, 2, Collections.nCopies(4, nugget));
        var budBlock = manager.getRecipeFor(RecipeType.CRAFTING, budInput, helper.getLevel()).orElseThrow()
                .value().assemble(budInput, helper.getLevel().registryAccess());
        helper.assertTrue(budBlock.is(ModBlocks.DYEDREAM_BUD_BLOCK.get().asItem()) && budBlock.getCount() == 1,
                "四份染梦晶芽粒应合成染梦水晶块");

        var blockInput = CraftingInput.of(1, 1, List.of(new ItemStack(ModBlocks.DYEDREAM_BUD_BLOCK.get())));
        var restored = manager.getRecipeFor(RecipeType.CRAFTING, blockInput, helper.getLevel()).orElseThrow()
                .value().assemble(blockInput, helper.getLevel().registryAccess());
        helper.assertTrue(restored.is(ModItems.DYEDREAM_BUD_NUGGET.get()) && restored.getCount() == 4,
                "染梦水晶块应拆回四份染梦晶芽粒");

        var ingot = new ItemStack(ModItems.DYEDREAM_ALLOY_INGOT.get());
        var alloyInput = CraftingInput.of(3, 3, Collections.nCopies(9, ingot));
        var alloyBlock = manager.getRecipeFor(RecipeType.CRAFTING, alloyInput, helper.getLevel()).orElseThrow()
                .value().assemble(alloyInput, helper.getLevel().registryAccess());
        helper.assertTrue(alloyBlock.is(ModBlocks.DYEDREAM_ALLOY_BLOCK.get().asItem()) && alloyBlock.getCount() == 1,
                "九份染梦合金锭应合成染梦合金块");

        var alloyBlockInput = CraftingInput.of(1, 1, List.of(new ItemStack(ModBlocks.DYEDREAM_ALLOY_BLOCK.get())));
        var ingots = manager.getRecipeFor(RecipeType.CRAFTING, alloyBlockInput, helper.getLevel()).orElseThrow()
                .value().assemble(alloyBlockInput, helper.getLevel().registryAccess());
        helper.assertTrue(ingots.is(ModItems.DYEDREAM_ALLOY_INGOT.get()) && ingots.getCount() == 9,
                "染梦合金块应拆回九份染梦合金锭");
        helper.succeed();
    }
}
