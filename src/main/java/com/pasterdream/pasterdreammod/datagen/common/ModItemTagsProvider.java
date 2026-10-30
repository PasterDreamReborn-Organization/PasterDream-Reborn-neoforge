package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                               CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
                               ExistingFileHelper existingFileHelper) {
        super(output, lookup, blockTags, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        copy(ModBlockTags.ORES_DYEDREAM_QUARTZ, ModItemTags.ORES_DYEDREAM_QUARTZ);
        copy(ModBlockTags.ORES_DYEDREAM_DUST, ModItemTags.ORES_DYEDREAM_DUST);
        copy(ModBlockTags.ORES_IN_GROUND_CALCITE, ModItemTags.ORES_IN_GROUND_CALCITE);
        copy(ModBlockTags.ORE_RATES_DENSE, ModItemTags.ORE_RATES_DENSE);
        tag(ItemTags.SWORDS).add(ModItems.TITANIUM_SWORD.get());
        tag(ItemTags.PICKAXES).add(ModItems.TITANIUM_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.TITANIUM_AXE.get());
        tag(ItemTags.SHOVELS).add(ModItems.TITANIUM_SHOVEL.get());
        tag(ItemTags.HOES).add(ModItems.TITANIUM_HOE.get());
        copy(ModBlockTags.ORES_TITANIUM, ModItemTags.ORES_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_TITANIUM, ModItemTags.STORAGE_BLOCKS_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM, ModItemTags.STORAGE_BLOCKS_RAW_TITANIUM);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        tag(ModItemTags.INGOTS_TITANIUM).add(ModItems.TITANIUM_INGOT.get());
        tag(ModItemTags.NUGGETS_TITANIUM).add(ModItems.TITANIUM_NUGGET.get());
        tag(ModItemTags.RAW_MATERIALS_TITANIUM).add(ModItems.RAW_TITANIUM.get());
        tag(Tags.Items.INGOTS).addTag(ModItemTags.INGOTS_TITANIUM);
        tag(Tags.Items.NUGGETS).addTag(ModItemTags.NUGGETS_TITANIUM);
        tag(Tags.Items.RAW_MATERIALS).addTag(ModItemTags.RAW_MATERIALS_TITANIUM);
        copy(ModBlockTags.ORES_MOLTEN_GOLD, ModItemTags.ORES_MOLTEN_GOLD);
        copy(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD, ModItemTags.STORAGE_BLOCKS_MOLTEN_GOLD);
        tag(ModItemTags.INGOTS_MOLTEN_GOLD).add(ModItems.MOLTEN_GOLD_INGOT.get());
        tag(ModItemTags.NUGGETS_MOLTEN_GOLD).add(ModItems.MOLTEN_GOLD_NUGGET.get());
        tag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD).add(ModItems.RAW_MOLTEN_GOLD.get());
        tag(Tags.Items.INGOTS).addTag(ModItemTags.INGOTS_MOLTEN_GOLD);
        tag(Tags.Items.NUGGETS).addTag(ModItemTags.NUGGETS_MOLTEN_GOLD);
        tag(Tags.Items.RAW_MATERIALS).addTag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD);
    }
}
