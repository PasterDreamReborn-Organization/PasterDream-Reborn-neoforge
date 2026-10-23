package com.pasterdream.pasterdreammod.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/** 跨模组材料分类使用 NeoForge 的 c 命名空间。 */
public final class ModBlockTags {
    public static final TagKey<Block> ORES_TITANIUM = common("ores/titanium");
    public static final TagKey<Block> STORAGE_BLOCKS_TITANIUM = common("storage_blocks/titanium");
    public static final TagKey<Block> STORAGE_BLOCKS_RAW_TITANIUM = common("storage_blocks/raw_titanium");

    private static TagKey<Block> common(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private ModBlockTags() {
    }
}
