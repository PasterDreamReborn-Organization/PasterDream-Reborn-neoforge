package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 自定义属性注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModAttributes.java}。
 * 用于技能参数（瞬身术 cd/consume/range、战技冷却/伤害、魔法伤害、SAN/能量波动等）。
 */
public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, PasterDreamMod.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> SKILL_COOLDOWN_REDUCTION =
            ATTRIBUTES.register("skill_cooldown_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.skill_cooldown_reduction", 0.0, -10.0, 1.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SKILL_DAMAGE_RATE =
            ATTRIBUTES.register("skill_damage_rate", () -> new RangedAttribute(
                    "attribute.pasterdream.skill_damage_rate", 1.0, 0.0, 100.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MAX_MELT_DREAM_ENERGY_EXTRA =
            ATTRIBUTES.register("max_melt_dream_energy_extra", () -> new RangedAttribute(
                    "attribute.pasterdream.max_melt_dream_energy_extra", 0.0, -100000.0, 100000.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MELT_DREAM_VARIABILITY =
            ATTRIBUTES.register("melt_dream_variability", () -> new RangedAttribute(
                    "attribute.pasterdream.melt_dream_variability", 0.0, -120000.0, 120000.0).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
        eventBus.addListener(ModAttributes::addAttributes);
    }

    private static void addAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SKILL_COOLDOWN_REDUCTION);
        event.add(EntityType.PLAYER, SKILL_DAMAGE_RATE);
        event.add(EntityType.PLAYER, MAX_MELT_DREAM_ENERGY_EXTRA);
        event.add(EntityType.PLAYER, MELT_DREAM_VARIABILITY);
    }

    private ModAttributes() {
    }
}
