package com.pasterdream.pasterdreammod.world.item;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.List;

public class ThermalDaggerItem extends SwordItem {
    private static final ResourceLocation SWIM_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "thermal_dagger_swim_speed");

    public ThermalDaggerItem(Properties properties) {
        super(ModToolTiers.THERMAL_DAGGER, withSwimSpeed(properties.fireResistant()));
    }

    private static Properties withSwimSpeed(Properties properties) {
        ItemAttributeModifiers base = SwordItem.createAttributes(ModToolTiers.THERMAL_DAGGER, 3, -2.3f);
        AttributeModifier swimSpeed = new AttributeModifier(SWIM_SPEED_ID, 0.5,
                AttributeModifier.Operation.ADD_VALUE);
        return properties.attributes(base
                .withModifierAdded(NeoForgeMod.SWIM_SPEED, swimSpeed, EquipmentSlotGroup.MAINHAND)
                .withModifierAdded(NeoForgeMod.SWIM_SPEED, swimSpeed, EquipmentSlotGroup.OFFHAND));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean retval = super.hurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, BlockPos.containing(target.getX(), target.getY(), target.getZ()),
                    ModSounds.THERMAL_DAGGER.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(),
                    64, 1.2, 1.5, 1.2, 0.15);
        }
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.pasterdream.thermal_dagger.swim"));
        tooltip.add(Component.translatable("tooltip.pasterdream.thermal_dagger"));
    }
}
