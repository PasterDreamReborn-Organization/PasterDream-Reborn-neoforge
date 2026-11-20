package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.world.block.BreakWindCurtainBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class BreakWindCurtainGameTests {
    private static final String PASS_COUNT_KEY = "pasterdream:break_wind_curtain_passes";

    /** entityInside 每刻会对每个相交的幕帐方块各调用一次，同一刻多次相交只应计数一次。 */
    @GameTest(template = "empty")
    public static void overlappingBlocksCountOnce(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 2, 1));
        var block = (BreakWindCurtainBlock) ModBlocks.BREAK_WIND_CURTAIN.get();
        var state = block.defaultBlockState();
        var player = glidingPlayer(helper, pos);

        block.entityInside(state, level, pos, player);
        block.entityInside(state, level, pos.east(), player);
        block.entityInside(state, level, pos.above(), player);

        helper.assertTrue(passes(player) == 1, "同一刻多次相交应只计数一次，实际 " + passes(player));
        helper.succeed();
    }

    /** 未处于鞘翅滑翔状态时穿过幕帐不应计数。 */
    @GameTest(template = "empty")
    public static void nonGlidingNotCounted(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 2, 1));
        var block = (BreakWindCurtainBlock) ModBlocks.BREAK_WIND_CURTAIN.get();
        var player = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "curtain-walk"));
        player.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

        block.entityInside(block.defaultBlockState(), level, pos, player);

        helper.assertTrue(passes(player) == 0, "未滑翔时不应计数，实际 " + passes(player));
        helper.succeed();
    }

    /** 两次计数之间至少间隔 20 刻，冷却内再次进入不重复计数。 */
    @GameTest(template = "empty")
    public static void passCountCooldown(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(1, 2, 1));
        var block = (BreakWindCurtainBlock) ModBlocks.BREAK_WIND_CURTAIN.get();
        var state = block.defaultBlockState();
        var player = glidingPlayer(helper, pos);

        helper.startSequence()
                .thenExecute(() -> block.entityInside(state, level, pos, player))
                .thenIdle(5)
                .thenExecute(() -> {
                    block.entityInside(state, level, pos, player);
                    helper.assertTrue(passes(player) == 1, "20 刻冷却内再次进入不应计数，实际 " + passes(player));
                })
                .thenIdle(25)
                .thenExecute(() -> {
                    block.entityInside(state, level, pos, player);
                    helper.assertTrue(passes(player) == 2, "冷却结束后再次进入应计数，实际 " + passes(player));
                })
                .thenSucceed();
    }

    private static FakePlayer glidingPlayer(GameTestHelper helper, BlockPos pos) {
        var player = new FakePlayer(helper.getLevel(), new GameProfile(UUID.randomUUID(), "curtain-glide"));
        player.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        // 悬空且未被骑乘时 startFallFlying 即可满足 isFallFlying
        player.startFallFlying();
        return player;
    }

    private static int passes(FakePlayer player) {
        return player.getPersistentData().getInt(PASS_COUNT_KEY);
    }
}
