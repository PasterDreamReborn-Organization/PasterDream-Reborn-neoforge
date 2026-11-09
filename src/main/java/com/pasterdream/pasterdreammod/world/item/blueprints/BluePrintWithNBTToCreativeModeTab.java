package com.pasterdream.pasterdreammod.world.item.blueprints;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.ItemStack;

public class BluePrintWithNBTToCreativeModeTab
{
    public static ItemStack buildNBT(String content)
    {
        ItemStack itemStack = new ItemStack(ModItems.BLUE_PRINT.get());
        itemStack.set(ModDataComponents.BLUEPRINT_CONTENT.get(), content);
        return itemStack;
    }
}
