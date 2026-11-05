package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.world.block.doll.DollBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UUZDollBlock extends DollBlock
{
    public UUZDollBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<UUZDollBlock> codec()
    {
        return simpleCodec(UUZDollBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new UUZDollBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.UUZ_DOLL.get(), UUZDollBlockEntity::serverTick);
    }
}
