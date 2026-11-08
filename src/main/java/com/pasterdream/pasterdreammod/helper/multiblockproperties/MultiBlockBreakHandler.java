package com.pasterdream.pasterdreammod.helper.multiblockproperties;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.calculatemainposition._3x3x3_CalculatePartPosition;
import com.pasterdream.pasterdreammod.world.block.shadowblastfurnace.ShadowBlastFurnaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public class MultiBlockBreakHandler
{
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        if (!(state.getBlock() instanceof ShadowBlastFurnaceBlock))
        {
            return;
        }

        Direction facing = state.getValue(ShadowBlastFurnaceBlock.FACING);
        String partName = state.getValue(ShadowBlastFurnaceBlock.PART).getSerializedName();
        boolean isMain = partName.equals("main");

        if (isMain)
        {
            return;
        }

        event.setCanceled(true);

        BlockPos mainPos = _3x3x3_CalculatePartPosition.getMainPosFromAddon(pos, facing, state.getValue(ShadowBlastFurnaceBlock.PART));
        BlockState mainState = level.getBlockState(mainPos);
        if (mainState.getBlock() == state.getBlock())
        {
            level.destroyBlock(mainPos, !player.isCreative());
        }
    }
}
