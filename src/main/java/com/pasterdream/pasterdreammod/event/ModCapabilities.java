package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagInventoryCapability;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarDataHandler;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarInventory;
import com.pasterdream.pasterdreammod.helper.fluidcontainercapability.GenericContainerFluidHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ModCapabilities
{
    private ModCapabilities()
    {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event)
    {
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, context) -> new FluidHandlerItemStack(ModDataComponents.ELIXIR_BOTTLE_FLUID, stack, 1000),
                ModItems.ELIXIR_BOTTLE.get());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.WEAPON_WORKSHOP_CRAFTING_TABLE.get(),
                (blockEntity, direction) -> direction == null ? blockEntity.getItemHandler() : blockEntity.getExternalHandler());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DYEDREAM_DESK.get(),
                (blockEntity, direction) -> blockEntity.getItemHandler());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CLAYPAN.get(),
                (blockEntity, direction) -> blockEntity.getItemHandler());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.CLAYPAN.get(),
                (blockEntity, direction) -> blockEntity.getFluidTank(0));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DREAM_CAULDRON.get(),
                (blockEntity, direction) -> direction == null ? blockEntity.getItemHandler() : blockEntity.getExternalHandler());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.DREAM_CAULDRON.get(),
                (blockEntity, direction) -> blockEntity.getExternalFluidHandler());
        event.registerItem(Capabilities.ItemHandler.ITEM,
                (stack, context) -> new StorageBagInventoryCapability(stack, StorageBagItem.SLOT_COUNT),
                ModItems.STORAGE_BAG.get());
        event.registerItem(Capabilities.ItemHandler.ITEM,
                (stack, context) -> new StorageBagInventoryCapability(stack, LargeStorageBagItem.SLOT_COUNT),
                ModItems.LARGE_STORAGE_BAG.get());
        event.registerItem(Capabilities.ItemHandler.ITEM,
                (stack, context) -> new MortarInventory(new MortarDataHandler(stack)),
                ModItems.MORTAR.get());
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, context) -> new MortarInventory(new MortarDataHandler(stack)),
                ModItems.MORTAR.get());
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, context) -> new GenericContainerFluidHandler(stack),
                ModItems.GLASS_JAR.get(),
                ModItems.GLASS_JAR_OF_WATER.get(),
                ModItems.GLASS_JAR_OF_MILK.get(),
                ModItems.GLASS_JAR_OF_YEAST.get(),
                ModItems.GLASS_JAR_OF_GUIDING_DRUG.get(),
                ModItems.GLASS_JAR_OF_WIND_PLANT_EXTRACT.get(),
                ModItems.GLASS_JAR_OF_INK.get(),
                ModItems.GLASS_CUP.get(),
                ModItems.GLASS_CUP_OF_APPLE_JUICE.get(),
                ModItems.GLASS_CUP_OF_UNCOOKED_DYEDREAM_FLOWER_TEA.get(),
                ModItems.GLASS_CUP_OF_COOKED_DYEDREAM_FLOWER_TEA.get(),
                ModItems.GLASS_CUP_OF_DYEDREAM_JUICE.get(),
                ModItems.GLASS_CUP_OF_HONEY_JUICE.get(),
                ModItems.GLASS_CUP_OF_WATERMELON_JUICE.get());
    }
}
