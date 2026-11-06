package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TITANIUM_INGOT.get());
        basicItem(ModItems.TITANIUM_NUGGET.get());
        basicItem(ModItems.RAW_TITANIUM.get());
        basicItem(ModItems.DYEDREAM_BUD_NUGGET.get());
        basicItem(ModItems.DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.RAW_DYEDREAM_ALLOY_INGOT.get());
        basicItem(ModItems.DYEDREAM_ALLOY_NUGGET.get());
        basicItem(ModItems.COTTON.get());
        basicItem(ModItems.SCULK_HEART.get());
        basicItem(ModItems.SPOOL.get());
        basicItem(ModItems.FABRIC.get());
        basicItem(ModItems.PROTECT_DECK.get());
        basicItem(ModItems.BLACK_STICK.get());
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
        basicItem(ModItems.JUNGLE_SPORE.get());
        basicItem(ModItems.BLUE_HEART_OF_THE_SEA.get());
        basicItem(ModItems.ELDER_GUARDIAN_SCALE.get());
        basicItem(ModItems.BLUE_DEW.get());
        basicItem(ModItems.DYEDREAM_QUARTZ.get());
        basicItem(ModItems.DYEDREAM_DUST.get());
        basicItem(ModItems.DYEDREAM_DUST_PIECE.get());
        basicItem(ModItems.PINK_SLIMEBALL.get());
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
        withExistingParent(ModItems.SMALL_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_dyedream_bud"));
        withExistingParent(ModItems.MEDIUM_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/medium_dyedream_bud"));
        withExistingParent(ModItems.LARGE_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/large_dyedream_bud"));
        withExistingParent(ModItems.LOST_SWORD_TOMB.getId().getPath(), modLoc("block/lost_sword_tomb"));
        getBuilder(ModItems.LIFE_CRYSTAL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/life_crystal.item")));
        handheldItem(ModItems.DESERT_SWORD.get());
        handheldItem(ModItems.CHENJINGMEN_DESERT_SWORD.get());
        handheldItem(ModItems.BROKEN_HERO_SWORD.get());
        basicItem(ModItems.NIGHTMARE_FUEL.get());
        basicItem(ModItems.RICE_CAKE.get());
        getBuilder(ModItems.QYM_DOLL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/qym_doll.item")));
        getBuilder(ModItems.UUZ_DOLL.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/uuz_doll.item")));
        getBuilder(ModItems.DESERT_HERO_TOMB.getId().getPath())
                .parent(new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                        modLoc("displaysettings/desert_hero_tomb.item")));
        basicItem(ModItems.CONGEAL_WIND.get());
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
    }

    private void wallItem(String wall, String base) {
        withExistingParent(wall, mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + base));
    }
}
