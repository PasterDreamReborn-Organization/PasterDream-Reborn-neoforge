package com.pasterdream.pasterdreammod.world.block.itemcontainer.openedmeltdreamcrystalchest;

import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.block.itemcontainer.ItemContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class OpenedMeltDreamCrystalChestBlock extends ItemContainerBlock
{
    public OpenedMeltDreamCrystalChestBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected com.mojang.serialization.MapCodec<OpenedMeltDreamCrystalChestBlock> codec()
    {
        return simpleCodec(OpenedMeltDreamCrystalChestBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPosition, BlockState blockState)
    {
        return new OpenedMeltDreamCrystalChestBlockEntity(blockPosition, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (stack.is(ModItems.MELT_DREAM_CRYSTAL_CHEST_RESET_TOOL.get()))
        {
            stack.getItem().useOn(new UseOnContext(player, hand, hitResult));
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        return box(0, 0, 0, 16, 15, 16);
    }
}
