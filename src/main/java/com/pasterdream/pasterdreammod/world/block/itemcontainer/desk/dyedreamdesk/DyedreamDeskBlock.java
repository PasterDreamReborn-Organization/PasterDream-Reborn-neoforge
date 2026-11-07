package com.pasterdream.pasterdreammod.world.block.itemcontainer.desk.dyedreamdesk;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;

public class DyedreamDeskBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public DyedreamDeskBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<DyedreamDeskBlock> codec()
    {
        return simpleCodec(DyedreamDeskBlock::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(2, 0, 2, 14, 12, 14);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new DyedreamDeskBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        openMenu(level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        openMenu(level, pos, player);
        return ItemInteractionResult.SUCCESS;
    }

    private static void openMenu(Level level, BlockPos pos, Player player)
    {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof DyedreamDeskBlockEntity desk)
        {
            desk.unpackLootTable(player);
            ((ServerPlayer) player).openMenu(desk, buffer -> buffer.writeBlockPos(pos));
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved)
    {
        if (!state.is(newState.getBlock()))
        {
            if (level.getBlockEntity(pos) instanceof DyedreamDeskBlockEntity desk)
            {
                ItemStack stack = desk.getItemHandler().getStackInSlot(0);
                if (!stack.isEmpty())
                    Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }
}
