package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureBlockEntity.class)
public class StructureBlockEntityMixin
{
    @ModifyExpressionValue(method = "loadAdditional", at = @At(value = "CONSTANT", args = "intValue=48"))
    private int increaseSizeLimit(int original)
    {
        return Byte.MAX_VALUE;
    }

    @ModifyExpressionValue(method = "loadAdditional", at = @At(value = "CONSTANT", args = "intValue=-48"))
    private int decreaseOffsetLimit(int original)
    {
        return Byte.MIN_VALUE;
    }
}
