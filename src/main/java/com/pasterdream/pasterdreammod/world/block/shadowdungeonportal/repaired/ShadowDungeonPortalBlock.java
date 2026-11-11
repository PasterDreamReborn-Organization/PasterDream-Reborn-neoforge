package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.repaired;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShadowDungeonPortalBlock extends BaseEntityBlock
{
    public ShadowDungeonPortalBlock()
    {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE).strength(-1, 3600000).noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return simpleCodec(properties -> new ShadowDungeonPortalBlock());
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ShadowDungeonPortalTileEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(3, 3, 3, 13, 13, 13);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type)
    {
        return type == ModBlockEntities.SHADOW_DUNGEON_PORTAL.get() ? (blockLevel, blockPos, state, blockEntity) -> ShadowDungeonPortalTileEntity.tick(blockLevel, blockPos, (ShadowDungeonPortalTileEntity)blockEntity) : null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState blockState, Level level, BlockPos blockPosition, Player player, InteractionHand interactionHand, BlockHitResult hitResult)
    {
        activate(level, blockPosition, player);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPosition, Player player, BlockHitResult hitResult)
    {
        activate(level, blockPosition, player);
        return InteractionResult.SUCCESS;
    }

    private void activate(Level level, BlockPos blockPosition, Player player)
    {
        if (!level.isClientSide && level.getBlockEntity(blockPosition) instanceof ShadowDungeonPortalTileEntity ShadowDungeonPortal)
        {
            ShadowDungeonPortal.activeShadowDungeonPortal(blockPosition, player);
        }
    }
}
