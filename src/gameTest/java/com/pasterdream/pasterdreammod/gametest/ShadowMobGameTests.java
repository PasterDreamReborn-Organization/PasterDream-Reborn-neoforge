package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowMobGameTests {
    @GameTest(template = "empty")
    public static void lampShadowMobsRegistered(GameTestHelper helper) {
        List<net.minecraft.world.entity.EntityType<?>> types = List.of(
                ModEntities.SHADOW_GOLEM.get(),
                ModEntities.SHADOW_GHOST.get(),
                ModEntities.SHADOW_SQUEAL_GHOST.get(),
                ModEntities.WAILING_SHADOW_GHOST.get(),
                ModEntities.FRIENDLY_SHADOW_GHOST.get(),
                ModEntities.BLACK_BEETLE.get(),
                ModEntities.BLACK_BEETLE_MOTHER.get(),
                ModEntities.SQUEAL_WAVE_PROJECTILE.get());
        for (var type : types) {
            helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(type) != null,
                    "灯影之下的实体类型应已注册: " + type);
        }

        helper.assertTrue(ModEntities.SHADOW_GOLEM.get().is(ModEntityTypeTags.SHADOW_MOB), "阴影傀儡应属于暗影生物标签");
        helper.assertTrue(ModEntities.SHADOW_GHOST.get().is(ModEntityTypeTags.SHADOW_MOB), "怨魂应属于暗影生物标签");
        helper.assertTrue(ModEntities.SHADOW_SQUEAL_GHOST.get().is(ModEntityTypeTags.SHADOW_MOB), "尖啸怨魂应属于暗影生物标签");
        helper.assertTrue(ModEntities.WAILING_SHADOW_GHOST.get().is(ModEntityTypeTags.SHADOW_MOB), "悲泣尖啸怨魂应属于暗影生物标签");
        helper.assertTrue(ModEntities.FRIENDLY_SHADOW_GHOST.get().is(ModEntityTypeTags.SHADOW_MOB), "友善怨魂应属于暗影生物标签");
        helper.assertTrue(ModEntities.BLACK_BEETLE.get().is(ModEntityTypeTags.SHADOW_MOB), "厚翅甲虫应属于暗影生物标签");
        helper.assertTrue(ModEntities.BLACK_BEETLE_MOTHER.get().is(ModEntityTypeTags.SHADOW_MOB), "厚翅甲虫之母应属于暗影生物标签");

        helper.assertTrue(ModItems.SHADOW_GOLEM_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem, "阴影傀儡刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem, "厚翅甲虫之母刷怪蛋应为原版 SpawnEggItem");

        var wailing = ModEntities.WAILING_SHADOW_GHOST.get().create(helper.getLevel());
        helper.assertTrue(wailing != null, "悲泣尖啸怨魂应能由刷怪蛋创建");
        helper.assertTrue("wailing_shadow_ghost".equals(wailing.getTexture()), "悲泣尖啸怨魂应使用独立纹理");
        helper.succeed();
    }
}
