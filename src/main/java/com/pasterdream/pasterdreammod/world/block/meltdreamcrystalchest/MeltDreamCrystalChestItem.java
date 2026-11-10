package com.pasterdream.pasterdreammod.world.block.meltdreamcrystalchest;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class MeltDreamCrystalChestItem extends BlockItem implements GeoItem
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MeltDreamCrystalChestItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(itemStack, context, tooltip, flag);
        CompoundTag tag = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if(tag != null && tag.contains("LootTables"))
        {
            ListTag listTag = tag.getList("LootTables", Tag.TAG_COMPOUND);
            CompoundTag normalLootTable = !listTag.isEmpty() ? listTag.getCompound(0) : null;
            CompoundTag rareLootTable = listTag.size() > 1 ? listTag.getCompound(1) : null;
            CompoundTag legendLootTable = listTag.size() > 2 ? listTag.getCompound(2) : null;

            if(normalLootTable != null)
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.normal_loot_table", normalLootTable.getString("LootTable"), normalLootTable.getInt("weight"), normalLootTable.getDouble("luck_multiplier_increase")));
                if(rareLootTable != null)
                {
                    tooltip.add(Component.translatable("tooltip.pasterdream.rare_loot_table", rareLootTable.getString("LootTable"), rareLootTable.getInt("weight"), rareLootTable.getDouble("luck_multiplier_increase")));
                    if(legendLootTable != null)
                    {
                        tooltip.add(Component.translatable("tooltip.pasterdream.legend_loot_table", legendLootTable.getString("LootTable"), legendLootTable.getInt("weight"), legendLootTable.getDouble("luck_multiplier_increase")));
                    }
                }
            }
        }
            else
            {
                tooltip.add(Component.translatable("tooltip.pasterdream.not_have_loot_table"));
            }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "state", 0, state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer)
    {
        consumer.accept(new GeoRenderProvider()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer()
            {
                if (renderer == null)
                {
                    renderer = new MeltDreamCrystalChestItemRenderer();
                }
                return renderer;
            }
        });
    }
}
