package com.pasterdream.pasterdreammod.world.block.weaponworkshop.craftingtable;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.helper.multiblockproperties.voxelshapecalculator.VoxelShapeCalculator;
import com.pasterdream.pasterdreammod.world.block.horizontaldirectionalblock.blockentity.HorizontalDirectionalBlockBenchBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.Containers.dropItemStack;

public class WeaponWorkshopCraftingTableBlock extends HorizontalDirectionalBlockBenchBaseEntityBlock
{
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public WeaponWorkshopCraftingTableBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected MapCodec<WeaponWorkshopCraftingTableBlock> codec()
    {
        return simpleCodec(WeaponWorkshopCraftingTableBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new WeaponWorkshopCraftingTableBlockEntity(blockPosition, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        List<VoxelShape> ListVoxelShape = VoxelShapeCalculator.calculateAllDirectionVoxelShapeFromEastVoxelShape(-1 / 16.0, 0, -3 / 16.0, 17 / 16.0, 13 / 16.0, 19 / 16.0);
        Direction facing = state.getValue(FACING);
        return switch (facing)
        {
            case EAST  -> ListVoxelShape.get(0);
            case SOUTH -> ListVoxelShape.get(1);
            case WEST  -> ListVoxelShape.get(2);
            case NORTH -> ListVoxelShape.get(3);
            default -> box(0, 0, 0, 16, 16, 16);
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPosition, Player player, BlockHitResult blockHitResult)
    {
        openMenu(level, blockPosition, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        openMenu(level, blockPosition, player);
        return ItemInteractionResult.SUCCESS;
    }

    private static void openMenu(Level level, BlockPos blockPosition, Player player)
    {
        if (!level.isClientSide)
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);
            if (blockEntity instanceof WeaponWorkshopCraftingTableBlockEntity weaponWorkshopCraftingTable)
            {
                ((ServerPlayer) player).openMenu(weaponWorkshopCraftingTable, buffer -> buffer.writeBlockPos(blockPosition));
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        if (!state.is(newState.getBlock()))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WeaponWorkshopCraftingTableBlockEntity weaponWorkshopCraftingTable)
            {
                for(int i = 0; i < 7; i++)
                {
                    dropItemStack(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, weaponWorkshopCraftingTable.getItemHandler().getStackInSlot(i));
                }

                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos blockPosition, Block block, BlockPos fromPosition, boolean isMoving)
    {
        super.neighborChanged(state, level, blockPosition, block, fromPosition, isMoving);

        if (!level.isClientSide)
        {
            boolean wasPowered = state.getValue(POWERED);
            boolean powered = level.hasNeighborSignal(blockPosition);

            if (!wasPowered && powered)
            {
                BlockEntity blockEntity = level.getBlockEntity(blockPosition);
                if (blockEntity instanceof WeaponWorkshopCraftingTableBlockEntity weaponWorkshopCraftingTable)
                {
                    weaponWorkshopCraftingTable.craft(null);
                }
            }

            if (wasPowered != powered)
            {
                level.setBlock(blockPosition, state.setValue(POWERED, powered), 2);
            }
        }
    }
}
