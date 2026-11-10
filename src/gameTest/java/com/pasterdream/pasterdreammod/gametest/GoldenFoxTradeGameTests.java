package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import com.pasterdream.pasterdreammod.recipe.GoldenFoxTradeRecipe;
import com.pasterdream.pasterdreammod.world.block.foxsculpture.FoxSculptureBlockEntity;
import com.pasterdream.pasterdreammod.world.block.goldenfoxsculpture.GoldenFoxSculptureBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class GoldenFoxTradeGameTests {
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path);
    }

    @GameTest(template = "empty")
    public static void tradeRecipesLoad(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();

        var appleHolder = manager.byKey(id("golden_fox_trade_golden_apple")).orElseThrow();
        helper.assertTrue(appleHolder.value() instanceof GoldenFoxTradeRecipe, "金狐交易配方应使用专用配方类");
        GoldenFoxTradeRecipe apple = (GoldenFoxTradeRecipe) appleHolder.value();
        helper.assertTrue(apple.getType() == ModRecipes.GOLDEN_FOX_TRADE.get(), "金狐交易配方类型不符");
        helper.assertTrue(apple.matches(new ItemStack(Items.GOLDEN_APPLE)), "金苹果应匹配金狐交易");
        helper.assertTrue(!apple.matches(new ItemStack(Items.DIRT)), "泥土不应匹配金狐交易");
        helper.assertTrue(apple.getResultItem(helper.getLevel().registryAccess()).is(Items.ENCHANTED_GOLDEN_APPLE),
                "金苹果应换取附魔金苹果");

        GoldenFoxTradeRecipe bucket = (GoldenFoxTradeRecipe) manager.byKey(id("golden_fox_trade_bucket"))
                .orElseThrow().value();
        helper.assertTrue(bucket.getResultItem(helper.getLevel().registryAccess()).is(ModItems.MELT_DREAM_LIQUID_BUCKET.get()),
                "空桶应换取融梦液体桶");

        GoldenFoxTradeRecipe coinPile = (GoldenFoxTradeRecipe) manager.byKey(id("golden_fox_trade_coin_pile"))
                .orElseThrow().value();
        ItemStack coinResult = coinPile.getResultItem(helper.getLevel().registryAccess());
        helper.assertTrue(coinResult.is(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()) && coinResult.getCount() == 3,
                "融梦钱堆应换取 3 个超级染梦深海秘宝");
        helper.assertTrue(Boolean.TRUE.equals(coinResult.get(ModDataComponents.DEEP_TREASURE_SUPER.get())),
                "钱堆交易产物应带超级秘宝组件");

        GoldenFoxTradeRecipe crystalFragment = (GoldenFoxTradeRecipe) manager
                .byKey(id("golden_fox_trade_crystal_fragment")).orElseThrow().value();
        helper.assertTrue(crystalFragment.getResultItem(helper.getLevel().registryAccess()).is(ModItems.KAICHU_OMAMORI.get()),
                "融梦水晶碎片应换取怀中御守");

        GoldenFoxTradeRecipe moltenGoldApple = (GoldenFoxTradeRecipe) manager
                .byKey(id("golden_fox_trade_molten_gold_apple")).orElseThrow().value();
        helper.assertTrue(moltenGoldApple.getResultItem(helper.getLevel().registryAccess())
                        .is(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get()),
                "熔金苹果应换取附魔熔金苹果");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void entitiesAndSculpturesRegistered(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.GOLDEN_FOX.get()).getPath().equals("golden_fox"),
                "金狐实体注册 ID 不符");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.GOLDEN_FOX_PET.get()).getPath().equals("golden_fox_pet"),
                "金狐宠物注册 ID 不符");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.GOLDEN_FOX_FIREBALL.get()).getPath()
                .equals("golden_fox_fireball"), "金狐火球注册 ID 不符");

        BlockPos goldenPos = new BlockPos(1, 1, 1);
        helper.setBlock(goldenPos, ModBlocks.GOLDEN_FOX_SCULPTURE.get());
        helper.assertTrue(helper.getBlockEntity(goldenPos) instanceof GoldenFoxSculptureBlockEntity,
                "金狐雕像应创建方块实体");

        BlockPos foxPos = new BlockPos(3, 1, 1);
        helper.setBlock(foxPos, ModBlocks.FOX_SCULPTURE.get());
        helper.assertTrue(helper.getBlockEntity(foxPos) instanceof FoxSculptureBlockEntity,
                "狐狸雕像应创建方块实体");
        helper.succeed();
    }
}
