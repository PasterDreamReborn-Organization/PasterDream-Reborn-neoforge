package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Collections;
import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamOreGameTests {
    @GameTest(template = "empty")
    public static void oreDropsAndToolLevels(GameTestHelper helper) {
        var stone = new ItemStack(Items.STONE_PICKAXE);
        var iron = new ItemStack(Items.IRON_PICKAXE);
        var silk = iron.copy();
        var enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        silk.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var quartz = ModBlocks.DYEDREAM_QUARTZ_ORE.get();
        var dust = ModBlocks.DYEDREAM_DUST_ORE.get();
        helper.assertTrue(quartz.defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "石英矿应使用镐");
        helper.assertTrue(dust.defaultBlockState().is(BlockTags.MINEABLE_WITH_PICKAXE), "粉尘矿应使用镐");
        helper.assertTrue(stone.isCorrectToolForDrops(dust.defaultBlockState()) == false, "粉尘矿应要求铁镐");
        helper.assertTrue(iron.isCorrectToolForDrops(dust.defaultBlockState()), "铁镐应可采集粉尘矿");
        for (var ore : List.of(quartz, dust)) {
            var state = ore.defaultBlockState();
            helper.getLevel().setBlock(pos, state, 3);
            var ordinary = Block.getDrops(state, helper.getLevel(), pos, null, null, iron);
            var product = ore == quartz ? ModItems.DYEDREAM_QUARTZ.get() : ModItems.DYEDREAM_DUST_PIECE.get();
            int maximum = ore == quartz ? 3 : 2;
            helper.assertTrue(ordinary.size() == 1 && ordinary.getFirst().is(product)
                    && ordinary.getFirst().getCount() >= 1 && ordinary.getFirst().getCount() <= maximum,
                    "普通掉落应保持来源产物与数量范围");
            var silkDrops = Block.getDrops(state, helper.getLevel(), pos, null, null, silk);
            helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(ore.asItem())
                    && silkDrops.getFirst().getCount() == 1, "精准采集应掉落矿石自身");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dustAndOreRecipes(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        var dustInput = CraftingInput.of(1, 1, List.of(new ItemStack(ModItems.DYEDREAM_DUST.get())));
        var pieces = manager.getRecipeFor(RecipeType.CRAFTING, dustInput, helper.getLevel()).orElseThrow()
                .value().assemble(dustInput, helper.getLevel().registryAccess());
        helper.assertTrue(pieces.is(ModItems.DYEDREAM_DUST_PIECE.get()) && pieces.getCount() == 9,
                "一份粉尘应拆成九份小撮粉尘");
        var pieceInput = CraftingInput.of(3, 3, Collections.nCopies(9, new ItemStack(ModItems.DYEDREAM_DUST_PIECE.get())));
        var restored = manager.getRecipeFor(RecipeType.CRAFTING, pieceInput, helper.getLevel()).orElseThrow()
                .value().assemble(pieceInput, helper.getLevel().registryAccess());
        helper.assertTrue(restored.is(ModItems.DYEDREAM_DUST.get()) && restored.getCount() == 1,
                "九份小撮粉尘应合成一份粉尘");
        for (var pair : List.of(
                List.of(ModItems.DYEDREAM_QUARTZ_ORE.get(), ModItems.DYEDREAM_QUARTZ.get()),
                List.of(ModItems.DYEDREAM_DUST_ORE.get(), ModItems.DYEDREAM_DUST_PIECE.get()))) {
            var input = new SingleRecipeInput(new ItemStack(pair.getFirst()));
            var recipe = manager.getRecipeFor(RecipeType.SMELTING, input, helper.getLevel()).orElseThrow();
            var expectedId = pair.getFirst() == ModItems.DYEDREAM_QUARTZ_ORE.get()
                    ? "dyedream_quartz_from_smelting" : "dyedream_dust_piece_from_smelting";
            helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, expectedId)),
                    "熔炼配方应保留来源 ID");
            helper.assertTrue(recipe.value().assemble(input, helper.getLevel().registryAccess()).is(pair.get(1))
                    && recipe.value().getExperience() == 1.0F && recipe.value().getCookingTime() == 200,
                    "熔炼产物、经验与耗时应与来源一致");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void quartzBuildingRecipesAndDrops(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        var quartz = new ItemStack(ModItems.DYEDREAM_QUARTZ.get());
        var baseInput = CraftingInput.of(2, 2, Collections.nCopies(4, quartz));
        var base = manager.getRecipeFor(RecipeType.CRAFTING, baseInput, helper.getLevel()).orElseThrow()
                .value().assemble(baseInput, helper.getLevel().registryAccess());
        helper.assertTrue(base.is(ModItems.DYEDREAM_QUARTZ_BLOCK.get()) && base.getCount() == 1,
                "四份染梦石英应合成一块建材");

        var block = new ItemStack(ModItems.DYEDREAM_QUARTZ_BLOCK.get());
        var stoneInput = new SingleRecipeInput(block);
        var stonecutting = manager.getAllRecipesFor(RecipeType.STONECUTTING).stream()
                .filter(recipe -> recipe.id().equals(ResourceLocation.fromNamespaceAndPath(
                        PasterDreamMod.MOD_ID, "dyedream_quartz_block_slab_from_stonecutting")))
                .findFirst().orElseThrow();
        helper.assertTrue(stonecutting.value().matches(stoneInput, helper.getLevel())
                && stonecutting.value().assemble(stoneInput, helper.getLevel().registryAccess())
                .is(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                && stonecutting.value().assemble(stoneInput, helper.getLevel().registryAccess()).getCount() == 2,
                "切石机应保留一块石英块产出两台阶的来源配方");

        var slab = ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get();
        var doubleSlab = slab.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        helper.getLevel().setBlock(pos, doubleSlab, 3);
        var drops = Block.getDrops(doubleSlab, helper.getLevel(), pos, null, null, new ItemStack(Items.IRON_PICKAXE));
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get())
                && drops.getFirst().getCount() == 2, "双层台阶应掉落两个台阶");
        helper.succeed();
    }
}
