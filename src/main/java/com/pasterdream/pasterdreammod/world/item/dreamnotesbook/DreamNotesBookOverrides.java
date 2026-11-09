package com.pasterdream.pasterdreammod.world.item.dreamnotesbook;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DreamNotesBookOverrides extends ItemOverrides
{
    public DreamNotesBookOverrides()
    {
        super();
    }

    @Override
    public BakedModel resolve(BakedModel original, ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed)
    {
        String contentName = itemStack.get(ModDataComponents.DREAM_NOTE_CONTENT.get());
        if (contentName != null)
        {
            DreamNotesBookInfo info = DreamNotesBookRegistry.getInfo(contentName);
            if (info != null && info.itemTexture() != null)
            {
                return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(info.itemTexture().withPrefix("item/"), ModelResourceLocation.STANDALONE_VARIANT));
            }
            else
            {
                return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dream_notes_book"), "inventory"));
            }
        }
        return original;
    }
}
