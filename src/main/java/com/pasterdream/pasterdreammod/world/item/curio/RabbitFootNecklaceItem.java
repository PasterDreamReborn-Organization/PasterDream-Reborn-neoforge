package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.world.item.ModRarities;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RabbitFootNecklaceItem extends Item implements ICurioItem {
    private static final ResourceLocation LUCK_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "rabbit_foot_necklace_luck");

    public RabbitFootNecklaceItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.common()));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 2, 1, false, false));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.LUCK);
            if (attr != null && attr.getModifier(LUCK_MODIFIER_UUID) == null) {
                attr.addPermanentModifier(new AttributeModifier(LUCK_MODIFIER_UUID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.LUCK);
            if (attr != null) {
                attr.removeModifier(LUCK_MODIFIER_UUID);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.rabbit_foot_necklace.effect"));
    }
}
