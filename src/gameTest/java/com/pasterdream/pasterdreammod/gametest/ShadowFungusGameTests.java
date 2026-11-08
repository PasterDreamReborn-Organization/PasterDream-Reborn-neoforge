package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowFungusGameTests {
    @GameTest(template = "empty", batch = "shadowFungusGrowth")
    public static void shadowFungusBonemeal(GameTestHelper helper) {
        var level = helper.getLevel();
        var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        helper.assertTrue(registry.get(ModConfiguredFeatures.SHADOW_FUNGUS_TREE) != null,
                "阴影蘑菇生长特征应已注册");
        BlockPos pos = helper.absolutePos(new BlockPos(1, 10, 1));
        level.setBlock(pos.below(), ModBlocks.SHADOW_NYLIUM.get().defaultBlockState(), 3);
        level.setBlock(pos, ModBlocks.SHADOW_FUNGUS.get().defaultBlockState(), 3);
        ((BonemealableBlock) ModBlocks.SHADOW_FUNGUS.get()).performBonemeal(level,
                RandomSource.create(48), pos, level.getBlockState(pos));
        int stems = 0;
        for (BlockPos target : BlockPos.betweenClosed(pos, pos.above(12)))
            if (level.getBlockState(target).is(ModBlocks.SHADOW_STEM.get())) stems++;
        helper.assertTrue(stems > 0, "阴影蘑菇骨粉催大应生成阴影菌柄");
        helper.succeed();
    }
}
