package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pasterdream.pasterdreammod.world.enchantment.EnchantmentHandler;
import com.pasterdream.pasterdreammod.world.enchantment.HeartDevourCurseHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Stack;

@Mixin(LivingEntity.class)
public class EnchantmentDamageMixin {
    @Shadow
    protected Stack<DamageContainer> damageContainers;

    /** 对齐 Forge LivingHurtEvent：盾牌与无敌帧处理之后、护甲减伤之前。 */
    @WrapOperation(method = "hurt",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),
            require = 2)
    private void pasterdream$applyArmorEnchantments(LivingEntity entity, DamageSource source, float amount,
                                                    Operation<Void> original) {
        DamageContainer damage = damageContainers.peek();
        float incoming = damage.getNewDamage();
        if (incoming > 0.0F && !entity.isInvulnerableTo(source)) {
            float adjusted = HeartDevourCurseHandler.applyHeartDevour(entity, incoming);
            adjusted = EnchantmentHandler.applyShelter(entity, adjusted);
            if (adjusted != incoming) {
                damage.setNewDamage(adjusted);
                amount = adjusted;
            }
        }
        original.call(entity, source, amount);
    }
}
