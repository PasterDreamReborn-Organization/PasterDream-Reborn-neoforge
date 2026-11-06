package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class StrikeRingItem extends Item implements ICurioItem {
    public static final String TAG_LV = "lv";
    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "strike_ring_attack_damage");

    public StrikeRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.common()));
    }

    public static int getLv(ItemStack stack) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        int lv = tag.getInt(TAG_LV);
        return lv <= 0 ? 1 : lv;
    }

    public static void setLv(ItemStack stack, int lv) {
        int clamped = Math.max(lv, 1);
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt(TAG_LV, clamped));
        stack.set(DataComponents.RARITY, clamped >= 2 ? ModRarities.excellent() : ModRarities.common());
    }

    public static float getPredicateValue(int lv) {
        if (lv <= 1) return 0.0f;
        return 1.0f - 1.0f / lv;
    }

    public static ItemStack createWithLv(Item item, int lv) {
        ItemStack stack = new ItemStack(item);
        setLv(stack, lv);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        int lv = getLv(stack);
        list.add(Component.translatable("tooltip.pasterdream.lv", lv).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.strike_ring.effect", (lv+1)).withStyle(ChatFormatting.BLUE));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        }
        else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    /**
     * 限制只能佩戴一枚强击戒指
     */
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
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        int lv = getLv(stack);
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null) {
                var existing = attr.getModifier(ATTACK_DAMAGE_MODIFIER_ID);
                if (existing == null || existing.amount() != lv) {
                    attr.removeModifier(ATTACK_DAMAGE_MODIFIER_ID);
                    attr.addPermanentModifier(
                            new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, (lv+1),
                                    AttributeModifier.Operation.ADD_VALUE));
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attr != null) {
                attr.removeModifier(ATTACK_DAMAGE_MODIFIER_ID);
                }
            }
        }
    }
