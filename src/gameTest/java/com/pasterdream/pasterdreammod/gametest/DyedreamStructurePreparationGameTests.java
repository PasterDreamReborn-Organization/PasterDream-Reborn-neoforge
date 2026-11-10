package com.pasterdream.pasterdreammod.gametest;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
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
}
