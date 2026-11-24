package com.pasterdream.pasterdreammod.world.enchantment;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * 附魔效果处理器。
 * <ul>
 *     <li>疾风连击：剑每级 +6% 攻击速度、斧每级 +4%（ADD_MULTIPLIED_BASE，最高 4 级）。</li>
 *     <li>聚梦：护甲每级 +0.25 融梦光环（{@code MELT_DREAM_VARIABILITY}，单位/分钟），等级不裁剪。</li>
 *     <li>庇护：每级 -2% 受到的伤害（全身护甲叠加；超过 12 级用反比例函数限制减伤上限）。</li>
 * </ul>
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class EnchantmentHandler {

    private static final ResourceLocation SWIFT_STRIKE_ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "swift_strike");

    private EnchantmentHandler() {
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        addGatherDreamAura(event);
        addSwiftStrike(event);
    }

    /** 疾风连击：剑每级 +6%、斧每级 +4% 攻击速度。 */
    private static void addSwiftStrike(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        int level = ModEnchantment.getLevel(stack, ModEnchantment.SWIFT_STRIKE_ENCHANTMENT);
        if (level <= 0) return;
        double multiplier = stack.getItem() instanceof AxeItem ? 0.04 : 0.06;
        event.addModifier(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        SWIFT_STRIKE_ATTACK_SPEED_ID,
                        level * multiplier,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ), EquipmentSlotGroup.MAINHAND
        );
    }

    /** 聚梦：护甲每级 +0.25 融梦光环；等级不裁剪，>5 级仍然生效。 */
    private static void addGatherDreamAura(ItemAttributeModifierEvent event) {
        if (!(event.getItemStack().getItem() instanceof ArmorItem armorItem)) return;

        // 同件护甲会被查询所有护甲槽位（如护腿的工具提示），只在其实际装备槽生成修饰器
        EquipmentSlot itemSlot = armorItem.getEquipmentSlot();

        int level = ModEnchantment.getLevel(event.getItemStack(), ModEnchantment.DREAM_GATHERING_ENCHANTMENT);
        if (level <= 0) return;
        event.addModifier(
                ModAttributes.MELT_DREAM_VARIABILITY,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "gather_dream/" + itemSlot.getName()),
                        level * 0.25,
                        AttributeModifier.Operation.ADD_VALUE
                ), EquipmentSlotGroup.bySlot(itemSlot)
        );
    }

    /** 庇护：每级 -2% 受到的伤害（全身护甲叠加）。 */
    public static float applyShelter(LivingEntity entity, float amount) {
        int totalLevel = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack armor = entity.getItemBySlot(slot);
            if (!armor.isEmpty()) {
                totalLevel += ModEnchantment.getLevel(armor, ModEnchantment.SHELTER_ENCHANTMENT);
            }
        }

        // 超过12级时，限制减伤上限，使用反比例函数，减伤理论上限不会超过25%
        if (totalLevel > 0) {
            if (totalLevel <= 12) {
                return amount * (1.0f - totalLevel * 0.02f);
            } else {
                double dmgReduce = 25 - 1.2 / (totalLevel - 10.8);
                return amount * (1.0f - (float) (dmgReduce * 0.01));
            }
        }
        return amount;
    }
}
