package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DesertHeroGameTests {

    @GameTest(template = "empty")
    public static void itemPropertiesAndTags(GameTestHelper helper) {
        ItemStack desert = new ItemStack(ModItems.DESERT_SWORD.get());
        helper.assertTrue(desert.getMaxDamage() == 1561, "朔漠大剑耐久应为 1561");
        helper.assertTrue(desert.is(ItemTags.SWORDS), "朔漠大剑应属于剑标签");

        ItemStack chenjingmen = new ItemStack(ModItems.CHENJINGMEN_DESERT_SWORD.get());
        helper.assertTrue(chenjingmen.getMaxDamage() == 1561, "『沉荆门』朔漠耐久应为 1561");
        helper.assertTrue(chenjingmen.is(ItemTags.SWORDS), "『沉荆门』朔漠应属于剑标签");

        ItemStack brokenHero = new ItemStack(ModItems.BROKEN_HERO_SWORD.get());
        helper.assertTrue(brokenHero.getMaxDamage() == 100, "断裂英雄剑耐久应为 100");
        helper.assertTrue(brokenHero.is(ItemTags.SWORDS), "断裂英雄剑应属于剑标签");
        helper.assertTrue(brokenHero.has(DataComponents.FIRE_RESISTANT), "断裂英雄剑应防火");

        var food = new ItemStack(ModItems.RICE_CAKE.get()).getFoodProperties(null);
        helper.assertTrue(food != null && food.nutrition() == 6 && Math.abs(food.saturation() - 4.98f) < 0.0001f,
                "年糕应为 6 点饱食度、0.415 饱和度系数食物");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void tombBlockEntityRegistrationAndProgress(GameTestHelper helper) {
        helper.setBlock(new BlockPos(0, 1, 0), ModBlocks.DESERT_HERO_TOMB.get());
        DesertHeroTombBlockEntity tomb = helper.getBlockEntity(new BlockPos(0, 1, 0));
        helper.assertTrue(tomb != null, "荒漠英雄之墓应创建方块实体");

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.assertTrue(tomb.getQuestNumber(player) == 0, "初始任务阶段应为 0");

        tomb.setQuestNumber(player, 2);
        tomb.setTask1Complete(player, true);
        helper.assertTrue(tomb.getQuestNumber(player) == 2, "任务阶段应可持久化到玩家进度");
        helper.assertTrue(tomb.isTask1Complete(player), "任务 1 完成状态应可记录");
        helper.assertTrue(!tomb.isTask2Complete(player), "未完成的任务 2 不应被误标");
        helper.assertTrue(!tomb.allTasksComplete(player), "仅完成任务 1 时不应视为全部完成");
        helper.succeed();
    }
}
