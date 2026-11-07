package com.pasterdream.pasterdreammod.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BushBlock;

public class PlantBushBlock extends BushBlock {
    public static final MapCodec<PlantBushBlock> CODEC = simpleCodec(PlantBushBlock::new);

    public PlantBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
