package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.effect.ProtectedRemovalEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

/** 保留来源的单次和全部效果移除保护；1.21 效果键改为 Holder。 */
@Mixin(LivingEntity.class)
public class ProtectedEffectRemovalMixin {

    @Inject(method = "removeEffect", at = @At("HEAD"), cancellable = true)
    private void pasterdream$protectRemoveEffect(Holder<MobEffect> effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (effect.value() instanceof ProtectedRemovalEffect protectedEffect
                && !protectedEffect.consumeRemovalAllowance(self)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "removeAllEffects", at = @At("HEAD"), cancellable = true)
    private void pasterdream$protectRemoveAllEffects(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        List<Holder<MobEffect>> effects = new ArrayList<>(self.getActiveEffectsMap().keySet());
        boolean hasProtected = false;
        for (Holder<MobEffect> effect : effects) {
            if (effect.value() instanceof ProtectedRemovalEffect) {
                hasProtected = true;
                break;
            }
        }
        if (!hasProtected) {
            return; // 无受保护效果：交给原方法
        }
        boolean removed = false;
        for (Holder<MobEffect> effect : effects) {
            if (effect.value() instanceof ProtectedRemovalEffect protectedEffect
                    && !protectedEffect.hasRemovalAllowance(self)) {
                continue; // 未授权：保留受保护效果
            }
            if (self.removeEffect(effect)) {
                removed = true;
            }
        }
        cir.setReturnValue(removed);
    }
}
