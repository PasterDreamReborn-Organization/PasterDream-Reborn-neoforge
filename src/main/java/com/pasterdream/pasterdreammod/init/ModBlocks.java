package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private ModBlocks() {
    }
}
