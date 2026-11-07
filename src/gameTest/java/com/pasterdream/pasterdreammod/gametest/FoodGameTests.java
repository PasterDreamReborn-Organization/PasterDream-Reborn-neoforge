package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class FoodGameTests {

    @GameTest(template = "empty")
    public static void windRunnerAllowsElytraFlight(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "wind-runner-elytra"));
        ItemStack stack = new ItemStack(Items.STICK);
        helper.assertFalse(stack.canElytraFly(player), "无风行者效果时不应允许鞘翅飞行");
        player.addEffect(new MobEffectInstance(ModEffects.WIND_RUNNER, 200, 0));
        helper.assertTrue(stack.canElytraFly(player), "风行者效果应允许任意物品开启鞘翅飞行");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dyedreamPerfumeBlocksPhantomTargeting(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "dyedream-perfume"));
        var phantom = helper.spawnWithNoFreeWill(EntityType.PHANTOM, new BlockPos(1, 1, 1));

        ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get().finishUsingItem(
                new ItemStack(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get()), helper.getLevel(), player);
        helper.assertTrue(player.hasEffect(ModEffects.DYEDREAM_PERFUME), "饮用香水后应获得染梦香水效果");

        phantom.setTarget(player);
        helper.assertTrue(phantom.getTarget() == null, "染梦香水效果期间幻翼不应锁定玩家");

        player.removeEffect(ModEffects.DYEDREAM_PERFUME);
        phantom.setTarget(player);
        helper.assertTrue(phantom.getTarget() == player, "效果结束后幻翼应能重新锁定玩家");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void galaxyJellyGrantsSlowFalling(GameTestHelper helper) {
        FakePlayer player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "galaxy-jelly"));
        ItemStack stack = new ItemStack(ModItems.GALAXY_JELLY.get());
        ModItems.GALAXY_JELLY.get().finishUsingItem(stack, helper.getLevel(), player);
        helper.assertTrue(player.hasEffect(MobEffects.SLOW_FALLING),
                "食用星河果冻后应获得缓降效果");
        player.removeEffect(MobEffects.SLOW_FALLING);

        player.addEffect(new MobEffectInstance(ModEffects.WIND_RUNNER, 200, 0));
        ModItems.GALAXY_JELLY.get().finishUsingItem(new ItemStack(ModItems.GALAXY_JELLY.get()), helper.getLevel(), player);
        helper.assertTrue(player.hasEffect(MobEffects.SLOW_FALLING), "风行者强化弹射同样应给予缓降");
        helper.succeed();
    }
}
