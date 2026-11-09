package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.world.item.DeepTreasureItem;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleWithFluidNBTBuilder;
import com.pasterdream.pasterdreammod.world.item.blueprints.BluePrintWithNBTToCreativeModeTab;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookRegistry;
import com.pasterdream.pasterdreammod.world.item.dreamnotesbook.DreamNotesBookWithNBTToCreativeModeTab;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

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
                        output.accept(ModItems.DYEDREAM_ALLOY_NUGGET.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_NUGGET.get());
                        output.accept(ModItems.SOUL_DUST.get());
                        output.accept(ModItems.DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.SHADOW_DEEP_SEA_TREASURE.get());
                        output.accept(DeepTreasureItem.createSuper(ModItems.SHADOW_DEEP_SEA_TREASURE.get()));
                        output.accept(ModItems.SOUL_ESSENCE.get());
                        output.accept(ModItems.MAGIC_STONE.get());
                        output.accept(ModItems.PINK_SLIMEBALL.get());
                        output.accept(ModItems.DREAM_FERTILIZER.get());
                        output.accept(ModItems.LIGHT_BALL.get());
                        output.accept(ModItems.DYEDREAM_COROLLA.get());
                        output.accept(ModItems.WHITE_COROLLA.get());
                        output.accept(ModItems.WHITE_CRYSTAL.get());
                        output.accept(ModItems.SHADOW_HILT.get());
                        output.accept(ModItems.PURE_HORROR.get());
                        output.accept(ModItems.CONGEAL_WIND.get());
                        output.accept(ModItems.WIND_RUNNER_CRYSTAL.get());
                        output.accept(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get());
                        output.accept(ModItems.BLUE_HEART_OF_THE_SEA.get());
                        output.accept(ModItems.ELDER_GUARDIAN_SCALE.get());
                        output.accept(ModItems.CHARGED_AMETHYST.get());
                        output.accept(ModItems.PROTECT_DECK.get());
                        output.accept(ModItems.COTTON.get());
                        output.accept(ModItems.SPOOL.get());
                        output.accept(ModItems.FABRIC.get());
                        output.accept(ModItems.PERGAMYN.get());
                        output.accept(ModItems.REED_ROD.get());
                        output.accept(ModItems.RYE_SEED.get());
                        output.accept(ModItems.FLOUR.get());
                        output.accept(ModItems.SALT.get());
                        output.accept(ModItems.COARSE_SALT.get());
                        output.accept(ModItems.MELT_DREAM_LIQUID_BUCKET.get());
                        output.accept(ModItems.SHADOW_LIQUID_BUCKET.get());
                        output.accept(ModItems.GLASS_JAR_OF_INK.get());
                        output.accept(ModItems.SCULK_HEART.get());
                        output.accept(ModItems.BLACK_STICK.get());
                        output.accept(ModItems.LAMP_SHADOW_BROKEN_NOTE.get());
                        output.accept(ModItems.DYEDREAM_BROKEN_NOTE.get());
                        output.accept(ModItems.BLACK_METAL_INGOT.get());
                        output.accept(ModItems.BLACK_METAL_NUGGET.get());
                        output.accept(ModItems.RUST_BLACK_METAL_GRAIN.get());
                        output.accept(ModItems.SHADOW_ALLOY_INGOT.get());
                        output.accept(ModItems.NIGHTMARE_FUEL.get());
                        output.accept(ModItems.SHADOW_DUNGEON_KEY.get());
                        output.accept(ModItems.BLACK_BEETLE_CARAPACE.get());
                        output.accept(ModItems.BLACK_BEETLE_VOCALCORD.get());
                        output.accept(ModItems.PEBBLE.get());
                        output.accept(ModItems.SNOWBALL_PEBBLE.get());
                        // 饰品原胚
                        output.accept(ModItems.EMBRYO_CHARM.get());
                        output.accept(ModItems.EMBRYO_NECKLACE.get());
                        output.accept(ModItems.EMBRYO_RING.get());
                        output.accept(ModItems.EMBRYO_BELT.get());
                        // 音乐唱片
                        output.accept(ModItems.SWEET_DREAM_MUSIC_DISC.get());
                        output.accept(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get());
                        output.accept(ModItems.AARONCOS_MUSIC_DISC.get());
                        output.accept(ModItems.WIND_JOURNEY_MUSIC_DISC.get());
                        // 预言卡（动态遍历所有已注册类型，含 KJS / 拓展模组注册的）
                        output.accept(ModItems.EMPTY_PROPHECY_CARD.get());
                        for (String type : ProphecyCardItem.getRegisteredTypes()) {
                            output.accept(ProphecyCardItem.createCard(ModItems.PROPHECY_CARD.get(), type));
                        }
                        // 药剂瓶（动态遍历所有已注册类型，含 KJS / 拓展模组注册的）
                        for (String type : PotionBottleItem.getRegisteredTypes()) {
                            output.accept(PotionBottleItem.createWithType(ModItems.POTION_BOTTLE.get(), type));
                        }
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
                        output.accept(ModItems.DOUGH.get());
                        output.accept(ModItems.DOUGH_WITH_EGG.get());
                        output.accept(ModItems.BREAD_SLICE.get());
                        output.accept(ModItems.CAKE_BASE.get());
                        output.accept(ModItems.WAFER_BISCUIT.get());
                        output.accept(ModItems.DYEDREAM_FRUIT.get());
                        output.accept(ModItems.FIG.get());
                        output.accept(ModItems.MOLTEN_GOLD_APPLE.get());
                        output.accept(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.get());
                        output.accept(ModItems.AMBER_CANDY.get());
                        output.accept(ModItems.QUEER_SOUP.get());
                        output.accept(ModItems.GLASS_CUP.get());
                        output.accept(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
                        output.accept(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
                        output.accept(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
                        output.accept(ModItems.DYEDREAM_POPSICLE.get());
                        output.accept(ModItems.FRIED_EGG.get());
                        output.accept(ModItems.BACON_AND_EGG.get());
                        output.accept(ModItems.RICE_CAKE.get());
                        output.accept(ModItems.SANDWICH.get());
                        output.accept(ModItems.CHOCOLATE.get());
                        output.accept(ModItems.POPPING_CANDY.get());
                        output.accept(ModItems.CHOCOLATE_MATCHA_CAKE.get());
                        output.accept(ModItems.SWISS_ROLL.get());
                        output.accept(ModItems.STUFFED_WAFER_COOKIES.get());
                        output.accept(ModItems.CREAM_BUN_CAKE.get());
                        output.accept(ModItems.BERRY_BUN_CAKE.get());
                        output.accept(ModItems.TUBER_BUN_CAKE.get());
                        output.accept(ModItems.WATERMELON_BUN_CAKE.get());
                        output.accept(ModItems.PUMPKIN_BUN_CAKE.get());
                        output.accept(ModItems.GLOW_BERRY_BUN_CAKE.get());
                        output.accept(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());
                        output.accept(ModItems.HEART_CHOCOLATE.get());
                        output.accept(ModItems.WHITE_HEART_CHOCOLATE.get());
                        output.accept(ModItems.PINK_HEART_CHOCOLATE.get());
                        output.accept(ModItems.GINGERBREAD_MAN.get());
                        output.accept(ModItems.CANDY_CANE.get());
                        output.accept(ModItems.BUBBLE_GUM.get());
                        output.accept(ModItems.GALAXY_JELLY.get());
                        output.accept(ModItems.MILKY_WAY_JELLY.get());
                        output.accept(ModItems.FORTUNE_JELLY.get());
                        output.accept(ModItems.WIND_RUNNER_JELLY.get());
                        output.accept(ModItems.MELT_DREAM_COTTON_CANDY.get());
                        output.accept(ModItems.YINHUL_COTTON_CANDY.get());
                        output.accept(ModItems.JELLYFISH_MUD.get());
                        output.accept(ModItems.JELLYFISH_JELLO.get());
                        output.accept(ModItems.PINEAPPLE_LOVE_SEA.get());
                        output.accept(ModItems.RED_DEW.get());
                        output.accept(ModItems.BLUE_DEW.get());
                        output.accept(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
                        output.accept(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get());
                        output.accept(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get());
                        output.accept(ModItems.LIGHT_ORGAN.get());
                        output.accept(ModItems.ODD_BACON_AND_EGG.get());
                        output.accept(ModItems.JUNGLE_SPORE.get());
                        output.accept(ModItems.BUBBLE_TEA.get());
                        output.accept(ModItems.ELIXIR_BOTTLE.get());
                        output.accept(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get());
                        output.accept(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get());
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_ELIXIR_BOTTLE_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_elixir_bottle_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_elixir_bottle_tab"))
                    .icon(() -> ModItems.ELIXIR_BOTTLE.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ELIXIR_BOTTLE.get());
                        for (Fluid fluid : BuiltInRegistries.FLUID)
                        {
                            if (fluid == ModFluids.POTION.get())
                            {
                                List<GenericMobEffect> allEffectList = new ArrayList<>();
                                for (Holder<MobEffect> effect : BuiltInRegistries.MOB_EFFECT.holders().toList())
                                {
                                    List<GenericMobEffect> effectList = new ArrayList<>();
                                    effectList.add(new GenericMobEffect(effect, 2, 18000));
                                    allEffectList.add(new GenericMobEffect(effect, 2, 18000));
                                    output.accept(ElixirBottleWithFluidNBTBuilder.builder(PotionHelper.createNBTPotion(effectList, 1000)));
                                }
                                output.accept(ElixirBottleWithFluidNBTBuilder.builder(PotionHelper.createNBTPotion(allEffectList, 1000)));
                            }
                                else
                                if (fluid != Fluids.EMPTY)
                                {
                                    output.accept(ElixirBottleWithFluidNBTBuilder.builder(new FluidStack(fluid, 1000)));
                                }
                        }
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
                    }).build());

    // ===== 灯影之下 =====
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_SHADOW_TAB = CREATIVE_MODE_TABS.register("pasterdream_shadow_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_shadow_tab"))
                    .icon(() -> ModBlocks.SHADOW.toStack())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.SHADOW.get());
                        output.accept(ModItems.THICK_SHADOW.get());
                        output.accept(ModItems.SHADOW_STONE.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.SHADOW_STONE_BRICK_WALL.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.NARROW_SHADOW_STONE_BRICK_WALL.get());
                        output.accept(ModItems.SHADOW_STONE_TILES.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_STAIRS.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_SLAB.get());
                        output.accept(ModItems.SHADOW_STONE_TILES_WALL.get());
                        output.accept(ModItems.CRACKED_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.CHISELED_SHADOW_STONE_BRICK.get());
                        output.accept(ModItems.SHADOW_STONE_CAGE_RUNE.get());
                        output.accept(ModItems.SHADOW_STONE_HOLY_GRAIL_RUNE.get());
                        output.accept(ModItems.SHADOW_STONE_OBLATION_RUNE.get());
                        output.accept(ModItems.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get());
                        output.accept(ModItems.SHADOW_DUNGEON_STONE.get());
                        output.accept(ModItems.CHISELED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.CRACKED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.FRACTURED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICK_STAIRS.get());
                        output.accept(ModItems.SHADOW_DUNGEON_BRICK_SLAB.get());
                        output.accept(ModItems.SHATTERED_SHADOW_DUNGEON_BRICKS.get());
                        output.accept(ModItems.SHADOW_NYLIUM.get());
                        output.accept(ModItems.SHADOW_LIGHT.get());
                        output.accept(ModItems.SHADOW_SHROOMLIGHT.get());
                        output.accept(ModItems.SHADOW_CANDLE.get());
                        output.accept(ModItems.SHADOW_WART_BLOCK.get());
                        output.accept(ModItems.SHADOW_STEM.get());
                        output.accept(ModItems.SHADOW_HYPHAE.get());
                        output.accept(ModItems.STRIPPED_SHADOW_STEM.get());
                        output.accept(ModItems.STRIPPED_SHADOW_HYPHAE.get());
                        output.accept(ModItems.SHADOW_PLANKS.get());
                        output.accept(ModItems.SHADOW_STAIRS.get());
                        output.accept(ModItems.SHADOW_SLAB.get());
                        output.accept(ModItems.SHADOW_FENCE.get());
                        output.accept(ModItems.SHADOW_FENCE_GATE.get());
                        output.accept(ModItems.SHADOW_PANE.get());
                        output.accept(ModItems.SHADOW_DOOR.get());
                        output.accept(ModItems.SHADOW_TRAPDOOR.get());
                        output.accept(ModItems.SHADOW_PRESSURE_PLATE.get());
                        output.accept(ModItems.SHADOW_BUTTON.get());
                        output.accept(ModItems.BLACK_METAL_BLOCK.get());
                        output.accept(ModItems.RUST_BLACK_METAL_BLOCK.get());
                        output.accept(ModItems.RUST_BLACK_METAL_WALL.get());
                        output.accept(ModItems.RUST_BLACK_METAL_BARS.get());
                        output.accept(ModItems.SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.WORN_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.COBWEB_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.KEY_SHADOW_BOOKSHELF.get());
                        output.accept(ModItems.SHADOW_CLAY_POT.get());
                        output.accept(ModItems.SHADOW_ARENA_BLOCK.get());
                        output.accept(ModItems.SHADOW_FISSURE_0.get());
                        output.accept(ModItems.SHADOW_FISSURE_1.get());
                        output.accept(ModItems.SHADOW_FISSURE_2.get());
                        output.accept(ModItems.SHADOW_FISSURE_3.get());
                        output.accept(ModItems.SHADOW_FISSURE_4.get());
                        output.accept(ModItems.SHADOW_FISSURE_5.get());
                    })
                    .build());

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
                        // 钓竿
                        output.accept(ModItems.STAR_WISH_ROD.get());
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
                        output.accept(ModItems.SHADOW_ALLOY_CROSSBOW.get());
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
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 1));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 2));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 3));
                        output.accept(RedDewRingItem.createWithLv(ModItems.RED_DEW_RING.get(), 4));
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
                        output.accept(ModItems.SAND_OF_TIME.get());
                        output.accept(ModItems.DESERT_HERO_TOMB.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_ANVIL.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_COOLER_POT.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_HAMMER.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_GRIND_STONE.get());
                        output.accept(ModItems.WEAPON_WORKSHOP_BLAST_FURNACE.get());
                        output.accept(ModItems.MORTAR.get());
                        output.accept(ModItems.PLIERS.get());
                        output.accept(ModItems.DYEDREAM_DESK.get());
                        output.accept(ModItems.SHADOW_DESK.get());
                        output.accept(ModItems.WIND_MOOR_DESK.get());
                        output.accept(ModItems.PICNIC_BASKET.get());
                        output.accept(ModItems.SHADOW_CHEST.get());
                        output.accept(ModItems.WIND_MOOR_CRATE.get());
                        output.accept(ModItems.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get());
                        output.accept(ModItems.FEATHER_PEN.get());
                        output.accept(ModItems.CLAYPAN.get());
                        output.accept(ModItems.DREAM_CAULDRON.get());
                        output.accept(ModItems.DREAM_ACCUMULATOR.get());
                        output.accept(ModItems.SORBENT.get());
                        output.accept(ModItems.SHADOW_DUNGEON_WALL_KEY.get());
                        output.accept(ModItems.SHADOW_DUNGEON_FLOOR_KEY.get());
                        output.accept(ModItems.SHADOW_BLAST_FURNACE_CORE.get());
                        output.accept(ModItems.SHADOW_HAND_TRAP.get());
                        output.accept(ModItems.ECOLOGY_GLASS_JAR.get());
                        output.accept(ModItems.FIREFLY_GLASS_JAR.get());
                        output.accept(ModItems.FIREFLY_NEST.get());
                        output.accept(ModItems.BIRDS_NEST.get());
                        output.accept(ModItems.CHRISTMAS_LIGHTS.get());
                        output.accept(ModItems.SMALL_STONE_SPIRIT_BLOCK.get());
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

    // ===== 风之旅途 =====
    // 来源：源 wind_journey_world 维度建筑方块
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WIND_JOURNEY_WORLD_TAB = CREATIVE_MODE_TABS.register("wind_journey_world",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.wind_journey_world"))
                    .icon(() -> new ItemStack(ModBlocks.CYAN_MOSS_STONE.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CYAN_MOSS_STONE.get());
                        output.accept(ModItems.CYAN_STONE.get());
                        output.accept(ModItems.WHITE_SAND.get());
                        output.accept(ModItems.WHITE_SANDSTONE.get());
                        output.accept(ModItems.WHITE_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.WHITE_SANDSTONE_SLAB.get());
                        output.accept(ModItems.WHITE_SANDSTONE_WALL.get());
                        output.accept(ModItems.CUT_WHITE_SANDSTONE.get());
                        output.accept(ModItems.CUT_WHITE_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.CUT_WHITE_SANDSTONE_SLAB.get());
                        output.accept(ModItems.CUT_WHITE_SANDSTONE_WALL.get());
                        output.accept(ModItems.CHISELED_WHITE_SANDSTONE.get());
                        output.accept(ModItems.SMOOTH_WHITE_SANDSTONE.get());
                        output.accept(ModItems.SMOOTH_WHITE_SANDSTONE_STAIRS.get());
                        output.accept(ModItems.SMOOTH_WHITE_SANDSTONE_SLAB.get());
                        output.accept(ModItems.SMOOTH_WHITE_SANDSTONE_WALL.get());
                        output.accept(ModItems.THICK_CLOUD.get());
                        output.accept(ModItems.WIND_MOOR_LOG.get());
                        output.accept(ModItems.WIND_MOOR_WOOD.get());
                        output.accept(ModItems.STRIPPED_WIND_MOOR_LOG.get());
                        output.accept(ModItems.STRIPPED_WIND_MOOR_WOOD.get());
                        output.accept(ModItems.WIND_MOOR_LEAVES_0.get());
                        output.accept(ModItems.WIND_MOOR_LEAVES_1.get());
                        output.accept(ModItems.WIND_MOOR_SAPLING.get());
                        output.accept(ModItems.WIND_MOOR_PLANKS.get());
                        output.accept(ModItems.WIND_MOOR_STAIRS.get());
                        output.accept(ModItems.WIND_MOOR_SLAB.get());
                        output.accept(ModItems.WIND_MOOR_FENCE.get());
                        output.accept(ModItems.WIND_MOOR_FENCE_GATE.get());
                        output.accept(ModItems.WIND_MOOR_PANE.get());
                        output.accept(ModItems.WIND_MOOR_DOOR.get());
                        output.accept(ModItems.WIND_MOOR_TRAPDOOR.get());
                        output.accept(ModItems.WIND_MOOR_PRESSURE_PLATE.get());
                        output.accept(ModItems.WIND_MOOR_BUTTON.get());
                        output.accept(ModItems.CYAN_STONE_BRICKS.get());
                        output.accept(ModItems.CYAN_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.CYAN_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.CYAN_STONE_BRICK_WALL.get());
                        output.accept(ModItems.CYAN_STONE_PRESSURE_PLATE.get());
                        output.accept(ModItems.CYAN_STONE_BUTTON.get());
                        output.accept(ModItems.MOSSY_CYAN_STONE_BRICKS.get());
                        output.accept(ModItems.MOSSY_CYAN_STONE_BRICK_STAIRS.get());
                        output.accept(ModItems.MOSSY_CYAN_STONE_BRICK_SLAB.get());
                        output.accept(ModItems.MOSSY_CYAN_STONE_BRICK_WALL.get());
                        output.accept(ModItems.CHISELED_CYAN_STONE_BRICKS.get());
                        output.accept(ModItems.CYAN_STONE_PILLAR.get());
                        output.accept(ModItems.POLISHED_CYAN_STONE.get());
                        output.accept(ModItems.POLISHED_CYAN_STONE_STAIRS.get());
                        output.accept(ModItems.POLISHED_CYAN_STONE_SLAB.get());
                        output.accept(ModItems.POLISHED_CYAN_STONE_WALL.get());
                        output.accept(ModItems.CLARITY_GLASS.get());
                        output.accept(ModItems.CLARITY_GLASS_PANE.get());
                        output.accept(ModItems.CARVE_CLARITY_GLASS.get());
                        output.accept(ModItems.CARVE_CLARITY_GLASS_PANE.get());
                        output.accept(ModItems.FRAME_CLARITY_GLASS.get());
                        output.accept(ModItems.FRAME_CLARITY_GLASS_PANE.get());
                        output.accept(ModItems.BREAK_WIND_CURTAIN.get());
                        output.accept(ModItems.CONGEAL_WIND_ORE.get());
                        output.accept(ModItems.WIND_RUNNER_CRYSTAL_ORE.get());
                        output.accept(ModItems.CONGEAL_WIND_BLOCK.get());
                        output.accept(ModItems.WIND_RUNNER_CRYSTAL_BLOCK.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_BLOCK.get());
                        output.accept(ModItems.FLUFFY_WIND_ALLOY_BLOCK.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_BARS.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_LANTERN.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_CHAIN.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_TRAPDOOR.get());
                        output.accept(ModItems.CONGEAL_WIND_IRON_PRESSURE_PLATE.get());
                        output.accept(ModItems.EJECTION_PRESSURE_PLATE.get());
                        output.accept(ModItems.EJECTION_PRESSURE_BLOCK.get());
                    })
                    .build());

    // ===== 调试 =====
    // 来源: 旧 pasterdream_dream_debug_tab（当前仅迁入调试剑、战利品生成器、粒子方块与已存在实体的刷怪蛋）
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_DREAM_DEBUG_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_dream_debug_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_dream_debug_tab"))
                    .icon(() -> new ItemStack(ModItems.DEBUG_SWORD.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DEBUG_SWORD.get());
                        output.accept(ModItems.SHADOW_BLAST_FURNACE.get());
                        output.accept(ModItems.LOOT_GENERATOR.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get());
                        output.accept(ModItems.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get());
                        output.accept(ModItems.TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG.get());
                        output.accept(ModItems.SHADOW_HAND_SPAWN_EGG.get());
                        output.accept(ModItems.FIREFLY_SPAWN_EGG.get());
                        output.accept(ModItems.SMALL_STONE_SPIRIT_SPAWN_EGG.get());
                    })
                    .build());

    // ===== 拓展 =====
    // 来源: 旧 paster_tab_9
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_EXPANSION_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_expansion_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_expansion_tab"))
                    .icon(() -> new ItemStack(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.DUKE_COIN_CURIO.get());
                        output.accept(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get());
                        output.accept(ModItems.ALLKINDS_RING.get());
                        output.accept(ModItems.KAICHU_OMAMORI.get());
                        output.accept(ModItems.THERMAL_DAGGER.get());
                        output.accept(ModItems.MELT_DREAM_AURORIAN_STEEL_INGOT.get());
                        output.accept(ModItems.TERRA_BLADE.get());
                        output.accept(ModItems.TERRA_FLOATING_ISLAND.get());
                        output.accept(ModItems.FADED_BLESSING_OF_CECILIA.get());
                        output.accept(ModItems.BLESSING_OF_CECILIA.get());
                        output.accept(ModItems.STRAWBERRY_HEART.get());
                        output.accept(ModItems.ANGEL_WING.get());
                        output.accept(ModItems.FORSAKENS_WING.get());
                        output.accept(ModItems.MACHINE_LIGHT_WING.get());
                        output.accept(ModItems.TURNBACK_CLOAK.get());
                        output.accept(ModItems.EVASION_CLOAK.get());
                        output.accept(ModItems.SLIVER_BELL.get());
                    })
                    .build());

    // ===== 笔记 =====
    // 来源: pasterdream_dream_notes_tab（当前迁入寻梦者笔记书与蓝图；剧情引导物品尚未迁入）
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PASTERDREAM_DREAM_NOTES_TAB =
            CREATIVE_MODE_TABS.register("pasterdream_dream_notes_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.pasterdream.pasterdream_dream_notes_tab"))
                    .icon(() -> DreamNotesBookWithNBTToCreativeModeTab.buildNBT("花园解密:迷梦冶梦莲"))
                    .displayItems((parameters, output) -> {
                        output.accept(BluePrintWithNBTToCreativeModeTab.buildNBT("精铸工坊"));
                        output.accept(BluePrintWithNBTToCreativeModeTab.buildNBT("暗影高炉"));

                        for (String content : DreamNotesBookRegistry.getAllContentNames()) {
                            output.accept(DreamNotesBookWithNBTToCreativeModeTab.buildNBT(content));
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private ModCreativeModeTabs() {
    }
}
