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
                        output.accept(ModItems.CONGEAL_WIND_IRON_INGOT.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_INGOT.get());
                        output.accept(ModItems.TITANIUM_NUGGET.get());
                        output.accept(ModItems.MOLTEN_GOLD_NUGGET.get());
                        output.accept(ModItems.SOUL_DUST.get());
                        output.accept(ModItems.SOUL_ESSENCE.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_NUGGET.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_NUGGET.get());
                        output.accept(ModItems.BLACK_STICK.get());
                        output.accept(ModItems.BLACK_METAL_INGOT.get());
                        output.accept(ModItems.BLACK_METAL_NUGGET.get());
                        output.accept(ModItems.SHADOW_ALLOY_INGOT.get());
                        output.accept(ModItems.NIGHTMARE_FUEL.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.PROTECT_DECK.get());
                        output.accept(ModItems.COTTON.get());
                        output.accept(ModItems.SCULK_HEART.get());
                        output.accept(ModItems.SPOOL.get());
                        output.accept(ModItems.FABRIC.get());
                        output.accept(ModItems.CONGEAL_WIND.get());
                        output.accept(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get());
                        output.accept(ModItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(ModItems.ELDER_GUARDIAN_SCALE.get());
                        // 饰品原胚
                        output.accept(ModItems.EMBRYO_CHARM.get());
                        output.accept(ModItems.EMBRYO_NECKLACE.get());
                        output.accept(ModItems.EMBRYO_RING.get());
                        output.accept(ModItems.EMBRYO_BELT.get());
                    }).build());

    // 食物栏图标暂用蔚蓝露滴；来源图标 DYEDREAM_FRUIT 尚未迁入。
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_FOOD_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_food_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_food_tab"))
                    .icon(() -> ModItems.BLUE_DEW.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BLUE_DEW.get());
                        output.accept(ModItems.JUNGLE_SPORE.get());
                        output.accept(ModItems.RICE_CAKE.get());
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
                        output.accept(ModItems.SOUL_ORE.get());
                        output.accept(ModItems.RAW_TITANIUM_BLOCK.get());
                        output.accept(ModItems.TITANIUM_BLOCK.get());
                        output.accept(ModItems.MOLTEN_GOLD_BLOCK.get());
                        output.accept(ModItems.SHADOW.get());
                        output.accept(ModItems.THICK_SHADOW.get());
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
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get());
                        output.accept(ModItems.ATTACK_ENHANCE_STONE.get());
                        output.accept(ModItems.LUCK_ENHANCE_STONE.get());
                        // 铜工具与装备
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.COPPER_PICKAXE.get());
                        output.accept(ModItems.COPPER_AXE.get());
                        output.accept(ModItems.COPPER_SHOVEL.get());
                        output.accept(ModItems.COPPER_HOE.get());
                        output.accept(ModItems.COPPER_HELMET.get());
                        output.accept(ModItems.COPPER_CHESTPLATE.get());
                        output.accept(ModItems.COPPER_LEGGINGS.get());
                        output.accept(ModItems.COPPER_BOOTS.get());
                        // 凝风铁工具
                        output.accept(ModItems.CONGEAL_WIND_IRON_SWORD.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_PICKAXE.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_AXE.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_SHOVEL.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_HOE.get());
                        // 萦风雷矛与萦风合金工具
                        output.accept(ModItems.WINDWREATHED_THUNDER_SPEAR.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_SWORD.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_AXE.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_HOE.get());
                        output.accept(ModItems.FLUFFY_WIND_BOW.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_HELMET.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());
                        output.accept(ModItems.WIND_RUNNER_CLOAK.get());
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
                        // 融梦水晶装备
                        output.accept(ModItems.MELT_DREAM_HELMET.get());
                        output.accept(ModItems.MELT_DREAM_CHESTPLATE.get());
                        output.accept(ModItems.MELT_DREAM_LEGGINGS.get());
                        output.accept(ModItems.MELT_DREAM_BOOTS.get());
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
                        output.accept(ModItems.INFERNO_SWORD.get());
                        output.accept(ModItems.SHADOW_EROSION_DAGGER.get());
                        output.accept(ModItems.SHADOW_EROSION_PICKAXE.get());
                        output.accept(ModItems.SHADOW_EROSION_AXE.get());
                        output.accept(ModItems.SHADOW_EROSION_SHOVEL.get());
                        output.accept(ModItems.SHADOW_EROSION_HOE.get());
                        output.accept(ModItems.SHADOW_ALLOY_PICKAXE.get());
                        output.accept(ModItems.SHADOW_ALLOY_AXE.get());
                        output.accept(ModItems.SHADOW_ALLOY_SHOVEL.get());
                        output.accept(ModItems.SHADOW_ALLOY_HOE.get());
                        // 独立武器
                        output.accept(ModItems.TIDE_SWORD.get());
                        output.accept(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
                        output.accept(ModItems.KUSANAGI.get());
                        output.accept(ModItems.MURAKUMO_KUSANAGI.get());
                        output.accept(ModItems.SWORD_EMBRYO.get());
                        output.accept(ModItems.DESERT_SWORD.get());
                        output.accept(ModItems.CHENJINGMEN_DESERT_SWORD.get());
                        output.accept(ModItems.BROKEN_HERO_SWORD.get());
                        // 饰品
                        output.accept(ModItems.GOLD_CHARM.get());
                        output.accept(ModItems.ENDEYE_CHARM.get());
                        output.accept(ModItems.SEA_CHARM.get());
                        output.accept(ModItems.CARAPAX_CHARM.get());
                        output.accept(ModItems.WORLDTREE_SEEDPOD.get());
                        output.accept(ModItems.GARLAND.get());
                        output.accept(ModItems.SNOW_VOW_HEAD.get());
                        output.accept(ModItems.FRACTURED_ANGEL_STATUE.get());
                    }).build());

    // ===== 设备 =====
    // 来源: 旧 paster_tab_5 (功能方块), 按原顺序
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_EQUIPMENT_TAB = CREATIVE_MODE_TABS.register("pasterdream_equipment_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_equipment_tab"))
                    .icon(() -> ModItems.QYM_DOLL.toStack())
                    .displayItems((parameters, output) ->
                    {
                        output.accept(ModItems.QYM_DOLL.get());
                        output.accept(ModItems.UUZ_DOLL.get());
                        output.accept(ModItems.LIFE_CRYSTAL.get());
                        output.accept(ModItems.LOST_SWORD_TOMB.get());
                        output.accept(ModItems.DESERT_HERO_TOMB.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
