package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModBlocks;
import com.pasterdream.pasterdreammod.init.ModEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModRecipes;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import com.pasterdream.pasterdreammod.recipe.GoldenFoxTradeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GoldenFoxEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(GoldenFoxEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(GoldenFoxEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    public GoldenFoxEntity(EntityType<GoldenFoxEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "gloden_fox_light");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Level world = this.level();

        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        // Turn the wild fox into a pet when offered a legendary dragon horn ice cream
        if (itemstack.is(ModItems.LEGENDARY_DRAGON_HORN_ICE_CREAM.get())) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            GoldenFoxPetEntity pet = ModEntities.GOLDEN_FOX_PET.get().create(world);
            if (pet != null) {
                pet.moveTo(x, y, z, this.getYRot(), this.getXRot());
                pet.setTame(true, true);
                pet.setOwnerUUID(player.getUUID());
                pet.setPersistenceRequired();
                world.addFreshEntity(pet);
            }

            if (!player.getAbilities().instabuild)
                itemstack.shrink(1);

            if (world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HEART,
                        x, y + 0.5, z, 8, 0.4, 0.4, 0.4, 0.05);
            }
            this.playSound(SoundEvents.FOX_AMBIENT, 1f, 1.2f);
            player.displayClientMessage(Component.translatable("entity.pasterdream.golden_fox.tamed"), true);
            this.discard();
            return InteractionResult.SUCCESS;
        }

        // Look up data-driven trade recipe
        GoldenFoxTradeRecipe trade = null;
        for (RecipeHolder<GoldenFoxTradeRecipe> holder : world.getRecipeManager().getAllRecipesFor(ModRecipes.GOLDEN_FOX_TRADE.get())) {
            GoldenFoxTradeRecipe recipe = holder.value();
            if (recipe.matches(itemstack)) {
                trade = recipe;
                break;
            }
        }

        if (trade == null)
            return InteractionResult.PASS;

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        if (!player.getAbilities().instabuild)
            itemstack.shrink(1);

        // Particles and sound
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD, x, y, z, 12, 0.6, 0.6, 0.6, 0.1);
        }
        this.playSound(SoundEvents.FOX_AMBIENT, 1f, 1f);

        // Place golden_fox_sculpture facing player
        world.setBlock(BlockPos.containing(x, y, z),
                ModBlocks.GOLDEN_FOX_SCULPTURE.get().defaultBlockState()
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, player.getDirection().getOpposite()), 3);

        // Drop trade result + fox_sculpture as bonus
        if (world instanceof ServerLevel serverLevel) {
            ItemStack tradeResult = trade.getResultItem(world.registryAccess());
            ItemEntity tradeDrop = new ItemEntity(serverLevel, x, y + 0.5, z, tradeResult);
            tradeDrop.setPickUpDelay(10);
            serverLevel.addFreshEntity(tradeDrop);

            ItemEntity sculptureDrop = new ItemEntity(serverLevel, x, y + 0.5, z,
                    new ItemStack(ModBlocks.FOX_SCULPTURE.get()));
            sculptureDrop.setPickUpDelay(10);
            serverLevel.addFreshEntity(sculptureDrop);
        }

        player.displayClientMessage(Component.translatable("entity.pasterdream.golden_fox.vanish"), false);
        this.discard();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 8);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }

    private PlayState movementPredicate(AnimationState<GoldenFoxEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<GoldenFoxEntity> event) {
        if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("empty")) {
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            this.remove(GoldenFoxEntity.RemovalReason.KILLED);
            this.dropExperience(this.lastHurtByPlayer);
        }
    }

    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ANIMATION.equals(key)) {
            this.animationprocedure = this.entityData.get(ANIMATION);
        }
    }

    public void setAnimation(String animation) {
        this.animationprocedure = animation;
        this.entityData.set(ANIMATION, animation);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
