package com.pasterdream.pasterdreammod.client.renderer;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 强化拖尾箭的箭体贴图映射。
 * <p>
 * 武器强化会把弹药替换成通用拖尾箭实体，箭体模型仍沿用原版 {@code ArrowRenderer}。
 * 这里按同步的弹药物品返回自定义箭矢贴图，使强化射击保留弹药外观。
 */
public final class ArrowProjectileTextures {

    private static final ResourceLocation BLACKSTONE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/blackstone_arrow.png");
    private static final ResourceLocation TOXIC_SPORE =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/toxic_spore_arrow.png");
    private static final ResourceLocation FRAGDREAM =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/fragdream_arrow.png");
    private static final ResourceLocation MAGICAL =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/magical_arrow.png");
    private static final ResourceLocation CHARGED =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/charged_arrow.png");
    private static final ResourceLocation CHAOTIC =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/chaotic_arrow.png");
    private static final ResourceLocation BLAST =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/blast_arrow.png");
    private static final ResourceLocation CONGEAL_WIND =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/congeal_wind_arrow.png");
    private static final ResourceLocation DEW =
            ResourceLocation.fromNamespaceAndPath("pasterdream", "textures/entities/dew_arrow.png");

    private ArrowProjectileTextures() {
    }

    /** 自定义箭矢返回对应箭体贴图；普通箭 / 药水箭 / 空弹药返回 {@code null}，由渲染器回退原版贴图。 */
    public static ResourceLocation get(ItemStack ammo) {
        if (ammo.isEmpty()) {
            return null;
        }
        Item item = ammo.getItem();
        if (item == ModItems.BLACKSTONE_ARROW.get()) {
            return BLACKSTONE;
        }
        if (item == ModItems.TOXIC_SPORE_ARROW.get()) {
            return TOXIC_SPORE;
        }
        if (item == ModItems.FRAGDREAM_ARROW.get()) {
            return FRAGDREAM;
        }
        if (item == ModItems.MAGICAL_ARROW.get()) {
            return MAGICAL;
        }
        if (item == ModItems.CHARGED_ARROW.get()) {
            return CHARGED;
        }
        if (item == ModItems.CHAOTIC_ARROW.get()) {
            return CHAOTIC;
        }
        if (item == ModItems.BLAST_ARROW.get()) {
            return BLAST;
        }
        if (item == ModItems.CONGEAL_WIND_ARROW.get()) {
            return CONGEAL_WIND;
        }
        if (item == ModItems.DEW_ARROW.get()) {
            return DEW;
        }
        return null;
    }
}
