package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.world.block.shadow.ShadowBlock;
import com.pasterdream.pasterdreammod.world.block.shadow.ThickShadowBlock;
import com.pasterdream.pasterdreammod.world.block.doll.qymdoll.QYMDollBlock;
import com.pasterdream.pasterdreammod.world.block.doll.uuzdoll.UUZDollBlock;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.world.block.DyedreamBuddingBlock;
import com.pasterdream.pasterdreammod.world.block.SoulOreBlock;
import com.pasterdream.pasterdreammod.world.block.DyedreamClusterBlock;
import com.pasterdream.pasterdreammod.world.block.desertherotomb.DesertHeroTombBlock;
import com.pasterdream.pasterdreammod.world.block.lostswordtomb.LostSwordTombBlock;
import com.pasterdream.pasterdreammod.world.block.lifecrystal.LifeCrystalBlock;
import com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable.WeaponWorkshopCraftingTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PasterDreamMod.MOD_ID);

    public static final DeferredBlock<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE)
                    .strength(9f, 5f).lightLevel(state -> 15).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DEEPSLATE)
                    .strength(9f, 5f).lightLevel(state -> 15).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> RAW_TITANIUM_BLOCK = BLOCKS.register("raw_titanium_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.POLISHED_DEEPSLATE)
                    .strength(10f, 6f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW)
                    .sound(SoundType.METAL).strength(11f, 7f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> SOUL_ORE = BLOCKS.register("soul_ore", SoulOreBlock::new);

    public static final DeferredBlock<Block> MOLTEN_GOLD_ORE = BLOCKS.register("molten_gold_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.NETHER_GOLD_ORE)
                    .strength(6f, 3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MOLTEN_GOLD_BLOCK = BLOCKS.register("molten_gold_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD)
                    .sound(SoundType.METAL).strength(7f, 4f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> DYEDREAM_QUARTZ_ORE = BLOCKS.register("dyedream_quartz_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE).strength(3f)));
    public static final DeferredBlock<Block> DYEDREAM_DUST_ORE = BLOCKS.register("dyedream_dust_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.CALCITE)
                    .strength(6f, 3f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> PINK_SLIME_BLOCK = BLOCKS.register("pink_slime_block", () -> new ColoredFallingBlock(new ColorRGBA(0xFF000000 | MapColor.COLOR_PINK.col), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.SLIME_BLOCK).strength(0.2f, 0.1f).friction(0.7f).speedFactor(0.9f).jumpFactor(0.7f).noOcclusion()));

    // ===== 染梦石英建材系列 =====
    public static final DeferredBlock<Block> DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final DeferredBlock<Block> SMOOTH_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("smooth_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final DeferredBlock<Block> BRICKS_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("bricks_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final DeferredBlock<Block> PILLAR_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("pillar_dyedream_quartz_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final DeferredBlock<Block> CHISELED_DYEDREAM_QUARTZ_BLOCK = BLOCKS.register("chiseled_dyedream_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f)));
    public static final DeferredBlock<Block> DYEDREAM_QUARTZ_BLOCK_STAIRS = BLOCKS.register("dyedream_quartz_block_stairs", () -> new StairBlock(DYEDREAM_QUARTZ_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).dynamicShape().noOcclusion()));
    public static final DeferredBlock<Block> DYEDREAM_QUARTZ_BLOCK_SLAB = BLOCKS.register("dyedream_quartz_block_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).dynamicShape()));
    public static final DeferredBlock<Block> DYEDREAM_QUARTZ_BLOCK_WALL = BLOCKS.register("dyedream_quartz_block_wall", () -> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).strength(0.8f).noOcclusion().dynamicShape().forceSolidOn()));

    // ===== 染梦晶芽与合金块 =====
    public static final DeferredBlock<Block> DYEDREAM_BUDDING_BLOCK = BLOCKS.register("dyedream_budding_block", () -> new DyedreamBuddingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1.5f, 1.5f).requiresCorrectToolForDrops().randomTicks()));
    public static final DeferredBlock<Block> SMALL_DYEDREAM_BUD = BLOCKS.register("small_dyedream_bud", () -> new DyedreamClusterBlock(4, 4, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> MEDIUM_DYEDREAM_BUD = BLOCKS.register("medium_dyedream_bud", () -> new DyedreamClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> LARGE_DYEDREAM_BUD = BLOCKS.register("large_dyedream_bud", () -> new DyedreamClusterBlock(9, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).lightLevel(s -> 10).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> DYEDREAM_BUD_BLOCK = BLOCKS.register("dyedream_bud_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.AMETHYST).strength(1f, 0f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DYEDREAM_ALLOY_BLOCK = BLOCKS.register("dyedream_alloy_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.METAL).strength(11f, 7f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> LOST_SWORD_TOMB = BLOCKS.register("lost_sword_tomb", () -> new LostSwordTombBlock());
    public static final DeferredBlock<Block> LIFE_CRYSTAL = BLOCKS.register("life_crystal", () -> new LifeCrystalBlock());
    public static final DeferredBlock<Block> DESERT_HERO_TOMB = BLOCKS.register("desert_hero_tomb", () -> new DesertHeroTombBlock());

    public static final DeferredBlock<Block> QYM_DOLL = BLOCKS.register("qym_doll", () -> new QYMDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).sound(SoundType.WOOD).strength(0F, 2147483647F).noOcclusion()));
    public static final DeferredBlock<Block> UUZ_DOLL = BLOCKS.register("uuz_doll", () -> new UUZDollBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).sound(SoundType.WOOD).strength(0F, 2147483647F).noOcclusion()));

    public static final DeferredBlock<Block> SHADOW = BLOCKS.register("shadow", () -> new ShadowBlock());
    public static final DeferredBlock<Block> THICK_SHADOW = BLOCKS.register("thick_shadow", () -> new ThickShadowBlock());

    public static final DeferredBlock<Block> WEAPON_WORKSHOP_CRAFTING_TABLE = BLOCKS.register("weapon_workshop_crafting_table", () -> new WeaponWorkshopCraftingTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.ANVIL).strength(2f, 10f).requiresCorrectToolForDrops().noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private ModBlocks() {
    }
}
