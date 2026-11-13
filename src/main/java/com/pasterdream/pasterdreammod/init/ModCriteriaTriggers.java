package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.advancement.critereon.BreakWindCurtainTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.EatGalaxyJellyAtHeightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.CaptureFireflyTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundDesertFortressTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.FoundTombTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.HasAdvancementTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.LookAtPinkSheepTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.MarkWailingShadowGhostTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.NewStandardSwordDrawingTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.OpenDreamAccumulatorTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.OpenWindJourneyCrystalChestTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.ReadDreamNoteTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.SummonWindKnightTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.UseBoneNeedleTrigger;
import com.pasterdream.pasterdreammod.advancement.critereon.WindFlightTrigger;
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

    /** 在染梦维度准星指向粉色羊 */
    public static final DeferredHolder<CriterionTrigger<?>, LookAtPinkSheepTrigger> LOOK_AT_PINK_SHEEP =
            TRIGGERS.register("look_at_pink_sheep", LookAtPinkSheepTrigger::new);

    /** 阅读寻梦者笔记（书） */
    public static final DeferredHolder<CriterionTrigger<?>, ReadDreamNoteTrigger> READ_DREAM_NOTE =
            TRIGGERS.register("read_dream_note", ReadDreamNoteTrigger::new);

    /** 在梦境维度使用苍白骨针 */
    public static final DeferredHolder<CriterionTrigger<?>, UseBoneNeedleTrigger> USE_BONE_NEEDLE =
            TRIGGERS.register("use_boneneedle", UseBoneNeedleTrigger::new);

    /** 发现失落剑冢结构 */
    public static final DeferredHolder<CriterionTrigger<?>, FoundTombTrigger> FOUND_TOMB =
            TRIGGERS.register("found_tomb", FoundTombTrigger::new);

    /** 发现沙漠遗迹结构 */
    public static final DeferredHolder<CriterionTrigger<?>, FoundDesertFortressTrigger> FOUND_DESERT_FORTRESS =
            TRIGGERS.register("found_desert_fortress", FoundDesertFortressTrigger::new);

    /** 新概念拔剑（剑冢击碎） */
    public static final DeferredHolder<CriterionTrigger<?>, NewStandardSwordDrawingTrigger> NEW_STANDARD_SWORD_DRAWING =
            TRIGGERS.register("new_standard_sword_drawing", NewStandardSwordDrawingTrigger::new);

    /** 持有指定前置进度 */
    public static final DeferredHolder<CriterionTrigger<?>, HasAdvancementTrigger> HAS_ADVANCEMENT =
            TRIGGERS.register("has_advancement", HasAdvancementTrigger::new);

    /** 首次打开蓄梦池 */
    public static final DeferredHolder<CriterionTrigger<?>, OpenDreamAccumulatorTrigger> OPEN_DREAM_ACCUMULATOR =
            TRIGGERS.register("open_dream_accumulator", OpenDreamAccumulatorTrigger::new);

    /** 用纷争预言卡标记悲泣尖啸怨魂 */
    public static final DeferredHolder<CriterionTrigger<?>, MarkWailingShadowGhostTrigger> MARK_WAILING_SHADOW_GHOST =
            TRIGGERS.register("mark_wailing_shadow_ghost", MarkWailingShadowGhostTrigger::new);

    /** 在风之旅途世界打开融梦水晶箱 */
    public static final DeferredHolder<CriterionTrigger<?>, OpenWindJourneyCrystalChestTrigger> OPEN_WIND_JOURNEY_CRYSTAL_CHEST =
            TRIGGERS.register("open_wind_journey_crystal_chest", OpenWindJourneyCrystalChestTrigger::new);

    /** 风之旅途顺风/逆风累计飞行距离 */
    public static final DeferredHolder<CriterionTrigger<?>, WindFlightTrigger> WIND_FLIGHT =
            TRIGGERS.register("wind_flight", WindFlightTrigger::new);

    /** 破风骑士祭坛召唤破风骑士 */
    public static final DeferredHolder<CriterionTrigger<?>, SummonWindKnightTrigger> SUMMON_WIND_KNIGHT =
            TRIGGERS.register("summon_wind_knight", SummonWindKnightTrigger::new);

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }

    private ModCriteriaTriggers() {
    }
}
