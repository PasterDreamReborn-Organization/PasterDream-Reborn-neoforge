package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class MeltDreamEnergyRingItem extends Item implements ICurioItem {

    /** 每分钟融梦能量回复量（融梦光环） */
    private static final double ENERGY_PER_MIN = 1.0;
    private static final ResourceLocation MELT_DREAM_VARIABILITY_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_energy_ring_melt_dream_variability");

    public MeltDreamEnergyRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.superior()));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                                ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.MELT_DREAM_VARIABILITY,
                new AttributeModifier(MELT_DREAM_VARIABILITY_ID,
                        ENERGY_PER_MIN, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                            handler.findFirstCurio(this).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.melt_dream_energy_ring.effect1"));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        } else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }
}
