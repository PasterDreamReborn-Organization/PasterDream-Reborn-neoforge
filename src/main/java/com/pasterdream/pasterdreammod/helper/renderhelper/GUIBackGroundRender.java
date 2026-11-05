package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GUIBackGroundRender
{
    public static ResourceLocation SAN_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_bar.png");
    public static ResourceLocation SAN_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_amount_bar.png");
    public static ResourceLocation SAN_CHANGE_UP = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_change_up.png");
    public static ResourceLocation SAN_CHANGE_DOWN = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/san/san_change_down.png");

    public static void rendSanBar(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(SAN_BAR, x, y, 0, 0, 28, 26, 28, 26);
    }

    public static void rendSanAmountBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(SAN_AMOUNT_BAR, x, y + (int)(26 * (1 - percent)), 0, (int)(26 * (1 - percent)), 28, (int)(26 * percent), 28, 26);
    }

    /** SAN 变化箭头（↑ 上升 / ↓ 下降）。small=true 时缩小为 7×8，用于变化幅度较小时。 */
    public static void rendSanChangeArrow(GuiGraphics guiGraphics, int x, int y, boolean up, boolean small)
    {
        int w = small ? 7 : 14;
        int h = small ? 8 : 16;
        guiGraphics.blit(up ? SAN_CHANGE_UP : SAN_CHANGE_DOWN, x, y, w, h, 0, 0, 14, 16, 14, 16);
    }

}
