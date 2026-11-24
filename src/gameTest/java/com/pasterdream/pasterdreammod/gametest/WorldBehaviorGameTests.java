package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WorldBehaviorGameTests {
    @GameTest(template = "empty", batch = "worldBehavior")
    public static void weatherSetFromSecondaryDimensionReachesOverworld(GameTestHelper helper) {
        var server = helper.getLevel().getServer();
        var secondary = server.getLevel(Level.END);
        helper.assertTrue(secondary != null, "测试存档应加载末地");
        var data = server.getWorldData().overworldData();
        int clearTime = data.getClearWeatherTime();
        int rainTime = data.getRainTime();
        int thunderTime = data.getThunderTime();
        boolean raining = data.isRaining();
        boolean thundering = data.isThundering();
        try {
            secondary.setWeatherParameters(0, 200, true, true);
            helper.assertTrue(data.isRaining() && data.isThundering() && data.getRainTime() == 200,
                    "在非主世界设置天气应更新主世界天气");
        } finally {
            data.setClearWeatherTime(clearTime);
            data.setRainTime(rainTime);
            data.setThunderTime(thunderTime);
            data.setRaining(raining);
            data.setThundering(thundering);
        }
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "worldBehavior")
    public static void structureBoundsSurviveSaveAndPacketRoundTrip(GameTestHelper helper) {
        var block = new StructureBlockEntity(BlockPos.ZERO, Blocks.STRUCTURE_BLOCK.defaultBlockState());
        var tag = new CompoundTag();
        tag.putInt("sizeX", 80);
        tag.putInt("posY", -90);
        block.loadWithComponents(tag, helper.getLevel().registryAccess());
        helper.assertTrue(block.getStructureSize().getX() == 80 && block.getStructurePos().getY() == -90,
                "结构方块应保留大于原版限制的尺寸与偏移");

        var packet = new ServerboundSetStructureBlockPacket(BlockPos.ZERO, StructureBlockEntity.UpdateType.UPDATE_DATA,
                StructureMode.SAVE, "pasterdream:test_structure", new BlockPos(120, -140, 88),
                new Vec3i(80, 160, 127), Mirror.NONE, Rotation.NONE, "", true, false, true, 1.0F, 0L);
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            ServerboundSetStructureBlockPacket.STREAM_CODEC.encode(buffer, packet);
            var decoded = ServerboundSetStructureBlockPacket.STREAM_CODEC.decode(buffer);
            helper.assertTrue(decoded.getOffset().equals(new BlockPos(120, -128, 88))
                            && decoded.getSize().equals(new Vec3i(80, 127, 127)),
                    "结构数据包应在有符号字节范围内保留尺寸，并截断超限值");
        } finally {
            buffer.release();
        }
        helper.succeed();
    }

    @GameTest(template = "empty", batch = "worldBehavior")
    public static void waterFlowPreservesTaggedNonCollidingBlock(GameTestHelper helper) {
        var protectedPos = new BlockPos(1, 2, 1);
        var torchPos = new BlockPos(4, 2, 1);
        helper.setBlock(protectedPos, ModBlocks.BIG_BUBBLE.get());
        helper.setBlock(torchPos.below(), Blocks.STONE);
        helper.setBlock(torchPos, Blocks.TORCH);
        runWaterTickAbove(helper, protectedPos);
        runWaterTickAbove(helper, torchPos);
        helper.assertBlockPresent(ModBlocks.BIG_BUBBLE.get(), protectedPos);
        helper.assertBlockPresent(Blocks.WATER, torchPos);
        helper.succeed();
    }

    private static void runWaterTickAbove(GameTestHelper helper, BlockPos position) {
        var source = position.above();
        helper.setBlock(source, Blocks.WATER);
        var worldPosition = helper.absolutePos(source);
        Fluids.WATER.tick(helper.getLevel(), worldPosition, helper.getLevel().getFluidState(worldPosition));
    }
}
