package com.pasterdream.pasterdreammod.datagen.common;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.cropblock.PasterDreamCropBlock;
import com.pasterdream.pasterdreammod.world.conditions.RealPlayerCondition;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
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

        dropSelf(ModBlocks.DYEDREAM_SAPLING.get());
        dropSelf(ModBlocks.PINK_MUSHROOM.get());
        add(ModBlocks.TALL_PINK_MUSHROOM.get(), block -> createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        dropSelf(ModBlocks.GOLDENROD.get());
        dropSelf(ModBlocks.FERRARIA_CRISPA.get());
        dropSelf(ModBlocks.EUSTOMA.get());
        dropSelf(ModBlocks.MALVA_SINENSIS_CAVAN.get());
        dropSelf(ModBlocks.LINHT_FLOWER.get());
        add(ModBlocks.DREAMING_LOTUS.get(), block -> plantTallFlowerDrops(block));
        add(ModBlocks.MISTY_DREAMING_LOTUS.get(), block -> plantTallFlowerDrops(block));
        dropSelf(ModBlocks.DYEDREAM_LILY_OF_THE_VALLEY.get());
        add(ModBlocks.BLAZE_FLOWER.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(Items.BLAZE_POWDER)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        dropSelf(ModBlocks.WHITE_ORCHID_FLOWER.get());
        dropSelf(ModBlocks.EDELWEISS.get());
        dropSelf(ModBlocks.NIPPY_EDELWEISS.get());
        dropSelf(ModBlocks.DYEDREAM_LILY_PAD.get());
        dropSelf(ModBlocks.DYEDREAM_LOTUS.get());
        add(ModBlocks.DYEDREAM_MOSS.get(), block -> plantGrassDrops(block));
        add(ModBlocks.STEM_GRASS.get(), block -> plantGrassDrops(block));
        add(ModBlocks.TALL_STEM_GRASS.get(), block -> plantTallGrassDrops(block, ModBlocks.STEM_GRASS.get(), 2));
        add(ModBlocks.SINGULARITY_FERN.get(), block -> plantGrassDrops(block));
        add(ModBlocks.CRIMSON_THORNS.get(), block -> plantTallGrassDrops(block, block, 1));
        add(ModBlocks.OATS.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RYE_SEED.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        add(ModBlocks.RYE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.RYE_SEED.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        add(ModBlocks.POLISHED_CALCITE_STALICRIPE.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.SMALL_POLISHED_CALCITE_STALICRIPE.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.DYEDREAM_SEAGRASS.get(), block -> plantGrassDrops(block));
        add(ModBlocks.REED.get(), block -> plantShearsOrSilkElseItem(block, ModItems.REED_ROD.get(), 1, 3));
        add(ModBlocks.DYEDREAM_VINE.get(), block -> plantShearsOrSilkElseItem(block, ModItems.DYEDREAM_FRUIT.get(), 0, 1));
        add(ModBlocks.JUNGLE_SPORANGIUM.get(), block -> plantShearsOrSilkElseItem(block, ModItems.JUNGLE_SPORE.get(), 1, 1));
        add(ModBlocks.FOURLEAF_CLOVER.get(), block -> createSilkTouchOrShearsDispatchTable(block,
                LootItem.lootTableItem(ModItems.FOURLEAF_CLOVER_CURIO.get())
                        .when(LootItemRandomChanceCondition.randomChance(0.05F))));
        add(ModBlocks.HAIRY_MOSS.get(), block -> plantGrassDrops(block));
        add(ModBlocks.WIND_CLEAVING_GRASS.get(), block -> plantGrassDrops(block));
        add(ModBlocks.WIND_FEATHER_GRASS.get(), block -> plantTallGrassDrops(block, block, 1));
        add(ModBlocks.WIND_ISLAND_REED.get(), block -> plantShearsOrSilkElseItem(block, ModItems.REED_ROD.get(), 1, 3));
        dropSelf(ModBlocks.SHADOW_SHORT_ROOTS.get());
        dropSelf(ModBlocks.SHADOW_ROOTS.get());
        add(ModBlocks.SHADOW_STEM_FERN.get(), block -> plantTallFlowerDrops(block));
        add(ModBlocks.SHADOW_SPROUTS.get(), block -> plantGrassDrops(block));
        dropSelf(ModBlocks.SHADOW_FERN.get());
        dropSelf(ModBlocks.SHADOW_FUNGUS.get());
        dropSelf(ModBlocks.LIGHT_BALL.get());
        dropSelf(ModBlocks.PEBBLE.get());
        cropDrops(ModBlocks.DYEDREAM_COROLLA_CROP.get(), ModItems.DYEDREAM_COROLLA.get(), 1, ModItems.DYEDREAM_COROLLA_CROP_AGE_1.get(), ModItems.DYEDREAM_COROLLA_CROP_AGE_0.get());
        cropDrops(ModBlocks.WHITE_COROLLA_CROP.get(), ModItems.WHITE_COROLLA.get(), 1, ModItems.WHITE_COROLLA_CROP_AGE_1.get(), ModItems.WHITE_COROLLA_CROP_AGE_0.get());
        cropDrops(ModBlocks.LIGHT_BALL_CROP.get(), ModItems.LIGHT_BALL.get(), 1, ModItems.LIGHT_BALL_CROP_AGE_1.get(), ModItems.LIGHT_BALL_CROP_AGE_0.get());
        cropDrops(ModBlocks.CLOUD_CROP.get(), ModItems.CLOUD.get(), 5, ModItems.CLOUD_CROP_AGE_1.get(), ModItems.CLOUD_CROP_AGE_0.get());
        cropDrops(ModBlocks.COTTON_CROP.get(), ModItems.COTTON.get(), 1, ModItems.COTTON_CROP_AGE_1.get(), ModItems.COTTON_CROP_AGE_0.get());

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
        add(ModBlocks.AMBER_CANDY_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.AMBER_CANDY.get())
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
        // 粉顶菌方块精准采集掉落自身，普通破坏掉落粉顶菌。
        add(ModBlocks.PINK_MUSHROOM_BLOCK.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.PINK_MUSHROOM.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        add(ModBlocks.PINK_MUSHROOM_STEM.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        dropSelf(ModBlocks.PINK_SHROOMLIGHT.get());
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
        dropSelf(ModBlocks.DYEDREAM_BUD_BRICKS.get());
        dropSelf(ModBlocks.DYEDREAM_BUD_STAIRS.get());
        add(ModBlocks.DYEDREAM_BUD_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_BUD_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_BUD_WALL.get());
        dropSelf(ModBlocks.DYEDREAM_SANDSTONE.get());
        dropSelf(ModBlocks.DYEDREAM_SANDSTONE_STAIRS.get());
        add(ModBlocks.DYEDREAM_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.DYEDREAM_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.DYEDREAM_SANDSTONE_WALL.get());
        dropSelf(ModBlocks.CUT_DYEDREAM_SANDSTONE.get());
        dropSelf(ModBlocks.CUT_DYEDREAM_SANDSTONE_STAIRS.get());
        add(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.CUT_DYEDREAM_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.CUT_DYEDREAM_SANDSTONE_WALL.get());
        dropSelf(ModBlocks.CHISELED_DYEDREAM_SANDSTONE.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE.get());
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_STAIRS.get());
        add(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.SMOOTH_DYEDREAM_SANDSTONE_WALL.get());
        add(ModBlocks.DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.CARVE_DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.CARVE_DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.GOLD_CARVE_DYEDREAM_GLASS_PANE.get(),
                block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.DYEDREAM_ICE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        add(ModBlocks.DYEDREAM_PACKED_ICE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(Items.AIR)));
        dropSelf(ModBlocks.CLOUD.get());
        dropSelf(ModBlocks.DARK_CLOUD.get());
        dropSelf(ModBlocks.BIG_BUBBLE.get());
        dropSelf(ModBlocks.DYEDREAM_CRYSTAL_LANTERN.get());
        dropSelf(ModBlocks.DYEDREAM_LANTERN.get());
        dropSelf(ModBlocks.ICE_STONE.get());
        add(ModBlocks.ICE_BUD.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModBlocks.ICE_BUD.get()))));
        dropSelf(ModBlocks.CALCITE_CONE.get());
        dropSelf(ModBlocks.SALT_BLOCK.get());
        dropSelf(ModBlocks.CHARGED_AMETHYST_BLOCK.get());
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
        dropSelf(ModBlocks.DYEDREAM_CRACK.get());
        dropSelf(ModBlocks.LIFE_CRYSTAL.get());
        dropSelf(ModBlocks.GOLDEN_FOX_SCULPTURE.get());
        dropSelf(ModBlocks.FOX_SCULPTURE.get());
        dropSelf(ModBlocks.SHADOW.get());
        dropSelf(ModBlocks.THICK_SHADOW.get());

        // ===== 灯影之下 =====
        dropSelf(ModBlocks.SHADOW_STONE.get());
        dropSelf(ModBlocks.SHADOW_STONE_BRICK.get());
        dropSelf(ModBlocks.SHADOW_STONE_BRICK_STAIRS.get());
        add(ModBlocks.SHADOW_STONE_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_STONE_BRICK_SLAB.get()));
        dropSelf(ModBlocks.SHADOW_STONE_BRICK_WALL.get());
        dropSelf(ModBlocks.NARROW_SHADOW_STONE_BRICK.get());
        dropSelf(ModBlocks.NARROW_SHADOW_STONE_BRICK_STAIRS.get());
        add(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.NARROW_SHADOW_STONE_BRICK_SLAB.get()));
        dropSelf(ModBlocks.NARROW_SHADOW_STONE_BRICK_WALL.get());
        dropSelf(ModBlocks.SHADOW_STONE_TILES.get());
        dropSelf(ModBlocks.SHADOW_STONE_TILES_STAIRS.get());
        add(ModBlocks.SHADOW_STONE_TILES_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_STONE_TILES_SLAB.get()));
        dropSelf(ModBlocks.SHADOW_STONE_TILES_WALL.get());
        dropSelf(ModBlocks.CRACKED_SHADOW_STONE_BRICK.get());
        dropSelf(ModBlocks.CHISELED_SHADOW_STONE_BRICK.get());
        dropSelf(ModBlocks.SHADOW_STONE_CAGE_RUNE.get());
        dropSelf(ModBlocks.SHADOW_STONE_HOLY_GRAIL_RUNE.get());
        dropSelf(ModBlocks.SHADOW_STONE_OBLATION_RUNE.get());
        dropSelf(ModBlocks.SHADOW_STONE_TRIPOD_CAULDRON_RUNE.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_STONE.get());
        dropSelf(ModBlocks.CHISELED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.CRACKED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.FRACTURED_SHADOW_DUNGEON_BRICKS.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_BRICK_STAIRS.get());
        add(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_DUNGEON_BRICK_SLAB.get()));
        add(ModBlocks.SHATTERED_SHADOW_DUNGEON_BRICKS.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(net.minecraft.world.item.Items.AIR)));
        dropSelf(ModBlocks.SHADOW_ARENA_BLOCK.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_0.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_1.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_2.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_3.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_4.get());
        dropSelf(ModBlocks.SHADOW_FISSURE_5.get());
        add(ModBlocks.SHADOW_NYLIUM.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModBlocks.SHADOW_STONE.get())));
        dropSelf(ModBlocks.SHADOW_LIGHT.get());
        dropSelf(ModBlocks.SHADOW_SHROOMLIGHT.get());
        dropSelf(ModBlocks.SHADOW_CANDLE.get());
        dropSelf(ModBlocks.SHADOW_WART_BLOCK.get());
        dropSelf(ModBlocks.SHADOW_STEM.get());
        dropSelf(ModBlocks.SHADOW_HYPHAE.get());
        dropSelf(ModBlocks.STRIPPED_SHADOW_STEM.get());
        dropSelf(ModBlocks.STRIPPED_SHADOW_HYPHAE.get());
        dropSelf(ModBlocks.SHADOW_PLANKS.get());
        dropSelf(ModBlocks.SHADOW_STAIRS.get());
        add(ModBlocks.SHADOW_SLAB.get(), block -> createSlabItemTable(ModBlocks.SHADOW_SLAB.get()));
        dropSelf(ModBlocks.SHADOW_FENCE.get());
        dropSelf(ModBlocks.SHADOW_FENCE_GATE.get());
        dropSelf(ModBlocks.SHADOW_PANE.get());
        add(ModBlocks.SHADOW_DOOR.get(), createDoorTable(ModBlocks.SHADOW_DOOR.get()));
        dropSelf(ModBlocks.SHADOW_TRAPDOOR.get());
        dropSelf(ModBlocks.SHADOW_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.SHADOW_BUTTON.get());
        add(ModBlocks.SHADOW_BOOKSHELF.get(), block -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(Items.BOOK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)))));
        add(ModBlocks.WORN_SHADOW_BOOKSHELF.get(), block -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(Items.BOOK)));
        add(ModBlocks.COBWEB_SHADOW_BOOKSHELF.get(), block -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(Items.BOOK)));
        add(ModBlocks.KEY_SHADOW_BOOKSHELF.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.KEY_SHADOW_BOOKSHELF.get()).when(this.hasSilkTouch())))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.BOOK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
                        .when(this.hasSilkTouch().invert()))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SHADOW_DUNGEON_KEY.get()))
                        .when(this.hasSilkTouch().invert())));
        dropSelf(ModBlocks.BLACK_METAL_BLOCK.get());
        dropSelf(ModBlocks.RUST_BLACK_METAL_BLOCK.get());
        dropSelf(ModBlocks.RUST_BLACK_METAL_WALL.get());
        dropSelf(ModBlocks.RUST_BLACK_METAL_BARS.get());
        dropSelf(ModBlocks.SHADOW_CLAY_POT.get());
        dropSelf(ModBlocks.QYM_DOLL.get());
        dropSelf(ModBlocks.UUZ_DOLL.get());
        dropSelf(ModBlocks.DESERT_HERO_TOMB.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_CRAFTING_TABLE.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_ANVIL.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_COOLER_POT.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_HAMMER.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_GRIND_STONE.get());
        dropSelf(ModBlocks.WEAPON_WORKSHOP_BLAST_FURNACE.get());
        dropSelf(ModBlocks.SHADOW_BLAST_FURNACE.get());
        dropSelf(ModBlocks.DYEDREAM_DESK.get());
        dropSelf(ModBlocks.SHADOW_DESK.get());
        dropSelf(ModBlocks.WIND_MOOR_DESK.get());
        dropSelf(ModBlocks.PICNIC_BASKET.get());
        dropSelf(ModBlocks.SHADOW_CHEST.get());
        dropSelf(ModBlocks.MELT_DREAM_CRYSTAL_CHEST.get());
        dropSelf(ModBlocks.OPENED_MELT_DREAM_CRYSTAL_CHEST.get());
        dropSelf(ModBlocks.TWILIGHT_LANTERN.get());
        dropSelf(ModBlocks.SHADOW_BED.get());
        dropSelf(ModBlocks.WIND_MOOR_CRATE.get());
        dropSelf(ModBlocks.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get());
        dropSelf(ModBlocks.RESEARCH_TABLE.get());
        dropSelf(ModBlocks.CLAYPAN.get());
        dropSelf(ModBlocks.DREAM_CAULDRON.get());
        dropSelf(ModBlocks.DREAM_ACCUMULATOR.get());
        dropSelf(ModBlocks.SHADOW_BLAST_FURNACE_CORE.get());
        // 暗影之手陷阱：精准采集掉落自身，否则 100% 阴影 + 10% 噩梦燃料
        add(ModBlocks.SHADOW_HAND_TRAP.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModBlocks.SHADOW_HAND_TRAP.get())
                                .when(hasSilkTouch()))
                        .add(LootItem.lootTableItem(ModItems.SHADOW.get())
                                .when(hasSilkTouch().invert())))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.NIGHTMARE_FUEL.get())
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                .when(hasSilkTouch().invert()))));
        dropOther(ModBlocks.SHADOW_DUNGEON_WALL_KEY.get(), ModItems.SHADOW_DUNGEON_KEY.get());
        dropOther(ModBlocks.SHADOW_DUNGEON_FLOOR_KEY.get(), ModItems.SHADOW_DUNGEON_KEY.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_GATE.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_BARRIER.get());
        dropSelf(ModBlocks.BROKEN_SHADOW_DUNGEON_PORTAL.get());
        dropSelf(ModBlocks.SHADOW_DUNGEON_PORTAL.get());
        add(ModBlocks.DREAM_SPAWNER.get(), LootTable.lootTable());
        add(ModBlocks.FADED_DREAM_SPAWNER.get(), LootTable.lootTable());
        dropSelf(ModBlocks.ECOLOGY_GLASS_JAR.get());
        dropSelf(ModBlocks.FIREFLY_GLASS_JAR.get());
        dropSelf(ModBlocks.FIREFLY_NEST.get());
        dropSelf(ModBlocks.CHRISTMAS_LIGHTS.get());
        add(ModBlocks.SMALL_STONE_SPIRIT_BLOCK.get(), block -> createSilkTouchOnlyTable(block));
        // 鸟巢：精准采集掉落自身，否则掉落 2 次树枝/鸡蛋
        add(ModBlocks.BIRDS_NEST.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.EGG).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .when(doesNotHaveSilkTouch()))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModBlocks.BIRDS_NEST.get()).when(hasSilkTouch()))));

        // ===== 盆栽植物 =====
        for (var entry : ModBlocks.POTTED_PLANTS.entrySet()) {
            var plant = entry.getKey();
            var potted = entry.getValue();
            if (ModBlocks.POTTED_CROPS.contains(potted)) {
                var items = ModBlocks.POTTED_CROP_ITEMS.get(potted);
                var matureCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(potted.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(com.pasterdream.pasterdreammod.world.block.PottedCropBlock.AGE, 1));
                add(potted.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(items.seedling().get()).when(matureCondition.invert())))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(items.mature().get()).when(matureCondition))));
            } else {
                add(potted.get(), block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.FLOWER_POT)))
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(plant.get()))));
            }
        }
        windJourneyLoot();

        add(ModBlocks.DREAM_TRAIN_STRUCTURE.get(), LootTable.lootTable());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_0.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_1.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_2.get());
        dropSelf(ModBlocks.MODEL_BREAK_PARTICLE_PROVIDER_BLOCK_3.get());
    }

    // ===== 风之旅途 =====
    private void windJourneyLoot() {
        var fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        add(ModBlocks.CONGEAL_WIND_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.CONGEAL_WIND.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune)))));
        add(ModBlocks.WIND_RUNNER_CRYSTAL_ORE.get(), block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(ModItems.WIND_RUNNER_CRYSTAL.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune)))));
        dropSelf(ModBlocks.CONGEAL_WIND_BLOCK.get());
        dropSelf(ModBlocks.WIND_RUNNER_CRYSTAL_BLOCK.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_BLOCK.get());
        dropSelf(ModBlocks.FLUFFY_WIND_ALLOY_BLOCK.get());
        dropSelf(ModBlocks.EJECTION_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.EJECTION_PRESSURE_BLOCK.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_BARS.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_LANTERN.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_CHAIN.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_TRAPDOOR.get());
        dropSelf(ModBlocks.CONGEAL_WIND_IRON_PRESSURE_PLATE.get());

        add(ModBlocks.CLARITY_GLASS.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.CLARITY_GLASS_PANE.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.CARVE_CLARITY_GLASS.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.CARVE_CLARITY_GLASS_PANE.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.FRAME_CLARITY_GLASS.get(), block -> createSilkTouchOnlyTable(block));
        add(ModBlocks.FRAME_CLARITY_GLASS_PANE.get(), block -> createSilkTouchOnlyTable(block));

        dropSelf(ModBlocks.WHITE_SAND.get());
        dropSelf(ModBlocks.THICK_CLOUD.get());
        dropSelf(ModBlocks.WHITE_SANDSTONE.get());
        dropSelf(ModBlocks.WHITE_SANDSTONE_STAIRS.get());
        add(ModBlocks.WHITE_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.WHITE_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.WHITE_SANDSTONE_WALL.get());
        dropSelf(ModBlocks.CUT_WHITE_SANDSTONE.get());
        dropSelf(ModBlocks.CUT_WHITE_SANDSTONE_STAIRS.get());
        add(ModBlocks.CUT_WHITE_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.CUT_WHITE_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.CUT_WHITE_SANDSTONE_WALL.get());
        dropSelf(ModBlocks.CHISELED_WHITE_SANDSTONE.get());
        dropSelf(ModBlocks.SMOOTH_WHITE_SANDSTONE.get());
        dropSelf(ModBlocks.SMOOTH_WHITE_SANDSTONE_STAIRS.get());
        add(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SMOOTH_WHITE_SANDSTONE_SLAB.get()));
        dropSelf(ModBlocks.SMOOTH_WHITE_SANDSTONE_WALL.get());

        dropSelf(ModBlocks.CYAN_STONE.get());
        add(ModBlocks.CYAN_MOSS_STONE.get(), block -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModBlocks.CYAN_STONE.get())));
        dropSelf(ModBlocks.CYAN_STONE_BRICKS.get());
        dropSelf(ModBlocks.CYAN_STONE_BRICK_STAIRS.get());
        add(ModBlocks.CYAN_STONE_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.CYAN_STONE_BRICK_SLAB.get()));
        dropSelf(ModBlocks.CYAN_STONE_BRICK_WALL.get());
        dropSelf(ModBlocks.CYAN_STONE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.CYAN_STONE_BUTTON.get());
        dropSelf(ModBlocks.MOSSY_CYAN_STONE_BRICKS.get());
        dropSelf(ModBlocks.MOSSY_CYAN_STONE_BRICK_STAIRS.get());
        add(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.MOSSY_CYAN_STONE_BRICK_SLAB.get()));
        dropSelf(ModBlocks.MOSSY_CYAN_STONE_BRICK_WALL.get());
        dropSelf(ModBlocks.CHISELED_CYAN_STONE_BRICKS.get());
        dropSelf(ModBlocks.CYAN_STONE_PILLAR.get());
        dropSelf(ModBlocks.POLISHED_CYAN_STONE.get());
        dropSelf(ModBlocks.POLISHED_CYAN_STONE_STAIRS.get());
        add(ModBlocks.POLISHED_CYAN_STONE_SLAB.get(), block -> createSlabItemTable(ModBlocks.POLISHED_CYAN_STONE_SLAB.get()));
        dropSelf(ModBlocks.POLISHED_CYAN_STONE_WALL.get());

        dropSelf(ModBlocks.WIND_MOOR_LOG.get());
        dropSelf(ModBlocks.WIND_MOOR_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_WIND_MOOR_LOG.get());
        dropSelf(ModBlocks.STRIPPED_WIND_MOOR_WOOD.get());
        add(ModBlocks.WIND_MOOR_LEAVES_0.get(), block -> createLeavesDrops(block, ModBlocks.WIND_MOOR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(ModBlocks.WIND_MOOR_LEAVES_1.get(), block -> createLeavesDrops(block, ModBlocks.WIND_MOOR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(ModBlocks.WIND_MOOR_SAPLING.get());
        add(ModBlocks.FIG_VINE.get(), block -> LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.FIG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(fortune)))));
        dropSelf(ModBlocks.WIND_MOOR_PLANKS.get());
        dropSelf(ModBlocks.WIND_MOOR_STAIRS.get());
        add(ModBlocks.WIND_MOOR_SLAB.get(), block -> createSlabItemTable(ModBlocks.WIND_MOOR_SLAB.get()));
        dropSelf(ModBlocks.WIND_MOOR_FENCE.get());
        dropSelf(ModBlocks.WIND_MOOR_FENCE_GATE.get());
        dropSelf(ModBlocks.WIND_MOOR_PANE.get());
        add(ModBlocks.WIND_MOOR_DOOR.get(), block -> createDoorTable(ModBlocks.WIND_MOOR_DOOR.get()));
        dropSelf(ModBlocks.WIND_MOOR_TRAPDOOR.get());
        dropSelf(ModBlocks.WIND_MOOR_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.WIND_MOOR_BUTTON.get());
        dropSelf(ModBlocks.BREAK_WIND_CURTAIN.get());
    }

    private LootTable.Builder plantGrassDrops(Block block) {
        return createSilkTouchOrShearsDispatchTable(block, EmptyLootItem.emptyItem());
    }

    private LootTable.Builder plantTallFlowerDrops(Block block) {
        return createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
    }

    private LootTable.Builder plantTallGrassDrops(Block block, Block drop, int count) {
        var lower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .when(lower).when(HAS_SHEARS.or(hasSilkTouch()))
                .add(LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))));
    }

    private LootTable.Builder plantShearsOrSilkElseItem(Block block, ItemLike drop, float min, float max) {
        var fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
        return createSilkTouchOrShearsDispatchTable(block,
                applyExplosionDecay(block, LootItem.lootTableItem(drop)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        .apply(ApplyBonusCount.addOreBonusCount(fortune))));
    }

    private void cropDrops(Block cropBlock, Item productItem, int productCount, Item matureItem, Item immatureItem) {
        var mature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PasterDreamCropBlock.AGE, 1));
        var immature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PasterDreamCropBlock.AGE, 0));
        var realPlayer = RealPlayerCondition.builder();
        add(cropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(mature).when(realPlayer)
                        .add(LootItem.lootTableItem(matureItem)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(mature).when(realPlayer.invert())
                        .add(LootItem.lootTableItem(productItem)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(productCount)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(immature)
                        .add(LootItem.lootTableItem(immatureItem))));
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
