package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public final class ModEnchantment {
    public static final ResourceKey<Enchantment> SWIFT_STRIKE_ENCHANTMENT = key("swift_strike");
    public static final ResourceKey<Enchantment> SHELTER_ENCHANTMENT = key("shelter");
    public static final ResourceKey<Enchantment> DREAM_GATHERING_ENCHANTMENT = key("gather_dream");
    public static final ResourceKey<Enchantment> MELT_DREAM_REPAIR_ENCHANTMENT = key("melt_dream_repair");
    public static final ResourceKey<Enchantment> HEART_DEVOUR_ENCHANTMENT = key("heart_devour");
    public static final ResourceKey<Enchantment> HEART_DEVOUR_REPAIR_ENCHANTMENT = key("heart_devour_repair");

    private ModEnchantment() {
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var items = context.lookup(Registries.ITEM);
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var armor = items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE);
        var repairable = items.getOrThrow(ModItemTags.REPAIR_ENCHANTABLE);
        var mending = enchantments.getOrThrow(Enchantments.MENDING);
        var ordinaryMin = Enchantment.dynamicCost(5, 10);
        var ordinaryMax = Enchantment.dynamicCost(25, 10);
        var repairMin = Enchantment.dynamicCost(25, 25);
        var repairMax = Enchantment.dynamicCost(75, 25);

        register(context, SWIFT_STRIKE_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModItemTags.SWIFT_STRIKE_ENCHANTABLE), items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                2, 4, ordinaryMin, ordinaryMax, 4, EquipmentSlotGroup.MAINHAND)));
        register(context, SHELTER_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                armor, 2, 3, ordinaryMin, ordinaryMax, 4, EquipmentSlotGroup.ARMOR)));
        register(context, DREAM_GATHERING_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                armor, 2, 5, ordinaryMin, ordinaryMax, 4, EquipmentSlotGroup.ARMOR)));
        register(context, MELT_DREAM_REPAIR_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                repairable, 2, 1, repairMin, repairMax, 4, EquipmentSlotGroup.ANY))
                .exclusiveWith(HolderSet.direct(mending)));
        register(context, HEART_DEVOUR_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                armor, 1, 1, repairMin, repairMax, 8, EquipmentSlotGroup.ARMOR)));
        register(context, HEART_DEVOUR_REPAIR_ENCHANTMENT, Enchantment.enchantment(Enchantment.definition(
                repairable, 1, 1, repairMin, repairMax, 8, EquipmentSlotGroup.ANY))
                .exclusiveWith(HolderSet.direct(mending, enchantments.getOrThrow(MELT_DREAM_REPAIR_ENCHANTMENT))));
    }

    public static int getLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        for (var enchantment : stack.getTagEnchantments().keySet()) {
            if (enchantment.is(key)) {
                return stack.getEnchantmentLevel(enchantment);
            }
        }
        return 0;
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key,
                                 Enchantment.Builder enchantment) {
        context.register(key, enchantment.build(key.location()));
    }
}
