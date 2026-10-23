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

    // 原图标所需内容尚未迁入，暂用本批次材料；保留来源物品栏 ID 和条目顺序。
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_items_tab"))
                    .icon(() -> ModItems.TITANIUM_INGOT.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.RAW_TITANIUM.get());
                        output.accept(ModItems.RAW_MOLTEN_GOLD.get());
                        output.accept(ModItems.TITANIUM_INGOT.get());
                        output.accept(ModItems.MOLTEN_GOLD_INGOT.get());
                        output.accept(ModItems.TITANIUM_NUGGET.get());
                        output.accept(ModItems.MOLTEN_GOLD_NUGGET.get());
                        output.accept(ModItems.BLACK_STICK.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_blocks_tab"))
                    .icon(() -> ModItems.TITANIUM_ORE.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TITANIUM_ORE.get());
                        output.accept(ModItems.DEEPSLATE_TITANIUM_ORE.get());
                        output.accept(ModItems.MOLTEN_GOLD_ORE.get());
                        output.accept(ModItems.RAW_TITANIUM_BLOCK.get());
                        output.accept(ModItems.TITANIUM_BLOCK.get());
                        output.accept(ModItems.MOLTEN_GOLD_BLOCK.get());
                    }).build());

    // 来源装备栏图标所需武器尚未迁入，暂用钛金剑。
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_GEAR_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_gear_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_gear_tab"))
                    .icon(() -> ModItems.TITANIUM_SWORD.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TITANIUM_UPGRADE.get());
                        output.accept(ModItems.TITANIUM_SWORD.get());
                        output.accept(ModItems.TITANIUM_PICKAXE.get());
                        output.accept(ModItems.TITANIUM_AXE.get());
                        output.accept(ModItems.TITANIUM_SHOVEL.get());
                        output.accept(ModItems.TITANIUM_HOE.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
