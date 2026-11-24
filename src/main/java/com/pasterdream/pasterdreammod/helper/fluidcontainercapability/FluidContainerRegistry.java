package com.pasterdream.pasterdreammod.helper.fluidcontainercapability;

import com.pasterdream.pasterdreammod.helper.potionhelper.GenericMobEffect;
import com.pasterdream.pasterdreammod.helper.potionhelper.PotionHelper;
import com.pasterdream.pasterdreammod.init.ModFluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FluidContainerRegistry
{
    private static final Map<Item, ContainerEntry> EMPTY_TO_FILL = new ConcurrentHashMap<>();
    private static final Map<Item, ContainerEntry> FILL_TO_EMPTY = new ConcurrentHashMap<>();
    private static final Map<Item, Map<Fluid, ContainerEntry>> EMPTY_FLUID_TO_ENTRY = new ConcurrentHashMap<>();

    public static void register(Item emptyItem, Fluid fluid, int amount, Item filledItem)
    {
        if (amount <= 0) throw new IllegalArgumentException("流体容器容量必须为正数");
        ContainerEntry entry = new ContainerEntry(new ItemStack(emptyItem), new FluidStack(fluid, amount), new ItemStack(filledItem));
        EMPTY_TO_FILL.put(emptyItem, entry);
        FILL_TO_EMPTY.put(filledItem, entry);
        EMPTY_FLUID_TO_ENTRY.computeIfAbsent(emptyItem, item -> new ConcurrentHashMap<>()).put(fluid, entry);
    }

    @Nullable
    public static ContainerEntry getEntryForFillToEmpty(ItemStack itemStack)
    {
        if(itemStack.getItem() == Items.POTION)
        {
            return new ContainerEntry(new ItemStack(Items.GLASS_BOTTLE), PotionHelper.createNBTPotion(PotionHelper.getListGenericMobEffectFromPotion(itemStack), 250), itemStack);
        }
            else
            {
                return FILL_TO_EMPTY.get(itemStack.getItem());
            }
    }

    @Nullable
    public static ContainerEntry getEntryForEmptyAndFluid(ItemStack emptyContainerItemStack, FluidStack fluidStack)
    {
        if(emptyContainerItemStack.getItem() == Items.GLASS_BOTTLE)
        {
            if (fluidStack.getFluid() == ModFluids.POTION.get())
            {
                List<GenericMobEffect> effectList = PotionHelper.getEffectType(fluidStack);
                ItemStack potionItem = PotionHelper.getEffectPotion(effectList);
                return new ContainerEntry(new ItemStack(Items.GLASS_BOTTLE), fluidStack.copyWithAmount(250), potionItem);
            }
            else
                if (fluidStack.getFluid() == Fluids.WATER)
                {
                    return new ContainerEntry(new ItemStack(Items.GLASS_BOTTLE), new FluidStack(Fluids.WATER, 250), PotionHelper.getWaterBottle());
                }
        }

        Map<Fluid, ContainerEntry> map = EMPTY_FLUID_TO_ENTRY.get(emptyContainerItemStack.getItem());
        return map != null ? map.get(fluidStack.getFluid()) : null;
    }

    @Nullable
    public static ContainerEntry getAnyEntryForEmpty(Item emptyItem)
    {
        return EMPTY_TO_FILL.get(emptyItem);
    }

    public static class ContainerEntry
    {
        public final ItemStack emptyContainerItemStack;
        public final FluidStack fluidStack;
        public final ItemStack fullContainerItemStack;

        public ContainerEntry(ItemStack emptyContainerItemStack, FluidStack fluidStack, ItemStack fullContainerItemStack)
        {
            this.emptyContainerItemStack = emptyContainerItemStack;
            this.fluidStack = fluidStack;
            this.fullContainerItemStack = fullContainerItemStack;
        }
    }
}
