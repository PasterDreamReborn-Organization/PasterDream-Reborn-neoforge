package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                                      ExistingFileHelper existingFileHelper) {
        super(output, lookup, PasterDreamMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EnchantmentTags.NON_TREASURE).add(ModEnchantment.SWIFT_STRIKE_ENCHANTMENT);
        tag(EnchantmentTags.TRADEABLE).add(
                ModEnchantment.SWIFT_STRIKE_ENCHANTMENT,
                ModEnchantment.SHELTER_ENCHANTMENT,
                ModEnchantment.DREAM_GATHERING_ENCHANTMENT,
                ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        tag(EnchantmentTags.TREASURE).add(
                ModEnchantment.SHELTER_ENCHANTMENT,
                ModEnchantment.DREAM_GATHERING_ENCHANTMENT,
                ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        tag(EnchantmentTags.DOUBLE_TRADE_PRICE).add(
                ModEnchantment.SHELTER_ENCHANTMENT,
                ModEnchantment.DREAM_GATHERING_ENCHANTMENT,
                ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        tag(EnchantmentTags.ON_RANDOM_LOOT).add(
                ModEnchantment.SHELTER_ENCHANTMENT,
                ModEnchantment.DREAM_GATHERING_ENCHANTMENT,
                ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
        tag(EnchantmentTags.CURSE).add(
                ModEnchantment.HEART_DEVOUR_ENCHANTMENT,
                ModEnchantment.HEART_DEVOUR_REPAIR_ENCHANTMENT);
    }
}
