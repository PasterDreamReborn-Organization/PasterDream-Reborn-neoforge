package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/** 来源数值保持不变；1.21 用不可采集方块标签替代整数挖掘等级。 */
public enum ModToolTiers implements Tier {
    MOLTEN_GOLD(251, 14.0F, 2.0F, BlockTags.INCORRECT_FOR_IRON_TOOL, 23,
            () -> Ingredient.of(ModItems.MOLTEN_GOLD_INGOT.get())),
    TITANIUM(1721, 9.0F, 3.5F, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 17,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get())),
    DYEDREAM(1314, 11.0F, 4.0F, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 22,
            () -> Ingredient.of(ModItems.DYEDREAM_ALLOY_INGOT.get())),
    MELT_DREAM(1728, 14.0F, 4.5F, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 22,
            () -> Ingredient.of(ModItems.MELT_DREAM_CRYSTAL_FRAGMENT.get())),
    HELLFIRE(1255, 16.0F, 3.0F, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 23,
            () -> Ingredient.of(ModItems.MOLTEN_GOLD_INGOT.get())),
    KUSANAGI(874, 1.0F, 3.0F, BlockTags.INCORRECT_FOR_STONE_TOOL, 16,
            () -> Ingredient.of(ModItems.JUNGLE_SPORE.get())),
    MURAKUMO_KUSANAGI(1311, 1.0F, 3.5F, BlockTags.INCORRECT_FOR_STONE_TOOL, 16,
            () -> Ingredient.of(ModItems.JUNGLE_SPORE.get())),
    TIDE_SWORD(1561, 4.0F, 4.5F, BlockTags.INCORRECT_FOR_STONE_TOOL, 11,
            () -> Ingredient.of(Items.PRISMARINE_CRYSTALS)),
    BEIHAI_RUO_TIDE_SWORD(1561, 4.0F, 5.0F, BlockTags.INCORRECT_FOR_STONE_TOOL, 11,
            () -> Ingredient.of(Items.PRISMARINE_CRYSTALS)),
    DESERT_SWORD(1561, 1.0F, 7.0F, BlockTags.INCORRECT_FOR_STONE_TOOL, 8,
            Ingredient::of),
    CHENJINGMEN_DESERT_SWORD(1561, 1.0F, 8.0F, BlockTags.INCORRECT_FOR_STONE_TOOL, 8,
            Ingredient::of),
    BROKEN_HERO_SWORD(100, 1.0F, 3.0F, BlockTags.INCORRECT_FOR_STONE_TOOL, 0,
            Ingredient::of);

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
