package com.pasterdream.pasterdreammod.world.block.dreamcauldron.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * 酿造配方适配器（对应《需求/法术工厂.md》第二节配方继承规则）。
 * 染梦果 ≈ 原版地狱疣槽位（Awkward 基底转化），槽B催化剂 = 原版效果物，
 * 槽C增强剂 = 红石延长 / 荧石强化。
 * 通过关卡的 {@code PotionBrewing} 查询原版和模组注册的酿造配方。
 */
public final class VanillaBrewingAdapter
{
    public enum Enhance { NONE, REDSTONE, GLOWSTONE }

    public enum BrewStatus { OK, INVALID_CATALYST, ENHANCE_UNSUPPORTED }

    /** 酿造结果：OK 时 effects 为最终药水的全部效果 */
    public record BrewOutcome(BrewStatus status, Holder<Potion> potion, List<MobEffectInstance> effects)
    {
        static BrewOutcome fail(BrewStatus status) { return new BrewOutcome(status, null, List.of()); }
    }

    private VanillaBrewingAdapter() {}

    /**
     * 从 Awkward 基底出发：催化剂 →（可选增强剂）→ 效果药水。
     * 催化剂无法作用（含槽B空/无效果产物）→ INVALID_CATALYST；
     * 催化剂有效但增强剂无对应配方 → ENHANCE_UNSUPPORTED。
     */
    public static BrewOutcome brew(Level level, ItemStack catalyst, Enhance enhance)
    {
        if (catalyst == null || catalyst.isEmpty())
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }

        ItemStack current = PotionContents.createItemStack(Items.POTION, Potions.AWKWARD);
        ItemStack out = level.potionBrewing().mix(catalyst, current);
        if (out.isEmpty() || ItemStack.isSameItemSameComponents(out, current))
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }
        current = out;

        if (enhance != Enhance.NONE)
        {
            ItemStack enhancer = enhance == Enhance.REDSTONE
                    ? new ItemStack(Items.REDSTONE)
                    : new ItemStack(Items.GLOWSTONE_DUST);
            ItemStack enhanced = level.potionBrewing().mix(enhancer, current);
            if (enhanced.isEmpty() || ItemStack.isSameItemSameComponents(enhanced, current))
            {
                return BrewOutcome.fail(BrewStatus.ENHANCE_UNSUPPORTED);
            }
            current = enhanced;
        }

        Holder<Potion> potion = current.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion().orElse(null);
        if (potion == null || potion.value().getEffects().isEmpty())
        {
            return BrewOutcome.fail(BrewStatus.INVALID_CATALYST);
        }
        return new BrewOutcome(BrewStatus.OK, potion, new ArrayList<>(potion.value().getEffects()));
    }
}
