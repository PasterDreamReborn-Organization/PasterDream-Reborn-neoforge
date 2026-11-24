package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.UUID;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class RecipeUnlockGameTests {
    @GameTest(template = "empty", batch = "recipeUnlock")
    public static void breakingClayPotUnlocksStonecuttingRecipe(GameTestHelper helper) {
        var level = helper.getLevel();
        // NeoForge 为普通 FakePlayer 提供空进度实现，测试需要走真实玩家的配方奖励链路。
        var player = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "clay-pot-test")) {
            @Override
            public boolean isFakePlayer() {
                return false;
            }
        };
        var recipe = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_clay_pot_from_stonecutting");
        var pos = new BlockPos(1, 1, 1);
        var worldPos = helper.absolutePos(pos);
        try {
            helper.setBlock(pos, Blocks.CLAY);
            helper.assertTrue(player.gameMode.destroyBlock(worldPos) && !player.getRecipeBook().contains(recipe),
                    "破坏黏土块不应解锁阴影陶罐配方");
            helper.setBlock(pos, ModBlocks.CLAY_POT.get());
            helper.assertTrue(player.gameMode.destroyBlock(worldPos) && player.getRecipeBook().contains(recipe),
                    "未持精准采集工具破坏陶罐后应解锁阴影陶罐配方");
        } finally {
            player.getAdvancements().stopListening();
        }
        helper.succeed();
    }
}
