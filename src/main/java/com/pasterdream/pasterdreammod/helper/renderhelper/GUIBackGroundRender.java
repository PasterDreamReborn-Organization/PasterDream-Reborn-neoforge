package com.pasterdream.pasterdreammod.helper.renderhelper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GUIBackGroundRender
{
    public static ResourceLocation PASTERDREAM_INVENTORY_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/pasterdream_inventory.png");
    public static ResourceLocation STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/storage_bag.png");
    public static ResourceLocation BIG_STORAGE_BAG_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/big_storage_bag.png");
    public static ResourceLocation CLAYPAN_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/claypan.png");
    public static ResourceLocation ARROW = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/claypan/arrow.png");
    public static ResourceLocation MORTAR_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/mortar/mortar.png");
    public static ResourceLocation DREAM_CAULDRON_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron.png");
    public static ResourceLocation DREAM_CAULDRON_BUTTON_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_button.png");
    public static ResourceLocation DREAM_CAULDRON_BUTTON_HOVERED_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_button_hovered.png");
    public static ResourceLocation DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/dream_cauldron/dream_cauldron_melt_dream_liquid_amount_bar.png");
    public static ResourceLocation EMPTY_FLUID_SLOT_BUCKET_SHAPE = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/empty_fluid_slot_bucket_shape.png");
    public static ResourceLocation WEAPON_WORKSHOP_CRAFTING_TABLE_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/weapon_workshop/weapon_workshop_crafting_table.png");
    public static ResourceLocation DYEDREAM_DESK_GUI = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/gui/desk/dyedream_desk.png");
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

    public static void rendPasterDreamInventoryGUI(GuiGraphics guiGraphics, int x, int y)
    {
        // 物品栏及其后的槽位贴图共用透明混合状态
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.blit(PASTERDREAM_INVENTORY_GUI, x, y, 0, 0, 170, 84, 170, 84);
    }

    public static void rendStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(STORAGE_BAG_GUI, x, y, 0, 0, 86, 73, 86, 73);
    }

    public static void rendBigStorageBagGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(BIG_STORAGE_BAG_GUI, x, y, 0, 0, 128, 110, 128, 110);
    }

    public static void rendClaypanGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(CLAYPAN_GUI, x, y, 0, 0, 82, 28, 82, 28);
    }

    public static void rendArrow(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(ARROW, x, y, 0, 0, (int)(22 * percent), 15, 22, 15);
    }

    public static void rendMortarGUI(GuiGraphics guiGraphics, int x, int y)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.blit(MORTAR_GUI, x, y, 0, 0, 128, 90, 128, 90);
    }

    public static void rendDreamCauldronGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_GUI, x, y, 0, 0, 171, 93, 171, 93);
    }

    public static void rendDreamCauldronMeltDreamLiquidBar(GuiGraphics guiGraphics, int x, int y, double percent)
    {
        guiGraphics.blit(DREAM_CAULDRON_MELT_DREAM_LIQUID_AMOUNT_BAR, x, y + (int)(0.5 + (47 * (1 - percent))), 0, (int)(0.5 + (47 * (1 - percent))), 5, (int)(0.49999999 + (47 * percent)), 5, 47);
    }

    public static void rendDreamCauldronButtonGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_BUTTON_GUI, x, y, 0, 0, 38, 15, 38, 15);
    }

    public static void rendDreamCauldronButtonHoveredGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(DREAM_CAULDRON_BUTTON_HOVERED_GUI, x, y, 0, 0, 38, 15, 38, 15);
    }

    public static void rendEmptyFluidSlotBucketShape(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(EMPTY_FLUID_SLOT_BUCKET_SHAPE, x, y, 0, 0, 16, 16, 16, 16);
    }

    public static void rendWeaponWorkshopCraftingTableGUI(GuiGraphics guiGraphics, int x, int y)
    {
        guiGraphics.blit(WEAPON_WORKSHOP_CRAFTING_TABLE_GUI, x, y, 0, 0, 174, 86, 174, 86);
    }

    public static void rendDyedreamDeskGUI(GuiGraphics guiGraphics, int x, int y)
    {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.blit(DYEDREAM_DESK_GUI, x, y, 0, 0, 28, 51, 28, 51);
    }

}
