package com.pasterdream.pasterdreammod.world.block.fireflyglassjar;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.network.animationstatechange.AnimationStateChangePacket;
import com.pasterdream.pasterdreammod.world.block.geckolibblock.AnimatableSync;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FireflyGlassJarBlockEntity extends BlockEntity implements GeoBlockEntity, AnimatableSync {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;
    private boolean animationStarted = false;

    public FireflyGlassJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIREFLY_GLASS_JAR.get(), pos, state);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "procedure", 0, this::procedureController));
    }

    private PlayState procedureController(AnimationState<FireflyGlassJarBlockEntity> state) {
        var controller = state.getController();
        if (animationState == 0) {
            return PlayState.STOP;
        }
        if (!animationStarted) {
            controller.forceAnimationReset();
            controller.setAnimation(RawAnimation.begin().thenPlay(String.valueOf(animationState)));
            animationStarted = true;
            return PlayState.CONTINUE;
        }
        if (controller.getAnimationState() == AnimationController.State.STOPPED) {
            animationStarted = false;
            animationState = 0;
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void setAnimationState(int state) {
        this.animationState = state;
        this.animationStarted = false;
        if (level != null && !level.isClientSide) {
            AnimationStateChangePacket packet = new AnimationStateChangePacket(this.worldPosition, this.animationState);
            PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(this.worldPosition), packet);
        }
    }

    @Override
    public int getAnimationState() {
        return animationState;
    }
}
