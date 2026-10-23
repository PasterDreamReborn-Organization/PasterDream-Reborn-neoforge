package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Collections;
import java.util.List;

/** 在真实服务端注册表和数据包加载后验证首批内容。 */
@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class TitaniumGameTests {
    @GameTest(template = "empty")
    public static void miningAndDrops(GameTestHelper helper) {
        var iron = new ItemStack(Items.IRON_PICKAXE);
        var diamond = new ItemStack(Items.DIAMOND_PICKAXE);
        var silk = diamond.copy();
        silk.enchant(helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.SILK_TOUCH), 1);

        helper.assertTrue(iron.isCorrectToolForDrops(ModBlocks.TITANIUM_ORE.get().defaultBlockState()),
                "普通钛矿应允许铁镐");
        for (var block : List.of(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(),
                ModBlocks.RAW_TITANIUM_BLOCK.get(), ModBlocks.TITANIUM_BLOCK.get())) {
            helper.assertFalse(iron.isCorrectToolForDrops(block.defaultBlockState()), "深层矿和储存块应拒绝铁镐");
            helper.assertTrue(diamond.isCorrectToolForDrops(block.defaultBlockState()), "钻石镐应可采集");
        }
        helper.assertTrue(ModBlocks.TITANIUM_BLOCK.get().defaultBlockState().is(BlockTags.BEACON_BASE_BLOCKS),
                "钛金块应可作为信标基座");
        for (var ore : List.of(ModBlocks.TITANIUM_ORE.get(), ModBlocks.DEEPSLATE_TITANIUM_ORE.get())) {
            assertDrop(helper, ore, diamond, ModItems.RAW_TITANIUM.get());
            assertDrop(helper, ore, silk, ore.asItem());
            helper.assertTrue(ore.defaultBlockState().getLightEmission() == 15, "钛矿应保留亮度 15");
        }
        assertDrop(helper, ModBlocks.RAW_TITANIUM_BLOCK.get(), diamond, ModItems.RAW_TITANIUM_BLOCK.get());
        assertDrop(helper, ModBlocks.TITANIUM_BLOCK.get(), diamond, ModItems.TITANIUM_BLOCK.get());
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void storageRoundTrips(GameTestHelper helper) {
        for (var pair : List.of(
                List.of(ModItems.TITANIUM_NUGGET.get(), ModItems.TITANIUM_INGOT.get()),
                List.of(ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_BLOCK.get()),
                List.of(ModItems.RAW_TITANIUM.get(), ModItems.RAW_TITANIUM_BLOCK.get()))) {
            var source = pair.get(0);
            var packed = pair.get(1);
            var input = CraftingInput.of(3, 3, Collections.nCopies(9, new ItemStack(source)));
            var manager = helper.getLevel().getRecipeManager();
            var recipe = manager.getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow();
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(packed) && result.getCount() == 1, "9 个材料应压缩成 1 个产物");
            var reverse = CraftingInput.of(1, 1, List.of(result));
            var restored = manager.getRecipeFor(RecipeType.CRAFTING, reverse, helper.getLevel()).orElseThrow()
                    .value().assemble(reverse, helper.getLevel().registryAccess());
            helper.assertTrue(restored.is(source) && restored.getCount() == 9, "解压应还原 9 个原料");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void cookingRecipes(GameTestHelper helper) {
        for (var item : List.of(ModItems.TITANIUM_ORE.get(), ModItems.DEEPSLATE_TITANIUM_ORE.get(),
                ModItems.RAW_TITANIUM.get())) {
            var input = new SingleRecipeInput(new ItemStack(item));
            var manager = helper.getLevel().getRecipeManager();
            var smelting = manager.getRecipeFor(RecipeType.SMELTING, input, helper.getLevel()).orElseThrow().value();
            var blasting = manager.getRecipeFor(RecipeType.BLASTING, input, helper.getLevel()).orElseThrow().value();
            for (var recipe : List.of(smelting, blasting)) {
                var result = recipe.assemble(input, helper.getLevel().registryAccess());
                helper.assertTrue(result.is(ModItems.TITANIUM_INGOT.get()) && result.getCount() == 1,
                        "冶炼产物必须为 1 个钛金锭");
                helper.assertTrue(recipe.getExperience() == 1.0F, "冶炼经验必须保留为 1");
            }
            helper.assertTrue(smelting.getCookingTime() == 200 && blasting.getCookingTime() == 100,
                    "熔炉/高炉时间必须保留为 200/100 tick");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void overworldOreInjection(GameTestHelper helper) {
        var registry = helper.getLevel().registryAccess();
        var feature = registry.lookupOrThrow(Registries.PLACED_FEATURE)
                .getOrThrow(ModPlacedFeatures.DEEPSLATE_TITANIUM_ORE_PATCH).value();
        var biomes = registry.lookupOrThrow(Registries.BIOME);
        helper.assertTrue(biomes.getOrThrow(Biomes.PLAINS).value().getGenerationSettings().hasFeature(feature),
                "主世界群系必须挂入深层钛矿");
        helper.assertFalse(biomes.getOrThrow(Biomes.NETHER_WASTES).value().getGenerationSettings().hasFeature(feature),
                "下界不应挂入深层钛矿");
        helper.succeed();
    }

    private static void assertDrop(GameTestHelper helper, Block block, ItemStack tool, Item expected) {
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        helper.getLevel().setBlock(pos, block.defaultBlockState(), 3);
        var drops = Block.getDrops(block.defaultBlockState(), helper.getLevel(), pos, null, null, tool);
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(expected) && drops.getFirst().getCount() == 1,
                "错误掉落: " + block + " -> " + drops);
    }
}
