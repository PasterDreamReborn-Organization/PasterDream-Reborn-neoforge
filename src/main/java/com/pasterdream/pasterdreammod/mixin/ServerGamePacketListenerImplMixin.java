package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
    @Shadow
    public ServerPlayer player;

    @Shadow
    private boolean clientIsFloating;

    @Inject(method = "tick", at = @At("HEAD"))
    private void pasterdream$exemptSlowFallingFromFloatingCheck(CallbackInfo ci)
    {
        if (player.hasEffect(MobEffects.SLOW_FALLING))
        {
            this.clientIsFloating = false;
        }
    }
}
