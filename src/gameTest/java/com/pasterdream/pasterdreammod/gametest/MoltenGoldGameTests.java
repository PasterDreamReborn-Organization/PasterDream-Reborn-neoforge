package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/** 验证炙焰金的生存获取链路和来源行为。 */
@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MoltenGoldGameTests {
    @GameTest(template = "empty")
    public static void miningAndDrops(GameTestHelper helper) {
        var iron = new ItemStack(Items.IRON_PICKAXE);
        var stone = new ItemStack(Items.STONE_PICKAXE);
        var silk = iron.copy();
        var fortune = iron.copy();
        var enchantments = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        silk.enchant(enchantments.getOrThrow(Enchantments.SILK_TOUCH), 1);
        fortune.enchant(enchantments.getOrThrow(Enchantments.FORTUNE), 3);
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        for (var block : List.of(ModBlocks.MOLTEN_GOLD_ORE.get(), ModBlocks.MOLTEN_GOLD_BLOCK.get())) {
            var state = block.defaultBlockState();
            helper.assertTrue(iron.isCorrectToolForDrops(state), "炙焰金应允许铁镐采集");
            helper.assertFalse(stone.isCorrectToolForDrops(state), "炙焰金应拒绝石镐采集");
            helper.assertTrue(state.getLightEmission() == 0, "炙焰金矿石/块不应自行发光");
            helper.getLevel().setBlock(pos, state, 3);
            var ordinary = Block.getDrops(state, helper.getLevel(), pos, null, null, iron);
            var expected = block == ModBlocks.MOLTEN_GOLD_ORE.get() ? ModItems.RAW_MOLTEN_GOLD.get() : block.asItem();
            helper.assertTrue(ordinary.size() == 1 && ordinary.getFirst().is(expected)
                    && ordinary.getFirst().getCount() == 1, "普通采集应掉落一个粗矿/储存块");
            var silkDrops = Block.getDrops(state, helper.getLevel(), pos, null, null, silk);
            helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(block.asItem())
                    && silkDrops.getFirst().getCount() == 1, "精准采集应掉落一个方块自身");
        }
        helper.assertTrue(ModBlocks.MOLTEN_GOLD_BLOCK.get().defaultBlockState().is(BlockTags.BEACON_BASE_BLOCKS),
                "炙焰金块应可作为信标基座");
        var ore = ModBlocks.MOLTEN_GOLD_ORE.get();
        var table = helper.getLevel().getServer().reloadableRegistries().getLootTable(ore.getLootTable());
        var params = new LootParams.Builder(helper.getLevel())
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.BLOCK_STATE, ore.defaultBlockState())
                .withParameter(LootContextParams.TOOL, fortune).create(LootContextParamSets.BLOCK);
        int total = 0;
        // 固定种子覆盖时运随机分支，避免依赖测试世界的随机状态。
        for (long seed = 1; seed <= 64; seed++) {
            var drops = table.getRandomItems(params, seed);
            helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.RAW_MOLTEN_GOLD.get()),
                    "时运应仍掉落粗炙焰金");
            int count = drops.getFirst().getCount();
            helper.assertTrue(count >= 1 && count <= 4, "时运 III 每次应掉落 1～4 个粗矿");
            total += count;
        }
        helper.assertTrue(total > 64, "时运应实际增加粗矿掉落");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void storageRoundTrips(GameTestHelper helper) {
        for (var pair : List.of(
                List.of(ModItems.MOLTEN_GOLD_NUGGET.get(), ModItems.MOLTEN_GOLD_INGOT.get()),
                List.of(ModItems.MOLTEN_GOLD_INGOT.get(), ModItems.MOLTEN_GOLD_BLOCK.get()))) {
            var input = CraftingInput.of(3, 3, Collections.nCopies(9, new ItemStack(pair.getFirst())));
            var manager = helper.getLevel().getRecipeManager();
            var packed = manager.getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel()).orElseThrow()
                    .value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(packed.is(pair.get(1)) && packed.getCount() == 1, "9 个材料应压缩为 1 个产物");
            var reverse = CraftingInput.of(1, 1, List.of(packed));
            var restored = manager.getRecipeFor(RecipeType.CRAFTING, reverse, helper.getLevel()).orElseThrow()
                    .value().assemble(reverse, helper.getLevel().registryAccess());
            helper.assertTrue(restored.is(pair.getFirst()) && restored.getCount() == 9, "解压应还原 9 个材料");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void cookingRecipes(GameTestHelper helper) {
        for (var item : List.of(ModItems.MOLTEN_GOLD_ORE.get(), ModItems.RAW_MOLTEN_GOLD.get())) {
            var input = new SingleRecipeInput(new ItemStack(item));
            var manager = helper.getLevel().getRecipeManager();
            var smelting = manager.getRecipeFor(RecipeType.SMELTING, input, helper.getLevel()).orElseThrow();
            var blasting = manager.getRecipeFor(RecipeType.BLASTING, input, helper.getLevel()).orElseThrow();
            String prefix = item == ModItems.MOLTEN_GOLD_ORE.get()
                    ? "molten_gold_ingot_from_molten_gold_ore_" : "moltengold_ingot_from_";
            helper.assertTrue(smelting.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, prefix + "smelting"))
                    && blasting.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, prefix + "blasting")),
                    "冶炼配方必须保留来源 ID，包括 moltengold 历史拼写");
            for (var recipe : List.of(smelting.value(), blasting.value())) {
                var result = recipe.assemble(input, helper.getLevel().registryAccess());
                helper.assertTrue(result.is(ModItems.MOLTEN_GOLD_INGOT.get()) && result.getCount() == 1,
                        "矿石/粗矿应冶炼为 1 个炙焰金锭");
                helper.assertTrue(recipe.getExperience() == 1.0F, "冶炼经验应为 1");
            }
            helper.assertTrue(smelting.value().getCookingTime() == 200 && blasting.value().getCookingTime() == 100,
                    "熔炉/高炉应保留 200/100 tick 耗时");
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void moltenGoldIngotIsFuel(GameTestHelper helper) {
        var stack = new ItemStack(ModItems.MOLTEN_GOLD_INGOT.get());
        helper.assertTrue(stack.getBurnTime(RecipeType.SMELTING) == 6400, "炙焰金锭在熔炉中应作为燃料燃烧 6400 tick");
        helper.assertTrue(stack.getBurnTime(null) == 6400, "炙焰金锭在熔炉与高炉中燃烧时间一致");
        helper.assertTrue(stack.has(DataComponents.FIRE_RESISTANT), "炙焰金锭应保留防火属性");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void netherOreGeneration(GameTestHelper helper) {
        var level = helper.getLevel();
        var registry = level.registryAccess();
        var feature = registry.lookupOrThrow(Registries.PLACED_FEATURE)
                .getOrThrow(ModPlacedFeatures.MOLTEN_GOLD_ORE_PATCH).value();
        registry.lookupOrThrow(Registries.BIOME).listElements().forEach(biome -> {
            var generation = biome.value().getGenerationSettings();
            boolean nether = biome.is(BiomeTags.IS_NETHER);
            helper.assertTrue(generation.hasFeature(feature) == nether, "矿脉必须且仅注入下界群系: " + biome.key());
            if (nether) {
                helper.assertTrue(generation.features().get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal())
                        .stream().anyMatch(holder -> holder.value() == feature), "必须保留来源生成阶段");
            }
        });
        var config = (OreConfiguration) feature.feature().value().config();
        helper.assertTrue(config.size == 16 && config.discardChanceOnAirExposure == 0f, "矿脉大小/暴露丢弃率应为 16/0");
        helper.assertTrue(config.targetStates.size() == 1, "只能有下界岩一种替换规则");
        var target = config.targetStates.getFirst();
        var random = RandomSource.create(37L);
        helper.assertTrue(target.state.is(ModBlocks.MOLTEN_GOLD_ORE.get()), "地物产物应为炙焰金矿石");
        helper.assertTrue(target.target.test(Blocks.NETHERRACK.defaultBlockState(), random), "矿脉应替换下界岩");
        for (var excluded : List.of(Blocks.STONE, Blocks.BASALT, Blocks.BLACKSTONE, Blocks.AIR)) {
            helper.assertFalse(target.target.test(excluded.defaultBlockState(), random), "矿脉不应替换 " + excluded);
        }
        var context = new PlacementContext(level, level.getChunkSource().getGenerator(), Optional.empty());
        for (int sample = 0; sample < 32; sample++) {
            Stream<BlockPos> positions = Stream.of(BlockPos.ZERO);
            for (var modifier : feature.placement()) {
                positions = positions.flatMap(pos -> modifier.getPositions(context, random, pos));
            }
            var placed = positions.toList();
            helper.assertTrue(placed.size() == 4, "每区块应尝试生成 4 次");
            helper.assertTrue(placed.stream().allMatch(pos -> pos.getY() >= 0 && pos.getY() <= 64
                    && pos.getX() >= 0 && pos.getX() < 16 && pos.getZ() >= 0 && pos.getZ() < 16),
                    "矿脉放置应限制在区块内的 Y=0～64");
        }
        helper.succeed();
    }
}
