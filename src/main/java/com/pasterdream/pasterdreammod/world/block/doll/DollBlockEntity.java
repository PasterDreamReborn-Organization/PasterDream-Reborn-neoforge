package com.pasterdream.pasterdreammod.world.block.doll;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class DollBlockEntity extends BlockEntity implements GeoBlockEntity
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;

    public DollBlockEntity(BlockEntityType<?> type, BlockPos blockPosition, BlockState state, String nameKey)
    {
        super(type, blockPosition, state);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "state", 0, this::stateController));
    }

    private PlayState stateController(AnimationState<DollBlockEntity> state)
    {
        AnimationController<DollBlockEntity> controller = state.getController();

        if(animationState == 0)
        {
            controller.setAnimation(RawAnimation.begin().thenLoop("0"));
        }
        else
        {
            controller.setAnimation(RawAnimation.begin().thenPlay("1"));
            if(controller.getAnimationState() == AnimationController.State.STOPPED)
            {
                animationState = 0;
            }
        }

        return PlayState.CONTINUE;
    }

    public void setAnimationState(int state)
    {
        this.animationState = state;
        if (level != null && !level.isClientSide)
        {
            sendAnimationSync();
        }
    }

    private void sendAnimationSync()
    {
        level.blockEvent(worldPosition, getBlockState().getBlock(), 1, animationState);
    }

    @Override
    public boolean triggerEvent(int id, int parameter)
    {
        if (id != 1) return super.triggerEvent(id, parameter);
        animationState = parameter;
        return true;
    }

    public int getAnimationState()
    {
        return animationState;
    }
}
