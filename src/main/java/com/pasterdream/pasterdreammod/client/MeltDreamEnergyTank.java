package com.pasterdream.pasterdreammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import com.pasterdream.pasterdreammod.init.ModAttachments;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class MeltDreamEnergyTank {
    private static final int BAR_WIDTH = 72;
    private static final int BAR_HEIGHT = 12;
    private static final int CHANGE_DISPLAY_TICKS = 120;
    private static final ResourceLocation BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/melt_dream_energy/melt_dream_energy_bar.png");
    private static final ResourceLocation AMOUNT = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/melt_dream_energy/melt_dream_energy_amount_bar.png");

    private static double lastValue = -1;
    private static int changeTimer = 0;

    public static void render(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        var client = Minecraft.getInstance();
        var player = client.player;
        if (player == null || client.options.hideGui) return;

        var energy = player.getData(ModAttachments.MELT_DREAM_ENERGY);
        double value = energy.getMeltDreamEnergy();
        double max = energy.getMaxMeltDreamEnergy();
        var attr = player.getAttribute(ModAttributes.MAX_MELT_DREAM_ENERGY_EXTRA);
        if (attr != null) max += attr.getValue();
        if (max <= 0) return;

        if (Math.abs(value - lastValue) > 0.001) {
            lastValue = value;
            changeTimer = CHANGE_DISPLAY_TICKS;
        } else if (changeTimer > 0) {
            changeTimer--;
        }

        boolean visible = PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_ALWAYS_SHOW.get()
                || (PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_SHOW_ON_CHANGE.get() && changeTimer > 0)
                || (PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_SHOW_ON_SNEAK.get() && player.isShiftKeyDown());
        if (!visible) return;

        int barX = PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_X.get();
        int barY = guiGraphics.guiHeight() - PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_Y_FROM_BOTTOM.get();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        try {
            guiGraphics.blit(BAR, barX, barY, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
            int filled = (int) (66 * Math.clamp(value / max, 0, 1));
            if (filled > 0) guiGraphics.blit(AMOUNT, barX + 3, barY + 4, 0, 0, filled, 4, 66, 4);

            String format = "%1$." + PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_DECIMAL_PLACES.get() + "f/%2$.0f";
            String text = String.format(Locale.ROOT, format, value, max);
            int textY = PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_TEXT_CENTERED.get()
                    ? barY + (BAR_HEIGHT - client.font.lineHeight) / 2 : barY - 8;
            if (client.font.width(text) <= 90) {
                guiGraphics.drawCenteredString(client.font, text, barX + BAR_WIDTH / 2, textY, 0xFFFFFFFF);
            } else {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(0.5f, 0.5f, 1f);
                int scaledTextY = PasterDreamClientConfig.MELT_DREAM_ENERGY_BAR_TEXT_CENTERED.get()
                        ? (barY + 2) * 2 : (barY - 4) * 2;
                guiGraphics.drawCenteredString(client.font, text, (barX + BAR_WIDTH / 2) * 2, scaledTextY, 0xFFFFFFFF);
                guiGraphics.pose().popPose();
            }
        } finally {
            RenderSystem.disableBlend();
        }
    }
}
