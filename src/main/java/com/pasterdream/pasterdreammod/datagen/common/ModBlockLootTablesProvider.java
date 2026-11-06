package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider {
    private static final float[] DYEDREAM_LEAVES_FRUIT_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] DYEDREAM_LEAVES_STICK_CHANCES = {0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    public ModBlockLootTablesProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        // ===== 染梦地形方块 =====
        dropSelf(ModBlocks.DYEDREAM_DIRT.get());
        add(ModBlocks.DYEDREAM_GRASS_BLOCK.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModItems.DYEDREAM_DIRT.get())));
        dropOther(ModBlocks.DYEDREAM_FARMLAND.get(), ModItems.DYEDREAM_DIRT.get());
        dropSelf(ModBlocks.DYEDREAM_LOG.get());
        dropSelf(ModBlocks.DYEDREAM_WOOD.get());
        add(ModBlocks.DYEDREAM_LEAVES.get(), block -> createDyedreamLeavesDrops(block, ModItems.DYEDREAM_FRUIT.get()));
        add(ModBlocks.DYEDREAM_WORLDTREE_LEAVES.get(), block -> createDyedreamLeavesDrops(block, ModItems.DYEDREAM_FRUIT.get()));
        dropSelf(ModBlocks.DYEDREAM_SAND.get());

        add(ModBlocks.DYEDREAM_QUARTZ_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_QUARTZ.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        add(ModBlocks.DYEDREAM_DUST_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_DUST_PIECE.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        // ===== 染梦世界·方解石原版矿物（掉落与原版一致） =====
        add(ModBlocks.CALCITE_COAL_ORE.get(), block -> createOreDrop(block, Items.COAL));
        add(ModBlocks.CALCITE_IRON_ORE.get(), block -> createOreDrop(block, Items.RAW_IRON));
        add(ModBlocks.CALCITE_GOLD_ORE.get(), block -> createOreDrop(block, Items.RAW_GOLD));
        add(ModBlocks.CALCITE_DIAMOND_ORE.get(), block -> createOreDrop(block, Items.DIAMOND));
        add(ModBlocks.CALCITE_EMERALD_ORE.get(), block -> createOreDrop(block, Items.EMERALD));
        add(ModBlocks.CALCITE_COPPER_ORE.get(), block -> createCopperOreDrops(block));
        add(ModBlocks.CALCITE_REDSTONE_ORE.get(), block -> createRedstoneOreDrops(block));
        add(ModBlocks.CALCITE_LAPIS_ORE.get(), block -> createLapisOreDrops(block));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.BRICKS_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.PILLAR_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_DYEDREAM_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_STAIRS.get());
        add(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_QUARTZ_BLOCK_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_QUARTZ_BLOCK_WALL.get());

        dropSelf(ModBlocks.DYEDREAM_PLANKS.get());
        dropSelf(ModBlocks.DYEDREAM_STAIRS.get());
        add(ModBlocks.DYEDREAM_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_FENCE.get());
        dropSelf(ModBlocks.DYEDREAM_FENCE_GATE.get());
        dropSelf(ModBlocks.DYEDREAM_PANE.get());
        add(ModBlocks.DYEDREAM_DOOR.get(), createDoorTable(ModBlocks.DYEDREAM_DOOR.get()));
        dropSelf(ModBlocks.DYEDREAM_TRAPDOOR.get());
        dropSelf(ModBlocks.DYEDREAM_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DYEDREAM_BUTTON.get());

        dropSelf(ModBlocks.POLISHED_CALCITE.get());
        dropSelf(ModBlocks.POLISHED_CALCITE_STAIRS.get());
        add(ModBlocks.POLISHED_CALCITE_SLAB.get(), block -> createSlabItemTable(ModBlocks.POLISHED_CALCITE_SLAB.get()));
        dropSelf(ModBlocks.POLISHED_CALCITE_WALL.get());
        dropSelf(ModBlocks.CALCITE_TILES.get());
        dropSelf(ModBlocks.CALCITE_TILES_STAIRS.get());
        add(ModBlocks.CALCITE_TILES_SLAB.get(), block -> createSlabItemTable(ModBlocks.CALCITE_TILES_SLAB.get()));
        dropSelf(ModBlocks.CALCITE_TILES_WALL.get());
        dropSelf(ModBlocks.PINK_SLIME_BLOCK.get());
        add(ModBlocks.DYEDREAM_BUDDING_BLOCK.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.SMALL_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))));
        add(ModBlocks.MEDIUM_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
        add(ModBlocks.LARGE_DYEDREAM_BUD.get(),
                block -> createSilkTouchDispatchTable(block,
                        applyExplosionDecay(block, LootItem.lootTableItem(ModItems.DYEDREAM_BUD_NUGGET.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
        dropSelf(ModBlocks.DYEDREAM_ALLOY_BLOCK.get());
        dropSelf(ModBlocks.DYEDREAM_BUD_BLOCK.get());
        add(ModBlocks.TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        add(ModBlocks.DEEPSLATE_TITANIUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_TITANIUM.get()));
        dropSelf(ModBlocks.RAW_TITANIUM_BLOCK.get());
        dropSelf(ModBlocks.TITANIUM_BLOCK.get());
        add(ModBlocks.MOLTEN_GOLD_ORE.get(), block -> createOreDrop(block, ModItems.RAW_MOLTEN_GOLD.get()));
        dropSelf(ModBlocks.MOLTEN_GOLD_BLOCK.get());
        add(ModBlocks.SOUL_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.SOUL_DUST.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        dropSelf(ModBlocks.LOST_SWORD_TOMB.get());
        dropSelf(ModBlocks.LIFE_CRYSTAL.get());
        dropSelf(ModBlocks.SHADOW.get());
        dropSelf(ModBlocks.THICK_SHADOW.get());
        dropSelf(ModBlocks.QYM_DOLL.get());
        dropSelf(ModBlocks.UUZ_DOLL.get());
        dropSelf(ModBlocks.DESERT_HERO_TOMB.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
    }

    private LootTable.Builder createDyedreamLeavesDrops(Block leaves, ItemLike fruit) {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        var hasShearsOrSilkTouch = HAS_SHEARS.or(this.hasSilkTouch());
        return createSilkTouchOrShearsDispatchTable(leaves,
                applyExplosionDecay(leaves, LootItem.lootTableItem(fruit))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                enchantments.getOrThrow(Enchantments.FORTUNE), DYEDREAM_LEAVES_FRUIT_CHANCES)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .when(hasShearsOrSilkTouch.invert())
                        .add(applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                        enchantments.getOrThrow(Enchantments.FORTUNE), DYEDREAM_LEAVES_STICK_CHANCES))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()).toList();
    }
}
