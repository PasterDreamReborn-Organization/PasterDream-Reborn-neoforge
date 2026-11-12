package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModDimensionTypes;
import com.pasterdream.pasterdreammod.worldgen.dimension.ModNoiseSettings;
import com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class AaroncosArenaGameTests {
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path);
    }

    @GameTest(template = "empty")
    public static void arenaContentRegistered(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.AARONCOS_EYE.get()).equals(id("aaroncos_eye")),
                "亚伦柯斯之眼方块应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.AARONCOS_HAND_CHEST.get()).equals(id("aaroncos_hand_chest")),
                "亚伦柯斯手箱方块应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.AARONCOS_ARENA_PORTALS.get()).equals(id("aaroncos_arena_portals")),
                "亚伦柯斯竞技场传送门方块应已注册");
        helper.assertTrue(BuiltInRegistries.ITEM.getKey(ModItems.AARONCOS_EYE.get()).equals(id("aaroncos_eye")),
                "亚伦柯斯之眼物品应已注册");
        helper.assertTrue(BuiltInRegistries.ITEM.getKey(ModItems.AARONCOS_ARENA_CREATE.get()).equals(id("aaroncos_arena_create")),
                "亚伦柯斯竞技场生成器应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.AARONCOS_EYE.get()).equals(id("aaroncos_eye")),
                "亚伦柯斯之眼方块实体应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.AARONCOS_HAND_CHEST.get()).equals(id("aaroncos_hand_chest")),
                "亚伦柯斯手箱方块实体应已注册");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void arenaWorldgenAndTemplatesLoad(GameTestHelper helper) {
        var registries = helper.getLevel().registryAccess();
        helper.assertTrue(registries.lookupOrThrow(Registries.DIMENSION_TYPE)
                        .get(ModDimensionTypes.AARONCOS_ARENA_WORLD).isPresent(),
                "竞技场维度类型应已注册");
        helper.assertTrue(registries.lookupOrThrow(Registries.NOISE_SETTINGS)
                        .get(ModNoiseSettings.AARONCOS_ARENA_WORLD).isPresent(),
                "竞技场噪声设置应已注册");
        helper.assertTrue(registries.lookupOrThrow(Registries.BIOME)
                        .get(ModBiomes.AARONCOS_ARENA).isPresent(),
                "竞技场群系应已注册");
        helper.assertTrue(registries.lookupOrThrow(Registries.STRUCTURE)
                        .get(ResourceKey.create(Registries.STRUCTURE, id("aaroncos_arena_portal"))).isPresent(),
                "竞技场传送门结构应已注册");
        helper.assertTrue(registries.lookupOrThrow(Registries.STRUCTURE_SET)
                        .get(ResourceKey.create(Registries.STRUCTURE_SET, id("aaroncos_arena_portal"))).isPresent(),
                "竞技场传送门结构集应已注册");
        var manager = helper.getLevel().getStructureManager();
        for (String name : List.of("aaroncos_arena", "aaroncos_arena_portal")) {
            helper.assertTrue(manager.get(id(name)).isPresent(), "竞技场结构模板应可加载: " + name);
        }
        helper.succeed();
    }
}
