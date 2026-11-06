package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.world.skill.BlinkSkill;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class BlinkGameTests {

    @GameTest(template = "empty")
    public static void blinkMovementAndCooldown(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "blink-movement"));
        player.getFoodData().setFoodLevel(20);
        player.setYRot(0);
        player.setXRot(0);
        BlinkSkill.execute(player);
        helper.assertTrue(player.getDeltaMovement().z > 0.9, "瞬身术应沿视线移动");
        helper.assertTrue(player.getEffect(ModEffects.BLINK_COOLDOWN).getDuration() == 50,
                "普通瞬身术冷却应为 50 tick");
        helper.assertTrue(player.getEffect(ModEffects.EVASION).getDuration() == 6,
                "瞬身术回避应持续 6 tick");
        player.setDeltaMovement(0, 0, 0);
        BlinkSkill.execute(player);
        helper.assertTrue(player.getDeltaMovement().lengthSqr() == 0, "冷却中不应再次位移");

        player.removeEffect(ModEffects.BLINK_COOLDOWN);
        player.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.ELYTRA));
        player.getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION).setBaseValue(0.5);
        player.getAttribute(ModAttributes.BLINK_RANGE).setBaseValue(2.0);
        BlinkSkill.execute(player);
        helper.assertTrue(player.getEffect(ModEffects.BLINK_COOLDOWN).getDuration() == 50,
                "鞘翅冷却 100 tick 经 50% 缩减后应为 50 tick");
        helper.assertTrue(player.getDeltaMovement().z > 1.9, "距离属性应影响位移");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void blinkConditionsAndCreative(GameTestHelper helper) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "blink-conditions"));
        player.getFoodData().setFoodLevel(6);
        BlinkSkill.execute(player);
        helper.assertFalse(player.hasEffect(ModEffects.EVASION), "饱食度不超过 6 时应拒绝瞬身术");
        helper.assertFalse(player.hasEffect(ModEffects.BLINK_COOLDOWN), "失败时不应产生冷却");

        var creative = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "blink-creative"));
        creative.getAbilities().instabuild = true;
        BlinkSkill.execute(creative);
        helper.assertTrue(creative.hasEffect(ModEffects.EVASION), "创造模式仍应获得回避");
        helper.assertFalse(creative.hasEffect(ModEffects.BLINK_COOLDOWN), "创造模式不应产生冷却");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void evasionConsumesOneLayerPerHit(GameTestHelper helper) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        float initialHealth = player.getHealth();
        player.addEffect(new MobEffectInstance(ModEffects.EVASION, 20, 1));
        helper.assertFalse(player.removeEffect(ModEffects.EVASION), "外部移除应被保护");
        helper.assertFalse(player.hurt(helper.getLevel().damageSources().generic(), 4.0f),
                "第一击应被回避取消");
        helper.assertTrue(player.getEffect(ModEffects.EVASION).getAmplifier() == 0,
                "第一击只消耗一层回避");
        helper.assertTrue(player.getHealth() == initialHealth, "第一击不应减少生命值");
        helper.assertTrue(player.invulnerableTime == 20, "回避后应保留原版受击无敌时间");
        helper.assertFalse(player.hurt(helper.getLevel().damageSources().generic(), 4.0f),
                "贴身连续碰撞不应在下一 tick 造成伤害");
        helper.assertTrue(player.getEffect(ModEffects.EVASION).getAmplifier() == 0,
                "无敌时间内不应额外消耗回避层数");
        player.invulnerableTime = 0;
        helper.assertFalse(player.hurt(helper.getLevel().damageSources().generic(), 4.0f),
                "第二击应被回避取消");
        helper.assertFalse(player.hasEffect(ModEffects.EVASION), "第二击后回避应耗尽");
        helper.assertTrue(player.getHealth() == initialHealth, "第二击不应减少生命值");
        helper.assertFalse(player.hurt(helper.getLevel().damageSources().generic(), 4.0f),
                "回避耗尽后仍在受击无敌时间内的碰撞不应掉血");
        helper.assertTrue(player.getHealth() == initialHealth, "贴身连续碰撞不应减少生命值");
        player.invulnerableTime = 0;
        helper.assertTrue(player.hurt(helper.getLevel().damageSources().generic(), 4.0f),
                "回避耗尽后的攻击应正常造成伤害");
        helper.assertTrue(player.getHealth() < initialHealth, "回避耗尽后生命值应下降");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void slimeContactDoesNotRepeatDamage(GameTestHelper helper) {
        var slime = helper.spawnWithNoFreeWill(EntityType.SLIME, new BlockPos(1, 2, 1));
        slime.setSize(2, true);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(slime.getX(), slime.getY(), slime.getZ());
        player.addEffect(new MobEffectInstance(ModEffects.EVASION, 20));
        float initialHealth = player.getHealth();

        slime.playerTouch(player);
        helper.assertFalse(player.hasEffect(ModEffects.EVASION), "史莱姆第一次碰撞应消耗回避");
        helper.assertTrue(player.getHealth() == initialHealth, "史莱姆第一次碰撞不应造成伤害");
        slime.playerTouch(player);
        helper.assertTrue(player.getHealth() == initialHealth, "持续贴身碰撞不应紧接着造成伤害");

        player.invulnerableTime = 0;
        slime.playerTouch(player);
        helper.assertTrue(player.getHealth() < initialHealth, "受击无敌时间结束后史莱姆应恢复正常伤害");
        helper.succeed();
    }
}
