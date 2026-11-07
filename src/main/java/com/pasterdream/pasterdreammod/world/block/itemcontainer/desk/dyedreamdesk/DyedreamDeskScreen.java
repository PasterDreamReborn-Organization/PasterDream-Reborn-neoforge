package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DyedreamDeskScreen extends AbstractContainerScreen<DyedreamDeskMenu>
{
    public DyedreamDeskScreen(DyedreamDeskMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
        imageWidth = 170;
        imageHeight = 145;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        GUIBackGroundRender.rendDyedreamDeskGUI(graphics, leftPos + 71, topPos);
        GUIBackGroundRender.rendPasterDreamInventoryGUI(graphics, leftPos, topPos + 60);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY)
    {
    }
}
