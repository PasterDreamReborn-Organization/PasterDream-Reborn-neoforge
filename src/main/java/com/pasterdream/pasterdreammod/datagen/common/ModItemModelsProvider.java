package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModFluids;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.PotionBottleItem;
import com.pasterdream.pasterdreammod.world.item.prophecycard.ProphecyCardItem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.MELT_DREAM_LIQUID_BUCKET.getId().getPath(), "neoforge:item/bucket_drip")
                .customLoader(DynamicFluidContainerModelBuilder::begin).fluid(ModFluids.MELT_DREAM_LIQUID.get()).end();
        withExistingParent(ModItems.SHADOW_LIQUID_BUCKET.getId().getPath(), "neoforge:item/bucket_drip")
                .customLoader(DynamicFluidContainerModelBuilder::begin).fluid(ModFluids.SHADOW_LIQUID.get()).end();

        basicItem(ModItems.DEEP_SEA_TREASURE.get());
        basicItem(ModItems.DYEDREAM_DEEP_SEA_TREASURE.get());
        basicItem(ModItems.SHADOW_DEEP_SEA_TREASURE.get());
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.DYEDREAM_BUD_NUGGET.get());
        basicItem(ModItems.DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.DYEDREAM_ALLOY_NUGGET.get());
        basicItem(ModItems.COTTON.get());
        basicItem(ModItems.FEATHER_PEN.get());
        handheldItem(ModItems.PLIERS.get());
        basicItem(ModItems.SAND_OF_TIME.get());
        basicItem(ModItems.SORBENT.get());
        withExistingParent(ModItems.FOURLEAF_CLOVER_CURIO.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/fourleaf_clover"));
        basicItem(ModItems.SCULK_HEART.get());
        basicItem(ModItems.SPOOL.get());
        basicItem(ModItems.FABRIC.get());
        basicItem(ModItems.PROTECT_DECK.get());
        basicItem(ModItems.BLACK_STICK.get());
        basicItem(ModItems.LAMP_SHADOW_BROKEN_NOTE.get());
        basicItem(ModItems.DYEDREAM_BROKEN_NOTE.get());
        basicItem(ModItems.DREAM_NOTES_BOOK.get());
        basicItem(ModItems.BLUE_PRINT.get());
        basicItem(ModItems.TITANIUM_UPGRADE.get());
        basicItem(ModItems.SCULK_UPGRADE.get());
        basicItem(ModItems.DYEDREAM_UPGRADE.get());
        handheldItem(ModItems.COPPER_SWORD.get());
        handheldItem(ModItems.COPPER_PICKAXE.get());
        handheldItem(ModItems.COPPER_AXE.get());
        handheldItem(ModItems.COPPER_SHOVEL.get());
        handheldItem(ModItems.COPPER_HOE.get());
        basicItem(ModItems.COPPER_HELMET.get());
        basicItem(ModItems.COPPER_CHESTPLATE.get());
        basicItem(ModItems.COPPER_LEGGINGS.get());
        basicItem(ModItems.COPPER_BOOTS.get());
        basicItem(ModItems.MELT_DREAM_HELMET.get());
        basicItem(ModItems.MELT_DREAM_CHESTPLATE.get());
        basicItem(ModItems.MELT_DREAM_LEGGINGS.get());
        basicItem(ModItems.MELT_DREAM_BOOTS.get());
        handheldItem(ModItems.TITANIUM_SWORD.get());
        handheldItem(ModItems.TITANIUM_PICKAXE.get());
        handheldItem(ModItems.TITANIUM_AXE.get());
        handheldItem(ModItems.TITANIUM_SHOVEL.get());
        handheldItem(ModItems.TITANIUM_HOE.get());
        handheldItem(ModItems.DYEDREAM_SWORD.get());
        handheldItem(ModItems.DYEDREAM_PICKAXE.get());
        handheldItem(ModItems.DYEDREAM_AXE.get());
        handheldItem(ModItems.DYEDREAM_SHOVEL.get());
        handheldItem(ModItems.DYEDREAM_HOE.get());
        handheldItem(ModItems.DYEDREAM_HAMMER.get());
        basicItem(ModItems.TITANIUM_HELMET.get());
        basicItem(ModItems.TITANIUM_CHESTPLATE.get());
        basicItem(ModItems.TITANIUM_LEGGINGS.get());
        basicItem(ModItems.TITANIUM_BOOTS.get());
        basicItem(ModItems.SCULK_HELMET.get());
        basicItem(ModItems.SCULK_CHESTPLATE.get());
        basicItem(ModItems.SCULK_LEGGINGS.get());
        basicItem(ModItems.SCULK_BOOTS.get());
        basicItem(ModItems.DYEDREAM_HELMET.get());
        basicItem(ModItems.DYEDREAM_CHESTPLATE.get());
        basicItem(ModItems.DYEDREAM_LEGGINGS.get());
        basicItem(ModItems.DYEDREAM_BOOTS.get());
        basicItem(ModItems.MOLTEN_GOLD_INGOT.get());
        basicItem(ModItems.MOLTEN_GOLD_NUGGET.get());
        basicItem(ModItems.RAW_MOLTEN_GOLD.get());
        basicItem(ModItems.SOUL_DUST.get());
        basicItem(ModItems.SOUL_ESSENCE.get());
        basicItem(ModItems.MAGIC_STONE.get());
        basicItem(ModItems.CHARGED_AMETHYST.get());
        basicItem(ModItems.REED_ROD.get());
        basicItem(ModItems.PERGAMYN.get());
        basicItem(ModItems.DREAM_TRAIN_TICKET.get());
        basicItem(ModItems.RYE_SEED.get());
        basicItem(ModItems.FLOUR.get());
        basicItem(ModItems.MORTAR.get());
        basicItem(ModItems.COARSE_SALT.get());
        basicItem(ModItems.SALT.get());
        basicItem(ModItems.PINK_EGG.get());
        basicItem(ModItems.EMBRYO_CHARM.get());
        basicItem(ModItems.EMBRYO_NECKLACE.get());
        basicItem(ModItems.EMBRYO_RING.get());
        basicItem(ModItems.EMBRYO_BELT.get());
        basicItem(ModItems.SWEET_DREAM_MUSIC_DISC.get());
        basicItem(ModItems.SNOWFALL_DREAM_MUSIC_DISC.get());
        basicItem(ModItems.WIND_JOURNEY_MUSIC_DISC.get());
        basicItem(ModItems.AARONCOS_MUSIC_DISC.get());
        handheldItem(ModItems.MOLTEN_GOLD_SWORD.get());
        handheldItem(ModItems.MOLTEN_GOLD_PICKAXE.get());
        handheldItem(ModItems.MOLTEN_GOLD_AXE.get());
        handheldItem(ModItems.MOLTEN_GOLD_SHOVEL.get());
        handheldItem(ModItems.MOLTEN_GOLD_HOE.get());
        handheldItem(ModItems.HELLFIRE_SWORD.get());
        handheldItem(ModItems.HELLFIRE_PICKAXE.get());
        handheldItem(ModItems.INFERNO_SWORD.get());
        handheldItem(ModItems.TIDE_SWORD.get());
        handheldItem(ModItems.BEIHAI_RUO_TIDE_SWORD.get());
        handheldItem(ModItems.KUSANAGI.get());
        handheldItem(ModItems.MURAKUMO_KUSANAGI.get());
        handheldItem(ModItems.SWORD_EMBRYO.get());
        basicItem(ModItems.DYEDREAM_FRUIT.get());
        basicItem(ModItems.ELIXIR_BOTTLE.get());
        basicItem(ModItems.ELIXIR_BOTTLE_OF_MELT_DREAM.get());
        basicItem(ModItems.ELIXIR_BOTTLE_OF_RAGE_ELIXIR.get());
        basicItem(ModItems.JUNGLE_SPORE.get());
        basicItem(ModItems.BLUE_HEART_OF_THE_SEA.get());
        basicItem(ModItems.ELDER_GUARDIAN_SCALE.get());
        basicItem(ModItems.BLUE_DEW.get());
        basicItem(ModItems.DYEDREAM_QUARTZ.get());
        basicItem(ModItems.DYEDREAM_DUST.get());
        basicItem(ModItems.DYEDREAM_DUST_PIECE.get());
        basicItem(ModItems.PINK_SLIMEBALL.get());
        basicItem(ModItems.DREAM_FERTILIZER.get());
        basicItem(ModItems.DYEDREAM_DYE.get());
        basicItem(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get());
        basicItem(ModItems.MELT_DREAM_COIN.get());
        basicItem(ModItems.MELT_DREAM_COIN_PILE.get());
        basicItem(ModItems.ATTACK_ENHANCE_STONE.get());
        basicItem(ModItems.LUCK_ENHANCE_STONE.get());
        handheldItem(ModItems.MELT_DREAM_PICKAXE.get());
        handheldItem(ModItems.MELT_DREAM_AXE.get());
        handheldItem(ModItems.MELT_DREAM_SHOVEL.get());
        handheldItem(ModItems.MELT_DREAM_HOE.get());
        handheldItem(ModItems.SHARP_MELT_DREAM_SWORD.get());
        wallItem(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.getId().getPath(), ModItems.DYEDREAM_QUARTZ_BLOCK.getId().getPath());
        withExistingParent(ModItems.DYEDREAM_FENCE.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/dyedream_planks"));
        withExistingParent(ModItems.DYEDREAM_BUTTON.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/dyedream_planks"));
        basicItem(ModItems.DYEDREAM_DOOR.get());
        withExistingParent(ModItems.DYEDREAM_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_trapdoor"));

        withExistingParent(ModItems.SHADOW_FENCE.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/shadow_planks"));
        withExistingParent(ModItems.SHADOW_BUTTON.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/shadow_planks"));
        basicItem(ModItems.SHADOW_DOOR.get());
        withExistingParent(ModItems.SHADOW_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_trapdoor"));
        wallItem(ModItems.SHADOW_STONE_BRICK_WALL.getId().getPath(), ModItems.SHADOW_STONE_BRICK.getId().getPath());
        wallItem(ModItems.NARROW_SHADOW_STONE_BRICK_WALL.getId().getPath(), ModItems.NARROW_SHADOW_STONE_BRICK.getId().getPath());
        wallItem(ModItems.SHADOW_STONE_TILES_WALL.getId().getPath(), ModItems.SHADOW_STONE_TILES.getId().getPath());
        wallItem(ModItems.RUST_BLACK_METAL_WALL.getId().getPath(), ModItems.RUST_BLACK_METAL_BLOCK.getId().getPath());
        withExistingParent(ModItems.RUST_BLACK_METAL_BARS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/rust_black_metal_bars"));
        withExistingParent(ModItems.SHADOW_CANDLE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/shadow_candle"));
        withExistingParent(ModItems.SHADOW_CLAY_POT.getId().getPath(), modLoc("block/clay_pot"));
        wallItem(ModItems.POLISHED_CALCITE_WALL.getId().getPath(), ModItems.POLISHED_CALCITE.getId().getPath());
        wallItem(ModItems.CALCITE_TILES_WALL.getId().getPath(), ModItems.CALCITE_TILES.getId().getPath());
        wallItem(ModItems.DYEDREAM_SANDSTONE_WALL.getId().getPath(), ModItems.DYEDREAM_SANDSTONE.getId().getPath());
        wallItem(ModItems.CUT_DYEDREAM_SANDSTONE_WALL.getId().getPath(), ModItems.CUT_DYEDREAM_SANDSTONE.getId().getPath());
        withExistingParent(ModItems.SMOOTH_DYEDREAM_SANDSTONE_WALL.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/dyedream_sandstone_top"));
        wallItem(ModItems.DYEDREAM_BUD_WALL.getId().getPath(), ModItems.DYEDREAM_BUD_BRICKS.getId().getPath());
        withExistingParent(ModItems.DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.CARVE_DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/carve_dyedream_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.GOLD_CARVE_DYEDREAM_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/gold_carve_dyedream_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.ICE_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/ice_bud"));
        withExistingParent(ModItems.CALCITE_CONE.getId().getPath(), modLoc("block/calcite_cone_tip"));
        withExistingParent(ModItems.SMALL_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_dyedream_bud"));
        withExistingParent(ModItems.MEDIUM_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/medium_dyedream_bud"));
        withExistingParent(ModItems.LARGE_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/large_dyedream_bud"));
        withExistingParent(ModItems.LOST_SWORD_TOMB.getId().getPath(), modLoc("block/lost_sword_tomb"));
        withExistingParent(ModItems.DYEDREAM_CRACK.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/dyedream_crack")).renderType("minecraft:translucent");
        getBuilder(ModItems.LIFE_CRYSTAL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/life_crystal.item")));
        getBuilder(ModItems.GOLDEN_FOX_SCULPTURE.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/golden_fox_sculpture.item")));
        getBuilder(ModItems.FOX_SCULPTURE.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/fox_sculpture.item")));
        getBuilder(ModItems.MELT_DREAM_CRYSTAL_CHEST.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/melt_dream_crystal_chest.item")));
        getBuilder(ModItems.OPENED_MELT_DREAM_CRYSTAL_CHEST.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/opened_melt_dream_crystal_chest.item")));
        basicItem(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get());
        handheldItem(ModItems.DESERT_SWORD.get());
        handheldItem(ModItems.CHENJINGMEN_DESERT_SWORD.get());
        handheldItem(ModItems.BROKEN_HERO_SWORD.get());
        basicItem(ModItems.BLACK_METAL_INGOT.get());
        basicItem(ModItems.BLACK_METAL_NUGGET.get());
        basicItem(ModItems.RUST_BLACK_METAL_GRAIN.get());
        basicItem(ModItems.SHADOW_ALLOY_INGOT.get());
        basicItem(ModItems.SHADOW_DUNGEON_KEY.get());
        basicItem(ModItems.CHRISTMAS_LIGHTS.get());
        handheldItem(ModItems.SHADOW_EROSION_DAGGER.get());
        handheldItem(ModItems.SHADOW_EROSION_PICKAXE.get());
        handheldItem(ModItems.SHADOW_EROSION_AXE.get());
        handheldItem(ModItems.SHADOW_EROSION_SHOVEL.get());
        handheldItem(ModItems.SHADOW_EROSION_HOE.get());
        handheldItem(ModItems.SHADOW_ALLOY_PICKAXE.get());
        handheldItem(ModItems.SHADOW_ALLOY_AXE.get());
        handheldItem(ModItems.SHADOW_ALLOY_SHOVEL.get());
        handheldItem(ModItems.SHADOW_ALLOY_HOE.get());
        // 暗影合金弩物品模型（含 pulling 0/1/2 与装填 arrow/firework 覆盖）
        ItemModelBuilder shadowAlloyCrossbow = withExistingParent(ModItems.SHADOW_ALLOY_CROSSBOW.getId().getPath(), mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow"));
        ItemModelBuilder shadowAlloyCrossbowPulling0 = withExistingParent("shadow_alloy_crossbow_pulling_0", mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow_pulling_0"));
        ItemModelBuilder shadowAlloyCrossbowPulling1 = withExistingParent("shadow_alloy_crossbow_pulling_1", mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow_pulling_1"));
        ItemModelBuilder shadowAlloyCrossbowPulling2 = withExistingParent("shadow_alloy_crossbow_pulling_2", mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow_pulling_2"));
        ItemModelBuilder shadowAlloyCrossbowArrow = withExistingParent("shadow_alloy_crossbow_arrow", mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow_arrow"));
        ItemModelBuilder shadowAlloyCrossbowFirework = withExistingParent("shadow_alloy_crossbow_firework", mcLoc("item/crossbow"))
                .texture("layer0", modLoc("item/shadow_alloy_crossbow_firework"));
        shadowAlloyCrossbow.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .model(shadowAlloyCrossbowPulling0).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.58F)
                .model(shadowAlloyCrossbowPulling1).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 1.0F)
                .model(shadowAlloyCrossbowPulling2).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("charged"), 1.0F)
                .model(shadowAlloyCrossbowArrow).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("charged"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("firework"), 1.0F)
                .model(shadowAlloyCrossbowFirework).end();
        basicItem(ModItems.NIGHTMARE_FUEL.get());
        basicItem(ModItems.PALE_BONENEEDLE.get());
        basicItem(ModItems.ROOTS_PALE_BONENEEDLE.get());
        basicItem(ModItems.RICE_CAKE.get());
        basicItem(ModItems.GLASS_JAR.get());
        basicItem(ModItems.GLASS_JAR_OF_WATER.get());
        basicItem(ModItems.GLASS_JAR_OF_MILK.get());
        basicItem(ModItems.GLASS_JAR_OF_YEAST.get());
        basicItem(ModItems.GLASS_JAR_OF_GUIDING_DRUG.get());
        basicItem(ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get());
        basicItem(ModItems.GLASS_JAR_OF_DREAM_JUICE.get());
        basicItem(ModItems.GLASS_JAR_OF_INK.get());
        basicItem(ModItems.DOUGH.get());
        basicItem(ModItems.DOUGH_WITH_EGG.get());
        basicItem(ModItems.BREAD_SLICE.get());
        basicItem(ModItems.CAKE_BASE.get());
        basicItem(ModItems.WAFER_BISCUIT.get());
        basicItem(ModItems.MOLTEN_GOLD_APPLE.get());
        withExistingParent(ModItems.ENCHANTED_MOLTEN_GOLD_APPLE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/molten_gold_apple"));
        basicItem(ModItems.AMBER_CANDY.get());
        basicItem(ModItems.QUEER_SOUP.get());
        basicItem(ModItems.DYEDREAM_POPSICLE.get());
        basicItem(ModItems.FRIED_EGG.get());
        basicItem(ModItems.BACON_AND_EGG.get());
        basicItem(ModItems.SANDWICH.get());
        basicItem(ModItems.CHOCOLATE.get());
        basicItem(ModItems.POPPING_CANDY.get());
        basicItem(ModItems.CHOCOLATE_MATCHA_CAKE.get());
        basicItem(ModItems.SWISS_ROLL.get());
        basicItem(ModItems.STUFFED_WAFER_COOKIES.get());
        basicItem(ModItems.CREAM_BUN_CAKE.get());
        basicItem(ModItems.BERRY_BUN_CAKE.get());
        basicItem(ModItems.TUBER_BUN_CAKE.get());
        basicItem(ModItems.WATERMELON_BUN_CAKE.get());
        basicItem(ModItems.PUMPKIN_BUN_CAKE.get());
        basicItem(ModItems.GLOW_BERRY_BUN_CAKE.get());
        basicItem(ModItems.DYEDREAM_FRUIT_BUN_CAKE.get());
        basicItem(ModItems.HEART_CHOCOLATE.get());
        basicItem(ModItems.WHITE_HEART_CHOCOLATE.get());
        basicItem(ModItems.PINK_HEART_CHOCOLATE.get());
        basicItem(ModItems.GINGERBREAD_MAN.get());
        basicItem(ModItems.CANDY_CANE.get());
        basicItem(ModItems.BUBBLE_GUM.get());
        basicItem(ModItems.GALAXY_JELLY.get());
        withExistingParent(ModItems.MILKY_WAY_JELLY.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/galaxy_jelly"));
        basicItem(ModItems.FORTUNE_JELLY.get());
        basicItem(ModItems.WIND_RUNNER_JELLY.get());
        basicItem(ModItems.MELT_DREAM_COTTON_CANDY.get());
        basicItem(ModItems.YINHUL_COTTON_CANDY.get());
        basicItem(ModItems.JELLYFISH_MUD.get());
        basicItem(ModItems.JELLYFISH_JELLO.get());
        basicItem(ModItems.PINEAPPLE_LOVE_SEA.get());
        basicItem(ModItems.GLASS_CUP.get());
        basicItem(ModItems.GLASS_CUP_OF_APPLE_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get());
        basicItem(ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_HONEY_JUICE.get());
        basicItem(ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get());
        basicItem(ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
        basicItem(ModItems.RED_DEW.get());
        basicItem(ModItems.GLASS_JAR_OF_GOLDENROD_TEA.get());
        basicItem(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get());
        basicItem(ModItems.GLASS_JAR_OF_DYEDREAM_PERFUME.get());
        basicItem(ModItems.LIGHT_ORGAN.get());
        basicItem(ModItems.ODD_BACON_AND_EGG.get());
        basicItem(ModItems.BUBBLE_TEA.get());
        getBuilder(ModItems.QYM_DOLL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/qym_doll.item")));
        getBuilder(ModItems.UUZ_DOLL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/uuz_doll.item")));
        getBuilder(ModItems.DESERT_HERO_TOMB.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/desert_hero_tomb.item")));
        getBuilder(ModItems.SHADOW_HAND_TRAP.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/shadow_hand_trap.item")));
        basicItem(ModItems.CONGEAL_WIND.get());
        basicItem(ModItems.WIND_RUNNER_CRYSTAL.get());
        basicItem(ModItems.CONGEAL_WIND_IRON_INGOT.get());
        basicItem(ModItems.CONGEAL_WIND_IRON_NUGGET.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_INGOT.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_UPGRADE.get());
        basicItem(ModItems.PULSE_WIND_RUNNER_CRYSTAL.get());
        ItemModelBuilder fluffyWindBow = withExistingParent(ModItems.FLUFFY_WIND_BOW.getId().getPath(), mcLoc("item/bow"))
                .texture("layer0", modLoc("item/fluffy_wind_bow"));
        ItemModelBuilder fluffyWindBowPulling0 = withExistingParent("fluffy_wind_bow_pulling_0", mcLoc("item/bow"))
                .texture("layer0", modLoc("item/fluffy_wind_bow_pulling_0"));
        ItemModelBuilder fluffyWindBowPulling1 = withExistingParent("fluffy_wind_bow_pulling_1", mcLoc("item/bow"))
                .texture("layer0", modLoc("item/fluffy_wind_bow_pulling_1"));
        ItemModelBuilder fluffyWindBowPulling2 = withExistingParent("fluffy_wind_bow_pulling_2", mcLoc("item/bow"))
                .texture("layer0", modLoc("item/fluffy_wind_bow_pulling_2"));
        fluffyWindBow.override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .model(fluffyWindBowPulling0).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65F)
                .model(fluffyWindBowPulling1).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1.0F)
                .predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9F)
                .model(fluffyWindBowPulling2).end();
        basicItem(ModItems.FRACTURED_ANGEL_STATUE.get());
        basicItem(ModItems.GOLD_CHARM.get());
        basicItem(ModItems.ENDEYE_CHARM.get());
        basicItem(ModItems.SEA_CHARM.get());
        basicItem(ModItems.CARAPAX_CHARM.get());
        basicItem(ModItems.WORLDTREE_SEEDPOD.get());
        basicItem(ModItems.GARLAND.get());
        basicItem(ModItems.SNOW_VOW_HEAD.get());
        basicItem(ModItems.LIGHT_BUTTERFLY_CURIO.get());
        basicItem(ModItems.BRIGHT_BUTTERFLY_CURIO.get());
        basicItem(ModItems.QYM_BUTTERFLY_STAR_HAIRPIN.get());
        basicItem(ModItems.RABBIT_FOOT_NECKLACE.get());
        basicItem(ModItems.FEATHER_NECKLACE.get());
        basicItem(ModItems.HEALTH_NECKLACE.get());
        basicItem(ModItems.FIRE_NECKLACE.get());
        basicItem(ModItems.CROSS_NECKLACE.get());
        basicItem(ModItems.BOBOJI_CURIO.get());
        basicItem(ModItems.SHADOW_BREATH.get());
        basicItem(ModItems.WAR_FLAG.get());
        basicItem(ModItems.WIND_SPIRIT.get());
        basicItem(ModItems.STORAGE_BAG.get());
        basicItem(ModItems.LARGE_STORAGE_BAG.get());
        basicItem(ModItems.NATURE_BELT.get());
        basicItem(ModItems.TRAVELER_BELT.get());
        basicItem(ModItems.DREAM_TRAVELER_BELT.get());
        basicItem(ModItems.COUNTER_RING.get());
        basicItem(ModItems.MELT_DREAM_ENERGY_RING.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_HELMET.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_CHESTPLATE.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_LEGGINGS.get());
        basicItem(ModItems.FLUFFY_WIND_ALLOY_BOOTS.get());
        basicItem(ModItems.WIND_RUNNER_CLOAK.get());
        handheldItem(ModItems.CONGEAL_WIND_IRON_SWORD.get());
        handheldItem(ModItems.CONGEAL_WIND_IRON_PICKAXE.get());
        handheldItem(ModItems.CONGEAL_WIND_IRON_AXE.get());
        handheldItem(ModItems.CONGEAL_WIND_IRON_SHOVEL.get());
        handheldItem(ModItems.CONGEAL_WIND_IRON_HOE.get());
        handheldItem(ModItems.FLUFFY_WIND_ALLOY_SWORD.get());
        handheldItem(ModItems.FLUFFY_WIND_ALLOY_PICKAXE.get());
        handheldItem(ModItems.FLUFFY_WIND_ALLOY_AXE.get());
        handheldItem(ModItems.FLUFFY_WIND_ALLOY_SHOVEL.get());
        handheldItem(ModItems.FLUFFY_WIND_ALLOY_HOE.get());
        getBuilder(ModItems.WINDWREATHED_THUNDER_SPEAR.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/windwreathed_thunder_spear.item")))
                .texture("layer0", modLoc("item/windwreathed_thunder_spear"));
        withExistingParent(ModItems.GOLDENROD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/goldenrod"));
        withExistingParent(ModItems.FERRARIA_CRISPA.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/ferraria_crispa"));
        withExistingParent(ModItems.EUSTOMA.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/eustoma"));
        withExistingParent(ModItems.MALVA_SINENSIS_CAVAN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/malva_sinensis_cavan"));
        withExistingParent(ModItems.LINHT_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/linht_flower"));
        withExistingParent(ModItems.DREAMING_LOTUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dreaming_lotus_top"));
        withExistingParent(ModItems.MISTY_DREAMING_LOTUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/misty_dreaming_lotus_top"));
        withExistingParent(ModItems.DYEDREAM_LILY_OF_THE_VALLEY.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_lily_of_the_valley"));
        withExistingParent(ModItems.BLAZE_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/blaze_flower"));
        withExistingParent(ModItems.WHITE_ORCHID_FLOWER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/white_orchid_flower"));
        withExistingParent(ModItems.EDELWEISS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/edelweiss"));
        withExistingParent(ModItems.NIPPY_EDELWEISS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/nippy_edelweiss"));
        withExistingParent(ModItems.LIGHT_BALL.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/light_ball"));

        withExistingParent(ModItems.DYEDREAM_MOSS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_moss"));
        withExistingParent(ModItems.STEM_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/stem_grass"));
        withExistingParent(ModItems.TALL_STEM_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/stem_grass"));
        withExistingParent(ModItems.SINGULARITY_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/singularity_fern"));
        withExistingParent(ModItems.CRIMSON_THORNS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/crimson_thorns_top"));
        withExistingParent(ModItems.OATS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/oats"));
        withExistingParent(ModItems.RYE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/rye"));
        withExistingParent(ModItems.POLISHED_CALCITE_STALICRIPE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/polished_calcite_stalicripe"));
        withExistingParent(ModItems.SMALL_POLISHED_CALCITE_STALICRIPE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_polished_calcite_stalicripe"));
        withExistingParent(ModItems.DYEDREAM_SEAGRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_seagrass"));
        withExistingParent(ModItems.REED.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/reed"));
        withExistingParent(ModItems.DYEDREAM_VINE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_vine"));
        withExistingParent(ModItems.JUNGLE_SPORANGIUM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/jungle_sporangium"));
        withExistingParent(ModItems.FOURLEAF_CLOVER.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/fourleaf_clover"));

        // 风之植物系列
        withExistingParent(ModItems.HAIRY_MOSS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/hairy_moss"));
        withExistingParent(ModItems.WIND_CLEAVING_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_cleaving_grass"));
        withExistingParent(ModItems.WIND_FEATHER_GRASS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_feather_grass_top"));
        withExistingParent(ModItems.WIND_ISLAND_REED.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_island_reed_top"));

        withExistingParent(ModItems.SHADOW_SHORT_ROOTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_short_roots"));
        withExistingParent(ModItems.SHADOW_ROOTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_roots"));
        withExistingParent(ModItems.SHADOW_STEM_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_stem_fern_top"));
        withExistingParent(ModItems.SHADOW_SPROUTS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_sprouts"));
        withExistingParent(ModItems.SHADOW_FERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_fern"));

        withExistingParent(ModItems.SHADOW_FUNGUS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/shadow_fungus"));

        withExistingParent(ModItems.PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));
        withExistingParent(ModItems.TALL_PINK_MUSHROOM.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/pink_mushroom"));

        withExistingParent(ModItems.DYEDREAM_SAPLING.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/dyedream_sapling"));


        basicItem(ModItems.DYEDREAM_COROLLA.get());
        basicItem(ModItems.WHITE_COROLLA.get());
        basicItem(ModItems.WHITE_CRYSTAL.get());
        basicItem(ModItems.SHADOW_HILT.get());
        basicItem(ModItems.PEBBLE.get());
        withExistingParent(ModItems.SNOWBALL_PEBBLE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", mcLoc("item/snowball"));
        basicItem(ModItems.PURE_HORROR.get());
        basicItem(ModItems.BLACK_BEETLE_CARAPACE.get());
        basicItem(ModItems.BLACK_BEETLE_VOCALCORD.get());
        basicItem(ModItems.COTTON.get());
        basicItem(ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
        basicItem(ModItems.WHITE_COROLLA_CROP_AGE_0.get());
        basicItem(ModItems.LIGHT_BALL_CROP_AGE_0.get());
        basicItem(ModItems.CLOUD_CROP_AGE_0.get());
        basicItem(ModItems.COTTON_CROP_AGE_0.get());
        withExistingParent(ModItems.DYEDREAM_COROLLA_CROP_AGE_1.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/dyedream_corolla_crop"));
        withExistingParent(ModItems.WHITE_COROLLA_CROP_AGE_1.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/white_corolla_crop"));
        withExistingParent(ModItems.LIGHT_BALL_CROP_AGE_1.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/light_ball_crop"));
        withExistingParent(ModItems.CLOUD_CROP_AGE_1.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/cloud_crop"));
        withExistingParent(ModItems.COTTON_CROP_AGE_1.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/cotton_crop"));
        // ===== 风之旅途 =====
        basicItem(ModItems.FIG.get());
        withExistingParent(ModItems.WIND_MOOR_SAPLING.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_moor_sapling"));
        withExistingParent(ModItems.WIND_MOOR_FENCE.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/wind_moor_planks"));
        withExistingParent(ModItems.WIND_MOOR_BUTTON.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/wind_moor_planks"));
        basicItem(ModItems.WIND_MOOR_DOOR.get());
        withExistingParent(ModItems.WIND_MOOR_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/wind_moor_trapdoor"));
        withExistingParent(ModItems.CYAN_STONE_BUTTON.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/cyan_stone"));
        wallItem(ModItems.WHITE_SANDSTONE_WALL.getId().getPath(), ModItems.WHITE_SANDSTONE.getId().getPath());
        wallItem(ModItems.CUT_WHITE_SANDSTONE_WALL.getId().getPath(), ModItems.CUT_WHITE_SANDSTONE.getId().getPath());
        wallItem(ModItems.SMOOTH_WHITE_SANDSTONE_WALL.getId().getPath(), "white_sandstone_top");
        wallItem(ModItems.CYAN_STONE_BRICK_WALL.getId().getPath(), ModItems.CYAN_STONE_BRICKS.getId().getPath());
        wallItem(ModItems.MOSSY_CYAN_STONE_BRICK_WALL.getId().getPath(), ModItems.MOSSY_CYAN_STONE_BRICKS.getId().getPath());
        wallItem(ModItems.POLISHED_CYAN_STONE_WALL.getId().getPath(), ModItems.POLISHED_CYAN_STONE.getId().getPath());
        withExistingParent(ModItems.CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/clarity_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.CARVE_CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/carve_clarity_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.FRAME_CLARITY_GLASS_PANE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/frame_clarity_glass")).renderType("minecraft:translucent");
        withExistingParent(ModItems.CONGEAL_WIND_IRON_BARS.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/congeal_wind_iron_bars"));
        withExistingParent(ModItems.CONGEAL_WIND_IRON_LANTERN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/congeal_wind_iron_lantern"));
        withExistingParent(ModItems.CONGEAL_WIND_IRON_CHAIN.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/congeal_wind_iron_chain"));
        withExistingParent(ModItems.BREAK_WIND_CURTAIN.getId().getPath(), modLoc("block/break_wind_curtain"));
        // ===== 拓展栏 =====
        handheldItem(ModItems.THERMAL_DAGGER.get());
        basicItem(ModItems.MELT_DREAM_AURORIAN_STEEL_INGOT.get());
        basicItem(ModItems.DUKE_COIN_CURIO.get());
        basicItem(ModItems.HIYORI_BUTTERFLY_HAIRPIN.get());
        basicItem(ModItems.ALLKINDS_RING.get());
        basicItem(ModItems.KAICHU_OMAMORI.get());
        basicItem(ModItems.TERRA_FLOATING_ISLAND.get());
        basicItem(ModItems.BLESSING_OF_CECILIA.get());
        basicItem(ModItems.FADED_BLESSING_OF_CECILIA.get());
        basicItem(ModItems.PALE_BONE_NEEDLE_TALISMAN.get());
        basicItem(ModItems.STRAWBERRY_HEART.get());
        basicItem(ModItems.ANGEL_WING.get());
        basicItem(ModItems.FORSAKENS_WING.get());
        basicItem(ModItems.MACHINE_LIGHT_WING.get());
        basicItem(ModItems.TURNBACK_CLOAK.get());
        basicItem(ModItems.EVASION_CLOAK.get());
        basicItem(ModItems.SLIVER_BELL.get());

        basicItem(ModItems.MEMORY_GEM.get());
        basicItem(ModItems.SOUL_GEM_OF_AKIZUKI_AYANE.get());

        basicItem(ModItems.DEBUG_SWORD.get());
        basicItem(ModItems.LOOT_GENERATOR.get());
        spawnEggItem(ModItems.TERRORBEAK_SPAWN_EGG.get());
        spawnEggItem(ModItems.CRAZY_TERRORBEAK_SPAWN_EGG.get());
        spawnEggItem(ModItems.WEAKENESS_TERRORBEAK_SPAWN_EGG.get());
        spawnEggItem(ModItems.SHADOW_HAND_SPAWN_EGG.get());
        spawnEggItem(ModItems.SHADOW_GOLEM_SPAWN_EGG.get());
        spawnEggItem(ModItems.SHADOW_GHOST_SPAWN_EGG.get());
        spawnEggItem(ModItems.SHADOW_SQUEAL_GHOST_SPAWN_EGG.get());
        spawnEggItem(ModItems.WAILING_SHADOW_GHOST_SPAWN_EGG.get());
        spawnEggItem(ModItems.FRIENDLY_SHADOW_GHOST_SPAWN_EGG.get());
        spawnEggItem(ModItems.BLACK_BEETLE_SPAWN_EGG.get());
        spawnEggItem(ModItems.BLACK_BEETLE_MOTHER_SPAWN_EGG.get());
        spawnEggItem(ModItems.FIREFLY_SPAWN_EGG.get());
        spawnEggItem(ModItems.SMALL_STONE_SPIRIT_SPAWN_EGG.get());
        spawnEggItem(ModItems.PINK_CHICKEN_SPAWN_EGG.get());
        spawnEggItem(ModItems.PINK_SLIME_SPAWN_EGG.get());
        spawnEggItem(ModItems.GOLDEN_FOX_SPAWN_EGG.get());

        registerProphecyCardModels();
        registerPotionBottleModels();
    }

    private void registerProphecyCardModels() {
        basicItem(ModItems.EMPTY_PROPHECY_CARD.get());
        List<String> types = List.of(
                ProphecyCardItem.TYPE_WIELDING_SWORD,
                ProphecyCardItem.TYPE_HOLY_GRAIL,
                ProphecyCardItem.TYPE_CONFLICT,
                ProphecyCardItem.TYPE_GUARD,
                ProphecyCardItem.TYPE_GRAVEYARD,
                ProphecyCardItem.TYPE_CHAOS,
                ProphecyCardItem.TYPE_SIN,
                ProphecyCardItem.TYPE_BALANCE,
                ProphecyCardItem.TYPE_SPRINT);
        for (String type : types) {
            withExistingParent("prophecy_card_" + type, mcLoc("item/generated"))
                    .texture("layer0", modLoc("item/prophecy_card_" + type));
        }
        ItemModelBuilder prophecyCard = withExistingParent(ModItems.PROPHECY_CARD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/empty_prophecy_card"));
        for (String type : types) {
            prophecyCard.override()
                    .predicate(modLoc("type"), ProphecyCardItem.getPredicateForType(type))
                    .model(new ModelFile.UncheckedModelFile(modLoc("item/prophecy_card_" + type))).end();
        }
    }

    private void registerPotionBottleModels() {
        withExistingParent("potion_bottle_of_berserk", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_berserk"));
        withExistingParent("potion_bottle_of_frozen", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_frozen"));
        withExistingParent("potion_bottle_of_highly_toxic", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_highly_toxic"));
        withExistingParent("potion_bottle_of_inferno", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_inferno"));
        withExistingParent("potion_bottle_of_lightning", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_lightning"));
        withExistingParent("potion_bottle_of_rejuvenation", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_rejuvenation"));
        withExistingParent("potion_bottle_of_cyclone", mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_cyclone"));
        ItemModelBuilder potionBottle = withExistingParent(ModItems.POTION_BOTTLE.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/potion_bottle_of_berserk"));
        potionBottle.override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_REJUVENATION))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_rejuvenation"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_CYCLONE))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_cyclone"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_HIGHLY_TOXIC))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_highly_toxic"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_FROZEN))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_frozen"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_INFERNO))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_inferno"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_BERSERK))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_berserk"))).end()
                .override().predicate(modLoc("type"), PotionBottleItem.getPredicateForType(PotionBottleItem.TYPE_LIGHTNING))
                .model(new ModelFile.UncheckedModelFile(modLoc("item/potion_bottle_of_lightning"))).end();
    }

    private void wallItem(String wall, String base) {
        withExistingParent(wall, mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + base));
    }
}
