package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest.OpenedMeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestBlockEntity;
import com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest.MeltDreamCrystalChestLootTableNBT;
import com.pasterdream.pasterdreammod.world.item.meltdreamcrystalchestresettool.MeltDreamCrystalChestResetCooldown;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MeltDreamCrystalChestGameTests {
    @GameTest(template = "empty", timeoutTicks = 120)
    public static void openAndResetDyedreamChest(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        ItemStack configured = MeltDreamCrystalChestLootTableNBT.onlyNormal(ModItems.MELT_DREAM_CRYSTAL_CHEST.get());
        CompoundTag data = configured.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        helper.assertTrue(data.getList("LootTables", Tag.TAG_COMPOUND).size() == 1,
                "普通品质箱子应包含一张战利品表");
        helper.setBlock(pos, ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get());
        MeltDreamCrystalChestBlockEntity chest = helper.getBlockEntity(pos);
        chest.setLootTablesNbt(data.getList("LootTables", Tag.TAG_COMPOUND));

        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "crystal-chest-test"));
        player.setPos(helper.absolutePos(pos).getCenter());
        helper.getLevel().addNewPlayer(player);
        helper.useBlock(pos, player);
        helper.assertTrue(chest.getAnimationState() == 1, "普通品质箱子应启动普通开启动画");
        helper.assertTrue(MeltDreamEnergyHelper.getPlayerMeltDreamEnergy(player) >= 2.0,
                "开启箱子应获得两点融梦能量");

        helper.runAfterDelay(45, () -> {
            helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get()),
                    "开奖后应转为已开启的容器方块");
            OpenedMeltDreamCrystalChestBlockEntity opened = helper.getBlockEntity(pos);
            ItemStack resetTool = opened.getItemHandler().getStackInSlot(4);
            helper.assertTrue(resetTool.is(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()),
                    "已开启箱子的中央槽应放入重置工具");
            helper.assertTrue(resetTool.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
                            .getList("LootTables", Tag.TAG_COMPOUND).size() == 1,
                    "重置工具应继承原箱子的战利品列表");

            MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(player, 100);
            player.setItemInHand(InteractionHand.MAIN_HAND, resetTool.copy());
            helper.useBlock(pos, player);
            helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get()),
                    "重置工具应恢复未开启的箱子");
            helper.assertTrue(MeltDreamCrystalChestResetCooldown.getRemainingTicks(player) > 0,
                    "生存模式重置应启动冷却");
            helper.setBlock(pos, ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get());
            player.setItemInHand(InteractionHand.MAIN_HAND,
                    MeltDreamCrystalChestLootTableNBT.onlyNormal(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()));
            helper.useBlock(pos, player);
            helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get()),
                    "冷却期间再次使用重置工具不得恢复箱子");
            helper.getLevel().removePlayerImmediately(player, Entity.RemovalReason.DISCARDED);
            helper.succeed();
        });
    }
}
