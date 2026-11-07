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

public class RedDewRingItem extends Item implements ICurioItem {
    public static final String TAG_LV = "lv";
    private static final ResourceLocation HEALTH_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "red_dew_ring_health");

    public RedDewRingItem() {
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
        stack.set(DataComponents.RARITY, switch (clamped) {
            case 2 -> ModRarities.excellent();
            case 3 -> ModRarities.superior();
            case 4 -> ModRarities.master();
            default -> ModRarities.common();
        });
    }

    /**
     * 根据等级计算 ItemProperties 的 predicate 值（0~1）
     * 公式: 1 - 1/lv，lv=1 → 0, lv=2 → 0.5, lv=3 → 0.667, lv=4 → 0.75, lv→∞ → 1.0
     * 魔改作者添加新等级贴图时，在模型 JSON 的 overrides 中使用此值作为 predicate
     * 如果不满意现在的属性加成那我建议你用kubejs curios或者hotai重写吧，我也没招了......
     */
    public static float getPredicateValue(int lv) {
        if (lv <= 1) return 0.0f;
        return 1.0f - 1.0f / lv;
    }

    public static ItemStack createWithLv(Item item, int lv) {
        ItemStack stack = new ItemStack(item);
        setLv(stack, lv);
        return stack;
    }

    /**
     * 根据等级返回增加的最大生命值：lv1 → 4, lv2 → 6, lv3 → 8, lv4 → 10
     */
    public static int getHealthBonus(int lv) {
        return 2 * Math.max(lv, 1) + 2;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        int lv = getLv(stack);
        list.add(Component.translatable("tooltip.pasterdream.lv", lv).withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("tooltip.pasterdream.red_dew_ring.effect", getHealthBonus(lv)).withStyle(ChatFormatting.BLUE));
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.pasterdream.introduction.tooltip"));
            list.add(Component.translatable("tooltip.pasterdream.only_one.tooltip"));
        }
        else {
            list.add(Component.translatable("tooltip.pasterdream.hold_shift"));
        }
    }

    /**
     * 限制只能佩戴一枚红露滴戒指
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
        int bonus = getHealthBonus(getLv(stack));
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attr != null) {
                var existing = attr.getModifier(HEALTH_MODIFIER_ID);
                if (existing == null || existing.amount() != bonus) {
                    attr.removeModifier(HEALTH_MODIFIER_ID);
                    attr.addPermanentModifier(
                            new AttributeModifier(HEALTH_MODIFIER_ID, bonus,
                                    AttributeModifier.Operation.ADD_VALUE));
                }
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var attr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (attr != null) {
                attr.removeModifier(HEALTH_MODIFIER_ID);
                // 卸下后当前血量不能超过新的最大血量
                if (entity.getHealth() > entity.getMaxHealth()) {
                    entity.setHealth(entity.getMaxHealth());
                }
            }
        }
    }
}
