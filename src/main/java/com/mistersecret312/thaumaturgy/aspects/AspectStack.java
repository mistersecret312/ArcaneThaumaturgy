package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;

public class AspectStack
{
    public static final AspectStack EMPTY = new AspectStack();

    public static final Codec<AspectStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFileCodec.create(Aspect.REGISTRY_KEY, Aspect.CODEC, true).fieldOf("aspect").forGetter(AspectStack::getAspect),
            Codec.INT.fieldOf("amount").forGetter(AspectStack::getAmount)
    ).apply(instance, AspectStack::new));

    private Holder<Aspect> aspect;
    private int amount;

    public AspectStack(Holder<Aspect> aspect, int amount)
    {
        this.aspect = aspect;
        this.amount = amount;
    }

    public AspectStack(Holder<Aspect> aspect)
    {
        this(aspect, 1);
    }

    public AspectStack()
    {
        this.aspect = null;
    }

    public MutableComponent getTranslatable()
    {
        return Component.translatable("aspect.thaumaturgy."+this.getAspect().get().getName());
    }

    public AspectStack copy()
    {
        if(this.isEmpty())
            return EMPTY;

        else return new AspectStack(this.getAspect(), this.getAmount());
    }

    public AspectStack copyWithSize(int size)
    {
        if(size == 0)
            return EMPTY;

        else return new AspectStack(this.getAspect(), size);
    }

    public boolean canStackWith(AspectStack otherStack)
    {
        return this.isEmpty() || this.is(otherStack);
    }

    public boolean is(AspectStack other)
    {
        return this.getAspect() == other.getAspect();
    }

    public void grow(int increment)
    {
        this.setAmount(this.getAmount()+increment);
    }

    public void shrink(int decrement)
    {
        this.grow(-decrement);
    }

    public boolean isEmpty()
    {
        return this == EMPTY || this.amount <= 0;
    }

    public Holder<Aspect> getAspect()
    {
        return aspect;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();

        tag.putString("aspect", this.getAspect().get().getName());
        tag.putInt("amount", this.getAmount());

        return tag;
    }

    public static AspectStack deserializeNBT(CompoundTag tag)
    {
        Aspect aspect = Aspects.allAspects.get(tag.getString("aspect"));
        int amount = tag.getInt("amount");

        return new AspectStack(Holder.direct(aspect), amount);
    }
}