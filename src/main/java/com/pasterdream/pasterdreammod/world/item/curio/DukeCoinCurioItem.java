package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class DukeCoinCurioItem extends Item implements ICurioItem {
    private static final ResourceLocation LUCK_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "duke_coin_luck");
    private static final ResourceLocation BLINK_CD_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "duke_coin_blink_cooldown_reduction");

    public DukeCoinCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.excellent()));
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
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null && luckAttr.getModifier(LUCK_ID) == null) {
                luckAttr.addPermanentModifier(new AttributeModifier(LUCK_ID, 7.0,
                        AttributeModifier.Operation.ADD_VALUE));
            }
            var blinkCdAttr = entity.getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCdAttr != null && blinkCdAttr.getModifier(BLINK_CD_ID) == null) {
                blinkCdAttr.addPermanentModifier(new AttributeModifier(BLINK_CD_ID, -7.0,
                        AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null) {
                luckAttr.removeModifier(LUCK_ID);
            }
            var blinkCdAttr = entity.getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCdAttr != null) {
                blinkCdAttr.removeModifier(BLINK_CD_ID);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.effect.luck"));
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.effect.blink_cd"));
        list.add(Component.translatable("tooltip.pasterdream.duke_coin_curio.flavor"));
    }
}
