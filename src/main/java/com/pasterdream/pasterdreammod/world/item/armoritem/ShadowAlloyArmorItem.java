package com.pasterdream.pasterdreammod.world.item.armoritem;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.renderer.ShadowAlloyArmorRenderer;
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

/**
 * 暗影合金盔甲 —— 灯影之下毕业装备（单件 +4 最大生命 / +1 攻击力），使用 GeckoLib GEO 模型渲染。
 *
 * <p>四个部位共用 {@code geo/shadow_alloy_armor.geo.json} 一个模型，
 * 由 {@link GeoArmorRenderer} 按装备槽（HEAD/CHEST/LEGS/FEET）
 * 与约定骨骼名（armorHead / armorBody / armorRightArm / armorLeftArm /
 * armorRightLeg / armorLeftLeg / armorRightBoot / armorLeftBoot）自动裁剪显示，
 * 无需每个部位单独建模型或单独做动画。
 *
 * <p>套装效果「黑暗守望」由 {@code ShadowAlloyArmorHandler} 在事件中按满套结算。
 */
public class ShadowAlloyArmorItem extends ArmorItem implements GeoItem {

    /** 单件最大生命值加成 */
    public static final double HEALTH_PER_PIECE = 4.0;
    /** 单件攻击力加成 */
    public static final double ATTACK_PER_PIECE = 1.0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ShadowAlloyArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(this.type.getSlot());
        return super.getDefaultAttributeModifiers()
                .withModifierAdded(Attributes.MAX_HEALTH,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "shadow_alloy_armor_health_" + this.type.getName()), HEALTH_PER_PIECE,
                                AttributeModifier.Operation.ADD_VALUE), slot)
                .withModifierAdded(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID,
                                "shadow_alloy_armor_attack_" + this.type.getName()), ATTACK_PER_PIECE,
                                AttributeModifier.Operation.ADD_VALUE), slot);
    }

    /** 是否穿满一整套暗影合金盔甲。 */
    public static boolean hasFullSet(LivingEntity entity) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack stack = entity.getItemBySlot(slot);
                if (!(stack.getItem() instanceof ArmorItem armorItem
                        && armorItem.getMaterial() == ModArmorMaterials.SHADOW_ALLOY)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_armor.5"));
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> net.minecraft.client.model.HumanoidModel<?> getGeoArmorRenderer(
                    T entity, ItemStack stack, EquipmentSlot slot, net.minecraft.client.model.HumanoidModel<T> original) {
                if (this.renderer == null) this.renderer = new ShadowAlloyArmorRenderer();
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
