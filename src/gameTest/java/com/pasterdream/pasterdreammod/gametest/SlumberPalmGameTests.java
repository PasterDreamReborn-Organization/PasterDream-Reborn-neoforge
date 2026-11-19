package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.SlumberPalmSaplingBlock;
import com.pasterdream.pasterdreammod.world.block.SlumberPalmWallBlock;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class SlumberPalmGameTests {
    @GameTest(template = "empty")
    public static void sandSaplingGrowsAndBearsFruit(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos base = helper.absolutePos(new BlockPos(1, 18, 1));
        level.setBlockAndUpdate(base.below(), Blocks.SANDSTONE.defaultBlockState());
        level.setBlockAndUpdate(base, Blocks.SAND.defaultBlockState());
        BlockPos root = base.above();
        var sapling = (SlumberPalmSaplingBlock) ModBlocks.SLUMBER_PALM_SAPLING.get();
        helper.assertTrue(sapling.defaultBlockState().canSurvive(level, root), "眠椰树苗应能在沙上存活");
        level.setBlockAndUpdate(root, sapling.defaultBlockState());
        RandomSource random = RandomSource.create(43);
        sapling.performBonemeal(level, random, root, level.getBlockState(root));
        sapling.performBonemeal(level, random, root, level.getBlockState(root));
        helper.assertTrue(level.getBlockState(root).is(ModBlocks.SLUMBER_PALM_LOG.get()), "骨粉应通过配置地物催生眠椰树");

        int fruitCount = 0;
        for (BlockPos pos : BlockPos.betweenClosed(root.offset(-6, 0, -6), root.offset(6, 17, 6))) {
            var state = level.getBlockState(pos);
            if (!state.is(ModBlocks.SLUMBER_PALM_WALL_BLOCK.get())) {
                continue;
            }
            fruitCount++;
            var support = level.getBlockState(pos.relative(state.getValue(SlumberPalmWallBlock.FACING).getOpposite()));
            helper.assertTrue(support.is(ModBlocks.SLUMBER_PALM_LOG.get()), "贴墙眠椰应挂在原木上");
            var blockEntity = level.getBlockEntity(pos);
            helper.assertTrue(blockEntity != null && blockEntity.getType() == ModBlockEntities.SLUMBER_PALM.get(),
                    "果实方块应创建共用的眠椰方块实体");
            var drops = Block.getDrops(state, level, pos, blockEntity, null, new ItemStack(Items.IRON_AXE));
            helper.assertTrue(drops.size() == 1 && drops.getFirst().is(ModItems.SLUMBER_PALM.get())
                    && drops.getFirst().getCount() == 2, "普通采集应掉落 2 个眠椰");
            ItemStack silkAxe = new ItemStack(Items.IRON_AXE);
            silkAxe.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
            var silkDrops = Block.getDrops(state, level, pos, blockEntity, null, silkAxe);
            helper.assertTrue(silkDrops.size() == 1 && silkDrops.getFirst().is(ModItems.SLUMBER_PALM_BLOCK.get()),
                    "精准采集应回收可落地或贴墙的眠椰块物品");
        }
        helper.assertTrue(fruitCount >= 1 && fruitCount <= 3, "眠椰树应挂 1–3 个果实方块");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void slumberPalmDrinksUseTheirContainers(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack mortar = ModItems.MORTAR.toStack();
        player.setItemInHand(InteractionHand.MAIN_HAND, mortar);
        var menu = new MortarMenu(1, player.getInventory(), mortar, InteractionHand.MAIN_HAND);
        menu.setCarried(ModItems.SLUMBER_PALM.toStack());
        menu.interactFluid(player, 0, true);
        helper.assertTrue(menu.getCarried().is(Items.BOWL) && menu.getFluid(0).is(ModFluids.SLUMBER_PALM_JUICE.get())
                && menu.getFluid(0).getAmount() == 250, "眠椰应能倒出 250 mB 眠椰汁并返还碗");
        menu.getSlot(0).set(ModItems.SALT.toStack());
        menu.craft();
        helper.assertTrue(menu.getFluid(0).isEmpty() && menu.getSlot(0).getItem().isEmpty(), "研磨应消耗眠椰汁与盐");
        helper.assertTrue(menu.getFluid(4).is(ModFluids.SEA_SALT_SLUMBER_PALM_BEVERAGE.get())
                && menu.getFluid(4).getAmount() == 250, "研磨应产出 250 mB 海盐眠椰饮");
        for (int i = 0; i < 2; i++) {
            menu.setCarried(ModItems.MARTINI_GLASS.toStack());
            menu.interactFluid(player, 4, false);
            ItemStack beverage = menu.getCarried();
            helper.assertTrue(beverage.is(ModItems.MARTINI_GLASS_OF_SEA_SALT_SLUMBER_PALM_BEVERAGE.get()),
                    "每 125 mB 应装满一只马天尼杯");
            helper.assertTrue(beverage.finishUsingItem(helper.getLevel(), player).is(ModItems.MARTINI_GLASS.get()),
                    "饮用海盐眠椰饮后应返还马天尼杯");
        }
        helper.assertTrue(menu.getFluid(4).isEmpty() && player.hasEffect(ModEffects.COOK), "海盐眠椰饮应施加料理效果");

        ItemStack soda = ModItems.GLASS_CUP_OF_SLUMBER_PALM_CLUB_SODA.toStack();
        helper.assertTrue(soda.finishUsingItem(helper.getLevel(), player).is(ModItems.GLASS_CUP.get())
                && player.hasEffect(MobEffects.MOVEMENT_SPEED), "眠椰苏打水应返还玻璃杯并施加速度效果");
        helper.succeed();
    }
}
