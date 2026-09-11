package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 粒子类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModParticleTypes.java}。
 * 客户端 Provider 在 {@code init/ModParticles} 的 {@code RegisterParticleProvidersEvent} 中注册。
 */
public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册粒子类型（SimpleParticleType）

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    private ModParticleTypes() {
    }
}
