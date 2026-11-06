package com.pasterdream.pasterdreammod.world.item.shadowerosiontool;

import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 影蚀工具共享逻辑 —— 主手时附加 SAN 波动属性修正（原版为 -1.2）。
 */
public final class ShadowErosionToolHelper {

    private static final ResourceLocation SAN_VARIABILITY_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_erosion_san_variability");
    private static final String SAN_VARIABILITY_NAME = "pasterdream.shadow_erosion.san_variability";
    private static final double SAN_VARIABILITY_AMOUNT = -1.2;
    private static final ResourceLocation BLOCK_REACH_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_erosion_block_reach");
    private static final String BLOCK_REACH_NAME = "pasterdream.shadow_erosion.block_reach";
    private static final double BLOCK_REACH_AMOUNT = 1;

    static Item.Properties withSanVariability(Item.Properties properties, ItemAttributeModifiers defaults) {
        return properties.attributes(defaults
                .withModifierAdded(ModAttributes.SAN_VARIABILITY,
                        new AttributeModifier(SAN_VARIABILITY_UUID, SAN_VARIABILITY_AMOUNT,
                                AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(BLOCK_REACH_UUID, BLOCK_REACH_AMOUNT,
                                AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));
    }
    static void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_erosion_tool.2"));
    }
}
