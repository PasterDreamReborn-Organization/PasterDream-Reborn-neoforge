package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
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

public class FourleafCloverCurioItem extends Item implements ICurioItem {
    private static final ResourceLocation HEALTH_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fourleaf_clover_health");
    private static final ResourceLocation LUCK_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "fourleaf_clover_luck");

    public FourleafCloverCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.excellent()));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() != null) {
            return CuriosApi.getCuriosInventory(slotContext.entity()).map(handler ->
                    handler.findFirstCurio(stack.getItem()).isEmpty()).orElse(true);
        }
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var healthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null && healthAttr.getModifier(HEALTH_UUID) == null) {
                healthAttr.addPermanentModifier(new AttributeModifier(HEALTH_UUID, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null && luckAttr.getModifier(LUCK_UUID) == null) {
                luckAttr.addPermanentModifier(new AttributeModifier(LUCK_UUID, 6.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide()) {
            var healthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null) healthAttr.removeModifier(HEALTH_UUID);
            var luckAttr = entity.getAttribute(Attributes.LUCK);
            if (luckAttr != null) luckAttr.removeModifier(LUCK_UUID);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.effect.health"));
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.effect.luck"));
        list.add(Component.translatable("tooltip.pasterdream.fourleaf_clover_curio.flavor"));
    }
}
