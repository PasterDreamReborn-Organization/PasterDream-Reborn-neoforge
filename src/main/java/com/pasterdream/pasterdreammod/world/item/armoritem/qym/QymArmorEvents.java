package com.pasterdream.pasterdreammod.world.item.armoritem.qym;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class QymArmorEvents {

    @SubscribeEvent
    public static void onEquipChange(LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer pl)) return;

        if (event.getSlot() == EquipmentSlot.CHEST && !pl.isCreative()) {
            boolean wasEquipped = event.getFrom().getItem() instanceof QymWindShirtItem;
            boolean isEquipped = event.getTo().getItem() instanceof QymWindShirtItem;
            if (wasEquipped != isEquipped) {
                pl.getAbilities().mayfly = isEquipped;
                if (!isEquipped) {
                    pl.getAbilities().flying = false;
                }
                pl.onUpdateAbilities();
            }
        }

        if (event.getSlot() == EquipmentSlot.LEGS) {
            boolean wasEquipped = event.getFrom().getItem() instanceof QymSwayingSkirtItem;
            boolean isEquipped = event.getTo().getItem() instanceof QymSwayingSkirtItem;
            if (wasEquipped != isEquipped) {
                MeltDreamEnergyHelper.setPlayerMeltDreamEnergyIsNeed(pl, !isEquipped);
                if (isEquipped) {
                    MeltDreamEnergyHelper.setPlayerMeltDreamEnergyAndSync(pl,
                            MeltDreamEnergyHelper.getPlayerMaxMeltDreamEnergyEffective(pl));
                }
            }
        }
    }

    /**
     * QYM套装：攻击附带目标当前生命值5%的魔法伤害（20tick冷却）。
     *
     * <p>1.21 的 {@link net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent} 在原版设置
     * 受击无敌时间之前触发，在此追加伤害会改写无敌帧与上次受击量，导致主攻击的结算被魔法伤害吃掉；
     * 因此改到护甲结算之后、扣血之前的 {@link LivingDamageEvent.Pre}，追加后还原无敌时间与上次受击量。
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (attacker.level().isClientSide()) return;
        if (attacker == event.getEntity()) return;
        if (!QymCatEarsItem.hasFullSet(attacker)) return;
        if (event.getNewDamage() <= 0.0F) return;

        LivingEntity target = event.getEntity();
        long gameTime = attacker.level().getGameTime();
        long lastProc = attacker.getPersistentData().getLong("pasterdream.qym_magic_last");
        if (gameTime - lastProc < 20) return;
        attacker.getPersistentData().putLong("pasterdream.qym_magic_last", gameTime);

        // 魔法伤害倍率由 MagicDamageHandler 在 indirectMagic 结算时统一应用，此处不再重复相乘。
        float magicDamage = target.getHealth() * 0.05F;
        int invulnerableTime = target.invulnerableTime;
        float lastHurt = target.lastHurt;
        target.invulnerableTime = 0;
        target.hurt(target.level().damageSources().indirectMagic(attacker, attacker), magicDamage);
        target.invulnerableTime = invulnerableTime;
        target.lastHurt = lastHurt;
    }
}
