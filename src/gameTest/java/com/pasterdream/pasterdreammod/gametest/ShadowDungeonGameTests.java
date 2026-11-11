package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.world.block.shadowbrazier.ShadowBrazierBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class ShadowDungeonGameTests {
    private static final List<String> DUNGEON_TEMPLATES = List.of(
            "shadow_dungeon",
            "shadow_dungeon_start_room",
            "shadow_dungeon_single_floor_frame",
            "shadow_dungeon_shadow_bed",
            "shadow_dungeon_shadow_brazier",
            "shadow_dungeon_black_beetle",
            "shadow_dungeon_digging_0",
            "shadow_dungeon_digging_1",
            "shadow_dungeon_digging_2",
            "shadow_dungeon_mezz_0",
            "shadow_dungeon_mezz_1",
            "shadow_dungeon_mezz_2",
            "shadow_dungeon_mezz_3",
            "shadow_dungeon_mezz_4",
            "shadow_dungeon_mezz_5",
            "shadow_dungeon_shadow_library_0",
            "shadow_dungeon_shadow_library_1",
            "shadow_dungeon_shadow_library_2",
            "shadow_dungeon_shadow_library_3",
            "shadow_dungeon_nameless_dyedream_world",
            "shadow_dungeon_nameless_overworld",
            "shadow_dungeon_repository");

    @GameTest(template = "empty")
    public static void shadowDungeonRegistries(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.SHADOW_BRAZIER.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_brazier")),
                "暗影火盆方块应以 shadow_brazier 注册");
        helper.assertTrue(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(ModBlockEntities.SHADOW_BRAZIER.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_brazier")),
                "暗影火盆方块实体应已注册");
        helper.assertTrue(BuiltInRegistries.BLOCK.getKey(ModBlocks.CLAY_POT.get())
                        .equals(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "clay_pot")),
                "陶罐方块应以 clay_pot 注册");
        helper.assertTrue(ModItems.SHADOW_BRAZIER.get().getDefaultInstance().is(ModItems.SHADOW_BRAZIER.get()),
                "暗影火盆物品应已注册");

        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SHADOW_BRAZIER.get());
        var blockEntity = helper.getBlockEntity(pos);
        helper.assertTrue(blockEntity instanceof ShadowBrazierBlockEntity,
                "暗影火盆应创建方块实体");
        ShadowBrazierBlockEntity brazier = (ShadowBrazierBlockEntity) blockEntity;
        helper.assertTrue(!brazier.isLit(), "暗影火盆初始应为未点燃");
        brazier.ignite();
        helper.assertTrue(brazier.isLit(), "暗影火盆点燃后应为已点燃");
        helper.assertTrue(brazier.getUpdateTag(helper.getLevel().registryAccess()).getBoolean("Lit"),
                "暗影火盆点燃状态应进入更新标签");

        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shadowDungeonWorldgenLoads(GameTestHelper helper) {
        var structureLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE);
        var setLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE_SET);
        var structureId = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_dungeon");
        helper.assertTrue(structureLookup.get(ResourceKey.create(Registries.STRUCTURE, structureId)).isPresent(),
                "暗影地牢入口结构应已注册");
        var setId = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_structures");
        helper.assertTrue(setLookup.get(ResourceKey.create(Registries.STRUCTURE_SET, setId)).isPresent(),
                "灯影之下共享结构集应已注册");

        var manager = helper.getLevel().getStructureManager();
        for (String name : DUNGEON_TEMPLATES) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(manager.get(id).isPresent(), "暗影地牢结构模板应可加载: " + id);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shadowDungeonNbtUsesComponents(GameTestHelper helper) throws Exception {
        int[] counts = new int[2];
        Set<String> noteContents = new HashSet<>();
        for (String name : DUNGEON_TEMPLATES) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structure/" + name + ".nbt");
            var resource = helper.getLevel().getServer().getResourceManager().getResource(id).orElseThrow();
            try (var stream = resource.open()) {
                var template = NbtIo.readCompressed(stream, NbtAccounter.unlimitedHeap());
                for (var block : template.getList("blocks", Tag.TAG_COMPOUND)) {
                    assertNoLegacyStacks(helper, ((CompoundTag) block).getCompound("nbt"),
                            name + "/block", counts, noteContents);
                }
                for (var entity : template.getList("entities", Tag.TAG_COMPOUND)) {
                    assertNoLegacyStacks(helper, ((CompoundTag) entity).getCompound("nbt"),
                            name + "/entity", counts, noteContents);
                }
            }
        }
        helper.assertTrue(counts[0] > 0, "暗影地牢模板应包含可解析的物品栈");
        helper.assertTrue(noteContents.contains("暗影地牢入口"), "暗影地牢入口模板应保留入口笔记内容");
        helper.assertTrue(noteContents.contains("暗影图书馆"), "暗影书库模板应保留图书馆笔记内容");
        helper.succeed();
    }

    private static void assertNoLegacyStacks(GameTestHelper helper, CompoundTag tag, String where,
                                             int[] counts, Set<String> noteContents) {
        if (tag.contains("id") && (tag.contains("Count") || tag.contains("count"))) {
            helper.assertTrue(!tag.contains("Count") && !tag.contains("tag"),
                    where + " 仍保留 1.20 物品字段");
            ItemStack stack = ItemStack.parseOptional(helper.getLevel().registryAccess(), tag);
            helper.assertTrue(!stack.isEmpty(), where + " 物品栈应可解析");
            counts[0]++;
            if (stack.is(ModItems.DREAM_NOTES_BOOK.get())) {
                String content = stack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());
                helper.assertTrue(content != null && !content.isEmpty(),
                        where + " 寻梦者笔记应含内容组件");
                noteContents.add(content);
            }
        }
        for (String key : tag.getAllKeys()) {
            Tag child = tag.get(key);
            if (child instanceof CompoundTag compound) {
                assertNoLegacyStacks(helper, compound, where + "/" + key, counts, noteContents);
            } else if (child instanceof ListTag list) {
                int index = 0;
                for (Tag element : list) {
                    if (element instanceof CompoundTag compound) {
                        assertNoLegacyStacks(helper, compound, where + "/" + key + "[" + index + "]",
                                counts, noteContents);
                    }
                    index++;
                }
            }
        }
    }

    @GameTest(template = "empty")
    public static void shadowDungeonRepositoryTablesLoad(GameTestHelper helper) {
        var tables = helper.getLevel().getServer().reloadableRegistries();
        for (var key : List.of(
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_COMMON,
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_CORE,
                ModLootTables.SHADOW_DUNGEON_REPOSITORY_SUPPLY)) {
            helper.assertTrue(tables.getLootTable(key) != LootTable.EMPTY,
                    "暗影地牢宝库战利品表应可加载: " + key.location());
        }
        helper.succeed();
    }
}
