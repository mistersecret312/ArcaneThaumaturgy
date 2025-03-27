package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.ItemStack;

public class AspectStack
{
    public static final Codec<AspectStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFileCodec.create(Aspect.REGISTRY_KEY, Aspect.CODEC, false).fieldOf("aspect").forGetter(AspectStack::getAspect),
            Codec.INT.fieldOf("amount").forGetter(AspectStack::getAmount)
    ).apply(instance, AspectStack::new));

    private Holder<Aspect> aspect;
    private int amount;

    public AspectStack(Holder<Aspect> aspect, int amount)
    {
        this.aspect = aspect;
        this.amount = amount;
    }

    public Holder<Aspect> getAspect()
    {
        return aspect;
    }

    public int getAmount()
    {
        return amount;
    }
}