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
        add(ModItems.COTTON.get(), "棉花");
        add(ModItems.SPOOL.get(), "线轴");
        add(ModItems.FABRIC.get(), "纺织布料");
        add(ModItems.PROTECT_DECK.get(), "护甲板");
        add(ModItems.BLACK_STICK.get(), "黑石棍");
        add(ModItems.TITANIUM_UPGRADE.get(), "钛金升级套件");
        add(ModItems.TITANIUM_SWORD.get(), "钛金剑");
        add(ModItems.TITANIUM_PICKAXE.get(), "钛金镐");
        add(ModItems.TITANIUM_AXE.get(), "钛金斧");
        add(ModItems.TITANIUM_SHOVEL.get(), "钛金锹");
        add(ModItems.TITANIUM_HOE.get(), "钛金锄");
        add(ModItems.TITANIUM_HELMET.get(), "钛金头盔");
        add(ModItems.TITANIUM_CHESTPLATE.get(), "钛金胸甲");
        add(ModItems.TITANIUM_LEGGINGS.get(), "钛金护腿");
        add(ModItems.TITANIUM_BOOTS.get(), "钛金靴子");
        add("effect.pasterdream.titanium_armor", "钛金守护");
        add("tooltip.pasterdreammod.titanium_armor.1", "§7套装效果：§e钛金守护");
        add("tooltip.pasterdreammod.titanium_armor.2", "§7▪ §9每 30 秒获得 2 颗吸收之♡");
        add(ModItems.MOLTEN_GOLD_SWORD.get(), "炙焰金剑");
        add(ModItems.MOLTEN_GOLD_PICKAXE.get(), "炙焰金镐");
        add(ModItems.MOLTEN_GOLD_AXE.get(), "炙焰金斧");
        add(ModItems.MOLTEN_GOLD_SHOVEL.get(), "炙焰金锹");
        add(ModItems.MOLTEN_GOLD_HOE.get(), "炙焰金锄");
        add("tooltip.pasterdreammod.molten_gold_sword.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.molten_gold_sword.2", "§7▪ §9攻击将使目标引燃3秒");
        add("tooltip.pasterdreammod.molten_gold_tool.1", "镶嵌：§7无");
        add("tooltip.pasterdreammod.molten_gold_tool.2", "§7▪ §9右键使用将消耗饱食度来获得短暂急迫效果");
        add("tooltip.pasterdreammod.molten_gold_tool.3", "§7▪ §9冷却时间：10秒");
        add("itemGroup.pasterdream.pasterdream_gear_tab", "帕斯特之梦 | 装备");
        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), "染梦石英矿石");
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), "染梦粉尘矿石");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英块");
        add(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get(), "平滑染梦石英块");
        add(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英砖");
        add(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get(), "染梦石英柱");
        add(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get(), "錾制染梦石英块");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get(), "染梦石英楼梯");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), "染梦石英台阶");
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get(), "染梦石英墙");
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
