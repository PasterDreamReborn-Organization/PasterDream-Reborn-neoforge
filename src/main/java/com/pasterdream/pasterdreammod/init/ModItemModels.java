package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

/**
 * 自定义物品模型注册中心（客户端）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModItemModels.java}。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ModItemModels {
    private ModItemModels() {
    }

    @SubscribeEvent
    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        // TODO: 替换指定物品的 baked model
    }

    @SubscribeEvent
    public static void bakingCompleted(ModelEvent.BakingCompleted event) {
        // TODO: Curios 渲染器注册等
    }
}
