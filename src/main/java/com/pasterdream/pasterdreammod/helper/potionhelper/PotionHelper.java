package com.pasterdream.pasterdreammod.helper.potionhelper;

import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PotionHelper
{
    //从流体栈中的药水组件读取效果
    public static List<GenericMobEffect> getEffectType(FluidStack fluidStack)
    {
        PotionContents potionContents = fluidStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        List<MobEffectInstance> potionEffects = new ArrayList<>();

        for (MobEffectInstance effectInstance : potionContents.getAllEffects())
        {
            potionEffects.add(effectInstance);
        }

        return GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(potionEffects);
    }

    //构建药水名称
    public static Component getPotionName(List<GenericMobEffect> effectList)
    {
        if (effectList.isEmpty())
        {
            return Component.literal("null").append(Component.translatable("fluid.pasterdream.potion"));
        }

        MutableComponent result = Component.empty();
        for (GenericMobEffect effect : effectList)
        {
            result.append(formatTime(effect.time()));
            MobEffect mobEffect = effect.effectType() != null ? effect.effectType().value() : null;
            result.append(mobEffect != null ? Component.translatable(mobEffect.getDescriptionId()).getString() : "null");
            if(effect.level() != 0)
            {
                result.append(Component.translatable("enchantment.level." + (effect.level() + 1)));
            }
            result.append(Component.literal(" - "));
        }

        result.append(Component.translatable("fluid.pasterdream.potion"));
        return result;
    }

    public static String formatTime(int ticks)
    {
        if(ticks == -1)
        {
            return "∞";
        }
        else
            if (ticks == 0 || ticks == 1)
            {
                return "";
            }
            else
                if (ticks < 72000)
                {
                    return (String.format("%02d", (ticks / 1200)) + ":" + String.format("%02d", ((ticks % 1200) / 20)));
                }
                    else
                    {
                        return (String.format("%02d", (ticks / 72000)) + ":" + String.format("%02d", ((ticks % 72000) / 1200)) + ":" + String.format("%02d", ((ticks % 1200) / 20)));
                    }
    }

    public static int getMixingPotionColor(List<GenericMobEffect> effectList)
    {
        long totalRed = 0;
        long totalGreen = 0;
        long totalBlue = 0;
        int totalLevel = 0;

        for(GenericMobEffect effect : effectList)
        {
            MobEffect mobEffect = effect.effectType() != null ? effect.effectType().value() : null;
            if(mobEffect != null)
            {
                int singleColor = mobEffect.getColor();

                int level = effect.level() + 1;
                totalLevel += level;
                totalRed += (long)(singleColor & 0x00FF0000) * level;
                totalGreen += (long)(singleColor & 0x0000FF00) * level;
                totalBlue += (long)(singleColor & 0x000000FF) * level;
            }
        }

        return (effectList.isEmpty() || totalLevel == 0) ? 0xFF000000 : 0xFF000000 | (int)(totalRed / totalLevel) + (int)(totalGreen / totalLevel) + (int)(totalBlue / totalLevel);
    }

    public static FluidStack createNBTPotion(List<GenericMobEffect> effectList, int amount)
    {
        List<MobEffectInstance> potionEffects = new ArrayList<>();

        for (GenericMobEffect effect : effectList)
        {
            MobEffectInstance effectInstance = GenericMobEffect.fromGenericMobEffectToEffectInstance(effect);
            if (effectInstance != null)
            {
                potionEffects.add(effectInstance);
            }
        }

        if (potionEffects.isEmpty())
        {
            return new FluidStack(Fluids.WATER, amount);
        }

        FluidStack fluidStack = new FluidStack(ModFluids.POTION.get(), amount);
        fluidStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), potionEffects));

        return fluidStack;
    }

    public static List<GenericMobEffect> getListGenericMobEffectFromPotion(ItemStack itemStack)
    {
        PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        List<MobEffectInstance> potionEffects = new ArrayList<>();

        for (MobEffectInstance effectInstance : potionContents.getAllEffects())
        {
            potionEffects.add(effectInstance);
        }

        return GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(potionEffects);
    }

    public static List<GenericMobEffect> getListGenericMobEffectFromPotion(Potion potion)
    {
        List<MobEffectInstance> potionEffects = potion.getEffects();
        return GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(potionEffects);
    }

    public static Potion getPotionFromListGenericMobEffect(List<GenericMobEffect> effectList)
    {
        for (Potion potion : BuiltInRegistries.POTION)
        {
            if (getListGenericMobEffectFromPotion(potion).equals(effectList))
            {
                return potion;
            }
        }

        return null;
    }

    public static ItemStack getCustomEffectPotion(List<GenericMobEffect> effectList)
    {
        List<MobEffectInstance> potionEffects = new ArrayList<>();
        for(GenericMobEffect effect : effectList)
        {
            potionEffects.add(GenericMobEffect.fromGenericMobEffectToEffectInstance(effect));
        }

        ItemStack potionItem = new ItemStack(Items.POTION);
        potionItem.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), potionEffects));
        return potionItem;
    }

    public static ItemStack getWaterBottle()
    {
        return PotionContents.createItemStack(Items.POTION, Potions.WATER);
    }

    /** 等级 → 罗马数字（原版 potency 最多只用到 V，高等级按规则拼接） */
    private static final int[] ROMAN_VALUES = {100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ROMAN_SYMBOLS =
            {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String toRoman(int level)
    {
        if (level <= 0)
        {
            return String.valueOf(level);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ROMAN_VALUES.length; i++)
        {
            while (level >= ROMAN_VALUES[i])
            {
                stringBuilder.append(ROMAN_SYMBOLS[i]);
                level -= ROMAN_VALUES[i];
            }
        }
        return stringBuilder.toString();
    }
}
