package com.pasterdream.pasterdreammod.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 帕斯特之梦品质等级。
 *
 * <p>NeoForge 1.21 的 {@link Rarity} 是网络化可扩展枚举，不能再用 Forge 的
 * {@code Rarity.create}。这里通过 {@code META-INF/enumextensions.json} 注册常量，
 * 并用 {@link EnumProxy} 延迟取得枚举值，避免 Rarity 初始化期间反向触发本类静态常量。
 */
public final class ModRarities {

    public static final EnumProxy<Rarity> COMMON_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:common", ChatFormatting.WHITE);
    public static final EnumProxy<Rarity> EXCELLENT_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:excellent", ChatFormatting.GREEN);
    public static final EnumProxy<Rarity> SUPERIOR_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:superior", ChatFormatting.AQUA);
    public static final EnumProxy<Rarity> MASTER_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:master", ChatFormatting.BLUE);
    public static final EnumProxy<Rarity> ANCIENT_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:ancient", ChatFormatting.DARK_PURPLE);
    public static final EnumProxy<Rarity> EPIC_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:epic", ChatFormatting.LIGHT_PURPLE);
    public static final EnumProxy<Rarity> LEGENDARY_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:legendary", ChatFormatting.RED);
    public static final EnumProxy<Rarity> MIRACLE_PROXY = new EnumProxy<>(Rarity.class,
            -1, "pasterdream:miracle", ChatFormatting.GOLD);

    private static final Map<String, EnumProxy<Rarity>> BY_NAME = Map.ofEntries(
            Map.entry("common", COMMON_PROXY),
            Map.entry("excellent", EXCELLENT_PROXY),
            Map.entry("superior", SUPERIOR_PROXY),
            Map.entry("master", MASTER_PROXY),
            Map.entry("ancient", ANCIENT_PROXY),
            Map.entry("epic", EPIC_PROXY),
            Map.entry("legendary", LEGENDARY_PROXY),
            Map.entry("miracle", MIRACLE_PROXY)
    );

    private static final Map<String, Integer> TIER_BY_NAME = Map.ofEntries(
            Map.entry("pasterdream:common", 1),
            Map.entry("pasterdream:excellent", 2),
            Map.entry("pasterdream:superior", 3),
            Map.entry("pasterdream:master", 4),
            Map.entry("pasterdream:ancient", 5),
            Map.entry("pasterdream:epic", 6),
            Map.entry("pasterdream:legendary", 7),
            Map.entry("pasterdream:miracle", 8)
    );

    public static Rarity common() {
        return COMMON_PROXY.getValue();
    }

    public static Rarity excellent() {
        return EXCELLENT_PROXY.getValue();
    }

    public static Rarity superior() {
        return SUPERIOR_PROXY.getValue();
    }

    public static Rarity master() {
        return MASTER_PROXY.getValue();
    }

    public static Rarity ancient() {
        return ANCIENT_PROXY.getValue();
    }

    public static Rarity epic() {
        return EPIC_PROXY.getValue();
    }

    public static Rarity legendary() {
        return LEGENDARY_PROXY.getValue();
    }

    public static Rarity miracle() {
        return MIRACLE_PROXY.getValue();
    }

    public static Component qualityTooltip(Rarity rarity) {
        Integer tier = tierOf(rarity);
        if (tier == null) {
            return Component.empty();
        }
        MutableComponent stars = Component.literal("★".repeat(tier));
        return Component.translatable("tooltip.pasterdream.quality",
                Component.translatable(rarityTranslationKey(rarity)),
                stars
        ).withStyle(rarity.getStyleModifier());
    }

    public static Rarity byName(String name) {
        if (name == null) {
            return null;
        }
        String key = name.toLowerCase(Locale.ROOT)
                .replace("pasterdream_", "")
                .replace("pasterdream:", "");
        EnumProxy<Rarity> proxy = BY_NAME.get(key);
        return proxy == null ? null : proxy.getValue();
    }

    public static Integer tierOf(Rarity rarity) {
        return TIER_BY_NAME.get(rarity.getSerializedName());
    }

    public static List<String> names() {
        return TIER_BY_NAME.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().replace("pasterdream:", ""))
                .toList();
    }

    private static String rarityTranslationKey(Rarity rarity) {
        return "rarity.pasterdream." + rarity.getSerializedName().replace("pasterdream:", "");
    }

    private ModRarities() {
    }
}
