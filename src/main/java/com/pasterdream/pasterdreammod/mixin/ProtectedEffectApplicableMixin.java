package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.world.effect.ProtectedRemovalEffect;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** NeoForge 在 canBeAffected 前判定 Applicable 事件，保护需在事件结果处执行。 */
@Mixin(MobEffectEvent.Applicable.class)
public class ProtectedEffectApplicableMixin {

    @Inject(method = "getApplicationResult", at = @At("HEAD"), cancellable = true, remap = false)
    private void pasterdream$forceCanBeAffected(CallbackInfoReturnable<Boolean> cir) {
        MobEffectEvent.Applicable self = (MobEffectEvent.Applicable) (Object) this;
        if (self.getEffectInstance() != null
                && self.getEffectInstance().getEffect().value() instanceof ProtectedRemovalEffect protectedEffect
                && protectedEffect.forceApplicableWhenAffected()) {
            cir.setReturnValue(true);
        }
    }
}
