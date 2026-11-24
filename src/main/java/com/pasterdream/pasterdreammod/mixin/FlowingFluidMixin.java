package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowingFluid.class)
public class FlowingFluidMixin {
    @Inject(method = "canHoldFluid", at = @At("HEAD"), cancellable = true)
    private void pasterdream$protectNonCollidingBlocks(BlockGetter level, BlockPos pos, BlockState state,
                                                      Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlockTags.FLUID_CAN_NOT_BRUSH_NOCOLLISSION_BLOCK)) {
            cir.setReturnValue(false);
        }
    }
}
