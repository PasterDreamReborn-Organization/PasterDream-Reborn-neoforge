package com.pasterdream.pasterdreammod.capability.meltdreamenergy;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class MeltDreamEnergy implements IMeltDreamEnergy, INBTSerializable<CompoundTag>
{
    private double meltDreamEnergy = 0.0;
    private boolean isNeedConsume = true;
    private boolean isConsumeDoubled = false;
    private double maxMeltDreamEnergy = 100.0;

    @Override
    public double getMeltDreamEnergy()
    {
        return meltDreamEnergy;
    }

    @Override
    public void setMeltDreamEnergy(double meltDreamEnergy)
    {
        this.meltDreamEnergy = meltDreamEnergy;
    }

    @Override
    public void addMeltDreamEnergy(double delta)
    {
        setMeltDreamEnergy(Math.max(0.0, meltDreamEnergy + delta));
    }

    @Override
    public void setIsOrNotNeedConsumeDreamEnergy(boolean isNeedConsume)
    {
        this.isNeedConsume = isNeedConsume;
    }

    @Override
    public boolean getIsOrNotNeedConsumeDreamEnergy()
    {
        return isNeedConsume;
    }

    @Override
    public void setIsConsumeDoubled(boolean isConsumeDoubled)
    {
        this.isConsumeDoubled = isConsumeDoubled;
    }

    @Override
    public boolean isConsumeDoubled()
    {
        return isConsumeDoubled;
    }

    @Override
    public double getMaxMeltDreamEnergy()
    {
        return maxMeltDreamEnergy;
    }

    @Override
    public void setMaxMeltDreamEnergy(double maxMeltDreamEnergy)
    {
        this.maxMeltDreamEnergy = maxMeltDreamEnergy;
    }

    @Override
    public void addMaxMeltDreamEnergy(double delta)
    {
        setMaxMeltDreamEnergy(delta + maxMeltDreamEnergy);
    }

    @Override
    public void copyValueFromOtherMeltDreamEnergy(IMeltDreamEnergy other)
    {
        this.meltDreamEnergy = other.getMeltDreamEnergy();
        this.isNeedConsume = other.getIsOrNotNeedConsumeDreamEnergy();
        this.isConsumeDoubled = other.isConsumeDoubled();
        this.maxMeltDreamEnergy = other.getMaxMeltDreamEnergy();
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider)
    {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("melt_dream_energy_count", getMeltDreamEnergy());
        tag.putBoolean("is_not_need", getIsOrNotNeedConsumeDreamEnergy());
        tag.putBoolean("is_consume_doubled", isConsumeDoubled());
        tag.putDouble("max_melt_dream_energy", getMaxMeltDreamEnergy());
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag)
    {
        setMeltDreamEnergy(tag.getDouble("melt_dream_energy_count"));
        setIsOrNotNeedConsumeDreamEnergy(tag.getBoolean("is_not_need"));
        setIsConsumeDoubled(tag.getBoolean("is_consume_doubled"));
        setMaxMeltDreamEnergy(tag.getDouble("max_melt_dream_energy"));
    }
}
