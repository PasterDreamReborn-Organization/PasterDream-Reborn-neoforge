package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class MobBehaviorGameTests {
    @GameTest(template = "empty", batch = "mobBehavior")
    public static void conflictMarkGlowsAndDiscardedTargetsAreCleared(GameTestHelper helper) {
        var target = helper.spawn(EntityType.PIG, 1, 2, 1);
        var skeleton = helper.spawn(EntityType.SKELETON, 2, 2, 1);
        target.addEffect(new MobEffectInstance(ModEffects.CONFLICT_MARK, 40));
        helper.assertTrue(target.isCurrentlyGlowing(), "纷争标记应让目标发光");
        target.removeEffect(ModEffects.CONFLICT_MARK);
        helper.assertTrue(!target.isCurrentlyGlowing(), "纷争标记移除后应停止发光");

        skeleton.setTarget(target);
        helper.assertTrue(skeleton.getTarget() == target, "骷髅应能获取仍存活的目标");
        target.discard();
        skeleton.tick();
        helper.assertTrue(skeleton.getTarget() == null, "目标移除后应清空引用");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "mobBehavior")
    public static void dyedreamPerfumeBlocksPhantomTargeting(GameTestHelper helper) {
        var phantom = helper.spawn(EntityType.PHANTOM, 1, 2, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.addEffect(new MobEffectInstance(ModEffects.DYEDREAM_PERFUME, 40));
        phantom.setTarget(player);
        helper.assertTrue(phantom.getTarget() == null, "幻翼应跳过带有染梦香水效果的玩家");
        player.removeEffect(ModEffects.DYEDREAM_PERFUME);
        phantom.setTarget(player);
        helper.assertTrue(phantom.getTarget() == player, "香水结束后幻翼应恢复正常索敌");
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "mobBehavior")
    public static void sheepEatsStemGrassAndDyedreamGrassBlock(GameTestHelper helper) {
        var sheep = helper.spawn(EntityType.SHEEP, 1, 2, 1);
        var pos = sheep.blockPosition();
        helper.getLevel().setBlock(pos.below(), ModBlocks.DYEDREAM_GRASS_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);
        helper.getLevel().setBlock(pos, ModBlocks.STEM_GRASS.get().defaultBlockState(), Block.UPDATE_ALL);
        var goal = new EatBlockGoal(sheep);
        eat(goal);
        helper.assertTrue(helper.getLevel().getBlockState(pos).isAir()
                        && helper.getLevel().getBlockState(pos.below()).is(ModBlocks.DYEDREAM_GRASS_BLOCK.get()),
                "羊应先吃掉茎草");
        eat(goal);
        helper.assertBlockPresent(ModBlocks.DYEDREAM_DIRT.get(), new BlockPos(1, 1, 1));
        helper.succeed();
    }

    private static void eat(EatBlockGoal goal) {
        goal.start();
        while (goal.canContinueToUse()) {
            goal.tick();
        }
        goal.stop();
    }
}
