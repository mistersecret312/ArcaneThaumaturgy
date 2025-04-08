package com.mistersecret312.thaumaturgy.containers;

import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class ArcaneWorkbenchCraftingContainer implements CraftingContainer
{
    public List<ItemStack> items;
    public ArcaneWorkbenchCraftingContainer(ItemStackHandler items)
    {
        this.items = new ArrayList<>();
        for (int i = 0; i < items.getSlots(); i++)
            this.items.add(items.getStackInSlot(i));

    }

    @Override
    public int getWidth()
    {
        return 3;
    }

    @Override
    public int getHeight()
    {
        return 3;
    }

    @Override
    public List<ItemStack> getItems()
    {
        return items;
    }

    @Override
    public int getContainerSize()
    {
        return items.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack stack : this.items)
            if(!stack.isEmpty())
                return false;

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot)
    {
        return pSlot >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount)
    {
        return ContainerHelper.removeItem(this.items, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot)
    {
        return ContainerHelper.takeItem(this.items, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack)
    {
        this.items.set(pSlot, pStack);
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
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents pContents)
    {
        for(ItemStack stack : this.items)
            pContents.accountSimpleStack(stack);
    }
}
