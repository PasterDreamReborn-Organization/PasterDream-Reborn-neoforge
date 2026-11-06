package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBiomeTags;
import com.pasterdream.pasterdreammod.worldgen.ModPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
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
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class SoulOreGameTests {
    @GameTest(template = "empty")
    public static void soulOreDropsAndRecipes(GameTestHelper helper) {
        var level = helper.getLevel();
        var ore = ModBlocks.SOUL_ORE.get();
        var state = ore.defaultBlockState();
        helper.assertTrue(state.is(BlockTags.MINEABLE_WITH_SHOVEL), "灵魂矿土应可用铲挖掘");
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        level.setBlock(pos, state, 3);
        var ordinary = Block.getDrops(state, level, pos, null, null, new ItemStack(Items.IRON_SHOVEL));
        helper.assertTrue(ordinary.size() == 1 && ordinary.getFirst().is(ModItems.SOUL_DUST.get())
                && ordinary.getFirst().getCount() >= 1 && ordinary.getFirst().getCount() <= 2,
                "普通采集应掉落 1～2 灵魂粉尘");
        var hand = Block.getDrops(state, level, pos, null, null, ItemStack.EMPTY);
        helper.assertTrue(hand.size() == 1 && hand.getFirst().is(ModItems.SOUL_DUST.get()),
                "徒手挖掘应掉落灵魂粉尘");
        var fortune = new ItemStack(Items.IRON_SHOVEL);
        fortune.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 3);
        int extra = 0;
        for (int i = 0; i < 64; i++) {
            var drops = Block.getDrops(state, level, pos, null, null, fortune);
            helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.SOUL_DUST.get()),
                    "时运采集应继续掉落灵魂粉尘");
            extra = Math.max(extra, drops.getFirst().getCount());
        }
        helper.assertTrue(extra > 2, "时运 III 应有机会超过普通采集上限");
        var silk = new ItemStack(Items.IRON_SHOVEL);
        silk.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
        var silkDrops = Block.getDrops(state, level, pos, null, null, silk);
        helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(ModItems.SOUL_ORE.get()),
                "精准采集应获得灵魂矿土");

        var manager = level.getRecipeManager();
        var cooking = new SingleRecipeInput(new ItemStack(ModItems.SOUL_ORE.get()));
        var smelting = manager.getRecipeFor(RecipeType.SMELTING, cooking, level).orElseThrow().value();
        var blasting = manager.getRecipeFor(RecipeType.BLASTING, cooking, level).orElseThrow().value();
        helper.assertTrue(smelting.assemble(cooking, level.registryAccess()).is(ModItems.SOUL_DUST.get())
                && blasting.assemble(cooking, level.registryAccess()).is(ModItems.SOUL_DUST.get()),
                "熔炉与高炉都应产出灵魂粉尘");
        helper.assertTrue(smelting.getCookingTime() == 200 && blasting.getCookingTime() == 100
                && smelting.getExperience() == 1f && blasting.getExperience() == 1f,
                "熔炼耗时与经验应与来源一致");
        for (var item : List.of(ModItems.SOUL_ESSENCE.get(), ModItems.EMBRYO_CHARM.get(),
                ModItems.EMBRYO_NECKLACE.get(), ModItems.EMBRYO_RING.get(), ModItems.EMBRYO_BELT.get())) {
            helper.assertTrue(manager.byKey(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item)).isPresent(),
                    "灵魂精华及原胚配方必须可加载: " + item);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void soulOreWorldgen(GameTestHelper helper) {
        var level = helper.getLevel();
        var registry = level.registryAccess();
        var feature = registry.lookupOrThrow(Registries.PLACED_FEATURE)
                .getOrThrow(ModPlacedFeatures.SOUL_ORE_PATCH).value();
        registry.lookupOrThrow(Registries.BIOME).listElements().forEach(biome -> {
            boolean target = biome.is(ModBiomeTags.CAN_SOUL_ORE_SPAWN_BIOME);
            helper.assertTrue(biome.value().getGenerationSettings().hasFeature(feature) == target,
                    "灵魂矿土只能注入目标群系: " + biome.key());
            if (target) {
                helper.assertTrue(biome.value().getGenerationSettings().features()
                        .get(GenerationStep.Decoration.VEGETAL_DECORATION.ordinal()).stream()
                        .anyMatch(holder -> holder.value() == feature), "应保留来源生成阶段");
            }
        });
        var config = (OreConfiguration) feature.feature().value().config();
        helper.assertTrue(config.size == 16 && config.discardChanceOnAirExposure == 0f
                && config.targetStates.size() == 1, "矿脉应为 16 方块且仅有一种替换规则");
        var target = config.targetStates.getFirst();
        var random = RandomSource.create(37L);
        helper.assertTrue(target.state.is(ModBlocks.SOUL_ORE.get())
                && target.target.test(Blocks.SOUL_SOIL.defaultBlockState(), random), "只能替换灵魂土");
        for (var excluded : List.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.STONE, Blocks.AIR)) {
            helper.assertFalse(target.target.test(excluded.defaultBlockState(), random), "不应替换 " + excluded);
        }
        var context = new PlacementContext(level, level.getChunkSource().getGenerator(), Optional.empty());
        for (int sample = 0; sample < 16; sample++) {
            Stream<BlockPos> positions = Stream.of(BlockPos.ZERO);
            for (var modifier : feature.placement()) {
                positions = positions.flatMap(pos -> modifier.getPositions(context, random, pos));
            }
            var placed = positions.toList();
            helper.assertTrue(placed.size() == 8 && placed.stream().allMatch(pos -> pos.getY() >= 0
                    && pos.getY() <= 128 && pos.getX() >= 0 && pos.getX() < 16
                    && pos.getZ() >= 0 && pos.getZ() < 16), "每区块应在 Y=0～128 尝试 8 次");
        }
        helper.succeed();
    }
}

