package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
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

    private record CropCase(Block block, Item seedling, Item product, int count) {
    }

    private record CropConversion(Item crop, Item product, int count) {
    }
}
