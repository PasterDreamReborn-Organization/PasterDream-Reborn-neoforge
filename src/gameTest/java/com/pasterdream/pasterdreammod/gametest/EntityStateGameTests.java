package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class EntityStateGameTests {
    @GameTest(template = "empty")
    public static void foxFireAndShakingCrystalArePlainEntities(GameTestHelper helper) {
        var level = helper.getLevel();
        var foxFire = ModEntities.FOX_FIRE.get().create(level);
        helper.assertTrue(foxFire != null && !LivingEntity.class.isInstance(foxFire), "狐火应为普通实体，不再拥有血量");
        helper.assertFalse(foxFire.hurt(level.damageSources().generic(), 1.0F), "狐火不应受到任何伤害");
        helper.assertTrue(foxFire.getBoundingBoxForCulling().getXsize() > foxFire.getBoundingBox().getXsize(),
                "狐火剔除箱应放大到效果半径");

        var crystal = ModEntities.SHAKING_CRYSTAL.get().create(level);
        helper.assertTrue(crystal != null && !LivingEntity.class.isInstance(crystal), "撼地水晶应为普通实体，不再拥有血量");
        helper.assertFalse(crystal.hurt(level.damageSources().generic(), 1.0F), "撼地水晶不应受到任何伤害");
        helper.assertFalse(crystal.isPushable(), "撼地水晶不应被推动");
        helper.assertFalse(crystal.isPickable(), "撼地水晶不应被准星选中");
        helper.assertFalse(crystal.isPushedByFluid(), "撼地水晶不应被流体推动");
        helper.assertFalse(crystal.causeFallDamage(3.0F, 1.0F, level.damageSources().fall()), "撼地水晶不应受到摔落伤害");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shakingCrystalLifetimeStillDamages(GameTestHelper helper) {
        var level = helper.getLevel();
        var center = Vec3.atCenterOf(helper.absolutePos(new BlockPos(2, 2, 2)));
        level.setBlockAndUpdate(helper.absolutePos(new BlockPos(2, 1, 2)), Blocks.STONE.defaultBlockState());

        var owner = helper.makeMockPlayer(GameType.SURVIVAL);
        owner.moveTo(center.x - 3.0, center.y, center.z);
        level.addFreshEntity(owner);
        var crystal = ModEntities.SHAKING_CRYSTAL.get().create(level);
        crystal.moveTo(center.x, center.y, center.z);
        crystal.setOwner(owner);
        crystal.setAttackDamage(2.0F);
        level.addFreshEntity(crystal);

        var zombie = EntityType.ZOMBIE.create(level);
        zombie.moveTo(center.x + 1.5, center.y, center.z);
        zombie.setNoAi(true);
        level.addFreshEntity(zombie);

        for (int i = 0; i < 62 && !crystal.isRemoved(); i++) {
            crystal.tick();
        }

        helper.assertTrue(crystal.isRemoved(), "撼地水晶应在存活时间结束后消失并爆炸");
        helper.assertTrue(zombie.getHealth() < zombie.getMaxHealth(), "撼地水晶爆发应伤害附近非己方生物");
        zombie.discard();
        helper.succeed();
    }
}
