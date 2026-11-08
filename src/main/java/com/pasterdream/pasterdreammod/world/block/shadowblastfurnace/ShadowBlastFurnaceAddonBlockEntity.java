package com.pasterdream.pasterdreammod.world.block.shadowblastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShadowBlastFurnaceAddonBlockEntity extends BlockEntity
{
    public ShadowBlastFurnaceAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHADOW_BLAST_FURNACE_ADDON.get(), pos, state);
    }

    @Nullable
    public ShadowBlastFurnaceBlockEntity getMainBlockEntity()
    {
        if (level == null)
        {
            return null;
        }

        BlockPos mainPosition = _3x3x3_CalculatePartPosition.getMainPosFromAddon(getBlockPos(), getBlockState().getValue(ShadowBlastFurnaceBlock.FACING), getBlockState().getValue(ShadowBlastFurnaceBlock.PART));
        BlockEntity main = level.getBlockEntity(mainPosition);

        return main instanceof ShadowBlastFurnaceBlockEntity shadowBlastFurnace ? shadowBlastFurnace : null;
    }
}
