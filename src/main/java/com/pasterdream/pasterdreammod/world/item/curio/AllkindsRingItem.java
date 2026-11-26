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
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class AllkindsRingItem extends Item implements ICurioItem {
    private static final ResourceLocation HEALTH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_health");
    private static final ResourceLocation ATTACK_DAMAGE_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_attack_damage");
    private static final ResourceLocation ATTACK_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_attack_speed");
    private static final ResourceLocation ENTITY_REACH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_entity_reach");
    private static final ResourceLocation BLOCK_REACH_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_block_reach");
    private static final ResourceLocation MOVEMENT_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_movement_speed");
    private static final ResourceLocation SKILL_CD_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_skill_cooldown_reduction");
    private static final ResourceLocation SKILL_MULTIPLIER_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_skill_damage");
    private static final ResourceLocation BLINK_CD_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "allkinds_ring_blink_cooldown_reduction");

    public AllkindsRingItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.legendary()));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH,
                new AttributeModifier(HEALTH_ID, 4.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_ID, 2.0, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(ATTACK_SPEED_ID, 0.1, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(ENTITY_REACH_ID, 0.2, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.BLOCK_INTERACTION_RANGE,
                new AttributeModifier(BLOCK_REACH_ID, 0.5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(MOVEMENT_SPEED_ID, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance skillCd = slotContext.entity().getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
            if (skillCd != null && skillCd.getModifier(SKILL_CD_ID) == null) {
                skillCd.addPermanentModifier(new AttributeModifier(SKILL_CD_ID, 0.05,
                        AttributeModifier.Operation.ADD_VALUE));
            }
            AttributeInstance skillDmg = slotContext.entity().getAttribute(ModAttributes.SKILL_DAMAGE_RATE);
            if (skillDmg != null && skillDmg.getModifier(SKILL_MULTIPLIER_ID) == null) {
                skillDmg.addPermanentModifier(new AttributeModifier(SKILL_MULTIPLIER_ID, 0.05,
                        AttributeModifier.Operation.ADD_VALUE));
            }
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCd != null && blinkCd.getModifier(BLINK_CD_ID) == null) {
                blinkCd.addPermanentModifier(new AttributeModifier(BLINK_CD_ID, 0.05,
                        AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance skillCd = slotContext.entity().getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
            if (skillCd != null) skillCd.removeModifier(SKILL_CD_ID);
            AttributeInstance skillDmg = slotContext.entity().getAttribute(ModAttributes.SKILL_DAMAGE_RATE);
            if (skillDmg != null) skillDmg.removeModifier(SKILL_MULTIPLIER_ID);
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCd != null) blinkCd.removeModifier(BLINK_CD_ID);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.flavor"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.lore"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.skill_cd"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.skill_dmg"));
        list.add(Component.translatable("tooltip.pasterdream.allkinds_ring.blink_cd"));
    }
}
