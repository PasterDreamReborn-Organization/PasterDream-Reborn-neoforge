package com.pasterdream.pasterdreammod.world.functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import java.util.List;
import com.pasterdream.pasterdreammod.init.ModLootTables;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * 实体战利品抢夺函数。与 {@code ApplyBonusCount} 不同，此函数不声明 {@code tool} 参数依赖，
 * 因此可在 ENTITY 战利品上下文中通过 datagen 验证。运行时从实体击杀者手中获取工具并计算抢夺等级。
 */
public class ApplyEntityLootingFunction extends LootItemConditionalFunction {
    public static final MapCodec<ApplyEntityLootingFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance).and(instance.group(
                    Enchantment.CODEC.fieldOf("enchantment").forGetter(function -> function.enchantment),
                    Codec.intRange(0, Integer.MAX_VALUE - 1).optionalFieldOf("bonus_per_level", 1).forGetter(function -> function.bonusPerLevel)
            )).apply(instance, ApplyEntityLootingFunction::new));

    private final Holder<Enchantment> enchantment;
    private final int bonusPerLevel;

    ApplyEntityLootingFunction(List<LootItemCondition> conditions, Holder<Enchantment> enchantment, int bonusPerLevel) {
        super(conditions);
        this.enchantment = enchantment;
        this.bonusPerLevel = bonusPerLevel;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext ctx) {
        int looting = getKillerEnchantLevel(ctx);
        if (looting > 0) {
            RandomSource random = ctx.getRandom();
            int bonus = 0;
            for (int i = 0; i < looting; i++) {
                bonus += random.nextInt(bonusPerLevel + 1);
            }
            stack.grow(bonus);
        }
        return stack;
    }

    /**
     * 从击杀者手中读取附魔等级。实体战利品上下文不提供 {@code LootContextParams.TOOL}，
     * 故必须经 {@code KILLER_ENTITY}（回退 {@code LAST_DAMAGE_PLAYER}）取击杀者主手物品。
     */
    private int getKillerEnchantLevel(LootContext ctx) {
        Entity killer = ctx.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
        if (killer == null) {
            killer = ctx.getParamOrNull(LootContextParams.LAST_DAMAGE_PLAYER);
        }
        if (killer instanceof LivingEntity living) {
            return EnchantmentHelper.getItemEnchantmentLevel(enchantment, living.getMainHandItem());
        }
        return 0;
    }

    @Override
    public LootItemFunctionType<ApplyEntityLootingFunction> getType() {
        return ModLootTables.APPLY_ENTITY_LOOTING_FUNCTION.get();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
        private final Holder<Enchantment> enchantment;
        private final int bonusPerLevel;

        public Builder(Holder<Enchantment> enchantment, int bonusPerLevel) {
            this.enchantment = enchantment;
            this.bonusPerLevel = bonusPerLevel;
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public ApplyEntityLootingFunction build() {
            return new ApplyEntityLootingFunction(getConditions(), enchantment, bonusPerLevel);
        }
    }

}
