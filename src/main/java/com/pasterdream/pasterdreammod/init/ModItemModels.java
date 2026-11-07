package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.renderhelper.CustomRendererBakedModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
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
    private static BakedModel elixirBottleModel;
    private ModItemModels() {
    }

    @SubscribeEvent
    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ModelResourceLocation modelLocation = new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "elixir_bottle"), "inventory");
        BakedModel original = event.getModels().get(modelLocation);
        if (original != null && !(original instanceof CustomRendererBakedModel))
        {
            elixirBottleModel = original;
            event.getModels().put(modelLocation, new CustomRendererBakedModel(original));
        }
    }

    @SubscribeEvent
    public static void bakingCompleted(ModelEvent.BakingCompleted event) {
        // TODO: Curios 渲染器注册等
    }

    public static BakedModel getElixirBottleModel()
    {
        return elixirBottleModel;
    }
}
