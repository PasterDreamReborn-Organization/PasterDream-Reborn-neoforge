package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    // 物品/装备图标已按来源恢复；方块图标所需 DYEDREAM_GRASS_BLOCK 尚未迁入，暂用钛矿。保留来源物品栏 ID 和条目顺序。
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_items_tab"))
                    .icon(() -> ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
                        output.accept(ModItems.DYEDREAM_DUST.get());
                        output.accept(ModItems.DYEDREAM_DUST_PIECE.get());
                        output.accept(ModItems.DYEDREAM_DYE.get());
                        output.accept(ModItems.DYEDREAM_BUD_NUGGET.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ.get());
                        output.accept(ModItems.MELT_DREAM_COIN.get());
                        output.accept(ModItems.MELT_DREAM_COIN_PILE.get());
                        output.accept(ModItems.RAW_TITANIUM.get());
                        output.accept(ModItems.RAW_MOLTEN_GOLD.get());
                        output.accept(ModItems.TITANIUM_INGOT.get());
                        output.accept(ModItems.MOLTEN_GOLD_INGOT.get());
                        output.accept(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_INGOT.get());
                        output.accept(ModItems.TITANIUM_NUGGET.get());
                        output.accept(ModItems.MOLTEN_GOLD_NUGGET.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_NUGGET.get());
                        output.accept(ModItems.BLACK_STICK.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.PROTECT_DECK.get());
                        output.accept(ModItems.COTTON.get());
                        output.accept(ModItems.SCULK_HEART.get());
                        output.accept(ModItems.SPOOL.get());
                        output.accept(ModItems.FABRIC.get());
                        output.accept(ModItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(ModItems.ELDER_GUARDIAN_SCALE.get());
                        // 蔚蓝露滴在来源属于食物栏；食物栏尚未迁入，暂置于物品栏。
                        output.accept(ModItems.BLUE_DEW.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_blocks_tab"))
                    .icon(() -> ModItems.TITANIUM_ORE.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_QUARTZ_ORE.get());
                        output.accept(ModItems.DYEDREAM_DUST_ORE.get());
                        output.accept(ModItems.TITANIUM_ORE.get());
                        output.accept(ModItems.DEEPSLATE_TITANIUM_ORE.get());
                        output.accept(ModItems.MOLTEN_GOLD_ORE.get());
                        output.accept(ModItems.RAW_TITANIUM_BLOCK.get());
                        output.accept(ModItems.TITANIUM_BLOCK.get());
                        output.accept(ModItems.MOLTEN_GOLD_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_BLOCK.get());
                        output.accept(ModItems.PINK_SLIME_BLOCK.get());
                        output.accept(ModItems.LARGE_DYEDREAM_BUD.get());
                        output.accept(ModItems.MEDIUM_DYEDREAM_BUD.get());
                        output.accept(ModItems.SMALL_DYEDREAM_BUD.get());
                        output.accept(ModItems.DYEDREAM_BUDDING_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_BUD_BLOCK.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_GEAR_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_gear_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_gear_tab"))
                    .icon(() -> ModItems.SHARP_MELT_DREAM_SWORD.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TITANIUM_UPGRADE.get());
                        output.accept(ModItems.SCULK_UPGRADE.get());
                        output.accept(ModItems.DYEDREAM_UPGRADE.get());
                        output.accept(ModItems.TITANIUM_SWORD.get());
                        output.accept(ModItems.TITANIUM_PICKAXE.get());
                        output.accept(ModItems.TITANIUM_AXE.get());
                        output.accept(ModItems.TITANIUM_SHOVEL.get());
                        output.accept(ModItems.TITANIUM_HOE.get());
                        // 钛金装备
                        output.accept(ModItems.TITANIUM_HELMET.get());
                        output.accept(ModItems.TITANIUM_CHESTPLATE.get());
                        output.accept(ModItems.TITANIUM_LEGGINGS.get());
                        output.accept(ModItems.TITANIUM_BOOTS.get());
                        // 染梦合金工具
                        output.accept(ModItems.DYEDREAM_SWORD.get());
                        output.accept(ModItems.DYEDREAM_PICKAXE.get());
                        output.accept(ModItems.DYEDREAM_AXE.get());
                        output.accept(ModItems.DYEDREAM_SHOVEL.get());
                        output.accept(ModItems.DYEDREAM_HOE.get());
                        output.accept(ModItems.DYEDREAM_HAMMER.get());
                        output.accept(ModItems.MELT_DREAM_PICKAXE.get());
                        output.accept(ModItems.MELT_DREAM_AXE.get());
                        output.accept(ModItems.MELT_DREAM_SHOVEL.get());
                        output.accept(ModItems.MELT_DREAM_HOE.get());
                        output.accept(ModItems.SHARP_MELT_DREAM_SWORD.get());
                        // 染梦合金装备
                        output.accept(ModItems.DYEDREAM_HELMET.get());
                        output.accept(ModItems.DYEDREAM_CHESTPLATE.get());
                        output.accept(ModItems.DYEDREAM_LEGGINGS.get());
                        output.accept(ModItems.DYEDREAM_BOOTS.get());
                        // 回响装备
                        output.accept(ModItems.SCULK_HELMET.get());
                        output.accept(ModItems.SCULK_CHESTPLATE.get());
                        output.accept(ModItems.SCULK_LEGGINGS.get());
                        output.accept(ModItems.SCULK_BOOTS.get());
                        // 炙焰金工具
                        output.accept(ModItems.MOLTEN_GOLD_SWORD.get());
                        output.accept(ModItems.MOLTEN_GOLD_PICKAXE.get());
                        output.accept(ModItems.MOLTEN_GOLD_AXE.get());
                        output.accept(ModItems.MOLTEN_GOLD_SHOVEL.get());
                        output.accept(ModItems.MOLTEN_GOLD_HOE.get());
                        // 狱炎工具
                        output.accept(ModItems.HELLFIRE_SWORD.get());
                        output.accept(ModItems.HELLFIRE_PICKAXE.get());
                        // 独立武器
                        output.accept(ModItems.TIDE_SWORD.get());
                        output.accept(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
