package com.pasterdream.pasterdreammod.world.item.drinkandfooditem;

import com.pasterdream.pasterdreammod.capability.meltdreamenergy.MeltDreamEnergyHelper;
import com.pasterdream.pasterdreammod.capability.san.SanHelper;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.FoodValueTooltips;
import com.pasterdream.pasterdreammod.helper.drinkandfoodproperties.PasterDreamDrinkAndFoodProperties;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.FluidContainerRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;

public class PasterDreamDrinkItem extends Item
{
    public final int useDuration;
    public final double sanAdd;
    public final double meltDreamEnergyAdd;

    public PasterDreamDrinkItem(PasterDreamDrinkAndFoodProperties properties)
    {
        super(properties);
        this.useDuration = properties.getUseDuration();
        this.sanAdd = properties.getSanAdd();
        this.meltDreamEnergyAdd = properties.getMeltDreamEnergyAdd();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, context, tooltip, flag);

        FoodValueTooltips.appendSanTooltip(tooltip, sanAdd);
        FoodValueTooltips.appendMeltDreamEnergyTooltip(tooltip, meltDreamEnergyAdd);

        FoodProperties food = itemStack.getFoodProperties(null);
        if (food != null)
        {
            for (var effectPair : food.effects())
            {
                MobEffectInstance instance = effectPair.effect();
                float probability = effectPair.probability();
                Component text = Component.translatable(instance.getDescriptionId());
                int amp = instance.getAmplifier();
                if (amp > 0)
                {
                    text = Component.translatable("potion.withAmplifier", text,
                            Component.translatable("potion.potency." + amp));
                }
                if (!instance.endsWithin(20))
                {
                    text = Component.translatable("potion.withDuration", text,
                            Component.literal(formatTickDuration(instance.getDuration())));
                }
                if (probability < 1.0f)
                {
                    text = Component.literal((int)(probability * 100) + "% ").append(text);
                }
                tooltip.add(text.copy().withStyle(instance.getEffect().value().getCategory().getTooltipFormatting()));
            }
        }
    }

    public static String formatTickDuration(int ticks)
    {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return minutes > 0 ? String.format("%d:%02d", minutes, seconds) : String.format("0:%02d", seconds);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return useDuration;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) //喝，为什么不喝？
    {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        FoodProperties food = stack.getFoodProperties(entity);
        if (food != null && entity instanceof Player player)
        {
            player.getFoodData().eat(food);
            for (var effectPair : food.effects())
            {
                if (level.random.nextFloat() < effectPair.probability())
                {
                    entity.addEffect(effectPair.effect());
                }
            }
        }

        if (!level.isClientSide && entity instanceof ServerPlayer player)
        {
            if (sanAdd != 0)
            {
                SanHelper.addPlayerSanAndSync(player, sanAdd);
            }

            if (meltDreamEnergyAdd != 0)
            {
                MeltDreamEnergyHelper.addPlayerMeltDreamEnergyAndSync(player, meltDreamEnergyAdd);
            }
        }

        if (!level.isClientSide)
        {
            if (entity instanceof Player player)
            {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (player instanceof ServerPlayer serverPlayer)
                {
                    CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
                }
            }
            onDrinkSpecial(entity, level);
        }

        if (entity instanceof Player player && player.isCreative())
        {
            return stack;
        }

        Item emptyContainer = null;
        FluidContainerRegistry.ContainerEntry entry = FluidContainerRegistry.getEntryForFillToEmpty(new ItemStack(this));
        if (entry != null)
        {
            emptyContainer = entry.emptyContainerItemStack.getItem();
        }
            else
            {
                ItemStack remainder = stack.getCraftingRemainingItem();
                if (!remainder.isEmpty())
                {
                    emptyContainer = remainder.getItem();
                }
            }
        stack.shrink(1);

        if (stack.isEmpty())
        {
            if (emptyContainer != null)
            {
                return new ItemStack(emptyContainer);
            }
            return ItemStack.EMPTY;
        }
            else
            {
                if (!level.isClientSide && emptyContainer != null)
                {
                    ItemStack containerStack = new ItemStack(emptyContainer);
                    if (entity instanceof Player player)
                    {
                        if (!player.getInventory().add(containerStack))
                        {
                            player.drop(containerStack, false);
                        }
                    }
                    else
                    {
                        IItemHandler handler = entity.getCapability(Capabilities.ItemHandler.ENTITY);
                        if (handler != null)
                        {
                            ItemStack remainder = ItemHandlerHelper.insertItemStacked(handler, containerStack, false);
                            if (!remainder.isEmpty())
                            {
                                entity.spawnAtLocation(remainder);
                            }
                        }
                        else
                        {
                            entity.spawnAtLocation(containerStack);
                        }
                    }
                }
                return stack;
            }
    }

    protected void onDrinkSpecial(LivingEntity entity, Level level)
    {
        //默认无操作
    }
}
