package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 药水注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModPotions.java}。
 */
public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<Potion, Potion> LUCK = POTIONS.register("luck",
            () -> new Potion(new MobEffectInstance(MobEffects.LUCK, 3600, 0)));
    public static final DeferredHolder<Potion, Potion> LONG_LUCK = POTIONS.register("long_luck",
            () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9600, 0)));
    public static final DeferredHolder<Potion, Potion> STRONG_LUCK = POTIONS.register("strong_luck",
            () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 1800, 1)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var brewing = event.getBuilder();
        var luck = LUCK.getDelegate();
        brewing.addMix(Potions.AWKWARD, ModItems.FORTUNE_JELLY.get(), luck);
        brewing.addMix(luck, Items.GLOWSTONE_DUST, STRONG_LUCK.getDelegate());
        brewing.addMix(luck, Items.REDSTONE, LONG_LUCK.getDelegate());
    }

    private ModPotions() {
    }
}
