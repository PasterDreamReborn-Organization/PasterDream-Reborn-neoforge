package com.pasterdream.pasterdreammod.helper.nonshadowcenteredstring;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix4f;

import java.util.List;

public class NonShadowCenteredString
{
    public static void drawCenteredStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, String string, int color)
    {
        int width = Minecraft.getInstance().font.width(string);
        int height = Minecraft.getInstance().font.lineHeight;
        guiGraphics.drawString(Minecraft.getInstance().font, string, x - width / 2, y - height / 2, color, false);
    }

    public static void drawLongitudinalScrollingStringWithOutShadow(GuiGraphics guiGraphics, int x, int y, int widthLimit, int heightLimit, int speed, List<String> strings, int color, long ms_timer)
    {
        int totalHeight = Minecraft.getInstance().font.lineHeight * strings.size();

        if(totalHeight > heightLimit)
        {
            double period = (double)(2 * totalHeight + heightLimit) / speed; //周期长度
            double timer0 = (ms_timer / 1000d) % period; //每周期中的时刻0
            double timer1 = (ms_timer / 1000d + period / 2) % period; //每周期中的时刻1
            int phase0 = (int)(speed * timer0); //相位0
            int phase1 = (int)(speed * timer1); //相位1

            Matrix4f matrix = guiGraphics.pose().last().pose();
            int offsetX = (int) matrix.m30();
            int offsetY = (int) matrix.m31();

            guiGraphics.enableScissor(x + offsetX, y + offsetY, x + offsetX + widthLimit, y + offsetY + heightLimit);
            for(int i = 0; i < strings.size(); i++)
            {
                guiGraphics.drawString(Minecraft.getInstance().font, strings.get(i), x, y + heightLimit + i * Minecraft.getInstance().font.lineHeight - phase0, color, false);
                guiGraphics.drawString(Minecraft.getInstance().font, strings.get(i), x, y + heightLimit + i * Minecraft.getInstance().font.lineHeight - phase1, color, false);
            }
            guiGraphics.disableScissor();
        }
            else
            {
                for(int i = 0; i < strings.size(); i++)
                {
                    guiGraphics.drawString(Minecraft.getInstance().font, strings.get(i), x, y + i * Minecraft.getInstance().font.lineHeight, color, false);
                }
            }
    }
}
