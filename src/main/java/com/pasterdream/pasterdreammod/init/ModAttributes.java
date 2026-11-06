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
    public static final DeferredHolder<Attribute, Attribute> MAGIC_DAMAGE_RATE =
            ATTRIBUTES.register("magic_damage_rate", () -> new RangedAttribute(
                    "attribute.pasterdream.magic_damage_rate", 1.0, 0.0, 100.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> ARROW_VELOCITY =
            ATTRIBUTES.register("arrow_velocity", () -> new RangedAttribute(
                    "attribute.pasterdream.arrow_velocity", 0.0, -1024.0, 1024.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> RANGED_CHARGE_TIME_REDUCTION =
            ATTRIBUTES.register("ranged_charge_time_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.ranged_charge_time_reduction", 0.0, -10.0, 1.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MAX_MELT_DREAM_ENERGY_EXTRA =
            ATTRIBUTES.register("max_melt_dream_energy_extra", () -> new RangedAttribute(
                    "attribute.pasterdream.max_melt_dream_energy_extra", 0.0, -100000.0, 100000.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MELT_DREAM_VARIABILITY =
            ATTRIBUTES.register("melt_dream_variability", () -> new RangedAttribute(
                    "attribute.pasterdream.melt_dream_variability", 0.0, -120000.0, 120000.0).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> BLINK_COOLDOWN_REDUCTION =
            ATTRIBUTES.register("blink_cooldown_reduction", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_cooldown_reduction", 0.0, -10.0, 1.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> BLINK_CONSUME =
            ATTRIBUTES.register("blink_consume", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_consume", 1.0, 0.0, 10.0).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> BLINK_RANGE =
            ATTRIBUTES.register("blink_range", () -> new RangedAttribute(
                    "attribute.pasterdream.blink_range", 1.0, 0.0, 10.0).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> SAN_VARIABILITY =
            ATTRIBUTES.register("san_variability", () -> new RangedAttribute(
                    "attribute.pasterdream.san_variability", 0.0, -120000.0, 120000.0).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MAX_SAN_EXTRA =
            ATTRIBUTES.register("max_san_extra", () -> new RangedAttribute(
                    "attribute.pasterdream.max_san_extra", 0.0, -100000.0, 100000.0).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
        eventBus.addListener(ModAttributes::addAttributes);
    }

    private static void addAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, BLINK_COOLDOWN_REDUCTION);
        event.add(EntityType.PLAYER, BLINK_CONSUME);
        event.add(EntityType.PLAYER, BLINK_RANGE);
        event.add(EntityType.PLAYER, SAN_VARIABILITY);
        event.add(EntityType.PLAYER, MAX_SAN_EXTRA);
        event.add(EntityType.PLAYER, SKILL_COOLDOWN_REDUCTION);
        event.add(EntityType.PLAYER, SKILL_DAMAGE_RATE);
        event.add(EntityType.PLAYER, MAGIC_DAMAGE_RATE);
        event.add(EntityType.PLAYER, ARROW_VELOCITY);
        event.add(EntityType.PLAYER, RANGED_CHARGE_TIME_REDUCTION);
        event.add(EntityType.PLAYER, MAX_MELT_DREAM_ENERGY_EXTRA);
        event.add(EntityType.PLAYER, MELT_DREAM_VARIABILITY);
    }

    private ModAttributes() {
    }
}
