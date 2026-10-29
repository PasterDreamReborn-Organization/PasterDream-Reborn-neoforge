package com.pasterdream.pasterdreammod.datagen.lang;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, PasterDreamMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.BLACK_STICK.get(), "黑石棍");
        add(ModItems.TITANIUM_UPGRADE.get(), "钛金升级套件");
        add(ModItems.TITANIUM_SWORD.get(), "钛金剑");
        add(ModItems.TITANIUM_PICKAXE.get(), "钛金镐");
        add(ModItems.TITANIUM_AXE.get(), "钛金斧");
        add(ModItems.TITANIUM_SHOVEL.get(), "钛金锹");
        add(ModItems.TITANIUM_HOE.get(), "钛金锄");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "帕斯特之梦 | 装备");
        add(ModBlocks.TITANIUM_ORE.get(), "钛矿石");
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), "深层钛矿石");
        add(ModBlocks.RAW_TITANIUM_BLOCK.get(), "粗钛金块");
        add(ModBlocks.TITANIUM_BLOCK.get(), "钛金块");
        add(ModItems.RAW_TITANIUM.get(), "粗钛");
        add(ModItems.TITANIUM_INGOT.get(), "钛金锭");
        add(ModItems.TITANIUM_NUGGET.get(), "钛金粒");
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), "炙焰金矿石");
        add(ModBlocks.MOLTEN_GOLD_BLOCK.get(), "炙焰金块");
        add(ModItems.MOLTEN_GOLD_INGOT.get(), "炙焰金锭");
        add(ModItems.MOLTEN_GOLD_NUGGET.get(), "炙焰金粒");
        add(ModItems.RAW_MOLTEN_GOLD.get(), "粗炙焰金");
        add(ModItems.DYEDREAM_QUARTZ.get(), "染梦石英");
        add(ModItems.DYEDREAM_DUST.get(), "染梦粉尘");
        add(ModItems.DYEDREAM_DUST_PIECE.get(), "小撮染梦粉尘");
        add(ModItems.PINK_SLIMEBALL.get(), "粉红粘液球");
        add(ModItems.DYEDREAM_DYE.get(), "染梦染料");
        add("itemGroup.pasterdream.pasterdream_items_tab", "帕斯特之梦 | 物品");
        add("itemGroup.pasterdream.pasterdream_blocks_tab", "染梦世界");
        add("pasterdream.configuration.title", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.section.pasterdream.common.toml", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.section.pasterdream.common.toml.title", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.debugLogging", "调试日志");
        add("pasterdream.configuration.debugLogging.tooltip", "是否输出调试日志");
    }
}
