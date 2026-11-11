package com.pasterdream.pasterdreammod.world.block.researchtable;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ResearchTableAddonBlockEntity extends BlockEntity
{
    public ResearchTableAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RESEARCH_TABLE_ADDON.get(), pos, state);
    }

    @Nullable
    public ResearchTableBlockEntity getMainBlockEntity()
    {
        BlockPos mainPosition = getBlockPos().relative(getBlockState().getValue(ResearchTableBlock.FACING).getClockWise());
        BlockEntity main = level.getBlockEntity(mainPosition);
        return main instanceof ResearchTableBlockEntity researchTable ? researchTable : null;
    }
}
