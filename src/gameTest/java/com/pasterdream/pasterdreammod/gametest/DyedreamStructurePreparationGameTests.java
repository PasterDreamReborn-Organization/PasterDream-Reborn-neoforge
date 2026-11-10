package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import com.pasterdream.pasterdreammod.init.ModStructurePlacementTypes;
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

import java.util.List;
import java.util.HashSet;
import java.util.Set;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DyedreamStructurePreparationGameTests {
    @GameTest(template = "empty")
    public static void dyedreamChestTablesLoad(GameTestHelper helper) {
        var tables = helper.getLevel().getServer().reloadableRegistries();
        for (var key : List.of(
                ModLootTables.PICNIC_BASKET_DYEDREAM_WORLD_LOOT,
                ModLootTables.DYEDREAM_RELIC_CHEST_LOOT_COMMON,
                ModLootTables.DYEDREAM_RELIC_CHEST_LOOT_RARE,
                ModLootTables.ECOSYSTEM_BUBBLE_CHEST_LOOT)) {
            helper.assertTrue(tables.getLootTable(key) != LootTable.EMPTY,
                    "染梦结构箱战利品表应可加载: " + key.location());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dreamTrainMarkerAndTemplateLoad(GameTestHelper helper) {
        var manager = helper.getLevel().getStructureManager();
        for (String name : List.of("dream_train_marker", "dream_train")) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(manager.get(id).isPresent(), "逐梦列车模板应可加载: " + id);
        }
        var pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.DREAM_TRAIN_STRUCTURE.get());
        helper.assertTrue(helper.getBlockEntity(pos).getType() == ModBlockEntities.DREAM_TRAIN_BROADCAST.get(),
                "列车标记应创建广播方块实体");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dreamTrainNotesUseComponents(GameTestHelper helper) throws Exception {
        var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structure/dream_train.nbt");
        var resource = helper.getLevel().getServer().getResourceManager().getResource(id).orElseThrow();
        Set<String> contents = new HashSet<>();
        int itemCount = 0;
        try (var stream = resource.open()) {
            var template = NbtIo.readCompressed(stream, NbtAccounter.unlimitedHeap());
            for (var block : template.getList("blocks", Tag.TAG_COMPOUND)) {
                var inventory = ((net.minecraft.nbt.CompoundTag) block).getCompound("nbt").getCompound("Inventory");
                for (var raw : inventory.getList("Items", Tag.TAG_COMPOUND)) {
                    var item = (net.minecraft.nbt.CompoundTag) raw;
                    itemCount++;
                    helper.assertTrue(!item.contains("Count") && !item.contains("tag"),
                            "列车物品栈不能保留 1.20 NBT 字段");
                    if (item.getString("id").equals("pasterdream:dream_notes_book")) {
                        ItemStack note = ItemStack.parseOptional(helper.getLevel().registryAccess(), item);
                        helper.assertTrue(note.is(ModItems.DREAM_NOTES_BOOK.get()), "寻梦者笔记应可解析");
                        contents.add(note.get(ModDataComponents.DREAM_NOTE_CONTENT.get()));
                    }
                }
            }
        }
        helper.assertTrue(itemCount == 3, "列车模板应保留 3 个预置物品");
        helper.assertTrue(contents.equals(Set.of("逐梦列车", "梦境漂泊")), "两本寻梦者笔记应保留内容组件");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dyedreamStructuresAndTemplatesLoad(GameTestHelper helper) {
        var structureLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE);
        var setLookup = helper.getLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE_SET);
        List<String> structures = List.of(
                "dyedream_church_1",
                "dyedream_church_2",
                "invaded_church",
                "dyedream_crystal_ball",
                "dyedream_wishing_tree",
                "fluffy_wind_church",
                "garden_decryption_misty_dreaming_lotus",
                "garden_decryption_nine_tailed_fox",
                "garden_decryption_nippy_edelweiss",
                "traveler_house",
                "lifecrystal_cave",
                "dyedream_pavilion_plain",
                "dyedream_pavilion_snowy_plain",
                "dyedream_floating_temple",
                "dyedream_tavern",
                "dyedream_campsite",
                "dyedream_tower_0",
                "dyedream_tower_1",
                "dyedream_sky_island",
                "dyedream_worldtree",
                "dyedream_laboratory",
                "dream_train",
                "melt_dream_liquid_well",
                "pinkagaric_house",
                "moon_house",
                "dyedream_ecosystem_bubble",
                "ecosystem_bubble",
                "big_bubbles_0",
                "big_bubbles_1",
                "big_bubbles_2");
        List<String> sets = List.of(
                "dyedream_structures",
                "dyedream_bubbles",
                "moon_house",
                "dyedream_worldtree_fixed");
        List<String> templates = List.of(
                "dyedream_church_1",
                "dyedream_church_2",
                "invaded_church",
                "dyedream_crystal_ball",
                "dyedream_wishing_tree",
                "fluffy_wind_church",
                "garden_decryption_misty_dreaming_lotus",
                "garden_decryption_nine_tailed_fox",
                "garden_decryption_nippy_edelweiss",
                "traveler_house",
                "lifecrystal_cave",
                "dyedream_pavilion_plain",
                "dyedream_pavilion_snowy_plain",
                "dyedream_floating_temple",
                "dyedream_tavern",
                "dyedream_campsite",
                "dyedream_tower_0",
                "dyedream_tower_1",
                "dyedream_sky_island",
                "dyedream_worldtree_top",
                "dyedream_worldtree_bottom",
                "dyedream_laboratory_top",
                "dyedream_laboratory_bottom",
                "melt_dream_liquid_well_top",
                "melt_dream_liquid_well_bottom",
                "pinkagaric_house_top",
                "pinkagaric_house_bottom",
                "moon_house",
                "dyedream_ecosystem_bubble",
                "ecosystem_bubble",
                "big_bubbles_0",
                "big_bubbles_1",
                "big_bubbles_2");
        for (String name : structures) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(structureLookup.get(ResourceKey.create(Registries.STRUCTURE, id)).isPresent(),
                    "染梦世界结构应已注册: " + id);
        }
        for (String name : sets) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(setLookup.get(ResourceKey.create(Registries.STRUCTURE_SET, id)).isPresent(),
                    "染梦世界结构集应已注册: " + id);
        }
        var manager = helper.getLevel().getStructureManager();
        for (String name : templates) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name);
            helper.assertTrue(manager.get(id).isPresent(), "染梦世界结构模板应可加载: " + id);
        }
        var fixedChunk = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fixed_chunk");
        helper.assertTrue(fixedChunk.equals(BuiltInRegistries.STRUCTURE_PLACEMENT.getKey(
                        ModStructurePlacementTypes.FIXED_CHUNK.get())),
                "固定区块结构放置类型应已注册: " + fixedChunk);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void dyedreamStructureNbtUsesComponents(GameTestHelper helper) throws Exception {
        List<String> names = List.of(
                "dyedream_church_1",
                "dyedream_church_2",
                "invaded_church",
                "dyedream_crystal_ball",
                "dyedream_wishing_tree",
                "fluffy_wind_church",
                "garden_decryption_misty_dreaming_lotus",
                "garden_decryption_nine_tailed_fox",
                "garden_decryption_nippy_edelweiss",
                "traveler_house",
                "lifecrystal_cave",
                "dyedream_pavilion_plain",
                "dyedream_pavilion_snowy_plain",
                "dyedream_floating_temple",
                "dyedream_tavern",
                "dyedream_campsite",
                "dyedream_tower_0",
                "dyedream_tower_1",
                "dyedream_sky_island",
                "dyedream_worldtree_top",
                "dyedream_worldtree_bottom",
                "dyedream_laboratory_top",
                "dyedream_laboratory_bottom",
                "melt_dream_liquid_well_top",
                "melt_dream_liquid_well_bottom",
                "pinkagaric_house_top",
                "pinkagaric_house_bottom",
                "moon_house",
                "dyedream_ecosystem_bubble",
                "ecosystem_bubble",
                "big_bubbles_0",
                "big_bubbles_1",
                "big_bubbles_2");
        int[] counts = new int[2];
        for (String name : names) {
            var id = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "structure/" + name + ".nbt");
            var resource = helper.getLevel().getServer().getResourceManager().getResource(id).orElseThrow();
            try (var stream = resource.open()) {
                var template = NbtIo.readCompressed(stream, NbtAccounter.unlimitedHeap());
                for (var block : template.getList("blocks", Tag.TAG_COMPOUND)) {
                    assertNoLegacyStacks(helper, ((CompoundTag) block).getCompound("nbt"),
                            name + "/block", counts);
                }
                for (var entity : template.getList("entities", Tag.TAG_COMPOUND)) {
                    assertNoLegacyStacks(helper, ((CompoundTag) entity).getCompound("nbt"),
                            name + "/entity", counts);
                }
            }
        }
        helper.assertTrue(counts[0] > 0, "染梦结构 NBT 应包含可解析的物品栈");
        helper.assertTrue(counts[1] > 0, "染梦结构 NBT 中的寻梦者笔记应保留内容组件");
        helper.succeed();
    }

    private static void assertNoLegacyStacks(GameTestHelper helper, CompoundTag tag, String where, int[] counts) {
        if (tag.contains("id") && (tag.contains("Count") || tag.contains("count"))) {
            helper.assertTrue(!tag.contains("Count") && !tag.contains("tag"),
                    where + " 仍保留 1.20 物品字段");
            ItemStack stack = ItemStack.parseOptional(helper.getLevel().registryAccess(), tag);
            helper.assertTrue(!stack.isEmpty(), where + " 物品栈应可解析");
            counts[0]++;
            if (stack.is(ModItems.DREAM_NOTES_BOOK.get())) {
                helper.assertTrue(stack.has(ModDataComponents.DREAM_NOTE_CONTENT.get()),
                        where + " 寻梦者笔记应含内容组件");
                counts[1]++;
            }
        }
        for (String key : tag.getAllKeys()) {
            Tag child = tag.get(key);
            if (child instanceof CompoundTag compound) {
                assertNoLegacyStacks(helper, compound, where + "/" + key, counts);
            } else if (child instanceof ListTag list) {
                int index = 0;
                for (Tag element : list) {
                    if (element instanceof CompoundTag compound) {
                        assertNoLegacyStacks(helper, compound, where + "/" + key + "[" + index + "]", counts);
                    }
                    index++;
                }
            }
        }
    }
}
