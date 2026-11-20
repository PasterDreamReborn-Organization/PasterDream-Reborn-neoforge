package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.Config;
import com.pasterdream.pasterdreammod.helper.ShadowDifficultyHelper;
import com.pasterdream.pasterdreammod.event.LowSanSpawnHandler;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.san.San;
import com.pasterdream.pasterdreammod.capability.san.SanAuraHandler;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.sanbiomeratemanager.SanBiomeRateManager;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.block.doll.DollBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class SanGameTests {
    private static FakePlayer player(GameTestHelper helper) {
        return new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "san-test"));
    }

    @GameTest(template = "empty")
    public static void attachmentLifecycle(GameTestHelper helper) {
        var player = player(helper);
        var san = player.getData(ModAttachments.SAN);
        helper.assertTrue(san.getSanValue() == 100 && san.getMaxSanValue() == 100 && san.getIsSanEnabled(), "首次加入应为 100/100");
        SanHelper.setPlayerMaxSanAndSync(player, 125);
        SanHelper.addPlayerSanAndSync(player, 500);
        helper.assertTrue(san.getSanValue() == 125, "恢复超过基础上限");
        SanHelper.addPlayerSanAndSync(player, -500);
        SanHelper.setIsSanEnabled(player, false);
        SanHelper.addPlayerSanAndSync(player, 1);
        var restored = new San();
        restored.deserializeNBT(helper.getLevel().registryAccess(), san.serializeNBT(helper.getLevel().registryAccess()));
        helper.assertTrue(restored.getSanValue() == 0 && restored.getMaxSanValue() == 125 && !restored.getIsSanEnabled(), "存档或禁用语义错误");
        SanAuraHandler.onPlayerRespawn(new PlayerEvent.PlayerRespawnEvent(player, true));
        helper.assertTrue(san.getSanValue() == 0, "从末地返回应保留当前理智");
        SanAuraHandler.onPlayerRespawn(new PlayerEvent.PlayerRespawnEvent(player, false));
        helper.assertTrue(san.getSanValue() == 125 && !san.getIsSanEnabled(), "死亡重生应恢复基础上限并保留开关");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void auraAndThresholds(GameTestHelper helper) {
        var player = player(helper);
        player.setPos(Vec3.atCenterOf(helper.absolutePos(BlockPos.ZERO)));
        var tick = new PlayerTickEvent.Post(player);
        var san = player.getData(ModAttachments.SAN);
        helper.assertTrue(SanBiomeRateManager.getRate(ResourceLocation.parse("minecraft:deep_dark")) == -0.0008, "深暗群系数据未加载");
        double[] values = {95, 50, 30, 15, 5, 0.5};
        var effects = java.util.List.of(ModEffects.CHEER_UP, ModEffects.LETHARGY, ModEffects.TRANCE,
                ModEffects.INSANE, ModEffects.INSANE, ModEffects.INSANE);
        for (int i = 0; i < values.length; i++) {
            player.removeAllEffects();
            san.setSanValue(values[i]);
            SanAuraHandler.onPlayerTick(tick);
            var effect = player.getEffect(effects.get(i));
            helper.assertTrue(effect != null && effect.getDuration() == 20 && effect.getAmplifier() == Math.max(0, i - 3), "SAN 阈值错误 " + values[i]);
            helper.assertTrue(Math.abs(san.getSanValue() - values[i] - san.getSanRate()) < 0.000001, "HUD 变化率和每 tick 变化不一致");
        }
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void qymDoll(GameTestHelper helper) {
        checkDoll(helper, ModBlocks.QYM_DOLL.get());
    }

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void uuzDoll(GameTestHelper helper) {
        checkDoll(helper, ModBlocks.UUZ_DOLL.get());
    }

    private static void checkDoll(GameTestHelper helper, Block block) {
        var pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, block);
        var player = player(helper);
        player.setPos(Vec3.atCenterOf(helper.absolutePos(pos)));
        helper.getLevel().addNewPlayer(player);
        SanHelper.setPlayerSanAndSync(player, 50);
        helper.useBlock(pos, player);
        helper.assertTrue(SanHelper.getPlayerSan(player) == 51 && ((DollBlockEntity) helper.getBlockEntity(pos)).getAnimationState() == 1,
                "右键应恢复一点 SAN 并启动玩偶动画");
        helper.succeedWhen(() -> {
            var effect = player.getEffect(ModEffects.REST);
            helper.assertTrue(effect != null && effect.getAmplifier() == 1 && effect.getDuration() == 200, "玩偶应给予 200 tick 休憩 II");
            helper.assertTrue(Math.abs(player.getAttributeValue(ModAttributes.SAN_VARIABILITY) - 2.4) < 0.00001, "休憩 II 理智光环错误");
            helper.getLevel().removePlayerImmediately(player, Entity.RemovalReason.DISCARDED);
        });
    }

    @GameTest(template = "empty")
    public static void foodAndShadowMobs(GameTestHelper helper) {
        var player = player(helper);
        SanHelper.setPlayerSanAndSync(player, 10);
        var stew = new ItemStack(Items.RABBIT_STEW);
        NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Finish(player, stew, 0, new ItemStack(Items.BOWL)));
        helper.assertTrue(player.getEffect(ModEffects.COOK).getDuration() == 2400 && SanHelper.getPlayerSan(player) == 12, "兔肉煲应回 2 SAN 并给予两分钟料理");
        var bread = new ItemStack(Items.BREAD);
        player.getFoodData().setFoodLevel(20);
        player.getFoodData().setSaturation(0);
        helper.assertTrue(player.canEat(false), "料理应允许饱食时进食");
        NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Start(player, bread, InteractionHand.MAIN_HAND, 32));
        NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Finish(player, bread, 0, ItemStack.EMPTY));
        player.removeEffect(ModEffects.COOK);
        helper.assertTrue(player.getAbsorptionAmount() == 5, "移除料理后应保留 5 点溢出黄心");
        for (var type : java.util.List.of(ModEntities.TERRORBEAK, ModEntities.CRAZY_TERRORBEAK, ModEntities.WEAKNESS_TERRORBEAK, ModEntities.SHADOW_HAND)) {
            helper.assertTrue(type.get().is(ModEntityTypeTags.SHADOW_MOB), "默认低 SAN 实体应在暗影标签内");
        }
        var beak = ModEntities.WEAKNESS_TERRORBEAK.get().create(helper.getLevel());
        beak.playerTouch(player);
        helper.assertTrue(Math.abs(SanHelper.getPlayerSan(player) - 11.98) < 0.00001, "接触虚弱尖喙应扣 0.02 SAN");
        beak.die(player.damageSources().playerAttack(player));
        helper.assertTrue(Math.abs(SanHelper.getPlayerSan(player) - 18.98) < 0.00001, "低 SAN 击杀虚弱尖喙应回 7 点");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "sanNight")
    public static void nightSpawnAndBed(GameTestHelper helper) {
        var level = helper.getLevel();
        var player = player(helper);
        var pos = helper.absolutePos(new BlockPos(2, 2, 2));
        player.setPos(Vec3.atCenterOf(pos));
        level.addNewPlayer(player);
        long oldTime = level.getDayTime();
        var probabilities = Config.LOW_SAN_SPAWN_CRITICAL_PROBS.get();
        var entities = Config.LOW_SAN_SPAWN_CRITICAL_ENTITIES.get();
        var light = Config.LOW_SAN_SPAWN_MAX_LIGHT.get();
        var minimum = Config.LOW_SAN_SPAWN_RADIUS_MIN.get();
        var maximum = Config.LOW_SAN_SPAWN_RADIUS_MAX.get();
        try {
            level.setDayTime(6000);
            level.updateSkyBrightness();
            NeoForge.EVENT_BUS.post(new CanPlayerSleepEvent(player, pos, null));
            helper.assertTrue(!player.hasEffect(ModEffects.REST), "白天碰床不应获得休憩");
            level.setDayTime(18000);
            level.updateSkyBrightness();
            NeoForge.EVENT_BUS.post(new CanPlayerSleepEvent(player, pos, null));
            helper.assertTrue(player.getEffect(ModEffects.REST).getDuration() == 3600, "夜间床应给予三分钟休憩");
            Config.LOW_SAN_SPAWN_CRITICAL_PROBS.set(java.util.List.of(1.0, 1.0, 1.0, 1.0));
            Config.LOW_SAN_SPAWN_CRITICAL_ENTITIES.set(java.util.List.of("pasterdream:shadow_hand:1"));
            Config.LOW_SAN_SPAWN_MAX_LIGHT.set(15);
            Config.LOW_SAN_SPAWN_RADIUS_MIN.set(0.0);
            Config.LOW_SAN_SPAWN_RADIUS_MAX.set(1.0);
            for (BlockPos ground : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, -1, 1))) {
                level.setBlockAndUpdate(ground, Blocks.STONE.defaultBlockState());
                level.setBlockAndUpdate(ground.above(), Blocks.AIR.defaultBlockState());
                level.setBlockAndUpdate(ground.above(2), Blocks.AIR.defaultBlockState());
            }
            SanHelper.setPlayerSanAndSync(player, 0);
            ShadowDifficultyHelper.setPlayerDifficulty(player, 3);
            LowSanSpawnHandler.onPlayerTick(new PlayerTickEvent.Post(player));
            var spawned = level.getEntities(ModEntities.SHADOW_HAND.get(), new AABB(pos).inflate(4),
                    hand -> player.getUUID().equals(hand.getPersistentData().getUUID("ShadowSourcePlayer")));
            helper.assertTrue(spawned.size() == 1, "低 SAN 应生成一个带源玩家标记的暗影之手，实际 " + spawned.size());
            helper.assertTrue(spawned.getFirst().getMaxHealth() == 24, "困难暗影之手应有 24 生命，实际 " + spawned.getFirst().getMaxHealth());
            spawned.getFirst().discard();
        } finally {
            level.removePlayerImmediately(player, Entity.RemovalReason.DISCARDED);
            level.setDayTime(oldTime);
            level.updateSkyBrightness();
            Config.LOW_SAN_SPAWN_CRITICAL_PROBS.set(probabilities);
            Config.LOW_SAN_SPAWN_CRITICAL_ENTITIES.set(entities);
            Config.LOW_SAN_SPAWN_MAX_LIGHT.set(light);
            Config.LOW_SAN_SPAWN_RADIUS_MIN.set(minimum);
            Config.LOW_SAN_SPAWN_RADIUS_MAX.set(maximum);
        }
        helper.succeed();
    }
}
