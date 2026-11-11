package com.pasterdream.pasterdreammod.world.block.shadowdungeonportal.broken;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BrokenShadowDungeonPortalTileEntity extends BlockEntity implements GeoBlockEntity, AnimatableSync
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;
    private int tickCounter = 0;

    public BrokenShadowDungeonPortalTileEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.BROKEN_SHADOW_DUNGEON_PORTAL.get(), pos, state);
    }

    public void repair()
    {
        if (animationState == 1)
        {
            return;
        }

        Level level = this.level;
        if (level == null || level.isClientSide)
        {
            return;
        }

        animationState = 1;
        setChangedAndSync();
    }

    public static void tick(Level level, BlockPos blockPosition, BrokenShadowDungeonPortalTileEntity blockEntity)
    {
        if (level.isClientSide)
        {
            return;
        }

        if(blockEntity.animationState == 1)
        {
            if(blockEntity.tickCounter >= 20)
            {
                level.setBlock(blockPosition, ModBlocks.SHADOW_DUNGEON_PORTAL.get().defaultBlockState(), 3);
            }
            blockEntity.tickCounter++;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "state", 0, this::stateController));
    }

    private void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            sendAnimationSync();
        }
    }

    private PlayState stateController(AnimationState<BrokenShadowDungeonPortalTileEntity> state)
    {
        AnimationController<BrokenShadowDungeonPortalTileEntity> controller = state.getController();

        if(animationState == 0)
        {
            controller.setAnimation(RawAnimation.begin().thenLoop("0"));
        }
        else
        {
            controller.setAnimation(RawAnimation.begin().thenPlay(String.valueOf(animationState)));
        }

        return PlayState.CONTINUE;
    }

    public void setAnimationState(int state)
    {
        animationState = state;
        if (level != null && !level.isClientSide)
        {
            sendAnimationSync();
        }
    }

    private void sendAnimationSync()
    {
        AnimationStateChangePacket packet = new AnimationStateChangePacket(worldPosition, animationState);
        if (level instanceof ServerLevel serverLevel)
        {
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(worldPosition), packet);
        }
    }

    public int getAnimationState()
    {
        return animationState;
    }
}
