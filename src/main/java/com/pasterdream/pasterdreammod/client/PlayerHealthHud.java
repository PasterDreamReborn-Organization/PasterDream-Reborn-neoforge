package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.awt.Color;
import java.util.Locale;

/**
 * 帕斯特之梦主题玩家血条：替换原版生命值行，绘制一条血条并叠加当前/最大生命值文本。
 * 数值按 {@link PasterDreamClientConfig#PASTER_HEALTH_HUD_DECIMAL_PLACES} 限定小数位数并去除末尾零，
 * 避免属性修饰符浮点误差导致的小数位过长（如 22.000002）。
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public class PlayerHealthHud
{
    private static final Minecraft MC = Minecraft.getInstance();
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/paster_widgets.png");

    private static double playerHealth = 0;
    private static long healthUpdateCounter = 0;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker)
    {
        if (!PasterDreamClientConfig.PASTER_HEALTH_HUD.get()) return;
        Player player = MC.player;
        if (player == null || MC.options.hideGui) return;
        if (MC.gameMode == null || !MC.gameMode.canHurtPlayer()) return;

        Font font = MC.font;
        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        int health = Mth.ceil(player.getHealth());
        float playerMaxHealth = player.getMaxHealth();
        float playerOtherHealth = player.getAbsorptionAmount();

        MC.getProfiler().push("pd_health");
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        {
            int updateCounter = MC.gui.getGuiTicks();
            boolean highlight = healthUpdateCounter > (long) updateCounter
                    && (healthUpdateCounter - (long) updateCounter) / 3L % 2L == 1L;

            if (player.invulnerableTime > 0 && health != playerHealth)
                healthUpdateCounter = updateCounter + (health < playerHealth ? 20 : 10);

            int flagColor = player.hasEffect(MobEffects.POISON) ? 1 : player.hasEffect(MobEffects.WITHER) ? 2 : 0;

            playerHealth = health;
            int startCount = highlight ? 84 : 0;
            int x = width / 2 - 91;
            int y = height - 40;

            /* Clamp to the 83px interior so current > max (e.g. 24/20) never overflows the frame. */
            float healthRatio = playerMaxHealth > 0.0F
                    ? Mth.clamp(health / playerMaxHealth, 0.0F, 1.0F) : 0.0F;

            /* Render Bar and Health */
            guiGraphics.blit(ICON, x, y, 0, 0, 85, 10);
            guiGraphics.blit(ICON, x + 1, y + 2, startCount + 1, 10 + 6 * flagColor,
                    (int) (83 * healthRatio), 6);

            /* Render String */
            String text = formatValue(health + playerOtherHealth) + "/" + formatValue(playerMaxHealth);
            guiGraphics.drawString(font, text, x + ((85 / 2) - (font.width(text) / 2)), y,
                    playerOtherHealth > 0.0F ? Color.YELLOW.getRGB() : Color.WHITE.getRGB());
        }
        MC.gui.leftHeight += 11;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        MC.getProfiler().pop();
    }

    /** 按配置的小数位数格式化，并去除末尾多余的零，避免浮点误差产生的过长小数。 */
    private static String formatValue(double value)
    {
        int decimals = PasterDreamClientConfig.PASTER_HEALTH_HUD_DECIMAL_PLACES.get();
        String text = String.format(Locale.ROOT, "%." + decimals + "f", value);
        if (text.indexOf('.') >= 0)
        {
            text = text.replaceAll("0+$", "");
            if (text.endsWith("."))
                text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    /**
     * 启用主题血条时隐藏原版生命值行（含伤害吸收黄心），避免两条血条重叠。
     * 按 overlay id 比较，避免依赖注册实例身份。
     */
    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Pre event)
    {
        if (!PasterDreamClientConfig.PASTER_HEALTH_HUD.get()) return;
        if (event.getName().equals(VanillaGuiLayers.PLAYER_HEALTH))
            event.setCanceled(true);
    }
}
