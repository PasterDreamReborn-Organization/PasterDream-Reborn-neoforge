package com.pasterdream.pasterdreammod.event;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModBlockEntities;
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
    }
}
