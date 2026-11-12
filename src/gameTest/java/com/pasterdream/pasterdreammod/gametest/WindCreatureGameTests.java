package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WindCreatureGameTests {
    @GameTest(template = "empty")
    public static void windCreaturesRegistered(GameTestHelper helper) {
        List<net.minecraft.world.entity.EntityType<?>> types = List.of(
                ModEntities.BONE_WING.get(),
                ModEntities.ASH_BONE_WING.get(),
                ModEntities.BONE_WING_FIRE_BALL_PROJECTILE.get(),
                ModEntities.THUNDERCLOUD.get(),
                ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get(),
                ModEntities.LIGHTNING_PROJECTILE.get(),
                ModEntities.JELLYFISH.get());
        for (var type : types) {
            helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(type) != null,
                    "风之旅途生物实体类型应已注册: " + type);
        }

        helper.assertTrue(ModItems.BONE_WING_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem,
                "骨翼刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.ASH_BONE_WING_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem,
                "余烬骨翼刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.THUNDERCLOUD_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem,
                "雷云刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.HIGHVOLTAGE_THUNDERCLOUD_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem,
                "高压雷云刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.JELLYFISH_SPAWN_EGG.get() instanceof net.minecraft.world.item.SpawnEggItem,
                "水母刷怪蛋应为原版 SpawnEggItem");

        boolean boneWingUndead = false;
        boolean ashBoneWingUndead = false;
        for (var holder : BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(EntityTypeTags.UNDEAD)) {
            if (holder.value() == ModEntities.BONE_WING.get()) boneWingUndead = true;
            if (holder.value() == ModEntities.ASH_BONE_WING.get()) ashBoneWingUndead = true;
        }
        helper.assertTrue(boneWingUndead, "骨翼应属于亡灵实体标签");
        helper.assertTrue(ashBoneWingUndead, "余烬骨翼应属于亡灵实体标签");

        var jellyfish = ModEntities.JELLYFISH.get().create(helper.getLevel());
        helper.assertTrue(jellyfish != null, "水母应能创建实例");
        helper.assertTrue("jellyfish".equals(jellyfish.getTexture()), "水母应使用独立纹理");

        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windBiomeSpawnsRegistered(GameTestHelper helper) {
        var biome = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME)
                .getOrThrow(ModBiomes.WIND_MOOR_ARCHIPELAGO).value();
        var monsters = biome.getMobSettings().getMobs(MobCategory.MONSTER);
        var creatures = biome.getMobSettings().getMobs(MobCategory.CREATURE);
        helper.assertTrue(monsters.unwrap().stream().anyMatch(data -> data.type == ModEntities.BONE_WING.get()),
                "风泊群岛应登记骨翼生成");
        helper.assertTrue(monsters.unwrap().stream().anyMatch(data -> data.type == ModEntities.ASH_BONE_WING.get()),
                "风泊群岛应登记余烬骨翼生成");
        helper.assertTrue(monsters.unwrap().stream().anyMatch(data -> data.type == ModEntities.THUNDERCLOUD.get()),
                "风泊群岛应登记雷云生成");
        helper.assertTrue(monsters.unwrap().stream().anyMatch(data -> data.type == ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get()),
                "风泊群岛应登记高压雷云生成");
        helper.assertTrue(creatures.unwrap().stream().anyMatch(data -> data.type == ModEntities.JELLYFISH.get()),
                "风泊群岛应登记水母生成");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windStoryAdvancementsRegistered(GameTestHelper helper) {
        var advancements = helper.getLevel().getServer().getAdvancements();
        List<String> paths = List.of(
                "enter_wind_journey",
                "condense_wind_dew",
                "crystal_wind_runner",
                "firefly_in_jar",
                "sea_bloom",
                "congeal_wind",
                "congeal_wind_iron",
                "lightning_in_bottle",
                "wingless_bird",
                "break_wind_curtain",
                "break_wind_curtain_hundred",
                "angel_beats",
                "wind_follow",
                "wind_deadwind_flight",
                "wind_tailwind_flight");
        for (String path : paths) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "story/" + path);
            helper.assertTrue(advancements.get(id) != null, "风之旅途 story 进度应已加载: " + id);
        }
        helper.succeed();
    }
}
