package com.pasterdream.pasterdreammod.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/** 跨模组材料分类使用 NeoForge 的 c 命名空间。 */
public final class ModBlockTags {
    public static final TagKey<Block> ORES_DYEDREAM_QUARTZ = common("ores/dyedream_quartz");
    public static final TagKey<Block> ORES_DYEDREAM_DUST = common("ores/dyedream_dust");
    public static final TagKey<Block> ORES_AMBER_CANDY = common("ores/amber_candy");
    public static final TagKey<Block> ORES_IN_GROUND_CALCITE = common("ores_in_ground/calcite");
    public static final TagKey<Block> ORE_RATES_SINGULAR = common("ore_rates/singular");
    public static final TagKey<Block> ORE_RATES_DENSE = common("ore_rates/dense");
    public static final TagKey<Block> ORES_TITANIUM = common("ores/titanium");
    public static final TagKey<Block> STORAGE_BLOCKS_TITANIUM = common("storage_blocks/titanium");
    public static final TagKey<Block> STORAGE_BLOCKS_RAW_TITANIUM = common("storage_blocks/raw_titanium");
    public static final TagKey<Block> ORES_MOLTEN_GOLD = common("ores/molten_gold");
    public static final TagKey<Block> ORES_SOUL_DUST = common("ores/soul_dust");
    public static final TagKey<Block> ORES_IN_GROUND_SOUL_SOIL = common("ores_in_ground/soul_soil");
    public static final TagKey<Block> STORAGE_BLOCKS_MOLTEN_GOLD = common("storage_blocks/molten_gold");
    public static final TagKey<Block> STORAGE_BLOCKS_DYEDREAM_ALLOY = common("storage_blocks/dyedream_alloy");
    public static final TagKey<Block> STORAGE_BLOCKS_DYEDREAM_BUD = common("storage_blocks/dyedream_bud");
    public static final TagKey<Block> STORAGE_BLOCKS_CHARGED_AMETHYST = common("storage_blocks/charged_amethyst");
    public static final TagKey<Block> STORAGE_BLOCKS_SALT = common("storage_blocks/salt");
    public static final TagKey<Block> STORAGE_BLOCKS_BLACK_METAL = common("storage_blocks/black_metal");

    public static final TagKey<Block> SHADOW_EROSION_TOOL_CAN_BOOST = TagKey.create(Registries.BLOCK, ResourceLocation.parse("pasterdream:shadow_erosion_tool_can_boost"));
    public static final TagKey<Block> DYEDREAM_DIRT = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt"));
    public static final TagKey<Block> WHITE_COROLLA_CAN_PLACE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.parse("pasterdream:white_corolla_can_place_on"));
    public static final TagKey<Block> CRIMSON_THORNS_CAN_PLACE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.parse("pasterdream:crimson_thorns_can_place_on"));
    public static final TagKey<Block> BLAZE_FLOWER_CAN_PLACE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.parse("pasterdream:blaze_flower_can_place_on"));
    public static final TagKey<Block> SHADOW_PLANTS_CAN_PLACE_ON = TagKey.create(Registries.BLOCK, ResourceLocation.parse("pasterdream:shadow_plants_can_place_on"));

    private static TagKey<Block> common(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private ModBlockTags() {
    }
}
