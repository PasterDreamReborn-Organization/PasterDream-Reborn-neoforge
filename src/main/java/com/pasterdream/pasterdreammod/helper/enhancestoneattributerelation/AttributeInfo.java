package com.pasterdream.pasterdreammod.helper.enhancestoneattributerelation;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public record AttributeInfo(Holder<Attribute> attribute, double min, double max){}
