package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class FrozenEffectGameTests {

    @GameTest(template = "empty")
    public static void frozenExpiryRestoresAi(GameTestHelper helper) {
        Zombie zombie = helper.spawn(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
        zombie.addEffect(new MobEffectInstance(ModEffects.FROZEN, 5, 0));
        helper.assertTrue(zombie.isNoAi(), "冰冻效果应撤除 AI");
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(!zombie.isNoAi(), "冰冻效果到期后应恢复 AI");
            helper.succeed();
        });
    }
}
