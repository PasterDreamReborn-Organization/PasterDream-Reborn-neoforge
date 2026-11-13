package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import com.pasterdream.pasterdreammod.config.PasterDreamClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.Random;
import java.util.Set;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public class LoadingTipRenderer {

    private static String currentTip = "";
    private static final Random RANDOM = new Random();

    private static final Set<Class<? extends Screen>> LOADING_SCREENS = Set.of(
            ConnectScreen.class,
            LevelLoadingScreen.class,
            ProgressScreen.class
    );

    @SubscribeEvent
    public static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        if (!PasterDreamClientConfig.LOADING_GUI_TIPS.get()) {
            currentTip = "";
            return;
        }
        Screen screen = event.getScreen();
        if (!LOADING_SCREENS.contains(screen.getClass())) {
            currentTip = "";
            return;
        }

        if (currentTip.isEmpty()) {
            currentTip = PasterDreamTipsManager.INSTANCE.getRandomTip(RANDOM);
        }

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = Minecraft.getInstance().font;
        int height = screen.height - 20;

        graphics.drawString(font, currentTip, 10, height, 0xFFFFFFFF);
        graphics.drawString(font, "PasterDream Tip:", 10, height - 10, 0xFFFFFF00);
    }
}
