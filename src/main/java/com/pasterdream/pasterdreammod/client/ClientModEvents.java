package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

/**
 * 客户端注册入口（MOD 总线）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code client/ClientModEvents.java}。
 * 持有客户端类引用的监听器必须放在这里，避免专用服务器加载主类时触发客户端类加载。
 *
 * <p>1.21.1 的 GUI 层注册事件为 {@link RegisterGuiLayersEvent}（旧名 {@code RegisterGuiOverlaysEvent}）。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {
    }

    /** 实体渲染器 + 方块实体渲染器。 */
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // TODO: ModEntityRenderer.registerRenderers(event);
        // TODO: ModBlockEntityRenderer.registerRenderers(event);
    }

    /** 实体模型层定义。 */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // TODO: ModEntityRenderer.registerLayerDefinitions(event);
    }

    /** HUD 层（融梦能量 / SAN / 失智 / BOSS 血条 等）。 */
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        // TODO: event.registerAboveAll(id("melt_dream_energy"), MeltDreamEnergyTank.LAYER);
        // TODO: event.registerAboveAll(id("san"), SanTank.LAYER);
    }
}
