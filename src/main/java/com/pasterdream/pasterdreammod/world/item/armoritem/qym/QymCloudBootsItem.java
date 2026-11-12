package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.tag.ModItemTags;
import com.pasterdream.pasterdreammod.world.item.IndestructibleItemEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
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

public class QymCloudBootsItem extends ArmorItem {

    public QymCloudBootsItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(this.type.getSlot());
        return super.getDefaultAttributeModifiers()
                .withModifierAdded(ModAttributes.SAN_VARIABILITY,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "qym_cloud_boots_san_variability"), -1,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.qym_cloud_boots.blink"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cloud_boots.skill_cd"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.set_bonus"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.damage_reduce"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.magic_damage"));
        list.add(Component.translatable("tooltip.pasterdream.qym_cat_ears.dream_evasion"));
        super.appendHoverText(stack, context, list, flag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        super.inventoryTick(stack, level, entity, slotId, selected);
        if (!level.isClientSide && entity instanceof ServerPlayer sp
                && sp.getItemBySlot(EquipmentSlot.FEET) == stack) {
            sp.removeEffect(ModEffects.BLINK_COOLDOWN);
            BuiltInRegistries.ITEM.getTagOrEmpty(ModItemTags.SKILL_COOLDOWN)
                    .forEach(holder -> sp.getCooldowns().removeCooldown(holder.value()));
        }
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
