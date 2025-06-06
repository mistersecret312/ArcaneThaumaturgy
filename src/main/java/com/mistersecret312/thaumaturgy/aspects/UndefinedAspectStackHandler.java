package com.mistersecret312.thaumaturgy.aspects;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

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

    public UndefinedAspectStackHandler(List<AspectStack> stacks, boolean hasTotalCapacity, int capacity)
    {
        this.stacks = new HashMap<>();
        stacks.forEach(stack -> this.stacks.put(stack.getAspect(), stack));

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public AspectStack getStackInSlot(Aspect slot)
    {
        return stacks.getOrDefault(slot, AspectStack.EMPTY);
    }

    public AspectStack getStackInSlot(int slot)
    {
        return this.stacks.entrySet().stream().toList().get(slot).getValue();
    }

    public Aspect getRandomAspect()
    {
        Random random = new Random();
        List<Map.Entry<Aspect, AspectStack>> list = this.stacks.entrySet().stream().toList();
        if(!list.isEmpty())
            return list.get(random.nextInt(list.size())).getKey();
        else return null;
    }

    public void setStackInSlot(AspectStack stack)
    {
        stacks.put(stack.getAspect(), stack);
    }

    public AspectStack insertAspect(AspectStack stack, boolean simulate)
    {

        if(!isValidSlot(stack.getAspect()))
            return AspectStack.EMPTY;

        AspectStack presentStack = this.getStackInSlot(stack.getAspect());

        int freeSpace = maxCapacity - getStored(stack.getAspect());
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
                stacks.put(stack.getAspect(), stack.copyWithSize(amountToPut));
            else presentStack.grow(amountToPut);
            onContentsChanged(stack.getAspect());
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
                onContentsChanged(aspect);
            }

            return existing.copyWithSize(toTake);
        } else
        {
            if (!simulate)
            {
                stacks.remove(aspect);
                onContentsChanged(aspect);
                return existing;
            } else return existing.copy();
        }
    }

    public void onContentsChanged(Aspect aspect)
    {

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

    public List<AspectStack> toList()
    {
        List<AspectStack> aspectStacks = new ArrayList<>();
        this.stacks.entrySet().stream().toList().forEach(entry -> aspectStacks.add(entry.getValue()));
        return aspectStacks;
    }

    public void clear()
    {
        this.stacks = new HashMap<>(512);
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

    public int getTotalStored()
    {
        int stored = 0;
        for (Map.Entry<Aspect, AspectStack> stack : stacks.entrySet())
            stored += stack.getValue().getAmount();

        return stored;
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

            stacks.put(stack.getAspect(), stack);
        }

        this.totalCapacity = nbt.getBoolean("total_capacity");
        this.maxCapacity = nbt.getInt("max_capacity");
    }
}
