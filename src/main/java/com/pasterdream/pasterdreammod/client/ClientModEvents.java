package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.model.KusanagiVenomStingerModel;
import com.pasterdream.pasterdreammod.init.ModBlockEntityRenderer;
import com.pasterdream.pasterdreammod.init.ModEntityRenderer;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.curio.RedDewRingItem;
import com.pasterdream.pasterdreammod.world.item.curio.StrikeRingItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.resources.ResourceLocation;
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

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.FLUFFY_WIND_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"),
                    (stack, level, entity, seed) -> entity != null && entity.isUsingItem()
                            && entity.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FLUFFY_WIND_BOW.get(), ResourceLocation.withDefaultNamespace("pull"),
                    (stack, level, entity, seed) -> entity == null || entity.getUseItem() != stack ? 0.0F
                            : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F);
            ItemProperties.register(ModItems.RED_DEW_RING.get(),
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lv"),
                    (stack, level, entity, seed) -> RedDewRingItem.getPredicateValue(RedDewRingItem.getLv(stack)));
            ItemProperties.register(ModItems.STRIKE_RING.get(),
                    ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lv"),
                    (stack, level, entity, seed) -> StrikeRingItem.getPredicateValue(StrikeRingItem.getLv(stack)));
        });
    }

    /** 实体渲染器 + 方块实体渲染器。 */
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModEntityRenderer.registerRenderers(event);
        ModBlockEntityRenderer.registerRenderers(event);
    }

    /** 实体模型层定义。 */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(KusanagiVenomStingerModel.LAYER_LOCATION, KusanagiVenomStingerModel::createBodyLayer);
    }

    /** HUD 层（融梦能量 / SAN / 失智 / BOSS 血条 等）。 */
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "melt_dream_energy"), MeltDreamEnergyTank::render);
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "san"), SanTank::render);
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "lose_mind"), LoseMind::render);
    }
}
