package com.pasterdream.pasterdreammod.world.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class StrippableRotatedPillarBlock extends RotatedPillarBlock {
    private final Supplier<Block> strippedBlock;

    public StrippableRotatedPillarBlock(Properties properties, Supplier<Block> strippedBlock) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            return strippedBlock.get().withPropertiesOf(state);
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
