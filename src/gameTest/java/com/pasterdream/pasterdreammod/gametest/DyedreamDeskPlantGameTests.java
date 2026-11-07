package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk.DyedreamDeskMenu;
import com.pasterdream.pasterdreammod.world.block.NippyEdelweissBlock;
import com.pasterdream.pasterdreammod.world.block.PottedCropBlock;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamDeskPlantGameTests {
    @GameTest(template = "empty")
    public static void deskInventoryAndRecipes(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DYEDREAM_DESK.get());
        DyedreamDeskBlockEntity desk = helper.getBlockEntity(pos);
        helper.assertTrue(desk.getItemHandler().getSlots() == 1, "染梦书桌应只有一个物品槽");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, helper.absolutePos(pos), null) != null,
                "书桌应暴露物品处理接口");
        desk.getItemHandler().setStackInSlot(0, new ItemStack(Items.BOOK, 3));
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "desk-test"));
        player.setPos(helper.absolutePos(pos).getCenter());
        var menu = new DyedreamDeskMenu(1, player.getInventory(), desk);
        helper.assertTrue(menu.stillValid(player), "玩家靠近书桌时菜单应有效");
        ItemStack moved = menu.quickMoveStack(player, 0);
        helper.assertTrue(moved.is(Items.BOOK) && moved.getCount() == 3
                && desk.getItemHandler().getStackInSlot(0).isEmpty(), "Shift 点击应将书本移入玩家背包");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_desk")).isPresent(), "应存在木材配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.withDefaultNamespace("dyedream_desk_from_lectern")).isPresent(), "应存在来源 minecraft 命名空间的讲台配方");
        var drops = Block.getDrops(helper.getBlockState(pos), helper.getLevel(), helper.absolutePos(pos), desk, player, ItemStack.EMPTY);
        helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.DYEDREAM_DESK.get()), "书桌应掉落自身");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void nippyEdelweissBlockEntity(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.NIPPY_EDELWEISS.get());
        helper.assertTrue(helper.getBlockEntity(pos) != null, "凛冽雪绒花应创建来源方块实体");
        helper.assertTrue(helper.getBlockState(pos).isRandomlyTicking(), "凛冽雪绒花应接收随机刻");
        BlockPos absolute = helper.absolutePos(pos);
        for (int i = 0; i < 5; i++)
            ((NippyEdelweissBlock) ModBlocks.NIPPY_EDELWEISS.get()).randomTick(helper.getBlockState(pos), helper.getLevel(), absolute, RandomSource.create(i));
        int snow = 0;
        for (BlockPos target : BlockPos.betweenClosed(absolute.offset(-3, -1, -3), absolute.offset(3, 0, 3)))
            if (helper.getLevel().getBlockState(target).is(Blocks.SNOW)) snow++;
        helper.assertTrue(snow > 0, "随机刻应在周围地面生成雪层");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "edelweissRitual")
    public static void edelweissRitual(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(1, 4, 1));
        BlockPos deskPos = center.below(3);
        level.setBlock(deskPos, ModBlocks.DYEDREAM_DESK.get().defaultBlockState(), 3);
        level.setBlock(center.below(), ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState(), 3);
        BlockPos[] corners = {center.offset(5, 0, 0), center.offset(-5, 0, 0),
                center.offset(0, 0, 5), center.offset(0, 0, -5)};
        Block[] pattern = {ModBlocks.SINGULARITY_FERN.get(), ModBlocks.LINHT_FLOWER.get(),
                ModBlocks.DYEDREAM_COROLLA_CROP.get(), ModBlocks.DYEDREAM_SAPLING.get()};
        for (int i = 0; i < corners.length; i++) {
            level.setBlock(corners[i].below(), ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState(), 3);
            level.setBlock(corners[i], pattern[i].defaultBlockState(), 3);
        }
        level.setBlock(center, ModBlocks.EDELWEISS.get().defaultBlockState(), 3);
        var snowGolem = EntityType.SNOW_GOLEM.create(level);
        var allay = EntityType.ALLAY.create(level);
        helper.assertTrue(snowGolem != null && allay != null, "仪式生物应可创建");
        snowGolem.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
        allay.setPos(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);
        level.addFreshEntity(snowGolem);
        level.addFreshEntity(allay);
        var player = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "ritual-test"));
        player.setPos(center.getCenter());
        var hit = new BlockHitResult(Vec3.atCenterOf(center), Direction.UP, center, false);
        level.getBlockState(center).useItemOn(ItemStack.EMPTY, level, player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(level.getBlockState(center).isAir(), "雪绒花仪式应移除中心花");
        for (BlockPos corner : corners)
            helper.assertTrue(level.getBlockState(corner).isAir(), "雪绒花仪式应消耗四角植物");
        helper.assertTrue(level.getBlockState(deskPos).isAir(), "默认配置应消耗染梦书桌");
        helper.assertTrue(!level.getEntitiesOfClass(ItemEntity.class, new AABB(center).inflate(2),
                item -> item.getItem().is(ModItems.NIPPY_EDELWEISS.get())).isEmpty(), "仪式应掉落凛冽雪绒花");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "pinkSingleGrowth")
    public static void pinkMushroomBonemeal(GameTestHelper helper) {
        var level = helper.getLevel();
        var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        helper.assertTrue(registry.get(ModConfiguredFeatures.PINK_HUGE_MUSHROOM) != null
                && registry.get(ModConfiguredFeatures.PINK_MUSHROOM_TREE) != null,
                "粉顶菌单株和 2×2 生长特征应已注册");
        BlockPos pos = helper.absolutePos(new BlockPos(1, 10, 1));
        level.setBlock(pos.below(), ModBlocks.DYEDREAM_DIRT.get().defaultBlockState(), 3);
        level.setBlock(pos, ModBlocks.PINK_MUSHROOM.get().defaultBlockState(), 3);
        ((BonemealableBlock) ModBlocks.PINK_MUSHROOM.get()).performBonemeal(level, RandomSource.create(44), pos, level.getBlockState(pos));
        int stems = 0;
        for (BlockPos target : BlockPos.betweenClosed(pos, pos.above(8)))
            if (level.getBlockState(target).is(ModBlocks.PINK_MUSHROOM_STEM.get())) stems++;
        helper.assertTrue(stems > 0, "粉顶菌骨粉催大应生成菌柄");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "dyedreamSaplingGrowth")
    public static void dyedreamSaplingBonemeal(GameTestHelper helper) {
        var level = helper.getLevel();
        var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        helper.assertTrue(registry.get(ModConfiguredFeatures.DYEDREAM_TREE) != null, "染梦树生长特征应已注册");
        BlockPos pos = helper.absolutePos(new BlockPos(1, 10, 1));
        level.setBlock(pos.below(), ModBlocks.DYEDREAM_DIRT.get().defaultBlockState(), 3);
        level.setBlock(pos, ModBlocks.DYEDREAM_SAPLING.get().defaultBlockState(), 3);
        var sapling = (BonemealableBlock) ModBlocks.DYEDREAM_SAPLING.get();
        sapling.performBonemeal(level, RandomSource.create(44), pos, level.getBlockState(pos));
        sapling.performBonemeal(level, RandomSource.create(45), pos, level.getBlockState(pos));
        helper.assertTrue(level.getBlockState(pos).is(ModBlocks.DYEDREAM_LOG.get()), "染梦树苗骨粉催大应生成原木");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "pinkGiantGrowth")
    public static void pinkMushroomTwoByTwoBonemeal(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos origin = helper.absolutePos(new BlockPos(1, 10, 1));
        for (int dx = 0; dx < 2; dx++) {
            for (int dz = 0; dz < 2; dz++) {
                BlockPos pos = origin.offset(dx, 0, dz);
                level.setBlock(pos.below(), ModBlocks.DYEDREAM_DIRT.get().defaultBlockState(), 3);
                level.setBlock(pos, ModBlocks.PINK_MUSHROOM.get().defaultBlockState(), 3);
            }
        }
        ((BonemealableBlock) ModBlocks.PINK_MUSHROOM.get()).performBonemeal(level, RandomSource.create(46),
                origin, level.getBlockState(origin));
        helper.assertTrue(level.getBlockState(origin).is(ModBlocks.PINK_MUSHROOM_STEM.get()),
                "2×2 粉顶菌骨粉催大应生成巨树菌柄");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "pinkTallGrowth")
    public static void tallPinkMushroomBonemeal(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(1, 10, 1));
        level.setBlock(pos.below(), ModBlocks.DYEDREAM_DIRT.get().defaultBlockState(), 3);
        level.setBlock(pos, ModBlocks.TALL_PINK_MUSHROOM.get().defaultBlockState()
                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 3);
        level.setBlock(pos.above(), ModBlocks.TALL_PINK_MUSHROOM.get().defaultBlockState()
                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
        ((BonemealableBlock) ModBlocks.TALL_PINK_MUSHROOM.get()).performBonemeal(level,
                RandomSource.create(47), pos.above(), level.getBlockState(pos.above()));
        helper.assertTrue(level.getBlockState(pos).is(ModBlocks.PINK_MUSHROOM_STEM.get())
                && !level.getBlockState(pos.above()).is(ModBlocks.TALL_PINK_MUSHROOM.get()),
                "双层粉顶菌从上半部催大应清除两半并生成巨菇");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void ordinaryFlowerPot(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, Blocks.FLOWER_POT);
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "flower-pot-test"));
        player.setPos(helper.absolutePos(pos).getCenter());
        ItemStack flower = ModItems.EDELWEISS.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, flower);
        var absolute = helper.absolutePos(pos);
        var hit = new BlockHitResult(Vec3.atCenterOf(absolute), Direction.UP, absolute, false);
        helper.getBlockState(pos).useItemOn(flower, helper.getLevel(), player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.POTTED_EDELWEISS.get()),
                "普通花盆应接受已注册的雪绒花");
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        helper.useBlock(pos, player);
        helper.assertTrue(helper.getBlockState(pos).is(Blocks.FLOWER_POT), "空手应从盆栽取出植物");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void pottedCropStagesAndHarvest(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, Blocks.FLOWER_POT);
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "potted-crop-test"));
        player.setPos(helper.absolutePos(pos).getCenter());
        var absolute = helper.absolutePos(pos);
        var hit = new BlockHitResult(Vec3.atCenterOf(absolute), Direction.UP, absolute, false);
        ItemStack mature = ModItems.CLOUD_CROP_AGE_1.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mature);
        helper.getBlockState(pos).useItemOn(mature, helper.getLevel(), player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.POTTED_CLOUD_CROP.get())
                && helper.getBlockState(pos).getValue(PottedCropBlock.AGE) == 1,
                "成熟作物应以 age=1 种入空花盆");
        ItemStack shears = new ItemStack(Items.SHEARS);
        player.setItemInHand(InteractionHand.MAIN_HAND, shears);
        helper.getBlockState(pos).useItemOn(shears, helper.getLevel(), player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(helper.getBlockState(pos).getValue(PottedCropBlock.AGE) == 0,
                "剪刀采收后盆栽应回到幼苗");
        helper.assertTrue(!helper.getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(absolute).inflate(2),
                item -> item.getItem().is(ModItems.CLOUD.get()) && item.getItem().getCount() == 5).isEmpty(),
                "云朵盆栽应产出来源数量 5");
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        helper.useBlock(pos, player);
        helper.assertTrue(helper.getBlockState(pos).is(Blocks.FLOWER_POT), "空手应取出幼苗并留下空花盆");
        ItemStack seedling = ModItems.CLOUD_CROP_AGE_0.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, seedling);
        helper.getBlockState(pos).useItemOn(seedling, helper.getLevel(), player, InteractionHand.MAIN_HAND, hit);
        helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.POTTED_CLOUD_CROP.get())
                && helper.getBlockState(pos).getValue(PottedCropBlock.AGE) == 0,
                "幼苗应以 age=0 种入空花盆");
        var seedlingDrops = Block.getDrops(helper.getBlockState(pos), helper.getLevel(), absolute, null, player, ItemStack.EMPTY);
        helper.assertTrue(seedlingDrops.stream().anyMatch(drop -> drop.is(Items.FLOWER_POT))
                && seedlingDrops.stream().anyMatch(drop -> drop.is(ModItems.CLOUD_CROP_AGE_0.get())),
                "打破幼苗盆栽应掉落花盆和幼苗");
        helper.setBlock(new BlockPos(2, 1, 1), Blocks.GLOWSTONE);
        ((PottedCropBlock) ModBlocks.POTTED_CLOUD_CROP.get()).randomTick(
                helper.getBlockState(pos), helper.getLevel(), absolute, RandomSource.create(0));
        helper.assertTrue(helper.getBlockState(pos).getValue(PottedCropBlock.AGE) == 1,
                "充足光照下盆栽作物应成熟");
        var matureDrops = Block.getDrops(helper.getBlockState(pos), helper.getLevel(), absolute, null, player, ItemStack.EMPTY);
        helper.assertTrue(matureDrops.stream().anyMatch(drop -> drop.is(Items.FLOWER_POT))
                && matureDrops.stream().anyMatch(drop -> drop.is(ModItems.CLOUD_CROP_AGE_1.get())),
                "打破成熟盆栽应掉落花盆和成熟植株");
        helper.succeed();
    }
}
