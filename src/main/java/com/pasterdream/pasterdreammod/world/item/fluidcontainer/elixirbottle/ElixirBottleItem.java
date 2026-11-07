package com.pasterdream.pasterdreammod.world.item.fluidcontainer.elixirbottle;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FluidDrinkPropertiesRegistry;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.GenericFluidDrinkProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class ElixirBottleItem extends Item
{
    private int capacity = 1000;

    public ElixirBottleItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack)
    {
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        // 装液后不再在名称中拼接流体信息，统一显示紫色「灵药瓶」；流体名称/容量改在 tooltip 展示
        if (!fluidStack.isEmpty())
        {
            return Component.translatable("item.pasterdream.elixir_bottle").withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        return Component.translatable("item.pasterdream.elixir_bottle");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, context, tooltip, flag);

        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        if (fluidStack.isEmpty())
        {
            tooltip.add(Component.translatable("tooltip.pasterdream.空").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("tooltip.pasterdream.总容量:").append("1000 mB").withStyle(ChatFormatting.GRAY));
            return;
        }

        // 非药水流体：显示流体名称；药水流体与效果列表随炼药釜迁入。
        tooltip.add(fluidStack.getDisplayName());

        if (FluidDrinkPropertiesRegistry.getProperties(fluidStack) != null)
        {
            GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);

            tooltip.add(Component.translatable("tooltip.pasterdream.每次饮用:").append(drinkProperties.getDrinkAmount() + "mB").withStyle(ChatFormatting.BLUE));
            FoodValueTooltips.appendSanTooltip(tooltip, drinkProperties.getSanAdd());
            FoodValueTooltips.appendMeltDreamEnergyTooltip(tooltip, drinkProperties.getMeltDreamEnergyAdd());
        }
            else
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.不可饮用").withStyle(ChatFormatting.RED));
            }

        tooltip.add(Component.translatable("pasterdream.tooltip.elixir_capacity", fluidStack.getAmount())
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);

        if (!fluidStack.isEmpty() && FluidDrinkPropertiesRegistry.getProperties(fluidStack) != null)
        {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
            else
            {
                return InteractionResultHolder.fail(itemStack);
            }
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity entity)
    {
        FluidStack fluidStack = getElixirBottleFluidStack(itemStack);
        GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);

        int time = drinkProperties != null ? drinkProperties.getUseDuration() : 32;
        int drinkAmount = drinkProperties != null ? drinkProperties.getDrinkAmount() : 250;

        if(fluidStack.getAmount() >= drinkAmount)
        {
            return time;
        }
            else
            {
                return Math.max(1,  time * fluidStack.getAmount() / drinkAmount);
            }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack)
    {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity)
    {
        if (!level.isClientSide && entity instanceof Player player)
        {
            FluidStack fluidStack = getElixirBottleFluidStack(itemStack);
            GenericFluidDrinkProperties drinkProperties = FluidDrinkPropertiesRegistry.getProperties(fluidStack);
            if (drinkProperties != null)
            {
                int drinkAmount = drinkProperties.getDrinkAmount();
                int fluidAmountInElixirBottle = fluidStack.getAmount();

                if (!player.isCreative())
                {
                    int drainAmount = Math.min(fluidAmountInElixirBottle, drinkAmount);
                    if (drainAmount > 0)
                    {
                        IFluidHandler handler = itemStack.getCapability(Capabilities.FluidHandler.ITEM);
                        if (handler != null) handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                    }
                }

                FoodProperties food = drinkProperties.getFoodProperties();
                if (food != null && food.nutrition() > 0)
                {
                    int nutrition = food.nutrition() * Math.min(fluidAmountInElixirBottle, drinkAmount) / drinkAmount;
                    player.getFoodData().eat(nutrition, food.saturation() / (food.nutrition() * 2.0F));
                }

                for (MobEffectInstance effect : drinkProperties.getEffects())
                {
                    if (fluidAmountInElixirBottle >= drinkAmount)
                    {
                        entity.addEffect(new MobEffectInstance(effect));
                    }
                        else
                        {
                            entity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration() * fluidAmountInElixirBottle / drinkAmount, effect.getAmplifier()));
                        }
                }

                if (player instanceof ServerPlayer serverPlayer)
                {
                    if (drinkProperties.getSanAdd() != 0)
                    {
                        if (fluidAmountInElixirBottle >= drinkAmount)
                        {
                            SanHelper.addPlayerSanAndSync(serverPlayer, drinkProperties.getSanAdd());
                        }
                            else
                            {
                                SanHelper.addPlayerSanAndSync(serverPlayer, drinkProperties.getSanAdd() * fluidAmountInElixirBottle / drinkAmount);
                            }
                    }

                    if (drinkProperties.getMeltDreamEnergyAdd() != 0)
                    {
                        if (fluidAmountInElixirBottle >= drinkAmount)
                        {
                            MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, drinkProperties.getMeltDreamEnergyAdd());
                        }
                            else
                            {
                                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(serverPlayer, drinkProperties.getMeltDreamEnergyAdd() * fluidAmountInElixirBottle / drinkAmount);
                            }
                    }
                }

                drinkProperties.getDrinkSpecial().accept(entity, level);
            }
        }
        return itemStack;
    }

    public static FluidStack getElixirBottleFluidStack(ItemStack itemStack)
    {
        IFluidHandler handler = itemStack.getCapability(Capabilities.FluidHandler.ITEM);
        return handler != null ? handler.getFluidInTank(0) : FluidStack.EMPTY;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return ElixirBottleRenderer.getInstance();
            }
        });
    }
}
