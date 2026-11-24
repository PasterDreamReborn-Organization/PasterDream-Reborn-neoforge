package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.init.ModEnchantment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 融梦修补附魔书的村民交易价格修正。
 * <p>
 * 原版 {@code VillagerTrades$EnchantBookForEmeralds} 的价格公式仅由附魔等级与宝藏标记决定，
 * 完全不读 {@link Enchantment#getMinCost(int)} / {@link Enchantment#getMaxCost(int)}，
 * 单级融梦修补只能刷出 [10, 38] 绿宝石。此处仅在 RETURN 处替换该附魔书的 offer 价格，
 * 将基础价改为 [34, 64]，其余附魔书保持原版公式不变。
 * <p>
 * 在 {@code @Inject(RETURN)} 处保留原交易的第二项成本、产物与交易参数，
 * 以兼容其它模组对同一方法的注入。
 */
@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class EnchantBookForEmeraldsMixin {

    @Inject(method = "getOffer", at = @At("RETURN"), cancellable = true)
    private void pasterdream$setMeltDreamRepairBookPrice(Entity entity, RandomSource random,
                                                         CallbackInfoReturnable<MerchantOffer> cir) {
        MerchantOffer offer = cir.getReturnValue();
        if (offer == null) return;

        var enchantments = EnchantmentHelper.getEnchantmentsForCrafting(offer.getResult());
        if (enchantments.keySet().stream().noneMatch(holder -> holder.is(ModEnchantment.MELT_DREAM_REPAIR_ENCHANTMENT))) return;

        ItemCost emerald = new ItemCost(Items.EMERALD, 34 + random.nextInt(31));
        cir.setReturnValue(new MerchantOffer(emerald, offer.getItemCostB(), offer.getResult(),
                offer.getMaxUses(), offer.getXp(), offer.getPriceMultiplier()));
    }
}
