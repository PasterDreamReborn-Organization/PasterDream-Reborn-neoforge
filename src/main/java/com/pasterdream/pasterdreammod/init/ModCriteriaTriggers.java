package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.BreakWindCurtainTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.CaptureFireflyTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 自定义进度触发器注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModCriteriaTriggers.java}。
 * 1.21 的 {@code Registries.TRIGGER_TYPES} 在模组构造前已冻结，必须通过
 * {@link DeferredRegister} 在注册事件阶段写入。
 */
public final class ModCriteriaTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, PasterDreamMod.MOD_ID);

    /** 在指定高度以上食用星河果冻 */
    public static final DeferredHolder<CriterionTrigger<?>, EatGalaxyJellyAtHeightTrigger> EAT_GALAXY_JELLY_AT_HEIGHT =
            TRIGGERS.register("eat_galaxy_jelly_at_height", EatGalaxyJellyAtHeightTrigger::new);

    /** 穿过破风幕帐 */
    public static final DeferredHolder<CriterionTrigger<?>, BreakWindCurtainTrigger> BREAK_WIND_CURTAIN =
            TRIGGERS.register("break_wind_curtain", BreakWindCurtainTrigger::new);

    /** 用生态玻璃罐右键捕获一只萤火虫 */
    public static final DeferredHolder<CriterionTrigger<?>, CaptureFireflyTrigger> CAPTURE_FIREFLY =
            TRIGGERS.register("capture_firefly", CaptureFireflyTrigger::new);

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }

    private ModCriteriaTriggers() {
    }
}
