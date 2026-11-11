package com.pasterdream.pasterdreammod.world.block.twilightlantern;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.shadowselect.ShadowSelectEndButtonPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShadowSelectEndScreen extends AbstractContainerScreen<ShadowSelectEndMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/screens/shadow_select_end_gui.png");

    public ShadowSelectEndScreen(ShadowSelectEndMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 320;
        this.imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();
        // 黑暗按钮（选影）
        this.addRenderableWidget(new ShadowSelectButton(this.leftPos + 48, this.topPos + 54, 82, 87,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/screens/atlas/imagebutton_hei_an_an_niu.png"),
                e -> ModNetwork.sendToServer(new ShadowSelectEndButtonPacket(0))));
        // 光明按钮（选灯）
        this.addRenderableWidget(new ShadowSelectButton(this.leftPos + 183, this.topPos + 52, 82, 87,
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/screens/atlas/imagebutton_guang_ming_an_niu.png"),
                e -> ModNetwork.sendToServer(new ShadowSelectEndButtonPacket(1))));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(GUI_TEXTURE, this.leftPos, this.topPos + 3, 0, 0, 320, 200, 320, 200);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.pasterdream.shadow_select_end.label_choose"), 98, 9, -3355444, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.pasterdream.shadow_select_end.label_outcome"), 95, 19, -3355444, false);
    }

    /** 1.21 的 ImageButton 改为 WidgetSprites，这里按来源图集手动绘制上下两帧。 */
    private static class ShadowSelectButton extends Button {

        private final ResourceLocation texture;

        private ShadowSelectButton(int x, int y, int width, int height, ResourceLocation texture, OnPress onPress) {
            super(x, y, width, height, Component.empty(), onPress, Button.DEFAULT_NARRATION);
            this.texture = texture;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            int v = this.isHoveredOrFocused() ? this.height : 0;
            guiGraphics.blit(this.texture, this.getX(), this.getY(), 0, v, this.width, this.height, this.width, this.height * 2);
        }
    }
}
