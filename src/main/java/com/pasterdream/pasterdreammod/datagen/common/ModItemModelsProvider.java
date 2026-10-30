package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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
        basicItem(ModItems.SPOOL.get());
        basicItem(ModItems.FABRIC.get());
        basicItem(ModItems.PROTECT_DECK.get());
        basicItem(ModItems.BLACK_STICK.get());
        basicItem(ModItems.TITANIUM_UPGRADE.get());
        basicItem(ModItems.DYEDREAM_UPGRADE.get());
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
        basicItem(ModItems.MOLTEN_GOLD_INGOT.get());
        basicItem(ModItems.MOLTEN_GOLD_NUGGET.get());
        basicItem(ModItems.RAW_MOLTEN_GOLD.get());
        handheldItem(ModItems.MOLTEN_GOLD_SWORD.get());
        handheldItem(ModItems.MOLTEN_GOLD_PICKAXE.get());
        handheldItem(ModItems.MOLTEN_GOLD_AXE.get());
        handheldItem(ModItems.MOLTEN_GOLD_SHOVEL.get());
        handheldItem(ModItems.MOLTEN_GOLD_HOE.get());
        basicItem(ModItems.DYEDREAM_QUARTZ.get());
        basicItem(ModItems.DYEDREAM_DUST.get());
        basicItem(ModItems.DYEDREAM_DUST_PIECE.get());
        basicItem(ModItems.PINK_SLIMEBALL.get());
        basicItem(ModItems.DYEDREAM_DYE.get());
        wallItem(ModItems.DYEDREAM_QUARTZ_BLOCK_WALL.getId().getPath(), ModItems.DYEDREAM_QUARTZ_BLOCK.getId().getPath());
        withExistingParent(ModItems.SMALL_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/small_dyedream_bud"));
        withExistingParent(ModItems.MEDIUM_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/medium_dyedream_bud"));
        withExistingParent(ModItems.LARGE_DYEDREAM_BUD.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/large_dyedream_bud"));
    }

    private void wallItem(String wall, String base) {
        withExistingParent(wall, mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + base));
    }
}
