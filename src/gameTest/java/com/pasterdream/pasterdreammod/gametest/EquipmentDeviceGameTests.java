package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.crate.picnicbasket.PicnicBasketMenu;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskBlockEntity;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.shadowdesk.ShadowDeskMenu;
import com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers.TheEndlessBookOfDreamSeekersBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class EquipmentDeviceGameTests {
    @GameTest(template = "empty")
    public static void deviceContainers(GameTestHelper helper) {
        BlockPos deskPos = new BlockPos(1, 1, 1);
        helper.setBlock(deskPos, ModBlocks.SHADOW_DESK.get());
        ShadowDeskBlockEntity shadowDesk = helper.getBlockEntity(deskPos);
        helper.assertTrue(shadowDesk.getItemHandler().getSlots() == 1, "阴影书桌应只有一个物品槽");
        helper.assertTrue(helper.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, helper.absolutePos(deskPos), null) != null,
                "阴影书桌应暴露物品处理接口");
        shadowDesk.getItemHandler().setStackInSlot(0, new ItemStack(Items.BOOK, 3));
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "device-test"));
        player.setPos(helper.absolutePos(deskPos).getCenter());
        var deskMenu = new ShadowDeskMenu(1, player.getInventory(), shadowDesk);
        helper.assertTrue(deskMenu.stillValid(player), "玩家靠近阴影书桌时菜单应有效");
        ItemStack moved = deskMenu.quickMoveStack(player, 0);
        helper.assertTrue(moved.is(Items.BOOK) && moved.getCount() == 3
                && shadowDesk.getItemHandler().getStackInSlot(0).isEmpty(), "Shift 点击应将书本移入玩家背包");

        BlockPos basketPos = new BlockPos(3, 1, 1);
        helper.setBlock(basketPos, ModBlocks.PICNIC_BASKET.get());
        PicnicBasketBlockEntity basket = helper.getBlockEntity(basketPos);
        helper.assertTrue(basket.getItemHandler().getSlots() == 15, "野餐篮应有十五个物品槽");
        basket.getItemHandler().setStackInSlot(0, new ItemStack(Items.APPLE, 2));
        player.setPos(helper.absolutePos(basketPos).getCenter());
        var basketMenu = new PicnicBasketMenu(1, player.getInventory(), basket);
        helper.assertTrue(basketMenu.stillValid(player), "玩家靠近野餐篮时菜单应有效");
        ItemStack movedApple = basketMenu.quickMoveStack(player, 0);
        helper.assertTrue(movedApple.is(Items.APPLE) && movedApple.getCount() == 2
                && basket.getItemHandler().getStackInSlot(0).isEmpty(), "Shift 点击应将苹果移入玩家背包");

        for (Block block : new Block[]{ModBlocks.WIND_MOOR_DESK.get(), ModBlocks.SHADOW_CHEST.get(), ModBlocks.WIND_MOOR_CRATE.get()}) {
            BlockPos pos = new BlockPos(5, 1, 1);
            helper.setBlock(pos, block);
            helper.assertTrue(helper.getBlockEntity(pos) != null, "设备应创建方块实体: " + block);
        }

        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_desk")).isPresent(), "应存在阴影书桌配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_moor_desk")).isPresent(), "应存在风泊书桌配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "picnic_basket")).isPresent(), "应存在野餐篮配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_moor_crate")).isPresent(), "应存在风泊板条筐配方");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_chest_from_stonecutting")).isPresent(), "应存在浸影宝盒切石配方");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void endlessBookUseAndPliersRemainder(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get());
        TheEndlessBookOfDreamSeekersBlockEntity book = helper.getBlockEntity(pos);
        book.getItemHandler().setStackInSlot(0, new ItemStack(Items.PAPER, 1));
        book.setAnimationState(1);
        book.triggerUseParticles(book.getItemHandler().getStackInSlot(0).copy());
        for (int i = 0; i < 21; i++) {
            book.serverTick(helper.getLevel(), helper.absolutePos(pos));
        }
        helper.assertTrue(!helper.getLevel().getEntitiesOfClass(ItemEntity.class,
                new AABB(helper.absolutePos(pos)).inflate(2), item -> item.getItem().is(Items.PAPER)).isEmpty(),
                "永恒书卷应在动画第二十刻掉出槽内物品副本");
        helper.assertTrue(book.getItemHandler().getStackInSlot(0).is(Items.PAPER), "来源行为应保留槽内原物品");

        ItemStack pliers = new ItemStack(ModItems.PLIERS.get());
        ItemStack remainder = pliers.getCraftingRemainingItem();
        helper.assertTrue(remainder.is(ModItems.PLIERS.get()) && remainder.getDamageValue() == 1, "园艺钳合成后应损耗一点耐久");
        pliers.setDamageValue(pliers.getMaxDamage() - 1);
        helper.assertTrue(pliers.getCraftingRemainingItem().isEmpty(), "园艺钳耐久耗尽时合成余料应为空");
        helper.succeed();
    }
}
