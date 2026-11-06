package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.renderer.FluffyWindAlloyArmorRenderer;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.world.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

/** 萦风合金护甲：单件移速与战技冷却缩减，满套效果由处理器结算。 */
public class FluffyWindAlloyArmorItem extends ArmorItem implements GeoItem {
    public static final double MOVE_SPEED_PER_PIECE = 0.05;
    public static final double SKILL_COOLDOWN_PER_PIECE = 0.05;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FluffyWindAlloyArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(this.type.getSlot());
        return super.getDefaultAttributeModifiers()
                .withModifierAdded(Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "fluffy_wind_alloy_armor_speed_" + this.type.getName()), MOVE_SPEED_PER_PIECE,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot)
                .withModifierAdded(ModAttributes.SKILL_COOLDOWN_REDUCTION,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "fluffy_wind_alloy_armor_cooldown_" + this.type.getName()), SKILL_COOLDOWN_PER_PIECE,
                                AttributeModifier.Operation.ADD_VALUE), slot);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.FLUFFY_WIND_ALLOY)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        for (int i = 1; i <= 8; i++) {
            tooltip.add(Component.translatable("tooltip.pasterdreammod.fluffy_wind_alloy_armor." + i));
        }
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> net.minecraft.client.model.HumanoidModel<?> getGeoArmorRenderer(
                    T entity, ItemStack stack, EquipmentSlot slot, net.minecraft.client.model.HumanoidModel<T> original) {
                if (this.renderer == null) this.renderer = new FluffyWindAlloyArmorRenderer();
                this.renderer.prepForRender(entity, stack, slot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
