package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalGeckolibBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class TheEndlessBookOfDreamSeekersBlock extends HorizontalDirectionalGeckolibBaseEntityBlock
{
    public TheEndlessBookOfDreamSeekersBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected com.mojang.serialization.MapCodec<TheEndlessBookOfDreamSeekersBlock> codec()
    {
        return simpleCodec(TheEndlessBookOfDreamSeekersBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new TheEndlessBookOfDreamSeekersBlockEntity(blockPosition, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPosition, Player player, BlockHitResult blockHitResult)
    {
        triggerUse(level, blockPosition);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        triggerUse(level, blockPosition);
        return ItemInteractionResult.SUCCESS;
    }

    private static void triggerUse(Level level, BlockPos blockPosition)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof TheEndlessBookOfDreamSeekersBlockEntity theEndlessBookOfDreamSeekersBlockEntity)
            {
                ItemStackHandler handler = theEndlessBookOfDreamSeekersBlockEntity.getItemHandler();
                ItemStack stackInSlot = handler.getStackInSlot(0);
                if (!stackInSlot.isEmpty())
                {
                    theEndlessBookOfDreamSeekersBlockEntity.setAnimationState(1);
                    theEndlessBookOfDreamSeekersBlockEntity.triggerUseParticles(stackInSlot.copy());
                }
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return type == ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get()
            ? (level.isClientSide ? null : (lvl, pos, state, be) -> ((TheEndlessBookOfDreamSeekersBlockEntity) be).serverTick(lvl, pos))
            : null;
    }
}

