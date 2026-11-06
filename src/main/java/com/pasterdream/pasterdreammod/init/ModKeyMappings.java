package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.skill.BlinkPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

/**
 * 按键绑定注册中心（客户端）。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModKeyMappings.java}。
 * 按键 → 网络包 → 技能核心 → 特效 链路见 {@code .claude/skills/skill-system/SKILL.md}。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public final class ModKeyMappings {
    public static final KeyMapping BLINK = new KeyMapping(
            "key.pasterdream.blink", GLFW.GLFW_KEY_C, "key.categories.pasterdream");

    private ModKeyMappings() {
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(BLINK);
    }

    @EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
    public static class KeyEventListener {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            if (Minecraft.getInstance().screen == null && Minecraft.getInstance().player != null) {
                while (ModKeyMappings.BLINK.consumeClick()) {
                    ModNetwork.sendToServer(new BlinkPacket());
                }
            }
        }
    }
}
