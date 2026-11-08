package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 成品药水流体编解码 —— 完全走原模组 PasterDream-Reborn 体系。
 * 来源的 EffectList 在配方加载时转换为流体的 POTION_CONTENTS 组件：
 * 构造用 {@link PotionHelper#createNBTPotion}，解析用 {@link PotionHelper#getEffectType}，
 * 与来源配方 JSON、釜 GUI 染色、灵药瓶饮用互通。
 * 每条效果的时长仍是"喝一口"的单次时长。
 */
public final class PotionEffectCodec
{
    private PotionEffectCodec() {}

    /** 读取成品药水全部效果；非药水/无药水组件返回空列表 */
    public static List<MobEffectInstance> readFluidEffects(FluidStack stack)
    {
        if (stack == null || stack.isEmpty()) return List.of();
        List<MobEffectInstance> effects = new ArrayList<>();
        for (GenericMobEffect g : PotionHelper.getEffectType(stack))
        {
            if (g.effectType() != null)
            {
                effects.add(GenericMobEffect.fromGenericMobEffectToEffectInstance(g));
            }
        }
        return effects;
    }

    /** 构造成品药水流体。effects 为空时 PotionHelper 返回水，调用方须先保证非空。 */
    public static FluidStack createPotionFluid(int amountMb, List<MobEffectInstance> effects)
    {
        List<GenericMobEffect> generic =
                GenericMobEffect.fromListMobEffectInstanceToListGenericMobEffect(effects);
        return PotionHelper.createNBTPotion(generic, amountMb);
    }

    /** 成品药水判定：必须为 potion 流体且药水效果列表非空 */
    public static boolean isPotionFluid(FluidStack stack)
    {
        if (stack == null || stack.isEmpty() || stack.getFluid() != ModFluids.POTION.get())
        {
            return false;
        }
        return !PotionHelper.getEffectType(stack).isEmpty();
    }
}
