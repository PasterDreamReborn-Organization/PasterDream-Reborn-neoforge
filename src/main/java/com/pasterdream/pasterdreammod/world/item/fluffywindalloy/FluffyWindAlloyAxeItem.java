package com.pasterdream.pasterdreammod.world.item.fluffywindalloy;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 萦风合金斧 —— 被动「疾风过境」 + 战技「落叶秋风」。
 * <p>
 * 潜行挖掘原木时，连锁破坏相连原木并吹落周围树叶，掉落物被风卷到玩家身边。
 * 近战攻击同样吃到疾风被动：伤害=(1+移动速度)×攻击力。
 */
public class FluffyWindAlloyAxeItem extends AxeItem implements FluffyWindAlloyTool {

    public FluffyWindAlloyAxeItem(Tier tier, Properties properties) {
        super(tier, properties.attributes(DiggerItem.createAttributes(tier, 5.5f, -2.5f)));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player
                && player.isShiftKeyDown() && state.is(BlockTags.LOGS)) {
            int extra = FluffyWindAlloyToolHelper.breakLogChain(level, pos, state, player, stack);
            if (extra > 0) {
                stack.hurtAndBreak(extra, entity, EquipmentSlot.MAINHAND);
            }
        }
        return super.mineBlock(stack, level, state, pos, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.skill_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.desc1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_tool.passive_name"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.passive1"));
        tooltip.add(Component.translatable("tooltip.pasterdream.fluffy_wind_alloy_axe.passive2"));
    }

    // ==================== 被动 · 攻击吃到疾风过境 ====================

    /** 施加自算伤害期间置位，防止对自身伤害再次套用形成递归 */
    private static final String APPLYING_TAG = "pasterdream:fluffy_wind_alloy_axe_applying";

    @EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
    public static class WindAlloyAxePassiveHandler {
        @SubscribeEvent
        public static void onLivingHurt(LivingIncomingDamageEvent event) {
            if (!(event.getSource().getEntity() instanceof Player player)) return;
            if (!(player.getMainHandItem().getItem() instanceof FluffyWindAlloyAxeItem)) return;
            if (player.getPersistentData().getBoolean(APPLYING_TAG)) return;
            if (event.getSource().getDirectEntity() != player) return;

            LivingEntity target = event.getEntity();

            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float critRatio = atk > 0 ? event.getAmount() / atk : 1.0f; // 保留暴击/附魔比例
            double speed = Math.max(player.getAttributeValue(Attributes.MOVEMENT_SPEED),
                    player.getDeltaMovement().horizontalDistance());
            float mainDamage = (float) ((1 + speed) * atk) * critRatio;

            event.setCanceled(true);
            player.getPersistentData().putBoolean(APPLYING_TAG, true);
            target.invulnerableTime = 0;
            target.hurt(target.level().damageSources().playerAttack(player), mainDamage);
            player.getPersistentData().remove(APPLYING_TAG);
        }
    }
}
