package com.pasterdream.pasterdreammod.world.block.doll.uuzdoll;

import com.pasterdream.pasterdreammod.world.block.doll.DollItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import java.util.function.Consumer;

public class UUZDollItem extends DollItem
{
    public UUZDollItem(Block block, Properties properties)
    {
        super(block, properties);
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
                    renderer = new UUZDollItemRenderer();
                }
                return renderer;
            }
        });
    }
}
