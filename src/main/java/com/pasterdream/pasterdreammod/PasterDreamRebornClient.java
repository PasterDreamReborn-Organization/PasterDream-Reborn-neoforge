package com.pasterdream.pasterdreammod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * 客户端入口。该类不会在专用服务端加载，可安全引用客户端类。
 *
 * <p>注册模组配置界面；客户端渲染/粒子/HUD 等注册集中在 {@code client.ClientModEvents} 等
 * {@code @EventBusSubscriber(value = Dist.CLIENT)} 类中。
 */
@Mod(value = PasterDreamMod.MOD_ID, dist = Dist.CLIENT)
public class PasterDreamRebornClient {
    public PasterDreamRebornClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
