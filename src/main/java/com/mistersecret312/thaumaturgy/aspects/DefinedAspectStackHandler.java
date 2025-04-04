package com.mistersecret312.thaumaturgy.aspects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinedAspectStackHandler implements INBTSerializable<CompoundTag>
{
    protected HashMap<Aspect, AspectStack> definedStacks;
    protected boolean totalCapacity;
    protected int maxCapacity;

    public DefinedAspectStackHandler(List<Aspect> aspects, boolean hasTotalCapacity, int capacity)
    {
        this.definedStacks = new HashMap<>(aspects.size());
        aspects.forEach(aspect -> definedStacks.put(aspect, AspectStack.EMPTY));

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public DefinedAspectStackHandler(List<Aspect> aspects, int capacity)
    {
        this.definedStacks = new HashMap<>(aspects.size());
        aspects.forEach(aspect -> definedStacks.put(aspect, AspectStack.EMPTY));

        this.totalCapacity = false;
        this.maxCapacity = capacity;
    }

    public AspectStack getStackInSlot(Aspect aspect)
    {
        return definedStacks.get(aspect);
    }

    public void setStackInSlot(Aspect aspect, AspectStack stack)
    {
        definedStacks.put(aspect, stack);
    }

    public AspectStack insertAspect(Aspect aspect, AspectStack stack, boolean simulate)
    {
        if (canInsert(stack))
        {
            AspectStack presentStack = definedStacks.get(aspect);

            int freeSpace = maxCapacity-getStored(aspect);
            int amountToPut = stack.getAmount();
            if (!presentStack.canStackWith(stack)) return stack;
            if (freeSpace == 0) return stack;

            boolean reachedLimit = amountToPut > freeSpace;
            if (amountToPut > freeSpace)
            {
                amountToPut = freeSpace;
                stack.shrink(amountToPut);
            }
            if (!simulate)
            {
                if (presentStack.isEmpty()) definedStacks.put(aspect, stack.copyWithSize(amountToPut));
                else presentStack.grow(amountToPut);
            }

            return reachedLimit ? stack.copyWithSize(amountToPut) : AspectStack.EMPTY;
        }
        else return AspectStack.EMPTY;
    }

    public AspectStack extractAspect(Aspect aspect, int amount, boolean simulate)
    {
        if(canExtract(aspect))
        {
            if (amount == 0) return AspectStack.EMPTY;

            AspectStack existing = definedStacks.get(aspect);
            if(existing.isEmpty())
                return AspectStack.EMPTY;

            int toTake = Math.min(amount, existing.getAmount());
            if(existing.getAmount() > toTake)
            {
                if(!simulate)
                {
                    definedStacks.put(aspect, existing.copyWithSize(existing.getAmount()-toTake));
                }

                return existing.copyWithSize(toTake);
            }
            else
            {
                if (!simulate)
                {
                    definedStacks.put(aspect, AspectStack.EMPTY);
                    return existing;
                } else return existing.copy();
            }
        }

        return AspectStack.EMPTY;
    }

    public boolean canInsert(AspectStack stack)
    {
        if(definedStacks.containsKey(stack.getAspect()))
        {
            return !isFull(stack.getAspect());
        }
        else return false;
    }

    public boolean canExtract(Aspect aspect)
    {
        return definedStacks.containsKey(aspect);
    }

    public boolean isFull(Aspect stack)
    {
        if(totalCapacity)
        {
            int stored = getStored(stack);

            return stored >= maxCapacity;
        }
        else
        {
            return getStored(stack) >= maxCapacity;
        }
    }

    public int getTotalStored()
    {

        int stored = 0;
        for (Map.Entry<Aspect, AspectStack> entry : definedStacks.entrySet())
            stored += entry.getValue().getAmount();

        return stored;
    }

    public int getStored(Aspect aspect)
    {
        if(totalCapacity)
        {
            int stored = 0;
            for (Map.Entry<Aspect, AspectStack> entry : definedStacks.entrySet())
                stored += entry.getValue().getAmount();

            return stored;
        }
        else return definedStacks.get(aspect).getAmount();
    }

    public int getSize()
    {
        return definedStacks.size();
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
        definedStacks.forEach((aspect, aspectStack) -> list.add(aspectStack.serializeNBT()));
        tag.put("Aspects", list);

        tag.putBoolean("total_capacity", totalCapacity);
        tag.putInt("max_capacity", maxCapacity);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        nbt.getList("Aspects", Tag.TAG_COMPOUND).forEach(tag -> {
            AspectStack stack = AspectStack.deserializeNBT(((CompoundTag) tag));
            definedStacks.put(stack.getAspect(), stack);
        });

        this.totalCapacity = nbt.getBoolean("total_capacity");
        this.maxCapacity = nbt.getInt("max_capacity");
    }
}
