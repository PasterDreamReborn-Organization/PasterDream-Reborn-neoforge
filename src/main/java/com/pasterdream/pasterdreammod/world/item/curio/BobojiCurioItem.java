package com.pasterdream.pasterdreammod.world.item.curio;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModAttributes;
import com.pasterdream.pasterdreammod.init.ModEffects;
import com.pasterdream.pasterdreammod.init.ModParticleTypes;
import com.pasterdream.pasterdreammod.world.item.ModRarities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BobojiCurioItem extends Item implements ICurioItem {
    private static final ResourceLocation BLINK_CD_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "boboji_blink_cooldown");
    private static final ResourceLocation BLINK_RANGE_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "boboji_blink_range");
    private static final ResourceLocation BLINK_CONSUME_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "boboji_blink_consume");
    private static final ResourceLocation MOVEMENT_SPEED_ID = ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "boboji_movement_speed");
    private static final String TAG_LAST_BLINK = "bobojiLastBlink";

    public BobojiCurioItem() {
        super(new Item.Properties().stacksTo(1).rarity(ModRarities.legendary()));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCd != null && blinkCd.getModifier(BLINK_CD_ID) == null) {
                blinkCd.addPermanentModifier(new AttributeModifier(BLINK_CD_ID,
                        0.2, AttributeModifier.Operation.ADD_VALUE));
            }
            AttributeInstance blinkRange = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE);
            if (blinkRange != null && blinkRange.getModifier(BLINK_RANGE_ID) == null) {
                blinkRange.addPermanentModifier(new AttributeModifier(BLINK_RANGE_ID,
                        0.1, AttributeModifier.Operation.ADD_VALUE));
            }
            AttributeInstance blinkConsume = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME);
            if (blinkConsume != null && blinkConsume.getModifier(BLINK_CONSUME_ID) == null) {
                blinkConsume.addPermanentModifier(new AttributeModifier(BLINK_CONSUME_ID,
                        -0.4, AttributeModifier.Operation.ADD_VALUE));
            }
            AttributeInstance moveSpeed = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (moveSpeed != null && moveSpeed.getModifier(MOVEMENT_SPEED_ID) == null) {
                moveSpeed.addPermanentModifier(new AttributeModifier(MOVEMENT_SPEED_ID,
                        0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() != null) {
            AttributeInstance blinkCd = slotContext.entity().getAttribute(ModAttributes.BLINK_COOLDOWN_REDUCTION);
            if (blinkCd != null) blinkCd.removeModifier(BLINK_CD_ID);
            AttributeInstance blinkRange = slotContext.entity().getAttribute(ModAttributes.BLINK_RANGE);
            if (blinkRange != null) blinkRange.removeModifier(BLINK_RANGE_ID);
            AttributeInstance blinkConsume = slotContext.entity().getAttribute(ModAttributes.BLINK_CONSUME);
            if (blinkConsume != null) blinkConsume.removeModifier(BLINK_CONSUME_ID);
            AttributeInstance moveSpeed = slotContext.entity().getAttribute(Attributes.MOVEMENT_SPEED);
            if (moveSpeed != null) moveSpeed.removeModifier(MOVEMENT_SPEED_ID);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide()) return;

        var entity = slotContext.entity();
        var evasion = entity.getEffect(ModEffects.EVASION);

        // 识别刚触发的瞬身术：仅匹配 BlinkSkill 原始的 6 tick 回避
        if (evasion != null && evasion.getDuration() == 6) {
            long now = entity.level().getGameTime();
            long lastBlink = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                    .copyTag().getLong(TAG_LAST_BLINK);
            if (now - lastBlink > 10) {
                CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putLong(TAG_LAST_BLINK, now));
                onBlink(level, entity);
            }
        }

        // 回避期间生成粒子拖尾
        if (evasion != null && level instanceof ServerLevel sl) {
            double x = entity.getX();
            double y = entity.getY() + 1.0;
            double z = entity.getZ();
            sl.sendParticles(ModParticleTypes.SPORE_PARTICLE.get(), x, y, z, 4, 0.4, 0.8, 0.4, 0.1);
            sl.sendParticles(ModParticleTypes.DUST_0_PARTICLE.get(), x, y, z, 4, 0.4, 0.8, 0.4, 0.1);
        }
    }

    private void onBlink(Level level, net.minecraft.world.entity.LivingEntity entity) {
        level.playSound(null, entity.blockPosition(), SoundEvents.PARROT_FLY, SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.playSound(null, entity.blockPosition(), SoundEvents.PARROT_AMBIENT, SoundSource.NEUTRAL, 1.0F, 1.0F);

        Vec3 center = entity.position();
        List<Player> nearby = level.getEntitiesOfClass(Player.class,
                AABB.ofSize(center, 15, 15, 15), e -> true);
        if (!nearby.isEmpty()) {
            for (Player p : nearby) {
                p.addEffect(new MobEffectInstance(ModEffects.EVASION, 10, 0, false, false));
            }
        } else {
            entity.addEffect(new MobEffectInstance(ModEffects.EVASION, 5, 0, false, false));
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        // 读档时 Curios 会把槽里那一件重新校验一遍，判重必须放行自身槽位；详见 CurioEquipHelper。
        return CurioEquipHelper.allowsSingleCopy(slotContext, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.speed"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.range"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.cd"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.stat.consume"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.elytra"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.evasion"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.share"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.effect.flavor"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.lore"));
        list.add(Component.translatable("tooltip.pasterdream.boboji_curio.dedication"));
    }
}
