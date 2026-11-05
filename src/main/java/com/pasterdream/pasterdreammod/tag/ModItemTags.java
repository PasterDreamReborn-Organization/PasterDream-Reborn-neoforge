package com.pasterdream.pasterdreammod.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** 跨模组材料分类使用 NeoForge 的 c 命名空间。 */
public final class ModItemTags {
    public static final TagKey<Item> SKILL_COOLDOWN = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "skill_cooldown"));
    public static final TagKey<Item> ORES_DYEDREAM_QUARTZ = common("ores/dyedream_quartz");
    public static final TagKey<Item> ORES_DYEDREAM_DUST = common("ores/dyedream_dust");
    public static final TagKey<Item> ORES_IN_GROUND_CALCITE = common("ores_in_ground/calcite");
    public static final TagKey<Item> ORE_RATES_DENSE = common("ore_rates/dense");
    public static final TagKey<Item> ORES_TITANIUM = common("ores/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_TITANIUM = common("storage_blocks/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_TITANIUM = common("storage_blocks/raw_titanium");
    public static final TagKey<Item> INGOTS_TITANIUM = common("ingots/titanium");
    public static final TagKey<Item> NUGGETS_TITANIUM = common("nuggets/titanium");
    public static final TagKey<Item> RAW_MATERIALS_TITANIUM = common("raw_materials/titanium");
    public static final TagKey<Item> INGOTS_RAW_DYEDREAM_ALLOY = common("ingots/raw_dyedream_alloy");
    public static final TagKey<Item> INGOTS_DYEDREAM_ALLOY = common("ingots/dyedream_alloy");
    public static final TagKey<Item> NUGGETS_DYEDREAM_ALLOY = common("nuggets/dyedream_alloy");
    public static final TagKey<Item> NUGGETS_DYEDREAM_BUD = common("nuggets/dyedream_bud");
    public static final TagKey<Item> ORES_MOLTEN_GOLD = common("ores/molten_gold");
    public static final TagKey<Item> STORAGE_BLOCKS_MOLTEN_GOLD = common("storage_blocks/molten_gold");
    public static final TagKey<Item> INGOTS_MOLTEN_GOLD = common("ingots/molten_gold");
    public static final TagKey<Item> NUGGETS_MOLTEN_GOLD = common("nuggets/molten_gold");
    public static final TagKey<Item> RAW_MATERIALS_MOLTEN_GOLD = common("raw_materials/molten_gold");
    public static final TagKey<Item> INGOTS_CONGEAL_WIND_IRON = common("ingots/congeal_wind_iron");
    public static final TagKey<Item> NUGGETS_CONGEAL_WIND_IRON = common("nuggets/congeal_wind_iron");
    public static final TagKey<Item> INGOTS_FLUFFY_WIND_ALLOY = common("ingots/fluffy_wind_alloy");
    public static final TagKey<Item> STORAGE_BLOCKS_DYEDREAM_ALLOY = common("storage_blocks/dyedream_alloy");
    public static final TagKey<Item> STORAGE_BLOCKS_DYEDREAM_BUD = common("storage_blocks/dyedream_bud");

    private static TagKey<Item> common(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private ModItemTags() {
    }
}
