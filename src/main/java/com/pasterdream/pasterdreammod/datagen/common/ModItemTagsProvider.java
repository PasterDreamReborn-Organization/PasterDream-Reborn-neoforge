package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion.CauldronPotionTags;
import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
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
        tag(CauldronPotionTags.BASE).add(ModItems.DYEDREAM_FRUIT.get());
        tag(CauldronPotionTags.ENHANCE_REDSTONE).add(Items.REDSTONE);
        tag(CauldronPotionTags.ENHANCE_GLOWSTONE).add(Items.GLOWSTONE_DUST);
        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("forge", "pen")))
                .add(ModItems.FEATHER_PEN.get());
        tag(CauldronPotionTags.ENHANCE)
                .addTag(CauldronPotionTags.ENHANCE_REDSTONE)
                .addTag(CauldronPotionTags.ENHANCE_GLOWSTONE);
        tag(CauldronPotionTags.CATALYST)
                .add(Items.SUGAR)
                .add(Items.RABBIT_FOOT)
                .add(Items.BLAZE_POWDER)
                .add(Items.GLISTERING_MELON_SLICE)
                .add(Items.SPIDER_EYE)
                .add(Items.GHAST_TEAR)
                .add(Items.MAGMA_CREAM)
                .add(Items.PUFFERFISH)
                .add(Items.GOLDEN_CARROT)
                .add(Items.PHANTOM_MEMBRANE)
                .add(Items.TURTLE_HELMET)
                .add(Items.FERMENTED_SPIDER_EYE);
        // 占星者的祈愿钓竿标签 —— 手持此类钓竿时深海秘宝必出 super 变体
        tag(ModItemTags.STAR_FISHING_RODS)
                .add(ModItems.STAR_WISH_ROD.get());
        tag(ModItemTags.SKILL_COOLDOWN)
                .add(ModItems.SHARP_MELT_DREAM_SWORD.get())
                .add(ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .add(ModItems.MURAKUMO_KUSANAGI.get())
                .add(ModItems.INFERNO_SWORD.get())
                .add(ModItems.CHENJINGMEN_DESERT_SWORD.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_SWORD.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_HOE.get())
                .add(ModItems.WINDWREATHED_THUNDER_SPEAR.get())
                .add(ModItems.TERRA_BLADE.get());
        tag(ModItemTags.ENHANCE_STONE)
                .add(Items.AIR)
                .add(ModItems.ATTACK_ENHANCE_STONE.get())
                .add(ModItems.LUCK_ENHANCE_STONE.get());
        // copy(BlockTag, ItemTag) 将 BlockTagsProvider 中注册的方块标签自动同步到对应物品标签
        copy(ModBlockTags.ORES_DYEDREAM_QUARTZ, ModItemTags.ORES_DYEDREAM_QUARTZ);
        copy(ModBlockTags.ORES_DYEDREAM_DUST, ModItemTags.ORES_DYEDREAM_DUST);
        copy(ModBlockTags.ORES_AMBER_CANDY, ModItemTags.ORES_AMBER_CANDY);
        copy(ModBlockTags.ORES_IN_GROUND_CALCITE, ModItemTags.ORES_IN_GROUND_CALCITE);
        copy(ModBlockTags.ORE_RATES_SINGULAR, ModItemTags.ORE_RATES_SINGULAR);
        copy(ModBlockTags.ORE_RATES_DENSE, ModItemTags.ORE_RATES_DENSE);
        copy(ModBlockTags.ORES_TITANIUM, ModItemTags.ORES_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_TITANIUM, ModItemTags.STORAGE_BLOCKS_TITANIUM);
        copy(ModBlockTags.STORAGE_BLOCKS_RAW_TITANIUM, ModItemTags.STORAGE_BLOCKS_RAW_TITANIUM);
        copy(ModBlockTags.ORES_MOLTEN_GOLD, ModItemTags.ORES_MOLTEN_GOLD);
        copy(ModBlockTags.ORES_SOUL_DUST, ModItemTags.ORES_SOUL_DUST);
        copy(ModBlockTags.ORES_IN_GROUND_SOUL_SOIL, ModItemTags.ORES_IN_GROUND_SOUL_SOIL);
        copy(ModBlockTags.STORAGE_BLOCKS_MOLTEN_GOLD, ModItemTags.STORAGE_BLOCKS_MOLTEN_GOLD);
        copy(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_ALLOY, ModItemTags.STORAGE_BLOCKS_DYEDREAM_ALLOY);
        copy(ModBlockTags.STORAGE_BLOCKS_DYEDREAM_BUD, ModItemTags.STORAGE_BLOCKS_DYEDREAM_BUD);
        copy(ModBlockTags.STORAGE_BLOCKS_CHARGED_AMETHYST, ModItemTags.STORAGE_BLOCKS_CHARGED_AMETHYST);
        copy(ModBlockTags.STORAGE_BLOCKS_SALT, ModItemTags.STORAGE_BLOCKS_SALT);
        tag(ModItemTags.SALT).add(ModItems.SALT.get());
        tag(ModItemTags.FLOUR).add(ModItems.FLOUR.get());
        tag(ModItemTags.FLOUR_WHEAT).add(ModItems.FLOUR.get());
        tag(Tags.Items.FOODS_DOUGH).add(ModItems.DOUGH.get());
        tag(ModItemTags.COOKED_EGGS).add(ModItems.FRIED_EGG.get());
        tag(Tags.Items.EGGS).add(ModItems.PINK_EGG.get());
        tag(ModItemTags.GALAXY_JELLY_BOOST).add(Items.ELYTRA);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.ORES_COAL, Tags.Items.ORES_COAL);
        copy(Tags.Blocks.ORES_IRON, Tags.Items.ORES_IRON);
        copy(Tags.Blocks.ORES_COPPER, Tags.Items.ORES_COPPER);
        copy(Tags.Blocks.ORES_GOLD, Tags.Items.ORES_GOLD);
        copy(Tags.Blocks.ORES_REDSTONE, Tags.Items.ORES_REDSTONE);
        copy(Tags.Blocks.ORES_LAPIS, Tags.Items.ORES_LAPIS);
        copy(Tags.Blocks.ORES_DIAMOND, Tags.Items.ORES_DIAMOND);
        copy(Tags.Blocks.ORES_EMERALD, Tags.Items.ORES_EMERALD);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        copy(Tags.Blocks.GLASS_BLOCKS, Tags.Items.GLASS_BLOCKS);
        copy(Tags.Blocks.GLASS_PANES, Tags.Items.GLASS_PANES);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(ModBlockTags.DYEDREAM_DIRT, ModItemTags.DYEDREAM_DIRT);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);

        tag(ItemTags.FOX_FOOD)
                .add(ModItems.DYEDREAM_FRUIT.get());

        tag(Tags.Items.FOODS_FRUIT)
                .add(ModItems.DYEDREAM_FRUIT.get());

        // 工具标签
        tag(ItemTags.SWORDS)
                .add(ModItems.COPPER_SWORD.get())
                .add(ModItems.TITANIUM_SWORD.get())
                .add(ModItems.MOLTEN_GOLD_SWORD.get())
                .add(ModItems.DYEDREAM_SWORD.get())
                .add(ModItems.SHARP_MELT_DREAM_SWORD.get())
                .add(ModItems.HELLFIRE_SWORD.get())
                .add(ModItems.INFERNO_SWORD.get())
                .add(ModItems.TIDE_SWORD.get())
                .add(ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .add(ModItems.KUSANAGI.get())
                .add(ModItems.MURAKUMO_KUSANAGI.get())
                .add(ModItems.SWORD_EMBRYO.get())
                .add(ModItems.DESERT_SWORD.get())
                .add(ModItems.CHENJINGMEN_DESERT_SWORD.get())
                .add(ModItems.BROKEN_HERO_SWORD.get())
                .add(ModItems.SHADOW_EROSION_DAGGER.get())
                .add(ModItems.CONGEAL_WIND_IRON_SWORD.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_SWORD.get())
                .add(ModItems.WINDWREATHED_THUNDER_SPEAR.get())
                .add(ModItems.TERRA_BLADE.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.COPPER_PICKAXE.get())
                .add(ModItems.TITANIUM_PICKAXE.get())
                .add(ModItems.MOLTEN_GOLD_PICKAXE.get())
                .add(ModItems.DYEDREAM_PICKAXE.get())
                .add(ModItems.MELT_DREAM_PICKAXE.get())
                .add(ModItems.HELLFIRE_PICKAXE.get())
                .add(ModItems.CONGEAL_WIND_IRON_PICKAXE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get())
                .add(ModItems.SHADOW_EROSION_PICKAXE.get())
                .add(ModItems.SHADOW_ALLOY_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItems.COPPER_AXE.get())
                .add(ModItems.TITANIUM_AXE.get())
                .add(ModItems.MOLTEN_GOLD_AXE.get())
                .add(ModItems.DYEDREAM_AXE.get())
                .add(ModItems.MELT_DREAM_AXE.get())
                .add(ModItems.CONGEAL_WIND_IRON_AXE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_AXE.get())
                .add(ModItems.SHADOW_EROSION_AXE.get())
                .add(ModItems.SHADOW_ALLOY_AXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.COPPER_SHOVEL.get())
                .add(ModItems.TITANIUM_SHOVEL.get())
                .add(ModItems.MOLTEN_GOLD_SHOVEL.get())
                .add(ModItems.DYEDREAM_SHOVEL.get())
                .add(ModItems.MELT_DREAM_SHOVEL.get())
                .add(ModItems.CONGEAL_WIND_IRON_SHOVEL.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get())
                .add(ModItems.SHADOW_EROSION_SHOVEL.get())
                .add(ModItems.SHADOW_ALLOY_SHOVEL.get());
        tag(ItemTags.HOES)
                .add(ModItems.COPPER_HOE.get())
                .add(ModItems.TITANIUM_HOE.get())
                .add(ModItems.MOLTEN_GOLD_HOE.get())
                .add(ModItems.DYEDREAM_HOE.get())
                .add(ModItems.MELT_DREAM_HOE.get())
                .add(ModItems.CONGEAL_WIND_IRON_HOE.get())
                .add(ModItems.FLUFFY_WIND_ALLOY_HOE.get())
                .add(ModItems.SHADOW_EROSION_HOE.get())
                .add(ModItems.SHADOW_ALLOY_HOE.get());

        // 源对原版方解石的额外标签：可作为石制工具合成材料
        tag(ItemTags.STONE_TOOL_MATERIALS)
                .add(Items.CALCITE);
        tag(ItemTags.STONE_CRAFTING_MATERIALS)
                .add(Items.CALCITE);
        // 『北海若』引潮沿用来源可在附魔台获得三叉戟专属附魔；1.21 由物品标签驱动。
        tag(ItemTags.TRIDENT_ENCHANTABLE)
                .add(ModItems.BEIHAI_RUO_TIDE_SWORD.get())
                .add(ModItems.WINDWREATHED_THUNDER_SPEAR.get());
        tag(ItemTags.BOW_ENCHANTABLE).add(ModItems.FLUFFY_WIND_BOW.get());
        // 暗影合金弩：1.21 由物品标签驱动附魔可应用范围；加入弩标签获得多重射击/穿透/快速装填，
        // 加入耐久标签获得耐久/经验修补/消失诅咒；力量与无限由物品类覆写放行。
        tag(ItemTags.CROSSBOW_ENCHANTABLE).add(ModItems.SHADOW_ALLOY_CROSSBOW.get());
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.SHADOW_ALLOY_CROSSBOW.get());
        tag(ModItemTags.CHANNELING_ENCHANTABLE)
                .addTag(ItemTags.TRIDENT_ENCHANTABLE)
                .add(ModItems.FLUFFY_WIND_BOW.get());
        tag(ItemTags.HEAD_ARMOR).add(ModItems.COPPER_HELMET.get()).add(ModItems.MELT_DREAM_HELMET.get()).add(ModItems.TITANIUM_HELMET.get()).add(ModItems.SCULK_HELMET.get()).add(ModItems.DYEDREAM_HELMET.get()).add(ModItems.FLUFFY_WIND_ALLOY_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(ModItems.COPPER_CHESTPLATE.get()).add(ModItems.MELT_DREAM_CHESTPLATE.get()).add(ModItems.TITANIUM_CHESTPLATE.get()).add(ModItems.SCULK_CHESTPLATE.get()).add(ModItems.DYEDREAM_CHESTPLATE.get()).add(ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get()).add(ModItems.WIND_RUNNER_CLOAK.get());
        tag(ItemTags.LEG_ARMOR).add(ModItems.COPPER_LEGGINGS.get()).add(ModItems.MELT_DREAM_LEGGINGS.get()).add(ModItems.TITANIUM_LEGGINGS.get()).add(ModItems.SCULK_LEGGINGS.get()).add(ModItems.DYEDREAM_LEGGINGS.get()).add(ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(ModItems.COPPER_BOOTS.get()).add(ModItems.MELT_DREAM_BOOTS.get()).add(ModItems.TITANIUM_BOOTS.get()).add(ModItems.SCULK_BOOTS.get()).add(ModItems.DYEDREAM_BOOTS.get()).add(ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());

        tag(ModItemTags.PURE_HORROR_SOURCES)
                .add(ModItems.WHITE_CRYSTAL.get())
                .add(ModItems.SHADOW_HILT.get());

        tag(ModItemTags.LAMP_SHADOW_WEAPON)
                .add(ModItems.SHADOW_EROSION_DAGGER.get())
                .add(ModItems.SHADOW_EROSION_PICKAXE.get())
                .add(ModItems.SHADOW_EROSION_AXE.get())
                .add(ModItems.SHADOW_EROSION_SHOVEL.get())
                .add(ModItems.SHADOW_EROSION_HOE.get())
                .add(ModItems.SHADOW_ALLOY_PICKAXE.get())
                .add(ModItems.SHADOW_ALLOY_AXE.get())
                .add(ModItems.SHADOW_ALLOY_SHOVEL.get())
                .add(ModItems.SHADOW_ALLOY_HOE.get())
                .add(ModItems.SHADOW_ALLOY_CROSSBOW.get());

        // 金属锭标签
        tag(ModItemTags.INGOTS_TITANIUM)
                .add(ModItems.TITANIUM_INGOT.get());
        tag(ModItemTags.INGOTS_RAW_DYEDREAM_ALLOY)
                .add(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
        tag(ModItemTags.INGOTS_DYEDREAM_ALLOY)
                .add(ModItems.DYEDREAM_ALLOY_INGOT.get());
        tag(Tags.Items.INGOTS)
                .addTag(ModItemTags.INGOTS_TITANIUM)
                .addTag(ModItemTags.INGOTS_MOLTEN_GOLD)
                .addTag(ModItemTags.INGOTS_RAW_DYEDREAM_ALLOY)
                .addTag(ModItemTags.INGOTS_DYEDREAM_ALLOY)
                .addTag(ModItemTags.INGOTS_CONGEAL_WIND_IRON)
                .addTag(ModItemTags.INGOTS_FLUFFY_WIND_ALLOY)
                .addTag(ModItemTags.INGOTS_BLACK_METAL)
                .addTag(ModItemTags.INGOTS_SHADOW_ALLOY)
                .add(ModItems.MELT_DREAM_AURORIAN_STEEL_INGOT.get());
        tag(TagKey.create(BuiltInRegistries.ITEM.key(),
                ResourceLocation.fromNamespaceAndPath("forge", "ingots/melt_dream_aurorian_steel")))
                .add(ModItems.MELT_DREAM_AURORIAN_STEEL_INGOT.get());
        tag(ModItemTags.INGOTS_MOLTEN_GOLD)
                .add(ModItems.MOLTEN_GOLD_INGOT.get());
        tag(ModItemTags.INGOTS_CONGEAL_WIND_IRON)
                .add(ModItems.CONGEAL_WIND_IRON_INGOT.get());
        tag(ModItemTags.INGOTS_FLUFFY_WIND_ALLOY)
                .add(ModItems.FLUFFY_WIND_ALLOY_INGOT.get());

        tag(ModItemTags.INGOTS_BLACK_METAL).add(ModItems.BLACK_METAL_INGOT.get());
        tag(ModItemTags.INGOTS_SHADOW_ALLOY).add(ModItems.SHADOW_ALLOY_INGOT.get());

        // 金属粒标签
        tag(ModItemTags.NUGGETS_TITANIUM)
                .add(ModItems.TITANIUM_NUGGET.get());
        tag(ModItemTags.NUGGETS_DYEDREAM_ALLOY)
                .add(ModItems.DYEDREAM_ALLOY_NUGGET.get());
        tag(ModItemTags.NUGGETS_DYEDREAM_BUD)
                .add(ModItems.DYEDREAM_BUD_NUGGET.get());
        tag(Tags.Items.NUGGETS)
                .addTag(ModItemTags.NUGGETS_TITANIUM)
                .addTag(ModItemTags.NUGGETS_MOLTEN_GOLD)
                .addTag(ModItemTags.NUGGETS_DYEDREAM_ALLOY)
                .addTag(ModItemTags.NUGGETS_DYEDREAM_BUD)
                .addTag(ModItemTags.NUGGETS_CONGEAL_WIND_IRON)
                .addTag(ModItemTags.NUGGETS_BLACK_METAL);
        tag(ModItemTags.NUGGETS_MOLTEN_GOLD)
                .add(ModItems.MOLTEN_GOLD_NUGGET.get());
        tag(ModItemTags.NUGGETS_BLACK_METAL).add(ModItems.BLACK_METAL_NUGGET.get());
        tag(ModItemTags.NUGGETS_CONGEAL_WIND_IRON)
                .add(ModItems.CONGEAL_WIND_IRON_NUGGET.get());

        tag(ModItemTags.RAW_MATERIALS_TITANIUM)
                .add(ModItems.RAW_TITANIUM.get());
        tag(Tags.Items.RAW_MATERIALS)
                .addTag(ModItemTags.RAW_MATERIALS_TITANIUM)
                .addTag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD);
        tag(ModItemTags.RAW_MATERIALS_MOLTEN_GOLD)
                .add(ModItems.RAW_MOLTEN_GOLD.get());

        // ===== 灯影之下 =====
        tag(ModItemTags.SHADOW_STONES)
                .add(ModItems.SHADOW_STONE.get())
                .add(ModItems.SHADOW_STONE_BRICK.get())
                .add(ModItems.NARROW_SHADOW_STONE_BRICK.get())
                .add(ModItems.SHADOW_STONE_TILES.get())
                .add(ModItems.CRACKED_SHADOW_STONE_BRICK.get())
                .add(ModItems.CHISELED_SHADOW_STONE_BRICK.get());

        tag(ModItemTags.SHADOW_STEMS)
                .add(ModItems.SHADOW_STEM.get())
                .add(ModItems.SHADOW_HYPHAE.get())
                .add(ModItems.STRIPPED_SHADOW_STEM.get())
                .add(ModItems.STRIPPED_SHADOW_HYPHAE.get());

        tag(ModItemTags.STORAGE_BLOCKS_BLACK_METAL)
                .add(ModItems.BLACK_METAL_BLOCK.get());
        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(ModItemTags.STORAGE_BLOCKS_BLACK_METAL);

        tag(ItemTags.NON_FLAMMABLE_WOOD)
                .add(ModItems.SHADOW_STEM.get())
                .add(ModItems.SHADOW_HYPHAE.get())
                .add(ModItems.STRIPPED_SHADOW_STEM.get())
                .add(ModItems.STRIPPED_SHADOW_HYPHAE.get())
                .add(ModItems.SHADOW_PLANKS.get())
                .add(ModItems.SHADOW_STAIRS.get())
                .add(ModItems.SHADOW_SLAB.get())
                .add(ModItems.SHADOW_FENCE.get())
                .add(ModItems.SHADOW_FENCE_GATE.get())
                .add(ModItems.SHADOW_PANE.get())
                .add(ModItems.SHADOW_DOOR.get())
                .add(ModItems.SHADOW_TRAPDOOR.get())
                .add(ModItems.SHADOW_PRESSURE_PLATE.get())
                .add(ModItems.SHADOW_BUTTON.get());

        tag(Tags.Items.BOOKSHELVES)
                .add(ModItems.SHADOW_BOOKSHELF.get())
                .add(ModItems.WORN_SHADOW_BOOKSHELF.get())
                .add(ModItems.COBWEB_SHADOW_BOOKSHELF.get())
                .add(ModItems.KEY_SHADOW_BOOKSHELF.get());

        // ===== 风之旅途 =====
        tag(ModItemTags.FRUITS_FIG).add(ModItems.FIG.get());
        copy(ModBlockTags.ORES_CONGEAL_WIND, ModItemTags.ORES_CONGEAL_WIND);
        copy(ModBlockTags.ORES_WIND_RUNNER_CRYSTAL, ModItemTags.ORES_WIND_RUNNER_CRYSTAL);
        copy(ModBlockTags.ORES_IN_GROUND_CYAN_STONE, ModItemTags.ORES_IN_GROUND_CYAN_STONE);
        copy(ModBlockTags.ORES_IN_GROUND_THICK_CLOUD, ModItemTags.ORES_IN_GROUND_THICK_CLOUD);
        copy(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND_IRON, ModItemTags.STORAGE_BLOCKS_CONGEAL_WIND_IRON);
        copy(ModBlockTags.STORAGE_BLOCKS_FLUFFY_WIND_ALLOY, ModItemTags.STORAGE_BLOCKS_FLUFFY_WIND_ALLOY);
        copy(ModBlockTags.STORAGE_BLOCKS_CONGEAL_WIND, ModItemTags.STORAGE_BLOCKS_CONGEAL_WIND);
        copy(ModBlockTags.STORAGE_BLOCKS_WIND_RUNNER_CRYSTAL, ModItemTags.STORAGE_BLOCKS_WIND_RUNNER_CRYSTAL);

        tag(TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("pasterdream", "dream_notes")))
                .add(ModItems.BLUE_PRINT.get());

    }
}
