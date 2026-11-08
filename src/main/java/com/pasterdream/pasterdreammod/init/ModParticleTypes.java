package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 粒子类型注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModParticleTypes.java}。
 * 客户端 Provider 在 {@code init/ModParticles} 的 {@code RegisterParticleProvidersEvent} 中注册。
 */
public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHARP_SWORD_SLASH =
            PARTICLE_TYPES.register("sharp_sword_slash", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DUST_0_PARTICLE =
            PARTICLE_TYPES.register("dust", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPORE_PARTICLE =
            PARTICLE_TYPES.register("spore", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> POISON_GAS_PARTICLE =
            PARTICLE_TYPES.register("poison_gas", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BUFF_0_PARTICLE =
            PARTICLE_TYPES.register("buff", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SOUL_PARTICLE =
            PARTICLE_TYPES.register("soul", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRACK_0_PARTICLE =
            PARTICLE_TYPES.register("crack", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MELTDREAM_CRYSTAL_PARTICLE =
            PARTICLE_TYPES.register("melt_dream_crystal", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> REJUVENATION_PARTICLE =
            PARTICLE_TYPES.register("rejuvenation", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_SMOKE_PARTICLE =
            PARTICLE_TYPES.register("yellow_smoke", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BERSERK_PARTICLE =
            PARTICLE_TYPES.register("berserk", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE_0_PARTICLE =
            PARTICLE_TYPES.register("snowflake_0", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE_1_PARTICLE =
            PARTICLE_TYPES.register("snowflake_1", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> INFERNO_PARTICLE =
            PARTICLE_TYPES.register("inferno", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TERRASWORD_WAVE_PARTICLE =
            PARTICLE_TYPES.register("terrasword_wave", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOX_FIRE_0_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_0", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOX_FIRE_1_PARTICLE =
            PARTICLE_TYPES.register("fox_fire_1", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FEATHER_WHITE_PARTICLE =
            PARTICLE_TYPES.register("feather_white", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_PARTICLE =
            PARTICLE_TYPES.register("golden", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SILVER_PARTICLE =
            PARTICLE_TYPES.register("silver", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    private ModParticleTypes() {
    }
}
