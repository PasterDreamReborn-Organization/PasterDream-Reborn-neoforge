package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PasterDreamMod.MOD_ID);

    // 物品/装备图标已按来源恢复。保留来源物品栏 ID 和条目顺序。
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
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
                        output.accept(ModItems.GLASS_JAR_OF_INK.get());
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
                        output.accept(ModItems.MAGIC_STONE.get());
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
                        output.accept(ModItems.CHARGED_AMETHYST.get());
                        output.accept(ModItems.PERGAMYN.get());
                        output.accept(ModItems.REED_ROD.get());
                        output.accept(ModItems.RYE_SEED.get());
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.COARSE_SALT.get());
                        // 饰品原胚
                        output.accept(ModItems.EMBRYO_CHARM.get());
                        output.accept(ModItems.EMBRYO_NECKLACE.get());
                        output.accept(ModItems.EMBRYO_RING.get());
                        output.accept(ModItems.EMBRYO_BELT.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_FOOD_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_food_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_food_tab"))
                    .icon(() -> ModItems.DYEDREAM_FRUIT.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GLASS_JAR.get());
                        output.accept(ModItems.GLASS_JAR_OF_WATER.get());
                        output.accept(ModItems.GLASS_JAR_OF_MILK.get());
                        output.accept(ModItems.GLASS_JAR_OF_YEAST.get());
                        output.accept(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
                        output.accept(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
                        output.accept(ModItems.BLUE_DEW.get());
                        output.accept(ModItems.JUNGLE_SPORE.get());
                        output.accept(ModItems.RICE_CAKE.get());
                        output.accept(ModItems.DYEDREAM_FRUIT.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_blocks_tab"))
                    .icon(() -> ModItems.DYEDREAM_GRASS_BLOCK.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_GRASS_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_FARMLAND.get());
                        output.accept(ModItems.DYEDREAM_DIRT.get());
                        output.accept(ModItems.DYEDREAM_SAND.get());
                        output.accept(ModItems.DYEDREAM_LOG.get());
                        output.accept(ModItems.DYEDREAM_WOOD.get());
                        output.accept(ModItems.DYEDREAM_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_WORLDTREE_LEAVES.get());
                        output.accept(ModItems.DYEDREAM_PLANKS.get());
                        output.accept(ModItems.DYEDREAM_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_SLAB.get());
                        output.accept(ModItems.DYEDREAM_FENCE.get());
                        output.accept(ModItems.DYEDREAM_FENCE_GATE.get());
                        output.accept(ModItems.DYEDREAM_PANE.get());
                        output.accept(ModItems.DYEDREAM_DOOR.get());
                        output.accept(ModItems.DYEDREAM_TRAPDOOR.get());
                        output.accept(ModItems.DYEDREAM_PRESSURE_PLATE.get());
                        output.accept(ModItems.DYEDREAM_BUTTON.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_ORE.get());
                        output.accept(ModItems.DYEDREAM_DUST_ORE.get());
                        output.accept(ModItems.AMBER_CANDY_ORE.get());
                        output.accept(ModItems.CALCITE_COAL_ORE.get());
                        output.accept(ModItems.CALCITE_IRON_ORE.get());
                        output.accept(ModItems.CALCITE_COPPER_ORE.get());
                        output.accept(ModItems.CALCITE_GOLD_ORE.get());
                        output.accept(ModItems.CALCITE_REDSTONE_ORE.get());
                        output.accept(ModItems.CALCITE_LAPIS_ORE.get());
                        output.accept(ModItems.CALCITE_DIAMOND_ORE.get());
                        output.accept(ModItems.CALCITE_EMERALD_ORE.get());
                        output.accept(ModItems.DYEDREAM_ALLOY_BLOCK.get());
                        output.accept(ModItems.PINK_SLIME_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_BLOCK.get());
                        output.accept(ModItems.PINK_MUSHROOM_STEM.get());
                        output.accept(ModItems.PINK_SHROOMLIGHT.get());
                        output.accept(ModItems.DYEDREAM_ICE.get());
                        output.accept(ModItems.DYEDREAM_PACKED_ICE.get());
                        output.accept(ModItems.BIG_BUBBLE.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_SLAB.get());
                        output.accept(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.get());
                        output.accept(ModItems.DYEDREAM_SANDSTONE.get());
                        output.accept(ModItems.DYEDREAM_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_SANDSTONE_SLAB.get());
                        output.accept(ModItems.DYEDREAM_SANDSTONE_WALL.get());
                        output.accept(ModItems.CUT_DYEDREAM_SANDSTONE.get());
                        output.accept(ModItems.CUT_DYEDREAM_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.CUT_DYEDREAM_SANDSTONE_SLAB.get());
                        output.accept(ModItems.CUT_DYEDREAM_SANDSTONE_WALL.get());
                        output.accept(ModItems.CHISELED_DYEDREAM_SANDSTONE.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_SANDSTONE.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get());
                        output.accept(ModItems.SMOOTH_DYEDREAM_SANDSTONE_WALL.get());
                        output.accept(ModItems.DYEDREAM_CRYSTAL_LANTERN.get());
                        output.accept(ModItems.DYEDREAM_LANTERN.get());
                        output.accept(ModItems.DYEDREAM_GLASS.get());
                        output.accept(ModItems.DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.CARVE_DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASS.get());
                        output.accept(ModItems.GOLD_CARVE_DYEDREAM_GLASS_PANE.get());
                        output.accept(ModItems.LARGE_DYEDREAM_BUD.get());
                        output.accept(ModItems.MEDIUM_DYEDREAM_BUD.get());
                        output.accept(ModItems.SMALL_DYEDREAM_BUD.get());
                        output.accept(ModItems.DYEDREAM_BUDDING_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_BUD_BLOCK.get());
                        output.accept(ModItems.DYEDREAM_BUD_BRICKS.get());
                        output.accept(ModItems.DYEDREAM_BUD_STAIRS.get());
                        output.accept(ModItems.DYEDREAM_BUD_SLAB.get());
                        output.accept(ModItems.DYEDREAM_BUD_WALL.get());
                        output.accept(ModItems.ICE_STONE.get());
                        output.accept(ModItems.ICE_BUD.get());
                        output.accept(ModItems.CLOUD.get());
                        output.accept(ModItems.DARK_CLOUD.get());
                        output.accept(ModItems.POLISHED_CALCITE.get());
                        output.accept(ModItems.POLISHED_CALCITE_STAIRS.get());
                        output.accept(ModItems.POLISHED_CALCITE_SLAB.get());
                        output.accept(ModItems.POLISHED_CALCITE_WALL.get());
                        output.accept(ModItems.CALCITE_TILES.get());
                        output.accept(ModItems.CALCITE_TILES_STAIRS.get());
                        output.accept(ModItems.CALCITE_TILES_SLAB.get());
                        output.accept(ModItems.CALCITE_TILES_WALL.get());
                        output.accept(ModItems.CALCITE_CONE.get());
                        output.accept(ModItems.TITANIUM_ORE.get());
                        output.accept(ModItems.DEEPSLATE_TITANIUM_ORE.get());
                        output.accept(ModItems.MOLTEN_GOLD_ORE.get());
                        output.accept(ModItems.SOUL_ORE.get());
                        output.accept(ModItems.RAW_TITANIUM_BLOCK.get());
                        output.accept(ModItems.SALT_BLOCK.get());
                        output.accept(ModItems.TITANIUM_BLOCK.get());
                        output.accept(ModItems.MOLTEN_GOLD_BLOCK.get());
                        output.accept(ModItems.CHARGED_AMETHYST_BLOCK.get());
                        // 暗影物品栏尚未迁移，暂留于此避免创造模式检索丢失。
                        output.accept(ModItems.SHADOW.get());
                        output.accept(ModItems.THICK_SHADOW.get());
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
                        output.accept(ModItems.SHARP_MELT_DREAM_SWORD.get());
                        output.accept(ModItems.DYEDREAM_PICKAXE.get());
                        output.accept(ModItems.DYEDREAM_AXE.get());
                        output.accept(ModItems.DYEDREAM_SHOVEL.get());
                        output.accept(ModItems.DYEDREAM_HOE.get());
                        output.accept(ModItems.DYEDREAM_HAMMER.get());
                        // 染梦合金装备
                        output.accept(ModItems.DYEDREAM_HELMET.get());
                        output.accept(ModItems.DYEDREAM_CHESTPLATE.get());
                        output.accept(ModItems.DYEDREAM_LEGGINGS.get());
                        output.accept(ModItems.DYEDREAM_BOOTS.get());
                        // 融梦水晶工具
                        output.accept(ModItems.MELT_DREAM_PICKAXE.get());
                        output.accept(ModItems.MELT_DREAM_AXE.get());
                        output.accept(ModItems.MELT_DREAM_SHOVEL.get());
                        output.accept(ModItems.MELT_DREAM_HOE.get());
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
                        output.accept(ModItems.FOURLEAF_CLOVER_CURIO.get());
                        output.accept(ModItems.SNOW_VOW_HEAD.get());
                        output.accept(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get());
                        output.accept(ModItems.LIGHT_BUTTERFLY_CURIO.get());
                        output.accept(ModItems.BRIGHT_BUTTERFLY_CURIO.get());
                        output.accept(ModItems.RABBIT_FOOT_NECKLACE.get());
                        output.accept(ModItems.FEATHER_NECKLACE.get());
                        output.accept(ModItems.HEALTH_NECKLACE.get());
                        output.accept(ModItems.FIRE_NECKLACE.get());
                        output.accept(ModItems.CROSS_NECKLACE.get());
                        output.accept(ModItems.NATURE_BELT.get());
                        output.accept(ModItems.TRAVELER_BELT.get());
                        output.accept(ModItems.DREAM_TRAVELER_BELT.get());
                        output.accept(StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 1));
                        output.accept(StrikeRingItem.createWithLv(ModItems.STRIKE_RING.get(), 2));
                        output.accept(ModItems.COUNTER_RING.get());
                        output.accept(ModItems.MELT_DREAM_ENERGY_RING.get());
                        output.accept(ModItems.STORAGE_BAG.get());
                        output.accept(ModItems.LARGE_STORAGE_BAG.get());
                        output.accept(ModItems.SHADOW_BREATH.get());
                        output.accept(ModItems.WAR_FLAG.get());
                        output.accept(ModItems.BOBOJI_CURIO.get());
                        output.accept(ModItems.WIND_SPIRIT.get());
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
                        output.accept(ModItems.MORTAR.get());
                        output.accept(ModItems.DYEDREAM_DESK.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_PLANTS_TAB = CREATIVE_MODE_TABS.register("pasterdream_plants_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_plants_tab"))
                    .icon(() -> ModItems.DYEDREAM_SAPLING.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DYEDREAM_SAPLING.get());
                        output.accept(ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
                        output.accept(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get());
                        output.accept(ModItems.LIGHT_BALL_CROP_AGE_0.get());
                        output.accept(ModItems.LIGHT_BALL_CROP_AGE_1.get());
                        output.accept(ModItems.CLOUD_CROP_AGE_0.get());
                        output.accept(ModItems.CLOUD_CROP_AGE_1.get());
                        output.accept(ModItems.STEM_GRASS.get());
                        output.accept(ModItems.TALL_STEM_GRASS.get());
                        output.accept(ModItems.PINK_MUSHROOM.get());
                        output.accept(ModItems.TALL_PINK_MUSHROOM.get());
                        output.accept(ModItems.POLISHED_CALCITE_STALICRIPE.get());
                        output.accept(ModItems.SMALL_POLISHED_CALCITE_STALICRIPE.get());
                        output.accept(ModItems.DYEDREAM_MOSS.get());
                        output.accept(ModItems.LINHT_FLOWER.get());
                        output.accept(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.get());
                        output.accept(ModItems.SINGULARITY_FERN.get());
                        output.accept(ModItems.DYEDREAM_VINE.get());
                        output.accept(ModItems.DYEDREAM_LILY_PAD.get());
                        output.accept(ModItems.DYEDREAM_SEAGRASS.get());
                        output.accept(ModItems.DYEDREAM_LOTUS.get());
                        output.accept(ModItems.DREAMING_LOTUS.get());
                        output.accept(ModItems.MISTY_DREAMING_LOTUS.get());
                        output.accept(ModItems.EDELWEISS.get());
                        output.accept(ModItems.NIPPY_EDELWEISS.get());
                        output.accept(ModItems.WHITE_COROLLA_CROP_AGE_0.get());
                        output.accept(ModItems.WHITE_COROLLA_CROP_AGE_1.get());
                        output.accept(ModItems.COTTON_CROP_AGE_0.get());
                        output.accept(ModItems.COTTON_CROP_AGE_1.get());
                        output.accept(ModItems.FERRARIA_CRISPA.get());
                        output.accept(ModItems.EUSTOMA.get());
                        output.accept(ModItems.JUNGLE_SPORANGIUM.get());
                        output.accept(ModItems.MALVA_SINENSIS_CAVAN.get());
                        output.accept(ModItems.REED.get());
                        output.accept(ModItems.RYE.get());
                        output.accept(ModItems.OATS.get());
                        output.accept(ModItems.GOLDENROD.get());
                        output.accept(ModItems.FOURLEAF_CLOVER.get());
                        output.accept(ModItems.BLAZE_FLOWER.get());
                        output.accept(ModItems.CRIMSON_THORNS.get());
                        output.accept(ModItems.HAIRY_MOSS.get());
                        output.accept(ModItems.WIND_CLEAVING_GRASS.get());
                        output.accept(ModItems.WIND_FEATHER_GRASS.get());
                        output.accept(ModItems.WIND_ISLAND_REED.get());
                        output.accept(ModItems.SHADOW_SHORT_ROOTS.get());
                        output.accept(ModItems.SHADOW_ROOTS.get());
                        output.accept(ModItems.SHADOW_STEM_FERN.get());
                        output.accept(ModItems.SHADOW_SPROUTS.get());
                        output.accept(ModItems.SHADOW_FERN.get());
                        output.accept(ModItems.SHADOW_FUNGUS.get());


                        output.accept(ModItems.WHITE_ORCHID_FLOWER.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
