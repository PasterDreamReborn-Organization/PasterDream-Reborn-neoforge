package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.world.item.storagebag.LargeStorageBagItem;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagInventoryCapability;
import com.pasterdream.pasterdreammod.world.item.storagebag.StorageBagItem;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarDataHandler;
import com.pasterdream.pasterdreammod.world.item.mortar.MortarInventory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = PasterDreamMod.MOD_ID)
public final class ModCapabilities
{
    private ModCapabilities()
    {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.WEAPON_WORKSHOP_CRAFTING_TABLE.get(),
                (blockEntity, direction) -> direction == null ? blockEntity.getItemHandler() : blockEntity.getExternalHandler());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DYEDREAM_DESK.get(),
                (blockEntity, direction) -> blockEntity.getItemHandler());
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
    }
}
