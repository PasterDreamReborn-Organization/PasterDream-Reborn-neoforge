package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.COTTON.get(), "Cotton");
        add(ModItems.SPOOL.get(), "Spool");
        add(ModItems.FABRIC.get(), "Fabric");
        add(ModItems.PROTECT_DECK.get(), "Protect Deck");
        add(ModItems.BLACK_STICK.get(), "Black Stick");
        add(ModItems.TITANIUM_UPGRADE.get(), "Titanium Upgrade");
        add(ModItems.TITANIUM_SWORD.get(), "Titanium Sword");
        add(ModItems.TITANIUM_PICKAXE.get(), "Titanium Pickaxe");
        add(ModItems.TITANIUM_AXE.get(), "Titanium Axe");
        add(ModItems.TITANIUM_SHOVEL.get(), "Titanium Shovel");
        add(ModItems.TITANIUM_HOE.get(), "Titanium Hoe");
        add(ModItems.TITANIUM_HELMET.get(), "Titanium Helmet");
        add(ModItems.TITANIUM_CHESTPLATE.get(), "Titanium Chestplate");
        add(ModItems.TITANIUM_LEGGINGS.get(), "Titanium Leggings");
        add(ModItems.TITANIUM_BOOTS.get(), "Titanium Boots");
        add("effect.pasterdream.titanium_armor", "Titanium Protection");
        add("tooltip.pasterdreammod.titanium_armor.1", "§7Set Bonus: §eTitanium Protection");
        add("tooltip.pasterdreammod.titanium_armor.2", "§7▪ §9Gain 2 Absorption hearts every 30s");
        add(ModItems.MOLTEN_GOLD_SWORD.get(), "Molten Gold Sword");
        add(ModItems.MOLTEN_GOLD_PICKAXE.get(), "Molten Gold Pickaxe");
        add(ModItems.MOLTEN_GOLD_AXE.get(), "Molten Gold Axe");
        add(ModItems.MOLTEN_GOLD_SHOVEL.get(), "Molten Gold Shovel");
        add(ModItems.MOLTEN_GOLD_HOE.get(), "Molten Gold Hoe");
        add("tooltip.pasterdreammod.molten_gold_sword.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.molten_gold_sword.2", "§7▪ §9Sets target on fire for 3 seconds");
        add("tooltip.pasterdreammod.molten_gold_tool.1", "Inlaid: §7None");
        add("tooltip.pasterdreammod.molten_gold_tool.2", "§7▪ §9Right-click to consume hunger for Haste");
        add("tooltip.pasterdreammod.molten_gold_tool.3", "§7▪ §9Cooldown: 10s");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "Paster Dream | Gear");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "Dyedream Quartz Ore");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "Dyedream Dust Ore");
        add(ModBlocks.PINK_SLIME_BLOCK.get(), "Pink Slime Block");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "Dyedream Quartz Block");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "Smooth Dyedream Quartz Block");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "Dyedream Quartz Bricks");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "Dyedream Quartz Pillar");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "Chiseled Dyedream Quartz Block");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "Dyedream Quartz Stairs");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "Dyedream Quartz Slab");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "Dyedream Quartz Wall");
        add(ModBlocks.TITANIUM_ORE.get(), "Titanium Ore");
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), "Deepslate Titanium Ore");
        add(ModBlocks.RAW_TITANIUM_BLOCK.get(), "Raw Titanium Block");
        add(ModBlocks.TITANIUM_BLOCK.get(), "Titanium Block");
        add(ModItems.RAW_TITANIUM.get(), "Raw Titanium");
        add(ModItems.TITANIUM_INGOT.get(), "Titanium Ingot");
        add(ModItems.TITANIUM_NUGGET.get(), "Titanium Nugget");
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), "Molten Gold Ore");
        add(ModBlocks.MOLTEN_GOLD_BLOCK.get(), "Molten Gold Block");
        add(ModItems.MOLTEN_GOLD_INGOT.get(), "Molten Gold Ingot");
        add(ModItems.MOLTEN_GOLD_NUGGET.get(), "Molten Gold Nugget");
        add(ModItems.RAW_MOLTEN_GOLD.get(), "Raw Molten Gold");
        add(ModItems.DYEDREAM_QUARTZ.get(), "Dyedream Quartz");
        add(ModItems.DYEDREAM_DUST.get(), "Dyedream Dust");
        add(ModItems.DYEDREAM_DUST_PIECE.get(), "Dyedream Dust Piece");
        add(ModItems.PINK_SLIMEBALL.get(), "Pink Slimeball");
        add(ModItems.DYEDREAM_DYE.get(), "Dyedream Dye");
        add("itemGroup.pasterdream.pasterdream_items_tab", "Paster Dream | Items");
        add("itemGroup.pasterdream.pasterdream_blocks_tab", "Dyedream World");
        add("pasterdream.configuration.title", "PasterDream: Reborn Configs");
        add("pasterdream.configuration.section.pasterdream.common.toml", "PasterDream: Reborn Configs");
        add("pasterdream.configuration.section.pasterdream.common.toml.title", "PasterDream: Reborn Configs");
        add("pasterdream.configuration.debugLogging", "Debug Logging");
        add("pasterdream.configuration.debugLogging.tooltip", "Enable debug logging");
    }
}
