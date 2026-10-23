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
        add(ModBlocks.TITANIUM_ORE.get(), "钛矿石");
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), "深层钛矿石");
        add(ModBlocks.RAW_TITANIUM_BLOCK.get(), "粗钛金块");
        add(ModBlocks.TITANIUM_BLOCK.get(), "钛金块");
        add(ModItems.RAW_TITANIUM.get(), "粗钛");
        add(ModItems.TITANIUM_INGOT.get(), "钛金锭");
        add(ModItems.TITANIUM_NUGGET.get(), "钛金粒");
        add("itemGroup.pasterdream.pasterdream_items_tab", "帕斯特之梦 | 物品");
        add("itemGroup.pasterdream.pasterdream_blocks_tab", "染梦世界");
        add("pasterdream.configuration.title", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.section.pasterdream.common.toml", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.section.pasterdream.common.toml.title", "帕斯特之梦：重生 配置");
        add("pasterdream.configuration.debugLogging", "调试日志");
        add("pasterdream.configuration.debugLogging.tooltip", "是否输出调试日志");
    }
}
