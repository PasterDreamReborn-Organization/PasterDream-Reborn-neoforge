package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class StructureWaterlogGameTests {

    @GameTest(template = "empty")
    public static void placeInWorldDoesNotWaterlog(GameTestHelper helper) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    helper.setBlock(new BlockPos(x, y, z), Blocks.WATER);
                }
            }
        }

        ServerLevel level = helper.getLevel();
        StructureTemplate template = singleBlockTemplate(level, Blocks.OAK_STAIRS.defaultBlockState());
        BlockPos target = new BlockPos(1, 1, 1);
        StructurePlaceSettings settings = new StructurePlaceSettings().setLiquidSettings(LiquidSettings.APPLY_WATERLOGGING);

        boolean placed = template.placeInWorld(level, helper.absolutePos(target), helper.absolutePos(target),
                settings, level.random, 2);
        helper.assertTrue(placed, "oak stairs should be placed");

        BlockState state = helper.getBlockState(target);
        helper.assertTrue(state.is(Blocks.OAK_STAIRS), "oak stairs should occupy the target position, got " + state);
        helper.assertFalse(state.getValue(BlockStateProperties.WATERLOGGED), "stairs must not be waterlogged");
        helper.assertTrue(state.getFluidState().isEmpty(), "waterlogged stairs would carry a fluid state");
        helper.assertTrue(settings.shouldApplyWaterlogging(), "caller settings must not be mutated");
        helper.succeed();
    }

    private static StructureTemplate singleBlockTemplate(ServerLevel level, BlockState state) {
        CompoundTag tag = new CompoundTag();

        ListTag size = new ListTag();
        size.add(IntTag.valueOf(1));
        size.add(IntTag.valueOf(1));
        size.add(IntTag.valueOf(1));
        tag.put("size", size);

        ListTag palette = new ListTag();
        palette.add(NbtUtils.writeBlockState(state));
        tag.put("palette", palette);

        ListTag pos = new ListTag();
        pos.add(IntTag.valueOf(0));
        pos.add(IntTag.valueOf(0));
        pos.add(IntTag.valueOf(0));

        CompoundTag block = new CompoundTag();
        block.put("pos", pos);
        block.putInt("state", 0);

        ListTag blocks = new ListTag();
        blocks.add(block);
        tag.put("blocks", blocks);

        StructureTemplate template = new StructureTemplate();
        template.load(level.registryAccess().lookupOrThrow(Registries.BLOCK), tag);
        return template;
    }
}
