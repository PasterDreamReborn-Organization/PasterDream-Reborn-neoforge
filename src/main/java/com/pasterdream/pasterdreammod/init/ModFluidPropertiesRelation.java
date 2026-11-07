package com.pasterdream.pasterdreammod.init;

import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FluidDrinkPropertiesRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.GenericFluidDrinkProperties;
import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle.ElixirBottleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

/**
 * 流体饮用属性注册。
 *
 * <p>对应 Forge 1.20.1 源仓库的 {@code init/ModFluidPropertiesRelation.java}。
 */
public final class ModFluidPropertiesRelation {
    public static void register() {
        FluidDrinkPropertiesRegistry.register(ModFluids.MELT_DREAM_LIQUID.get(), new GenericFluidDrinkProperties().drinkAmount(1000).useDuration(32).food(new FoodProperties.Builder().alwaysEdible().build()).meltDreamEnergyAdd(25));

        FluidDrinkPropertiesRegistry.register(ModFluids.RAGE_ELIXIR.get(), new GenericFluidDrinkProperties().drinkAmount(1000).useDuration(32).food(new FoodProperties.Builder().alwaysEdible().build()).onDrinkSpecial(((livingEntity, level) ->
        {
            if (level.isClientSide)
            {
                return;
            }

            boolean applied = false;

            var skillCdAttr = livingEntity.getAttribute(ModAttributes.SKILL_COOLDOWN_REDUCTION);
            ResourceLocation skillId = ResourceLocation.fromNamespaceAndPath("pasterdream", "elixir_bottle_of_rage_elixir_skill_cd_reduction");
            if (skillCdAttr != null && skillCdAttr.getModifier(skillId) == null)
            {
                skillCdAttr.addPermanentModifier(new AttributeModifier(skillId, 0.15, AttributeModifier.Operation.ADD_VALUE));
                applied = true;
            }

            var attackDamageAttr = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
            ResourceLocation attackId = ResourceLocation.fromNamespaceAndPath("pasterdream", "elixir_bottle_of_rage_elixir_attack_damage");
            if (attackDamageAttr != null && attackDamageAttr.getModifier(attackId) == null)
            {
                attackDamageAttr.addPermanentModifier(new AttributeModifier(attackId, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                applied = true;
            }

            if (livingEntity instanceof Player player)
            {
                if (applied)
                {
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSounds.AWAKE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.success"), false);
                }
                    else
                    {
                        player.displayClientMessage(Component.translatable("item.pasterdream.elixir_bottle_of_rage_elixir.client.fail"), false);
                    }
            }
        })));

        FluidDrinkPropertiesRegistry.register(ModFluids.POTION.get(), new GenericFluidDrinkProperties().drinkAmount(250).useDuration(32).food(new FoodProperties.Builder().alwaysEdible().build()).onDrinkSpecial(((livingEntity, level) ->
        {
            FluidStack fluidStack = ElixirBottleItem.getElixirBottleFluidStack(livingEntity.getUseItem());
            List<GenericMobEffect> effectList = PotionHelper.getEffectType(fluidStack);

            for (GenericMobEffect effect : effectList)
            {
                if(fluidStack.getAmount() >= 250)
                {
                    livingEntity.addEffect(new MobEffectInstance(effect.effectType(), effect.time(), effect.level()));
                }
                    else
                    {
                        livingEntity.addEffect(new MobEffectInstance(effect.effectType(), effect.time() * fluidStack.getAmount() / 250, effect.level()));
                    }
            }
        })));
    }

    private ModFluidPropertiesRelation() {
    }
}
