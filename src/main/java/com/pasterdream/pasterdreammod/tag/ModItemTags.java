package com.pasterdream.pasterdreammod.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** 跨模组材料分类使用 NeoForge 的 c 命名空间。 */
public final class ModItemTags {
    public static final TagKey<Item> ORES_TITANIUM = common("ores/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_TITANIUM = common("storage_blocks/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_TITANIUM = common("storage_blocks/raw_titanium");
    public static final TagKey<Item> INGOTS_TITANIUM = common("ingots/titanium");
    public static final TagKey<Item> NUGGETS_TITANIUM = common("nuggets/titanium");
    public static final TagKey<Item> RAW_MATERIALS_TITANIUM = common("raw_materials/titanium");

    private static TagKey<Item> common(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private ModItemTags() {
    }
}
