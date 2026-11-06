package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.effect.ShadowSilenceEffect;
import com.pasterdream.pasterdreammod.world.effect.WindThunderBoonEffect;
import com.pasterdream.pasterdreammod.world.effect.BlinkCooldownEffect;
import com.pasterdream.pasterdreammod.world.effect.EvasionEffect;
import com.pasterdream.pasterdreammod.world.effect.ConfusionEffect;
import com.pasterdream.pasterdreammod.world.effect.CookEffect;
import com.pasterdream.pasterdreammod.world.effect.InsaneEffect;
import com.pasterdream.pasterdreammod.world.effect.TranceEffect;
import com.pasterdream.pasterdreammod.world.effect.LethargyEffect;
import com.pasterdream.pasterdreammod.world.effect.CheerUpEffect;
import com.pasterdream.pasterdreammod.world.effect.RestEffect;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.effect.DyedreamArmorEffect;
import com.pasterdream.pasterdreammod.world.effect.DyedreamUpEffect;
import com.pasterdream.pasterdreammod.world.effect.HighlyToxicEffect;
import com.pasterdream.pasterdreammod.world.effect.SculkArmorEffect;
import com.pasterdream.pasterdreammod.world.effect.MeltDreamCrystalArmorEffect;
import com.pasterdream.pasterdreammod.world.effect.TitaniumArmorEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 药水效果注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModEffects.java}。
 */
public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> TITANIUM_ARMOR =
            MOB_EFFECTS.register("titanium_armor", TitaniumArmorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> EVASION =
            MOB_EFFECTS.register("evasion", EvasionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> BLINK_COOLDOWN =
            MOB_EFFECTS.register("blink_cooldown", BlinkCooldownEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> WIND_THUNDER_BOON =
            MOB_EFFECTS.register("wind_thunder_boon", WindThunderBoonEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> DYEDREAM_ARMOR =
            MOB_EFFECTS.register("dyedream_armor", DyedreamArmorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> DYEDREAM_UP =
            MOB_EFFECTS.register("dyedream_up", DyedreamUpEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SCULK_ARMOR =
            MOB_EFFECTS.register("sculk_armor", SculkArmorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> MELT_DREAM_CRYSTAL_ARMOR =
            MOB_EFFECTS.register("melt_dream_crystal_armor", MeltDreamCrystalArmorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> HIGHLY_TOXIC =
            MOB_EFFECTS.register("highly_toxic", HighlyToxicEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> REST =
            MOB_EFFECTS.register("rest", RestEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> CHEER_UP =
            MOB_EFFECTS.register("cheer_up", CheerUpEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> LETHARGY =
            MOB_EFFECTS.register("lethargy", LethargyEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> TRANCE =
            MOB_EFFECTS.register("trance", TranceEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> INSANE =
            MOB_EFFECTS.register("insane", InsaneEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> COOK =
            MOB_EFFECTS.register("cook", CookEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> CONFUSION =
            MOB_EFFECTS.register("confusion", ConfusionEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SHADOW_SILENCE =
            MOB_EFFECTS.register("shadow_silence", ShadowSilenceEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

    private ModEffects() {
    }
}
