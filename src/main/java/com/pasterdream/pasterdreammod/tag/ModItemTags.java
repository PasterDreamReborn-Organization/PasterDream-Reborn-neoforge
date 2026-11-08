package com.pasterdream.pasterdreammod.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** 跨模组材料分类使用 NeoForge 的 c 命名空间。 */
public final class ModItemTags {
    public static final TagKey<Item> SKILL_COOLDOWN = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "skill_cooldown"));
    public static final TagKey<Item> ENHANCE_STONE = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "enhance_stone"));
    public static final TagKey<Item> CHANNELING_ENCHANTABLE = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "channeling_enchantable"));
    public static final TagKey<Item> ORES_DYEDREAM_QUARTZ = common("ores/dyedream_quartz");
    public static final TagKey<Item> ORES_DYEDREAM_DUST = common("ores/dyedream_dust");
    public static final TagKey<Item> ORES_AMBER_CANDY = common("ores/amber_candy");
    public static final TagKey<Item> ORES_IN_GROUND_CALCITE = common("ores_in_ground/calcite");
    public static final TagKey<Item> ORE_RATES_SINGULAR = common("ore_rates/singular");
    public static final TagKey<Item> ORE_RATES_DENSE = common("ore_rates/dense");
    public static final TagKey<Item> ORES_TITANIUM = common("ores/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_TITANIUM = common("storage_blocks/titanium");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_TITANIUM = common("storage_blocks/raw_titanium");
    public static final TagKey<Item> INGOTS_BLACK_METAL = common("ingots/black_metal");
    public static final TagKey<Item> INGOTS_SHADOW_ALLOY = common("ingots/shadow_alloy");
    public static final TagKey<Item> NUGGETS_BLACK_METAL = common("nuggets/black_metal");
    public static final TagKey<Item> LAMP_SHADOW_WEAPON = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "lamp_shadow_weapon"));
    public static final TagKey<Item> PURE_HORROR_SOURCES = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "pure_horror_sources"));
    public static final TagKey<Item> INGOTS_TITANIUM = common("ingots/titanium");
    public static final TagKey<Item> NUGGETS_TITANIUM = common("nuggets/titanium");
    public static final TagKey<Item> RAW_MATERIALS_TITANIUM = common("raw_materials/titanium");
    public static final TagKey<Item> INGOTS_RAW_DYEDREAM_ALLOY = common("ingots/raw_dyedream_alloy");
    public static final TagKey<Item> INGOTS_DYEDREAM_ALLOY = common("ingots/dyedream_alloy");
    public static final TagKey<Item> NUGGETS_DYEDREAM_ALLOY = common("nuggets/dyedream_alloy");
    public static final TagKey<Item> NUGGETS_DYEDREAM_BUD = common("nuggets/dyedream_bud");
    public static final TagKey<Item> ORES_SOUL_DUST = common("ores/soul_dust");
    public static final TagKey<Item> ORES_IN_GROUND_SOUL_SOIL = common("ores_in_ground/soul_soil");
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
    public static final TagKey<Item> STORAGE_BLOCKS_CHARGED_AMETHYST = common("storage_blocks/charged_amethyst");
    public static final TagKey<Item> STORAGE_BLOCKS_SALT = common("storage_blocks/salt");
    public static final TagKey<Item> SALT = common("salt");
    public static final TagKey<Item> FLOUR = common("flour");
    public static final TagKey<Item> FLOUR_WHEAT = common("flour/wheat");
    public static final TagKey<Item> COOKED_EGGS = common("cooked_eggs");
    public static final TagKey<Item> GALAXY_JELLY_BOOST = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("pasterdream", "galaxy_jelly_boost"));
    public static final TagKey<Item> DYEDREAM_DIRT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("pasterdream", "dyedream_dirt"));
    public static final TagKey<Item> SHADOW_STONES = common("shadow_stones");
    public static final TagKey<Item> SHADOW_STEMS = common("shadow_stems");
    public static final TagKey<Item> STORAGE_BLOCKS_BLACK_METAL = common("storage_blocks/black_metal");

    private static TagKey<Item> common(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private ModItemTags() {
    }
}
