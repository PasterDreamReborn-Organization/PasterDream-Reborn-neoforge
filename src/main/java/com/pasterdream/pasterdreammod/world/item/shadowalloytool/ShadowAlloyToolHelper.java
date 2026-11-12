package com.pasterdream.pasterdreammod.world.item.shadowalloytool;

import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 暗影合金工具共享逻辑。
 * <p>
 * 主手时提供理智光环 -1.2、方块触及 +1（继承影蚀工具），
 * 并提供低 SAN 档位加成倍率。
 * <p>
 * 自带「噬心修补」（内置被动，非真实附魔）：位于背包中时消耗理智修复耐久。
 * <p>
 * 数值均为占位，待 runClient 原型调整（见 document/design/item/暗影合金工具.md）。
 */
public final class ShadowAlloyToolHelper {

    // ==== 主手属性（继承自影蚀） ====
    private static final ResourceLocation SAN_VARIABILITY_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_erosion_san_variability");
    private static final String SAN_VARIABILITY_NAME = "pasterdream.shadow_alloy.san_variability";
    private static final double SAN_VARIABILITY_AMOUNT = -1.2;
    private static final ResourceLocation BLOCK_REACH_UUID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "shadow_erosion_block_reach");
    private static final String BLOCK_REACH_NAME = "pasterdream.shadow_alloy.block_reach";
    private static final double BLOCK_REACH_AMOUNT = 1;

    // ==== 低 SAN 档位加成倍率（占位，待调） ====
    /** 中档（40%~60%）倍率 */
    static final float LOW_SAN_BONUS_MID = 1.15F;
    /** 高档（20%~40%）倍率 */
    static final float LOW_SAN_BONUS_HIGH = 1.30F;
    /** 最高档（<=20%）倍率 */
    static final float LOW_SAN_BONUS_MAX = 1.50F;

    // ==== 自带「噬心修补」（内置被动） ====
    /** 修复 1 点耐久的理智消耗 */
    private static final double REPAIR_COST = 0.1D;
    /** 噬心修补处理间隔（tick） */
    private static final int REPAIR_INTERVAL = 10;

    private ShadowAlloyToolHelper() {
    }

    static Item.Properties withSanVariability(Item.Properties properties, ItemAttributeModifiers defaults) {
        return properties.attributes(defaults
                .withModifierAdded(ModAttributes.SAN_VARIABILITY,
                        new AttributeModifier(SAN_VARIABILITY_UUID, SAN_VARIABILITY_AMOUNT,
                                AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(BLOCK_REACH_UUID, BLOCK_REACH_AMOUNT,
                                AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND));
    }

    /** 依据当前 SAN 比例返回低 SAN 加成倍率（挖掘速度与攻击伤害共用）。 */
    static float getLowSanMultiplier(ServerPlayer player) {
        double maxSan = SanHelper.getPlayerMaxSanEffective(player);
        if (maxSan <= 0.0D) return 1.0F;
        double ratio = SanHelper.getPlayerSan(player) / maxSan;
        if (ratio <= 0.2D) return LOW_SAN_BONUS_MAX;
        if (ratio <= 0.4D) return LOW_SAN_BONUS_HIGH;
        if (ratio <= 0.6D) return LOW_SAN_BONUS_MID;
        return 1.0F;
    }

    /**
     * 自带「噬心修补」（内置被动，非真实附魔，效仿融梦水晶系列）：
     * 工具位于背包中时，每 {@link #REPAIR_INTERVAL} tick 消耗 {@link #REPAIR_COST} 理智修复 1 点耐久。
     * 创造模式不消耗理智；理智不足则停止修复。
     */
    public static void onInventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;
        if (player.tickCount % REPAIR_INTERVAL != 0) return;
        if (stack.getDamageValue() < 1) return;

        var san = player.getData(ModAttachments.SAN);
        if (!san.getIsSanEnabled()) return;
        boolean free = player.isCreative();
        if (!free && san.getSanValue() < REPAIR_COST) return;
        if (!free) {
            SanHelper.addPlayerSanAndSync(player, -REPAIR_COST);
        }
        stack.setDamageValue(stack.getDamageValue() - 1);
    }

    public static void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.passive"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.repair_header"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.shadow_alloy_tool.repair"));
    }
}
