package com.pasterdream.pasterdreammod.world.item.moltengoldtool;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 炙焰金剑 —— 命中时点燃目标 3 秒。
 */
public class MoltenGoldSwordItem extends SwordItem implements MoltenGoldTool {

    public MoltenGoldSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.fireResistant().attributes(SwordItem.createAttributes(tier, damage, speed)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.igniteForSeconds(3);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.molten_gold_sword.2"));
    }
}
