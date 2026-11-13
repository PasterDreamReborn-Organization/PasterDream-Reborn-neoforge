package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModEntityTypeTags;
import com.pasterdream.pasterdreammod.world.block.windknightaltar.WindKnightAltarBlock;
import com.pasterdream.pasterdreammod.world.block.windknightaltar.WindKnightAltarBlockEntity;
import com.pasterdream.pasterdreammod.world.block.windknightaltar.WindKnightAltarProcedure;
import com.pasterdream.pasterdreammod.world.entity.WindKnightEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class WindKnightGameTests {

    @GameTest(template = "empty")
    public static void windKnightRegistries(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntities.WIND_KNIGHT.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_knight")),
                "破风骑士实体应以 wind_knight 注册");
        helper.assertTrue(ModItems.WIND_KNIGHT_SPAWN_EGG.get() instanceof SpawnEggItem,
                "破风骑士刷怪蛋应为原版 SpawnEggItem");
        helper.assertTrue(ModItems.BREAK_WIND_KNIGHT_ALTAR.get() instanceof BlockItem,
                "破风骑士祭坛物品应为 BlockItem");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.BREAK_WIND_KNIGHT_ALTAR.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "break_wind_knight_altar")),
                "破风骑士祭坛方块实体应注册");

        WindKnightEntity knight = ModEntities.WIND_KNIGHT.get().create(helper.getLevel());
        helper.assertTrue(knight != null, "破风骑士应能创建实体");
        helper.assertTrue(knight.getMaxHealth() == 250.0F, "破风骑士最大生命应为 250");
        helper.assertTrue(knight.getAttributeValue(Attributes.ARMOR) == 20.0, "破风骑士护甲应为 20");
        helper.assertTrue(knight.getAttributeValue(Attributes.ATTACK_DAMAGE) == 20.0, "破风骑士攻击伤害应为 20");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void altarBlockAndStructure(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.BREAK_WIND_KNIGHT_ALTAR.get());
        helper.assertTrue(helper.getBlockState(pos).getValue(WindKnightAltarBlock.STAGE) == 0,
                "破风骑士祭坛默认阶段应为 0");
        helper.assertTrue(helper.getBlockEntity(pos) instanceof WindKnightAltarBlockEntity,
                "破风骑士祭坛应创建方块实体");

        ResourceLocation structureId = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                "break_wind_knight_altar");
        var structureHolder = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE)
                .get(ResourceKey.create(Registries.STRUCTURE, structureId));
        helper.assertTrue(structureHolder.isPresent(), "破风骑士祭坛结构应已注册");
        var windMoor = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME)
                .getOrThrow(com.pasterdream.pasterdreammod.worldgen.biome.ModBiomes.WIND_MOOR_ARCHIPELAGO);
        helper.assertTrue(structureHolder.get().value().biomes().contains(windMoor),
                "破风骑士祭坛结构应只生成于风泊群岛群系");
        helper.assertTrue(helper.getLevel().getStructureManager().get(structureId).isPresent(),
                "破风骑士祭坛结构模板应可加载");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windKnightLootAndTags(GameTestHelper helper) {
        var loot = helper.getLevel().getServer().reloadableRegistries()
                .getLootTable(ModEntities.WIND_KNIGHT.get().getDefaultLootTable());
        helper.assertTrue(loot != LootTable.EMPTY, "破风骑士战利品表应可加载");

        boolean thundercloud = false;
        boolean highvoltage = false;
        for (var holder : BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(ModEntityTypeTags.WIND_KNIGHT_MINION)) {
            if (holder.value() == ModEntities.THUNDERCLOUD.get()) thundercloud = true;
            if (holder.value() == ModEntities.HIGHVOLTAGE_THUNDERCLOUD.get()) highvoltage = true;
        }
        helper.assertTrue(thundercloud && highvoltage, "破风骑士召唤物标签应包含两种雷云");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void windKnightAdvancements(GameTestHelper helper) {
        var advancements = helper.getLevel().getServer().getAdvancements();
        for (String path : java.util.List.of("story/summon_wind_knight", "story/pulse_wind_runner_crystal")) {
            helper.assertTrue(advancements.get(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, path)) != null,
                    "应存在进度: " + path);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void altarSummonProcedure(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.BREAK_WIND_KNIGHT_ALTAR.get().defaultBlockState()
                .setValue(WindKnightAltarBlock.STAGE, 4));
        BlockPos absolute = helper.absolutePos(pos);
        WindKnightAltarProcedure.trySummon(helper.getLevel(),
                new Vec3(absolute.getX() + 0.5, absolute.getY() + 0.5, absolute.getZ() + 0.5));

        helper.assertTrue(helper.getBlockState(pos).getValue(WindKnightAltarBlock.STAGE) == 0,
                "召唤后祭坛阶段应重置为 0");
        helper.assertTrue(!helper.getLevel().getEntitiesOfClass(WindKnightEntity.class,
                        new AABB(absolute).inflate(6)).isEmpty(),
                "祭坛应成功召唤破风骑士");
        helper.succeed();
    }
}
