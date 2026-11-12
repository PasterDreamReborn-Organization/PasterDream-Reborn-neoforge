package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.List;

import javax.annotation.Nullable;

public class QymSwayingSkirtItem extends ArmorItem {

    public QymSwayingSkirtItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(this.type.getSlot());
        return super.getDefaultAttributeModifiers()
                .withModifierAdded(ModAttributes.SAN_VARIABILITY,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "qym_swaying_skirt_san_variability"), -1,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.qym_swaying_skirt.energy"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.set_bonus"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.damage_reduce"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.magic_damage"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.dream_evasion"));
        super.appendHoverText(stack, context, list, flag);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        var entity = new IndestructibleItemEntity(level, location.getX(), location.getY(), location.getZ(), stack);
        entity.setDefaultPickUpDelay();
        entity.setDeltaMovement(location.getDeltaMovement());
        return entity;
    }
}
