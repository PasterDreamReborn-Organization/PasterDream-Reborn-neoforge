package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.List;

public class MeltDreamCrystalArmorItem extends ArmorItem {
    /** 每件融梦能量上限加成 */
    private static final double MAX_ENERGY_PER_PIECE = 5.0;
    /** 每件每分钟融梦能量回复量（融梦光环） */
    private static final double ENERGY_PER_MIN_PER_PIECE = 1.0;

    public MeltDreamCrystalArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(this.type.getSlot());
        return super.getDefaultAttributeModifiers()
                .withModifierAdded(Attributes.MAX_HEALTH,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "melt_dream_armor_health_" + this.type.getName()), 2.0,
                                AttributeModifier.Operation.ADD_VALUE), slot)
                .withModifierAdded(ModAttributes.MAX_MELT_DREAM_ENERGY_EXTRA,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "melt_dream_armor_max_energy_" + this.type.getName()), MAX_ENERGY_PER_PIECE,
                                AttributeModifier.Operation.ADD_VALUE), slot)
                .withModifierAdded(ModAttributes.MELT_DREAM_VARIABILITY,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "melt_dream_armor_regeneration_" + this.type.getName()), ENERGY_PER_MIN_PER_PIECE,
                                AttributeModifier.Operation.ADD_VALUE), slot);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.MELT_DREAM)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.7"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.melt_dream_armor.8"));
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
