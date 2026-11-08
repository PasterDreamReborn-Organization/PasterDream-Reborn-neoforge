package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.network.skill.BlinkPacket;
import com.pasterdream.pasterdreammod.network.curio.CloakActivationPacket;
import com.pasterdream.pasterdreammod.network.curio.KaichuOmamoriActivationPacket;
import com.pasterdream.pasterdreammod.network.fluffywindalloy.WindHoverInputPacket;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorHandler;
import com.pasterdream.pasterdreammod.world.item.armoritem.FluffyWindAlloyArmorItem;
import com.pasterdream.pasterdreammod.world.item.armoritem.WindRunnerCloakHandler;
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
    public static final KeyMapping KAICHU_OMAMORI = new KeyMapping(
            "key.pasterdream.kaichu_omamori", GLFW.GLFW_KEY_V, "key.categories.pasterdream");
    public static final KeyMapping CLOAK_ACTIVATE = new KeyMapping(
            "key.pasterdream.cloak_activate", GLFW.GLFW_KEY_Z, "key.categories.pasterdream");

    private ModKeyMappings() {
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(BLINK);
        event.register(KAICHU_OMAMORI);
        event.register(CLOAK_ACTIVATE);
    }

    @EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
    public static class KeyEventListener {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            if (Minecraft.getInstance().screen == null && Minecraft.getInstance().player != null) {
                while (ModKeyMappings.BLINK.consumeClick()) {
                    ModNetwork.sendToServer(new BlinkPacket());
                }
                while (ModKeyMappings.KAICHU_OMAMORI.consumeClick()) {
                    ModNetwork.sendToServer(new KaichuOmamoriActivationPacket());
                }
                while (ModKeyMappings.CLOAK_ACTIVATE.consumeClick()) {
                    ModNetwork.sendToServer(new CloakActivationPacket());
                }
            }
            var player = Minecraft.getInstance().player;
            if (player == null) return;
            boolean relevant = FluffyWindAlloyArmorItem.hasFullSet(player) || WindRunnerCloakHandler.isWearing(player);
            boolean jumping = relevant && Minecraft.getInstance().options.keyJump.isDown();
            boolean previous = player.getPersistentData().getBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP);
            if (jumping != previous || (jumping && player.tickCount % 5 == 0)) {
                player.getPersistentData().putBoolean(FluffyWindAlloyArmorHandler.NBT_HOVER_JUMP, jumping);
                ModNetwork.sendToServer(new WindHoverInputPacket(jumping));
            }
            if (FluffyWindAlloyArmorItem.hasFullSet(player)) {
                FluffyWindAlloyArmorHandler.applySlowFall(player, false);
            }
        }
    }
}
