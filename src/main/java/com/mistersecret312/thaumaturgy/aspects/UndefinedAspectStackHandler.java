package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public class UndefinedAspectStackHandler implements INBTSerializable<CompoundTag>
{
    protected NonNullList<AspectStack> stacks;
    protected boolean totalCapacity;
    protected int maxCapacity;

    public UndefinedAspectStackHandler(int size, boolean hasTotalCapacity, int capacity)
    {
        this.stacks = NonNullList.withSize(size, AspectStack.EMPTY);

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public UndefinedAspectStackHandler(int size, int capacity)
    {
        this.stacks = NonNullList.withSize(size, AspectStack.EMPTY);

        this.totalCapacity = false;
        this.maxCapacity = capacity;
    }

    public UndefinedAspectStackHandler(NonNullList<AspectStack> stacks, boolean hasTotalCapacity, int capacity)
    {
        this.stacks = stacks;

        this.totalCapacity = hasTotalCapacity;
        this.maxCapacity = capacity;
    }

    public AspectStack getStackInSlot(int slot)
    {
        return stacks.get(slot);
    }

    public void setStackInSlot(int slot, AspectStack stack)
    {
        stacks.set(slot, stack);
    }

    public AspectStack insertAspect(int slot, AspectStack stack, boolean simulate)
    {
        validateSlotIndex(slot);

        if(!isValidSlot(stack, slot))
            return AspectStack.EMPTY;

        AspectStack presentStack = stacks.get(slot);

        int freeSpace = maxCapacity - getStored(slot);
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
            if (presentStack.isEmpty()) stacks.set(slot, stack.copyWithSize(amountToPut));
            else presentStack.grow(amountToPut);
        }

        return reachedLimit ? stack.copyWithSize(amountToPut) : AspectStack.EMPTY;
    }
    public AspectStack extractAspect(int slot, int amount, boolean simulate)
    {
        if (amount == 0)
            return AspectStack.EMPTY;
        validateSlotIndex(slot);


        AspectStack existing = stacks.get(slot);
        if (existing.isEmpty())
            return AspectStack.EMPTY;

        int toTake = Math.min(amount, existing.getAmount());
        if (existing.getAmount() > toTake)
        {
            if (!simulate)
            {
                stacks.set(slot, existing.copyWithSize(existing.getAmount() - toTake));
            }

            return existing.copyWithSize(toTake);
        } else
        {
            if (!simulate)
            {
                stacks.set(slot, AspectStack.EMPTY);
                return existing;
            } else return existing.copy();
        }
    }

    public boolean isValidSlot(AspectStack stack, int slot)
    {
        return stacks.get(slot).canStackWith(stack) && !isFull(slot);
    }

    public boolean isFull(int slot)
    {
        if(totalCapacity)
        {
            int stored = getStored(slot);

            return stored >= maxCapacity;
        }
        else
        {
            return getStored(slot) >= maxCapacity;
        }
    }

    public int getStored(int slot)
    {
        if(totalCapacity)
        {
            int stored = 0;
            for(AspectStack stack : stacks)
                stored += stack.getAmount();

            return stored;
        }
        else return stacks.get(slot).getAmount();
    }

    protected void validateSlotIndex(int slot)
    {
        if(slot < 0 || slot >= stacks.size())
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
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
        stacks.forEach(aspectStack -> list.add(aspectStack.serializeNBT()));
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

            stacks.set(i, stack);
        }

        this.totalCapacity = nbt.getBoolean("total_capacity");
        this.maxCapacity = nbt.getInt("max_capacity");
    }
}
