package com.pasterdream.pasterdreammod.world.block.shadowhandtrap;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShadowHandTrapBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ShadowHandTrapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHADOW_HAND_TRAP.get(), pos, state);
    }

    public void onServerTick() {
        // 方块实体的服务端 tick 逻辑（粒子、检测等可在此添加）
    }

    private int getAnimationValue() {
        if (level == null) return 0;
        BlockState bs = getBlockState();
        if (bs.getBlock() instanceof ShadowHandTrapBlock && bs.hasProperty(ShadowHandTrapBlock.ANIMATION))
            return bs.getValue(ShadowHandTrapBlock.ANIMATION);
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::idlePredicate));
        data.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    private PlayState idlePredicate(software.bernie.geckolib.animation.AnimationState<ShadowHandTrapBlockEntity> event) {
        if (getAnimationValue() == 0) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(software.bernie.geckolib.animation.AnimationState<ShadowHandTrapBlockEntity> event) {
        int anim = getAnimationValue();
        if (anim != 0 && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(String.valueOf(anim)));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                if (getBlockState().hasProperty(ShadowHandTrapBlock.ANIMATION))
                    level.setBlock(worldPosition, getBlockState().setValue(ShadowHandTrapBlock.ANIMATION, 0), 3);
                event.getController().forceAnimationReset();
            }
        } else if (anim == 0) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
