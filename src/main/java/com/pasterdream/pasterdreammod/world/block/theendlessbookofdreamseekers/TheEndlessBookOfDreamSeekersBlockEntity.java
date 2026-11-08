package com.pasterdream.pasterdreammod.world.block.theendlessbookofdreamseekers;

import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TheEndlessBookOfDreamSeekersBlockEntity extends BlockEntity implements GeoBlockEntity
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int animationState = 0;
    private int tickCount = 0;
    private int useParticleTick = 0;
    private ItemStack pendingDropItem = ItemStack.EMPTY;

    public TheEndlessBookOfDreamSeekersBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.THE_ENDLESS_BOOK_OF_DREAM_SEEKERS.get(), pos, state);
    }

    public void serverTick(Level level, BlockPos pos)
    {
        tickCount++;
        if (tickCount >= 10)
        {
            tickCount = 0;
            if (level instanceof ServerLevel serverLevel)
            {
                serverLevel.sendParticles(ParticleTypes.ENCHANT,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    6, 0.4, 0.4, 0.4, 1);
            }
        }

        if (useParticleTick > 0 && level instanceof ServerLevel serverLevel)
        {
            double px = pos.getX() + 0.5;
            double py = pos.getY() + 0.4;
            double pz = pos.getZ() + 0.5;
            int tick = useParticleTick;

            if (tick == 1)
            {
                level.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, px, py, pz, 16, 1, 1, 1, 0.5);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), px, py, pz, 16, 1, 1, 1, 0.1);
            }
            else if (tick == 10)
            {
                level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, px, py, pz, 16, 1, 1, 1, 0.5);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), px, py, pz, 16, 1, 1, 1, 0.1);
            }
            else if (tick == 20)
            {
                if (!pendingDropItem.isEmpty())
                {
                    Block.popResource(level, pos, pendingDropItem);
                    pendingDropItem = ItemStack.EMPTY;
                }
                serverLevel.sendParticles(ParticleTypes.ENCHANT, px, py, pz, 16, 1, 1, 1, 0.5);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), px, py, pz, 16, 1, 1, 1, 0.1);
            }
            else if (tick == 30)
            {
                level.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                serverLevel.sendParticles(ParticleTypes.ENCHANT, px, py, pz, 16, 1, 1, 1, 0.5);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), px, py, pz, 16, 1, 1, 1, 0.1);
            }
            else if (tick == 40)
            {
                serverLevel.sendParticles(ParticleTypes.ENCHANT, px, py, pz, 16, 1, 1, 1, 0.5);
                serverLevel.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), px, py, pz, 16, 1, 1, 1, 0.1);
            }

            useParticleTick++;
            if (useParticleTick > 40)
                useParticleTick = 0;
        }
    }

    public void triggerUseParticles(ItemStack dropStack)
    {
        this.pendingDropItem = dropStack;
        this.useParticleTick = 1;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slotIndex)
        {
            setChangedAndSync();
        }

        @Override
        public boolean isItemValid(int slotIndex, ItemStack stack)
        {
            return true;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
    };

    private void setChangedAndSync()
    {
        setChanged();
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.putInt("AnimationState", animationState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        animationState = tag.getInt("AnimationState");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("AnimationState", animationState);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
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

    private PlayState stateController(AnimationState<TheEndlessBookOfDreamSeekersBlockEntity> state)
    {
        AnimationController<TheEndlessBookOfDreamSeekersBlockEntity> controller = state.getController();

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
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public int getAnimationState()
    {
        return animationState;
    }

    public ItemStackHandler getItemHandler()
    {
        return itemHandler;
    }
}
