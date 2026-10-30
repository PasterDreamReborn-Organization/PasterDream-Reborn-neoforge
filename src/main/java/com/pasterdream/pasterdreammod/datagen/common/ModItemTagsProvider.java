package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // copy(BlockTag, ItemTag) 将 BlockTagsProvider 中注册的方块标签自动同步到对应物品标签
        copy(ModBlockTags.ORES_DYEDREAM_QUARTZ, ModItemTags.ORES_DYEDREAM_QUARTZ);
        copy(ModBlockTags.ORES_DYEDREAM_DUST, ModItemTags.ORES_DYEDREAM_DUST);
        copy(ModBlockTags.ORES_IN_GROUND_CALCITE, ModItemTags.ORES_IN_GROUND_CALCITE);
        copy(ModBlockTags.ORE_RATES_DENSE, ModItemTags.ORE_RATES_DENSE);
        copy(ModBlockTags.ORES_TITANIUM, ModItemTags.ORES_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_TITANIUM, ModItemTags.STORAGE_BLOCKS_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM, ModItemTags.STORAGE_BLOCKS_RAW_TITANIUM);
        copy(ModBlockTags.ORES_MOLTEN_GOLD, ModItemTags.ORES_MOLTEN_GOLD);
        copy(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD, ModItemTags.STORAGE_BLOCKS_MOLTEN_GOLD);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        // 工具标签
        tag(ItemTags.SWORDS)
                .add(ModItems.TITANIUM_SWORD.get())
                .add(ModItems.MOLTEN_GOLD_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.TITANIUM_PICKAXE.get())
                .add(ModItems.MOLTEN_GOLD_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.TITANIUM_AXE.get())
                .add(ModItems.MOLTEN_GOLD_AXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.TITANIUM_SHOVEL.get())
                .add(ModItems.MOLTEN_GOLD_SHOVEL.get());
        tag(ItemTags.HOES)
                .add(ModItems.TITANIUM_HOE.get())
                .add(ModItems.MOLTEN_GOLD_HOE.get());
        tag(ItemTags.HEAD_ARMOR).add(ModItems.TITANIUM_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.TITANIUM_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.TITANIUM_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.TITANIUM_BOOTS.get());

        // 金属锭标签
        tag(ModItemTags.INGOTS_TITANIUM)
                .add(ModItems.TITANIUM_INGOT.get());
        tag(Tags.Items.INGOTS)
                .addTag(ModItemTags.INGOTS_TITANIUM)
                .addTag(ModItemTags.INGOTS_MOLTEN_GOLD);
        tag(ModItemTags.INGOTS_MOLTEN_GOLD)
                .add(ModItems.MOLTEN_GOLD_INGOT.get());

        // 金属粒标签
        tag(ModItemTags.NUGGETS_TITANIUM)
                .add(ModItems.TITANIUM_NUGGET.get());
        tag(Tags.Items.NUGGETS)
                .addTag(ModItemTags.NUGGETS_TITANIUM)
                .addTag(ModItemTags.NUGGETS_MOLTEN_GOLD);
        tag(ModItemTags.NUGGETS_MOLTEN_GOLD)
                .add(ModItems.MOLTEN_GOLD_NUGGET.get());

        tag(ModItemTags.RAW_MATERIALS_TITANIUM)
                .add(ModItems.RAW_TITANIUM.get());
        tag(Tags.Items.RAW_MATERIALS)
                .addTag(ModItemTags.RAW_MATERIALS_TITANIUM)
                .addTag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD);
        tag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD)
                .add(ModItems.RAW_MOLTEN_GOLD.get());

    }
}
