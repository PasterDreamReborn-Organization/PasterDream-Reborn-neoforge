package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.shadowhandtrap.ShadowHandTrapBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowHandTrapGameTests {

    @GameTest(template = "empty", timeoutTicks = 100)
    public static void waitsForAnimationBeforeSelfDestruct(GameTestHelper helper) {
        BlockPos pos = new BlockPos(0, 1, 0);
        helper.setBlock(pos, ModBlocks.SHADOW_HAND_TRAP.get());
        BlockState state = helper.getBlockState(pos);
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        ((ShadowHandTrapBlock) state.getBlock()).entityInside(
                state, helper.getLevel(), helper.absolutePos(pos), player);
        helper.assertTrue(helper.getBlockState(pos).is(ModBlocks.SHADOW_HAND_TRAP.get()),
                "触发后陷阱应立即保留");

        helper.runAfterDelay(20, () -> helper.assertTrue(
                helper.getBlockState(pos).is(ModBlocks.SHADOW_HAND_TRAP.get()),
                "攻击动画（24 tick）结束前陷阱不应自毁"));

        helper.runAfterDelay(30, () -> {
            helper.assertTrue(helper.getBlockState(pos).isAir(), "动画结束后陷阱应自毁");
            helper.succeed();
        });
    }
}
