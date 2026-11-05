package com.pasterdream.pasterdreammod.helper.nbthelper;

import com.pasterdream.pasterdreammod.helper.pasterdreamingredient.ItemIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 汇总工作台输入物的来源自定义数据与附魔。
 *
 * <p>1.20.1 来源把 {@code Enchantments} 作为物品 NBT 列表处理；1.21 附魔改存
 * {@link DataComponents#ENCHANTMENTS}，因此按原“2^等级求和后取 bitLength - 1”的
 * 合并规则改为操作 {@link ItemEnchantments}。攻击/幸运加成仍保留为自定义数据键，
 * 并在处理阶段写入 1.21 的属性修饰符组件。
 */
public class ItemsNBTAdderCalculator
{
    public static CompoundTag calculate(java.util.List<ItemStack> items)
    {
        CompoundTag calculatedNBT = new CompoundTag();

        double attackBoostValue = 0;
        double luckBoostValue = 0;

        for (ItemStack itemStack : items)
        {
            if (itemStack == null)
            {
                continue;
            }

            CompoundTag itemNBT = ItemIngredient.getCustomDataTag(itemStack);
            if (itemNBT == null)
            {
                continue;
            }

            if(itemNBT.contains("AttackBoost"))
            {
                attackBoostValue += itemNBT.getDouble("AttackBoost");
            }

            if(itemNBT.contains("LuckBoost"))
            {
                luckBoostValue += itemNBT.getDouble("LuckBoost");
            }
        }

        if(attackBoostValue != 0)
        {
            calculatedNBT.putDouble("AttackBoost", attackBoostValue);
        }

        if(luckBoostValue != 0)
        {
            calculatedNBT.putDouble("LuckBoost", luckBoostValue);
        }

        return calculatedNBT;
    }

    public static ItemEnchantments calculateEnchantments(java.util.List<ItemStack> items)
    {
        Map<Holder<Enchantment>, BigInteger> enchantmentValues = new HashMap<>();

        for (ItemStack itemStack : items)
        {
            if (itemStack == null)
            {
                continue;
            }

            ItemEnchantments enchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            for (var entry : enchantments.entrySet())
            {
                BigInteger value = BigInteger.ONE.shiftLeft(entry.getIntValue());
                enchantmentValues.merge(entry.getKey(), value, BigInteger::add);
            }
        }

        if (enchantmentValues.isEmpty())
        {
            return null;
        }

        ItemEnchantments.Mutable calculatedEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        for (Map.Entry<Holder<Enchantment>, BigInteger> entry : enchantmentValues.entrySet())
        {
            int level = entry.getValue().bitLength() - 1;
            if (level > 0)
            {
                calculatedEnchantments.set(entry.getKey(), Math.min(level, 255));
            }
        }

        return calculatedEnchantments.toImmutable();
    }
}
