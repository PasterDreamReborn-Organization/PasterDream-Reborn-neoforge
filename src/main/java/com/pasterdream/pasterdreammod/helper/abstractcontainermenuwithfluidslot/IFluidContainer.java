package com.pasterdream.pasterdreammod.helper.abstractcontainermenuwithfluidslot;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidContainer
{
    int getFluidContainerSize();
    FluidStack getFluid(int index);
    void setFluid(int index, FluidStack stack);
    default int getMaxFluidCapacity(int index)
    {
        return 1000;
    }
    // 方法名禁止与原版 Container 的方法重名（setChanged/stillValid/startOpen/stopOpen 等）。
    // 上游 Forge 1.20.1 的 reobf 只按原版继承关系改名：实现类若同时 implements net.minecraft.world.Container，
    // 同名实现会被重混淆成 SRG 名（如 m_6596_），而本接口的声明不会跟着改，正式环境经接口调用即 AbstractMethodError。
    // NeoForge 1.21.1 不做 reobf、开发与运行时同为 mojmap，不会触发该崩溃；此处与上游保持同名，避免两端接口漂移。
    void setFluidChanged();
    default boolean canPlaceFluid(int index, FluidStack stack)
    {
        return true;
    }
    default boolean canTakeFluid(int index, Player player)
    {
        return true;
    }
}
