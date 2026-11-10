package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.shadowbed.ShadowBedBlockEntity;
import com.pasterdream.pasterdreammod.world.block.twilightlantern.TwilightLanternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.List;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class TwilightLanternGameTests {

    @GameTest(template = "empty")
    public static void twilightLanternRegistries(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.TWILIGHT_LANTERN.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "twilight_lantern")),
                "暮影之笼方块应以 twilight_lantern 注册");
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.SHADOW_BED.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_bed")),
                "暮影长床方块应以 shadow_bed 注册");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.TWILIGHT_LANTERN.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "twilight_lantern")),
                "暮影之笼方块实体应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.SHADOW_BED.get())
                .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_bed")),
                "暮影长床方块实体应已注册");
        helper.assertTrue(ModItems.TWILIGHT_LANTERN.get() != ModItems.SHADOW_BED.get(), "两个方块物品应分别注册");

        BlockPos lanternPos = new BlockPos(1, 1, 1);
        helper.setBlock(lanternPos, ModBlocks.TWILIGHT_LANTERN.get());
        helper.assertTrue(helper.getBlockEntity(lanternPos) instanceof TwilightLanternBlockEntity,
                "暮影之笼应创建方块实体");

        BlockPos bedPos = new BlockPos(3, 1, 1);
        helper.setBlock(bedPos, ModBlocks.SHADOW_BED.get());
        helper.assertTrue(helper.getBlockEntity(bedPos) instanceof ShadowBedBlockEntity,
                "暮影长床主部件应创建方块实体");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void twilightLanternContent(GameTestHelper helper) {
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_bed")).isPresent(),
                "应存在暮影长床配方");

        var advancements = helper.getLevel().getServer().getAdvancements();
        for (String path : List.of("story/lamp_shadow_root", "story/bastion_guard", "story/enter_lamp_shadow_world")) {
            helper.assertTrue(advancements.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path)) != null,
                    "应存在进度: " + path);
        }

        var structures = helper.getLevel().getStructureManager();
        for (String name : List.of("twilight_lantern", "shadow_world_spawn")) {
            helper.assertTrue(structures.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name)).isPresent(),
                    "应能加载结构模板: " + name);
        }
        helper.succeed();
    }
}
