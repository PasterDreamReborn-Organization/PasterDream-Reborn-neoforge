package com.pasterdream.pasterdreammod.world.item.curio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class HiyoriButterflyHairpinItem extends Item implements ICurioItem {
    private static final ResourceLocation MAX_HEALTH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "hiyori_butterfly_hairpin_health");
    private static final ResourceLocation LUCK_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "hiyori_butterfly_hairpin_luck");
    private static final ResourceLocation SAN_VARIABILITY_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "hiyori_butterfly_hairpin_san_variability");

    public HiyoriButterflyHairpinItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.epic()));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH,
                new AttributeModifier(MAX_HEALTH_ID, 2.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.LUCK,
                new AttributeModifier(LUCK_ID, 15.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(ModAttributes.SAN_VARIABILITY,
                new AttributeModifier(SAN_VARIABILITY_ID, 0.96, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                    handler.findFirstCurio(stack.getItem()).isEmpty())
                    .orElse(true);
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.hiyori_butterfly_hairpin.lore"));
    }
}
