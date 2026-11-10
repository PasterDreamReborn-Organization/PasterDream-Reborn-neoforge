package com.pasterdream.pasterdreammod.world.entity;

import com.pasterdream.pasterdreammod.init.ModEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * 萦风弓强化箭：无重力、命中飘浮、青绿色三棱柱拖尾与飞行时长兜底。
 * <p>
 * 继承 {@link TrailArrowEntity}，从而复用弹药物品同步、特殊箭矢行为钩子与通用拖尾采样，
 * 使萦风弓强化射击特殊箭矢时仍保留其自身特性。
 */
public class FluffyWindArrowEntity extends TrailArrowEntity {

    private static final int HEAD_COLOR = 0xFF3FE8C4;
    private static final int TAIL_COLOR = 0x0032C79E;
    private static final int MAX_FLIGHT_TICKS = 200;
    /** 命中施加的飘浮时长（tick），1 秒 */
    private static final int LEVITATION_DURATION = 20;

    private int flightTicks;
    private boolean channelingLightning;
    private int channelingFlameLevel;

    public FluffyWindArrowEntity(EntityType<? extends FluffyWindArrowEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public FluffyWindArrowEntity(Level level, LivingEntity shooter, ItemStack ammo) {
        this(level, shooter, ammo, null);
    }

    public FluffyWindArrowEntity(Level level, LivingEntity shooter, ItemStack ammo, @Nullable ItemStack weapon) {
        super(ModEntities.FLUFFY_WIND_ARROW.get(), shooter, level, ammo.copyWithCount(1), weapon);
        this.setNoGravity(true);
        this.setAmmoItem(ammo);
        if (shooter instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
        PotionContents contents = ammo.get(DataComponents.POTION_CONTENTS);
        if (contents != null) {
            contents.forEachEffect(this::addEffect);
        }
    }

    public void enableChannelingLightning(int flameLevel) {
        this.channelingLightning = true;
        this.channelingFlameLevel = Math.max(0, flameLevel);
    }

    public boolean hasChannelingLightning() {
        return this.channelingLightning;
    }

    public int channelingFlameLevel() {
        return this.channelingFlameLevel;
    }

    @Override
    public int trailHeadColor() {
        return HEAD_COLOR;
    }

    @Override
    public int trailTailColor() {
        return TAIL_COLOR;
    }

    @Override
    public void tick() {
        // 基类负责客户端拖尾采样与服务端/客户端箭矢行为 tick。
        super.tick();
        if (!this.level().isClientSide && !this.inGround && ++this.flightTicks > MAX_FLIGHT_TICKS) {
            this.discard();
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (!this.level().isClientSide) {
            target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, LEVITATION_DURATION, 0, false, true));
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("WindFlightTicks", this.flightTicks);
        tag.putBoolean("WindChanneling", this.channelingLightning);
        tag.putInt("WindFlame", this.channelingFlameLevel);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.flightTicks = tag.getInt("WindFlightTicks");
        this.channelingLightning = tag.getBoolean("WindChanneling");
        this.channelingFlameLevel = tag.getInt("WindFlame");
    }
}
