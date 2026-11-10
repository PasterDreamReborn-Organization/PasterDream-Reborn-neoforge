package com.pasterdream.pasterdreammod.init;

import net.minecraft.world.entity.MobCategory;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

/**
 * 自定义生物生成类别。
 *
 * <p>NeoForge 1.21 的 {@link MobCategory} 是可扩展枚举，不能再调用 Forge 的
 * {@code MobCategory.create}。这里通过 {@code META-INF/enumextensions.json} 注册常量，
 * 并用 {@link EnumProxy} 延迟取得枚举值。
 */
public final class ModMobCategories {

    /** 怨魂系实体独立生成类别，上限 30 只，避免占满原版怪物配额 (70) */
    public static final EnumProxy<MobCategory> SHADOW_GHOST_PROXY = new EnumProxy<>(MobCategory.class,
            "pasterdream:shadow_ghost", 30, false, false, 128);

    public static MobCategory shadowGhost() {
        return SHADOW_GHOST_PROXY.getValue();
    }

    private ModMobCategories() {
    }
}
