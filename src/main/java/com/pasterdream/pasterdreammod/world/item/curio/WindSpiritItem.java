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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * 风精灵：增加玩家发射的弹射物速度 50%，并减少拉弓与装填时间 25%。
 */
public class WindSpiritItem extends Item implements ICurioItem {

    private static final ResourceLocation ARROW_VELOCITY_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_spirit_arrow_velocity");
    private static final ResourceLocation RANGED_CHARGE_TIME_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "wind_spirit_ranged_charge_time");

    public WindSpiritItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.superior()));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.wind_spirit.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.wind_spirit.effect2"));
        list.add(Component.translatable("tooltip.pasterdream.wind_spirit.lore"));
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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                                ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(ModAttributes.ARROW_VELOCITY,
                new AttributeModifier(ARROW_VELOCITY_ID,
                        0.5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(ModAttributes.RANGED_CHARGE_TIME_REDUCTION,
                new AttributeModifier(RANGED_CHARGE_TIME_ID,
                        0.25, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
}
