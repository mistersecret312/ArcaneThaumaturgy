package com.mistersecret312.thaumaturgy.containers;

import com.mistersecret312.thaumaturgy.aspects.UndefinedAspectStackHandler;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CrucibleContainer implements Container
{
    public ItemStack catalyst;
    public UndefinedAspectStackHandler handler;
    public CrucibleContainer(UndefinedAspectStackHandler handler, ItemStack catalyst)
    {
        this.handler = handler;
        this.catalyst = catalyst;
    }

    @Override
    public int getContainerSize()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return catalyst.isEmpty() && handler.isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot)
    {
        return catalyst;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount)
    {
        catalyst.shrink(pAmount);
        if(!catalyst.isEmpty())
            this.setChanged();

        return catalyst;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot)
    {
        if(catalyst.isEmpty())
            return ItemStack.EMPTY;
        else catalyst.setCount(0);
        return catalyst;
    }

    @Override
    public void setItem(int pSlot, ItemStack stack)
    {
        this.catalyst = stack;
    }

    @Override
    public void setChanged()
    {

    }

    @Override
    public boolean stillValid(Player pPlayer)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        this.catalyst.setCount(0);
        this.handler = new UndefinedAspectStackHandler(1, 1);
    }
}
