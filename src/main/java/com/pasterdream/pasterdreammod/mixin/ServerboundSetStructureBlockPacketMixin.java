package com.pasterdream.pasterdreammod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerboundSetStructureBlockPacket.class)
public class ServerboundSetStructureBlockPacketMixin
{
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At(value = "CONSTANT", args = "intValue=48"))
    private int increaseSizeLimit(int original)
    {
        return Byte.MAX_VALUE;
    }

    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At(value = "CONSTANT", args = "intValue=-48"))
    private int decreaseOffsetMin(int original)
    {
        return Byte.MIN_VALUE;
    }

    // 在写入有符号字节前限制范围，避免结构尺寸或偏移在传输时回绕。
    @ModifyArg(method = "write", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/FriendlyByteBuf;writeByte(I)Lnet/minecraft/network/FriendlyByteBuf;"), index = 0)
    private int clampSerializedByte(int value)
    {
        return Mth.clamp(value, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
