package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class UndefinedAspectStackHandler implements INBTSerializable<CompoundTag>
{
    protected HashMap<Aspect, AspectStack> stacks;
    protected boolean totalCapacity;
    protected int maxCapacity;

    public UndefinedAspectStackHandler(int size, boolean hasTotalCapacity, int capacity)
    {
        this.stacks = new HashMap<>(size);

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public UndefinedAspectStackHandler(int size, int capacity)
    {
        this.stacks = new HashMap<>(size);

        this.totalCapacity = false;
        this.maxCapacity = capacity;
    }

    public UndefinedAspectStackHandler(NonNullList<AspectStack> stacks, boolean hasTotalCapacity, int capacity)
    {
        this.stacks = new HashMap<>();
        stacks.forEach(stack -> this.stacks.put(stack.getAspect().get(), stack));

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public AspectStack getStackInSlot(Aspect slot)
    {
        return stacks.getOrDefault(slot, AspectStack.EMPTY);
    }

    public void setStackInSlot(AspectStack stack)
    {
        stacks.put(stack.getAspect().get(), stack);
    }

    public AspectStack insertAspect(AspectStack stack, boolean simulate)
    {

        if(!isValidSlot(stack.getAspect().get()))
            return AspectStack.EMPTY;

        AspectStack presentStack = this.getStackInSlot(stack.getAspect().get());

        int freeSpace = maxCapacity - getStored(stack.getAspect().get());
        int amountToPut = stack.getAmount();
        if (!presentStack.canStackWith(stack))
            return stack;
        if (freeSpace == 0) return stack;

        boolean reachedLimit = amountToPut > freeSpace;
        if (amountToPut > freeSpace)
        {
            amountToPut = freeSpace;
            stack.shrink(amountToPut);
        }
        if (!simulate)
        {
            if (presentStack.isEmpty())
                stacks.put(stack.getAspect().get(), stack.copyWithSize(amountToPut));
            else presentStack.grow(amountToPut);
        }

        return reachedLimit ? stack.copyWithSize(amountToPut) : AspectStack.EMPTY;
    }
    public AspectStack extractAspect(Aspect aspect, int amount, boolean simulate)
    {
        if (amount == 0)
            return AspectStack.EMPTY;

        AspectStack existing = stacks.get(aspect);
        if (existing.isEmpty())
            return AspectStack.EMPTY;

        int toTake = Math.min(amount, existing.getAmount());
        if (existing.getAmount() > toTake)
        {
            if (!simulate)
            {
                stacks.put(aspect, existing.copyWithSize(existing.getAmount() - toTake));
            }

            return existing.copyWithSize(toTake);
        } else
        {
            if (!simulate)
            {
                stacks.remove(aspect);
                return existing;
            } else return existing.copy();
        }
    }

    public boolean isValidSlot(Aspect aspect)
    {
        return !isFull(aspect);
    }

    public boolean isFull(Aspect aspect)
    {
        if(totalCapacity)
        {
            int stored = getStored(aspect);

            return stored >= maxCapacity;
        }
        else
        {
            return getStored(aspect) >= maxCapacity;
        }
    }

    public boolean isEmpty()
    {
        for(Map.Entry<Aspect, AspectStack> entry : this.stacks.entrySet())
        {
            return entry.getValue().isEmpty();
        }
        return true;
    }

    public int getStored(Aspect aspect)
    {
        if(totalCapacity)
        {
            int stored = 0;
            for(Map.Entry<Aspect, AspectStack> stack : stacks.entrySet())
                stored += stack.getValue().getAmount();

            return stored;
        }
        else return this.getStackInSlot(aspect).getAmount();
    }

    public int getSize()
    {
        return stacks.size();
    }

    public int getMaxCapacity()
    {
        return maxCapacity;
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();

        ListTag list = new ListTag();
        stacks.forEach((aspect, stack) -> list.add(stack.serializeNBT()));
        tag.put("Aspects", list);

        tag.putBoolean("total_capacity", totalCapacity);
        tag.putInt("max_capacity", maxCapacity);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag aspectList = nbt.getList("Aspects", Tag.TAG_COMPOUND);
        for (int i = 0; i < aspectList.size(); i++)
        {
            CompoundTag aspectTag = aspectList.getCompound(i);
            AspectStack stack = AspectStack.deserializeNBT(aspectTag);

            stacks.put(stack.getAspect().get(), stack);
        }

        this.totalCapacity = nbt.getBoolean("total_capacity");
        this.maxCapacity = nbt.getInt("max_capacity");
    }
}
