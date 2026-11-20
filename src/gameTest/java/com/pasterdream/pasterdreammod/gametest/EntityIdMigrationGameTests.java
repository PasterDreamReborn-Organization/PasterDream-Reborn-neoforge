package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.compat.LegacyIdRemapper;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/**
 * 实体 ID 纠错的兼容性测试：旧配置 ID 在解析阶段被映射为新 ID，旧 ID 不再注册。
 */
@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class EntityIdMigrationGameTests {

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path);
    }

    @GameTest(template = "empty")
    public void legacyEntityIdsRemapToCurrentIds(GameTestHelper helper) {
        ResourceLocation legacyTerrorbeak = LegacyIdRemapper.entityType(id("weakeness_terrorbeak"));
        ResourceLocation legacyProjectile = LegacyIdRemapper.entityType(id("projectile_bone_wing_fire_ball_projectile"));

        helper.assertTrue(legacyTerrorbeak.equals(id("weakness_terrorbeak")),
                "旧实体 ID weakeness_terrorbeak 未映射为 weakness_terrorbeak");
        helper.assertTrue(legacyProjectile.equals(id("bone_wing_fire_ball_projectile")),
                "旧射弹 ID projectile_bone_wing_fire_ball_projectile 未映射为 bone_wing_fire_ball_projectile");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.containsKey(legacyTerrorbeak),
                "映射后的 weakness_terrorbeak 未注册");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.containsKey(legacyProjectile),
                "映射后的 bone_wing_fire_ball_projectile 未注册");
        helper.assertFalse(BuiltInRegistries.ENTITY_TYPE.containsKey(id("weakeness_terrorbeak")),
                "旧实体 ID weakeness_terrorbeak 仍然注册");
        helper.assertFalse(BuiltInRegistries.ENTITY_TYPE.containsKey(id("projectile_bone_wing_fire_ball_projectile")),
                "旧射弹 ID projectile_bone_wing_fire_ball_projectile 仍然注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void legacySpawnEggIdRemapsAndCurrentIdIsRegistered(GameTestHelper helper) {
        ResourceLocation legacyEgg = LegacyIdRemapper.item(id("weakeness_terrorbeak_spawn_egg"));

        helper.assertTrue(legacyEgg.equals(id("weakness_terrorbeak_spawn_egg")),
                "旧刷怪蛋 ID 未映射为 weakness_terrorbeak_spawn_egg");
        helper.assertTrue(ModItems.WEAKNESS_TERRORBEAK_SPAWN_EGG.isBound(),
                "weakness_terrorbeak_spawn_egg 未注册");
        helper.assertTrue(BuiltInRegistries.ITEM.containsKey(legacyEgg),
                "映射后的刷怪蛋物品未注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public void unrelatedIdsPassThroughUnchanged(GameTestHelper helper) {
        ResourceLocation normal = id("terrorbeak");
        helper.assertTrue(LegacyIdRemapper.entityType(normal).equals(normal),
                "非旧 ID 的实体 ID 不应被改动");
        helper.assertTrue(LegacyIdRemapper.item(normal).equals(normal),
                "非旧 ID 的物品 ID 不应被改动");
        helper.succeed();
    }
}
