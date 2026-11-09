package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.pasterdream.pasterdreammod.init.ModDataComponents;
import com.pasterdream.pasterdreammod.init.ModItems;
import net.minecraft.world.item.ItemStack;

public class DreamNotesBookWithNBTToCreativeModeTab
{
    public static ItemStack buildNBT(String content)
    {
        ItemStack itemStack = new ItemStack(ModItems.DREAM_NOTES_BOOK.get());
        itemStack.set(ModDataComponents.DREAM_NOTE_CONTENT.get(), content);
        return itemStack;
    }
}
