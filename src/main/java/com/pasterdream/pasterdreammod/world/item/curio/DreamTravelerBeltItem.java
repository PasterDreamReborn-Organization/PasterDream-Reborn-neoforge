package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DreamTravelerBeltItem extends Item implements ICurioItem {
    private static final ResourceLocation MOVEMENT_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_traveler_belt_movement_speed");
    private static final ResourceLocation BLINK_COOLDOWN_REDUCTION_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_traveler_belt_blink_cooldown_reduction");

    public DreamTravelerBeltItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.superior()));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance speedAttr = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(MOVEMENT_SPEED_ID) == null) {
                speedAttr.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_ID,
                        0.03, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            AttributeInstance cdAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (cdAttr != null && cdAttr.getModifier(BLINK_COOLDOWN_REDUCTION_ID) == null) {
                cdAttr.addPermanentModifier(new AttributeModifier(BLINK_COOLDOWN_REDUCTION_ID,
                        0.1, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance speedAttr = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null) {
                speedAttr.removeModifier(MOVEMENT_SPEED_ID);
            }
            AttributeInstance cdAttr = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (cdAttr != null) {
                cdAttr.removeModifier(BLINK_COOLDOWN_REDUCTION_ID);
            }
        }
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
        list.add(Component.translatable("tooltip.pasterdream.dream_traveler_belt.effect1"));
        list.add(Component.translatable("tooltip.pasterdream.dream_traveler_belt.effect2"));
    }
}
