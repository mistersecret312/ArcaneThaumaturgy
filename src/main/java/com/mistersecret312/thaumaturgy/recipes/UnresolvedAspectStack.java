package com.mistersecret312.thaumaturgy.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class UnresolvedAspectStack
{
    public static final Codec<UnresolvedAspectStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("aspect_name").forGetter(UnresolvedAspectStack::getName),
            Codec.INT.fieldOf("amount").forGetter(UnresolvedAspectStack::getAmount)
    ).apply(instance, UnresolvedAspectStack::new));

    public String name;
    public int amount;

    public UnresolvedAspectStack(String id, int amount)
    {
        this.name = id;
        this.amount = amount;
    }

    public String getName()
    {
        return name;
    }

    public int getAmount()
    {
        return amount;
    }
}
