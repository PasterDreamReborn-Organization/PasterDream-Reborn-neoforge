package com.pasterdream.pasterdreammod.capability.san;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.minecraft.util.Mth;

public class San implements ISan, INBTSerializable<CompoundTag>
{
    private double sanValue = 100.0;
    private boolean isEnabled = true;
    private double maxSanValue = 100.0;
    private double sanRate = 0.0;

    @Override
    public double getSanValue()
    {
        return sanValue;
    }

    @Override
    public void setSanValue(double sanValue)
    {
        this.sanValue = sanValue;
    }

    @Override
    public void addSanValue(double delta)
    {
        if(!isEnabled) return;
        setSanValue(Mth.clamp(sanValue + delta, 0.0, maxSanValue));
    }

    @Override
    public void setIsSanEnable(boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean getIsSanEnabled()
    {
        return isEnabled;
    }

    @Override
    public double getMaxSanValue()
    {
        return maxSanValue;
    }

    @Override
    public void setMaxSanValue(double maxSanValue)
    {
        this.maxSanValue = maxSanValue;
    }

    @Override
    public void addMaxSanValue(double delta)
    {
        setMaxSanValue(delta + maxSanValue);
    }

    @Override
    public double getSanRate()
    {
        return sanRate;
    }

    @Override
    public void setSanRate(double sanRate)
    {
        this.sanRate = sanRate;
    }

    @Override
    public void copyValueFromOtherSan(ISan other)
    {
        this.sanValue = other.getSanValue();
        this.maxSanValue = other.getMaxSanValue();
        this.isEnabled = other.getIsSanEnabled();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider)
    {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("san_value", getSanValue());
        tag.putBoolean("is_enabled", getIsSanEnabled());
        tag.putDouble("max_san_value", getMaxSanValue());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag)
    {
        setSanValue(tag.getDouble("san_value"));
        setIsSanEnable(tag.getBoolean("is_enabled"));
        setMaxSanValue(tag.getDouble("max_san_value"));
    }
}
