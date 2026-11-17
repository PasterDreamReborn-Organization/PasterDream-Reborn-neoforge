package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class PlantGameTests {
    @GameTest(template = "empty")
    public static void cropStagesAndProducts(GameTestHelper helper) {
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var crops = List.of(
                new CropCase(ModBlocks.DYEDREAM_COROLLA_CROP.get(), ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get(), ModItems.DYEDREAM_COROLLA.get(), 1),
                new CropCase(ModBlocks.WHITE_COROLLA_CROP.get(), ModItems.WHITE_COROLLA_CROP_AGE_0.get(), ModItems.WHITE_COROLLA.get(), 1),
                new CropCase(ModBlocks.LIGHT_BALL_CROP.get(), ModItems.LIGHT_BALL_CROP_AGE_0.get(), ModItems.LIGHT_BALL.get(), 1),
                new CropCase(ModBlocks.CLOUD_CROP.get(), ModItems.CLOUD_CROP_AGE_0.get(), ModItems.CLOUD.get(), 5),
                new CropCase(ModBlocks.COTTON_CROP.get(), ModItems.COTTON_CROP_AGE_0.get(), ModItems.COTTON.get(), 1));
        var fakePlayer = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "plant-test"));
        for (var crop : crops) {
            var immature = crop.block().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 0);
            var mature = crop.block().defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1);
            helper.getLevel().setBlock(pos, immature, 3);
            var youngDrops = Block.getDrops(immature, helper.getLevel(), pos, null, null, ItemStack.EMPTY);
            helper.assertTrue(youngDrops.size() == 1 && youngDrops.getFirst().is(crop.seedling()), "未成熟作物应掉落幼苗: " + crop.block());
            helper.getLevel().setBlock(pos, mature, 3);
            var matureDrops = Block.getDrops(mature, helper.getLevel(), pos, null, null, ItemStack.EMPTY);
            helper.assertTrue(matureDrops.size() == 1 && matureDrops.getFirst().is(crop.product())
                    && matureDrops.getFirst().getCount() == crop.count(), "非玩家收割成熟作物应掉落来源产物: " + crop.block());
            var fakePlayerDrops = Block.getDrops(mature, helper.getLevel(), pos, null, fakePlayer, ItemStack.EMPTY);
            helper.assertTrue(fakePlayerDrops.size() == 1 && fakePlayerDrops.getFirst().is(crop.product())
                    && fakePlayerDrops.getFirst().getCount() == crop.count(), "模拟玩家收割成熟作物应掉落来源产物: " + crop.block());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void plantSubstratesAndDrops(GameTestHelper helper) {
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        helper.assertTrue(ModBlocks.DYEDREAM_DIRT.get().defaultBlockState().is(ModBlockTags.DYEDREAM_DIRT), "染梦作物应有染梦泥土基底");
        helper.assertTrue(net.minecraft.world.level.block.Blocks.NETHERRACK.defaultBlockState().is(ModBlockTags.BLAZE_FLOWER_CAN_PLACE_ON), "炽焰花应可种在下界岩上");
        var shears = new ItemStack(Items.SHEARS);
        var reed = ModBlocks.REED.get().defaultBlockState();
        helper.getLevel().setBlock(pos, reed, 3);
        var sheared = Block.getDrops(reed, helper.getLevel(), pos, null, null, shears);
        helper.assertTrue(sheared.size() == 1 && sheared.getFirst().is(ModItems.REED.get()), "剪刀收割芦苇应掉落植株");
        var ordinary = Block.getDrops(reed, helper.getLevel(), pos, null, null, ItemStack.EMPTY);
        helper.assertTrue(ordinary.size() == 1 && ordinary.getFirst().is(ModItems.REED_ROD.get())
                && ordinary.getFirst().getCount() >= 1 && ordinary.getFirst().getCount() <= 3, "普通收割芦苇应掉落芦苇杆");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void matureCropConversionRecipes(GameTestHelper helper) {
        var conversions = List.of(
                new CropConversion(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), ModItems.DYEDREAM_COROLLA.get(), 1),
                new CropConversion(ModItems.WHITE_COROLLA_CROP_AGE_1.get(), ModItems.WHITE_COROLLA.get(), 1),
                new CropConversion(ModItems.LIGHT_BALL_CROP_AGE_1.get(), ModItems.LIGHT_BALL.get(), 1),
                new CropConversion(ModItems.CLOUD_CROP_AGE_1.get(), ModItems.CLOUD.get(), 5),
                new CropConversion(ModItems.COTTON_CROP_AGE_1.get(), ModItems.COTTON.get(), 1));
        for (var conversion : conversions) {
            var input = CraftingInput.of(1, 1, List.of(new ItemStack(conversion.crop())));
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input,
                    helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                            BuiltInRegistries.ITEM.getKey(conversion.product()).getPath())),
                    "成熟作物转换配方 ID 不符: " + conversion.product());
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(conversion.product()) && result.getCount() == conversion.count(),
                    "成熟作物转换产物不符: " + conversion.product());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void cropHarvestRequiresPliersOrShears(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var block = ModBlocks.COTTON_CROP.get();
        var mature = block.defaultBlockState().setValue(PasterDreamCropBlock.AGE, 1);
        var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);

        helper.assertTrue(Config.IS_NEED_USE_PLIERS_OR_SHEARS.get(), "默认配置应要求园艺钳或剪刀");

        level.setBlock(pos, mature, 3);
        var plainResult = mature.useItemOn(new ItemStack(Items.STICK), level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(plainResult == ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION, "普通物品不应收割成熟作物");
        helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 1, "普通物品不应改变成熟作物状态");

        var shears = new ItemStack(Items.SHEARS);
        var shearsResult = mature.useItemOn(shears, level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(shearsResult.consumesAction(), "剪刀应可收割成熟作物");
        helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 0, "剪刀收割后作物应回到未成熟状态");

        level.setBlock(pos, mature, 3);
        var pliers = new ItemStack(ModItems.PLIERS.get());
        var pliersResult = mature.useItemOn(pliers, level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(pliersResult.consumesAction(), "园艺钳应可收割成熟作物");
        helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 0, "园艺钳收割后作物应回到未成熟状态");

        level.setBlock(pos, mature, 3);
        Config.IS_NEED_USE_PLIERS_OR_SHEARS.set(false);
        try {
            var relaxedResult = mature.useItemOn(new ItemStack(Items.STICK), level, player, InteractionHand.MAIN_HAND, hit);
            helper.assertTrue(relaxedResult.consumesAction(), "关闭配置后普通物品应可收割成熟作物");
            helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 0, "关闭配置后普通物品收割应改变作物状态");
        } finally {
            Config.IS_NEED_USE_PLIERS_OR_SHEARS.set(true);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void cropRipensWithDreamFertilizer(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 1, 1));
        var block = ModBlocks.COTTON_CROP.get();
        var immature = block.defaultBlockState().setValue(PasterDreamCropBlock.AGE, 0);
        var hit = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);

        level.setBlock(pos, immature, 3);
        var fertilizer = new ItemStack(ModItems.DREAM_FERTILIZER.get(), 2);
        var ripened = immature.useItemOn(fertilizer, level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(ripened.consumesAction(), "衍梦肥泥应可催熟未成熟作物");
        helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 1, "衍梦肥泥应把作物推进到成熟状态");
        helper.assertTrue(fertilizer.getCount() == 1, "生存模式使用衍梦肥泥应消耗 1 个");

        var mature = level.getBlockState(pos);
        var surplus = new ItemStack(ModItems.DREAM_FERTILIZER.get(), 2);
        var surplusResult = mature.useItemOn(surplus, level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(surplusResult == ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION, "已成熟作物不应响应衍梦肥泥");
        helper.assertTrue(surplus.getCount() == 2, "已成熟作物不应消耗衍梦肥泥");

        var creativePlayer = helper.makeMockPlayer(GameType.CREATIVE);
        var creativeFertilizer = new ItemStack(ModItems.DREAM_FERTILIZER.get(), 2);
        level.setBlock(pos, immature, 3);
        var creativeResult = immature.useItemOn(creativeFertilizer, level, creativePlayer, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(creativeResult.consumesAction(), "创造模式玩家也应可催熟作物");
        helper.assertTrue(level.getBlockState(pos).getValue(PasterDreamCropBlock.AGE) == 1, "创造模式玩家催熟后作物应成熟");
        helper.assertTrue(creativeFertilizer.getCount() == 2, "创造模式使用衍梦肥泥不应消耗物品");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void flowerDyeRecipes(GameTestHelper helper) {
        var cases = List.of(
                new FlowerDye(ModItems.FERRARIA_CRISPA.get(), Items.BLACK_DYE, "black_dye_from_ferraria_crispa"),
                new FlowerDye(ModItems.EUSTOMA.get(), Items.GREEN_DYE, "green_dye_from_eustoma"),
                new FlowerDye(ModItems.MALVA_SINENSIS_CAVAN.get(), Items.PINK_DYE, "pink_dye_from_malva_sinensis_cavan"));
        for (var dyeCase : cases) {
            var input = CraftingInput.of(1, 1, List.of(new ItemStack(dyeCase.flower())));
            var recipe = helper.getLevel().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input,
                    helper.getLevel()).orElseThrow();
            helper.assertTrue(recipe.id().equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, dyeCase.id())),
                    "花合成染料配方 ID 不符: " + dyeCase.flower());
            var result = recipe.value().assemble(input, helper.getLevel().registryAccess());
            helper.assertTrue(result.is(dyeCase.dye()) && result.getCount() == 1, "花合成染料配方产物不符: " + dyeCase.flower());
        }
        helper.succeed();
    }

    private record CropCase(Block block, Item seedling, Item product, int count) {
    }

    private record CropConversion(Item crop, Item product, int count) {
    }

    private record FlowerDye(Item flower, Item dye, String id) {
    }
}
