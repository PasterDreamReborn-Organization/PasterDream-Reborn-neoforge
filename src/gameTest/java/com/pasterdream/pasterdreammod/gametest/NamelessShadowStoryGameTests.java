package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModMenus;
import com.pasterdream.pasterdreammod.world.effect.ProtectedRemovalEffect;
import com.pasterdream.pasterdreammod.world.effect.ShadowSpyonEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class NamelessShadowStoryGameTests {

    @GameTest(template = "empty")
    public static void registriesLoad(GameTestHelper helper) {
        ResourceLocation nameless = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "nameless");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.NAMELESS.get()).equals(nameless),
                "无名实体应以 nameless 注册");
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.get(nameless) == ModEntities.NAMELESS.get(),
                "无名实体应可从实体类型注册表查询");

        helper.assertTrue(BuiltInRegistries.MOB_EFFECT.getKey(ModEffects.SHADOW_SPYON.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_spyon")),
                "暗影窥视效果应已注册");
        helper.assertTrue(ModEffects.SHADOW_SPYON.get() instanceof ShadowSpyonEffect,
                "暗影窥视应为自定义效果");
        helper.assertTrue(ModEffects.SHADOW_SPYON.get() instanceof ProtectedRemovalEffect,
                "暗影窥视应受效果移除保护");

        helper.assertTrue(BuiltInRegistries.MENU.getKey(ModMenus.SHADOW_SELECT_END.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_select_end")),
                "灯与影选择菜单应已注册");

        for (var item : List.of(ModItems.TALENT_LIGHT.get(), ModItems.TALENT_SHADOW.get())) {
            helper.assertTrue(BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(PasterDreamMod.MOD_ID),
                    "天赋物品应已注册: " + item);
        }

        var egg = ModItems.NAMELESS_SPAWN_EGG.toStack();
        helper.assertTrue(egg.getItem() instanceof SpawnEggItem spawnEgg
                        && spawnEgg.getType(egg) == ModEntities.NAMELESS.get(),
                "无名刷怪蛋应生成无名实体");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void storyAdvancementsLoad(GameTestHelper helper) {
        var advancements = helper.getLevel().getServer().getAdvancements();
        for (String path : List.of(
                "story/shadow_npc_first_dialogue",
                "story/shadow_intrude_complete",
                "story/shadow_npc_second_dialogue",
                "story/shadow_choice",
                "story/talent_light",
                "story/talent_shadow")) {
            helper.assertTrue(advancements.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path)) != null,
                    "应存在进度: " + path);
        }
        helper.succeed();
    }
}
