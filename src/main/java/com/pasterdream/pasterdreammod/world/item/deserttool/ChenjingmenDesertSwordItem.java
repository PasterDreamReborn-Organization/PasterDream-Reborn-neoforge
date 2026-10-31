package com.pasterdream.pasterdreammod.world.item.deserttool;

import com.pasterdream.pasterdreammod.helper.cooldown.SkillCooldownHelper;
import com.pasterdream.pasterdreammod.helper.cooldown.SkillLockHelper;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChenjingmenDesertSwordItem extends SwordItem {

    private static final int COOLDOWN_TICKS = 200;
    private static final double SHARPNESS_DAMAGE_BONUS = 0.5;
    private static final double SKILL_BASE_DAMAGE = 5.0;
    private static final double LOST_HP_RATIO_MULTIPLIER = 2.0;
    private static final double LOST_HP_RATIO_OFFSET = 1.0;
    private static final String SKILL_TAG = "skill";

    public ChenjingmenDesertSwordItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, damage, speed)));
    }

    private static boolean isSkillActive(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(SKILL_TAG);
    }

    private static void setSkillActive(ItemStack stack, boolean active) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putBoolean(SKILL_TAG, active));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        if (selected && entity instanceof LivingEntity living && !level.isClientSide()) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 0, false, false));
            living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2, 0, false, false));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (isSkillActive(stack)) {
            setSkillActive(stack, false);
            if (attacker instanceof Player player) {
                Holder<Enchantment> sharpness = target.level().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS);
                double pasterAtk = player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + stack.getEnchantmentLevel(sharpness) * SHARPNESS_DAMAGE_BONUS;
                double lostHpRatio = (player.getMaxHealth() - player.getHealth()) / player.getMaxHealth();
                float bonusDamage = (float) (SKILL_BASE_DAMAGE + (lostHpRatio * LOST_HP_RATIO_MULTIPLIER + LOST_HP_RATIO_OFFSET) * pasterAtk)
                        * SkillCooldownHelper.getSkillDamageMultiplier(player);
                target.invulnerableTime = 0;
                target.hurt(player.damageSources().playerAttack(player), bonusDamage);
            }
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    ModSounds.SKILL_HIT.get(), target.getSoundSource(), 1.5f, 1.0f);
            if (target.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), target.getX(), target.getY(), target.getZ(), 64, 1.5, 1.8, 1.5, 0.2);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        ItemStack stack = player.getItemInHand(hand);
        if (SkillLockHelper.isSkillLocked(player)) return InteractionResultHolder.fail(stack);
        if (!level.isClientSide && !isSkillActive(stack)) {
            setSkillActive(stack, true);
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 60, 0, false, false));
            SkillCooldownHelper.applySharedCooldown(player, COOLDOWN_TICKS);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SWORD_SLASH.get(), player.getSoundSource(), 0.8f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticleTypes.BUFF_0_PARTICLE.get(), player.getX(), player.getY() - 0.5, player.getZ(), 20, 0.5, 1, 0.5, 1);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.1"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.2"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.3"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.4"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.5"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.6"));
        tooltip.add(Component.translatable("tooltip.pasterdreammod.chenjingmen_desert_sword.7"));
    }
}
