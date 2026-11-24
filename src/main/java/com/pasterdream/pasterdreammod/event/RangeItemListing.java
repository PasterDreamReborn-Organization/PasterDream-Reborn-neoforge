package com.pasterdream.pasterdreammod.event;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

/**
 * 价格与可成交次数为随机区间的交易条目。
 * <p>
 * 原版与 NeoForge 的 {@link net.neoforged.neoforge.common.BasicItemListing} 只能表达固定价格与固定次数，
 * 而游商交易表需要「2~4 绿宝石 / 16~32 次」这类区间随机值，故单独实现。
 * 价格与 {@code maxUses} 在每次 {@link #getOffer} 时用 {@link RandomSource} 重新取值（含两端）。
 * <p>
 * 1.21 起 {@link MerchantOffer} 的价格改用 {@link ItemCost}，因此这里按「物品 + 数量」构造，
 * 不再从模板 {@link ItemStack} 复制。
 */
public class RangeItemListing implements VillagerTrades.ItemListing {

    private final Item priceItem;
    private final ItemStack forSale;
    private final int priceMin;
    private final int priceMax;
    private final int usesMin;
    private final int usesMax;
    private final int xp;
    private final float priceMultiplier;

    public RangeItemListing(Item priceItem, ItemStack forSale, int priceMin, int priceMax,
                            int usesMin, int usesMax, int xp, float priceMultiplier) {
        this.priceItem = priceItem;
        this.forSale = forSale;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.usesMin = usesMin;
        this.usesMax = usesMax;
        this.xp = xp;
        this.priceMultiplier = priceMultiplier;
    }

    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource random) {
        // 每次生成都按区间重新取价格与成交次数，避免多个 offer 共享同一实例
        ItemCost cost = new ItemCost(this.priceItem, Mth.nextInt(random, this.priceMin, this.priceMax));

        int maxUses = Mth.nextInt(random, this.usesMin, this.usesMax);
        return new MerchantOffer(cost, this.forSale.copy(), maxUses, this.xp, this.priceMultiplier);
    }
}
