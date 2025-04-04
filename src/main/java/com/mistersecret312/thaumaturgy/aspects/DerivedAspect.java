package com.mistersecret312.thaumaturgy.aspects;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Supplier;

public class DerivedAspect extends Aspect
{

    private DerivationData derive;

    public DerivedAspect(List<Integer> color, ResourceLocation texture, DerivationData derive)
    {
        super(color, texture);
        this.derive = derive;
    }

    public DerivationData getDerivationData()
    {
        return derive;
    }
}
