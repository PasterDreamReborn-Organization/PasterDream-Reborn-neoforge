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
        add(ModItems.BLACK_STICK.get(), "Black Stick");
        add(ModItems.TITANIUM_UPGRADE.get(), "Titanium Upgrade");
        add(ModItems.TITANIUM_SWORD.get(), "Titanium Sword");
        add(ModItems.TITANIUM_PICKAXE.get(), "Titanium Pickaxe");
        add(ModItems.TITANIUM_AXE.get(), "Titanium Axe");
        add(ModItems.TITANIUM_SHOVEL.get(), "Titanium Shovel");
        add(ModItems.TITANIUM_HOE.get(), "Titanium Hoe");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "Paster Dream | Gear");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "Dyedream Quartz Ore");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "Dyedream Dust Ore");
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
