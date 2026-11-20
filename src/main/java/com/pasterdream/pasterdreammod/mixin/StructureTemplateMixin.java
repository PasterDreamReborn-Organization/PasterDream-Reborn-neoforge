package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 结构放置时不保留液体：不把结构内部及含水方块灌成水，避免水下结构被水填满。
 * <p>
 * 1.20.1 的 {@code StructurePlaceSettings#shouldKeepLiquids()} 在 1.21.1 被拆成 {@code LiquidSettings}，
 * 语义相反的 {@code StructurePlaceSettings#shouldApplyWaterlogging()} 承担了“给可含水方块灌水”的判断。
 * 结构 JSON 不写 {@code liquid_settings} 时默认 {@code APPLY_WATERLOGGING}，
 * 所以水下放置的结构（如梦境实验室）会被灌满水；这里在 {@code placeInWorld} 期间强制为 false。
 */
@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {

    private static final ThreadLocal<Boolean> FORCE_NO_KEEP_LIQUIDS = new ThreadLocal<>();

    @Inject(method = "placeInWorld", at = @At("HEAD"))
    private void setNoKeepLiquids(CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.set(true);
    }

    @Inject(method = "placeInWorld", at = @At("RETURN"))
    private void clearNoKeepLiquids(CallbackInfoReturnable<Boolean> cir) {
        FORCE_NO_KEEP_LIQUIDS.remove();
    }

    @ModifyExpressionValue(method = "placeInWorld",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;shouldApplyWaterlogging()Z"))
    private boolean forceNoKeepLiquids(boolean original) {
        if (FORCE_NO_KEEP_LIQUIDS.get() != null) {
            return false;
        }
        return original;
    }
}
