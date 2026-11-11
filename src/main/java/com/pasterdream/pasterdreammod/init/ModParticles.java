package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.particle.Buff0Particle;
import com.pasterdream.pasterdreammod.client.particle.CrackParticle;
import com.pasterdream.pasterdreammod.client.particle.DreamFertilizerParticle;
import com.pasterdream.pasterdreammod.client.particle.Dust0Particle;
import com.pasterdream.pasterdreammod.client.particle.FurySpellParticle;
import com.pasterdream.pasterdreammod.client.particle.InfernoParticle;
import com.pasterdream.pasterdreammod.client.particle.LeavesParticle;
import com.pasterdream.pasterdreammod.client.particle.MeltdreamCrystalParticle;
import com.pasterdream.pasterdreammod.client.particle.MeltdreamCrystalBigParticle;
import com.pasterdream.pasterdreammod.client.particle.PoisonGasParticle;
import com.pasterdream.pasterdreammod.client.particle.RejuvenationParticle;
import com.pasterdream.pasterdreammod.client.particle.SnowflakeParticle;
import com.pasterdream.pasterdreammod.client.particle.YellowSmokeParticle;
import com.pasterdream.pasterdreammod.client.particle.SharpSwordSlashParticle;
import com.pasterdream.pasterdreammod.client.particle.SoulParticle;
import com.pasterdream.pasterdreammod.client.particle.SporeParticle;
import com.pasterdream.pasterdreammod.client.particle.FeatherWhiteParticle;
import com.pasterdream.pasterdreammod.client.particle.FoxFire0Particle;
import com.pasterdream.pasterdreammod.client.particle.FoxFire1Particle;
import com.pasterdream.pasterdreammod.client.particle.GoldenParticle;
import com.pasterdream.pasterdreammod.client.particle.SilverParticle;
import com.pasterdream.pasterdreammod.client.particle.TerraswordWaveParticle;
import com.pasterdream.pasterdreammod.client.particle.FireflyParticle;
import com.pasterdream.pasterdreammod.client.particle.FireflyGlassJarParticle;
import com.pasterdream.pasterdreammod.client.particle.LightFireflyGlassJarParticle;
import com.pasterdream.pasterdreammod.client.particle.ShadowStoneParticle;
import com.pasterdream.pasterdreammod.client.particle.SquealWaveParticle;
import com.pasterdream.pasterdreammod.client.particle.WhiteSwordSparkParticle;
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
        event.registerSpriteSet(ModParticleTypes.LEAVES_PARTICLE.get(), LeavesParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SHARP_SWORD_SLASH.get(), SharpSwordSlashParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.DUST_0_PARTICLE.get(), Dust0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SPORE_PARTICLE.get(), SporeParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.POISON_GAS_PARTICLE.get(), PoisonGasParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.BUFF_0_PARTICLE.get(), Buff0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SOUL_PARTICLE.get(), SoulParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.DREAMFERTILIZER_PARTICLE.get(), DreamFertilizerParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.CRACK_0_PARTICLE.get(), CrackParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.MELTDREAM_CRYSTAL_PARTICLE.get(), MeltdreamCrystalParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.MELTDREAM_CRYSTAL_BIG_PARTICLE.get(), MeltdreamCrystalBigParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.REJUVENATION_PARTICLE.get(), RejuvenationParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.YELLOW_SMOKE_PARTICLE.get(), YellowSmokeParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.BERSERK_PARTICLE.get(), FurySpellParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SNOWFLAKE_0_PARTICLE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SNOWFLAKE_1_PARTICLE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.INFERNO_PARTICLE.get(), InfernoParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.TERRASWORD_WAVE_PARTICLE.get(), TerraswordWaveParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FOX_FIRE_0_PARTICLE.get(), FoxFire0Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FOX_FIRE_1_PARTICLE.get(), FoxFire1Particle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FEATHER_WHITE_PARTICLE.get(), FeatherWhiteParticle::provider);
        event.registerSpriteSet(ModParticleTypes.GOLDEN_PARTICLE.get(), GoldenParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SILVER_PARTICLE.get(), SilverParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FIREFLY_GLASS_JAR_PARTICLE.get(), FireflyGlassJarParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.LIGHT_FIREFLY_GLASS_JAR_PARTICLE.get(), LightFireflyGlassJarParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FIREFLY_PARTICLE.get(), FireflyParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SHADOW_STONE_PARTICLE.get(), ShadowStoneParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SQUEAL_WAVE_PARTICLE.get(), SquealWaveParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.WHITE_SWORD_SPARK_PARTICLE.get(), WhiteSwordSparkParticle.Provider::new);
    }
}
