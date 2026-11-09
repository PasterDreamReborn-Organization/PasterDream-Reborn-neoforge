package com.pasterdream.pasterdreammod.world.block.weaponworkshop.blastfurnace;

import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._2x4x2_CalculatePartPosition;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WeaponWorkshopBlastFurnaceAddonBlockEntity extends BlockEntity
{
    public WeaponWorkshopBlastFurnaceAddonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.WEAPON_WORKSHOP_BLAST_FURNACE_ADDON.get(), pos, state);
    }

    @Nullable
    public WeaponWorkshopBlastFurnaceBlockEntity getMainBlockEntity()
    {
        if (level == null)
        {
            return null;
        }

        BlockPos mainPosition = _2x4x2_CalculatePartPosition.getMainPosFromAddon(getBlockPos(), getBlockState().getValue(WeaponWorkshopBlastFurnaceBlock.FACING), getBlockState().getValue(WeaponWorkshopBlastFurnaceBlock.PART));
        BlockEntity main = level.getBlockEntity(mainPosition);

        return main instanceof WeaponWorkshopBlastFurnaceBlockEntity weaponWorkshopBlastFurnace ? weaponWorkshopBlastFurnace : null;
    }
}
