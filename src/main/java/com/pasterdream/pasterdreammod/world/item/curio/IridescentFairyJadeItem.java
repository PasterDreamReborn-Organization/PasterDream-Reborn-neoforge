package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class IridescentFairyJadeItem extends Item implements ICurioItem {
    private static final ResourceLocation ARROW_VELOCITY_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "iridescent_fairy_jade_arrow_velocity");

    public IridescentFairyJadeItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.miracle()));
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.ARROW_VELOCITY,
                new AttributeModifier(ARROW_VELOCITY_ID,
                        0.5, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.1"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.2"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.3"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.4"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.5"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.6"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.7"));
        list.add(Component.translatable("tooltip.pasterdream.iridescent_fairy_jade.8"));
    }
}
