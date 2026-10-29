package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/** 来源数值保持不变；1.21 用不可采集方块标签替代整数挖掘等级。 */
public enum ModToolTiers implements Tier {
    TITANIUM(1721, 9.0F, 3.5F, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 17,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));

    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final TagKey<Block> incorrectBlocksForDrops;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModToolTiers(int uses, float speed, float attackDamageBonus, TagKey<Block> incorrectBlocksForDrops,
                 int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
