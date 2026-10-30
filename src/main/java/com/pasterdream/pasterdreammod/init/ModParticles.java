package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.particle.Buff0Particle;
import com.pasterdream.pasterdreammod.client.particle.SharpSwordSlashParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

/**
 * 粒子 Provider 注册中心（客户端）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModParticles.java}。
 * 粒子行为类放 {@code client/particle/}，纹理 JSON 手动维护于 {@code assets/pasterdream/particles/}。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ModParticles {
    private ModParticles() {
    }

    @SubscribeEvent
    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.SHARP_SWORD_SLASH.get(), SharpSwordSlashParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.BUFF_0_PARTICLE.get(), Buff0Particle.Provider::new);
    }
}
