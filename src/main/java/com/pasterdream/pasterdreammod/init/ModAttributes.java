package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 自定义属性注册中心。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModAttributes.java}。
 * 用于技能参数（瞬身术 cd/consume/range、战技冷却/伤害、魔法伤害、SAN/能量波动等）。
 */
public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, PasterDreamMod.MOD_ID);

    // TODO: 在此注册自定义属性

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    private ModAttributes() {
    }
}
