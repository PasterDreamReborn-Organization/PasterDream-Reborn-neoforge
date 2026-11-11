package com.pasterdream.pasterdreammod.world.block.shadowbrazier;

import com.mojang.serialization.MapCodec;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShadowBrazierBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 2);
    private static final VoxelShape SHAPE = box(1, 1, 1, 15, 5, 15);

    public ShadowBrazierBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.STONE)
                .strength(-1, 3600000)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(properties -> new ShadowBrazierBlock());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowBrazierBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(state, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ShadowBrazierBlockEntity brazier) {
            brazier.onServerTick();
        }
        world.scheduleTick(pos, this, 20);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = player.getMainHandItem().is(ModBlocks.SHADOW_CANDLE.get().asItem())
                ? InteractionHand.MAIN_HAND
                : player.getOffhandItem().is(ModBlocks.SHADOW_CANDLE.get().asItem())
                ? InteractionHand.OFF_HAND
                : null;
        if (hand != null) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ShadowBrazierBlockEntity brazier) {
                if (!brazier.isLit()) {
                    brazier.ignite();
                    world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1, 1);
                    if (!world.isClientSide()) {
                        player.displayClientMessage(
                                Component.translatable("message.pasterdream.shadow_brazier.lit"), false);
                        player.displayClientMessage(
                                Component.translatable("message.pasterdream.shadow_brazier.shadow_spread"), false);
                    }
                    player.swing(hand, true);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (!world.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("message.pasterdream.shadow_brazier.need_candle"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
